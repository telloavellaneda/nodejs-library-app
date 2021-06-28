package com.mg43.google;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SheetsFetcher {

    private static JsonFactory 			JSON_FACTORY;
    private static String				JSON_CREDENTIALS;
    private static HttpTransport 		HTTP_TRANSPORT;
    private static java.io.File 		DATA_STORE_DIR;
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static List<String> 		SCOPES;

    public SheetsFetcher(String folder) throws Exception {
    	final String SHEETS_FOLDER = folder + "/src/main/resources/google/sheets/";
    	
    	JSON_FACTORY = JacksonFactory.getDefaultInstance();
    	    	
    	SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY, SheetsScopes.DRIVE_READONLY);
    	
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	DATA_STORE_DIR = new java.io.File(SHEETS_FOLDER);
        DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        
    	JSON_CREDENTIALS = SHEETS_FOLDER + "sheets_secret.json";
    }
    
	public Sheets getSheetsService(String application) throws IOException {
    	Credential credential = authorize(JSON_CREDENTIALS);
    	return new Sheets
				.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(application)
				.build();
   	}

    private Credential authorize(String credentials) throws IOException {
    	// Load client secrets.
    	java.io.File jsonCredentials = new java.io.File(credentials);
        InputStream inputStream = new java.io.FileInputStream(jsonCredentials);
    	GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

    	// Build flow and trigger user authorization request.
    	GoogleAuthorizationCodeFlow flow =
	    	new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		    	.setDataStoreFactory(DATA_STORE_FACTORY)
		    	.setAccessType("offline")
		    	.build();
    	Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    	return credential;
   	}
}