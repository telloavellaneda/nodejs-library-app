/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.dao;

import java.util.List;
import mx.com.eai.tpp.model.Ddetallematerial;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  26-nov-2017 18:20:27  Description: 
 * DetalleMaterialDao.java  
 * *****************************************************************************
 */
public interface DetalleMaterialDao extends AbstractDao<Ddetallematerial, Integer>
{
    public List<Ddetallematerial> materialesBySolicitud(int solicitudID) throws Exception;
}
