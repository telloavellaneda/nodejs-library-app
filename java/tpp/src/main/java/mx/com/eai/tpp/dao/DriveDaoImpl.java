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
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 21:43:58  Description: 
 * DriveDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class DriveDaoImpl extends AbstractDaoImpl<Cdrive, Integer> implements DriveDao
{
    public DriveDaoImpl()
    {
        super(Cdrive.class);
    }
    
    public Cdrive getDrive() throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Cdrive.class);
        criteria.add(Restrictions.eq("id", 1));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Cdrive) criteria.uniqueResult();
    }

}
