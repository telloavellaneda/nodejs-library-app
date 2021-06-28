/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.eai.tpp.form.DetalleSolicitudForm;
import mx.com.eai.tpp.service.EntityServiceComponent;
import mx.com.eai.tpp.service.GenerarPDFSolicitud;
import mx.com.eai.tpp.service.GetParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  03-dic-2017 11:22:38  Description: 
 * ServletPDFSolicitudController.java  
 * *****************************************************************************
 */
@Component("pdfServletSolicitud")
public class ServletPDFSolicitudController implements HttpRequestHandler
{
    @Autowired
    private EntityServiceComponent entityServiceComponent;
    @Autowired
    private GetParameterService getParameterService;
    @Autowired
    private GenerarPDFSolicitud generarPDFSolicitud;
    private Logger log = Logger.getLogger(this.getClass());
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            DetalleSolicitudForm detalleSolicitudForm = (DetalleSolicitudForm) this.entityServiceComponent.getEntity(String.valueOf(this.getParameterService.getParameterFromRequest(request, "key")));
            this.generarPDFSolicitud.generarPDF(detalleSolicitudForm, request, response);
        } catch (Exception e)
        {
            log.error("Ocurrio un error", e);
        }
    }
}
