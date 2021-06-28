package com.mg43.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.io.FileInputStream;
import java.util.Locale;

import com.aspose.pdf.*;
import com.mg43.model.Item;
import com.mg43.model.Nota;
import com.mg43.model.Cobranza;
import com.mg43.model.Departamento;
import com.mg43.util.StringTools;

public class PrintReceipt {

	private String LAYOUT_FILE;
	private String LICENSE_FILE;
	private String OUTPUT_FOLDER;
	private String FONT="Helvetica";
	
	public PrintReceipt(String folder) throws Exception {
		String RESOURCES_FOLDER = folder + "/src/main/resources";

		LAYOUT_FILE 	= RESOURCES_FOLDER + "/pdf-layout/layout-new.pdf";
		LICENSE_FILE 	= RESOURCES_FOLDER + "/aspose/Aspose.Pdf.lic";

		OUTPUT_FOLDER 	= folder + "/output/";
	}
	
	public void create(String file) throws Exception {
		Locale.setDefault(new Locale("en-us"));

		Document pdfDocument = new Document();
		com.aspose.pdf.DocumentInfo docInfo = pdfDocument.getInfo();
		//set Author information
		docInfo.setAuthor("Administracion Maricopa 43");
		docInfo.setCreationDate(new java.util.Date());
		docInfo.setKeywords("Maricopa 43, Administracion, administracion@maricopa43.com");
		docInfo.setModDate(new java.util.Date());
		
		pdfDocument.save(OUTPUT_FOLDER + file + ".pdf");
	}
	
