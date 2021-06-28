/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.util.Constant;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 20:00:51  Description: 
 * SolicitudDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class SolicitudDaoImpl extends AbstractDaoImpl<Dsolicitud, Integer> implements SolicitudDao
{

    public SolicitudDaoImpl()
    {
        super(Dsolicitud.class);
    }

    public List<Dsolicitud> obtenerSolicitudesUsuario(Dusuario usuario) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dsolicitud.class);
        criteria.add(Restrictions.eq("persid", usuario));
        criteria.addOrder(Order.desc("fecharegistro"));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List<Dsolicitud> obtenerSolicitudesPendientes(Dusuario usuario) throws Exception
    {
        String sentencia;
        if (usuario.getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION)
        {
            sentencia = "select sol.* "
                    + "FROM dsolicitud sol "
                    + "     INNER JOIN dusuario usuarioSol ON (sol.persId = usuarioSol.persId) "
                    + "     INNER JOIN dusuario padreUsuSol ON (usuarioSol.usuariopadreid = padreUsuSol.persId) "
                    + "     LEFT OUTER JOIN dusuario usuarioRev ON (sol.ultimousuariorevisaid = usuarioRev.persId) "
                    + "     LEFT OUTER JOIN dusuario padreUsuRev ON (usuarioRev.usuariopadreid = padreUsuRev.persId) "
                    + "WHERE "
                    + "     ((usuarioSol.usuariopadreid = " + usuario.getPersid() + " OR padreUsuSol.tipousuarioid = " + Constant.ID_TIPO_USUARIO_ADMINISTRACION + ") AND sol.estatussolicitudid = " + Constant.ID_ESTATUS_SOLICITUD_PENDIENTE + ") OR "
                    + "     ((usuarioRev.usuariopadreid = " + usuario.getPersid() + " OR padreUsuRev.tipousuarioid = " + Constant.ID_TIPO_USUARIO_ADMINISTRACION + ") AND sol.estatussolicitudid = " + Constant.ID_ESTATUS_SOLICITUD_APROBADA + ") "
                    + "ORDER BY fecharegistro";
        }
        else
        {
            sentencia = "select sol.* "
                    + "FROM dsolicitud sol "
                    + "     INNER JOIN dusuario usuarioSol ON (sol.persId = usuarioSol.persId) "
                    + "     LEFT OUTER JOIN dusuario usuarioRev ON (sol.ultimousuariorevisaid = usuarioRev.persId) "
                    + "WHERE "
                    + "     (usuarioSol.usuariopadreid = " + usuario.getPersid() + " AND sol.estatussolicitudid = " + Constant.ID_ESTATUS_SOLICITUD_PENDIENTE + ") OR "
                    + "     (usuarioRev.usuariopadreid = " + usuario.getPersid() + " AND sol.estatussolicitudid = " + Constant.ID_ESTATUS_SOLICITUD_APROBADA + ") "
                    + "ORDER BY fecharegistro";
        }

        Query query = getCurrentSession().createSQLQuery(sentencia)
                .addEntity(Dsolicitud.class);
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query.list();

//        Criteria criteria = getCurrentSession().createCriteria(Dsolicitud.class);
//        criteria.createAlias("estatussolicitudid", "estatussolicitud", JoinType.INNER_JOIN);
//        criteria.add(Restrictions.eq("estatussolicitud.estatussolicitudid", Constant.ID_ESTATUS_SOLICITUD_PENDIENTE));
//        criteria.addOrder(Order.desc("fecharegistro"));
//        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        return criteria.list();
    }

    public List<Dsolicitud> obtenerSolicitudesByFecha(Date fechaInicio, Date fechaFin, int estatusID, Dusuario usuario) throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFin);
        cal.add(Calendar.DAY_OF_YEAR, 1);

        String sentencia;
        if (usuario.getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION)
        {
            Criteria criteria = getCurrentSession().createCriteria(Dsolicitud.class);
            criteria.createAlias("estatussolicitudid", "estatussolicitud", JoinType.INNER_JOIN);
            criteria.add(Restrictions.between("fecharegistro", fechaInicio, cal.getTime()));
            if (estatusID != 0)
            {
                criteria.add(Restrictions.eq("estatussolicitud.estatussolicitudid", estatusID));
            }
            criteria.addOrder(Order.desc("fecharegistro"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            return criteria.list();
        }
        else
        {
            sentencia = "select sol.* "
                    + "FROM dsolicitud sol "
                    + "     INNER JOIN dusuario usuarioSol ON (sol.persId = usuarioSol.persId) "
                    + "     LEFT OUTER JOIN dusuario usuarioRev ON (sol.ultimousuariorevisaid = usuarioRev.persId) "
                    + "WHERE "
                    + "     sol.fecharegistro >= :fechaInicial AND sol.fecharegistro <= :fechaFinal AND "
                    + "     sol.solicitudId NOT IN (select solicitudId FROM dRevisionSolicitud where persId = " + usuario.getPersid() + " ) "
                    + "     ((usuarioSol.usuariopadreid = " + usuario.getPersid() + " "
                    + (estatusID != 0 ? "AND sol.estatussolicitudid = " + estatusID : "") + ") OR "
                    + "     (usuarioRev.usuariopadreid = " + usuario.getPersid() + " "
                    + (estatusID != 0 ? "AND sol.estatussolicitudid = " + estatusID : "") + ")) "
                    + "ORDER BY fecharegistro";
        }

        Query query = getCurrentSession().createSQLQuery(sentencia)
                .addEntity(Dsolicitud.class)
                .setParameter("fechaInicial", fechaInicio)
                .setParameter("fechaFinal", cal.getTime());
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query.list();
    }
    
    public Dsolicitud buscarSolicitudByFolioAndUsuarioRevisa(int folio, Dusuario usuario) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dsolicitud.class);
        criteria.createAlias("solicitudid", "solicitud", JoinType.INNER_JOIN)
                .createAlias("drevisionsolicitudList", "revisionsolicitud", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("solicitud.solicitudid", folio))
                .add(Restrictions.eq("revisionsolicitud.persid", usuario));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Dsolicitud)criteria.uniqueResult();
    }

    public List<Dsolicitud> buscarSolicitudesRevisadasByFecha(Date fechaInicio, Date fechaFin, int estatusID, Dusuario usuario) throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFin);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        
        Criteria criteria = getCurrentSession().createCriteria(Dsolicitud.class);
        criteria.createAlias("estatussolicitudid", "estatussolicitud", JoinType.INNER_JOIN)
                .createAlias("drevisionsolicitudList", "revisionsolicitud", JoinType.INNER_JOIN);
        criteria.add(Restrictions.between("fecharegistro", fechaInicio, cal.getTime()))
                .add(Restrictions.eq("revisionsolicitud.persid", usuario));
        if (estatusID != 0)
        {
            criteria.add(Restrictions.eq("estatussolicitud.estatussolicitudid", estatusID));
        }
        criteria.addOrder(Order.desc("fecharegistro"));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
