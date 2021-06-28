package force.eai.mx.orchestration;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import force.eai.mx.util.Force;
import force.eai.mx.util.Pager;
import force.eai.mx.util.Filter;
import force.eai.mx.form.FormUtils;
import force.eai.mx.dao.QryLoadData;
import force.eai.mx.database.Upsert;
import force.eai.mx.tools.FormatValues;

public class BuildDashboard {
	private double pageSize = 100;
	
	private Pager pager;
	private FormUtils fu;
	private Upsert upsert;
	private ResultSet rset;
	private QryLoadData queries;
	private HttpSession session;
	private FormatValues fv = new FormatValues();
	
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byFase = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byStatus = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byBancos = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byDecreto = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byUsuario = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,LinkedHashMap<String,String>> 	byMonths = new LinkedHashMap<String,LinkedHashMap<String,String>>();
	private LinkedHashMap<String,HashMap<String,String>>		dsBuscar = new LinkedHashMap<String,HashMap<String,String>>();

	public BuildDashboard(Connection connection, HttpSession session) throws SQLException {
		this.upsert = new Upsert(connection);
		this.session = session;
		init();
	}
	
	public void close() {
		try {
			rset.close();
		} catch (SQLException|NullPointerException sqlException) {
			// Nothing
		}
		this.upsert.close();
	}
	
	private void init() {
		this.queries = new QryLoadData(Force.class.cast(session.getAttribute("force")));
		this.fu = new FormUtils(session);
		
		Filter filter = new Filter();
		
		filter.setForceId(fu.getStringFromSession("filter_force_id"));
		filter.setFullName(fu.getStringFromSession("filter_full_name"));
		filter.setIdBanco(fu.getStringFromSession("filter_banco"));
		filter.setFase(fu.getStringFromSession("filter_fase"));
		filter.setStatus(fu.getStringFromSession("filter_status"));
		filter.setIdUsuario(fu.getStringFromSession("filter_user"));
		filter.setIdUsuarioResponsable(fu.getStringFromSession("filter_user_responsable"));
		filter.setDateExpYear(fu.getStringFromSession("filter_year"));
		filter.setDateExpMonth(fu.getStringFromSession("filter_month"));
		filter.setDateAutYear(fu.getStringFromSession("filter_year_autorizacion"));
		filter.setDateAutMonth(fu.getStringFromSession("filter_month_autorizacion"));
		filter.setDateFrmYear(fu.getStringFromSession("filter_year_firma"));
		filter.setDateFrmMonth(fu.getStringFromSession("filter_month_firma"));
		filter.setDecreto(fu.getStringFromSession("filter_decreto"));
		
		queries.setFilter(filter);
		queries.applyFilter();
	}
	
	public void count() throws SQLException {
		this.pager = new Pager();		
		Pager oldPager = ( session.getAttribute("pager") != null ) ? (Pager)session.getAttribute("pager") : new Pager();

		rset = upsert.query(queries.getMainQueryCount());
		while ( rset.next() ) {
			if ( rset.getString("STATUS").equals("CLIENTES") ) {
				pager.setClTotal(rset.getDouble("CLIENTES"));
				pager.setClSolic(rset.getDouble("MONTO_SOLIC"));
				pager.setClAprob(rset.getDouble("MONTO_APROB"));
				pager.setClFinal(rset.getDouble("MONTO_FINAL"));
				pager.setClPages(createPages(rset.getDouble("CLIENTES")));
				pager.setClCurrent(oldPager.getClCurrent());
				
			} else if ( rset.getString("STATUS").equals("PROSPECTOS") ) {
				pager.setPrTotal(rset.getDouble("CLIENTES"));
				pager.setPrSolic(rset.getDouble("MONTO_SOLIC"));
				pager.setPrAprob(rset.getDouble("MONTO_APROB"));
				pager.setPrFinal(rset.getDouble("MONTO_FINAL"));
				pager.setPrPages(createPages(rset.getDouble("CLIENTES")));
				pager.setPrCurrent(oldPager.getPrCurrent());
			}
		}
		pager.setAllTotal(pager.getClTotal() + pager.getPrTotal());
		pager.setAllSolic(pager.getClSolic() + pager.getPrSolic());
		pager.setAllAprob(pager.getClAprob() + pager.getPrAprob());
		pager.setAllFinal(pager.getClFinal() + pager.getPrFinal());
		pager.setAllPages(createPages(pager.getAllTotal()));
		pager.setAllCurrent(oldPager.getAllCurrent());
		
		session.setAttribute("pager", pager);
	}
	
