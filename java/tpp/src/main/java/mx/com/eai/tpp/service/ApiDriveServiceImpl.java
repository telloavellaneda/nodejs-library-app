package mx.com.eai.tpp.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Copy;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import mx.com.eai.tpp.util.Constant;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Service;

@Service
public class ApiDriveServiceImpl implements ApiDriveService
{

    @Resource(name = "rutas")
    private Properties rutasProperties;
    private static final String APPLICATION_NAME = "eaiTPP";
    private static java.io.File DATA_STORE_DIR;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;
    private static List<String> SCOPES;

    private static Drive drive;

    static
    {
        try
        {
            DATA_STORE_DIR = new java.io.File("/opt/eaiforce/drive");
//            DATA_STORE_DIR = new java.io.File("/tpp/drive");
            SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE);
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    private static Credential authorize() throws Exception
    {
        InputStream in = ApiDriveServiceImpl.class.getResourceAsStream("/drive_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow
                = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .setApprovalPrompt("auto")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void getDriveService() throws Exception
    {
        Credential credential = authorize();
        drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public String createFolder(String folder, String driveParentId) throws Exception
    {
        File body = new File();
        body.setTitle(folder);
        if (driveParentId != null && !driveParentId.equals(""))
        {
            body.setParents(Arrays.asList(new ParentReference().setId(driveParentId)));
        }
        body.setMimeType("application/vnd.google-apps.folder");

        return drive.files().insert(body).execute().getId();
    }

    public String uploadFile(UploadedFile file, String fileName, String driveID, String driveParentId) throws Exception
    {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType extension = allTypes.forName(file.getContentType());
        fileName = fileName + extension.getExtension();

        OutputStream out = new FileOutputStream(new java.io.File(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName));

        int read = 0;
        byte[] bytes = new byte[1024];

        InputStream in = file.getInputstream();
        while ((read = in.read(bytes)) != -1)
        {
            out.write(bytes, 0, read);
        }

        file.getInputstream().close();
        out.flush();
        out.close();

        java.io.File fileTemp = new java.io.File(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);

        driveID = this.uploadFileGoogleDrive(fileTemp, driveID, driveParentId);
        fileTemp.delete();
        return driveID;
    }

    public String copyFile(String driveIDCopy, String driveID, String driveParentId) throws Exception
    {
        File fileMetadata = drive.files().get(driveIDCopy).execute();
        if (driveParentId != null && !driveParentId.equals(""))
        {
            fileMetadata.setParents(Arrays.asList(new ParentReference().setId(driveParentId)));
        }

        if (driveID != null && !driveID.equals(""))
        {
            this.deleteFile(driveID);
        }
        Copy copy = drive.files().copy(driveIDCopy, fileMetadata);
        return copy.execute().getId();
    }

    public String uploadFileGoogleDrive(java.io.File file, String driveID, String driveParentId) throws Exception
    {
        File fileMetadata = new File();
        fileMetadata.setTitle(file.getName());
        if (driveParentId != null && !driveParentId.equals(""))
        {
            fileMetadata.setParents(Arrays.asList(new ParentReference().setId(driveParentId)));
        }
        FileContent mediaContent = new FileContent(null, file);

        if (driveID != null && !driveID.equals(""))
        {
            this.deleteFile(driveID);
        }
        Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(false);
        return insert.execute().getId();
    }

    public void deleteFile(String driveID) throws Exception
    {
        drive.files().delete(driveID).execute();
    }

    public String downloadFile(String driveID) throws Exception
    {
        java.io.File parentDir = new java.io.File(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL));

        File file = drive.files().get(driveID).execute();

        OutputStream out = new FileOutputStream(new java.io.File(parentDir, file.getTitle()));

        MediaHttpDownloader downloader = new MediaHttpDownloader(HTTP_TRANSPORT, drive.getRequestFactory().getInitializer());
        downloader.setDirectDownloadEnabled(false);
        downloader.download(new GenericUrl(file.getDownloadUrl()), out);
        return file.getTitle();
    }
}
