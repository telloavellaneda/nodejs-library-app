package force.eai.mx.dao;

import force.eai.mx.util.Empleo;

public class EaiEntEmpleos {
	private Empleo empleo = new Empleo();

	public String insert() {		
		String query = "Insert into EAI_ENT_EMPLEOS" + 
				"(" +
				" ID_PERSONA" +
				", NOMBRE_EMPRESA" +
				", SECTOR" +
				", GIRO" +
				", PUESTO" +
				", PROFESION" +
				", TIPO_EMPLEO" +
				", TIPO_CONTRATO" +
				", ANT_ANIOS" +
				", ANT_MESES" +
				", INGRESOS_BRUTO" +
				", INGRESOS_NETO" +
				", OTROS_INGRESOS" +
				", FUENTE_INGRESOS" +
				", FECHA_CREACION" +
				", STATUS" +
				") values (" +
				"  " + empleo.getIdPersona() +
				", '" + empleo.getNombreEmpresa() + "'" +
				", '" + empleo.getSector() + "'" +
				", '" + empleo.getGiro() + "'" +
				", '" + empleo.getPuesto() + "'" +
				", '" + empleo.getProfesion() + "'" +
				", '" + empleo.getTipoEmpleo() + "'" +
				", '" + empleo.getTipoContrato() + "'" +
				", '" + empleo.getAntiguedadAnios() + "'" +
				", '" + empleo.getAntiguedadMeses() + "'" +
				", '" + empleo.getIngresosBruto() + "'" +
				", '" + empleo.getIngresosNeto() + "'" +
				", '" + empleo.getOtrosIngresos() + "'" +
				", '" + empleo.getFuenteIngresos() + "'" +
				", CURRENT_TIMESTAMP " +
				", '" + empleo.getStatus() + "'" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_ENT_EMPLEOS" + 
				" SET" +
				" NOMBRE_EMPRESA = '" + empleo.getNombreEmpresa() + "'" +
				" ,SECTOR = '" + empleo.getSector() + "'" +
				" ,GIRO = '" + empleo.getGiro() + "'" +
				" ,PUESTO = '" + empleo.getPuesto() + "'" +
				" ,PROFESION = '" + empleo.getProfesion() + "'" +
				" ,TIPO_EMPLEO = '" + empleo.getTipoEmpleo() + "'" +
				" ,TIPO_CONTRATO = '" + empleo.getTipoContrato() + "'" +
				" ,ANT_ANIOS = '" + empleo.getAntiguedadAnios() + "'" +
				" ,ANT_MESES = '" + empleo.getAntiguedadMeses() + "'" +
				" ,INGRESOS_BRUTO = '" + empleo.getIngresosBruto() + "'" +
				" ,INGRESOS_NETO = '" + empleo.getIngresosNeto() + "'" +
				" ,OTROS_INGRESOS = '" + empleo.getOtrosIngresos() + "'" +
				" ,FUENTE_INGRESOS = '" + empleo.getFuenteIngresos() + "'" +
				" ,FECHA_MODIFICACION = CURRENT_TIMESTAMP " +
				" ,STATUS = '" + empleo.getStatus() + "' " +
				"WHERE ID_PERSONA = " + empleo.getIdPersona();
		
		//System.out.println(query);
		return query;
	}

	public void setEmpleo(Empleo empleo) {
		this.empleo = empleo;
	}		
}