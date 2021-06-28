/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.model.subclases;

import mx.com.eai.tpp.model.Ddetallematerial;
import org.primefaces.model.UploadedFile;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:47:28  Description: 
 * DetalleMaterialImagen.java  
 * *****************************************************************************
 */
public class DetalleMaterialImagen
{
    private Ddetallematerial material;
    private UploadedFile imagen;
    private UploadedFile factura;

    public DetalleMaterialImagen()
    {
        this.material = new Ddetallematerial();
    }

    public DetalleMaterialImagen(Ddetallematerial material)
    {
        this.material = material;
    }
    
    

    /**
     * @return the material
     */
    public Ddetallematerial getMaterial()
    {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Ddetallematerial material)
    {
        this.material = material;
    }

    /**
     * @return the imagen
     */
    public UploadedFile getImagen()
    {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(UploadedFile imagen)
    {
        this.imagen = imagen;
    }

    /**
     * @return the factura
     */
    public UploadedFile getFactura()
    {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(UploadedFile factura)
    {
        this.factura = factura;
    }
}