	public void print(Departamento departamento, String file, LinkedHashMap<String, String[]> financial) throws Exception {		
		String fileToPrint = OUTPUT_FOLDER + file + ".pdf";
		String fileToSend = OUTPUT_FOLDER + file + "_" + departamento.getDepartamento() + ".pdf";
		
		StringTools strTools = new StringTools();
		
		// Create license object
		License license = new License();
		license.setLicense(new FileInputStream(LICENSE_FILE));
		
		 //open document
		Document layout = new Document(LAYOUT_FILE);
		//get particular page
		Page layoutPage = layout.getPages().get_Item(1);
		
		Document pdfDocument = new Document(fileToSend);
		// Add first page
		pdfDocument.getPages().add(layoutPage);
		// Add second page
		pdfDocument.getPages().add(layout.getPages().get_Item(2));
		// Add third page
		pdfDocument.getPages().add(layout.getPages().get_Item(3));
		
		Page pdfPage = pdfDocument.getPages().get_Item(1);
		
		TextBuilder textBuilder;
		
		textBuilder = new TextBuilder(pdfDocument.getPages().get_Item(1));
		textBuilder.appendText( printText(225,	463, 8, 1, Color.getRed(),		FONT,	departamento.getRecibo()) );
		textBuilder.appendText( printText(62,	427, 9, 0, Color.getBlue(),		FONT,	departamento.getFecha()) );
		textBuilder.appendText( printText(53,	382, 9, 0, Color.getBlue(),		FONT,	departamento.getMes()) );
		textBuilder.appendText( printText(250,	382, 9, 1, Color.getBlue(),		FONT,	departamento.getDepartamento()) );
		textBuilder.appendText( printText(75,	350, 9, 0, Color.getBlue(),		FONT,	departamento.getNombre()) );
		textBuilder.appendText( printText(108,	319, 9, 0, Color.getBlue(),		FONT,	departamento.getMontos().get("2").getMonto()) );
		textBuilder.appendText( printText(248,	319, 8, 1, Color.getGray(),		FONT,	departamento.getReferencia()) );
		textBuilder.appendText( printText(63,	185, 11, 1, Color.getBlack(),	FONT,	departamento.getTotal()) );
		
		if ( !departamento.getAdeudo().equals("") )
			textBuilder.appendText( printText(104,	152, 7, 1, Color.getRed(),		FONT,	String.format("%15s", "$ " + departamento.getAdeudo()) ) );
		
		int rowPixel = 287;		
		for (Iterator<String> iterator = departamento.getMontos().keySet().iterator(); iterator.hasNext();){
        	String key = iterator.next();
        	if (key.equals("1") || key.equals("2") || key.equals("saldo"))
        		continue;
        	
        	if (!departamento.getMontos().get(key).getMonto().equals("")) {
        		 textBuilder.appendText( printText(60,	rowPixel, 7f, 0, Color.getBlue(),	FONT,	departamento.getMontos().get(key).getConcepto() ));
        		 textBuilder.appendText( printText(215,	rowPixel, 6.8f, 0, Color.getBlue(),	"Courier",	String.format("%15s", departamento.getMontos().get(key).getMonto()) ));
	        	rowPixel -= 10;
        	}
        }

		Nota nota = strTools.parseNotes(departamento, 40);

		// Notas
		rowPixel = 142;
		for (String line: nota.getNotes() ) {
			textBuilder.appendText( printText(160,	rowPixel, 6.3f, 0, Color.getBlack(),	FONT, line) );
			rowPixel -= 8;
		}
		
		// Adeudos
		rowPixel = 142;
		for (Item item: nota.getItems() ) {
			textBuilder.appendText( printText(31,	rowPixel, 6.3f, 0, Color.getBlack(),	FONT, 	item.getItem()) );
			textBuilder.appendText( printText(110,	rowPixel, 6.3f, 0, Color.getBlack(),	"Courier", 	String.format("%10s", item.getAmount()) ) );
			rowPixel -= 8;
		}
		//textBuilder.appendText( printText(110,	rowPixel, 6.3f, 0, Color.getRed(),	"Courier", 	String.format("%10s", nota.getTotal()) ) );
		
		// Add Financial Summary
		rowPixel = 435;
		String line = "";
		textBuilder = new TextBuilder(pdfDocument.getPages().get_Item(2));
		
		for (int i=0; i<68; i++) line += "_";
		
		for (int i=0; i<53; i++) {
			textBuilder.appendText( printText(38,	rowPixel, 6.0f, 0, Color.getLightGray(),	FONT,	line));
			rowPixel -= 7;			
		}
		
		rowPixel = 435;
		for (String key : financial.keySet() ){
			textBuilder.appendText( printText(40,	rowPixel, 6.5f, 0, Color.getBlue(),		FONT,	financial.get(key)[0]) );
			textBuilder.appendText( printText(45,	rowPixel, 5.3f, 0, Color.getBlack(),	FONT,	financial.get(key)[1]) );
			textBuilder.appendText( printText(100,	rowPixel, 5.3f, 2, Color.getGray(),		FONT,	financial.get(key)[2]) );
			textBuilder.appendText( printText(208,	rowPixel, 5.5f, 0, Color.getBlack(),	"Courier",	String.format("%15s", financial.get(key)[3]) ));
						
			rowPixel -= 7;
		}
		
		// Cobranza
		rowPixel = 381;
		textBuilder = new TextBuilder(pdfDocument.getPages().get_Item(3));
		for (Cobranza cobranza : departamento.getCobranza().values()) {
			Color tempColor;
			String status;
			
			if ( cobranza.getStatus().equals("") ) {
				tempColor = Color.getBlue();
				status = "0.00";
			} else if ( !strTools.isGreaterThanZero(cobranza.getStatus()) ) {
				tempColor = Color.getBlue();
				status = cobranza.getStatus();
			} else {
				tempColor = Color.getRed();
				status = cobranza.getStatus();
			}
			
			//tempColor = Color.getBlue();
			
			textBuilder.appendText( printText(43,	rowPixel, 6.5f, 0, tempColor,		FONT,	cobranza.getId()) );
			textBuilder.appendText( printText(67,	rowPixel, 6.5f, 0, tempColor,		FONT,	cobranza.getNombre()) );
			textBuilder.appendText( printText(197,	rowPixel, 6.5f, 0, tempColor,		"Courier",	String.format("%15s", status)) );
			//textBuilder.appendText( printText(197,	rowPixel, 6.5f, 0, tempColor,		"Courier",	String.format("%15s", "?")) );
			
			rowPixel -= 15;
		}
		
		// Signing document
		pdfDocument.getInfo().setSubject("Recibo de Cuotas. (Depto. " + departamento.getDepartamento() + " - " + departamento.getMes() + ")");
		pdfDocument.getInfo().setTitle("Recibo de Cuotas. (Depto. " + departamento.getDepartamento() + " - " + departamento.getMes() + ")");
		pdfDocument.encrypt("", "Elizabeth84", 1, 1);
		pdfDocument.save(fileToSend);
        
        if ( departamento.getPaperless().equals("No") ) {
    		Document pdfDocumentToPrint = new Document(fileToPrint);
    		pdfDocumentToPrint.getPages().add(pdfPage);
    		pdfDocumentToPrint.encrypt("", "Elizabeth84", 1, 1);
    		pdfDocumentToPrint.save(fileToPrint);	
        }

		layout.close();
	}
	
	public TextFragment printText(int x, int y, float size, int style, Color color, String font, String value) {
		TextFragment textFragment = new TextFragment(value);
		textFragment.setPosition(new Position(x, y));
		
		textFragment.getTextState().setFont(FontRepository.findFont(font));
		textFragment.getTextState().setFontSize(size);
		textFragment.getTextState().setFontStyle(style);
		textFragment.getTextState().setForegroundColor(color);
		
		return textFragment;
	}
	
	/*
	public void updateDimension(String file) throws Exception {
		Document pdfDocument = new Document("/Users/sateav/Downloads/" + file + ".pdf");
		for (int i = 1; i <= pdfDocument.getPages().size(); i++)
			pdfDocument.getPages().get_Item(i).setPageSize(306, 396);	
		pdfDocument.save("/Users/sateav/Downloads/" + file + ".pdf");
	}*/
}
