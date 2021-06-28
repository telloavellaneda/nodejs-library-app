/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.com.eai.tpp.dao.MotivoIngresoDao;
import mx.com.eai.tpp.dao.PersonalDao;
import mx.com.eai.tpp.dao.RevisionSolicitudDao;
import mx.com.eai.tpp.dao.VehiculoDao;
import mx.com.eai.tpp.form.DetalleSolicitudForm;
import mx.com.eai.tpp.model.Cestatussolicitud;
import mx.com.eai.tpp.model.Ddetallematerial;
import mx.com.eai.tpp.model.Dpersonal;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dvehiculo;
import mx.com.eai.tpp.model.subclases.DetalleMaterialImagen;
import mx.com.eai.tpp.service.EntityServiceComponent;
import mx.com.eai.tpp.service.EnvioCorreoService;
import mx.com.eai.tpp.service.GetParameterService;
import mx.com.eai.tpp.service.MessageService;
import mx.com.eai.tpp.service.SessionService;
import mx.com.eai.tpp.service.SolicitudService;
import mx.com.eai.tpp.util.Constant;
import mx.com.eai.tpp.util.Util;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:21:13  Description: 
 * DetalleSolicitudController.java  
 * *****************************************************************************
 */
@Controller
@Scope(value = "view")
public class DetalleSolicitudController
{

    @Autowired
    private MessageService messageService;
    @Autowired
    private GetParameterService getParameterService;
    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private VehiculoDao vehiculoDao;
    @Autowired
    private MotivoIngresoDao motivoIngresoDao;
    @Autowired
    private RevisionSolicitudDao revisionSolicitudDao;
    @Autowired
    private EntityServiceComponent entityServiceComponent;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private EnvioCorreoService envioCorreoService;
    @Resource(name = "rutas")
    private Properties rutasProperties;

    private DetalleSolicitudForm detalleSolicitudForm;
    private Logger log = Logger.getLogger(this.getClass());

    public DetalleSolicitudController()
    {
        this.detalleSolicitudForm = new DetalleSolicitudForm();
    }

    @PostConstruct
    public void init()
    {
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DAY_OF_YEAR, 2);
            this.detalleSolicitudForm.setFechaInicio(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, 90);
            this.detalleSolicitudForm.setFechaFin(cal.getTime());
            this.detalleSolicitudForm.setUsuario(this.sessionService.getUsuarioFromSession());
            this.detalleSolicitudForm.setMotivos(this.motivoIngresoDao.getAllOrderByDescripcion());

