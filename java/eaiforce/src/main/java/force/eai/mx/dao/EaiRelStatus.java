package force.eai.mx.dao;

import force.eai.mx.util.Cliente;

public class EaiRelStatus {
	private Cliente cliente;
	private String idUsuario;

	public EaiRelStatus(Cliente cliente, String idUsuario) {
		this.cliente = cliente;
		this.idUsuario = idUsuario;
	}
	
	public String insert() {
		String query = "Insert into EAI_REL_STATUS values (\n"
				+ cliente.getForceId() + "\n" 
				+ ",'" + idUsuario + "'\n"
				+ ",'" + cliente.getIdFase() + "'\n"
				+ ",'" + cliente.getIdStatus() + "'\n"
				+ ", TO_TIMESTAMP('" + cliente.getFechas("status") + "','DD/MM/YYYY HH24:MI:SSXFF')\n" +
				")";

		//System.out.println(query);
		return query;
	}
}
