package force.eai.mx.orchestration;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import force.eai.mx.util.Force;
import force.eai.mx.util.Cliente;
import force.eai.mx.util.Documento;
import force.eai.mx.google.DriveSender;

public class UploadFile {

	private File file;
	private FileItem fileItem;
	
	public String upload(HttpServletRequest request, String folder, int maxSize) throws Exception {	
		// File Upload
		int maxFileSize = 4000 * maxSize;
		int maxMemSize = 1000 * maxSize;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold( maxMemSize );
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax( maxFileSize );
		
		@SuppressWarnings("unchecked")
		List<FileItem> fileItems = upload.parseRequest(request);
		
		fileItem = fileItems.iterator().next();
		if (fileItem == null)
			throw new FileUploadException("No hay archivos");
		
		file = new File(folder + fileItem.getName());
		fileItem.write(file);
		
		return new force.eai.mx.security.SecurityUtils().decode64(fileItem.getFieldName());
	}
	
	public Documento pushToDrive(Force force, Cliente cliente, String environment) throws Exception {
		// Google Drive Upload
		String driveId = "";
		String id = new force.eai.mx.security.SecurityUtils().decode64(fileItem.getFieldName());
		String suffix = environment.substring(0, 1);
		
		String titleIdCliente = force.getIdCliente() + suffix;
		String titleForceId = cliente.getForceId() + suffix;
		
		String titleFileName =  titleForceId + "_" + id +  "-" + cliente.getDocumento(id).getDriveBase();
		String titleExtension = getExtension(fileItem.getName());
		
		DriveSender drive = new DriveSender();
		drive.getDriveService();

		String driveEnvironmente = search(drive, environment);
		String driveIdCliente = search(drive, titleIdCliente);
		String driveForceId = search(drive, titleForceId);
		
		if ( driveIdCliente.equals("") )
			driveIdCliente = createFolder(drive, titleIdCliente, driveEnvironmente);
		
		if ( driveForceId.equals("") )
			driveForceId = createFolder(drive, titleForceId, driveIdCliente);
				
		// An "Ass Insurance" to not let Google to mess up with my files
		try {
			driveId = drive.searchObject(titleFileName, "contains").getId();
		} catch (Exception exception) { 
			// Nothing
		}
        
        // Upload Document at Correct Folder
		Documento gDrive = new Documento();
		gDrive.setDriveId(driveId);
		gDrive.setDriveTitle(titleFileName + titleExtension);
		gDrive.setDriveDescription(cliente.getDocumento(id).getNombreDocumento());
		gDrive.setNombreArchivo(file.getAbsolutePath());
		gDrive.setDriveParentId(driveForceId);
		gDrive.setDriveSize(fileItem.getSize() + "");
        driveId = drive.upsertFile(gDrive);

        // Values need to change when DB upsert
		gDrive.setIdDocumento(id);
		gDrive.setDriveId(driveId);
		gDrive.setForceId(cliente.getForceId());
		gDrive.setNombreArchivo(titleFileName + titleExtension);
		gDrive.setDriveBase(cliente.getDocumento(id).getDriveBase());
		gDrive.setNombreDocumento(cliente.getDocumento(id).getNombreDocumento());
		gDrive.setIdUsuarioModificacion(force.getUsuario("0").getIdPersona());

        return gDrive;
	}
	
	public void delete() throws Exception {
		file.delete();
	}
		
	private String search(DriveSender drive, String value) {
		try {
			return drive.searchObject(value, "=").getId();
		} catch(Exception e) {
			return "";
		}
	}
	
	private String createFolder(DriveSender drive, String name, String parent) {
		try {
			Documento folder = new Documento();
			folder.setDriveTitle(name);
			folder.setDriveDescription(name);
			folder.setDriveParentId(parent);
			return drive.upsertFolder(folder);			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private String getExtension(String value) {
		try {
			return "." + value.split("\\.")[1];					
		} catch (Exception e) {
			return ".pdf";
		}
	}
}
