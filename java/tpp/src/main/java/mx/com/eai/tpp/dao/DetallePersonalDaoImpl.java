/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Ddetallepersonal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 23:07:20  Description: 
 * DetallePersonalDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class DetallePersonalDaoImpl extends AbstractDaoImpl<Ddetallepersonal, Integer> implements DetallePersonalDao
{
    public DetallePersonalDaoImpl()
    {
        super(Ddetallepersonal.class);
    }

}
