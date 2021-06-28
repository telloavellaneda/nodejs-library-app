package force.eai.mx.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import force.eai.mx.util.Documento;

public class DriveSender {
	
    private static String APPLICATION_NAME 	= "eaiforce";
    private static String DATA_STORE_NAME 	= "google/drive";
    private static String JSON_CREDENTIALS 	= "google/drive/drive_secret.json";
    
    private static java.io.File 		DATA_STORE_DIR;
    private static JsonFactory 			JSON_FACTORY;
    private static HttpTransport 		HTTP_TRANSPORT;
    private static List<String> 		SCOPES;
    private static FileDataStoreFactory DATA_STORE_FACTORY;    
    
    private Drive drive = null;
    
    static {
        try {
        	DATA_STORE_NAME = DriveSender
        			.class
        			.getProtectionDomain()
        			.getCodeSource()
        			.getLocation()
        			.getPath() + "/" + DATA_STORE_NAME;
        	
        	DATA_STORE_DIR 		= new java.io.File(DATA_STORE_NAME);
            DATA_STORE_FACTORY 	= new FileDataStoreFactory(DATA_STORE_DIR);

        	JSON_FACTORY 		= JacksonFactory.getDefaultInstance();
            HTTP_TRANSPORT 		= GoogleNetHttpTransport.newTrustedTransport();
            SCOPES				= Arrays.asList(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void getDriveService() throws IOException {
        Credential credential = authorize();
        drive = new Drive
        		.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
        		.setApplicationName(APPLICATION_NAME)
        		.build();
    }
    
    public static Credential authorize() throws IOException {
        InputStream in = DriveSender.class.getClassLoader().getResourceAsStream(JSON_CREDENTIALS);        
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
        		.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .setApprovalPrompt("auto")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }
    
	public String upsertFolder(Documento documento) throws Exception {
		File body = new File();

		body.setTitle(documento.getDriveTitle());
		body.setDescription(documento.getDriveDescription());
		body.setMimeType("application/vnd.google-apps.folder");
		if ( !documento.getDriveParentId().equals("") )
			body.setParents( Arrays.asList(new ParentReference().setId(documento.getDriveParentId())) );
		
		if ( documento.getDriveId().equals("") )
			return drive.files().insert(body).execute().getId();
		else {
			body.setId(documento.getDriveId());
			return drive.files().update(documento.getDriveId(), body).execute().getId();
		}
	}

	public String upsertFile(Documento documento) throws Exception {
		File body = new File();
		java.io.File fileContent = new java.io.File(documento.getNombreArchivo());
    	FileContent mediaContent = new FileContent(null, fileContent);

    	body.setTitle(documento.getDriveTitle());
		body.setDescription(documento.getDriveDescription());
		if ( !documento.getDriveParentId().equals("") )
			body.setParents( Arrays.asList(new ParentReference().setId(documento.getDriveParentId())) );
    	
    	if ( documento.getDriveId().equals("") )
    		return drive.files().insert(body, mediaContent).execute().getId();

    	else {
			body.setId(documento.getDriveId());
    		return drive.files().update(documento.getDriveId(), body, mediaContent).execute().getId();
		}
	}

	public void downloadFile(Documento documento) throws Exception {
		File file = getFile(documento.getDriveId());
		java.io.File parentDir = new java.io.File(documento.getDirectorio());
		if ( !parentDir.exists() && !parentDir.mkdirs() )
			throw new IOException("Unable to create parent directory");
		
	    OutputStream out = new FileOutputStream(new java.io.File(parentDir, file.getTitle()));

	    MediaHttpDownloader downloader = new MediaHttpDownloader(HTTP_TRANSPORT, drive.getRequestFactory().getInitializer());
	    downloader.setDirectDownloadEnabled(false);
	    //downloader.setProgressListener( new DriveSenderListener() );
	    downloader.download(new GenericUrl( file.getDownloadUrl() ), out);
	}
	
	public Iterator<String> generateIds(int maxResults) throws Exception {
		return drive.files().generateIds().setMaxResults(maxResults).execute().getIds().iterator();
	}
	
	public File getFile(String id) throws Exception {
		return drive.files().get(id).execute();
	}
	
	public File searchObject(String title, String operator) {
		try {
			return drive.files().list().setQ("title " + operator + " '" + title + "'").execute().getItems().get(0);			
		} catch(Exception e) {
			return null;
		}
	}
	
	public String getTitle(String id) {
		try {
			return drive.files().get(id).execute().getTitle();
		} catch (Exception e) {
			return "";
		}
	}
    
    @SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
        // Build a new authorized API client service.    	
    	DriveSender drive = new DriveSender();
        drive.getDriveService();

    	Documento documento;
    	String appId = "";
        String envId = "";
        String idCliente = "";
        String idUsuario = "";
        String forceId = "";
        String docId = "";
        
    	appId = "0Bz1W8SPophMBYVF5TnVvNW9WWDQ";
    	envId = "0Bz1W8SPophMBR0tHVElZZEZ0bVE";
    	idCliente = "0Bz1W8SPophMBZHNnWEJNVUdUOWc";
    	idUsuario = "0Bz1W8SPophMBbjVVWDd5Z2RrYXc";
    	forceId = "0Bz1W8SPophMBNE9vVjFKa0dFdW8";
    	docId = "0Bz1W8SPophMBX0hoSHNQdWlyWUU";
        
        String forceAppName = "eaiforce";
        String environment = "dev";
        String forceIdCliente = "1003";
        String forceIdUsuario = "9008";
        String forceForceId = "802134";
        String forceDocName = "802134-01.pdf";
        String fileName = "/Users/sateav/Downloads/EAI_Contrato-Confidencialidad_v1_2016.pdf";

        //System.out.println( "Por Id: " + drive.getFile("0Bz1W8SPophMBMXdCRzltQy0yVEk").getTitle());
        System.out.println( "Por Titulo: " + drive.searchObject("802124d", "="));

        String searched = "A PDF";
        //String searchId = drive.searchObject(searched).getId();
//        System.out.println("buscado " + searchId);
        
        documento = new Documento();
        documento.setDriveId(appId);
        documento.setDriveTitle(forceAppName);
        documento.setDriveDescription("EAI Force");
//        appId = drive.upsertFolder(documento);
//        System.out.println("appName " + appId);

        documento = new Documento();
        documento.setDriveId(envId);
        documento.setDriveTitle(environment);
        documento.setDriveDescription( environment.toUpperCase() + " Environment");
        documento.setDriveParentId(appId);
//        envId = drive.upsertFolder(documento);
//        System.out.println("envId " + envId);

        documento = new Documento();
        documento.setDriveId(idCliente);
        documento.setDriveTitle(forceIdCliente);
        documento.setDriveDescription("Cliente " + forceIdCliente);
        documento.setDriveParentId(envId);
//        idCliente = drive.upsertFolder(documento);
//        System.out.println("idCliente " + idCliente);

        documento = new Documento();
        documento.setDriveId(idUsuario);
        documento.setDriveTitle(forceIdUsuario);
        documento.setDriveDescription("Usuario " + forceIdUsuario);
        documento.setDriveParentId(idCliente);
//        idUsuario = drive.upsertFolder(documento);
//        System.out.println("idUsuario " + idUsuario);

        documento = new Documento();
        documento.setDriveId(forceId);
        documento.setDriveTitle(forceForceId);
        documento.setDriveDescription("Force Id " + forceForceId);
        documento.setDriveParentId(idUsuario);
//        forceId = drive.upsertFolder(documento);
//        System.out.println("forceId " + forceId);

        documento = new Documento();
        documento.setDriveId(docId);
        documento.setDriveTitle(forceDocName);
        documento.setDriveDescription(forceDocName);
        documento.setDriveParentId(forceId);
        documento.setNombreArchivo(fileName);
//        docId = drive.upsertFile(documento);
//        System.out.println("documento " + docId);
        
        documento = new Documento();
        documento.setDriveId(docId);
        documento.setDirectorio("/Users/sateav/Downloads");
//        drive.downloadFile(documento);
        
        documento = new Documento();
//        documento.setDriveId(searchId);
        documento.setDirectorio("/Users/sateav/Downloads");
//        drive.downloadFile(documento);
    }
}
