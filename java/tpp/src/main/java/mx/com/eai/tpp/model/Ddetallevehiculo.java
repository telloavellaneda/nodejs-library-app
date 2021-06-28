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
 * Ddetallevehiculo.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "DDETALLEVEHICULO")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Ddetallevehiculo.findAll", query = "SELECT d FROM Ddetallevehiculo d"),
    @NamedQuery(name = "Ddetallevehiculo.findBySolicitudid", query = "SELECT d FROM Ddetallevehiculo d WHERE d.solicitudid = :solicitudid"),
    @NamedQuery(name = "Ddetallevehiculo.findByNombreconductor", query = "SELECT d FROM Ddetallevehiculo d WHERE d.nombreconductor = :nombreconductor"),
    @NamedQuery(name = "Ddetallevehiculo.findByNumpoliza", query = "SELECT d FROM Ddetallevehiculo d WHERE d.numpoliza = :numpoliza"),
    @NamedQuery(name = "Ddetallevehiculo.findByDrivepoliza", query = "SELECT d FROM Ddetallevehiculo d WHERE d.drivepoliza = :drivepoliza")
})
public class Ddetallevehiculo implements Serializable {
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
    @Size( max = 100)
    @Column(name = "NOMBRECONDUCTOR")
    private String nombreconductor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NUMPOLIZA")
    private String numpoliza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIGENCIAPOLIZA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigenciapoliza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DRIVEPOLIZA")
    private String drivepoliza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DRIVETARJETACIRCULACION")
    private String drivetarjetacirculacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "MOTIVOINGRESO")
    private String motivoingreso;
    @Size(max = 150)
    @Column(name = "EMPRESA")
    private String empresa;
    @JoinColumn(name = "VEHICULOID", referencedColumnName = "VEHICULOID")
    @ManyToOne(optional = false)
    private Dvehiculo vehiculoid;
    @JoinColumn(name = "SOLICITUDID", referencedColumnName = "SOLICITUDID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Dsolicitud dsolicitud;

    public Ddetallevehiculo()
    {
    }

    public Ddetallevehiculo(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public Ddetallevehiculo(Integer solicitudid, String numpoliza, String drivepoliza)
    {
        this.solicitudid = solicitudid;
        this.numpoliza = numpoliza;
        this.drivepoliza = drivepoliza;
    }

    public Integer getSolicitudid()
    {
        return solicitudid;
    }

    public void setSolicitudid(Integer solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    public String getNombreconductor()
    {
        return nombreconductor;
    }

    public void setNombreconductor(String nombreconductor)
    {
        this.nombreconductor = nombreconductor;
    }

    public String getNumpoliza()
    {
        return numpoliza;
    }

    public void setNumpoliza(String numpoliza)
    {
        this.numpoliza = numpoliza;
    }

    public String getDrivepoliza()
    {
        return drivepoliza;
    }

    public void setDrivepoliza(String drivepoliza)
    {
        this.drivepoliza = drivepoliza;
    }

    public Dvehiculo getVehiculoid()
    {
        return vehiculoid;
    }

    public void setVehiculoid(Dvehiculo vehiculoid)
    {
        this.vehiculoid = vehiculoid;
    }

    public Dsolicitud getDsolicitud()
    {
        return dsolicitud;
    }

    public void setDsolicitud(Dsolicitud dsolicitud)
    {
        this.dsolicitud = dsolicitud;
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
        if (!(object instanceof Ddetallevehiculo))
        {
            return false;
        }
        Ddetallevehiculo other = (Ddetallevehiculo) object;
        if ((this.solicitudid == null && other.solicitudid != null) || (this.solicitudid != null && !this.solicitudid.equals(other.solicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Ddetallevehiculo[ solicitudid=" + solicitudid + " ]";
    }

    /**
     * @return the drivetarjetacirculacion
     */
    public String getDrivetarjetacirculacion()
    {
        return drivetarjetacirculacion;
    }

    /**
     * @param drivetarjetacirculacion the drivetarjetacirculacion to set
     */
    public void setDrivetarjetacirculacion(String drivetarjetacirculacion)
    {
        this.drivetarjetacirculacion = drivetarjetacirculacion;
    }

    /**
     * @return the vigenciapoliza
     */
    public Date getVigenciapoliza()
    {
        return vigenciapoliza;
    }

    /**
     * @param vigenciapoliza the vigenciapoliza to set
     */
    public void setVigenciapoliza(Date vigenciapoliza)
    {
        this.vigenciapoliza = vigenciapoliza;
    }

    /**
     * @return the motivoingreso
     */
    public String getMotivoingreso()
    {
        return motivoingreso;
    }

    /**
     * @param motivoingreso the motivoingreso to set
     */
    public void setMotivoingreso(String motivoingreso)
    {
        this.motivoingreso = motivoingreso;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa()
    {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa)
    {
        this.empresa = empresa;
    }

}