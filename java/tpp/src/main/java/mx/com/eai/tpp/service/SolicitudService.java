/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import java.util.Date;
import java.util.List;
import mx.com.eai.tpp.form.DetalleSolicitudForm;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;
import org.primefaces.model.UploadedFile;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:59:13  Description: 
 * SolicitudService.java  
 * *****************************************************************************
 */
public interface SolicitudService
{
    public void saveOrUpdate (Dsolicitud solicitud) throws Exception;
    
    public void getDetalleSolicitud(DetalleSolicitudForm detalleSolicitudForm) throws Exception;

    public void saveSolicitudPersonal(DetalleSolicitudForm detalleSolicitudForm) throws Exception;

    public void saveSolicitudVehiculo(DetalleSolicitudForm detalleSolicitudForm) throws Exception;

    public void saveSolicitudMateriales(DetalleSolicitudForm detalleSolicitudForm) throws Exception;

    public String downloadFile(String driveID) throws Exception;
    
    public List<Dsolicitud> obtenerMisSolicitudes(Dusuario usuario) throws Exception;

    public List<Dsolicitud> obtenerSolicitudesPendientes(Dusuario usuario) throws Exception;
    
    public List<Dsolicitud> buscarSolicitudesByFecha(Date fechaInicio, Date fechaFin, int estatusID, Integer folio) throws Exception;
    
    public List<Dsolicitud> buscarSolicitudesRevisadasByFecha(Date fechaInicio, Date fechaFin, int estatusID, Integer folio) throws Exception;
    
    public String descargarDocumentos(Dsolicitud solicitud) throws Exception;
    
    public String saveDocumentoAprobacion(Dsolicitud solicitud, UploadedFile aprobacion) throws Exception;
}
