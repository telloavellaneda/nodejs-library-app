
/*
 * Copyright (C) 2013 AshleyHeyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  1/08/2013 05:09:40 PM  Description: 
 * GetParameterServiceImpl.java  
 * *****************************************************************************
 */
@Service
public class GetParameterServiceImpl implements GetParameterService
{

    public String getParameterString(String parameter) throws Exception
    {
        Object result = this.getParameter(parameter);
        if (result != null)
        {
            return result.toString();
        }
        else
        {
            return "";
        }
    }

    public Integer getParameterInteger(String parameter) throws Exception
    {
        Object result = this.getParameter(parameter);
        if (result != null)
        {
            return Integer.parseInt(result.toString());
        }
        else
        {
            return 0;
        }
    }

    public Integer getParameterInteger(String parameter, int valorDefault) throws Exception
    {
        Object result = this.getParameter(parameter);
        if (result != null)
        {
            return Integer.parseInt(result.toString());
        }
        else
        {
            return valorDefault;
        }
    }

    public boolean getParameterBoolean(String parameter) throws Exception
    {
        Object result = this.getParameter(parameter);
        return (result != null);
    }

    public Object getParameter(String parameter) throws Exception
    {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getParameter(parameter) == null || request.getParameter(parameter).equals(""))
        {
            return null;
        }
        else
        {
            return request.getParameter(parameter);
        }
    }

    public Object getParameterFromRequest(HttpServletRequest httpServletRequest, String parameter) throws Exception
    {
        if (httpServletRequest.getParameter(parameter) == null || httpServletRequest.getParameter(parameter).equals(""))
        {
            return null;
        }
        else
        {
            return httpServletRequest.getParameter(parameter);
        }
    }

}
