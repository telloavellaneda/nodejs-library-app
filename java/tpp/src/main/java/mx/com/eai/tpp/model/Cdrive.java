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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 21:42:40  Description: 
 * Cdrive.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "CDRIVE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Cdrive.findAll", query = "SELECT c FROM Cdrive c"),
    @NamedQuery(name = "Cdrive.findById", query = "SELECT c FROM Cdrive c WHERE c.id = :id"),
    @NamedQuery(name = "Cdrive.findByPersonal", query = "SELECT c FROM Cdrive c WHERE c.personal = :personal"),
    @NamedQuery(name = "Cdrive.findByVehiculos", query = "SELECT c FROM Cdrive c WHERE c.vehiculos = :vehiculos")
})
public class Cdrive implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "SOLICITUDES")
    private String solicitudes;
    @Size(max = 200)
    @Column(name = "PERSONAL")
    private String personal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "VEHICULOS")
    private String vehiculos;

    public Cdrive()
    {
    }

    public Cdrive(Integer id)
    {
        this.id = id;
    }

    public Cdrive(Integer id, String vehiculos)
    {
        this.id = id;
        this.vehiculos = vehiculos;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPersonal()
    {
        return personal;
    }

    public void setPersonal(String personal)
    {
        this.personal = personal;
    }

    public String getVehiculos()
    {
        return vehiculos;
    }

    public void setVehiculos(String vehiculos)
    {
        this.vehiculos = vehiculos;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cdrive))
        {
            return false;
        }
        Cdrive other = (Cdrive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Cdrive[ id=" + id + " ]";
    }

    /**
     * @return the solicitudes
     */
    public String getSolicitudes()
    {
        return solicitudes;
    }

    /**
     * @param solicitudes the solicitudes to set
     */
    public void setSolicitudes(String solicitudes)
    {
        this.solicitudes = solicitudes;
    }

}