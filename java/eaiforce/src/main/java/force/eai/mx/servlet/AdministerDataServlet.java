package force.eai.mx.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import force.eai.mx.database.Connect;
import force.eai.mx.orchestration.UpsertForce;
import force.eai.mx.util.Catalogo;
import force.eai.mx.util.Force;

/**
 * Servlet implementation class AdministerDataServlet
 */
@WebServlet("/AdministerDataServlet")
public class AdministerDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdministerDataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			@SuppressWarnings("unchecked")
			Map<String, String[]> map = request.getParameterMap();
			String idCliente = map.get("x")[0];
			
			Connect connect =  new Connect("dev");
	//		if ( !connect.getMessage().getCode().equals("0") ) {
	//			response.setCharacterEncoding("UTF-8");
	//			response.getWriter().println(connect.getMessage().getMessage());
	//			return;
	//		}
			
			Force force = new Force();
			force.setIdCliente(idCliente);		
	
			String[] parametros = map.get("q");
			LinkedHashMap<String,LinkedHashMap<String,Catalogo>> initCatalogues = new LinkedHashMap<String,LinkedHashMap<String,Catalogo>>();
			LinkedHashMap<String,Catalogo> temporal = new LinkedHashMap<String,Catalogo>();
	
			for (int i = 0; i < parametros.length ; i++) {
				String idBanco = parametros[i].split("_")[0];
				String idProducto = parametros[i].split("_")[1];
				String key = idBanco + "-" + idProducto;
				
				temporal.put(key, new Catalogo());
				temporal.get(key).setCampo("ID_BANCO", idBanco);
				temporal.get(key).setCampo("ID_PRODUCTO", idProducto);
			}
			initCatalogues.put("BANCOS", temporal);

			UpsertForce upsertForce = new UpsertForce(connect.getConnection());
			upsertForce.setForce(force);
			upsertForce.setInitCatalogues(initCatalogues);
			upsertForce.upsertBancosProductos();			
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(upsertForce.getMessage().getMessage());
			
		} catch (Exception e) {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
