package force.eai.mx.dao;

import force.eai.mx.util.Referencia;

public class EaiEntReferencias {
	private Referencia referencia;

	public EaiEntReferencias(Referencia referencia) {
		this.referencia = referencia;
	}
	
	public String insert() {		
		String query = "Insert into EAI_ENT_REFERENCIAS" + 
				"(" +
				" ID_PERSONA" +
				", TIPO" +
				", PARENTESCO" +
				" ,NOMBRE"  +
				" ,SEGUNDO_NOMBRE" +
				" ,APELLIDO_PATERNO"  +
				" ,APELLIDO_MATERNO" +
				", LADA " +
				", NUMERO " +
				", FECHA_CREACION" +
				", STATUS" +
				") values (" +
				"  " + referencia.getIdPersona() +
				", '" + referencia.getTipo() + "'" +
				", '" + referencia.getParentesco() + "'" +
				", '" + referencia.getNombre() + "'" +
				", '" + referencia.getSegundoNombre() + "'" +
				", '" + referencia.getApellidoPaterno() + "'" +
				", '" + referencia.getApellidoMaterno() + "'" +
				", '" + referencia.getLada() + "'" +
				", '" + referencia.getNumero() + "'" +
				", CURRENT_TIMESTAMP " +
				", '" + referencia.getStatus() + "'" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_ENT_REFERENCIAS" + 
				" SET" +
				" TIPO = '" + referencia.getTipo() + "'" +
				" ,PARENTESCO = '" + referencia.getParentesco() + "'" +
				" ,NOMBRE = '" + referencia.getNombre() + "'" +
				" ,SEGUNDO_NOMBRE = '" + referencia.getSegundoNombre() + "'" +
				" ,APELLIDO_PATERNO = '" + referencia.getApellidoPaterno() + "'" +
				" ,APELLIDO_MATERNO = '" + referencia.getApellidoMaterno() + "'" +
				" ,LADA = '" + referencia.getLada() + "'" +
				" ,NUMERO = '" + referencia.getNumero() + "'" +
				" ,FECHA_MODIFICACION = CURRENT_TIMESTAMP " +
				" ,STATUS = '" + referencia.getStatus() + "' " +
				"WHERE ID_PERSONA = " + referencia.getIdPersona();
		
		//System.out.println(query);
		return query;
	}
}