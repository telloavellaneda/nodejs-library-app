package force.eai.mx.orchestration;

import force.eai.mx.util.Email;
import force.eai.mx.util.Force;
import force.eai.mx.util.Cliente;
import force.eai.mx.util.Seguimiento;
import force.eai.mx.google.GmailSender;
import force.eai.mx.tools.FormatValues;

public class NotifyUser {

	private Email email;
	private Force force;
	private Cliente cliente;
	private FormatValues fv = new FormatValues();
	
	public NotifyUser() {
		email = new Email();
	}
	
	public String send(String id) throws Exception {
		GmailSender gmailSender = new GmailSender();	
		email.setFromLabel( fv.titleCase(force.getUsuario("0").getNombre() + " " + force.getUsuario("0").getApellidoPaterno()) );
		email.setSubject("EAI Force (Notificación Automática): ["+ cliente.getForceId() +"] " + fv.titleCase(cliente.getPersona().getNombreCompleto()));
		email.setBody( createMessage(cliente.getSeguimiento(id)) );	
		
		return gmailSender.sendEmail(GmailSender.getGmailService(), this.email);
	}
	
	private String createMessage(Seguimiento seguimiento) {
		String html = "";
		
		html += "<strong>Usuario:</strong> " + fv.titleCase(seguimiento.getUsuario().getNombre() + " " + seguimiento.getUsuario().getApellidoPaterno()) + ".<br>";
		html += "<strong>Fecha:</strong> " + fv.formatTimestamp(seguimiento.getFechaCreacion()) + ".<br>";
		html += "<strong>Mensaje:</strong>" + "<br>";
		html += "<br>";
		html += fv.prettyFormat(seguimiento.getMensaje());

		return html;
	}
	
	public Email getEmail() {
		return email;
	}
	public void setForce(Force force) {
		this.force = force;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}