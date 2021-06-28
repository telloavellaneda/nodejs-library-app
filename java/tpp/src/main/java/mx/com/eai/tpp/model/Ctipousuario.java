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
 * @author Ashley Heyeral Ruiz Fernandez  25-dic-2017 21:33:43  Description: 
 * Ctipousuario.java  
 * *****************************************************************************
 */

@Entity
@Table(name = "CTIPOUSUARIO")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Ctipousuario.findAll", query = "SELECT c FROM Ctipousuario c"),
    @NamedQuery(name = "Ctipousuario.findByTipousuarioid", query = "SELECT c FROM Ctipousuario c WHERE c.tipousuarioid = :tipousuarioid"),
    @NamedQuery(name = "Ctipousuario.findByDescripcion", query = "SELECT c FROM Ctipousuario c WHERE c.descripcion = :descripcion")
})
public class Ctipousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPOUSUARIOID")
    private Integer tipousuarioid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipousuarioid")
    private List<Dusuario> dusuarioList;

    public Ctipousuario()
    {
    }

    public Ctipousuario(Integer tipousuarioid)
    {
        this.tipousuarioid = tipousuarioid;
    }

    public Ctipousuario(Integer tipousuarioid, String descripcion)
    {
        this.tipousuarioid = tipousuarioid;
        this.descripcion = descripcion;
    }

    public Integer getTipousuarioid()
    {
        return tipousuarioid;
    }

    public void setTipousuarioid(Integer tipousuarioid)
    {
        this.tipousuarioid = tipousuarioid;
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
    public List<Dusuario> getDusuarioList()
    {
        return dusuarioList;
    }

    public void setDusuarioList(List<Dusuario> dusuarioList)
    {
        this.dusuarioList = dusuarioList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (tipousuarioid != null ? tipousuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ctipousuario))
        {
            return false;
        }
        Ctipousuario other = (Ctipousuario) object;
        if ((this.tipousuarioid == null && other.tipousuarioid != null) || (this.tipousuarioid != null && !this.tipousuarioid.equals(other.tipousuarioid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "mx.com.eai.tpp.model.Ctipousuario[ tipousuarioid=" + tipousuarioid + " ]";
    }

}