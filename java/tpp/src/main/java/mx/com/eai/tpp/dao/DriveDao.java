/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Cdrive;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 21:43:23  Description: 
 * DriveDao.java  
 * *****************************************************************************
 */
public interface DriveDao extends AbstractDao<Cdrive, Integer>
{
    public Cdrive getDrive() throws Exception;
}
