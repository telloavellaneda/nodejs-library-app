package force.eai.mx.dao;

import force.eai.mx.util.Seguimiento;

public class EaiRelSeguimiento {
	private Seguimiento seguimiento;

	public EaiRelSeguimiento() {
		// Nothing
	}
	
	public EaiRelSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}
	
	public String insert() {
		String query = "Insert into EAI_REL_SEGUIMIENTO VALUES (\n"
				+ seguimiento.getForceId() + "\n"
				+ ", " + seguimiento.getId() + "\n"
				+ ", '" + seguimiento.getUsuario().getIdPersona() + "'\n"
				+ ", TO_CLOB('" + seguimiento.getMensaje() + "')\n"
				+ ", TO_TIMESTAMP('" + seguimiento.getFechaCreacion() + "','DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ", 1\n"
				+ ")";
		
		//System.out.println(query);
		return query;
	}
	
	public String getNextValue(String forceId) { 
		return "SELECT NVL(MAX(ID),-1)+1 FROM EAI_REL_SEGUIMIENTO WHERE FORCE_ID = " + forceId;
	}	
}
