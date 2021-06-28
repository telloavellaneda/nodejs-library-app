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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import mx.com.eai.tpp.util.Constant;
import org.hibernate.annotations.GenericGenerator;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Ddetallematerial.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "DDETALLEMATERIAL")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Ddetallematerial.findAll", query = "SELECT d FROM Ddetallematerial d"),
    @NamedQuery(name = "Ddetallematerial.findByDetallematerialid", query = "SELECT d FROM Ddetallematerial d WHERE d.detallematerialid = :detallematerialid"),
    @NamedQuery(name = "Ddetallematerial.findByCantidad", query = "SELECT d FROM Ddetallematerial d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "Ddetallematerial.findByDescripcion", query = "SELECT d FROM Ddetallematerial d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "Ddetallematerial.findByMarca", query = "SELECT d FROM Ddetallematerial d WHERE d.marca = :marca"),
    @NamedQuery(name = "Ddetallematerial.findByModelo", query = "SELECT d FROM Ddetallematerial d WHERE d.modelo = :modelo"),
    @NamedQuery(name = "Ddetallematerial.findByNumserie", query = "SELECT d FROM Ddetallematerial d WHERE d.numserie = :numserie"),
    @NamedQuery(name = "Ddetallematerial.findByPropietario", query = "SELECT d FROM Ddetallematerial d WHERE d.propietario = :propietario"),
    @NamedQuery(name = "Ddetallematerial.findByDriveimagen", query = "SELECT d FROM Ddetallematerial d WHERE d.driveimagen = :driveimagen"),
    @NamedQuery(name = "Ddetallematerial.findByActivo", query = "SELECT d FROM Ddetallematerial d WHERE d.activo = :activo")
})
public class Ddetallematerial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "DETALLEMATERIALID")
    private Integer detallematerialid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MODELO")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NUMSERIE")
    private String numserie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PROPIETARIO")
    private String propietario;
    @Basic(optional = false)
    @Column(name = "DRIVEIMAGEN")
    private String driveimagen;
    @Basic(optional = false)
    @Column(name = "DRIVEFACTURA")
    private String drivefactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private Integer activo;
    @JoinColumn(name = "SOLICITUDID", referencedColumnName = "SOLICITUDID")
    @ManyToOne(optional = false)
    private Dsolicitud solicitudid;

    public Ddetallematerial()
    {
        this.activo = Constant.ACTIVO;
    }

    public Ddetallematerial(Integer detallematerialid)
    {
        this.detallematerialid = detallematerialid;
    }

    public Ddetallematerial(Integer detallematerialid, Integer cantidad, String descripcion, String marca, String modelo, String numserie, String propietario, String uso, String driveimagen)
    {
        this.detallematerialid = detallematerialid;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.numserie = numserie;
        this.propietario = propietario;
        this.driveimagen = driveimagen;
    }

    public Integer getDetallematerialid()
    {
        return detallematerialid;
    }

    public void setDetallematerialid(Integer detallematerialid)
    {
        this.detallematerialid = detallematerialid;
    }

    public Integer getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(Integer cantidad)
    {
        this.cantidad = cantidad;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
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

    public String getNumserie()
    {
        return numserie;
    }

    public void setNumserie(String numserie)
    {
        this.numserie = numserie;
    }

    public String getPropietario()
    {
        return propietario;
    }

    public void setPropietario(String propietario)
    {
        this.propietario = propietario;
    }

    public String getDriveimagen()
    {
        return driveimagen;
    }

    public void setDriveimagen(String driveimagen)
    {
        this.driveimagen = driveimagen;
    }

    public Dsolicitud getSolicitudid()
    {
        return solicitudid;
    }

    public void setSolicitudid(Dsolicitud solicitudid)
    {
        this.solicitudid = solicitudid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (detallematerialid != null ? detallematerialid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ddetallematerial))
        {
            return false;
        }
        Ddetallematerial other = (Ddetallematerial) object;
        if ((this.detallematerialid == null && other.detallematerialid != null) || (this.detallematerialid != null && !this.detallematerialid.equals(other.detallematerialid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Ddetallematerial[ detallematerialid=" + detallematerialid + " ]";
    }

    /**
     * @return the activo
     */
    public Integer getActivo()
    {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Integer activo)
    {
        this.activo = activo;
    }

    /**
     * @return the drivefactura
     */
    public String getDrivefactura()
    {
        return drivefactura;
    }

    /**
     * @param drivefactura the drivefactura to set
     */
    public void setDrivefactura(String drivefactura)
    {
        this.drivefactura = drivefactura;
    }

}