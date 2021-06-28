
/*
 * Copyright (C) 2013 AshleyHeyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import javax.servlet.http.HttpServletRequest;

/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  1/08/2013 05:02:52 PM  Description: 
 * GetParameterService.java  
 * *****************************************************************************
 */
public interface GetParameterService
{
    public String getParameterString(String parameter) throws Exception;

    public Integer getParameterInteger(String parameter) throws Exception;
    
    public Integer getParameterInteger(String parameter, int valorDefault) throws Exception;

    public boolean getParameterBoolean(String parameter) throws Exception;
    
    public Object getParameterFromRequest(HttpServletRequest httpServletRequest, String parameter) throws Exception;
}
