package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import force.eai.mx.util.Message;
import force.eai.mx.util.Section;
import force.eai.mx.database.Upsert;
import force.eai.mx.util.Catalogo;

public class LoadCatalogues {
	private Upsert upsert;
	private ResultSet rset;
	private Message message = new Message();

	private LinkedHashMap<String,Section>    formSections	= new LinkedHashMap<String,Section>();
	private LinkedHashMap<String,String[][]> formCatalogues	= new LinkedHashMap<String,String[][]>();
	private LinkedHashMap<String,LinkedHashMap<String,Catalogo>> formCataloguesHashMap = new LinkedHashMap<String,LinkedHashMap<String,Catalogo>>();

	public LoadCatalogues(Connection connection) throws SQLException {
		this.upsert = new Upsert(connection);
	}
	
	public void close() throws SQLException {
		this.rset.close();
		this.upsert.close();
	}
	
	public void loadCatalogues() throws SQLException {		
		// SECCIONES
		rset = upsert.query(querySections);
		while ( rset.next() ) {
			Section section = new Section();
			section.setId(rset.getString("ID"));
			section.setSection(rset.getString("SECTION"));
			section.setTitle(rset.getString("TITLE"));
			section.setDivId(rset.getString("DIV_ID"));
			section.setIncludeFile(rset.getString("INCLUDE_FILE"));
			section.setPreffix(rset.getString("PREFFIX"));
			section.setStatus(rset.getString("STATUS"));
			formSections.put( rset.getString("SECTION"), section );
		}

		// ADHERIDOS
		rset = upsert.query(getQueryAdheridos());
		while ( rset.next() ) {
			if ( formCataloguesHashMap.get("adheridos") == null )
				formCataloguesHashMap.put("adheridos", new LinkedHashMap<String,Catalogo>());
			
			formCataloguesHashMap.get("adheridos").put(rset.getString(1), new Catalogo());
			formCataloguesHashMap.get("adheridos").get(rset.getString(1)).setId(rset.getString(1));
			formCataloguesHashMap.get("adheridos").get(rset.getString(1)).setNombre(rset.getString(2));
			
			if ( rset.getString(3) != null ) {
				if ( formCataloguesHashMap.get("adheridos" + rset.getString(1)) == null )
					formCataloguesHashMap.put("adheridos" + rset.getString(1), new LinkedHashMap<String,Catalogo>());
				
				formCataloguesHashMap.get("adheridos" + rset.getString(1)).put(rset.getString(3), new Catalogo());
				formCataloguesHashMap.get("adheridos" + rset.getString(1)).get(rset.getString(3)).setId(rset.getString(3));
				formCataloguesHashMap.get("adheridos" + rset.getString(1)).get(rset.getString(3)).setNombre(rset.getString(4));
			}
		}
		
		// PAISES
		rset = upsert.query(queryPaises);
		while (rset.next()) {
			if ( formCataloguesHashMap.get("paises") == null )
				formCataloguesHashMap.put("paises", new LinkedHashMap<String,Catalogo>());
			
			formCataloguesHashMap.get("paises").put(rset.getString("ID"), new Catalogo());
			formCataloguesHashMap.get("paises").get(rset.getString("ID")).setId(rset.getString("ID"));
			formCataloguesHashMap.get("paises").get(rset.getString("ID")).setNombre(rset.getString("NOMBRE").toUpperCase());
			formCataloguesHashMap.get("paises").get(rset.getString("ID")).setStatus(rset.getString("STATUS"));
		}
		
		rset.close();
		
		formCatalogues.put("tipo", tipo);
		formCatalogues.put("sexo", sexo);
		formCatalogues.put("estados", estados);
		formCatalogues.put("decreto", decreto);
		formCatalogues.put("origenEnganche", origenEnganche);
		formCatalogues.put("plazo", plazo);
		formCatalogues.put("tipoComision", tipoComision);
		formCatalogues.put("coacreditado", coacreditado);
		formCatalogues.put("nacionalidad", nacionalidad);
		formCatalogues.put("nivelAcademico", nivelAcademico);
		formCatalogues.put("estadoCivil", estadoCivil);
		formCatalogues.put("regimen", regimen);
		formCatalogues.put("identificacion", identificacion);
		formCatalogues.put("tipoDomicilio", tipoDomicilio);
		formCatalogues.put("sectorEmpresa", sectorEmpresa);
		formCatalogues.put("giroEmpresa", giroEmpresa);
		formCatalogues.put("tipoEmpleo", tipoEmpleo);
		formCatalogues.put("tipoContrato", tipoContrato);
		formCatalogues.put("fuenteIngresos", fuenteIngresos);
		formCatalogues.put("parentesco", parentesco);
		
		message.setCode("0");
		message.setMessage("Carga Exitosa");
	}
	
