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
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 10:06:15  Description: 
 * VehiculoDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class VehiculoDaoImpl extends AbstractDaoImpl<Dvehiculo, Integer> implements VehiculoDao
{
    public VehiculoDaoImpl()
    {
        super(Dvehiculo.class);
    }

    public Dvehiculo findByPlaca(String placa) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dvehiculo.class);
        criteria.add(Restrictions.eq("placa", placa));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Dvehiculo) criteria.uniqueResult();
    }

}
