/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Ddetallepersonal.java  
 * *****************************************************************************
 */
@Entity
@Table(name = "DDETALLEPERSONAL")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Ddetallepersonal.findAll", query = "SELECT d FROM Ddetallepersonal d"),
            @NamedQuery(name = "Ddetallepersonal.findBySolicitudid", query = "SELECT d FROM Ddetallepersonal d WHERE d.solicitudid = :solicitudid"),
            @NamedQuery(name = "Ddetallepersonal.findByTiposeguro", query = "SELECT d FROM Ddetallepersonal d WHERE d.tiposeguro = :tiposeguro"),
            @NamedQuery(name = "Ddetallepersonal.findByNumseguro", query = "SELECT d FROM Ddetallepersonal d WHERE d.numseguro = :numseguro"),
            @NamedQuery(name = "Ddetallepersonal.findByDriveseguro", query = "SELECT d FROM Ddetallepersonal d WHERE d.driveseguro = :driveseguro"),
            @NamedQuery(name = "Ddetallepersonal.findByActividad", query = "SELECT d FROM Ddetallepersonal d WHERE d.actividad = :actividad"),
            @NamedQuery(name = "Ddetallepersonal.findByRequierechofer", query = "SELECT d FROM Ddetallepersonal d WHERE d.requierechofer = :requierechofer"),
            @NamedQuery(name = "Ddetallepersonal.findByDrivelicencia", query = "SELECT d FROM Ddetallepersonal d WHERE d.drivelicencia = :drivelicencia")
        })
public class Ddetallepersonal implements Serializable
{
    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters
            = @Parameter(name = "property", value = "dsolicitud"))
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOLICITUDID")
    private Integer solicitudid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOSEGURO")
    private String tiposeguro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NUMSEGURO")
    private String numseguro;
    @Size(max = 100)
    @Column(name = "DRIVESEGURO")
    private String driveseguro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUIERECHOFER")
    private Integer requierechofer;
    @Size(max = 100)
    @Column(name = "DRIVEIDENTIFICACION")
    private String driveidentificacion;
    @Size(max = 100)
    @Column(name = "DRIVEFOTO")
    private String drivefoto;
    @Size(max = 100)
    @Column(name = "DRIVELICENCIA")
    private String drivelicencia;
    @Column(name = "VIGENCIASEGURO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigenciaseguro;
    @Column(name = "VIGENCIALICENCIAMANEJO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigencialicenciamanejo;
    @JoinColumn(name = "SOLICITUDID", referencedColumnName = "SOLICITUDID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Dsolicitud dsolicitud;
    @JoinColumn(name = "PERSONALID", referencedColumnName = "PERSONALID")
    @ManyToOne(optional = false)
    private Dpersonal personalid;

    public Ddetallepersonal()
    {
        this.requierechofer = 2;
    }

    public Ddetallepersonal(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public Ddetallepersonal(Integer solicitudid, String tiposeguro, String numseguro, String actividad, Integer requierechofer)
    {
        this.solicitudid = solicitudid;
        this.tiposeguro = tiposeguro;
        this.numseguro = numseguro;
        this.actividad = actividad;
        this.requierechofer = requierechofer;
    }

    public Integer getSolicitudid()
    {
        return solicitudid;
    }

    public void setSolicitudid(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public String getTiposeguro()
    {
        return tiposeguro;
    }

    public void setTiposeguro(String tiposeguro)
    {
        this.tiposeguro = tiposeguro;
    }

    public String getNumseguro()
    {
        return numseguro;
    }

    public void setNumseguro(String numseguro)
    {
        this.numseguro = numseguro;
    }

    public String getDriveseguro()
    {
        return driveseguro;
    }

    public void setDriveseguro(String driveseguro)
    {
        this.driveseguro = driveseguro;
    }

    public String getActividad()
    {
        return actividad;
    }

    public void setActividad(String actividad)
    {
        this.actividad = actividad;
    }

    public Integer getRequierechofer()
    {
        return requierechofer;
    }

    public void setRequierechofer(Integer requierechofer)
    {
        this.requierechofer = requierechofer;
    }

    public String getDrivelicencia()
    {
        return drivelicencia;
    }

    public void setDrivelicencia(String drivelicencia)
    {
        this.drivelicencia = drivelicencia;
    }

    public Dsolicitud getDsolicitud()
    {
        return dsolicitud;
    }

    public void setDsolicitud(Dsolicitud dsolicitud)
    {
        this.dsolicitud = dsolicitud;
    }

    public Dpersonal getPersonalid()
    {
        return personalid;
    }

    public void setPersonalid(Dpersonal personalid)
    {
        this.personalid = personalid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (solicitudid != null ? solicitudid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ddetallepersonal))
        {
            return false;
        }
        Ddetallepersonal other = (Ddetallepersonal) object;
        if ((this.solicitudid == null && other.solicitudid != null) || (this.solicitudid != null && !this.solicitudid.equals(other.solicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Ddetallepersonal[ solicitudid=" + solicitudid + " ]";
    }

    /**
     * @return the driveidentificacion
     */
    public String getDriveidentificacion()
    {
        return driveidentificacion;
    }

    /**
     * @param driveidentificacion the driveidentificacion to set
     */
    public void setDriveidentificacion(String driveidentificacion)
    {
        this.driveidentificacion = driveidentificacion;
    }

    /**
     * @return the drivefoto
     */
    public String getDrivefoto()
    {
        return drivefoto;
    }

    /**
     * @param drivefoto the drivefoto to set
     */
    public void setDrivefoto(String drivefoto)
    {
        this.drivefoto = drivefoto;
    }

    /**
     * @return the vigenciaseguro
     */
    public Date getVigenciaseguro()
    {
        return vigenciaseguro;
    }

    /**
     * @param vigenciaseguro the vigenciaseguro to set
     */
    public void setVigenciaseguro(Date vigenciaseguro)
    {
        this.vigenciaseguro = vigenciaseguro;
    }

    /**
     * @return the vigencialicenciamanejo
     */
    public Date getVigencialicenciamanejo()
    {
        return vigencialicenciamanejo;
    }

    /**
     * @param vigencialicenciamanejo the vigencialicenciamanejo to set
     */
    public void setVigencialicenciamanejo(Date vigencialicenciamanejo)
    {
        this.vigencialicenciamanejo = vigencialicenciamanejo;
    }

}
