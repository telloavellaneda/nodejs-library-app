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
import mx.com.eai.tpp.util.Constant;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  24-nov-2017 18:33:41  Description: 
 * Cestatussolicitud.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "CESTATUSSOLICITUD")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Cestatussolicitud.findAll", query = "SELECT c FROM Cestatussolicitud c"),
    @NamedQuery(name = "Cestatussolicitud.findByEstatussolicitudid", query = "SELECT c FROM Cestatussolicitud c WHERE c.estatussolicitudid = :estatussolicitudid"),
    @NamedQuery(name = "Cestatussolicitud.findByDescripcion", query = "SELECT c FROM Cestatussolicitud c WHERE c.descripcion = :descripcion")
})
public class Cestatussolicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUSSOLICITUDID")
    private Integer estatussolicitudid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatussolicitudid")
    private List<Dsolicitud> dsolicitudList;

    public Cestatussolicitud()
    {
        this.estatussolicitudid = Constant.ID_ESTATUS_SOLICITUD_PENDIENTE;
        this.descripcion = "Pendiente";
    }

    public Cestatussolicitud(Integer estatussolicitudid)
    {
        this.estatussolicitudid = estatussolicitudid;
    }

    public Cestatussolicitud(Integer estatussolicitudid, String descripcion)
    {
        this.estatussolicitudid = estatussolicitudid;
        this.descripcion = descripcion;
    }

    public Integer getEstatussolicitudid()
    {
        return estatussolicitudid;
    }

    public void setEstatussolicitudid(Integer estatussolicitudid)
    {
        this.estatussolicitudid = estatussolicitudid;
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
        hash += (estatussolicitudid != null ? estatussolicitudid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cestatussolicitud))
        {
            return false;
        }
        Cestatussolicitud other = (Cestatussolicitud) object;
        if ((this.estatussolicitudid == null && other.estatussolicitudid != null) || (this.estatussolicitudid != null && !this.estatussolicitudid.equals(other.estatussolicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Cestatussolicitud[ estatussolicitudid=" + estatussolicitudid + " ]";
    }

}