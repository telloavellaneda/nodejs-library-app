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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import mx.com.eai.tpp.util.Constant;
import org.hibernate.annotations.GenericGenerator;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Dsolicitud.java  
 * *****************************************************************************
 */
@Entity
@Table(name = "DSOLICITUD")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Dsolicitud.findAll", query = "SELECT d FROM Dsolicitud d"),
            @NamedQuery(name = "Dsolicitud.findBySolicitudid", query = "SELECT d FROM Dsolicitud d WHERE d.solicitudid = :solicitudid"),
            @NamedQuery(name = "Dsolicitud.findByDrivefolder", query = "SELECT d FROM Dsolicitud d WHERE d.drivefolder = :drivefolder"),
            @NamedQuery(name = "Dsolicitud.findByFecharegistro", query = "SELECT d FROM Dsolicitud d WHERE d.fecharegistro = :fecharegistro"),
            @NamedQuery(name = "Dsolicitud.findByFechainicio", query = "SELECT d FROM Dsolicitud d WHERE d.fechainicio = :fechainicio"),
            @NamedQuery(name = "Dsolicitud.findByFechafin", query = "SELECT d FROM Dsolicitud d WHERE d.fechafin = :fechafin")
        })
public class Dsolicitud implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "SOLICITUDID")
    private Integer solicitudid;
    @Size(max = 100)
    @Column(name = "DRIVEFOLDER")
    private String drivefolder;
    @Size(max = 100)
    @Column(name = "DRIVEAPROBACION")
    private String driveaprobacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAREGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudid")
    private List<Ddetallematerial> ddetallematerialList;
    @JoinColumn(name = "PERSID", referencedColumnName = "PERSID")
    @ManyToOne(optional = false)
    private Dusuario persid;
    @JoinColumn(name = "TIPOSOLICITUDID", referencedColumnName = "TIPOSOLICITUDID")
    @ManyToOne(optional = false)
    private Ctiposolicitud tiposolicitudid;
    @JoinColumn(name = "ESTATUSSOLICITUDID", referencedColumnName = "ESTATUSSOLICITUDID")
    @ManyToOne(optional = false)
    private Cestatussolicitud estatussolicitudid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "dsolicitud")
    @PrimaryKeyJoinColumn
    private Ddetallepersonal ddetallepersonal;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "dsolicitud")
    @PrimaryKeyJoinColumn
    private Ddetallevehiculo ddetallevehiculo;
    @Column(name = "TIPOENTRADA")
    private Integer tipoentrada;
    @Size(max = 1000)
    @Column(name = "USO")
    private String uso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APROBACIONFINAL")
    private Integer aprobacionfinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudid")
    private List<Drevisionsolicitud> drevisionsolicitudList;
    @JoinColumn(name = "MOTIVOINGRESOID", referencedColumnName = "MOTIVOINGRESOID")
    @ManyToOne
    private Cmotivoingreso motivoingresoid;
    @JoinColumn(name = "ULTIMOUSUARIOREVISAID", referencedColumnName = "PERSID")
    @ManyToOne
    private Dusuario ultimousuariorevisaid;

    public Dsolicitud()
    {
        this.tiposolicitudid = new Ctiposolicitud();
        this.estatussolicitudid = new Cestatussolicitud();
        this.motivoingresoid = new Cmotivoingreso();
        this.aprobacionfinal = Constant.INACTIVO;
    }

    public Dsolicitud(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public Dsolicitud(Integer solicitudid, String drivefolder, Date fecharegistro, Date fechainicio, Date fechafin)
    {
        this.solicitudid = solicitudid;
        this.drivefolder = drivefolder;
        this.fecharegistro = fecharegistro;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
    }

    public Integer getSolicitudid()
    {
        return solicitudid;
    }

    public void setSolicitudid(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public String getDrivefolder()
    {
        return drivefolder;
    }

    public void setDrivefolder(String drivefolder)
    {
        this.drivefolder = drivefolder;
    }

    public Date getFecharegistro()
    {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro)
    {
        this.fecharegistro = fecharegistro;
    }

    public Date getFechainicio()
    {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio)
    {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin()
    {
        return fechafin;
    }

    public void setFechafin(Date fechafin)
    {
        this.fechafin = fechafin;
    }

    @XmlTransient
    public List<Ddetallematerial> getDdetallematerialList()
    {
        return ddetallematerialList;
    }

    public void setDdetallematerialList(List<Ddetallematerial> ddetallematerialList)
    {
        this.ddetallematerialList = ddetallematerialList;
    }

    public Dusuario getPersid()
    {
        return persid;
    }

    public void setPersid(Dusuario persid)
    {
        this.persid = persid;
    }

    public Ctiposolicitud getTiposolicitudid()
    {
        return tiposolicitudid;
    }

    public void setTiposolicitudid(Ctiposolicitud tiposolicitudid)
    {
        this.tiposolicitudid = tiposolicitudid;
    }

    public Cestatussolicitud getEstatussolicitudid()
    {
        return estatussolicitudid;
    }

    public void setEstatussolicitudid(Cestatussolicitud estatussolicitudid)
    {
        this.estatussolicitudid = estatussolicitudid;
    }

    public Ddetallepersonal getDdetallepersonal()
    {
        return ddetallepersonal;
    }

    public void setDdetallepersonal(Ddetallepersonal ddetallepersonal)
    {
        this.ddetallepersonal = ddetallepersonal;
    }

    public Ddetallevehiculo getDdetallevehiculo()
    {
        return ddetallevehiculo;
    }

    public void setDdetallevehiculo(Ddetallevehiculo ddetallevehiculo)
    {
        this.ddetallevehiculo = ddetallevehiculo;
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
        if (!(object instanceof Dsolicitud))
        {
            return false;
        }
        Dsolicitud other = (Dsolicitud) object;
        if ((this.solicitudid == null && other.solicitudid != null) || (this.solicitudid != null && !this.solicitudid.equals(other.solicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Dsolicitud[ solicitudid=" + solicitudid + " ]";
    }

    public Cmotivoingreso getMotivoingresoid()
    {
        return motivoingresoid;
    }

    public void setMotivoingresoid(Cmotivoingreso motivoingresoid)
    {
        this.motivoingresoid = motivoingresoid;
    }

    /**
     * @return the driveaprobacion
     */
    public String getDriveaprobacion()
    {
        return driveaprobacion;
    }

    /**
     * @param driveaprobacion the driveaprobacion to set
     */
    public void setDriveaprobacion(String driveaprobacion)
    {
        this.driveaprobacion = driveaprobacion;
    }

    /**
     * @return the tipoentrada
     */
    public Integer getTipoentrada()
    {
        return tipoentrada;
    }

    /**
     * @param tipoentrada the tipoentrada to set
     */
    public void setTipoentrada(Integer tipoentrada)
    {
        this.tipoentrada = tipoentrada;
    }

    @XmlTransient
    public List<Drevisionsolicitud> getDrevisionsolicitudList()
    {
        return drevisionsolicitudList;
    }

    public void setDrevisionsolicitudList(List<Drevisionsolicitud> drevisionsolicitudList)
    {
        this.drevisionsolicitudList = drevisionsolicitudList;
    }

    /**
     * @return the ultimousuariorevisaid
     */
    public Dusuario getUltimousuariorevisaid()
    {
        return ultimousuariorevisaid;
    }

    /**
     * @param ultimousuariorevisaid the ultimousuariorevisaid to set
     */
    public void setUltimousuariorevisaid(Dusuario ultimousuariorevisaid)
    {
        this.ultimousuariorevisaid = ultimousuariorevisaid;
    }

    /**
     * @return the aprobacionfinal
     */
    public Integer getAprobacionfinal()
    {
        return aprobacionfinal;
    }

    /**
     * @param aprobacionfinal the aprobacionfinal to set
     */
    public void setAprobacionfinal(Integer aprobacionfinal)
    {
        this.aprobacionfinal = aprobacionfinal;
    }

    /**
     * @return the uso
     */
    public String getUso()
    {
        return uso;
    }

    /**
     * @param uso the uso to set
     */
    public void setUso(String uso)
    {
        this.uso = uso;
    }
}
