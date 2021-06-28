package com.mg43.util;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;

import com.mg43.model.Item;
import com.mg43.model.Nota;
import com.mg43.model.Departamento;

public class StringTools {
	String FORMAT;
	DecimalFormat df;
	
	public StringTools() {
		FORMAT = "#,##0.00";
		df = new DecimalFormat(FORMAT);
	}
	
	public boolean isGreaterThanZero(String value) {
		try {
			if ( df.parse(value).doubleValue() > 0 )
				return true;
			else 
				return false; 
		} catch (Exception exception) {
			return false;
		}
	}

	public Nota parseNotes(Departamento departamento, int length) {
		Nota nota = new Nota();

		final String TOKEN_CURRENCY = "\\$";		
		String seccionAdeudo = "";
		String seccionNota = "";
		double total = 0;
		
		try {
			Item item;
			seccionAdeudo = departamento.getDetalleAdeudo().trim();
			
			for ( String line : seccionAdeudo.split("\n") ) {
				String text = line.split(TOKEN_CURRENCY)[0].trim();
				String amount = line.split(TOKEN_CURRENCY)[1].trim();

				item = new Item(text, format(amount));
				nota.getItems().add(item);

				total += df.parse(amount).doubleValue();
			}			
		} catch (Exception exception) {
			//exception.printStackTrace();
		}
		nota.setTotal(format(Double.toString(total)));
		
		try {
			seccionNota = departamento.getNotas().trim();
			nota.setNotes( rearrange(seccionNota, length) );
		} catch (Exception exception) {
			nota.setNotes(rearrange(departamento.getNotas(), length));
		}		
		
		return nota;
	}
	
	public ArrayList<String> rearrange(String value, int length) {
		ArrayList<String> result = new ArrayList<String>();
		for ( String line : value.split("\n") )
			for ( String innerLine: WordUtils.wrap(line, length).split("\n") )
				result.add(innerLine);
		return result;
	}
	
	public String format(Object value) {
		try {
			double temp = df.parse(value.toString()).doubleValue();
			return df.format(temp);
		} catch (Exception exception) {
			return value.toString();
		}
	}
	
	public static void main(String[] args) {
		String detalleAdeudo = ""
				+ "Faltante en MTTO Dic 2016    $100\n"
				+ "Faltante en MTTO Ene 2017    $150\n";
		
		String notas = ""
				+ "Te recordamos que la cuota de mantenimiento es de 3,500 pesos."
				+ "El Cincinnati Enquirer reportó que un hombre exhortó al municipio a no poner en peligro los fondos que recibe del Gobierno federal.\n"
				+ "El Presidente Donald Trump amenazó con dejar de otorgar fondos a jurisdicciones locales que no cooperen con las autoridades federales de inmigración.";
		
		Departamento departamento = new Departamento();
		departamento.setDetalleAdeudo(detalleAdeudo);
		departamento.setNotas(notas);
		
		Nota nota = new StringTools().parseNotes(departamento, 70);
		for (Item item: nota.getItems())
			System.out.println(item.getItem() + "\t" + item.getAmount() );
		System.out.println("TOTAL A PAGAR" + "\t" + nota.getTotal());
		
		for (String linea: nota.getNotes())
			System.out.println(linea);
		
		System.out.println();

		notas = "El Cincinnati Enquirer reportó que un hombre exhortó al municipio a no poner en peligro los fondos que recibe del Gobierno federal.\n"
				+ "El Presidente Donald Trump amenazó con dejar de otorgar fondos a jurisdicciones locales que no cooperen con las autoridades federales de inmigración."
				+ "";		
		
		for (String linea: new StringTools().rearrange(notas, 30) )
			System.out.println(linea);
	}
}
