/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import mx.com.eai.tpp.model.Drevisionsolicitud;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  25-dic-2017 23:22:00  Description: 
 * RevisionSolicitudDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class RevisionSolicitudDaoImpl extends AbstractDaoImpl<Drevisionsolicitud, Integer> implements RevisionSolicitudDao
{
    public RevisionSolicitudDaoImpl()
    {
        super(Drevisionsolicitud.class);
    }
    
    public Drevisionsolicitud getRevisionByUsuario(int solicitudId, int persId) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Drevisionsolicitud.class);
        criteria.createAlias("solicitudid", "solicitud", JoinType.INNER_JOIN)
                .createAlias("persid", "usuario", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("solicitud.solicitudid", solicitudId))
                .add(Restrictions.eq("usuario.persid", persId));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Drevisionsolicitud) criteria.uniqueResult();
    }

}
