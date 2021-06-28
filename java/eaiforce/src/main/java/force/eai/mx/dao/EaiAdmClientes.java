package force.eai.mx.dao;

import force.eai.mx.util.Catalogo;
import force.eai.mx.util.Force;

public class EaiAdmClientes {
	private Force force;
	private Catalogo catalogo;

	public String insert() {
		String query = "Insert into EAI_ADM_CLIENTES\n" +
				"(\n" +
				"ID_CLIENTE\n" +
				",TIPO\n" +
				",NOMBRE\n" +
				",SEGUNDO_NOMBRE\n" +
				",APELLIDO_PATERNO\n" +
				",APELLIDO_MATERNO\n" +
				",FECHA\n" + 
				",ANIOS\n" +
				",MESES\n" +
				",SEXO\n" +
				",RFC\n" +
				",HOMOCLAVE\n" +  
				",BANCO\n" +
				",CUENTA\n" +  
				",CLABE\n" +     
				",TARJETA_CREDITO\n" +
				",FECHA_EXPIRACION\n" +
				",CODIGO_SEGURIDAD\n" + 
				",SITIO_WEB\n" + 
				",DOMINIO\n" + 
				",EMAIL\n" +
				",FECHA_CREACION\n" +
				",STATUS\n" +
				") values (\n" +
				"  "  + force.getIdCliente() + 
				", '" + force.getPersona().getTipo() + "'\n" +
				", '" + force.getPersona().getNombre() + "'\n" +
				", '" + force.getPersona().getSegundoNombre() + "'\n" +
				", '" + force.getPersona().getApellidoPaterno() + "'\n" +
				", '" + force.getPersona().getApellidoMaterno() + "'\n" +
				", TO_DATE('" + force.getPersona().getFecha() + "','DD/MM/YYYY')\n" +
				", '" + force.getPersona().getAnios() + "'\n" +
				", '" + force.getPersona().getMeses() + "'\n" +
				", '" + force.getPersona().getSexo() + "'\n" +
				", '" + force.getPersona().getRfc() + "'\n" +
				", '" + force.getPersona().getHomoclave() + "'\n" +
				", '" + force.getPerfil().getBanco() + "'\n" +
				", '" + force.getPerfil().getCuenta() + "'\n" +
				", '" + force.getPerfil().getClabe() + "'\n" +
				", '" + force.getPerfil().getTarjetaCredito() + "'\n" +
				", '" + force.getPerfil().getFechaExpiracion() + "'\n" +
				", '" + force.getPerfil().getCodigoSeguridad() + "'\n" +
				", '" + force.getPerfil().getSitioWeb() + "'\n" +
				", '" + force.getPerfil().getDominio() + "'\n" +
				", '" + force.getPersona().getEmail() + "'\n" +
				", CURRENT_TIMESTAMP\n" +
				", '" + force.getPersona().getStatus() + "'\n" +
				")";

		//System.out.println("\n" + query);
		return query;
	}
	
