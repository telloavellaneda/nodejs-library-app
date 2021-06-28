/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.List;
import mx.com.eai.tpp.model.Ddetallematerial;
import mx.com.eai.tpp.util.Constant;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 18:22:10  Description: 
 * DetalleMaterialDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class DetalleMaterialDaoImpl extends AbstractDaoImpl<Ddetallematerial, Integer> implements DetalleMaterialDao
{
    public DetalleMaterialDaoImpl()
    {
        super(Ddetallematerial.class);
    }
    
    public List<Ddetallematerial> materialesBySolicitud(int solicitudID) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Ddetallematerial.class);
        criteria.createAlias("solicitudid", "solicitud", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("solicitud.solicitudid", solicitudID));
        criteria.add(Restrictions.eq("activo", 0));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
