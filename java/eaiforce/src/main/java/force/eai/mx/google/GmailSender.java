package force.eai.mx.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import force.eai.mx.util.Email;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;

public class GmailSender {
	
    private static String APPLICATION_NAME 	= "eaiforce";
    private static String DATA_STORE_NAME 	= "google/gmail";
    private static String JSON_CREDENTIALS 	= "google/gmail/client_secret.json";
    
    private static java.io.File 		DATA_STORE_DIR;
    private static JsonFactory 			JSON_FACTORY;
    private static HttpTransport 		HTTP_TRANSPORT;
    private static List<String> 		SCOPES;
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    
    static {
        try {
        	DATA_STORE_NAME = GmailSender
        			.class
        			.getProtectionDomain()
        			.getCodeSource()
        			.getLocation()
        			.getPath() + "/" + DATA_STORE_NAME;
        	
        	DATA_STORE_DIR 		= new java.io.File(DATA_STORE_NAME);
            DATA_STORE_FACTORY 	= new FileDataStoreFactory(DATA_STORE_DIR);

            JSON_FACTORY 		= JacksonFactory.getDefaultInstance();
            HTTP_TRANSPORT 		= GoogleNetHttpTransport.newTrustedTransport();
            SCOPES 				= Arrays.asList(GmailScopes.GMAIL_SEND, GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_MODIFY);
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail
        		.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
        		.setApplicationName(APPLICATION_NAME)
        		.build();
    }
    
    public static Credential authorize() throws IOException {
		// Load client secret
		InputStream in = GmailSender.class.getClassLoader().getResourceAsStream(JSON_CREDENTIALS);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
				.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline")
				.build();
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    
	private Message createGmailMessage(MimeMessage email) throws MessagingException, IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		email.writeTo(bytes);
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}
        
	public String sendEmail(Gmail service, force.eai.mx.util.Email email) throws Exception {		
		Properties props = new Properties();
		Session mailSession = Session.getDefaultInstance(props,null);
		MimeMessage message = new MimeMessage(mailSession); 

		// To
		for ( int i = 0; i < email.getTo().length; i ++ ) {
			InternetAddress address = new InternetAddress(email.getTo()[i]);
			message.addRecipient( javax.mail.Message.RecipientType.TO, address );			
		}
		
		// CC
		if (email.getCc() != null) {
			for ( int i = 0; i < email.getCc().length; i ++ )
				if ( !email.getCc()[i].equals("") )
					message.addRecipient( javax.mail.Message.RecipientType.CC, new InternetAddress(email.getCc()[i]) );
		}

		// Reply To
		if (email.getReply() != null) {
			InternetAddress[] address = new InternetAddress[ email.getReply().length];
			for ( int i = 0; i < email.getReply().length; i ++ )
				address[i] = new InternetAddress(email.getReply()[i]);
			message.setReplyTo(address);
		}

		// From
		InternetAddress fromAddress= new InternetAddress(email.getFrom() ,email.getFromLabel());
		message.setFrom( fromAddress );

		message.setSubject( email.getSubject() );
		
		// Create the message part
         BodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setContent(email.getBody(), "text/html");

         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         if ( email.getFileNames() != null ) {
	         // Part two is attachment
	         DataSource source = null;
	         
	         for (int i = 0; i < email.getFileNames().length; i++ ) {
		         messageBodyPart = new MimeBodyPart();	         
		         source = new FileDataSource( email.getFileNamesDir() + email.getFileNames()[i] );
		         messageBodyPart.setDataHandler( new DataHandler(source) );
		         messageBodyPart.setFileName(email.getFileNames()[i]);
		         multipart.addBodyPart(messageBodyPart);
	         }
         }

		// Send the complete message parts
		message.setContent( multipart );

		Message gmailMessage = createGmailMessage(message);
		gmailMessage = service.users().messages().send(email.getFrom(), gmailMessage).execute();
		return gmailMessage.getId();
	}
	
	public static void main(String[] args) throws Exception {
		String title = "[EAI Force] Mensaje de Prueba";
		String html = "<p><strong>" + title + "</strong></p>";
		String fileNamesDir = "/Users/sateav/Downloads/";
		String[] fileNames = new String[] {
				"sateav.jpg"
				,"04_BasicWorkflow.pdf"
			};
		String[] to = new String[] {
			"sateav@gmail.com"
		};
		String[] cc = new String[] {
			"s_tello@hotmail.com"
		};
		String[] reply = new String[] {
			"stello@eai.mx"
		};
		String from = "notificaciones@eai.mx";
		String fromLabel = "EAI Force";
		
		Email email = new Email(); 
		email.setTo(to);
		email.setCc(cc);
		email.setReply(reply);
		email.setFrom(from);
		email.setFromLabel(fromLabel);
		email.setSubject(title);
		email.setBody(html);
		email.setFileNamesDir(fileNamesDir);
		email.setFileNames(fileNames);
		
		System.out.println(new GmailSender().sendEmail(GmailSender.getGmailService(), email));
    }
}