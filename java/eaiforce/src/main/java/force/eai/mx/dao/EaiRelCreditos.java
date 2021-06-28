package force.eai.mx.dao;

import force.eai.mx.util.Credito;

public class EaiRelCreditos {
	private Credito credito = new Credito();

	public String insert() {		
		String query = "Insert into EAI_REL_CREDITOS" + 
				"(" +
				" FORCE_ID" +
				", ID_BANCO" +
				", ADHERIDO" +
				", ADHERIDO_OPCION" +
				", PRODUCTO" +
				", DESTINO" +
				", IMPORTE_SOLIC" +
				", IMPORTE_APROB" +
				", IMPORTE_FINAL" +
				", DECRETO" +
				", DECRETO_COMENTARIOS" +
				", COACREDITADO" +
				", VALOR_ESTIMADO" +
				", VALOR_AVALUO" +
				", ENGANCHE" +
				", ORIGEN_ENGANCHE" +
				", PLAZO" +
				", TASA" +
				", AFORO" +
				", TIPO_COMISION_APERTURA" +
				", COMISION_APERTURA" +
				", NOTARIA" +
				", ABOGADO" +
				", EJECUTIVO_BANCO" +
				", FECHA_CREACION" +
				", STATUS" +
				") values (" +
				"  "  + credito.getForceId() +  
				", '" + credito.getIdBanco() + "'" +
				", '" + credito.getAdherido() + "'" +
				", '" + credito.getAdheridoOpcion() + "'" +
				", '" + credito.getProducto() + "'" +
				", '" + credito.getDestino() + "'" +
				", '" + credito.getImporteSolicitado() + "'" +
				", '" + credito.getImporteAprobado() + "'" +
				", '" + credito.getImporteFinal() + "'" +
				", '" + credito.getDecreto() + "'" +
				", '" + credito.getDecretoComentarios() + "'" +
				", '" + credito.getCoacreditado() + "'" +
				", '" + credito.getValorEstimado() + "'" +
				", '" + credito.getValorAvaluo() + "'" +
				", '" + credito.getEnganche() + "'" +
				", '" + credito.getOrigenEnganche() + "'" +
				", '" + credito.getPlazo() + "'" +
				", '" + credito.getTasa() + "'" +
				", '" + credito.getAforo() + "'" +
				", '" + credito.getTipoComisionApertura() + "'" +
				", '" + credito.getComisionApertura() + "'" +
				", '" + credito.getNotaria() + "'" +
				", '" + credito.getAbogado() + "'" +
				", '" + credito.getEjecutivoBanco() + "'" +
				", CURRENT_TIMESTAMP" +
				", '" + credito.getStatus() + "'" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_REL_CREDITOS " + 
				"SET " +
				"  ID_BANCO = " + credito.getIdBanco() +
				" ,ADHERIDO = '" + credito.getAdherido() + "'" +
				" ,ADHERIDO_OPCION = '" + credito.getAdheridoOpcion() + "'" +
				" ,PRODUCTO = '" + credito.getProducto() + "'" +
				" ,DESTINO = '" + credito.getDestino() + "'" +
				" ,IMPORTE_SOLIC = '" + credito.getImporteSolicitado() + "'" +
				" ,IMPORTE_APROB = '" + credito.getImporteAprobado() + "'" +
				" ,IMPORTE_FINAL = '" + credito.getImporteFinal() + "'" +
				" ,DECRETO = '" + credito.getDecreto() + "'" +
				" ,DECRETO_COMENTARIOS = '" + credito.getDecretoComentarios() + "'" +
				" ,COACREDITADO = '" + credito.getCoacreditado() + "'" +
				" ,VALOR_ESTIMADO = '" + credito.getValorEstimado() + "'" +
				" ,VALOR_AVALUO = '" + credito.getValorAvaluo() + "'" +
				" ,ENGANCHE = '" + credito.getEnganche() + "'" +
				" ,ORIGEN_ENGANCHE = '" + credito.getOrigenEnganche() + "'" +
				" ,PLAZO = '" + credito.getPlazo() + "'" +
				" ,TASA = '" + credito.getTasa() + "'" +
				" ,AFORO = '" + credito.getAforo() + "'" +
				" ,TIPO_COMISION_APERTURA = '" + credito.getTipoComisionApertura() + "'" +
				" ,COMISION_APERTURA = '" + credito.getComisionApertura() + "'" +
				" ,NOTARIA = '" + credito.getNotaria() + "'" +
				" ,ABOGADO = '" + credito.getAbogado() + "'" +
				" ,EJECUTIVO_BANCO = '" + credito.getEjecutivoBanco() + "'" +
				" ,FECHA_MODIFICACION = CURRENT_TIMESTAMP " +
				" ,STATUS = '" + credito.getStatus() + "' " +
				"WHERE FORCE_ID = " + credito.getForceId();
		
		//System.out.println(query);
		return query;
	}
			
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
}