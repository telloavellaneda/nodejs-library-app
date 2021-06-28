/*
 * Copyright (C) 2014 Ashley Heyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import java.util.List;
import org.springframework.core.io.FileSystemResource;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  18/09/2014 12:01:58 PM  Description: 
 * EnvioCorreoService.java  
 * *****************************************************************************
 */

public interface EnvioCorreoService 
{
    public void enviarMensaje(String asunto, String mensaje, String correosTO, String correosCC, String correosCCO, List<FileSystemResource> archivos, boolean html) throws Exception;
}
