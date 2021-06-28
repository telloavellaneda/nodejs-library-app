package force.eai.mx.dao;

import force.eai.mx.util.Cliente;

public class EaiRelClientes {
	private Cliente cliente;

	public EaiRelClientes() {
		// Nothing
	}

	public EaiRelClientes(Cliente cliente) {
		this.cliente = cliente;
	}

	public String insert() {
		String query = "Insert into EAI_REL_CLIENTES\n" +
				"(\n" +
				" FORCE_ID\n" +
				",ID_USUARIO\n" +
				",ID_USUARIO_CREACION\n" +
				",ID_USUARIO_RESPONSABLE\n" +
				",TIPO\n" +
				",ID_PERSONA\n" +
				",ID_CONYUGE\n" +
				",ID_APODERADO\n" +
				",ID_COACREDITADO_1\n" +
				",ID_COACREDITADO_2\n" +
				",ID_REFERENCIA_1\n" +
				",ID_REFERENCIA_2\n" +
				",ID_REFERENCIA_3\n" +
				",ID_FASE\n" +
				",ID_STATUS\n" +
				",ID_ANTERIOR\n" +
				",SECCION\n" +
				",FECHA_CREACION\n" +
				") values (\n" +
				cliente.getForceId() + "\n" + 
				",'" + cliente.getUsuario().getIdPersona() + "'\n" +
				",'" + cliente.getUsuarioCreacion().getIdPersona() + "'\n" +
				",'" + cliente.getUsuarioResponsable().getIdPersona() + "'\n" +
				",'" + cliente.getTipo() + "'\n" +
				",'" + cliente.getPersona().getIdPersona() + "'\n" +
				",'" + cliente.getConyuge().getIdPersona() + "'\n" +
				",'" + cliente.getApoderado().getIdPersona() + "'\n" +
				",'" + cliente.getCoacreditado("0").getIdPersona() + "'\n" +
				",'" + cliente.getCoacreditado("1").getIdPersona() + "'\n" +
				",'" + cliente.getReferencia("0").getIdPersona() + "'\n" +
				",'" + cliente.getReferencia("1").getIdPersona() + "'\n" +
				",'" + cliente.getReferencia("2").getIdPersona() + "'\n" +
				",'" + cliente.getIdFase() + "'\n" +
				",'" + cliente.getIdStatus() + "'\n" +
				",'" + cliente.getPreviousId() + "'\n" +
				",'" + cliente.getSeccion() + "'\n" +
				",TO_TIMESTAMP('" + cliente.getFechas("creacion") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_REL_CLIENTES\n" + 
				"SET\n" +
				" ID_USUARIO = '" + cliente.getUsuario().getIdPersona() + "'\n" +				
				", ID_USUARIO_RESPONSABLE = '" + cliente.getUsuarioResponsable().getIdPersona() + "'\n" +				
				", ID_USUARIO_MODIFICACION = '" + cliente.getUsuarioModificacion().getIdPersona() + "'\n" +				
				", TIPO = " + cliente.getTipo() + "\n" +
				", ID_PERSONA = '" + cliente.getPersona().getIdPersona() + "'\n" +
				", ID_CONYUGE = '" + cliente.getConyuge().getIdPersona() + "'\n" +
				", ID_APODERADO = '" + cliente.getApoderado().getIdPersona() + "'\n" +
				", ID_COACREDITADO_1 = '" + cliente.getCoacreditado("0").getIdPersona() + "'\n" +
				", ID_COACREDITADO_2 = '" + cliente.getCoacreditado("1").getIdPersona() + "'\n" +
				", ID_ANTERIOR = '" + cliente.getPreviousId() + "'\n" +	
				", SECCION = '" + cliente.getSeccion() + "'\n" +	
				", FECHA_MODIFICACION = TO_TIMESTAMP('" + cliente.getFechas("modificacion") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				" WHERE FORCE_ID = " + cliente.getForceId();

		//System.out.println(query);
		return query;
	}

	public String updateStatus() {
		String query = "Update EAI_REL_CLIENTES\n" + 
				"SET\n" +
				" ID_USUARIO_MODIFICACION = '" + cliente.getUsuarioModificacion().getIdPersona() + "'\n" +				
				", ID_FASE = '" + cliente.getIdFase() + "'\n" +
				", ID_STATUS = '" + cliente.getIdStatus() + "'\n" +
				", FECHA_MODIFICACION = TO_TIMESTAMP('" + cliente.getFechas("modificacion") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				" WHERE FORCE_ID = " + cliente.getForceId();

		//System.out.println(query);
		return query;
	}

	public String updateDrive() {
		String query = "Update EAI_REL_CLIENTES\n" + 
				"SET\n" +
				" ID_USUARIO_MODIFICACION = '" + cliente.getUsuarioModificacion().getIdPersona() + "'\n" +				
				", DRIVE_ID = '" + cliente.getDriveId() + "'\n" +
				", FECHA_MODIFICACION = TO_TIMESTAMP('" + cliente.getFechas("modificacion") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				" WHERE FORCE_ID = " + cliente.getForceId();

		//System.out.println(query);
		return query;
	}

	public String updateTimestamp() {
		String query = "Update EAI_REL_CLIENTES\n" + 
				"SET\n" +
				" ID_USUARIO_MODIFICACION = '" + cliente.getUsuarioModificacion().getIdPersona() + "'\n" +				
				", FECHA_MODIFICACION = TO_TIMESTAMP('" + cliente.getFechas("modificacion") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				" WHERE FORCE_ID = " + cliente.getForceId();

		//System.out.println(query);
		return query;
	}
	
	public String getNextValue() { 
		return "SELECT EAI_REL_CLIENTES_SEQ.NEXTVAL FROM SYS.DUAL";
	}
		
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}