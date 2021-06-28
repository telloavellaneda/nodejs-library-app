/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import mx.com.eai.tpp.dao.DetalleMaterialDao;
import mx.com.eai.tpp.dao.DriveDao;
import mx.com.eai.tpp.dao.MotivoIngresoDao;
import mx.com.eai.tpp.dao.PersonalDao;
import mx.com.eai.tpp.dao.RevisionSolicitudDao;
import mx.com.eai.tpp.dao.SolicitudDao;
import mx.com.eai.tpp.dao.UsuarioDao;
import mx.com.eai.tpp.dao.VehiculoDao;
import mx.com.eai.tpp.form.DetalleSolicitudForm;
import mx.com.eai.tpp.model.Cdrive;
import mx.com.eai.tpp.model.Ddetallematerial;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.model.subclases.DetalleMaterialImagen;
import mx.com.eai.tpp.util.Constant;
import mx.com.eai.tpp.util.Util;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:59:04  Description: 
 * SolicitudServiceImpl.java  
 * *****************************************************************************
 */
@Service
public class SolicitudServiceImpl implements SolicitudService
{

    @Resource(name = "rutas")
    private Properties rutasProperties;
    @Autowired
    private ApiDriveService apiDriveService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SolicitudDao solicitudDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private DriveDao driveDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private VehiculoDao vehiculoDao;
    @Autowired
    private MotivoIngresoDao motivoIngresoDao;
    @Autowired
    private DetalleMaterialDao detalleMaterialDao;
    @Autowired
    private RevisionSolicitudDao revisionSolicitudDao;
    @Autowired
    private EntityServiceComponent entityServiceComponent;

    public void saveOrUpdate(Dsolicitud solicitud) throws Exception
    {
        this.solicitudDao.saveOrUpdate(solicitud);
    }

