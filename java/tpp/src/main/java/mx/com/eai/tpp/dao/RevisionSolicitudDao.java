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


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  25-dic-2017 23:21:32  Description: 
 * RevisionSolicitudDao.java  
 * *****************************************************************************
 */
public interface RevisionSolicitudDao extends AbstractDao<Drevisionsolicitud, Integer>
{
    public Drevisionsolicitud getRevisionByUsuario(int solicitudId, int persId) throws Exception;
}
