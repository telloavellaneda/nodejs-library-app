/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.form;

import java.util.Date;
import java.util.List;
import mx.com.eai.tpp.model.Dsolicitud;
import mx.com.eai.tpp.util.Util;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  27-dic-2017 20:18:30  Description: 
 * SolicitudesRevisadasForm.java  
 * *****************************************************************************
 */
public class SolicitudesRevisadasForm
{
    private Date busquedaFechaInicio;
    private Date busquedaFechaFin;
    private int estatusSeleccionado;
    private List<Dsolicitud> solicitudes;
    private Integer folioSolicitud;

    public SolicitudesRevisadasForm()
    {
        this.busquedaFechaInicio = Util.getFechaActual();
        this.busquedaFechaFin = Util.getFechaActual();
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
}
