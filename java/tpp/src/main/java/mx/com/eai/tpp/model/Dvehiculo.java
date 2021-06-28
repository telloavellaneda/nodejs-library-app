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
 * Dvehiculo.java  
 * *****************************************************************************
 */
@Entity
@Table(name = "DVEHICULO")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Dvehiculo.findAll", query = "SELECT d FROM Dvehiculo d"),
            @NamedQuery(name = "Dvehiculo.findByVehiculoid", query = "SELECT d FROM Dvehiculo d WHERE d.vehiculoid = :vehiculoid"),
            @NamedQuery(name = "Dvehiculo.findByTipo", query = "SELECT d FROM Dvehiculo d WHERE d.tipo = :tipo"),
            @NamedQuery(name = "Dvehiculo.findByMarca", query = "SELECT d FROM Dvehiculo d WHERE d.marca = :marca"),
            @NamedQuery(name = "Dvehiculo.findByModelo", query = "SELECT d FROM Dvehiculo d WHERE d.modelo = :modelo"),
            @NamedQuery(name = "Dvehiculo.findByColor", query = "SELECT d FROM Dvehiculo d WHERE d.color = :color"),
            @NamedQuery(name = "Dvehiculo.findByPlaca", query = "SELECT d FROM Dvehiculo d WHERE d.placa = :placa"),
            @NamedQuery(name = "Dvehiculo.findByDescripcion", query = "SELECT d FROM Dvehiculo d WHERE d.descripcion = :descripcion"),
            @NamedQuery(name = "Dvehiculo.findByNumpoliza", query = "SELECT d FROM Dvehiculo d WHERE d.numpoliza = :numpoliza"),
            @NamedQuery(name = "Dvehiculo.findByVigenciapoliza", query = "SELECT d FROM Dvehiculo d WHERE d.vigenciapoliza = :vigenciapoliza"),
            @NamedQuery(name = "Dvehiculo.findByDrivetarjetacirculacion", query = "SELECT d FROM Dvehiculo d WHERE d.drivetarjetacirculacion = :drivetarjetacirculacion"),
            @NamedQuery(name = "Dvehiculo.findByDrivepoliza", query = "SELECT d FROM Dvehiculo d WHERE d.drivepoliza = :drivepoliza")
        })
public class Dvehiculo implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "VEHICULOID")
    private Integer vehiculoid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Integer tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MODELO")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "COLOR")
    private String color;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PLACA")
    private String placa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
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
    @Size(max = 100)
    @Column(name = "DRIVETARJETACIRCULACION")
    private String drivetarjetacirculacion;
    @Size(max = 100)
    @Column(name = "DRIVEPOLIZA")
    private String drivepoliza;
    @Size(max = 100)
    @Column(name = "DRIVEFOLDER")
    private String drivefolder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculoid")
    private List<Ddetallevehiculo> ddetallevehiculoList;

    public Dvehiculo()
    {
    }

    public Dvehiculo(Integer vehiculoid)
    {
        this.vehiculoid = vehiculoid;
    }

    public Dvehiculo(Integer vehiculoid, Integer tipo, String marca, String modelo, String color, String placa, String descripcion, String numpoliza, Date vigenciapoliza, String drivetarjetacirculacion, String drivepoliza)
    {
        this.vehiculoid = vehiculoid;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.descripcion = descripcion;
        this.numpoliza = numpoliza;
        this.vigenciapoliza = vigenciapoliza;
        this.drivetarjetacirculacion = drivetarjetacirculacion;
        this.drivepoliza = drivepoliza;
    }

    public Integer getVehiculoid()
    {
        return vehiculoid;
    }

    public void setVehiculoid(Integer vehiculoid)
    {
        this.vehiculoid = vehiculoid;
    }

    public Integer getTipo()
    {
        return tipo;
    }

    public void setTipo(Integer tipo)
    {
        this.tipo = tipo;
    }

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getPlaca()
    {
        return placa;
    }

    public void setPlaca(String placa)
    {
        this.placa = placa;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getNumpoliza()
    {
        return numpoliza;
    }

    public void setNumpoliza(String numpoliza)
    {
        this.numpoliza = numpoliza;
    }

    public Date getVigenciapoliza()
    {
        return vigenciapoliza;
    }

    public void setVigenciapoliza(Date vigenciapoliza)
    {
        this.vigenciapoliza = vigenciapoliza;
    }

    public String getDrivetarjetacirculacion()
    {
        return drivetarjetacirculacion;
    }

    public void setDrivetarjetacirculacion(String drivetarjetacirculacion)
    {
        this.drivetarjetacirculacion = drivetarjetacirculacion;
    }

    public String getDrivepoliza()
    {
        return drivepoliza;
    }

    public void setDrivepoliza(String drivepoliza)
    {
        this.drivepoliza = drivepoliza;
    }

    @XmlTransient
    public List<Ddetallevehiculo> getDdetallevehiculoList()
    {
        return ddetallevehiculoList;
    }

    public void setDdetallevehiculoList(List<Ddetallevehiculo> ddetallevehiculoList)
    {
        this.ddetallevehiculoList = ddetallevehiculoList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (vehiculoid != null ? vehiculoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dvehiculo))
        {
            return false;
        }
        Dvehiculo other = (Dvehiculo) object;
        if ((this.vehiculoid == null && other.vehiculoid != null) || (this.vehiculoid != null && !this.vehiculoid.equals(other.vehiculoid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Dvehiculo[ vehiculoid=" + vehiculoid + " ]";
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
