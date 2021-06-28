
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

/* *****************************************************************************
 * @author grc  22-jun-2013 12:37:35  Description: 
 * MessageService.java 
 * *****************************************************************************
 */
public interface MessageService
{

    /**
     * Mensaje informativo
     *
     * @param title
     * @param description
     */
    public void addInfo(String title, String description);

    /**
     * Mensaje de error
     *
     * @param title
     * @param description
     */
    public void addError(String title, String description);

    /**
     * Mensajes de advertencia
     *
     * @param title
     * @param description
     */
    public void addWarn(String title, String description);

    /**
     *
     * @param title
     * @param description
     */
    public void addFatal(String title, String description);
}