    public void getDetalleSolicitud(DetalleSolicitudForm detalleSolicitudForm) throws Exception
    {
        detalleSolicitudForm.setSolicitud(this.solicitudDao.findById(detalleSolicitudForm.getNumSolicitud()));
        if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_PERSONAL)
        {
            detalleSolicitudForm.setPersonal(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid());
            detalleSolicitudForm.setRenderFoto(true);
            detalleSolicitudForm.setRenderIdentificacion(true);
            detalleSolicitudForm.setRenderSeguro(detalleSolicitudForm.getPersonal().getDriveseguro() != null);
            detalleSolicitudForm.setRenderLicencia(detalleSolicitudForm.getPersonal().getDrivelicencia() != null);
            detalleSolicitudForm.setDetallepersonal(detalleSolicitudForm.getSolicitud().getDdetallepersonal());
            detalleSolicitudForm.setRenderDetallePersonal(true);
        }
        else
        {
            detalleSolicitudForm.setVehiculo(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid());
            detalleSolicitudForm.setRenderPolizaSeguro(true);
            detalleSolicitudForm.setRenderTarjetaCirculacion(true);
            detalleSolicitudForm.setRenderDetalleVehiculo(true);
            detalleSolicitudForm.setDetallevehiculo(detalleSolicitudForm.getSolicitud().getDdetallevehiculo());

            if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_MATERIAL)
            {
                List<Ddetallematerial> materiales = this.detalleMaterialDao.materialesBySolicitud(detalleSolicitudForm.getNumSolicitud());
                for (Ddetallematerial mat : materiales)
                {
                    detalleSolicitudForm.getMateriales().add(new DetalleMaterialImagen(mat));
                }
                detalleSolicitudForm.setRenderDetalleMaterial(false);
            }
        }
        detalleSolicitudForm.setRenderDocAprobacion(
                detalleSolicitudForm.getSolicitud().getAprobacionfinal() == Constant.ACTIVO
                && detalleSolicitudForm.getSolicitud().getDriveaprobacion() != null
        );
        if (detalleSolicitudForm.getSolicitud().getEstatussolicitudid().getEstatussolicitudid() != Constant.ID_ESTATUS_SOLICITUD_PENDIENTE)
        {
            detalleSolicitudForm.setUltimaRevisionSolicitud(
                    this.revisionSolicitudDao.getRevisionByUsuario(
                            detalleSolicitudForm.getSolicitud().getSolicitudid(),
                            detalleSolicitudForm.getSolicitud().getUltimousuariorevisaid().getPersid()
                    ));
        }

    }

    public List<Dsolicitud> obtenerMisSolicitudes(Dusuario usuario) throws Exception
    {
        return this.solicitudDao.obtenerSolicitudesUsuario(usuario);
    }

    public List<Dsolicitud> obtenerSolicitudesPendientes(Dusuario usuario) throws Exception
    {
        return this.solicitudDao.obtenerSolicitudesPendientes(usuario);
    }

    public List<Dsolicitud> buscarSolicitudesByFecha(Date fechaInicio, Date fechaFin, int estatusID, Integer folio) throws Exception
    {
        if (folio != null && folio != 0)
        {
            List<Dsolicitud> list = new ArrayList<Dsolicitud>();
            list.add(this.solicitudDao.findById(folio));
            return list;
        }
        else
        {
            return this.solicitudDao.obtenerSolicitudesByFecha(fechaInicio, fechaFin, estatusID, this.sessionService.getUsuarioFromSession());
        }
    }

    public List<Dsolicitud> buscarSolicitudesRevisadasByFecha(Date fechaInicio, Date fechaFin, int estatusID, Integer folio) throws Exception
    {
        if (folio != null && folio != 0)
        {
            List<Dsolicitud> list = new ArrayList<Dsolicitud>();
            Dsolicitud s = this.solicitudDao.buscarSolicitudByFolioAndUsuarioRevisa(folio, this.sessionService.getUsuarioFromSession());
            if (s != null)
            {
                list.add(s);
            }
            return list;
        }
        else
        {
            return this.solicitudDao.buscarSolicitudesRevisadasByFecha(fechaInicio, fechaFin, estatusID, this.sessionService.getUsuarioFromSession());
        }
    }

    public String downloadFile(String driveID) throws Exception
    {
        this.apiDriveService.getDriveService();
        return this.apiDriveService.downloadFile(driveID);
    }

    public void saveSolicitudPersonal(DetalleSolicitudForm detalleSolicitudForm) throws Exception
    {
        Cdrive drive = this.driveDao.getDrive();
        this.apiDriveService.getDriveService();

        Dusuario usuario = this.sessionService.getUsuarioFromSession();
        if (usuario.getDrivefolder() == null)
        {
            usuario.setDrivefolder(this.apiDriveService.createFolder(usuario.getUsuario(), drive.getSolicitudes()));
            usuarioDao.saveOrUpdate(usuario);
        }

        detalleSolicitudForm.getSolicitud().setFecharegistro(Util.getFechaActual());
        detalleSolicitudForm.getSolicitud().setPersid(usuario);
        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());

        if (detalleSolicitudForm.getSolicitud().getDrivefolder() == null)
        {
            detalleSolicitudForm.getSolicitud().setDrivefolder(this.apiDriveService.createFolder("Sol-PER-" + detalleSolicitudForm.getSolicitud().getSolicitudid(), usuario.getDrivefolder()));
        }

        if (detalleSolicitudForm.getPersonal().getDrivefolder() == null)
        {
            detalleSolicitudForm.getPersonal().setDrivefolder(this.apiDriveService.createFolder(detalleSolicitudForm.getPersonal().getNombre() + " " + detalleSolicitudForm.getPersonal().getApaterno(), drive.getPersonal()));
        }

        if (detalleSolicitudForm.getImagenFotografia() != null && !detalleSolicitudForm.getImagenFotografia().getFileName().equals(""))
        {
            detalleSolicitudForm.getPersonal().setDrivefoto(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getImagenFotografia(),
                    Constant.NOMBRE_ARCHIVO_FOTOGRAFIA,
                    detalleSolicitudForm.getPersonal().getDrivefoto(),
                    detalleSolicitudForm.getPersonal().getDrivefolder()
            ));
        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getImagenFotografia() != null && !detalleSolicitudForm.getImagenFotografia().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallepersonal().setDrivefoto(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getPersonal().getDrivefoto(),
                    detalleSolicitudForm.getDetallepersonal().getDrivefoto(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        if (detalleSolicitudForm.getPdfIdentificacion() != null && !detalleSolicitudForm.getPdfIdentificacion().getFileName().equals(""))
        {
            detalleSolicitudForm.getPersonal().setDriveidentificacion(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getPdfIdentificacion(),
                    Constant.NOMBRE_ARCHIVO_IDENTIFICACION,
                    detalleSolicitudForm.getPersonal().getDriveidentificacion(),
                    detalleSolicitudForm.getPersonal().getDrivefolder()
            ));

        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfIdentificacion() != null && !detalleSolicitudForm.getPdfIdentificacion().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallepersonal().setDriveidentificacion(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getPersonal().getDriveidentificacion(),
                    detalleSolicitudForm.getDetallepersonal().getDriveidentificacion(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        if (detalleSolicitudForm.getPersonal().getTiposeguro().equals("Seguro de Gastos Médicos"))
        {
            if (detalleSolicitudForm.getPdfPolizaSeguroMedico() != null && !detalleSolicitudForm.getPdfPolizaSeguroMedico().getFileName().equals(""))
            {
                detalleSolicitudForm.getPersonal().setDriveseguro(this.apiDriveService.uploadFile(
                        detalleSolicitudForm.getPdfPolizaSeguroMedico(),
                        Constant.NOMBRE_ARCHIVO_SEGURO_MEDICO,
                        detalleSolicitudForm.getPersonal().getDriveseguro(),
                        detalleSolicitudForm.getPersonal().getDrivefolder()
                ));
            }
            if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfPolizaSeguroMedico() != null && !detalleSolicitudForm.getPdfPolizaSeguroMedico().getFileName().equals("")))
            {
                detalleSolicitudForm.getDetallepersonal().setDriveseguro(this.apiDriveService.copyFile(
                        detalleSolicitudForm.getPersonal().getDriveseguro(),
                        detalleSolicitudForm.getDetallepersonal().getDriveseguro(),
                        detalleSolicitudForm.getSolicitud().getDrivefolder()
                ));
            }
        }
        else if (detalleSolicitudForm.getDetallepersonal().getDriveseguro() != null)
        {
            this.apiDriveService.deleteFile(detalleSolicitudForm.getDetallepersonal().getDriveseguro());
        }

        if (detalleSolicitudForm.getDetallepersonal().getRequierechofer() == 1)
        {
            if (detalleSolicitudForm.getPdfLicencia() != null && !detalleSolicitudForm.getPdfLicencia().getFileName().equals(""))
            {
                detalleSolicitudForm.getPersonal().setDrivelicencia(this.apiDriveService.uploadFile(
                        detalleSolicitudForm.getPdfLicencia(),
                        Constant.NOMBRE_ARCHIVO_LICENCIA,
                        detalleSolicitudForm.getPersonal().getDrivelicencia(),
                        detalleSolicitudForm.getPersonal().getDrivefolder()
                ));
            }
            if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfLicencia() != null && !detalleSolicitudForm.getPdfLicencia().getFileName().equals("")))
            {
                detalleSolicitudForm.getDetallepersonal().setDrivelicencia(this.apiDriveService.copyFile(
                        detalleSolicitudForm.getPersonal().getDrivelicencia(),
                        detalleSolicitudForm.getDetallepersonal().getDrivelicencia(),
                        detalleSolicitudForm.getSolicitud().getDrivefolder()
                ));
            }

        }
        else if (detalleSolicitudForm.getDetallepersonal().getDrivelicencia() != null)
        {
            this.apiDriveService.deleteFile(detalleSolicitudForm.getDetallepersonal().getDrivelicencia());
        }

        this.personalDao.saveOrUpdate(detalleSolicitudForm.getPersonal());

        detalleSolicitudForm.getDetallepersonal().setTiposeguro(detalleSolicitudForm.getPersonal().getTiposeguro());
        detalleSolicitudForm.getDetallepersonal().setNumseguro(detalleSolicitudForm.getPersonal().getNumseguro());
        detalleSolicitudForm.getDetallepersonal().setDsolicitud(detalleSolicitudForm.getSolicitud());
        detalleSolicitudForm.getDetallepersonal().setPersonalid(detalleSolicitudForm.getPersonal());
        detalleSolicitudForm.getDetallepersonal().setVigenciaseguro(detalleSolicitudForm.getPersonal().getVigenciaseguro());
        detalleSolicitudForm.getDetallepersonal().setVigencialicenciamanejo(detalleSolicitudForm.getPersonal().getVigencialicenciamanejo());
        detalleSolicitudForm.getSolicitud().setDdetallepersonal(detalleSolicitudForm.getDetallepersonal());

        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());
    }

    public void saveSolicitudVehiculo(DetalleSolicitudForm detalleSolicitudForm) throws Exception
    {
        Cdrive drive = this.driveDao.getDrive();
        this.apiDriveService.getDriveService();

        Dusuario usuario = this.sessionService.getUsuarioFromSession();
        if (usuario.getDrivefolder() == null)
        {
            usuario.setDrivefolder(this.apiDriveService.createFolder(usuario.getUsuario(), drive.getSolicitudes()));
            usuarioDao.saveOrUpdate(usuario);
        }
        
        detalleSolicitudForm.getSolicitud().setFecharegistro(Util.getFechaActual());
        detalleSolicitudForm.getSolicitud().setPersid(usuario);
        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());

        if (detalleSolicitudForm.getSolicitud().getDrivefolder() == null)
        {
            detalleSolicitudForm.getSolicitud().setDrivefolder(this.apiDriveService.createFolder("Sol-VEH-" + detalleSolicitudForm.getSolicitud().getSolicitudid(), usuario.getDrivefolder()));
        }
        if (detalleSolicitudForm.getVehiculo().getDrivefolder() == null)
        {
            detalleSolicitudForm.getVehiculo().setDrivefolder(this.apiDriveService.createFolder(detalleSolicitudForm.getVehiculo().getPlaca(), drive.getVehiculos()));
        }

        if (detalleSolicitudForm.getPdfPolizaSeguroVehiculo() != null && !detalleSolicitudForm.getPdfPolizaSeguroVehiculo().getFileName().equals(""))
        {
            detalleSolicitudForm.getVehiculo().setDrivepoliza(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getPdfPolizaSeguroVehiculo(),
                    Constant.NOMBRE_ARCHIVO_SEGURO_VEHICULO,
                    detalleSolicitudForm.getVehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getVehiculo().getDrivefolder()
            ));
        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfPolizaSeguroVehiculo() != null && !detalleSolicitudForm.getPdfPolizaSeguroVehiculo().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallevehiculo().setDrivepoliza(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getVehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getDetallevehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        if (detalleSolicitudForm.getPdfTarjetaCirculacion() != null && !detalleSolicitudForm.getPdfTarjetaCirculacion().getFileName().equals(""))
        {
            detalleSolicitudForm.getVehiculo().setDrivetarjetacirculacion(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getPdfTarjetaCirculacion(),
                    Constant.NOMBRE_ARCHIVO_TARETA_CIRCULACION,
                    detalleSolicitudForm.getVehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getVehiculo().getDrivefolder()
            ));
        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfTarjetaCirculacion() != null && !detalleSolicitudForm.getPdfTarjetaCirculacion().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallevehiculo().setDrivetarjetacirculacion(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getVehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getDetallevehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        this.vehiculoDao.saveOrUpdate(detalleSolicitudForm.getVehiculo());

        detalleSolicitudForm.getDetallevehiculo().setNumpoliza(detalleSolicitudForm.getVehiculo().getNumpoliza());
        detalleSolicitudForm.getDetallevehiculo().setVigenciapoliza(detalleSolicitudForm.getVehiculo().getVigenciapoliza());
        detalleSolicitudForm.getDetallevehiculo().setDsolicitud(detalleSolicitudForm.getSolicitud());
        detalleSolicitudForm.getDetallevehiculo().setVehiculoid(detalleSolicitudForm.getVehiculo());
        detalleSolicitudForm.getSolicitud().setDdetallevehiculo(detalleSolicitudForm.getDetallevehiculo());

        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());
    }

    public void saveSolicitudMateriales(DetalleSolicitudForm detalleSolicitudForm) throws Exception
    {
        Cdrive drive = this.driveDao.getDrive();
        this.apiDriveService.getDriveService();

        Dusuario usuario = this.sessionService.getUsuarioFromSession();
        if (usuario.getDrivefolder() == null)
        {
            usuario.setDrivefolder(this.apiDriveService.createFolder(usuario.getUsuario(), drive.getSolicitudes()));
            usuarioDao.saveOrUpdate(usuario);
        }
        detalleSolicitudForm.getSolicitud().setFecharegistro(Util.getFechaActual());
        detalleSolicitudForm.getSolicitud().setPersid(usuario);
        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());

        if (detalleSolicitudForm.getSolicitud().getDrivefolder() == null)
        {
            detalleSolicitudForm.getSolicitud().setDrivefolder(this.apiDriveService.createFolder("Sol-MAT-" + detalleSolicitudForm.getSolicitud().getSolicitudid(), usuario.getDrivefolder()));
        }
        if (detalleSolicitudForm.getVehiculo().getDrivefolder() == null)
        {
            detalleSolicitudForm.getVehiculo().setDrivefolder(this.apiDriveService.createFolder(detalleSolicitudForm.getVehiculo().getPlaca(), drive.getVehiculos()));
        }

        if (detalleSolicitudForm.getPdfPolizaSeguroVehiculo() != null && !detalleSolicitudForm.getPdfPolizaSeguroVehiculo().getFileName().equals(""))
        {
            detalleSolicitudForm.getVehiculo().setDrivepoliza(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getPdfPolizaSeguroVehiculo(),
                    Constant.NOMBRE_ARCHIVO_SEGURO_VEHICULO,
                    detalleSolicitudForm.getVehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getVehiculo().getDrivefolder()
            ));
        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfPolizaSeguroVehiculo() != null && !detalleSolicitudForm.getPdfPolizaSeguroVehiculo().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallevehiculo().setDrivepoliza(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getVehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getDetallevehiculo().getDrivepoliza(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        if (detalleSolicitudForm.getPdfTarjetaCirculacion() != null && !detalleSolicitudForm.getPdfTarjetaCirculacion().getFileName().equals(""))
        {
            detalleSolicitudForm.getVehiculo().setDrivetarjetacirculacion(this.apiDriveService.uploadFile(
                    detalleSolicitudForm.getPdfTarjetaCirculacion(),
                    Constant.NOMBRE_ARCHIVO_TARETA_CIRCULACION,
                    detalleSolicitudForm.getVehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getVehiculo().getDrivefolder()
            ));
        }
        if (detalleSolicitudForm.getNumSolicitud() == 0 || (detalleSolicitudForm.getPdfTarjetaCirculacion() != null && !detalleSolicitudForm.getPdfTarjetaCirculacion().getFileName().equals("")))
        {
            detalleSolicitudForm.getDetallevehiculo().setDrivetarjetacirculacion(this.apiDriveService.copyFile(
                    detalleSolicitudForm.getVehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getDetallevehiculo().getDrivetarjetacirculacion(),
                    detalleSolicitudForm.getSolicitud().getDrivefolder()
            ));
        }

        this.vehiculoDao.saveOrUpdate(detalleSolicitudForm.getVehiculo());

        detalleSolicitudForm.getDetallevehiculo().setNumpoliza(detalleSolicitudForm.getVehiculo().getNumpoliza());
        detalleSolicitudForm.getDetallevehiculo().setVigenciapoliza(detalleSolicitudForm.getVehiculo().getVigenciapoliza());
        detalleSolicitudForm.getDetallevehiculo().setDsolicitud(detalleSolicitudForm.getSolicitud());
        detalleSolicitudForm.getDetallevehiculo().setVehiculoid(detalleSolicitudForm.getVehiculo());
        detalleSolicitudForm.getSolicitud().setDdetallevehiculo(detalleSolicitudForm.getDetallevehiculo());

        this.solicitudDao.saveOrUpdate(detalleSolicitudForm.getSolicitud());

        int i = 1;
        for (DetalleMaterialImagen mat : detalleSolicitudForm.getMateriales())
        {
            if (mat.getImagen() != null && !mat.getImagen().getFileName().equals(""))
            {
                mat.getMaterial().setDriveimagen(this.apiDriveService.uploadFile(
                        mat.getImagen(),
                        Constant.NOMBRE_ARCHIVO_MATERIAL_IMAGEN + i,
                        mat.getMaterial().getDriveimagen(),
                        detalleSolicitudForm.getSolicitud().getDrivefolder()
                ));
            }
            if (mat.getFactura() != null && !mat.getFactura().getFileName().equals(""))
            {
                mat.getMaterial().setDrivefactura(this.apiDriveService.uploadFile(
                        mat.getFactura(),
                        Constant.NOMBRE_ARCHIVO_MATERIAL_IMAGEN + i,
                        mat.getMaterial().getDrivefactura(),
                        detalleSolicitudForm.getSolicitud().getDrivefolder()
                ));
            }
            mat.getMaterial().setSolicitudid(detalleSolicitudForm.getSolicitud());
            this.detalleMaterialDao.saveOrUpdate(mat.getMaterial());
            i++;
        }
    }

    public String descargarDocumentos(Dsolicitud solicitud) throws Exception
    {
        this.apiDriveService.getDriveService();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        String fileName;
        ZipEntry entrada;
        FileInputStream fileInputStream;
        int leido;
        byte[] buffer;
        if (solicitud.getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_PERSONAL)
        {
            fileName = this.apiDriveService.downloadFile(solicitud.getDdetallepersonal().getDrivefoto());
            entrada = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entrada);
            fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
            buffer = new byte[1024];
            while (0 < (leido = fileInputStream.read(buffer)))
            {
                zipOutputStream.write(buffer, 0, leido);
            }
            fileInputStream.close();
            zipOutputStream.closeEntry();

            fileName = this.apiDriveService.downloadFile(solicitud.getDdetallepersonal().getDriveidentificacion());
            entrada = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entrada);
            fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
            buffer = new byte[1024];
            while (0 < (leido = fileInputStream.read(buffer)))
            {
                zipOutputStream.write(buffer, 0, leido);
            }
            fileInputStream.close();
            zipOutputStream.closeEntry();

            if (solicitud.getDdetallepersonal().getTiposeguro().equals("Seguro de Gastos Médicos"))
            {
                fileName = this.apiDriveService.downloadFile(solicitud.getDdetallepersonal().getDriveseguro());
                entrada = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entrada);
                fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
                buffer = new byte[1024];
                while (0 < (leido = fileInputStream.read(buffer)))
                {
                    zipOutputStream.write(buffer, 0, leido);
                }
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }

            if (solicitud.getDdetallepersonal().getRequierechofer() == 1)
            {
                fileName = this.apiDriveService.downloadFile(solicitud.getDdetallepersonal().getDrivelicencia());
                entrada = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(entrada);
                fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
                buffer = new byte[1024];
                while (0 < (leido = fileInputStream.read(buffer)))
                {
                    zipOutputStream.write(buffer, 0, leido);
                }
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
        }
        else
        {
            fileName = this.apiDriveService.downloadFile(solicitud.getDdetallevehiculo().getDrivepoliza());
            entrada = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entrada);
            fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
            buffer = new byte[1024];
            while (0 < (leido = fileInputStream.read(buffer)))
            {
                zipOutputStream.write(buffer, 0, leido);
            }
            fileInputStream.close();
            zipOutputStream.closeEntry();

            fileName = this.apiDriveService.downloadFile(solicitud.getDdetallevehiculo().getDrivetarjetacirculacion());
            entrada = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(entrada);
            fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
            buffer = new byte[1024];
            while (0 < (leido = fileInputStream.read(buffer)))
            {
                zipOutputStream.write(buffer, 0, leido);
            }
            fileInputStream.close();
            zipOutputStream.closeEntry();

            if (solicitud.getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_MATERIAL)
            {
                List<Ddetallematerial> materiales = this.detalleMaterialDao.materialesBySolicitud(solicitud.getSolicitudid());
                for (Ddetallematerial mat : materiales)
                {
                    if (mat.getDriveimagen() != null)
                    {
                        fileName = this.apiDriveService.downloadFile(mat.getDriveimagen());
                        entrada = new ZipEntry("materiales/" + fileName);
                        zipOutputStream.putNextEntry(entrada);
                        fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
                        buffer = new byte[1024];
                        while (0 < (leido = fileInputStream.read(buffer)))
                        {
                            zipOutputStream.write(buffer, 0, leido);
                        }
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    }
                    if (mat.getDrivefactura() != null)
                    {
                        fileName = this.apiDriveService.downloadFile(mat.getDrivefactura());
                        entrada = new ZipEntry("materiales/" + fileName);
                        zipOutputStream.putNextEntry(entrada);
                        fileInputStream = new FileInputStream(rutasProperties.getProperty(Constant.RUTA_ARCHIVOS_TEMPORAL) + fileName);
                        buffer = new byte[1024];
                        while (0 < (leido = fileInputStream.read(buffer)))
                        {
                            zipOutputStream.write(buffer, 0, leido);
                        }
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    }
                }
            }
        }

        zipOutputStream.close();
        return this.entityServiceComponent.addEntity(byteArrayOutputStream);
    }

    public String saveDocumentoAprobacion(Dsolicitud solicitud, UploadedFile aprobacion) throws Exception
    {
        this.apiDriveService.getDriveService();
        return this.apiDriveService.uploadFile(
                aprobacion,
                solicitud.getSolicitudid() + Constant.NOMBRE_ARCHIVO_APROBACION,
                solicitud.getDriveaprobacion(),
                solicitud.getDrivefolder()
        );
    }
}
