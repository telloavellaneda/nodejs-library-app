package force.eai.mx.dao;

import java.text.MessageFormat;

import force.eai.mx.util.Force;
import force.eai.mx.util.Filter;
import force.eai.mx.tools.QryLoadTools;

public class QryLoadData {
	//private String RESOURCES = "resources/queries";
	private String RESOURCES = "queries";
	
	private Force force;
	private Filter filter;
	private QryLoadTools qlt;
	
	private String fieldFecha = "";
	private String filterClause = "";
		
	public QryLoadData(Force force) {
		this.force = force;
		this.qlt = new QryLoadTools(RESOURCES);
	}
		
	public String getMainQuery(int from, int to) {
		return getBaseMainQuery(createFilterWidget(), from, to);
	}

	public String getClientesMainQuery(int from, int to) {
		return getBaseMainQuery(createFilterCliente(), from, to);
	}

	public String getProspectosMainQuery(int from, int to) {
		return getBaseMainQuery(createFilterProspecto(), from, to);
	}
		
	private String getBaseMainQuery(String statusFilter, int from, int to) {
		String orderBy = "";
		orderBy += ( filter.isOrderByName() ) ? "X.APELLIDO_PATERNO" : "NVL(C.IMPORTE_SOLIC,0)";		
		orderBy += ( filter.isOrderDesc() ) ? " DESC" : "";

		String filter = MessageFormat.format(createFilter(), statusFilter);
		
		Object[] args = { force.getIdCliente(), filter, orderBy, String.valueOf(from), String.valueOf(to) };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("main.query"), args));
		//System.out.println(query);
		return query;
	}

	public String getMainQueryCount() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("count.query"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getMainExportQuery(int from, int to) {
		Object[] args = { force.getIdCliente(), filterClause, String.valueOf(from), String.valueOf(to) };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("export.query"), args));	
		//System.out.println(query);
		return query;
	}

	public String getByFasesQuery() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("fases.query"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getByStatusQuery() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("status.query"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getByBanksQuery() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("bancos.query"), args));
		//System.out.println(query);
		return query;
	}
	

	public String getByDecretoQuery() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("decreto.query"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getByUsuariosQuery() {
		Object[] args = { force.getIdCliente(), filterClause };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("usuario.query"), args));
		//System.out.println(query);
		return query;
	}

	public String getByMonthsQuery() {		
		Object[] args = { force.getIdCliente(), filterClause, fieldFecha };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("months.query"), args));
		//System.out.println(query);
		return query;
	}
	
	public String getNotificacionesQuery(String days) {
		String filterClause = "";
		
		// Multiusuario Operaciones
		String fieldUsuario = ( force.getUsuario("0").getProfile().isOperaciones() ) ? "ID_USUARIO_RESPONSABLE" : "ID_USUARIO";

		if ( !force.getUsuario("0").getProfile().isMultiusuario() )
			filterClause = qlt.build("AND A." + fieldUsuario, force.getUsuario("0").getIdPersona());
		
		Object[] args = { force.getIdCliente(), filterClause, fieldUsuario, days };
		String query = qlt.clean(MessageFormat.format( qlt.readQuery("notificaciones.query"), args));
		//System.out.println(query);
		return query;		
	}
		
	public void applyFilter() {
		filterClause = MessageFormat.format(createFilter(), createFilterWidget());
	}
	
	private String createFilter() {
		String result = "";
		
		if ( !filter.getDateAutYear().equals("") || !filter.getDateAutMonth().equals("") )
			fieldFecha = "FECHA_AUTORIZACION";
		else if ( !filter.getDateFrmYear().equals("") || !filter.getDateFrmMonth().equals("") )
			fieldFecha = "FECHA_FIRMA";
		else
			fieldFecha = "FECHA_EXPEDIENTE";
		
		result += qlt.build("AND A.FORCE_ID", 				filter.getForceId());
		result += qlt.build("AND A.ID_FASE", 				filter.getFase());
		result += "{0}";
		result += qlt.build("AND A.ID_USUARIO", 			filter.getIdUsuario());
		result += qlt.build("AND A.ID_USUARIO_RESPONSABLE", filter.getIdUsuarioResponsable());
		result += qlt.build("AND C.ID_BANCO", 				filter.getIdBanco());
		result += qlt.build("AND NVL(C.DECRETO,'')", 		filter.getDecreto());
		result += qlt.build("AND TO_CHAR(B.FECHA_EXPEDIENTE, ''YYYY'')", 	filter.getDateExpYear());
		result += qlt.build("AND TO_CHAR(B.FECHA_EXPEDIENTE, ''MM'')", 		filter.getDateExpMonth());
		result += qlt.build("AND TO_CHAR(B.FECHA_AUTORIZACION, ''YYYY'')", 	filter.getDateAutYear());
		result += qlt.build("AND TO_CHAR(B.FECHA_AUTORIZACION, ''MM'')", 	filter.getDateAutMonth());
		result += qlt.build("AND TO_CHAR(B.FECHA_FIRMA, ''YYYY'')", 		filter.getDateFrmYear());
		result += qlt.build("AND TO_CHAR(B.FECHA_FIRMA, ''MM'')", 			filter.getDateFrmMonth());
		
		result += qlt.buildLike("AND X.NOMBRE || '' '' || X.APELLIDO_PATERNO LIKE UPPER(''%" + filter.getFullName() + "%'')",	filter.getFullName());		
		
		return result;
	}
	
	private String createFilterWidget() {
		return qlt.build("AND A.ID_STATUS", filter.getStatus());
	}
	
	private String createFilterCliente() {		
		if ( !filter.getStatus().equals("") && filter.getStatus().equals("0") )
			return qlt.build("AND A.ID_STATUS", "-1");
		else if ( !filter.getStatus().equals("") && !filter.getStatus().equals("0") )
			return qlt.build("AND A.ID_STATUS", filter.getStatus());
		else 
			return "AND A.ID_STATUS <> 0";
	}

	private String createFilterProspecto() {
		if ( !filter.getStatus().equals("") && !filter.getStatus().equals("0") )
			return qlt.build("AND A.ID_STATUS", "-1");
		else
			return qlt.build("AND A.ID_STATUS", "0");
	}
	
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
}