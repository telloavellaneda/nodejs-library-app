
/*
 * Copyright (C) 2017 Ashley Heyeral Ruiz Fernandez
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.List;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.util.Constant;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/* *****************************************************************************
 * @author José Eduardo Figueroa Magaña  18-jul-2013 12:15:47  Description: 
 * UsuarioDaoImpl.java  
 * *****************************************************************************
 */
@Repository
@Transactional
public class UsuarioDaoImpl extends AbstractDaoImpl<Dusuario, Integer> implements UsuarioDao
{

    public UsuarioDaoImpl()
    {
        super(Dusuario.class);
    }

    public Dusuario getEncontrarUsuario(String usuario) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dusuario.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.add(Restrictions.eq("activo", Constant.ACTIVO));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Dusuario) criteria.uniqueResult();
    }
    
    public List<Dusuario> usuariosAdministradores() throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dusuario.class);
        criteria.add(Restrictions.eq("tipo", Constant.ID_TIPO_USUARIO_ADMINISTRACION))
                .add(Restrictions.eq("activo", Constant.ACTIVO));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
    
    public List<Dusuario> usuariosByNivel(Dusuario usuario) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dusuario.class);
        criteria.add(Restrictions.eq("usuariopadreid", usuario))
                .add(Restrictions.eq("activo", Constant.ACTIVO));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
    
    public boolean validaUsuario(String usuario) throws Exception
    {
        Criteria criteria = getCurrentSession().createCriteria(Dusuario.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.uniqueResult() != null;
    }
}