	private String getQueryAdheridos() {
		String query = "";
		
		query += "SELECT\n"
				+ "A.ID_ADHERIDO\n"
				+ ", A.NOMBRE ADHERIDO\n"
				+ ", B.ID\n"
				+ ", B.NOMBRE OPCION\n"
				+ "FROM\n"
				+ "EAI_CAT_ADHERIDOS A\n"
				+ ", EAI_CAT_ADHERIDOS_OPCIONES B\n"
				+ "WHERE A.ID_ADHERIDO = B.ID_ADHERIDO(+)\n"
				+ "ORDER BY A.ID_ADHERIDO, B.NOMBRE";
		
		return query;
	}
		
	public Message getMessage() {
		return message;
	}
	public LinkedHashMap<String, Section> getFormSections() {
		return formSections;
	}	
	public LinkedHashMap<String, String[][]> getFormCatalogues() {
		return formCatalogues;
	}
	public LinkedHashMap<String, LinkedHashMap<String, Catalogo>> getFormCataloguesHashMap() {
		return formCataloguesHashMap;
	}
		
	final private String querySections = "SELECT * FROM EAI_CAT_SECCIONES WHERE STATUS = 1";
	
	final private String queryPaises = "SELECT ID, NOMBRE, STATUS " +
			"FROM EAI_CAT_PAISES " +
			"WHERE STATUS IN (0)";
	
	final private String[][] estados = new String[][] {
		{"AS", "AGUASCALIENTES"},
		{"BC", "BAJA CALIFORNIA"},
		{"BS", "BAJA CALIFORNIA SUR"},
		{"CC", "CAMPECHE"},
		{"CL", "COAHUILA"},
		{"CM", "COLIMA"},
		{"CS", "CHIAPAS"},
		{"CH", "CHIHUAHUA"},
		{"DF", "DISTRITO FEDERAL"},
		{"DG", "DURANGO"},
		{"GT", "GUANAJUATO"},
		{"GR", "GUERRERO"},
		{"HG", "HIDALGO"},
		{"JC", "JALISCO"},
		{"MC", "MEXICO"},
		{"MN", "MICHOACAN"},
		{"MS", "MORELOS"},
		{"NT", "NAYARIT"},
		{"NL", "NUEVO LEON"},
		{"OC", "OAXACA"},
		{"PL", "PUEBLA"},
		{"QT", "QUERETARO"},
		{"QR", "QUINTANA ROO"},
		{"SP", "SAN LUIS POTOSI"},
		{"SL", "SINALOA"},
		{"SR", "SONORA"},
		{"TC", "TABASCO"},
		{"TS", "TAMAULIPAS"},
		{"TL", "TLAXCALA"},
		{"VZ", "VERACRUZ"},
		{"YN", "YUCATAN"},
		{"ZS", "ZACATECAS"}
	};
		
	final private String[][] tipo = new String[][] {
		{"0", "Física"},
		{"1", "F. Actividad Empresarial"},
		{"2", "Moral"}
	};
	
	final private String[][] sexo = new String[][] {
		{"M", "Masculino"},
		{"F", "Femenino"}
	};

	final private String[][] decreto = new String[][] {
		{"APROBADO", "Aprobado"},
		{"RECHAZADO", "Rechazado"},
		{"CONDICIONADO", "Condicionado"}
	};
	
	final private String[][] origenEnganche = new String[][] {
		{"AHORRO", "Ahorro"},
		{"PRESTAMO", "Préstamo"},
		{"DONTATIVO", "Donativo"},
		{"VENTA AUTO", "Venta Automóvil"},
		{"VENTA CASA", "Venta Casa/Terreno"},
		{"OTRO", "Otro"}
	};
	
	final private String[][] plazo = new String[][] {
		{"5", "5 Años"},
		{"10", "10 Años"},
		{"15", "15 Años"},
		{"20", "20 Años"},
		{"OTRO", "Otro"},
	};

