package force.eai.mx.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.util.Force;
import force.eai.mx.util.Message;
import force.eai.mx.util.Cliente;
import force.eai.mx.util.Documento;
import force.eai.mx.tools.BuildJson;
import force.eai.mx.database.Connect;
import force.eai.mx.google.DriveSender;
import force.eai.mx.orchestration.UploadFile;
import force.eai.mx.orchestration.UpsertCustomer;

/**
 * Servlet implementation class UploadFilesServlet
 */
@WebServlet("/UploadFilesServlet")
public class UploadFilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMP_DIRECTORY = "temp-directory";
       
    public UploadFilesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		if ( session.getAttribute("force") != null )
			execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if ( request.getContentType() != null && request.getContentType().indexOf("multipart/form-data") >= 0 )
				uploadFile(request, response);

			else if ( request.getParameter("action") != null && request.getParameter("action").equals("drive") ) 
				downloadFile(request, response);

			else if ( request.getParameter("action") != null && request.getParameter("action").equals("download") ) 
				download(request, response);

			else
				throw new Exception("Acción Inválida");
			
		} catch (Exception exception) {
			exception.printStackTrace();
			response.setCharacterEncoding("ISO-8859-1");
			response.getWriter().write( new BuildJson().write(new Message("-1", exception.toString())) );
		}
	}
	
	private void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String key = request.getParameter("key");
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		
		String filePath = "";
		if (request.getParameter("report") != null)
			filePath = key;
		else if ( cliente != null && cliente.getDocumento(key) != null )
			filePath = getServletContext().getInitParameter(TEMP_DIRECTORY) + cliente.getDocumento(key).getNombreArchivo();
		else 
			return;
		
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        String mimeType = getServletContext().getMimeType(filePath);
        // set to binary type if MIME mapping not found
        if (mimeType == null)        
            mimeType = "application/octet-stream";
        
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();         
        downloadFile.delete();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1)
			outStream.write(buffer, 0, bytesRead);
        
        inStream.close();
        outStream.close();
	}
	
	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Message message = new Message();
		
		DriveSender drive = new DriveSender();
		drive.getDriveService();
		
		String key = request.getParameter("key");
		Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
		Documento file = new Documento();
		file.setDriveId(cliente.getDocumento(key).getDriveId());
		file.setDirectorio(getServletContext().getInitParameter(TEMP_DIRECTORY));
		drive.downloadFile(file);
		
		message.setCode("0");
		message.setMessage("Archivo descargado");		

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write( new BuildJson().write(message) );
	}
	
	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		Force force = (Force)session.getAttribute("force");
		Cliente cliente = (Cliente)session.getAttribute("cliente");
		String environment = (String)session.getAttribute("env");

		UploadFile uploadFile = new UploadFile();
		
		// Upload File from Browser
		String id = uploadFile.upload(request, getServletContext().getInitParameter(TEMP_DIRECTORY), 2048);
				
		// Upload file to Google Drive
		Documento documento = uploadFile.pushToDrive(force, cliente, environment);

		// Deletes File from Temporary Upload at eaiapps.mx
		 uploadFile.delete();
		
        // Upsert Database record
        Connect connect = new Connect(environment);		
		UpsertCustomer upsertCustomer = new UpsertCustomer(connect.getConnection());
		
		documento.setFechaEntrega(upsertCustomer.getTimestamp());
		
		cliente.setDocumento(id, documento);
		cliente.setDriveId(documento.getDriveParentId());
		cliente.setUsuarioModificacion(force.getUsuario("0"));
		cliente.setFechas("modificacion", upsertCustomer.getTimestamp());
		
		upsertCustomer.setCliente(cliente);
		upsertCustomer.upsertDocumento(documento);
		upsertCustomer.upsertDriveId();
		upsertCustomer.close();
		connect.close();

		session.setAttribute("cliente", cliente);
				
		Message message = new Message("0", documento.getNombreDocumento() + "<br>cargado(a) exitosamente");
		// Message message = new Message("0",  "Archivo 1 <br>cargado(a) exitósamente");
		String queryString = new force.eai.mx.security.SecurityUtils()
				.encode64((
						"id=" + 
						id + 
						"&" + 
						"msg=" + 
						new BuildJson().write(message))
					.getBytes()
		);		
		
		response.sendRedirect("include/uploadForm.jsp?x=" + queryString );
	}
}