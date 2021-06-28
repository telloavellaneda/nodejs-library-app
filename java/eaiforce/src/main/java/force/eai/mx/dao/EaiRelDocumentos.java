package force.eai.mx.dao;

import force.eai.mx.util.Documento;

public class EaiRelDocumentos {
	private Documento documento;
	
	public EaiRelDocumentos(Documento documento) {
		this.documento = documento;
	}

	public String insert() {
		String query = "Insert into EAI_REL_DOCUMENTOS values (\n"
				+ documento.getForceId() + "\n"
				+ ", '" + documento.getIdDocumento() + "'\n"
				+ ", '" + documento.getNombreArchivo() + "'\n"
				+ ", '" + documento.getDriveId() + "'\n"
				+ ", '" + documento.getDriveSize() + "'\n"
				+ ", TO_TIMESTAMP('" + documento.getFechaEntrega() + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ", CURRENT_TIMESTAMP\n"
				+ ", ''\n"
				+ ", '" + documento.getIdUsuarioModificacion() + "'\n"
				+ ", '1'\n"
				+ ")";
		
		//System.out.println(query);
		return query;
	}

	public String update() {
		String query = "Update EAI_REL_DOCUMENTOS\n"
				+ "SET\n"
				+ "NOMBRE_ARCHIVO = '" + documento.getNombreArchivo() + "'\n"
				+ ", DRIVE_ID = '" + documento.getDriveId() + "'\n"
				+ ", DRIVE_SIZE = '" + documento.getDriveSize() + "'\n"
				+ ", FECHA_ENTREGA = TO_TIMESTAMP('" + documento.getFechaEntrega() + "','DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ", FECHA_MODIFICACION =  CURRENT_TIMESTAMP\n"
				+ ", ID_USUARIO_MODIFICACION = '" + documento.getIdUsuarioModificacion() + "'\n"
				+ ", STATUS = '" + documento.getStatus() + "'\n"
				+ "WHERE FORCE_ID = " + documento.getForceId() + "\n"
				+ "AND ID_DOCUMENTO = '" + documento.getIdDocumento() + "'";
		
		//System.out.println(query);
		return query;
	}
}