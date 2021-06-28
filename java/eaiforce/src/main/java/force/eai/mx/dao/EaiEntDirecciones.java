package force.eai.mx.dao;

import force.eai.mx.util.Direccion;

public class EaiEntDirecciones {
	private Direccion direccion = new Direccion();

	public String insert() {
		String query = "Insert into EAI_ENT_DIRECCIONES " +
				"( " +
				" ID_PERSONA " +
				", TIPO " +
				", CALLE " +
				", NUMERO_EXT " +
				", NUMERO_INT " +
				", COLONIA " +
				", CODIGO_POSTAL " +
				", DELEGACION " +
				", CIUDAD " +
				", ESTADO " +
				", TIPO_DOMICILIO " +
				", MONTO_RENTA " +
				", ANT_ANIOS " +
				", ANT_MESES " +
				", STATUS " +
				") values ( " +
				direccion.getIdPersona() +
				", " + "'" + direccion.getTipo() + "'" +
				", " + "'" + direccion.getCalle() + "'" +
				", " + "'" + direccion.getNumeroExterior() + "'" +
				", " + "'" + direccion.getNumeroInterior() + "'" +
				", " + "'" + direccion.getColonia() + "'" +
				", " + "'" + direccion.getCodigoPostal() + "'" +
				", " + "'" + direccion.getDelegacion() + "'" +
				", " + "'" + direccion.getCiudad() + "'" +
				", " + "'" + direccion.getEstado() + "'" +
				", " + "'" + direccion.getTipoDomicilio() + "'" +
				", " + "'" + direccion.getMontoRenta() + "'" +
				", " + "'" + direccion.getAntiguedadAnios() + "'" +
				", " + "'" + direccion.getAntiguedadMeses() + "'" +
				", " + "'" + direccion.getStatus() + "'" +
				")";

		//System.out.println(query);
		return query;
	}

	public String update() {
		String query = "Update EAI_ENT_DIRECCIONES " +
				"SET" +
				"  CALLE ='" + direccion.getCalle() + "'" +
				", NUMERO_EXT ='" + direccion.getNumeroExterior() + "'" +
				", NUMERO_INT ='" + direccion.getNumeroInterior() + "'" +
				", COLONIA ='" + direccion.getColonia() + "'" +
				", CODIGO_POSTAL ='" + direccion.getCodigoPostal() + "'" +
				", DELEGACION ='" + direccion.getDelegacion() + "'" +
				", CIUDAD ='" + direccion.getCiudad() + "'" +
				", ESTADO ='" + direccion.getEstado() + "'" +
				", TIPO_DOMICILIO ='" + direccion.getTipoDomicilio() + "'" +
				", MONTO_RENTA ='" + direccion.getMontoRenta() + "'" +
				", ANT_ANIOS ='" + direccion.getAntiguedadAnios() + "'" +
				", ANT_MESES ='" + direccion.getAntiguedadMeses() + "'" +
				", STATUS ='" + direccion.getStatus() + "' " +
				"WHERE ID_PERSONA = " + direccion.getIdPersona() +
				" AND TIPO = '" + direccion.getTipo() + "'";
		
		//System.out.println(query);
		return query;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
}