	public String update() {
		String query = "Update EAI_ADM_CLIENTES\n" + 
				"SET\n" +
				"TIPO = '" + force.getPersona().getTipo() + "'\n" +
				",NOMBRE = '" + force.getPersona().getNombre() + "'\n" +
				",SEGUNDO_NOMBRE = '" + force.getPersona().getSegundoNombre() + "'\n" +
				",APELLIDO_PATERNO = '" + force.getPersona().getApellidoPaterno() + "'\n" +
				",APELLIDO_MATERNO = '" + force.getPersona().getApellidoMaterno() + "'\n" +
				",FECHA = TO_DATE('" + force.getPersona().getFecha() + "','DD/MM/YYYY')\n" +
				",ANIOS = '" + force.getPersona().getAnios() + "'\n" +
				",MESES = '" + force.getPersona().getMeses() + "'\n" +
				",SEXO = '" + force.getPersona().getSexo() + "'\n" +
				",RFC = '" + force.getPersona().getRfc() + "'\n" +
				",HOMOCLAVE = '" + force.getPersona().getHomoclave() + "'\n" +
				",BANCO = '" + force.getPerfil().getBanco() + "'\n" +
				",CUENTA = '" + force.getPerfil().getCuenta() + "'\n" +
				",CLABE = '" + force.getPerfil().getClabe() + "'\n" +
				",TARJETA_CREDITO = '" + force.getPerfil().getTarjetaCredito() + "'\n" +
				",FECHA_EXPIRACION = '" + force.getPerfil().getFechaExpiracion() + "'\n" +
				",CODIGO_SEGURIDAD = '" + force.getPerfil().getCodigoSeguridad() + "'\n" +
				",SITIO_WEB = '" + force.getPerfil().getSitioWeb() + "'\n" +
				",DOMINIO = '" + force.getPerfil().getDominio() + "'\n" +
				",EMAIL = '" + force.getPersona().getEmail() + "'\n" +
				",FECHA_MODIFICACION = CURRENT_TIMESTAMP\n" +
				",STATUS = '" + force.getPersona().getStatus() + "'\n" +
				"WHERE ID_CLIENTE = " + force.getIdCliente();

		//System.out.println("\n" + query);
		return query;
	}
	
	public String insertStatus() {
		String query = "Insert into EAI_ADM_CLIENTES_STATUS\n" +
				"(\n" +
				" ID_CLIENTE\n" +
				", ID_FASE\n" +
				", ID_STATUS\n" +
				") values (\n" +
				" '"  + force.getIdCliente() + "'\n" + 
				", '"  + catalogo.getCampo("ID_FASE") + "'\n" + 
				", '"  + catalogo.getCampo("ID_STATUS") + "'\n" + 
				")";

		//System.out.println(query);
		return query;
	}

	public String insertBancosProductos() {
		String query = "Insert into EAI_ADM_CLIENTES_BANCOS\n" +
				"(\n" +
				" ID_CLIENTE\n" +
				", ID_BANCO\n" +
				", ID_PRODUCTO\n" +
				", STATUS\n" +
				") values (\n" +
				" '"  + force.getIdCliente() + "'\n" + 
				", '"  + catalogo.getCampo("ID_BANCO") + "'\n" + 
				", '" + catalogo.getCampo("ID_PRODUCTO") + "'\n" +
				", '" + catalogo.getCampo("STATUS") + "'\n" +
				")";

		//System.out.println(query);
		return query;
	}
	
	public String insertDestinos() {
		String query = "Insert into EAI_ADM_CLIENTES_DESTINOS\n" +
				"(\n" +
				" ID_CLIENTE\n" +
				", ID_DESTINO\n" +
				") values (\n" +
				" '"  + force.getIdCliente() + "'\n" + 
				", '"  + catalogo.getCampo("ID_DESTINO") + "'\n" + 
				")";

		//System.out.println(query);
		return query;
	}

	public String insertDocumentos() {
		String query = "Insert into EAI_ADM_CLIENTES_DOCUMENTOS\n" +
				"(\n" +
				"ID_CLIENTE\n" +
				", ID_DOCUMENTO\n" +
				") values (\n" +
				"'"  + force.getIdCliente() + "'\n" + 
				", '"  + catalogo.getCampo("ID_DOCUMENTO") + "'\n" + 
				")";
		
		//System.out.println(query);
		return query;
	}
	
	public String insertParametros() {
		String query = "Insert into EAI_ADM_CLIENTES_DATA values (\n"
				+ force.getIdCliente() + "\n"
				+ ",'" + catalogo.getId() + "'\n"
				+ ",'" + catalogo.getNombre() + "'\n"
				+ ",'" + catalogo.getDescripcion() + "'\n"
				+ ",CURRENT_TIMESTAMP" + "\n"
				+ ")";
		
		//System.out.println(query);
		return query;
	}
	
	public String getNextValue() {
		return "SELECT EAI_ADM_CLIENTES_SEQ.NEXTVAL FROM SYS.DUAL";
	}
		
	public void setForce(Force force) {
		this.force = force;
	}
	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
}