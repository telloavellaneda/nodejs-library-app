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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  25-dic-2017 21:39:55  Description: 
 * Drevisionsolicitud.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "DREVISIONSOLICITUD")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Drevisionsolicitud.findAll", query = "SELECT d FROM Drevisionsolicitud d"),
    @NamedQuery(name = "Drevisionsolicitud.findByRevisionsolicitudid", query = "SELECT d FROM Drevisionsolicitud d WHERE d.revisionsolicitudid = :revisionsolicitudid"),
    @NamedQuery(name = "Drevisionsolicitud.findByFecharevision", query = "SELECT d FROM Drevisionsolicitud d WHERE d.fecharevision = :fecharevision"),
    @NamedQuery(name = "Drevisionsolicitud.findByObservaciones", query = "SELECT d FROM Drevisionsolicitud d WHERE d.observaciones = :observaciones")
})
public class Drevisionsolicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "REVISIONSOLICITUDID")
    private Integer revisionsolicitudid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAREVISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharevision;
    @Size(max = 1000)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @JoinColumn(name = "PERSID", referencedColumnName = "PERSID")
    @ManyToOne(optional = false)
    private Dusuario persid;
    @JoinColumn(name = "SOLICITUDID", referencedColumnName = "SOLICITUDID")
    @ManyToOne(optional = false)
    private Dsolicitud solicitudid;

    public Drevisionsolicitud()
    {
    }

    public Drevisionsolicitud(Integer revisionsolicitudid)
    {
        this.revisionsolicitudid = revisionsolicitudid;
    }

    public Drevisionsolicitud(Integer revisionsolicitudid, Date fecharevision)
    {
        this.revisionsolicitudid = revisionsolicitudid;
        this.fecharevision = fecharevision;
    }

    public Integer getRevisionsolicitudid()
    {
        return revisionsolicitudid;
    }

    public void setRevisionsolicitudid(Integer revisionsolicitudid)
    {
        this.revisionsolicitudid = revisionsolicitudid;
    }

    public Date getFecharevision()
    {
        return fecharevision;
    }

    public void setFecharevision(Date fecharevision)
    {
        this.fecharevision = fecharevision;
    }

    public String getObservaciones()
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    public Dusuario getPersid()
    {
        return persid;
    }

    public void setPersid(Dusuario persid)
    {
        this.persid = persid;
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
        hash += (revisionsolicitudid != null ? revisionsolicitudid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drevisionsolicitud))
        {
            return false;
        }
        Drevisionsolicitud other = (Drevisionsolicitud) object;
        if ((this.revisionsolicitudid == null && other.revisionsolicitudid != null) || (this.revisionsolicitudid != null && !this.revisionsolicitudid.equals(other.revisionsolicitudid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Drevisionsolicitud[ revisionsolicitudid=" + revisionsolicitudid + " ]";
    }

}