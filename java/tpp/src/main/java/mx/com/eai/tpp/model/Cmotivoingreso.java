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
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 16:27:28  Description: 
 * Cmotivoingreso.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "CMOTIVOINGRESO")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Cmotivoingreso.findAll", query = "SELECT c FROM Cmotivoingreso c"),
    @NamedQuery(name = "Cmotivoingreso.findByMotivoingresoid", query = "SELECT c FROM Cmotivoingreso c WHERE c.motivoingresoid = :motivoingresoid"),
    @NamedQuery(name = "Cmotivoingreso.findByDescripcion", query = "SELECT c FROM Cmotivoingreso c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Cmotivoingreso.findByDiasvigencia", query = "SELECT c FROM Cmotivoingreso c WHERE c.diasvigencia = :diasvigencia"),
    @NamedQuery(name = "Cmotivoingreso.findByActivo", query = "SELECT c FROM Cmotivoingreso c WHERE c.activo = :activo")
})
public class Cmotivoingreso implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOTIVOINGRESOID")
    private Integer motivoingresoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIASVIGENCIA")
    private Integer diasvigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private Short activo;
    @OneToMany(mappedBy = "motivoingresoid")
    private List<Dsolicitud> dsolicitudList;

    public Cmotivoingreso()
    {
    }

    public Cmotivoingreso(Integer motivoingresoid)
    {
        this.motivoingresoid = motivoingresoid;
    }

    public Cmotivoingreso(Integer motivoingresoid, String descripcion, Integer diasvigencia, Short activo)
    {
        this.motivoingresoid = motivoingresoid;
        this.descripcion = descripcion;
        this.diasvigencia = diasvigencia;
        this.activo = activo;
    }

    public Integer getMotivoingresoid()
    {
        return motivoingresoid;
    }

    public void setMotivoingresoid(Integer motivoingresoid)
    {
        this.motivoingresoid = motivoingresoid;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public Integer getDiasvigencia()
    {
        return diasvigencia;
    }

    public void setDiasvigencia(Integer diasvigencia)
    {
        this.diasvigencia = diasvigencia;
    }

    public Short getActivo()
    {
        return activo;
    }

    public void setActivo(Short activo)
    {
        this.activo = activo;
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
        hash += (motivoingresoid != null ? motivoingresoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cmotivoingreso))
        {
            return false;
        }
        Cmotivoingreso other = (Cmotivoingreso) object;
        if ((this.motivoingresoid == null && other.motivoingresoid != null) || (this.motivoingresoid != null && !this.motivoingresoid.equals(other.motivoingresoid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Cmotivoingreso[ motivoingresoid=" + motivoingresoid + " ]";
    }

}