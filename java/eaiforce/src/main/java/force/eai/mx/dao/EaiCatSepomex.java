package force.eai.mx.dao;

import force.eai.mx.util.ZipCode;

public class EaiCatSepomex {
	private ZipCode zipCode = new ZipCode();

	public String insert() {
		String query = "Insert into EAI_CAT_SEPOMEX ( " +
				" CODIGO" +
				" , COLONIA" +
				" , TIPO_COLONIA" +
				" , DELEGACION" +
				" , CIUDAD" +
				" , ESTADO" +
				" , SELECTED_INDEX" +
				" , STATUS" +
				" ) VALUES ( " +
				" '" + zipCode.getCodigo() + "'" +
				", '" + zipCode.getColonia() + "'" +
				", '" + zipCode.getTipoColonia() + "'" +
				", '" + zipCode.getDelegacion() + "'" +
				", '" + zipCode.getCiudad() + "'" +
				", '" + zipCode.getEstado() + "'" +
				", '" + zipCode.getIndex() + "'" +
				", '" + zipCode.getStatus() + "'" +
				")";
		
		//System.out.println(query);
		return query;
	}
		
	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}
}
