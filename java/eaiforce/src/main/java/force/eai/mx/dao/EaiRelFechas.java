package force.eai.mx.dao;

import java.util.LinkedHashMap;

import force.eai.mx.util.Cliente;

public class EaiRelFechas {
	private Cliente cliente;
	//private force.eai.mx.tools.FormatValues fv;
	private LinkedHashMap<String,String> fields;

	public EaiRelFechas(Cliente cliente) {
		this.cliente = cliente;
		//this.fv = new force.eai.mx.tools.FormatValues();
		this.fields = new LinkedHashMap<String,String>();
		this.parse();
	}
	
	public String insert() {
		String query = "Insert into EAI_REL_FECHAS VALUES (\n"
				+ fields.get("FORCE_ID") + "\n"
				+ ",TO_DATE('" + fields.get("FECHA_EXPEDIENTE") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FASE_1") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FASE_2") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FASE_3") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FASE_4") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FASE_5") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_AUTORIZACION") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_FIRMA") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_VENC_LINEA") + "', 'DD/MM/YYYY')\n"
				+ ",TO_DATE('" + fields.get("FECHA_VENC_CERTIFICADO") + "', 'DD/MM/YYYY')\n"
				+ ",TO_TIMESTAMP('" + fields.get("FECHA_FASE") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ",TO_TIMESTAMP('" + fields.get("FECHA_STATUS") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ",TO_TIMESTAMP('" + fields.get("FECHA_CREACION") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ",TO_TIMESTAMP('" + fields.get("FECHA_MODIFICACION") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n"
				+ ")";

		//System.out.println("\n" + query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_REL_FECHAS\n" + 
				"SET\n" +
				"FECHA_EXPEDIENTE = TO_DATE('" + fields.get("FECHA_EXPEDIENTE") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE_1 = TO_DATE('" + fields.get("FECHA_FASE_1") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE_2 = TO_DATE('" + fields.get("FECHA_FASE_2") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE_3 = TO_DATE('" + fields.get("FECHA_FASE_3") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE_4 = TO_DATE('" + fields.get("FECHA_FASE_4") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE_5 = TO_DATE('" + fields.get("FECHA_FASE_5") + "', 'DD/MM/YYYY')\n" +
				",FECHA_AUTORIZACION = TO_DATE('" + fields.get("FECHA_AUTORIZACION") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FIRMA = TO_DATE('" + fields.get("FECHA_FIRMA") + "', 'DD/MM/YYYY')\n" +
				",FECHA_VENC_LINEA = TO_DATE('" + fields.get("FECHA_VENC_LINEA") + "', 'DD/MM/YYYY')\n" +
				",FECHA_VENC_CERTIFICADO = TO_DATE('" + fields.get("FECHA_VENC_CERTIFICADO") + "', 'DD/MM/YYYY')\n" +
				",FECHA_FASE = TO_TIMESTAMP('" + fields.get("FECHA_FASE") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				",FECHA_STATUS = TO_TIMESTAMP('" + fields.get("FECHA_STATUS") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				",FECHA_MODIFICACION = TO_TIMESTAMP('" + fields.get("FECHA_MODIFICACION") + "', 'DD/MM/YYYY HH24:MI:SSXFF')\n" +
				"WHERE FORCE_ID = " + fields.get("FORCE_ID");

		//System.out.println("\n" + query);
		return query;
	}
	
	public void parse() {
		fields.put("FORCE_ID", cliente.getForceId());
		fields.put("FECHA_EXPEDIENTE", cliente.getFechas("expediente"));
		fields.put("FECHA_FASE_1", cliente.getFechas("fase1"));
		fields.put("FECHA_FASE_2", cliente.getFechas("fase2"));
		fields.put("FECHA_FASE_3", cliente.getFechas("fase3"));
		fields.put("FECHA_FASE_4", cliente.getFechas("fase4"));
		fields.put("FECHA_FASE_5", cliente.getFechas("fase5"));
		fields.put("FECHA_AUTORIZACION", cliente.getFechas("autorizacion"));
		fields.put("FECHA_FIRMA", cliente.getFechas("firma"));
		fields.put("FECHA_VENC_LINEA", cliente.getFechas("linea"));
		fields.put("FECHA_VENC_CERTIFICADO", cliente.getFechas("certificado"));
		fields.put("FECHA_FASE", cliente.getFechas("fase"));
		fields.put("FECHA_STATUS", cliente.getFechas("status"));
		fields.put("FECHA_CREACION", cliente.getFechas("creacion"));
		fields.put("FECHA_MODIFICACION", cliente.getFechas("modificacion"));
	}	
}