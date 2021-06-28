/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Dvehiculo;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 10:05:26  Description: 
 * VehiculoDao.java  
 * *****************************************************************************
 */
public interface VehiculoDao extends AbstractDao<Dvehiculo, Integer>
{
    public Dvehiculo findByPlaca(String placa) throws Exception;
}
