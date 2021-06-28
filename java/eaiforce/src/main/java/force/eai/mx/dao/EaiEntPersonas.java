package force.eai.mx.dao;

import force.eai.mx.util.Persona;

public class EaiEntPersonas {
	private Persona persona = new Persona();

	public String insert() {		
		String query = "Insert into EAI_ENT_PERSONAS " +
				"(" +
				"  ID_PERSONA " +
				" ,TIPO"  +
				" ,NOMBRE"  +
				" ,SEGUNDO_NOMBRE" +
				" ,APELLIDO_PATERNO"  +
				" ,APELLIDO_MATERNO" +
				" ,FECHA" + 
				" ,ANIOS" +
				" ,MESES" +
				" ,SEXO" +
				" ,PAIS_NACIMIENTO" + 
				" ,ESTADO_NACIMIENTO" + 
				" ,LUGAR_NACIMIENTO" +
				" ,NACIONALIDAD" +
				" ,RFC" +
				" ,HOMOCLAVE" +  
				" ,CURP" +
				" ,NO_IMSS" +  
				" ,NIVEL_ACADEMICO" +     
				" ,ESTADO_CIVIL" +
				" ,REGIMEN" +
				" ,IDENTIFICACION" + 
				" ,NUMERO_IDENTIFICACION" +
				" ,EMAIL" +
				" ,FECHA_CREACION" +
				" ,STATUS" +
				" ) values ( " +
				"" + persona.getIdPersona() +
				",'" + persona.getTipo() + "'" +
				", UPPER('" + persona.getNombre() + "') " +
				", UPPER('" + persona.getSegundoNombre() + "') " +
				", UPPER('" + persona.getApellidoPaterno() + "') " +
				", UPPER('" + persona.getApellidoMaterno() + "') " +
				", TO_DATE('" + persona.getFecha() + "','DD/MM/YYYY') " +
				", '" + persona.getAnios() + "' " +
				", '" + persona.getMeses() + "' " +
				", '" + persona.getSexo() + "' " +
				", '" + persona.getPaisNacimiento() + "' " +
				", '" + persona.getEstadoNacimiento() + "' " +
				", '" + persona.getLugarNacimiento() + "' " +
				", '" + persona.getNacionalidad() + "' " +
				", UPPER('" + persona.getRfc() + "') " +
				", UPPER('" + persona.getHomoclave() + "') " +
				", UPPER('" + persona.getCurp() + "') " +
				", '" + persona.getNoImss() + "' " +
				", '" + persona.getNivelAcademico() + "' " +
				", '" + persona.getEstadoCivil() + "' " +
				", '" + persona.getRegimen() + "' " +
				", '" + persona.getIdentificacion() + "' " +
				", '" + persona.getNumeroIdentificacion() + "' " +
				", '" + persona.getEmail() + "' " +
				", CURRENT_TIMESTAMP " +
				", '" + persona.getStatus() + "' " +
				")";
		
		//System.out.println(query);
		return query;
	}
		
	public String update() {
		String query = "Update EAI_ENT_PERSONAS " +
				"SET" +
				" TIPO = '" + persona.getTipo() + "'" +
				" ,NOMBRE = UPPER('" + persona.getNombre() + "') " +
				" ,SEGUNDO_NOMBRE = UPPER('" + persona.getSegundoNombre() + "') " +
				" ,APELLIDO_PATERNO = UPPER('" + persona.getApellidoPaterno() + "') " +
				" ,APELLIDO_MATERNO = UPPER('" + persona.getApellidoMaterno() + "') " +
				" ,FECHA = to_date('" + persona.getFecha() + "','DD/MM/YYYY')" +
				" ,ANIOS = '" + persona.getAnios() + "'" +
				" ,MESES = '" + persona.getMeses() + "'" +
				" ,SEXO = '" + persona.getSexo() + "'" +
				" ,PAIS_NACIMIENTO = '" + persona.getPaisNacimiento() + "' " +
				" ,ESTADO_NACIMIENTO = '" + persona.getEstadoNacimiento() + "' " +
				" ,LUGAR_NACIMIENTO = '" + persona.getLugarNacimiento() + "' " +
				" ,NACIONALIDAD = '" + persona.getNacionalidad() + "' " +
				" ,RFC = UPPER('" + persona.getRfc() + "') " +
				" ,HOMOCLAVE = UPPER('" + persona.getHomoclave() + "') " +
				" ,CURP = '" + persona.getCurp() + "' " +
				" ,NO_IMSS = '" + persona.getNoImss() + "' " +
				" ,NIVEL_ACADEMICO = '" + persona.getNivelAcademico() + "' " +  
				" ,ESTADO_CIVIL = '" + persona.getEstadoCivil() + "' " +
				" ,REGIMEN = '" + persona.getRegimen() + "' " +
				" ,IDENTIFICACION = '" + persona.getIdentificacion() + "' " +
				" ,NUMERO_IDENTIFICACION = '" + persona.getNumeroIdentificacion() + "' " +
				" ,EMAIL = '" + persona.getEmail() + "' " +
				" ,FECHA_MODIFICACION = CURRENT_TIMESTAMP " +
				" ,STATUS = '" + persona.getStatus() + "' " +				
				"WHERE ID_PERSONA = " + persona.getIdPersona();

		//System.out.println(query);
		return query;
	}
		
	public String getNextValue() { 
		return "SELECT EAI_ENT_PERSONAS_SEQ.NEXTVAL FROM SYS.DUAL";
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}