package force.eai.mx.dao;

public class QryLoadCustomer {

	public String getQueryCliente() {
		return "SELECT A.*\n" +
		"FROM EAI_REL_CLIENTES A\n" +
		"WHERE FORCE_ID = ?";
	}

	public String getQueryFechas() {
		return "SELECT *\n" +
		"FROM EAI_REL_FECHAS\n" +
		"WHERE FORCE_ID = ?";
	}

	public String getQueryPersona() { 
		return "SELECT B.*\n" +
			"FROM EAI_ENT_PERSONAS B\n" +
			"WHERE B.ID_PERSONA = ?";
	}

	public String getQueryDirecciones() { 
		return "SELECT B.*\n" +
			"FROM EAI_ENT_DIRECCIONES B\n" +
			"WHERE B.ID_PERSONA = ?";
	}

	public String getQueryTelefonos() { 
		return "SELECT B.*\n" +
			"FROM EAI_ENT_TELEFONOS B\n" +
			"WHERE B.ID_PERSONA = ?";
	}

	public String getQueryEmpleos() { 
		return "SELECT A.*\n" +
			"FROM EAI_ENT_EMPLEOS A\n" +
			"WHERE ID_PERSONA = ?";
	}
	
	public String getQueryReferencias() { 
		return "SELECT A.*\n" +
			"FROM EAI_ENT_REFERENCIAS A\n" +
			"WHERE ID_PERSONA = ?";
	}

	public String getQueryCredito() { 
		return "SELECT A.*\n" +
			" ,B.NOMBRE BANCO\n" +
			"FROM EAI_REL_CREDITOS A\n" +
			", EAI_CAT_BANCOS B\n" +
			"WHERE FORCE_ID = ?\n" +
			" AND A.ID_BANCO = B.ID_BANCO";
	}
	
	public String getQueryDocumentacion() { 
		return "SELECT Y.*\n" +
			", Z.FORCE_ID\n" +
			", Z.NOMBRE_ARCHIVO NOM_ARCHIVO\n" +
			", Z.DRIVE_ID\n" +
			", Z.DRIVE_SIZE\n" +
			", Z.FECHA_ENTREGA\n" +
			", Z.FECHA_CREACION\n" +
			", Z.FECHA_MODIFICACION\n" +
			", NVL(Z.STATUS,'') STATUS_DOC\n" +
			"FROM\n" + 
			" (\n" +
			"SELECT A.*\n"
			+ "FROM EAI_CAT_DOCUMENTACION A\n"
			+ ", EAI_ADM_CLIENTES_DOCUMENTOS B\n"
			+ "WHERE A.STATUS = 0\n"
			+ "AND B.ID_CLIENTE = ?\n"
			+ "AND A.ID_DOCUMENTO = B.ID_DOCUMENTO\n" + 
			" ) Y,\n" +
			" (\n" +
			"SELECT *\n" +
			"FROM EAI_REL_DOCUMENTOS B\n" +
			"WHERE B.FORCE_ID = ?\n" + 
			" ) Z\n" +
			"WHERE Y.ID_DOCUMENTO = Z.ID_DOCUMENTO(+)\n" +
			"ORDER BY Y.ID_DOCUMENTO";
	}

	public String getQuerySeguimiento() { 
		return "SELECT A.*\n" +
			", C.NOMBRE\n" +
			", C.APELLIDO_PATERNO\n" +
			"FROM EAI_REL_SEGUIMIENTO A\n" +
			", EAI_ADM_USUARIOS C\n" +
			"WHERE A.FORCE_ID = ?\n" +
			" AND A.STATUS IN (0,1)\n" +
			" AND A.ID_USUARIO = C.ID_USUARIO\n" +
			"ORDER BY A.FECHA_CREACION" ;
	}

	public String getQueryUsuario() { 
		return "SELECT B.*\n" +
			" FROM EAI_ADM_USUARIOS B\n" +
			"WHERE B.ID_USUARIO = ?";
	}
	
	public String getQuerySeguimientoNew() { 
		return "SELECT COUNT(*)\n" + 
			"FROM EAI_REL_SEGUIMIENTO\n" +
			"WHERE FORCE_ID = ?";
	}
	
	public String getQueryStatus() { 
		return "SELECT A.*\n"
			+ ",B.DESCRIPCION FASE\n"
			+ ",C.DESCRIPCION STATUS\n"
			+ ",D.NOMBRE\n"
			+ ",D.APELLIDO_PATERNO\n"
			+ "FROM EAI_REL_STATUS A\n"
			+ ",EAI_CAT_FASES B\n"
			+ ",EAI_CAT_STATUS C\n"
			+ ",EAI_ADM_USUARIOS D\n"
			+ "WHERE A.FORCE_ID = ?\n"
			+ "AND A.ID_USUARIO = D.ID_USUARIO\n"
			+ "AND A.ID_FASE = B.ID_FASE\n"
			+ "AND A.ID_STATUS = C.ID_STATUS\n"
			+ "ORDER BY A.FECHA, A.ID_FASE";
	}
}