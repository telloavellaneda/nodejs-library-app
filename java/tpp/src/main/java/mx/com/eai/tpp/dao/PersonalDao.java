/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Dpersonal;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 20:15:49  Description: 
 * PersonalDao.java  
 * *****************************************************************************
 */
public interface PersonalDao extends AbstractDao<Dpersonal, Integer>
{
    public Dpersonal getByCURP(String curp) throws Exception;
}
