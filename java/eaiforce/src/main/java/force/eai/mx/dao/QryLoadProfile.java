package force.eai.mx.dao;

import java.text.MessageFormat;

import force.eai.mx.util.Force;
import force.eai.mx.util.Filter;
import force.eai.mx.tools.QryLoadTools;

public class QryLoadProfile {
	//private String RESOURCES = "resources/profile";
	private String RESOURCES = "profile";

	private Force force;
	private Filter filter;
	private QryLoadTools qlt;
		
	public QryLoadProfile(Force force) {
		this.force = force;
		this.qlt = new QryLoadTools(RESOURCES);
	}
	
	public String getUsuariosQuery(String field, String perfil) {
		Object[] args = { force.getIdCliente(), field, perfil };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("countUsuario.profile"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getListaUsuariosQuery() {
		Object[] args = { force.getIdCliente() };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("usuario.profile"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getFasesStatusQuery() {
		Object[] args = { force.getIdCliente() };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("status.profile"), args));
		//System.out.println(query);
		return query;
	}

	public String getYearsQuery(String field) {
		Object[] args = { force.getIdCliente(), field };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("years.profile"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getBancosQuery() {
		Object[] args = { force.getIdCliente() };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("bancos.profile"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getDestinosQuery() {
		Object[] args = { force.getIdCliente() };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("destinos.profile"), args));
		//System.out.println(query);
		return query;
	}

	public String getParametrosQuery() {
		Object[] args = { force.getIdCliente() };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("parametros.profile"), args));
		//System.out.println(query);
		return query;
	}

	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
}