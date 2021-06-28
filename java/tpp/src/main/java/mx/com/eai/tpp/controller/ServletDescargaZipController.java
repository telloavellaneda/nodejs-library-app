/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */

package mx.com.eai.tpp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import mx.com.eai.tpp.service.EntityServiceComponent;
import mx.com.eai.tpp.service.GetParameterService;

/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:59:04  Description: 
 * DescargarZipController.java  
 * *****************************************************************************
 */
@Component("downloadZip")
public class ServletDescargaZipController implements HttpRequestHandler
{
    @Autowired
    private EntityServiceComponent entityServiceComponent;
    @Autowired
    private GetParameterService getParameterService;

    private Logger log = Logger.getLogger(this.getClass());

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) this.entityServiceComponent.getEntity(String.valueOf(this.getParameterService.getParameterFromRequest(request, "key")));
            String nombre = String.valueOf(this.getParameterService.getParameterFromRequest(request, "file"));

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + (nombre.equals("") ? "download" : nombre) + ".zip");

            InputStream in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ServletOutputStream out = response.getOutputStream();

            int n;
            byte[] outputByte = new byte[4096];

            while (-1 != (n = in.read(outputByte)))
            {
                out.write(outputByte, 0, n);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }
}