	public void build() throws SQLException {				
		double clientes = 0;
		double montoSolic = 0;
		double montoAprob = 0;
		double montoFinal = 0;
		
		rset = upsert.query(queries.getByFasesQuery());
		while ( rset.next() ) {			
			String key = rset.getString(1);
			byFase.put(key, new LinkedHashMap<String,String>());
			byFase.get(key).put("ID_FASE", rset.getString(1));
			byFase.get(key).put("FASE", rset.getString(2));
			byFase.get(key).put("CLIENTES", fv.formatMoneyCompact(rset.getString("CLIENTES")));
			byFase.get(key).put("MONTO", fv.formatMoney(rset.getDouble("MONTO")) );
			byFase.get(key).put("MONTO_APROBADO", fv.formatMoney(rset.getDouble("MONTO_APROBADO")) );
			byFase.get(key).put("MONTO_FINAL", fv.formatMoney(rset.getDouble("MONTO_FINAL")) );
			
			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byFase.put("TOTAL", new LinkedHashMap<String,String>());
		byFase.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byFase.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byFase.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byFase.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));
		
		clientes = 0;
		montoSolic = 0;
		montoAprob = 0;
		montoFinal = 0;
		
		rset = upsert.query(queries.getByStatusQuery());
		while ( rset.next() ) {
			String key = rset.getString(1) + "-" + rset.getString(3);
			byStatus.put(key, new LinkedHashMap<String,String>());
			byStatus.get(key).put("ID_FASE", rset.getString(1));
			byStatus.get(key).put("FASE", rset.getString(2));
			byStatus.get(key).put("ID_STATUS", rset.getString(3));
			byStatus.get(key).put("STATUS", rset.getString(4));
			byStatus.get(key).put("CLIENTES", fv.formatMoneyCompact(rset.getString("CLIENTES")));
			byStatus.get(key).put("MONTO", fv.formatMoney(rset.getDouble("MONTO")) );
			byStatus.get(key).put("MONTO_APROBADO", fv.formatMoney(rset.getDouble("MONTO_APROBADO")) );
			byStatus.get(key).put("MONTO_FINAL", fv.formatMoney(rset.getDouble("MONTO_FINAL")) );

			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byStatus.put("TOTAL", new LinkedHashMap<String,String>());
		byStatus.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byStatus.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byStatus.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byStatus.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));

		clientes = 0;
		montoSolic = 0;
		montoAprob = 0;
		montoFinal = 0;

		rset = upsert.query(queries.getByBanksQuery());
		while ( rset.next() ) {
			String key = rset.getString(1);
			byBancos.put(key, new LinkedHashMap<String,String>());
			byBancos.get(key).put("ID_BANCO", rset.getString(1));
			byBancos.get(key).put("BANCO", rset.getString(2));
			byBancos.get(key).put("CLIENTES", fv.formatMoneyCompact(rset.getString("CLIENTES")));
			byBancos.get(key).put("MONTO", fv.formatMoney(rset.getDouble("MONTO")) );
			byBancos.get(key).put("MONTO_APROBADO", fv.formatMoney(rset.getDouble("MONTO_APROBADO")) );
			byBancos.get(key).put("MONTO_FINAL", fv.formatMoney(rset.getDouble("MONTO_FINAL")) );

			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byBancos.put("TOTAL", new LinkedHashMap<String,String>());
		byBancos.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byBancos.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byBancos.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byBancos.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));

		clientes = 0;
		montoSolic = 0;
		montoAprob = 0;
		montoFinal = 0;

		rset = upsert.query(queries.getByDecretoQuery());
		while ( rset.next() ) {
			String key = rset.getString(1) + "|" + rset.getString(2);
			if (byDecreto.get(key) == null)
				byDecreto.put(key, new LinkedHashMap<String,String>());
			byDecreto.get(key).put(
					rset.getString(3), 
					fv.formatMoneyCompact(rset.getString(4)) + "|" + rset.getDouble("MONTO") + "|" + rset.getDouble("MONTO_FINAL") 
					);
			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byDecreto.put("TOTAL", new LinkedHashMap<String,String>());
		byDecreto.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byDecreto.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byDecreto.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byDecreto.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));

		clientes = 0;
		montoSolic = 0;
		montoAprob = 0;
		montoFinal = 0;

		rset = upsert.query(queries.getByUsuariosQuery());
		while ( rset.next() ) {
			String key = rset.getString(1);
			byUsuario.put(key, new LinkedHashMap<String,String>());
			byUsuario.get(key).put("ID_USUARIO", rset.getString(1));
			byUsuario.get(key).put("USUARIO", rset.getString(2) + " " + rset.getString(3));
			byUsuario.get(key).put("CLIENTES", fv.formatMoneyCompact(rset.getString("CLIENTES")));
			byUsuario.get(key).put("MONTO", fv.formatMoney(rset.getDouble("MONTO")) );
			byUsuario.get(key).put("MONTO_APROBADO", fv.formatMoney(rset.getDouble("MONTO_APROBADO")) );
			byUsuario.get(key).put("MONTO_FINAL", fv.formatMoney(rset.getDouble("MONTO_FINAL")) );

			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byUsuario.put("TOTAL", new LinkedHashMap<String,String>());
		byUsuario.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byUsuario.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byUsuario.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byUsuario.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));

		clientes = 0;
		montoSolic = 0;
		montoAprob = 0;
		montoFinal = 0;

		rset = upsert.query(queries.getByMonthsQuery());
		while ( rset.next() ) {
			String key = rset.getString(1);
			byMonths.put(key, new LinkedHashMap<String,String>());
			byMonths.get(key).put("MESES", rset.getString(1));
			byMonths.get(key).put("CLIENTES", fv.formatMoneyCompact(rset.getString("CLIENTES")));
			byMonths.get(key).put("MONTO", fv.formatMoney(rset.getDouble("MONTO")) );
			byMonths.get(key).put("MONTO_APROBADO", fv.formatMoney(rset.getDouble("MONTO_APROBADO")) );
			byMonths.get(key).put("MONTO_FINAL", fv.formatMoney(rset.getDouble("MONTO_FINAL")) );

			clientes += rset.getDouble("CLIENTES");
			montoSolic += rset.getDouble("MONTO");
			montoAprob += rset.getDouble("MONTO_APROBADO");
			montoFinal += rset.getDouble("MONTO_FINAL");
		}
		byMonths.put("TOTAL", new LinkedHashMap<String,String>());
		byMonths.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		byMonths.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		byMonths.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		byMonths.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));
		
		session.setAttribute("dsByFase", byFase);
		session.setAttribute("dsByStatus", byStatus);
		session.setAttribute("dsByBancos", byBancos);
		session.setAttribute("dsByUsuario", byUsuario);
		session.setAttribute("dsByDecreto", byDecreto);
		session.setAttribute("dsByMonths", byMonths);		
	}
	
	public int fetchClientes(int pageRequested, boolean order) throws SQLException {
		if ( pager == null )
			pager = (Pager)session.getAttribute("pager");
		
		if ( pager == null )
			throw new SQLException("Paginador de Cliente no existe");
		
		if ( pageRequested >= pager.getClPages().size() )
			pageRequested = ( pager.getClPages().size() != 0) ? pager.getClPages().size() - 1 : 0;
		else if ( pageRequested < 0 )
			pageRequested = 0;
		
		pager.setClCurrent(pageRequested);

		if ( pager.getClPages().get(pageRequested) != null ) {
			if (pager.getClPages().get(pageRequested).get("from") != null)
				pager.setClFrom( pager.getClPages().get(pageRequested).get("from") );
			if (pager.getClPages().get(pageRequested).get("to") != null)
				pager.setClTo( pager.getClPages().get(pageRequested).get("to") );
		}

		queries.getFilter().setOrderDesc(order);
		session.setAttribute("dsClientes", fetch(queries.getClientesMainQuery(pager.getClFrom(), pager.getClTo())) );
		session.setAttribute("pager", pager);
		return pageRequested;
	}
	
	public int fetchProspectos(int pageRequested, boolean order) throws SQLException {
		if ( pager == null )
			pager = (Pager)session.getAttribute("pager");
		
		if ( pager == null )
			throw new SQLException("Paginador de Prospectos no existe");
		
		if ( pageRequested >= pager.getPrPages().size() )
			pageRequested = ( pager.getPrPages().size() != 0) ? pager.getPrPages().size() - 1 : 0;
		else if ( pageRequested < 0 )
			pageRequested = 0;
		
		pager.setPrCurrent(pageRequested);
		
		if ( pager.getPrPages().get(pageRequested) != null ) {
			if (pager.getPrPages().get(pageRequested).get("from") != null)
				pager.setPrFrom( pager.getPrPages().get(pageRequested).get("from") );
			if (pager.getPrPages().get(pageRequested).get("to") != null)
				pager.setPrTo( pager.getPrPages().get(pageRequested).get("to") );
		}

		queries.getFilter().setOrderDesc(order);
		session.setAttribute("dsProspectos", fetch(queries.getProspectosMainQuery(pager.getPrFrom(), pager.getPrTo())) );		
		session.setAttribute("pager", pager);
		return pageRequested;
	}
	
	public void fetchSearch(int pageRequested) throws SQLException {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		String dataset = "";
		int from = 0;
		int to = 0;
		
		if ( pager == null )
			pager = (Pager)session.getAttribute("pager");
		
		if ( pageRequested != -99 ) {
			if ( pageRequested >= pager.getAllPages().size() )
				pageRequested = ( pager.getAllPages().size() != 0) ? pager.getAllPages().size() - 1 : 0;
			else if ( pageRequested < 0 )
				pageRequested = 0;

			pager.setAllCurrent(pageRequested);
			
			if ( pager.getAllPages().get(pageRequested) != null ) {
				if (pager.getAllPages().get(pageRequested).get("from") != null)
					pager.setAllFrom( pager.getAllPages().get(pageRequested).get("from") );
				if (pager.getAllPages().get(pageRequested).get("to") != null)
					pager.setAllTo( pager.getAllPages().get(pageRequested).get("to") );
			}
			from = pager.getAllFrom();
			to = pager.getAllTo();
			dataset = "dsBuscar";
			
		} else {
			// ALL
			from = 0;
			to = new Double( pager.getAllTotal() ).intValue();
			dataset = "dsReport";
		}

		rset = upsert.query(queries.getMainExportQuery(from, to));
        ResultSetMetaData rsmd = rset.getMetaData();
        for (int j = 0; j < rsmd.getColumnCount(); j++ )
    		columns.put(rsmd.getColumnName(j+1), getType(rsmd.getColumnName(j+1)));

        dsBuscar.put("-1", columns);
        
        int i = 0;
        while (rset.next()) {
        	dsBuscar.put(i + "", new LinkedHashMap<String, String>());
        	for (Iterator<String> iterator = columns.keySet().iterator(); iterator.hasNext();) {
            	String column = iterator.next();
            	dsBuscar.get(i + "").put(column, rset.getString(column));
            }
        	i++;
        }
        session.setAttribute("pager", pager);
		session.setAttribute(dataset, dsBuscar);
    }
	
	public LinkedHashMap<String,HashMap<String,String>> fetch (String query) throws SQLException {
		double clientes = 0;
		double montoSolic = 0;
		double montoAprob = 0;
		double montoFinal = 0;
		LinkedHashMap<String,HashMap<String,String>> dataSet = new LinkedHashMap<String,HashMap<String,String>>();
		
		try {
			ResultSet rset = upsert.query(query);
			while ( rset.next() ) {
				String key = rset.getString("FORCE_ID");
				String fullName = fv.fullName(new String[] { rset.getString("APELLIDO_PATERNO"), rset.getString("APELLIDO_MATERNO"), rset.getString("NOMBRE"), rset.getString("SEGUNDO_NOMBRE") } );
				
				dataSet.put(key, new LinkedHashMap<String,String>());
				dataSet.get(key).put("FORCE_ID", rset.getString("FORCE_ID"));
				dataSet.get(key).put("NOMBRE", fullName);
				dataSet.get(key).put("IMPORTE_SOLIC", fv.formatMoney(rset.getString("IMPORTE_SOLIC")));
				dataSet.get(key).put("IMPORTE_APROB", fv.formatMoney(rset.getString("IMPORTE_APROB")));
				dataSet.get(key).put("IMPORTE_FINAL", fv.formatMoney(rset.getString("IMPORTE_FINAL")));
				dataSet.get(key).put("DECRETO", rset.getString("DECRETO"));
				dataSet.get(key).put("BANCO", rset.getString("BANCO"));
				dataSet.get(key).put("NOMBRE_STATUS", rset.getString("NOMBRE_STATUS"));
				dataSet.get(key).put("FECHA_CREACION", fv.formatTimestampCompact(rset.getString("FECHA_CREACION")));

				clientes ++;		
				montoSolic += rset.getDouble("IMPORTE_SOLIC");
				montoAprob += rset.getDouble("IMPORTE_APROB");
				montoFinal += rset.getDouble("IMPORTE_FINAL");
			}
			rset.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new SQLException("Error en el paginador", sqlException);
		}
		
		dataSet.put("TOTAL", new LinkedHashMap<String,String>());
		dataSet.get("TOTAL").put("CLIENTES", fv.formatMoneyCompact(clientes));
		dataSet.get("TOTAL").put("MONTO", fv.formatMoney(montoSolic));
		dataSet.get("TOTAL").put("MONTO_APROBADO", fv.formatMoney(montoAprob));
		dataSet.get("TOTAL").put("MONTO_FINAL", fv.formatMoney(montoFinal));
		
		return dataSet;
	}
	
	private HashMap<Integer,HashMap<String,Integer>> createPages(double total) {
		HashMap<Integer,HashMap<String,Integer>> pages = new HashMap<Integer,HashMap<String,Integer>>();
		
		for (double i = 0; i < Math.ceil(total / pageSize); i++) {
			pages.put(parseInt(i), new HashMap<String,Integer>());
			double temporal = ( ((i+1) * pageSize) <= total ) ? (i+1) * pageSize : total;
			pages.get(parseInt(i)).put("from", parseInt(i * pageSize));
			pages.get(parseInt(i)).put("to", parseInt(temporal));
		}
		
		return pages;
	}
	
	private Integer parseInt(double value) {
		return Integer.valueOf(new Double(value).intValue());
	}
	
	private String getType(String column) {
		if (column.indexOf("INT_") != -1)
			return "1";
		if (column.indexOf("DBL_") != -1)
			return "2";
		if (column.indexOf("FEC_") != -1)
			return "3";
		if (column.indexOf("ID_") != -1)
			return "4";
		return "0";
	}
	
	public Pager getPager() {
		return pager;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByFase() {
		return byFase;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByStatus() {
		return byStatus;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByBancos() {
		return byBancos;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByDecreto() {
		return byDecreto;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByUsuario() {
		return byUsuario;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getByMonths() {
		return byMonths;
	}
	public LinkedHashMap<String, HashMap<String, String>> getDsBuscar() {
		return dsBuscar;
	}
	
	public void setPageSize(double pageSize) {
		this.pageSize = pageSize;
	}
}