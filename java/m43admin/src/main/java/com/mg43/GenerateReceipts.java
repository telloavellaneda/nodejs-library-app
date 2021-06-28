package com.mg43;

public class GenerateReceipts {

    public static void main(String[] args) throws Exception {
		final String LINEA 	= "90"; 
		final String FOLIO 	= "2021-05";
		
	    new com.mg43.service.GenerateReceipts().generate(LINEA, FOLIO);
	}
}