package force.eai.mx.dao;

import force.eai.mx.util.Telefono;

public class EaiEntTelefonos {
	private Telefono telefono = new Telefono();

	public String insert() {
		String query = "Insert into EAI_ENT_TELEFONOS " +
				"( " +
				" ID_PERSONA " +
				", TIPO " +
				", CODIGO " +
				", LADA " +
				", NUMERO " +
				", STATUS " +
				") values ( " +
				telefono.getIdPersona() +
				", " + "'" + telefono.getTipo() + "'" +
				", " + "'" + telefono.getCodigo() + "'" +
				", " + "'" + telefono.getLada() + "'" +
				", " + "'" + telefono.getNumero() + "'" +
				", " + "'" + telefono.getStatus() + "'" +
				")";

		//System.out.println(query);
		return query;
	}

	public String update() {
		String query = "Update EAI_ENT_TELEFONOS " +
				"SET" +
				" LADA = " + "'" + telefono.getLada() + "'" +
				", NUMERO =" + "'" + telefono.getNumero() + "'" +
				", STATUS =" + "'" + telefono.getStatus() + "'" +
				" WHERE ID_PERSONA = " + telefono.getIdPersona() +
				" AND TIPO = '" + telefono.getTipo() + "'";
		
		//System.out.println(query);
		return query;
	}

	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}
}