	final private String[][] tipoComision = new String[][] {
		{"CONTADO", "Contado"},
		{"FINANCIADA", "Financiada"},
		{"SIN COMISION", "Sin Comisión"}
	};
	
	final private String[][] coacreditado = new String[][] {
		{"", "No"},
		{"CONYUGE", "Cónyuge"},
		{"CONCUBINO", "Concubino(a)"},
		{"CONVIVIENTE", "Conviviente"},
		{"OTRO", "Otro"}
	};
	
	final private String[][] nacionalidad = new String[][] {
		{"MEXICANA", "Mexicana"},
		{"OTRO", "Extranjera"}
	};
	
	final private String[][] nivelAcademico = new String[][] {
		{"PRIMARIA", "Primaria"},
		{"SECUNDARIA", "Secundaria"},
		{"PREPARATORIA", "Preparatoria"},
		{"TECNICO", "Técnico"},
		{"LICENCIATURA", "Licenciatura"},
		{"POSGRADO", "Posgrado"},
		{"OTRO", "Otro"}
	};
	
	final private String[][] estadoCivil = new String[][] {
		{"SOLTERO", "Soltero(a)"},
		{"CASADO", "Casado(a)"},
		{"DIVORCIADO", "Divorciado(a)"},
		{"SEPARADO", "Separado(a)"},
		{"VIUDO", "Viudo(a)"},
		{"UNION LIBRE", "Unión Libre"},
		{"SOCIEDAD DE CONVIVENCIA", "Sociedad de Convivencia"}
	};
	
	final private String[][] regimen = new String[][] {
		{"SOCIEDAD", "Sociedad Conyugal"},
		{"SEPARACION", "Separación de Bienes"}
	};
	
	final private String[][] identificacion = new String[][] {
		{"IFE", "IFE"},
		{"PASAPORTE", "PASAPORTE"},
		{"CARTILLA", "CARTILLA MILITAR"},
		{"OTRO", "OTRO"}
	};
	
	final private String[][] tipoDomicilio = new String[][] {
		{"PROPIO", "PROPIO"},
		{"RENTADO", "RENTADO"},
		{"FAMILIAR", "DE UN FAMILIAR"},
		{"HIPOTECADO", "HIPOTECADO"},
		{"OTRO", "OTRO"}
	};
	
	final private String[][] sectorEmpresa = new String[][] {
		{"PRIVADO", "PRIVADO"},
		{"MUNICIPAL", "MUNICIPAL"},
		{"PUBLICO ESTATAL", "PUBLICO ESTATAL"},
		{"PUBLICO FEDERAL", "PUBLICO FEDERAL"},
		{"NO APLICA", "NO APLICA"},
		{"OTRO", "OTRO"}
	};
	
	final private String[][] giroEmpresa = new String[][] {
		{"SERVICIOS", "SERVICIOS"},
		{"INDUSTRIA", "INDUSTRIA"},
		{"CONSTRUCCION", "CONSTRUCCION"},
		{"AGRICULTURA", "AGRICULTURA/PESCA"},
		{"OTRO", "OTRO"}
	};
		
	final private String[][] tipoEmpleo = new String[][] {
		{"EMPLEADO", "EMPLEADO"},
		{"PROPIO", "NEGOCIO PROPIO"},
		{"OTRO", "OTRO"}
	};
	
	final private String[][] tipoContrato = new String[][] {
		{"TEMPORAL", "TEMPORAL"},
		{"INDEFINIDO", "INDEFINIDO"}
	};
	
	final private String[][] fuenteIngresos = new String[][] {
		{"ASALARIADO", "ASALARIADO"},
		{"HONORARIOS", "HONORARIOS"},
		{"ACTVIDAD EMPRESARIAL", "ACTIVIDAD EMPRESARIAL"},
		{"ARRENDAMIENTO", "ARRENDAMIENTO"},
		{"INVERSION", "CUENTAS DE INVERSION"},
		{"OTRO", "OTRO"}
	};

	final private String[][] parentesco = new String[][] {
		{"FAMILIAR", "Familiar"},
		{"AMIGO", "Amigo(a)"},
		{"VECINO", "Vecino(a)"},
		{"SOCIO", "Socio(a)"},
		{"COMPANERO", "Compañero(a) Laboral"},
		{"OTRO", "Otro"}
	};
}