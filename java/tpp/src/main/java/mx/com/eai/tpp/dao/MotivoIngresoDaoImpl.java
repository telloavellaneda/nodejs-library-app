/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Cmotivoingreso;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 16:32:56  Description: 
 * MotivoIngresoDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class MotivoIngresoDaoImpl extends AbstractDaoImpl<Cmotivoingreso, Integer> implements MotivoIngresoDao
{
    public MotivoIngresoDaoImpl()
    {
        super(Cmotivoingreso.class);
    }

}
