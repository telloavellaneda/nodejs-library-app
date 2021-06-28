/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.eai.tpp.form.DetalleSolicitudForm;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  03-dic-2017 11:34:52  Description: 
 * GenerarPDFSolicitud.java  
 * *****************************************************************************
 */
public interface GenerarPDFSolicitud
{
    public void generarPDF(DetalleSolicitudForm detalleSolicitudForm, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