            if (this.getParameterService.getParameterInteger("numSolicitud") != 0)
            {
                this.detalleSolicitudForm.setSoloLectura(this.getParameterService.getParameterBoolean("revisar"));
                this.detalleSolicitud(this.getParameterService.getParameterInteger("numSolicitud"));
            }
            else
            {

                if (this.detalleSolicitudForm.getUsuario().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION)
                {
                    this.detalleSolicitudForm.setUsuarioAdmin(true);
                    if (this.detalleSolicitudForm.getNumSolicitud() == 0)
                    {
                        this.detalleSolicitudForm.setSolicitudes(this.solicitudService.obtenerSolicitudesPendientes(this.detalleSolicitudForm.getUsuario()));
                    }
                }

                if (this.detalleSolicitudForm.getUsuario().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_SUPERVISOR)
                {
                    this.detalleSolicitudForm.setUsuarioAdmin(true);
                    if (this.detalleSolicitudForm.getNumSolicitud() == 0)
                    {
                        this.detalleSolicitudForm.setSolicitudes(this.solicitudService.obtenerSolicitudesPendientes(this.detalleSolicitudForm.getUsuario()));
                    }
                    else if (this.detalleSolicitudForm.getSolicitud().getPersid().getPersid() == this.detalleSolicitudForm.getUsuario().getPersid())
                    {
                        this.detalleSolicitudForm.setUsuarioAdmin(false);
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void buscarSolicitudes()
    {
        try
        {
            this.detalleSolicitudForm.setSolicitudes(this.solicitudService.buscarSolicitudesByFecha(
                    this.detalleSolicitudForm.getBusquedaFechaInicio(),
                    this.detalleSolicitudForm.getBusquedaFechaFin(),
                    this.detalleSolicitudForm.getEstatusSeleccionado(),
                    this.detalleSolicitudForm.getFolioSolicitud()
            ));
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void redirectSolicitud(SelectEvent event)
    {
        try
        {
            Dsolicitud sol = (Dsolicitud) event.getObject();
            this.detalleSolicitud(sol.getSolicitudid());
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void detalleSolicitud(int numSolicitud)
    {
        try
        {
            this.detalleSolicitudForm.setNumSolicitud(numSolicitud);
            this.solicitudService.getDetalleSolicitud(this.detalleSolicitudForm);
            if (this.detalleSolicitudForm.getUsuario().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION
                    || (this.detalleSolicitudForm.getUsuario().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_SUPERVISOR && this.detalleSolicitudForm.getSolicitud().getPersid().getPersid() != this.detalleSolicitudForm.getUsuario().getPersid()))
            {
                this.detalleSolicitudForm.setUsuarioAdmin(true);
            }
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error:", e);
        }
    }

    public void buscaPersonal()
    {
        try
        {
            this.detalleSolicitudForm.getPersonal().setCurp(this.detalleSolicitudForm.getPersonal().getCurp().toUpperCase());
            Dpersonal personal = this.personalDao.getByCURP(this.detalleSolicitudForm.getPersonal().getCurp());
            if (personal != null)
            {
                this.detalleSolicitudForm.setPersonal(personal);
                this.detalleSolicitudForm.setRenderFoto(true);
                this.detalleSolicitudForm.setRenderIdentificacion(true);
                this.detalleSolicitudForm.setRenderSeguro(personal.getDriveseguro() != null);
                this.detalleSolicitudForm.setRenderLicencia(personal.getDrivelicencia() != null);
                if (detalleSolicitudForm.getPersonal().getTiposeguro().equals("Seguro de Gastos Médicos")
                        && this.detalleSolicitudForm.getPersonal().getVigenciaseguro().before(this.detalleSolicitudForm.getFechaInicio()))
                {
                    this.messageService.addError("Seguro vencida", "Su poliza de seguro médico esta vencida, favor de actualizarla");
                    detalleSolicitudForm.getPersonal().setVigenciaseguro(null);
                    this.detalleSolicitudForm.setRenderSeguro(false);

                }
                if (this.detalleSolicitudForm.getPersonal().getVigencialicenciamanejo().before(this.detalleSolicitudForm.getFechaInicio()))
                {
                    this.messageService.addWarn("Licencia vencida", "Si requiere chofer, su licencia de manejo esta vencida, favor de actualizarla");
                    detalleSolicitudForm.getPersonal().setVigencialicenciamanejo(null);
                    this.detalleSolicitudForm.setRenderLicencia(false);
                }
            }
            else
            {
                this.messageService.addWarn("No existe", "No se ha encontrado un registro anterior, registra los datos solicitados.");
            }
            this.detalleSolicitudForm.setRenderDetallePersonal(true);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void buscaVehiculo()
    {
        try
        {
            this.detalleSolicitudForm.getVehiculo().setPlaca(this.detalleSolicitudForm.getVehiculo().getPlaca().toUpperCase());
            Dvehiculo vehiculo = this.vehiculoDao.findByPlaca(this.detalleSolicitudForm.getVehiculo().getPlaca());
            if (vehiculo != null)
            {
                this.detalleSolicitudForm.setVehiculo(vehiculo);
                this.detalleSolicitudForm.setRenderTarjetaCirculacion(true);
                if (this.detalleSolicitudForm.getVehiculo().getVigenciapoliza().before(this.detalleSolicitudForm.getFechaInicio()))
                {
                    this.messageService.addWarn("Poliza vencida", "Su poliza de seguro esta vencida, favor de actualizarla");
                    this.detalleSolicitudForm.getVehiculo().setVigenciapoliza(null);
                    this.detalleSolicitudForm.setRenderPolizaSeguro(false);
                }
                else
                {
                    this.detalleSolicitudForm.setRenderPolizaSeguro(true);
                }
            }
            else
            {
                String placa = this.detalleSolicitudForm.getVehiculo().getPlaca();
                this.detalleSolicitudForm.setVehiculo(new Dvehiculo());
                this.detalleSolicitudForm.getVehiculo().setPlaca(placa);
                this.detalleSolicitudForm.setRenderPolizaSeguro(false);
                this.detalleSolicitudForm.setRenderTarjetaCirculacion(false);
                this.messageService.addWarn("No existe", "No se ha encontrado un registro anterior, registra los datos solicitados");
            }
            Calendar cal = Calendar.getInstance();
            if (this.detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_MATERIAL)
            {
                this.detalleSolicitudForm.getSolicitud().setMotivoingresoid(this.motivoIngresoDao.findById(detalleSolicitudForm.getSolicitud().getMotivoingresoid().getMotivoingresoid()));
                cal.add(Calendar.DAY_OF_YEAR, 1);
                this.detalleSolicitudForm.getSolicitud().setFechainicio(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, detalleSolicitudForm.getSolicitud().getMotivoingresoid().getDiasvigencia());
                this.detalleSolicitudForm.getSolicitud().setFechafin(cal.getTime());
                this.detalleSolicitudForm.getDetallevehiculo().setMotivoingreso("Entrada y/o salida de materiales");
            }
            else
            {
                cal.add(Calendar.DAY_OF_YEAR, 2);
                this.detalleSolicitudForm.getSolicitud().setFechainicio(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 90);
                this.detalleSolicitudForm.getSolicitud().setFechafin(cal.getTime());
            }

            this.detalleSolicitudForm.setRenderDetalleVehiculo(true);
            this.detalleSolicitudForm.setRenderDetalleMaterial(false);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void quitarMaterial(int index)
    {
        if (this.detalleSolicitudForm.getMateriales().get(index).getMaterial().getDetallematerialid() != null)
        {
            this.detalleSolicitudForm.getMateriales().get(index).getMaterial().setActivo(Constant.INACTIVO);
        }
        else
        {
            this.detalleSolicitudForm.getMateriales().remove(index);
        }
    }

    public void limpiarMaterial()
    {
        this.detalleSolicitudForm.setItemMaterial(new DetalleMaterialImagen());
        this.detalleSolicitudForm.setRenderDetalleMaterial(false);
    }

    public void uploadFotografia(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setImagenFotografia(event.getFile());
    }

    public void uploadIdentificacion(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfIdentificacion(event.getFile());
    }

    public void uploadPolizaSeguroMedico(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfPolizaSeguroMedico(event.getFile());
    }

    public void uploadLicencia(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfLicencia(event.getFile());
    }

    public void uploadPolizaSeguroVehiculo(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfPolizaSeguroVehiculo(event.getFile());
    }

    public void uploadTarjetaCirculacion(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfTarjetaCirculacion(event.getFile());
    }

    public void uploadImagenMaterial(FileUploadEvent event)
    {
        this.detalleSolicitudForm.getItemMaterial().setImagen(event.getFile());
    }

    public void uploadFacturaMaterial(FileUploadEvent event)
    {
        this.detalleSolicitudForm.getItemMaterial().setFactura(event.getFile());
    }

    public void uploadAprobacion(FileUploadEvent event)
    {
        this.detalleSolicitudForm.setPdfAprobacion(event.getFile());
    }

    public void descargarMaterial(Ddetallematerial mat, int tipo)
    {
        try
        {
            String fileName = this.solicitudService.downloadFile(tipo == 1 ? mat.getDriveimagen() : mat.getDrivefactura());
            this.detalleSolicitudForm.setDownloadFileName(fileName);
            this.detalleSolicitudForm.setRenderDescarga(true);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error:", e);
        }
    }

    public void descargarArchivo(int tipoArchivo)
    {
        try
        {
            String driveID = "";
            switch (tipoArchivo)
            {
                case 1:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallepersonal().getDrivefoto();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getPersonal().getDrivefoto();
                    }
                    break;
                case 2:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallepersonal().getDriveidentificacion();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getPersonal().getDriveidentificacion();
                    }
                    break;
                case 3:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallepersonal().getDriveseguro();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getPersonal().getDriveseguro();
                    }
                    break;
                case 4:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallepersonal().getDrivelicencia();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getPersonal().getDrivelicencia();
                    }
                    break;
                case 5:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallevehiculo().getDrivepoliza();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getVehiculo().getDrivepoliza();
                    }
                    break;
                case 6:
                    if (this.detalleSolicitudForm.getNumSolicitud() > 0)
                    {
                        driveID = this.detalleSolicitudForm.getDetallevehiculo().getDrivetarjetacirculacion();
                    }
                    else
                    {
                        driveID = this.detalleSolicitudForm.getVehiculo().getDrivetarjetacirculacion();
                    }
                    break;
                case 7:
                    driveID = this.detalleSolicitudForm.getSolicitud().getDriveaprobacion();
                    break;

            }
            String fileName = this.solicitudService.downloadFile(driveID);
            this.detalleSolicitudForm.setDownloadFileName(fileName);
            this.detalleSolicitudForm.setRenderDescarga(true);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error:", e);
        }
    }

    public void registrarSolicitud()
    {
        try
        {
            boolean validacion = true;
            if (this.detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_PERSONAL)
            {
                if (!this.detalleSolicitudForm.isRenderFoto()
                        && (this.detalleSolicitudForm.getImagenFotografia() == null || this.detalleSolicitudForm.getImagenFotografia().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la fotografia", "");
                }
                if (!this.detalleSolicitudForm.isRenderIdentificacion()
                        && (this.detalleSolicitudForm.getPdfIdentificacion() == null || this.detalleSolicitudForm.getPdfIdentificacion().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la identificación", "");
                }
                if (detalleSolicitudForm.getPersonal().getTiposeguro().equals("Seguro de Gastos Médicos")
                        && !this.detalleSolicitudForm.isRenderSeguro()
                        && (this.detalleSolicitudForm.getPdfPolizaSeguroMedico() == null || this.detalleSolicitudForm.getPdfPolizaSeguroMedico().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la poliza del seguro medico", "");
                }
                if (detalleSolicitudForm.getDetallepersonal().getRequierechofer() == 1
                        && !this.detalleSolicitudForm.isRenderLicencia()
                        && (this.detalleSolicitudForm.getPdfLicencia() == null || this.detalleSolicitudForm.getPdfLicencia().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la licencia de manejo", "");
                }
                if (validacion)
                {
                    this.detalleSolicitudForm.getSolicitud().setMotivoingresoid(null);
                    this.solicitudService.saveSolicitudPersonal(this.detalleSolicitudForm);
                    this.messageService.addInfo("Solicitud Creada", "Se ha generado con exito la solicitud de ingreso de personal");

                }
            }
            else
            {
                if (!this.detalleSolicitudForm.isRenderPolizaSeguro()
                        && (this.detalleSolicitudForm.getPdfPolizaSeguroVehiculo() == null || this.detalleSolicitudForm.getPdfPolizaSeguroVehiculo().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la poliza de seguro", "");
                }
                if (!this.detalleSolicitudForm.isRenderTarjetaCirculacion()
                        && (this.detalleSolicitudForm.getPdfTarjetaCirculacion() == null || this.detalleSolicitudForm.getPdfTarjetaCirculacion().getFileName().equals("")))
                {
                    validacion = false;
                    this.messageService.addError("Debes adjuntar la tarjeta de circulacion", "");
                }
                if (validacion)
                {
                    if (this.detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_VEHICULO)
                    {
                        this.detalleSolicitudForm.getSolicitud().setMotivoingresoid(null);
                        this.solicitudService.saveSolicitudVehiculo(this.detalleSolicitudForm);
                        this.messageService.addInfo("Solicitud Creada", "Se ha generado con exito la solicitud de ingreso de vehiculo");
                    }
                    else
                    {
                        this.solicitudService.saveSolicitudMateriales(this.detalleSolicitudForm);
                        this.messageService.addInfo("Solicitud Creada", "Se ha generado con exito la solicitud para materiales");
                    }
                }
            }
            if (validacion)
            {
                this.mostrarPDFSolicitud();
                this.detalleSolicitudForm.setRevisarPDF(false);
            }
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void mostrarPDFSolicitud()
    {
        try
        {
            this.detalleSolicitudForm.setRevisarPDF(true);
            this.detalleSolicitudForm.setKeyPDF(this.entityServiceComponent.addEntity(this.detalleSolicitudForm));
            this.detalleSolicitudForm.setRenderPDF(true);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void descargarDocumentos()
    {
        try
        {
            this.detalleSolicitudForm.setKeyPDF(this.solicitudService.descargarDocumentos(this.detalleSolicitudForm.getSolicitud()));
            this.detalleSolicitudForm.setRenderDescargaZip(true);
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void revisarSolicitud()
    {
        try
        {
            if (this.detalleSolicitudForm.getTipoRevision() == 1)
            {
                this.detalleSolicitudForm.getSolicitud().setEstatussolicitudid(new Cestatussolicitud(Constant.ID_ESTATUS_SOLICITUD_APROBADA));
                if (this.detalleSolicitudForm.getPdfAprobacion() != null && !this.detalleSolicitudForm.getPdfAprobacion().getFileName().equals(""))
                {
                    this.detalleSolicitudForm.getSolicitud().setDriveaprobacion(
                            this.solicitudService.saveDocumentoAprobacion(
                                    this.detalleSolicitudForm.getSolicitud(),
                                    this.detalleSolicitudForm.getPdfAprobacion()
                            ));
                }
                if (this.detalleSolicitudForm.getUsuario().getTipousuarioid().getTipousuarioid() == Constant.ID_TIPO_USUARIO_ADMINISTRACION)
                {
                    this.detalleSolicitudForm.getSolicitud().setAprobacionfinal(Constant.ACTIVO);
                }
                this.messageService.addInfo("Aprobada", "La solicitud se ha aprobado con exito");
            }
            else
            {
                this.detalleSolicitudForm.getSolicitud().setEstatussolicitudid(new Cestatussolicitud(Constant.ID_ESTATUS_SOLICITUD_CANCELADA));
                this.messageService.addInfo("Rechazada", "La solicitud se ha rechazado con exito");
            }
            this.detalleSolicitudForm.getSolicitud().setUltimousuariorevisaid(this.detalleSolicitudForm.getUsuario());
            this.solicitudService.saveOrUpdate(this.detalleSolicitudForm.getSolicitud());

            this.detalleSolicitudForm.getRevisionsolicitud().setFecharevision(Util.getFechaActual());
            this.detalleSolicitudForm.getRevisionsolicitud().setPersid(this.detalleSolicitudForm.getUsuario());
            this.detalleSolicitudForm.getRevisionsolicitud().setSolicitudid(this.detalleSolicitudForm.getSolicitud());
            this.revisionSolicitudDao.saveOrUpdate(this.detalleSolicitudForm.getRevisionsolicitud());
            this.detalleSolicitudForm = new DetalleSolicitudForm();
            this.init();
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void subirAprobacion()
    {
        try
        {
            this.detalleSolicitudForm.getSolicitud().setDriveaprobacion(
                    this.solicitudService.saveDocumentoAprobacion(
                            this.detalleSolicitudForm.getSolicitud(),
                            this.detalleSolicitudForm.getPdfAprobacion()
                    ));

            this.solicitudService.saveOrUpdate(this.detalleSolicitudForm.getSolicitud());
            this.messageService.addInfo("Actualizado", "Se ha actualizado el documento de aprobacion de la solicitud con exito");
            this.detalleSolicitudForm = new DetalleSolicitudForm();
            this.init();
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }
    }

    public void revocarAcceso()
    {
        try
        {
            this.detalleSolicitudForm.getSolicitud().setEstatussolicitudid(new Cestatussolicitud(Constant.ID_ESTATUS_SOLICITUD_CANCELADA));
            this.solicitudService.saveOrUpdate(this.detalleSolicitudForm.getSolicitud());
            this.messageService.addWarn("Permiso Revocado", "Se ha revocado el permiso de acceso de forma exitosa");
            this.detalleSolicitudForm = new DetalleSolicitudForm();
            this.init();
        }
        catch (Exception e)
        {
            log.error("Ocurrio un error: ", e);
        }

        try
        {
            List<FileSystemResource> archivo = new ArrayList<FileSystemResource>();
            if (this.detalleSolicitudForm.getSolicitud().getDriveaprobacion() != null)
            {
                this.descargarArchivo(7);
                archivo.add(new FileSystemResource(new File(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + "/" + this.detalleSolicitudForm.getDownloadFileName())));
            }
            this.envioCorreoService.enviarMensaje(
                    "Revocación de acceso folio: " + this.detalleSolicitudForm.getSolicitud().getSolicitudid(),
                    "Se ha revocado el permiso que se adjunta al presente correo \n"
                    + this.detalleSolicitudForm.getObservacionesRevocacion(),
                    "agustin.sandoval@tpp.com.mx"
                    , "", "", archivo, false);
        }
        catch (Exception e)
        {
            this.log.error("error al enviar facturacion: ", e);
        }
    }

    public void cerrarPDF()
    {
        try
        {
            this.detalleSolicitudForm.setRenderPDF(false);
            if (!this.detalleSolicitudForm.isRevisarPDF())
            {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/webapp/private/solicitud/detalle-solicitud.jsf");
            }
        }
        catch (Exception e)
        {
            this.log.error("error al enviar facturacion: ", e);
        }
    }

    public void cerrarDescarga()
    {
        this.detalleSolicitudForm.setRenderDescarga(false);
    }

    public void cerrarDescargaZip()
    {
        this.detalleSolicitudForm.setRenderDescargaZip(false);
    }

    /**
     * @return the detalleSolicitudForm
     */
    public DetalleSolicitudForm getDetalleSolicitudForm()
    {
        return detalleSolicitudForm;
    }

    /**
     * @param detalleSolicitudForm the detalleSolicitudForm to set
     */
    public void setDetalleSolicitudForm(DetalleSolicitudForm detalleSolicitudForm)
    {
        this.detalleSolicitudForm = detalleSolicitudForm;
    }
}
