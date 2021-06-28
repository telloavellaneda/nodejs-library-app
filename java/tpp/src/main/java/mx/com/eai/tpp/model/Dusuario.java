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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Dusuario.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "DUSUARIO")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Dusuario.findAll", query = "SELECT d FROM Dusuario d"),
    @NamedQuery(name = "Dusuario.findByPersid", query = "SELECT d FROM Dusuario d WHERE d.persid = :persid"),
    @NamedQuery(name = "Dusuario.findByDrivefolder", query = "SELECT d FROM Dusuario d WHERE d.drivefolder = :drivefolder"),
    @NamedQuery(name = "Dusuario.findByNombre", query = "SELECT d FROM Dusuario d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Dusuario.findByApaterno", query = "SELECT d FROM Dusuario d WHERE d.apaterno = :apaterno"),
    @NamedQuery(name = "Dusuario.findByAmaterno", query = "SELECT d FROM Dusuario d WHERE d.amaterno = :amaterno"),
    @NamedQuery(name = "Dusuario.findByUsuario", query = "SELECT d FROM Dusuario d WHERE d.usuario = :usuario"),
    @NamedQuery(name = "Dusuario.findByContrasenia", query = "SELECT d FROM Dusuario d WHERE d.contrasenia = :contrasenia"),
    @NamedQuery(name = "Dusuario.findByActivo", query = "SELECT d FROM Dusuario d WHERE d.activo = :activo"),
    @NamedQuery(name = "Dusuario.findByCuentabloqueada", query = "SELECT d FROM Dusuario d WHERE d.cuentabloqueada = :cuentabloqueada"),
    @NamedQuery(name = "Dusuario.findByCuentaexpirada", query = "SELECT d FROM Dusuario d WHERE d.cuentaexpirada = :cuentaexpirada"),
    @NamedQuery(name = "Dusuario.findByCredencialexpirada", query = "SELECT d FROM Dusuario d WHERE d.credencialexpirada = :credencialexpirada")
})
public class Dusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "PERSID")
    private Integer persid;
    @Size(max = 100)
    @Column(name = "DRIVEFOLDER")
    private String drivefolder;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AMATERNO")
    private String amaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONTRASENIA")
    private String contrasenia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private Integer activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUENTABLOQUEADA")
    private Integer cuentabloqueada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUENTAEXPIRADA")
    private Integer cuentaexpirada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDENCIALEXPIRADA")
    private Integer credencialexpirada;
    @Size(max = 150)
    @Column(name = "EMPRESA")
    private String empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persid")
    private List<Drevisionsolicitud> drevisionsolicitudList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persid")
    private List<Dsolicitud> dsolicitudList;
    @OneToMany(mappedBy = "usuariopadreid")
    private List<Dusuario> dusuarioList;
    @JoinColumn(name = "USUARIOPADREID", referencedColumnName = "PERSID")
    @ManyToOne
    private Dusuario usuariopadreid;
    @JoinColumn(name = "TIPOUSUARIOID", referencedColumnName = "TIPOUSUARIOID")
    @ManyToOne(optional = false)
    private Ctipousuario tipousuarioid;

    public Dusuario()
    {
        this.tipousuarioid =  new Ctipousuario();
    }

    public Dusuario(Integer persid)
    {
        this.persid = persid;
    }

    public Dusuario(Integer persid, String nombre, String apaterno, String amaterno, String usuario, String contrasenia, Integer activo, Integer cuentabloqueada, Integer cuentaexpirada, Integer credencialexpirada)
    {
        this.persid = persid;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.cuentabloqueada = cuentabloqueada;
        this.cuentaexpirada = cuentaexpirada;
        this.credencialexpirada = credencialexpirada;
    }

    public Integer getPersid()
    {
        return persid;
    }

    public void setPersid(Integer persid)
    {
        this.persid = persid;
    }

    public String getDrivefolder()
    {
        return drivefolder;
    }

    public void setDrivefolder(String drivefolder)
    {
        this.drivefolder = drivefolder;
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

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public Integer getActivo()
    {
        return activo;
    }

    public void setActivo(Integer activo)
    {
        this.activo = activo;
    }

    public Integer getCuentabloqueada()
    {
        return cuentabloqueada;
    }

    public void setCuentabloqueada(Integer cuentabloqueada)
    {
        this.cuentabloqueada = cuentabloqueada;
    }

    public Integer getCuentaexpirada()
    {
        return cuentaexpirada;
    }

    public void setCuentaexpirada(Integer cuentaexpirada)
    {
        this.cuentaexpirada = cuentaexpirada;
    }

    public Integer getCredencialexpirada()
    {
        return credencialexpirada;
    }

    public void setCredencialexpirada(Integer credencialexpirada)
    {
        this.credencialexpirada = credencialexpirada;
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

    @XmlTransient
    public List<Dsolicitud> getDsolicitudList()
    {
        return dsolicitudList;
    }

    public void setDsolicitudList(List<Dsolicitud> dsolicitudList)
    {
        this.dsolicitudList = dsolicitudList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (persid != null ? persid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dusuario))
        {
            return false;
        }
        Dusuario other = (Dusuario) object;
        if ((this.persid == null && other.persid != null) || (this.persid != null && !this.persid.equals(other.persid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Dusuario[ persid=" + persid + " ]";
    }

    @XmlTransient
    public List<Dusuario> getDusuarioList()
    {
        return dusuarioList;
    }

    public void setDusuarioList(List<Dusuario> dusuarioList)
    {
        this.dusuarioList = dusuarioList;
    }

    public Dusuario getUsuariopadreid()
    {
        return usuariopadreid;
    }

    public void setUsuariopadreid(Dusuario usuariopadreid)
    {
        this.usuariopadreid = usuariopadreid;
    }

    public Ctipousuario getTipousuarioid()
    {
        return tipousuarioid;
    }

    public void setTipousuarioid(Ctipousuario tipousuarioid)
    {
        this.tipousuarioid = tipousuarioid;
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