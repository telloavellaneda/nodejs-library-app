<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "force.eai.mx.util.Force" %>
<%@ page import = "force.eai.mx.form.FormUtils" %>
<%@ page import = "force.eai.mx.tools.CreateReports" %>

<%
if ( !(session.getAttribute("force") != null && session.getAttribute("dsReport") != null) ) {
	response.sendRedirect("/");
	return;
}

FormUtils fu = new FormUtils(session);
Force force = (Force)session.getAttribute("force");

String fileName = getServletContext().getInitParameter("temp-directory") + "EAIForce" + force.getUsuario("0").getIdPersona() + ".xlsx";
CreateReports cr = new CreateReports();
cr.setMainExport(fu.getDashboardSet("dsReport"));
out.println(fileName);
cr.create(fileName);
response.sendRedirect("uploadFiles?action=download&report=yes&key=" + fileName);
return;
%>