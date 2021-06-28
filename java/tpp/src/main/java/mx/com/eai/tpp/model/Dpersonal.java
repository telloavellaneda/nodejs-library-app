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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Dpersonal.java  
 * *****************************************************************************
 */
@Entity
@Table(name = "DPERSONAL")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Dpersonal.findAll", query = "SELECT d FROM Dpersonal d"),
            @NamedQuery(name = "Dpersonal.findByPersonalid", query = "SELECT d FROM Dpersonal d WHERE d.personalid = :personalid"),
            @NamedQuery(name = "Dpersonal.findByNombre", query = "SELECT d FROM Dpersonal d WHERE d.nombre = :nombre"),
            @NamedQuery(name = "Dpersonal.findByApaterno", query = "SELECT d FROM Dpersonal d WHERE d.apaterno = :apaterno"),
            @NamedQuery(name = "Dpersonal.findByAmaterno", query = "SELECT d FROM Dpersonal d WHERE d.amaterno = :amaterno"),
            @NamedQuery(name = "Dpersonal.findByExtranjero", query = "SELECT d FROM Dpersonal d WHERE d.extranjero = :extranjero"),
            @NamedQuery(name = "Dpersonal.findByCurp", query = "SELECT d FROM Dpersonal d WHERE d.curp = :curp"),
            @NamedQuery(name = "Dpersonal.findByTiposeguro", query = "SELECT d FROM Dpersonal d WHERE d.tiposeguro = :tiposeguro"),
            @NamedQuery(name = "Dpersonal.findByNumseguro", query = "SELECT d FROM Dpersonal d WHERE d.numseguro = :numseguro"),
            @NamedQuery(name = "Dpersonal.findByVigenciaseguro", query = "SELECT d FROM Dpersonal d WHERE d.vigenciaseguro = :vigenciaseguro"),
            @NamedQuery(name = "Dpersonal.findByDriveseguro", query = "SELECT d FROM Dpersonal d WHERE d.driveseguro = :driveseguro"),
            @NamedQuery(name = "Dpersonal.findByTiposanguineo", query = "SELECT d FROM Dpersonal d WHERE d.tiposanguineo = :tiposanguineo"),
            @NamedQuery(name = "Dpersonal.findByEmpresa", query = "SELECT d FROM Dpersonal d WHERE d.empresa = :empresa"),
            @NamedQuery(name = "Dpersonal.findByVigencialicenciamanejo", query = "SELECT d FROM Dpersonal d WHERE d.vigencialicenciamanejo = :vigencialicenciamanejo"),
            @NamedQuery(name = "Dpersonal.findByDrivelicencia", query = "SELECT d FROM Dpersonal d WHERE d.drivelicencia = :drivelicencia"),
            @NamedQuery(name = "Dpersonal.findByDriveidentificacion", query = "SELECT d FROM Dpersonal d WHERE d.driveidentificacion = :driveidentificacion"),
            @NamedQuery(name = "Dpersonal.findByDrivefoto", query = "SELECT d FROM Dpersonal d WHERE d.drivefoto = :drivefoto")
        })
