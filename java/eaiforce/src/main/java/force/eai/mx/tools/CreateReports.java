package force.eai.mx.tools;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class CreateReports {
	private LinkedHashMap<String, LinkedHashMap<String, String>> mainExport;
			
	public void create(String fullName) throws IOException {		
		File fileName = new File(fullName);
		FileOutputStream fos = new FileOutputStream(fileName);
        XSSFWorkbook  workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("eaiForce");

        CellStyle headerStyle = workbook.createCellStyle();
        CellStyle stringStyle = workbook.createCellStyle();
        CellStyle integerStyle = workbook.createCellStyle();
        CellStyle doubleStyle = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        
    	DataFormat format = workbook.createDataFormat();
        
    	XSSFFont font = workbook.createFont();
    	font.setBold(true);
    	font.setColor(IndexedColors.WHITE.getIndex());
    	
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        headerStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setFont(font);

        stringStyle.setAlignment(CellStyle.ALIGN_LEFT);
        integerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        doubleStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        dateStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        
        integerStyle.setDataFormat(format.getFormat("0"));
        doubleStyle.setDataFormat(format.getFormat("#,##0.00"));
        dateStyle.setDataFormat(format.getFormat("dd/mm/yyyy"));
        
        int r = 0;
        int c = 0;
    	Row row = sheet.createRow(r);
    	LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
    	
    	for (Iterator<String> iterator = mainExport.get("-1").keySet().iterator(); iterator.hasNext();) {
        	String column = iterator.next();
        	String type = mainExport.get("-1").get(column);
        	if ( !type.equals("4") ) {
        		//System.out.println(column + "\t" + type);
        		columns.put(column, type);
        		Cell cell = row.createCell(c);
	        	cell.setCellStyle(headerStyle);
	        	cell.setCellType(Cell.CELL_TYPE_STRING);
	        	cell.setCellValue(getType(column));
            	sheet.setColumnWidth(c, 5000);
	        	c++;
        	}
        }
        
        r++;
        for (Iterator<String> iterator = mainExport.keySet().iterator(); iterator.hasNext();) {
        	c = 0;
            row = sheet.createRow(r);
            String id = iterator.next();
            
            if ( !id.equals("-1") ) {
	        	for (Iterator<String> columnIterator = columns.keySet().iterator(); columnIterator.hasNext();) {
	            	String column = columnIterator.next();
	            	String type = columns.get(column);            	
	
	            	Cell cell = row.createCell(c);
	            	if (type.equals("1")) {
	                	cell.setCellStyle(integerStyle);                    
	            		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	cell.setCellValue(getDouble(mainExport.get(id).get(column)));
	            	} else if (type.equals("2")) {
	                	cell.setCellStyle(doubleStyle);                    
	            		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                	cell.setCellValue(getDouble(mainExport.get(id).get(column)));
	            	} else if (type.equals("3")) {
	                	cell.setCellStyle(dateStyle);
	            		cell.setCellType(Cell.CELL_TYPE_STRING);
	                	cell.setCellValue(mainExport.get(id).get(column));
	            	} else {
	                	cell.setCellStyle(stringStyle);                    
	            		cell.setCellType(Cell.CELL_TYPE_STRING);
	                	cell.setCellValue(mainExport.get(id).get(column));
	            	}
	            	c++;
	            }
	        	r++;
            }
        }
        
        workbook.write(fos);
        workbook.close();
        fos.flush();
        fos.close();
	}
	
	private double getDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch(Exception e) {
			return 0;
		}
	}
		
	private String getType(String column) {
		if (column.indexOf("INT_") != -1)
			return column.replaceAll("INT_", "").replaceAll("_", " ");
		if (column.indexOf("DBL_") != -1)
			return column.replaceAll("DBL_", "").replaceAll("_", " ");
		if (column.indexOf("FEC_") != -1)
			return column.replaceAll("FEC_", "").replaceAll("_", " ");
		if (column.indexOf("ID_") != -1)
			return column.replaceAll("_", " ");
		return column.replaceAll("_", " ");
	}

	public void setMainExport(LinkedHashMap<String, LinkedHashMap<String, String>> mainExport) {
		this.mainExport = mainExport;
	}
}
