/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import org.primefaces.model.UploadedFile;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  10-nov-2017 19:10:00  Description: 
 * ApiDriveService.java  
 * *****************************************************************************
 */
public interface ApiDriveService
{
    public void getDriveService() throws Exception;

    public String createFolder(String folder, String driveParentId) throws Exception;

    public String uploadFile(UploadedFile file, String fileName, String driveID, String driveParentId) throws Exception;
    
    public String copyFile(String driveIDCopy, String driveID, String driveParentId) throws Exception;
    
    public void deleteFile(String driveID) throws Exception;

    public String downloadFile(String driveID) throws Exception;
}
