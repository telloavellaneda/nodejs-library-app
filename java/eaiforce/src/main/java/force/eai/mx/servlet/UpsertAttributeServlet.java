package force.eai.mx.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import force.eai.mx.tools.BuildJson;
import force.eai.mx.util.Message;

/**
 * Servlet implementation class UpsertAttributeServlet
 */
@WebServlet("/UpsertAttributeServlet")
public class UpsertAttributeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpsertAttributeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message;
		HttpSession session = request.getSession();
		
		if ( session.getAttribute("force") == null )
			message = new Message("-2", "Inicie Sesión");

		else
			message = upsertAttributes(request, response);
		
		response.setCharacterEncoding("ISO-8859-1");
		response.getWriter().write( new BuildJson().write(message) );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Message upsertAttributes(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		Map<String,String[]> parametros = request.getParameterMap();
		
		for (Iterator<Entry<String, String[]>> iterator = parametros.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>) iterator.next();
			if ( entry.getValue().length > 0 && !entry.getValue()[0].equals("") )
				session.setAttribute(entry.getKey(), entry.getValue()[0]);
			else 
				session.removeAttribute(entry.getKey());
			//System.out.println( entry.getKey() + " " + entry.getValue()[0]);
		}
		return new Message("0", "Successful Upsert");
	}
}
