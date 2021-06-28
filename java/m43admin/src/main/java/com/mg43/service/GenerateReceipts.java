package com.mg43.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.log4j.Logger;

import com.mg43.google.SheetsFetcher;
import com.mg43.model.Cobranza;
import com.mg43.model.Departamento;
import com.mg43.model.Monto;

public class GenerateReceipts {
    final private static String APPLICATION_NAME	= "mg43";
    final private static String SPREADSHEET_ID 		= "124hn42GreG3_nRkIwzuZkaKIPwwAUNylQYHBaY6J9P0";
    final private static String DIRECTORIO 			= "Directorio!B3:N22";
    final private static String INGRESOS 			= "2021-In";
    final private static String EGRESOS 			= "2021-Out";

    private final static Logger log;
    private final Sheets sheets;
    private final String folder;

    static {
        log = Logger.getLogger(GenerateReceipts.class.getName());
    }

    public GenerateReceipts() throws Exception {
        File directory = new File("./");
        folder = directory.getCanonicalPath();
        sheets = new SheetsFetcher(folder).getSheetsService(APPLICATION_NAME);
    }

    public void generate(String linea, String folio) throws Exception {
        int contador;
        String range;
        ValueRange response;

        // Calcula la Fecha
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String fecha = DateCreator.getDate(dateFormat.format(cal.getTime()));

        // Calcula el Mes
        String mes = DateCreator.getMonthString(folio);

        LinkedHashMap<String, Departamento> directorio = getNames();
        LinkedHashMap<String, String> conceptos = new LinkedHashMap<>();
        LinkedHashMap<String, String[]> financial = getFinancial(DateCreator.getFinancialColumns(folio));

        range = INGRESOS + "!" + "D" + linea + ":" + "P" + linea;
        //System.out.println(range);

        log.info("");
        log.info("Conceptos del Mes");
        log.info("-----------------");

        contador = 1;
        response = sheets.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
        for (Object o : response.getValues().get(0)) {
            String key = contador + "";
            String value = o.toString().replaceAll("\\n", " ").replaceAll("  ", " ").trim();
            conceptos.put(key, value);
            log.info(key + "\t" + value);
            contador++;
        }

        final String FROM = Integer.parseInt(linea) + 1 + "";
        final String TO = Integer.parseInt(linea) + 20 + "";

        // Cobranza
        range = INGRESOS + "!" + "B" + FROM + ":" + "Q" + TO ;
        response = sheets.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
        LinkedHashMap<String, Cobranza> cobranza = new LinkedHashMap<>();
        List<List<Object>> values = response.getValues();
        for (List<Object> row : values) {
            String idDepto = this.get(row,0);
            Cobranza objeto = new Cobranza();
            objeto.setId(idDepto);
            objeto.setNombre(directorio.get(idDepto).getNombre());
            objeto.setStatus(get(row,15));
            cobranza.put(idDepto, objeto);
        }

        // Ingresos
        range = INGRESOS + "!" + "B" + FROM + ":" + "S" + TO ;
        response = sheets.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
        values = response.getValues();

        log.info("");
        PrintReceipt print = new PrintReceipt(folder);
        print.create(folio);

        contador = 0;
        for (List<Object> row : values) {
            contador++;
            String idDepto = get(row,0);
            String totalRecibo = get(row,2);
            Departamento departamento = directorio.get(idDepto);

            // if (!idDepto.equals("301"))
            // continue;

            for (String keyConcepto : conceptos.keySet()) {
                String concepto = conceptos.get(keyConcepto);

                Monto monto = new Monto();
                monto.setId(keyConcepto);
                monto.setConcepto(concepto);
                monto.setMonto(get(row, Integer.parseInt(keyConcepto) + 1));
                departamento.getMontos().put(keyConcepto, monto);

                //System.out.println(keyConcepto + "\t" + concepto + "\t\t" + monto.getMonto());
            }

            departamento.setRecibo(folio + "." + String.format("%03d", contador) );
            departamento.setIndex(contador);
            departamento.setDepartamento(idDepto);
            departamento.setNombre(directorio.get(idDepto).getNombre());
            departamento.setFecha(fecha);
            departamento.setMes(mes);
            departamento.setTotal("$ " + totalRecibo);

            departamento.getMontos().put("saldo", createMonto("saldo", "Saldo", get(row,15)));
            departamento.setNotas(get(row,16));
            departamento.setAdeudo(get(row,15));
            departamento.setDetalleAdeudo(get(row,17));
            departamento.setArchivo(folio + "_" + idDepto + ".pdf");

            departamento.setCobranza(cobranza);

            print.create(folio + "_" + idDepto);
            print.print(departamento, folio, financial);

            // NOT USED
            //print.updateDimension(FOLIO + "_" + idDepto);

            log.info("Generando recibo para Departamento " + idDepto + " por un total de " + totalRecibo);
        }
        //print.updateDimension(FOLIO);

        new SendEmail(folder, APPLICATION_NAME).sendEmail(directorio);
    }

    private LinkedHashMap<String, Departamento> getNames() throws Exception {
        log.info("");
        log.info("Lista de Departamentos a generar");

        LinkedHashMap<String, Departamento> result = new LinkedHashMap<>();

        ValueRange response = sheets.spreadsheets().values().get(SPREADSHEET_ID, DIRECTORIO).execute();
        List<List<Object>> values = response.getValues();
        for (List<Object> row : values) {
            String key = get(row,0);
            result.put(key, new Departamento());
            result.get(key).setId(key);
            result.get(key).setReferencia(get(row,1));
            result.get(key).setNombre(get(row,2));
            result.get(key).setEmail(get(row,5));
            result.get(key).setEmailComplemento(get(row,6));
            result.get(key).setTelefonos(get(row,7) + "," + get(row,8));
            result.get(key).setPaperless(get(row,10));
            log.info(key + "\t" + result.get(key).getNombre());
        }
        return result;
    }

    private LinkedHashMap<String, String[]> getFinancial(String[] columns) throws Exception {
        log.info("");
        log.info("Obteniendo Resumen Financiero");

        LinkedHashMap<String, String[]> result = new LinkedHashMap<>();

        ValueRange response = sheets.spreadsheets().values().get(SPREADSHEET_ID, EGRESOS + "!" + columns[0] + "2:" + columns[1] + "55").execute();
        List<List<Object>> values = response.getValues();
        int i = 0;
        for (List<Object> row : values) {
            String key = i + "";
            result.put(key, new String[] { get(row,0), get(row,1), get(row,2), get(row,3) });
            log.info(get(row,0) + "\t" + get(row,1) + "\t" + get(row,2));
            i++;
        }

        return result;
    }

    private Monto createMonto(String id, String concepto, String monto) {
        Monto objeto = new Monto();
        objeto.setId(id);
        objeto.setConcepto(concepto);
        objeto.setMonto(monto);
        return objeto;
    }

    private String get(List<Object> row, int index) {
        try {
            return row.get(index).toString();
        } catch (Exception e) {
            return "";
        }
    }
}
