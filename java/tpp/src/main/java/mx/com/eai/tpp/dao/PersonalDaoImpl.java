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
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 20:16:17  Description: 
 * PersonalDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class PersonalDaoImpl extends AbstractDaoImpl<Dpersonal, Integer> implements PersonalDao
{
    public PersonalDaoImpl()
    {
        super(Dpersonal.class);
    }

    public Dpersonal getByCURP(String curp) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dpersonal.class);
        criteria.add(Restrictions.eq("curp", curp));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Dpersonal) criteria.uniqueResult();
    }

}
