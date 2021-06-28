/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.com.eai.tpp.model.Cmotivoingreso;
import mx.com.eai.tpp.model.Ddetallepersonal;
import mx.com.eai.tpp.model.Ddetallevehiculo;
import mx.com.eai.tpp.model.Dpersonal;
import mx.com.eai.tpp.model.Drevisionsolicitud;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.model.Dusuario;
import mx.com.eai.tpp.model.Dvehiculo;
import mx.com.eai.tpp.model.subclases.DetalleMaterialImagen;
import mx.com.eai.tpp.util.Util;
import org.primefaces.model.UploadedFile;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:21:43  Description: 
 * DetalleSolicitudForm.java  
 * *****************************************************************************
 */
public class DetalleSolicitudForm
{
    private Dusuario usuario;
    private int numSolicitud;
    private String downloadFileName;
    private Dsolicitud solicitud;
    private Dpersonal personal;
    private Ddetallepersonal detallepersonal;
    private Dvehiculo vehiculo;
    private Ddetallevehiculo detallevehiculo;
    private Drevisionsolicitud ultimaRevisionSolicitud;
    private Drevisionsolicitud revisionsolicitud;
    private UploadedFile imagenFotografia;
    private UploadedFile pdfPolizaSeguroMedico;
    private UploadedFile pdfIdentificacion;
    private UploadedFile pdfLicencia;
    private UploadedFile pdfPolizaSeguroVehiculo;
    private UploadedFile pdfTarjetaCirculacion;
    private UploadedFile pdfAprobacion;
    private List<Cmotivoingreso> motivos;
    private List<DetalleMaterialImagen> materiales;
    private DetalleMaterialImagen itemMaterial;
    private boolean renderDetallePersonal;
    private boolean renderDetalleVehiculo;
    private boolean renderDescarga;
    private boolean renderFoto;
    private boolean renderIdentificacion;
    private boolean renderSeguro;
    private boolean renderLicencia;
    private boolean renderTarjetaCirculacion;
    private boolean renderPolizaSeguro;
    private boolean renderDetalleMaterial;
    private boolean renderPDF;
    private boolean renderDescargaZip;
    private boolean renderDocAprobacion;
    private String keyPDF;
    private boolean usuarioAdmin;
    private int tipoRevision;
    private Date fechaInicio;
    private Date fechaFin;
    private Date busquedaFechaInicio;
    private Date busquedaFechaFin;
    private int estatusSeleccionado;
    private List<Dsolicitud> solicitudes;
    private Integer folioSolicitud;
    private String observacionesRevocacion;
    private boolean soloLectura;
    private boolean revisarPDF;
    
    public DetalleSolicitudForm()
    {
        this.solicitud = new Dsolicitud();
        this.personal = new Dpersonal();
        this.vehiculo = new Dvehiculo();
        this.detallepersonal = new Ddetallepersonal();
        this.detallevehiculo = new Ddetallevehiculo();
        this.itemMaterial = new DetalleMaterialImagen();
        this.materiales = new ArrayList<DetalleMaterialImagen>();
        this.renderDetalleMaterial =  true;
        this.revisionsolicitud = new Drevisionsolicitud();
        this.busquedaFechaInicio = Util.getFechaActual();
        this.busquedaFechaFin = Util.getFechaActual();
    }

    /**
     * @return the numSolicitud
     */
    public int getNumSolicitud()
    {
        return numSolicitud;
    }

    /**
     * @param numSolicitud the numSolicitud to set
     */
    public void setNumSolicitud(int numSolicitud)
    {
        this.numSolicitud = numSolicitud;
    }

    /**
     * @return the solicitud
     */
    public Dsolicitud getSolicitud()
    {
        return solicitud;
    }

    /**
     * @param solicitud the solicitud to set
     */
    public void setSolicitud(Dsolicitud solicitud)
    {
        this.solicitud = solicitud;
    }

    /**
     * @return the imagenFotografia
     */
    public UploadedFile getImagenFotografia()
    {
        return imagenFotografia;
    }

    /**
     * @param imagenFotografia the imagenFotografia to set
     */
    public void setImagenFotografia(UploadedFile imagenFotografia)
    {
        this.imagenFotografia = imagenFotografia;
    }

    /**
     * @return the pdfPolizaSeguroMedico
     */
    public UploadedFile getPdfPolizaSeguroMedico()
    {
        return pdfPolizaSeguroMedico;
    }

    /**
     * @param pdfPolizaSeguroMedico the pdfPolizaSeguroMedico to set
     */
    public void setPdfPolizaSeguroMedico(UploadedFile pdfPolizaSeguroMedico)
    {
        this.pdfPolizaSeguroMedico = pdfPolizaSeguroMedico;
    }

