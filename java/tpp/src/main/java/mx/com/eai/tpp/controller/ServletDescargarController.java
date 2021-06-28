/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.eai.tpp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.eai.tpp.service.GetParameterService;
import mx.com.eai.tpp.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

/**
 *
 * @author AshleyHeyeral
 */
@Component("downloadServlet")
public class ServletDescargarController implements HttpRequestHandler
{
    @Autowired
    private GetParameterService getParameterService;
    @Resource(name = "rutas")
    private Properties rutasProperties;

    private Logger log = Logger.getLogger(this.getClass());

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String fileName = (String) this.getParameterService.getParameterFromRequest(request, "file");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            
            
            File file = new File(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + "/" + fileName);
            FileInputStream fileIn = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();

            byte[] outputByte = new byte[4096];
            while (fileIn.read(outputByte, 0, 4096) != -1)
            {
                out.write(outputByte, 0, 4096);
            }
            fileIn.close();
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }
}
