package force.eai.mx.dao;

import force.eai.mx.util.Persona;

public class EaiAdmPreferencias {
	private Persona usuario = new Persona();

	public String insert() {
		String query = "Insert into EAI_ADM_PREFERENCIAS " +
				"( " +
				" ID_USUARIO " +
				", FILTROS " +
				", WIDGETS " +
				", SELECCIONES " +
				", FECHA_CREACION " +
				") values ( " +
				"  '" + usuario.getIdPersona() + "'" +
				", '" + usuario.getFiltros() + "' " +
				", '" + usuario.getWidgets() + "'" +
				", '" + usuario.getSelecciones() + "'" +
				", CURRENT_TIMESTAMP " +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_ADM_PREFERENCIAS " + 
				"SET " +
				" FILTROS = '" + usuario.getFiltros() + "'" +
				" ,WIDGETS = '" + usuario.getWidgets() + "'" +
				" ,SELECCIONES = '" + usuario.getSelecciones() + "'" +
				", FECHA_MODIFICACION = CURRENT_TIMESTAMP " +
				" WHERE ID_USUARIO = " + usuario.getIdPersona();

		//System.out.println(query);
		return query;
	}
		
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
}