    /**
     * @return the pdfIdentificacion
     */
    public UploadedFile getPdfIdentificacion()
    {
        return pdfIdentificacion;
    }

    /**
     * @param pdfIdentificacion the pdfIdentificacion to set
     */
    public void setPdfIdentificacion(UploadedFile pdfIdentificacion)
    {
        this.pdfIdentificacion = pdfIdentificacion;
    }

    /**
     * @return the pdfLicencia
     */
    public UploadedFile getPdfLicencia()
    {
        return pdfLicencia;
    }

    /**
     * @param pdfLicencia the pdfLicencia to set
     */
    public void setPdfLicencia(UploadedFile pdfLicencia)
    {
        this.pdfLicencia = pdfLicencia;
    }

    /**
     * @return the pdfPolizaSeguroVehiculo
     */
    public UploadedFile getPdfPolizaSeguroVehiculo()
    {
        return pdfPolizaSeguroVehiculo;
    }

    /**
     * @param pdfPolizaSeguroVehiculo the pdfPolizaSeguroVehiculo to set
     */
    public void setPdfPolizaSeguroVehiculo(UploadedFile pdfPolizaSeguroVehiculo)
    {
        this.pdfPolizaSeguroVehiculo = pdfPolizaSeguroVehiculo;
    }

    /**
     * @return the pdfTarjetaCirculacion
     */
    public UploadedFile getPdfTarjetaCirculacion()
    {
        return pdfTarjetaCirculacion;
    }

    /**
     * @param pdfTarjetaCirculacion the pdfTarjetaCirculacion to set
     */
    public void setPdfTarjetaCirculacion(UploadedFile pdfTarjetaCirculacion)
    {
        this.pdfTarjetaCirculacion = pdfTarjetaCirculacion;
    }

    /**
     * @return the materiales
     */
    public List<DetalleMaterialImagen> getMateriales()
    {
        return materiales;
    }

    /**
     * @param materiales the materiales to set
     */
    public void setMateriales(List<DetalleMaterialImagen> materiales)
    {
        this.materiales = materiales;
    }

    /**
     * @return the personal
     */
    public Dpersonal getPersonal()
    {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Dpersonal personal)
    {
        this.personal = personal;
    }

