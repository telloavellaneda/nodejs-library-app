/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.Date;
import java.util.List;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 20:01:13  Description: 
 * SolicitudDao.java  
 * *****************************************************************************
 */
public interface SolicitudDao extends AbstractDao<Dsolicitud, Integer>
{
    public List<Dsolicitud> obtenerSolicitudesUsuario(Dusuario usuario) throws Exception;
    
    public List<Dsolicitud> obtenerSolicitudesPendientes(Dusuario usuario) throws Exception;
    
    public List<Dsolicitud> obtenerSolicitudesByFecha(Date fechaInicio, Date fechaFin, int estatusID, Dusuario usuario) throws Exception;
    
    public Dsolicitud buscarSolicitudByFolioAndUsuarioRevisa(int folio, Dusuario usuario) throws Exception;
    
    public List<Dsolicitud> buscarSolicitudesRevisadasByFecha(Date fechaInicio, Date fechaFin, int estatusID, Dusuario usuario) throws Exception;
}