public class Dpersonal implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "PERSONALID")
    private Integer personalid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APATERNO")
    private String apaterno;
    @Size(max = 50)
    @Column(name = "AMATERNO")
    private String amaterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXTRANJERO")
    private Integer extranjero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CURP")
    private String curp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOSEGURO")
    private String tiposeguro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NUMSEGURO")
    private String numseguro;
    @Column(name = "VIGENCIASEGURO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigenciaseguro;
    @Size(max = 100)
    @Column(name = "DRIVESEGURO")
    private String driveseguro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPOSANGUINEO")
    private String tiposanguineo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "EMPRESA")
    private String empresa;
    @Column(name = "VIGENCIALICENCIAMANEJO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigencialicenciamanejo;
    @Size(max = 100)
    @Column(name = "DRIVELICENCIA")
    private String drivelicencia;
    @Size(max = 100)
    @Column(name = "DRIVEIDENTIFICACION")
    private String driveidentificacion;
    @Size(max = 100)
    @Column(name = "DRIVEFOTO")
    private String drivefoto;
    @Size(max = 100)
    @Column(name = "DRIVEFOLDER")
    private String drivefolder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalid")
    private List<Ddetallepersonal> ddetallepersonalList;

    public Dpersonal()
    {
        this.extranjero = 1;
    }

    public Dpersonal(Integer personalid)
    {
        this.personalid = personalid;
    }

    public Dpersonal(Integer personalid, String nombre, String apaterno, String amaterno, Integer extranjero, String curp, String tiposeguro, String numseguro, String tiposanguineo, String empresa, String driveidentificacion, String drivefoto)
    {
        this.personalid = personalid;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.extranjero = extranjero;
        this.curp = curp;
        this.tiposeguro = tiposeguro;
        this.numseguro = numseguro;
        this.tiposanguineo = tiposanguineo;
        this.empresa = empresa;
        this.driveidentificacion = driveidentificacion;
        this.drivefoto = drivefoto;
    }

    public Integer getPersonalid()
    {
        return personalid;
    }

    public void setPersonalid(Integer personalid)
    {
        this.personalid = personalid;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApaterno()
    {
        return apaterno;
    }

    public void setApaterno(String apaterno)
    {
        this.apaterno = apaterno;
    }

    public String getAmaterno()
    {
        return amaterno;
    }

    public void setAmaterno(String amaterno)
    {
        this.amaterno = amaterno;
    }

    public Integer getExtranjero()
    {
        return extranjero;
    }

    public void setExtranjero(Integer extranjero)
    {
        this.extranjero = extranjero;
    }

    public String getCurp()
    {
        return curp;
    }

    public void setCurp(String curp)
    {
        this.curp = curp;
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

    public Date getVigenciaseguro()
    {
        return vigenciaseguro;
    }

    public void setVigenciaseguro(Date vigenciaseguro)
    {
        this.vigenciaseguro = vigenciaseguro;
    }

    public String getDriveseguro()
    {
        return driveseguro;
    }

    public void setDriveseguro(String driveseguro)
    {
        this.driveseguro = driveseguro;
    }

    public String getTiposanguineo()
    {
        return tiposanguineo;
    }

    public void setTiposanguineo(String tiposanguineo)
    {
        this.tiposanguineo = tiposanguineo;
    }

    public String getEmpresa()
    {
        return empresa;
    }

    public void setEmpresa(String empresa)
    {
        this.empresa = empresa;
    }

    public Date getVigencialicenciamanejo()
    {
        return vigencialicenciamanejo;
    }

    public void setVigencialicenciamanejo(Date vigencialicenciamanejo)
    {
        this.vigencialicenciamanejo = vigencialicenciamanejo;
    }

    public String getDrivelicencia()
    {
        return drivelicencia;
    }

    public void setDrivelicencia(String drivelicencia)
    {
        this.drivelicencia = drivelicencia;
    }

    public String getDriveidentificacion()
    {
        return driveidentificacion;
    }

    public void setDriveidentificacion(String driveidentificacion)
    {
        this.driveidentificacion = driveidentificacion;
    }

    public String getDrivefoto()
    {
        return drivefoto;
    }

    public void setDrivefoto(String drivefoto)
    {
        this.drivefoto = drivefoto;
    }

    @XmlTransient
    public List<Ddetallepersonal> getDdetallepersonalList()
    {
        return ddetallepersonalList;
    }

    public void setDdetallepersonalList(List<Ddetallepersonal> ddetallepersonalList)
    {
        this.ddetallepersonalList = ddetallepersonalList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (personalid != null ? personalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dpersonal))
        {
            return false;
        }
        Dpersonal other = (Dpersonal) object;
        if ((this.personalid == null && other.personalid != null) || (this.personalid != null && !this.personalid.equals(other.personalid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Dpersonal[ personalid=" + personalid + " ]";
    }

    /**
     * @return the drivefolder
     */
    public String getDrivefolder()
    {
        return drivefolder;
    }

    /**
     * @param drivefolder the drivefolder to set
     */
    public void setDrivefolder(String drivefolder)
    {
        this.drivefolder = drivefolder;
    }

}