    /**
     * @return the vehiculo
     */
    public Dvehiculo getVehiculo()
    {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(Dvehiculo vehiculo)
    {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the renderDetallePersonal
     */
    public boolean isRenderDetallePersonal()
    {
        return renderDetallePersonal;
    }

    /**
     * @param renderDetallePersonal the renderDetallePersonal to set
     */
    public void setRenderDetallePersonal(boolean renderDetallePersonal)
    {
        this.renderDetallePersonal = renderDetallePersonal;
    }

    /**
     * @return the detallepersonal
     */
    public Ddetallepersonal getDetallepersonal()
    {
        return detallepersonal;
    }

    /**
     * @param detallepersonal the detallepersonal to set
     */
    public void setDetallepersonal(Ddetallepersonal detallepersonal)
    {
        this.detallepersonal = detallepersonal;
    }

    /**
     * @return the renderFoto
     */
    public boolean isRenderFoto()
    {
        return renderFoto;
    }

    /**
     * @param renderFoto the renderFoto to set
     */
    public void setRenderFoto(boolean renderFoto)
    {
        this.renderFoto = renderFoto;
    }

    /**
     * @return the downloadFileName
     */
    public String getDownloadFileName()
    {
        return downloadFileName;
    }

    /**
     * @param downloadFileName the downloadFileName to set
     */
    public void setDownloadFileName(String downloadFileName)
    {
        this.downloadFileName = downloadFileName;
    }

    /**
     * @return the renderDescarga
     */
    public boolean isRenderDescarga()
    {
        return renderDescarga;
    }

    /**
     * @param renderDescarga the renderDescarga to set
     */
    public void setRenderDescarga(boolean renderDescarga)
    {
        this.renderDescarga = renderDescarga;
    }

    /**
     * @return the renderIdentificacion
     */
    public boolean isRenderIdentificacion()
    {
        return renderIdentificacion;
    }

    /**
     * @param renderIdentificacion the renderIdentificacion to set
     */
    public void setRenderIdentificacion(boolean renderIdentificacion)
    {
        this.renderIdentificacion = renderIdentificacion;
    }

    /**
     * @return the renderSeguro
     */
    public boolean isRenderSeguro()
    {
        return renderSeguro;
    }

    /**
     * @param renderSeguro the renderSeguro to set
     */
    public void setRenderSeguro(boolean renderSeguro)
    {
        this.renderSeguro = renderSeguro;
    }

    /**
     * @return the renderLicencia
     */
    public boolean isRenderLicencia()
    {
        return renderLicencia;
    }

    /**
     * @param renderLicencia the renderLicencia to set
     */
    public void setRenderLicencia(boolean renderLicencia)
    {
        this.renderLicencia = renderLicencia;
    }

    /**
     * @return the detallevehiculo
     */
    public Ddetallevehiculo getDetallevehiculo()
    {
        return detallevehiculo;
    }

    /**
     * @param detallevehiculo the detallevehiculo to set
     */
    public void setDetallevehiculo(Ddetallevehiculo detallevehiculo)
    {
        this.detallevehiculo = detallevehiculo;
    }

    /**
     * @return the renderTarjetaCirculacion
     */
    public boolean isRenderTarjetaCirculacion()
    {
        return renderTarjetaCirculacion;
    }

    /**
     * @param renderTarjetaCirculacion the renderTarjetaCirculacion to set
     */
    public void setRenderTarjetaCirculacion(boolean renderTarjetaCirculacion)
    {
        this.renderTarjetaCirculacion = renderTarjetaCirculacion;
    }

    /**
     * @return the renderPolizaSeguro
     */
    public boolean isRenderPolizaSeguro()
    {
        return renderPolizaSeguro;
    }

    /**
     * @param renderPolizaSeguro the renderPolizaSeguro to set
     */
    public void setRenderPolizaSeguro(boolean renderPolizaSeguro)
    {
        this.renderPolizaSeguro = renderPolizaSeguro;
    }

    /**
     * @return the renderDetalleVehiculo
     */
    public boolean isRenderDetalleVehiculo()
    {
        return renderDetalleVehiculo;
    }

    /**
     * @param renderDetalleVehiculo the renderDetalleVehiculo to set
     */
    public void setRenderDetalleVehiculo(boolean renderDetalleVehiculo)
    {
        this.renderDetalleVehiculo = renderDetalleVehiculo;
    }

    /**
     * @return the motivos
     */
    public List<Cmotivoingreso> getMotivos()
    {
        return motivos;
    }

    /**
     * @param motivos the motivos to set
     */
    public void setMotivos(List<Cmotivoingreso> motivos)
    {
        this.motivos = motivos;
    }

    /**
     * @return the itemMaterial
     */
    public DetalleMaterialImagen getItemMaterial()
    {
        return itemMaterial;
    }

    /**
     * @param itemMaterial the itemMaterial to set
     */
    public void setItemMaterial(DetalleMaterialImagen itemMaterial)
    {
        this.itemMaterial = itemMaterial;
    }

    /**
     * @return the renderDetalleMaterial
     */
    public boolean isRenderDetalleMaterial()
    {
        return renderDetalleMaterial;
    }

    /**
     * @param renderDetalleMaterial the renderDetalleMaterial to set
     */
    public void setRenderDetalleMaterial(boolean renderDetalleMaterial)
    {
        this.renderDetalleMaterial = renderDetalleMaterial;
    }

    /**
     * @return the renderPDF
     */
    public boolean isRenderPDF()
    {
        return renderPDF;
    }

    /**
     * @param renderPDF the renderPDF to set
     */
    public void setRenderPDF(boolean renderPDF)
    {
        this.renderPDF = renderPDF;
    }

    /**
     * @return the keyPDF
     */
    public String getKeyPDF()
    {
        return keyPDF;
    }

    /**
     * @param keyPDF the keyPDF to set
     */
    public void setKeyPDF(String keyPDF)
    {
        this.keyPDF = keyPDF;
    }

    /**
     * @return the renderDescargaZip
     */
    public boolean isRenderDescargaZip()
    {
        return renderDescargaZip;
    }

    /**
     * @param renderDescargaZip the renderDescargaZip to set
     */
    public void setRenderDescargaZip(boolean renderDescargaZip)
    {
        this.renderDescargaZip = renderDescargaZip;
    }

    /**
     * @return the usuarioAdmin
     */
    public boolean isUsuarioAdmin()
    {
        return usuarioAdmin;
    }

    /**
     * @param usuarioAdmin the usuarioAdmin to set
     */
    public void setUsuarioAdmin(boolean usuarioAdmin)
    {
        this.usuarioAdmin = usuarioAdmin;
    }

    /**
     * @return the tipoRevision
     */
    public int getTipoRevision()
    {
        return tipoRevision;
    }

    /**
     * @param tipoRevision the tipoRevision to set
     */
    public void setTipoRevision(int tipoRevision)
    {
        this.tipoRevision = tipoRevision;
    }

    /**
     * @return the revisionsolicitud
     */
    public Drevisionsolicitud getRevisionsolicitud()
    {
        return revisionsolicitud;
    }

    /**
     * @param revisionsolicitud the revisionsolicitud to set
     */
    public void setRevisionsolicitud(Drevisionsolicitud revisionsolicitud)
    {
        this.revisionsolicitud = revisionsolicitud;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio()
    {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin()
    {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin)
    {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the solicitudes
     */
    public List<Dsolicitud> getSolicitudes()
    {
        return solicitudes;
    }

    /**
     * @param solicitudes the solicitudes to set
     */
    public void setSolicitudes(List<Dsolicitud> solicitudes)
    {
        this.solicitudes = solicitudes;
    }

    /**
     * @return the busquedaFechaInicio
     */
    public Date getBusquedaFechaInicio()
    {
        return busquedaFechaInicio;
    }

    /**
     * @param busquedaFechaInicio the busquedaFechaInicio to set
     */
    public void setBusquedaFechaInicio(Date busquedaFechaInicio)
    {
        this.busquedaFechaInicio = busquedaFechaInicio;
    }

    /**
     * @return the busquedaFechaFin
     */
    public Date getBusquedaFechaFin()
    {
        return busquedaFechaFin;
    }

    /**
     * @param busquedaFechaFin the busquedaFechaFin to set
     */
    public void setBusquedaFechaFin(Date busquedaFechaFin)
    {
        this.busquedaFechaFin = busquedaFechaFin;
    }

    /**
     * @return the estatusSeleccionado
     */
    public int getEstatusSeleccionado()
    {
        return estatusSeleccionado;
    }

    /**
     * @param estatusSeleccionado the estatusSeleccionado to set
     */
    public void setEstatusSeleccionado(int estatusSeleccionado)
    {
        this.estatusSeleccionado = estatusSeleccionado;
    }

    /**
     * @return the folioSolicitud
     */
    public Integer getFolioSolicitud()
    {
        return folioSolicitud;
    }

    /**
     * @param folioSolicitud the folioSolicitud to set
     */
    public void setFolioSolicitud(Integer folioSolicitud)
    {
        this.folioSolicitud = folioSolicitud;
    }

    /**
     * @return the pdfAprobacion
     */
    public UploadedFile getPdfAprobacion()
    {
        return pdfAprobacion;
    }

    /**
     * @param pdfAprobacion the pdfAprobacion to set
     */
    public void setPdfAprobacion(UploadedFile pdfAprobacion)
    {
        this.pdfAprobacion = pdfAprobacion;
    }

    /**
     * @return the renderDocAprobacion
     */
    public boolean isRenderDocAprobacion()
    {
        return renderDocAprobacion;
    }

    /**
     * @param renderDocAprobacion the renderDocAprobacion to set
     */
    public void setRenderDocAprobacion(boolean renderDocAprobacion)
    {
        this.renderDocAprobacion = renderDocAprobacion;
    }

    /**
     * @return the observacionesRevocacion
     */
    public String getObservacionesRevocacion()
    {
        return observacionesRevocacion;
    }

    /**
     * @param observacionesRevocacion the observacionesRevocacion to set
     */
    public void setObservacionesRevocacion(String observacionesRevocacion)
    {
        this.observacionesRevocacion = observacionesRevocacion;
    }

    /**
     * @return the usuario
     */
    public Dusuario getUsuario()
    {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Dusuario usuario)
    {
        this.usuario = usuario;
    }

    /**
     * @return the ultimaRevisionSolicitud
     */
    public Drevisionsolicitud getUltimaRevisionSolicitud()
    {
        return ultimaRevisionSolicitud;
    }

    /**
     * @param ultimaRevisionSolicitud the ultimaRevisionSolicitud to set
     */
    public void setUltimaRevisionSolicitud(Drevisionsolicitud ultimaRevisionSolicitud)
    {
        this.ultimaRevisionSolicitud = ultimaRevisionSolicitud;
    }

    /**
     * @return the soloLectura
     */
    public boolean isSoloLectura()
    {
        return soloLectura;
    }

    /**
     * @param soloLectura the soloLectura to set
     */
    public void setSoloLectura(boolean soloLectura)
    {
        this.soloLectura = soloLectura;
    }

    /**
     * @return the revisarPDF
     */
    public boolean isRevisarPDF()
    {
        return revisarPDF;
    }

    /**
     * @param revisarPDF the revisarPDF to set
     */
    public void setRevisarPDF(boolean revisarPDF)
    {
        this.revisarPDF = revisarPDF;
    }
}
