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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:42  Description: 
 * Ctiposolicitud.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "CTIPOSOLICITUD")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Ctiposolicitud.findAll", query = "SELECT c FROM Ctiposolicitud c"),
    @NamedQuery(name = "Ctiposolicitud.findByTiposolicitudid", query = "SELECT c FROM Ctiposolicitud c WHERE c.tiposolicitudid = :tiposolicitudid"),
    @NamedQuery(name = "Ctiposolicitud.findByDescripcion", query = "SELECT c FROM Ctiposolicitud c WHERE c.descripcion = :descripcion")
})
public class Ctiposolicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPOSOLICITUDID")
    private Integer tiposolicitudid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposolicitudid")
    private List<Dsolicitud> dsolicitudList;

    public Ctiposolicitud()
    {
    }

    public Ctiposolicitud(Integer tiposolicitudid)
    {
        this.tiposolicitudid = tiposolicitudid;
    }

    public Ctiposolicitud(Integer tiposolicitudid, String descripcion)
    {
        this.tiposolicitudid = tiposolicitudid;
        this.descripcion = descripcion;
    }

    public Integer getTiposolicitudid()
    {
        return tiposolicitudid;
    }

    public void setTiposolicitudid(Integer tiposolicitudid)
    {
        this.tiposolicitudid = tiposolicitudid;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
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
        hash += (tiposolicitudid != null ? tiposolicitudid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ctiposolicitud))
        {
            return false;
        }
        Ctiposolicitud other = (Ctiposolicitud) object;
        if ((this.tiposolicitudid == null && other.tiposolicitudid != null) || (this.tiposolicitudid != null && !this.tiposolicitudid.equals(other.tiposolicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Ctiposolicitud[ tiposolicitudid=" + tiposolicitudid + " ]";
    }

}