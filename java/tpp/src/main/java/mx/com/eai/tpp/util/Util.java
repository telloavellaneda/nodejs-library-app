package mx.com.eai.tpp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Util
{
    public static final String FORMATODDMMYYYY = "dd/MM/yyyy";
    public static final String FORMATO_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATOYYYYMMDD = "yyyy/MM/dd";
    public static final String FORMATODDMMYYYYE = "ddMMyyyy";
    public static final String FORMATODDMMYYE = "ddMMyy";
    public static final String FORMATOHHMM = "HH:mm";
    public static final String FORMATOHHMMSS = "HH:mm:ss";
    public static final String FORMATODDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm";
    public static final String FORMATODDMMYYHHMM = "dd/MM/yy HH:mm";
    public static final String FORMATOYYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final String FORMATO_EVENTO = "dd/MM/yyyy HH:mm";
    public static final String FORMATO_HL7 = "yyyyMMddHHmmss";
    public static final String FORMATODAY = "dd";
    public static final String FORMATOMOTH = "MMMM";
    public static final String FORMATOYEAR = "YYYY";
    public static final int ASCENDENTE = 0;
    public static final int DESCENDENTE = 1;
    private Logger log = Logger.getLogger(this.getClass());

    public static Date parseDateTime(String s)
    {
        try
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return formatter.parse(s);
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String printDateTime(Date dt)
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return (dt == null) ? null : formatter.format(dt);
    }

    public static String encriptarMd5(String valor)
    {
        try
        {
            Md5PasswordEncoder md5 = new Md5PasswordEncoder();
            valor = md5.encodePassword(valor, null);
        } catch (Exception e)
        {
            valor = "";
        }
        return valor;
    }

    public static List<Integer> mesesEntreFechas(Date fechaInicio, Date fechaFin)
    {
        List<Integer> meses = new ArrayList<Integer>();
        Calendar calFechaInicio = Calendar.getInstance();
        calFechaInicio.setTime(fechaInicio);
        int mesInicio = calFechaInicio.get(Calendar.MONTH);

        int anioInicio = calFechaInicio.get(Calendar.YEAR);

        Calendar calFechaFen = Calendar.getInstance();
        calFechaFen.setTime(fechaFin);
        int mesFin = calFechaFen.get(Calendar.MONTH);

        int anioFin = calFechaFen.get(Calendar.YEAR);

        if (anioInicio - anioFin != 0)
        {
            mesFin += 12;
        }
        for (int i = mesInicio; i <= mesFin; i++)
        {
            meses.add(i + 1);
        }

        return meses;
    }

    public static List<Date> diasEntreFechas(Date fechaInicio, Date fechaFin)
    {
        List<Date> dias = new ArrayList<Date>();
        Calendar calFechaInicio = Calendar.getInstance();
        calFechaInicio.setTime(fechaInicio);

        Calendar calFechaFin = Calendar.getInstance();
        calFechaFin.setTime(fechaFin);

        if (calFechaInicio.get(Calendar.DAY_OF_YEAR) != calFechaFin.get(Calendar.DAY_OF_YEAR))
        {
            do
            {
                dias.add(calFechaInicio.getTime());
                calFechaInicio.add(Calendar.DATE, 1);
            }
            while (calFechaInicio.before(calFechaFin));
            dias.add(calFechaFin.getTime());
        }
        else
        {
            dias.add(calFechaFin.getTime());
        }

        return dias;
    }

    public static String convertirCardinales(int numero)
    {
        String cardinal;
        switch (numero)
        {
            case 1:
                cardinal = "1°";
                break;
            case 2:
                cardinal = "2°";
                break;
            case 3:
                cardinal = "3°";
                break;
            default:
                cardinal = "";
        }
        return cardinal;
    }

    public static float calcularPrecioConIVA(float precio, float iva)
    {
        DecimalFormat formateador = new DecimalFormat("########0.00");
        String total = formateador.format(precio + (precio * (iva / 100)));
        return Float.parseFloat(total);
    }

    public static List<String> generarListaNumeros(int numeroInicial, int numeroFinal)
    {
        List<String> list = new ArrayList<String>();
        for (int i = numeroInicial; i <= numeroFinal; i++)
        {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public static String getDiasEstancia(Date fechaIngreso)
    {

        Calendar ingreso = new GregorianCalendar();
        ingreso.setTime(fechaIngreso);
        Calendar hoy = new GregorianCalendar();
        hoy.setTime(Util.getFechaActual());
        Calendar diasHos = new GregorianCalendar();
        diasHos.setTimeInMillis(hoy.getTimeInMillis() - ingreso.getTimeInMillis());
        return String.valueOf(diasHos.get(Calendar.DAY_OF_YEAR) == 365 || diasHos.get(Calendar.DAY_OF_YEAR) == 366 ? 1 : diasHos.get(Calendar.DAY_OF_YEAR));
    }

    public static String getDiasEntreFechas(Date fechaInicla, Date fechaFinal)
    {

        Calendar fInicial = new GregorianCalendar();
        fInicial.setTime(fechaInicla);
        Calendar fFinal = new GregorianCalendar();
        fFinal.setTime(fechaFinal);
        Calendar diasHos = new GregorianCalendar();
        diasHos.setTimeInMillis(fFinal.getTimeInMillis() - fInicial.getTimeInMillis());
        return String.valueOf(diasHos.get(Calendar.DAY_OF_YEAR) == 365 || diasHos.get(Calendar.DAY_OF_YEAR) == 366 ? 1 : diasHos.get(Calendar.DAY_OF_YEAR));
    }

    public static String encriptarSha(String valor)
    {
        try
        {
            LdapShaPasswordEncoder sha = new LdapShaPasswordEncoder();
            valor = sha.encodePassword(valor, null);
        } catch (Exception e)
        {
            valor = "";
        }
        return valor;
    }

    public static String getAlfaNumAleatorio(int longitud)
    {
        String cadenaAleatoria = "";
        long milis = new GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud)
        {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z'))
            {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }

    /**
     * Obtiene la ruta de la aplicacion http://192.25.25.55:8082/HechoEnMexico/
     *
     * @param request
     * @return
     */
    public static String getRutaAplicacion(HttpServletRequest request)
    {
        String dominio = ResourceBundle.getBundle("Aplicacion.properties").getString("ruta.dominio");
        String ruta = request.getScheme() + "://" + dominio
                + request.getContextPath() + "/";

        return ruta;
    }

    public static Date parsearFecha(String valor)
    {
        Date fecha;
        String formato;
        try
        {
            formato = valor.charAt(2) == '/' ? "dd/MM/yyyy"
                    : valor.charAt(2) == '-' ? "dd-MM-yyyy"
                    : valor.charAt(4) == '/' ? "yyyy/MM/dd" : valor.charAt(4) == '-' ? "yyyy-MM-dd" : "";
            fecha = parsearFecha(valor, formato);

        } catch (Exception e)
        {
            fecha = null;
        }

        return fecha;
    }

    public static Date parsearHora(String valor)
    {
        Date fecha;
        String formato;
        try
        {
            formato = valor.charAt(2) == ':' ? "HH:mm" : "";
            fecha = parsearFecha(valor, formato);

        } catch (Exception e)
        {
            fecha = null;
        }

        return fecha;
    }

    public static Date parsearFecha(String valor, String formato)
    {

        Date fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        try
        {
            fecha = formateador.parse(valor);
        } catch (ParseException e)
        {
            fecha = null;
        }
        return fecha;
    }

    public static String formatearFecha(Date date, String formato)
    {

        String fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        if (date == null)
        {
            fecha = "";
        }
        else
        {
            fecha = formateador.format(date);
        }
        return fecha;
    }

    public static String formatearFecha(Date date)
    {

        String fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATODDMMYYYY);
        fecha = formateador.format(date);
        return fecha;
    }

    public static String formatearFechaHora(Date date)
    {

        String fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATODDMMYYYYHHMMSS);
        fecha = formateador.format(date);
        return fecha;
    }

    /**
     * Obtiene la fecha con el formato dd/MM/yyyy HH24:mm
     *
     * @param date
     * @return
     */
    public static String formatearFechaEvento(Date date)
    {

        String fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_EVENTO);
        fecha = formateador.format(date);
        return fecha;
    }

    /**
     * Asigna un timestamp a una fecha con el formato YYYY-MM-DD HH24:MI
     *
     * @param date
     * @return
     */
    public static Date formatearFechaToEvento(String valor)
    {
        Date fecha;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_EVENTO);
        try
        {
            fecha = formateador.parse(valor);
        } catch (ParseException e)
        {
            fecha = null;
        }
        return fecha;
    }

    public static void ordena(List lista, final String propiedad,
            final int ordenacion)
    {

        Collections.sort(lista, new Comparator()
        {
            public int compare(Object obj1, Object obj2)
            {

                Class clase = obj1.getClass();
                String getter = "get" + Character.toUpperCase(propiedad.charAt(0)) + propiedad.substring(1);
                Logger logger = Logger.getLogger(this.getClass());
                try
                {
                    Method getPropiedad = clase.getMethod(getter);

                    Object propiedad1 = getPropiedad.invoke(ordenacion == ASCENDENTE ? obj1 : obj2);
                    Object propiedad2 = getPropiedad.invoke(ordenacion == ASCENDENTE ? obj2 : obj1);

                    if (propiedad1 instanceof Comparable
                            && propiedad2 instanceof Comparable)
                    {
                        Comparable prop1 = (Comparable) propiedad1;
                        Comparable prop2 = (Comparable) propiedad2;
                        return prop1.compareTo(prop2);
                    }// CASO DE QUE NO SEA COMPARABLE
                    else if (propiedad1.equals(propiedad2))
                    {
                        return 0;
                    }
                    else
                    {
                        return 1;
                    }

                } catch (Exception e)
                {
                    logger.error("Error al ordernar la lista: ", e);
                }
                return 0;
            }
        });
    }

    public static Map ordenarHashMap(Map hmap)
    {
        HashMap map = new LinkedHashMap();
        List mapKeys = new ArrayList(hmap.keySet());
        Collections.sort(mapKeys);
        for (int i = 0; i < mapKeys.size(); i++)
        {
            map.put(mapKeys.get(i), hmap.get(mapKeys.get(i)));
        }
        return map;
    }

    public static Map ordenarHashValuesMap(Map map)
    {

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }

    public static Date restarDias(Date date, int dias)
    {
        Calendar fecha = Calendar.getInstance(); // obtiene la fecha actual
        fecha.add(Calendar.DATE, -dias); // incrementa en 30 dï¿œas la fecha
        // actual.
        return fecha.getTime();
    }

    public static Date sumaAnios(Date date, int anios)
    {
        Calendar fecha = Calendar.getInstance(); // obtiene la fecha actual
        fecha.setTime(date);
        fecha.add(Calendar.YEAR, +anios); // incrementa en 30 dï¿œas la fecha
        // actual.
        return fecha.getTime();
    }

    public static Calendar fechaHabil(Calendar fecha, int dias)
    {
        int toAdd = dias < 0 ? -1 : 1;
        dias = dias < 0 ? dias * -1 : dias;
        for (int i = 0; i < dias; i++)
        {
            fecha.add(Calendar.DATE, toAdd);
            while (!esHabil(fecha))
            {
                fecha.add(Calendar.DATE, toAdd);
            }
        }
        return fecha;
    }

    public static boolean esHabil(Calendar fecha)
    {
        switch (fecha.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                // Domingo
                return false;
            case 7:
                // Sabado
                return false;
            default:
                // Entre semana
                // Obtener los dias festivos, por defaul no son festivos
                boolean isFestivo = false;
                if (isFestivo)
                {
                    return false;
                }
                else
                {
                    return true;
                }
        }
    }

    public static Map<Integer, String> getListTransform(Map<Integer, String> map)
    {
        Map<Integer, String> mapTransform = new HashMap<Integer, String>();
        if (map != null)
        {
            java.util.Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry<Integer, String> pairs = (Map.Entry<Integer, String>) it.next();
                mapTransform.put(pairs.getKey(), getStringTransform(pairs.getValue()));
            }
        }
        return mapTransform;
    }

    public static String getStringTransform(String value)
    {
        String substr = "", substrLasted = "", string = value;
        if (value != null && value.length() > 0)
        {
            string = string.toLowerCase();
            substr = ("" + string.charAt(0)).toUpperCase();
            substrLasted = string.substring(1, string.length());
        }
        return "" + substr + substrLasted;
    }

    public static void descargarArchivo(byte[] arrDatos,
            HttpServletResponse response, String nombreArchivo)
            throws IOException
    {

        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
        String content = getContentType(extension);

        OutputStream outputStream = response.getOutputStream();
        if (content != null && content.length() > 0)
        {
            response.setContentType(content);
        }
        response.setContentLength(arrDatos.length);
        response.setHeader("Content-Disposition", "attachment; filename="
                + nombreArchivo);
        outputStream.write(arrDatos, 0, arrDatos.length);
        outputStream.flush();
        outputStream.close();
    }

    public static String getContentType(String extension)
    {

        Map<String, String> tipos = new HashMap<String, String>();
        tipos.put(".DOC", "application/msword");
        tipos.put(".DOCX",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        tipos.put(".PDF", "application/pdf");
        tipos.put(".RAR", "application/x-rar-compressed");
        tipos.put(".REV", "application/x-rar-compressed");
        tipos.put(".R00", "application/x-rar-compressed");
        tipos.put(".R01", "application/x-rar-compressed");
        tipos.put(".ZIP", "application/zip");
        tipos.put(".ZIPX", "application/zip");
        tipos.put(".XLA", "application/vnd.ms-excel");
        tipos.put(".XLC", "application/vnd.ms-excel");
        tipos.put(".XLM", "application/vnd.ms-excel");
        tipos.put(".XLS", "application/vnd.ms-excel");
        tipos.put(".XLT", "application/vnd.ms-excel");
        tipos.put(".XLW", "application/vnd.ms-excel");
        tipos.put(".XLSX", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        tipos.put(".PPT", "application/vnd.ms-powerpoint");
        tipos.put(".PPTX", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        tipos.put(".TXT", "text/plain");

        String contentType = tipos.get(extension) != null ? tipos.get(extension) : "";

        return contentType;

    }

    public String getDateTime(String format)
    {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Obtiene el no. de minutes entre una fecha y otra
     *
     * @param oldTime
     * @param currentTime
     * @return
     */
    public Long compareTimesByMinutes(Timestamp oldTime, Timestamp currentTime)
    {
        Long difmiliSeconds = currentTime.getTime() - oldTime.getTime(); // obtiene el no. de milisegundos
        difmiliSeconds = (difmiliSeconds / (1000 * 60));// la diferencia en minutos
        return difmiliSeconds;
    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne)
    {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne)
        {
            items[0] = new SelectItem("0", "-- Seleccione --");
            i++;
        }
        for (Object x : entities)
        {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static float calcularEdad(Date fechaNac)
    {

        Calendar fechaActual = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(fechaNac);

        // Cálculo de las diferencias.
        float anios = fechaActual.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
        float meses = fechaActual.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
        //int dias = fechaActual.get(Calendar.DAY_OF_MONTH) - nacimiento.get(Calendar.DAY_OF_MONTH);

        return anios + ((12 + meses) / 12);
    }

    public static int calcularEdadEnDias(Date fechaNac)
    {

        Calendar fechaActual = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(fechaNac);

        int anios = fechaActual.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
        int meses = fechaActual.get(Calendar.MONTH) - nacimiento.get(Calendar.MONTH);
        int dias = fechaActual.get(Calendar.DATE) - nacimiento.get(Calendar.DATE);

        return (anios * 365) + (meses * 30) + dias + 1;
    }

    public static float calcularIMC(float peso, float talla)
    {
        return (float) (peso / (talla * talla));
    }

    public static int calcularBalance(int ingresos, int egresos)
    {
        return (int) ingresos - egresos;
    }

    public static int calcularEscala(int[] ap)
    {
        int suma = Constant.INIT;
        for (int i = Constant.INIT; i < ap.length; i++)
        {
            suma = suma + ap[i];
        }
        return (int) suma;
    }

    public static Date getFechaActual()
    {
        return Calendar.getInstance().getTime();
    }

    public static String getFechaFormatoFacturaXML()
    {
        Date fechaActual = Calendar.getInstance().getTime();
        String fecha = Util.formatearFecha(fechaActual, FORMATO_YYYY_MM_DD);
        String hora = Util.formatearFecha(fechaActual, FORMATOHHMMSS);
        return fecha + "T" + hora;
    }

    public static String[] CalculaEdadDgis(Date nacimiento)
    {
        /*TIPO EDAD CATALOGO
        HORAS	0
        DIAS	1
        MESES	2
        AÑOS	3*/
        String[] tipoEdad = new String[2];
        long lbirthday = nacimiento.getTime();

        Calendar age = Calendar.getInstance();
        age.setTimeInMillis(Math.abs(lbirthday - System.currentTimeMillis()));

        if (age.get(Calendar.DAY_OF_MONTH) < 1)
        {
            tipoEdad[0] = "" + age.get(Calendar.HOUR_OF_DAY);
            tipoEdad[1] = "0";
        }
        else if (age.get(Calendar.DAY_OF_MONTH) >= 1 && age.get(Calendar.MONTH) < 1)
        {
            tipoEdad[0] = "" + age.get(Calendar.DAY_OF_MONTH);
            tipoEdad[1] = "1";
        }
        else if ((age.get(Calendar.YEAR) - 1970) < 1)
        {
            tipoEdad[0] = "" + age.get(Calendar.MONTH);
            tipoEdad[1] = "2";
        }
        else
        {
            tipoEdad[0] = "" + (age.get(Calendar.YEAR) - 1970);
            tipoEdad[1] = "3";
        }
        return tipoEdad;
    }

    public static String CalculaEdadFormatoConPuntos(Date nacimiento)
    {
        String edad = "";
        String mes;
        String dia;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            final Date birthday = nacimiento;
            long lbirthday = birthday.getTime();

            Calendar age = Calendar.getInstance();
            age.setTimeInMillis(Math.abs(lbirthday - System.currentTimeMillis()));
            if (age.get(Calendar.MONTH) < 10)
            {
                mes = "0" + age.get(Calendar.MONTH);
            }
            else
            {
                mes = "" + age.get(Calendar.MONTH);
            }
            if (age.get(Calendar.DAY_OF_MONTH) < 10)
            {
                dia = "0" + age.get(Calendar.DAY_OF_MONTH);
            }
            else
            {
                dia = "" + age.get(Calendar.DAY_OF_MONTH);
            }
            edad = (age.get(Calendar.YEAR) - 1970 + "." + mes + "." + dia);
        } catch (Exception e)
        {
        }
        return edad;
    }

    public static String CalculaEdadFormatoConPuntos(Date nacimiento, Date fechaCalculo)
    {
        String edad = "";
        String mes;
        String dia;
        try
        {
            final Date birthday = nacimiento;
            long lbirthday = birthday.getTime();

            Calendar age = Calendar.getInstance();
            age.setTimeInMillis(Math.abs(lbirthday - fechaCalculo.getTime()));
            if (age.get(Calendar.MONTH) < 10)
            {
                mes = "0" + age.get(Calendar.MONTH);
            }
            else
            {
                mes = "" + age.get(Calendar.MONTH);
            }
            if (age.get(Calendar.DAY_OF_MONTH) < 10)
            {
                dia = "0" + age.get(Calendar.DAY_OF_MONTH);
            }
            else
            {
                dia = "" + age.get(Calendar.DAY_OF_MONTH);
            }
            edad = (age.get(Calendar.YEAR) - 1970 + "." + mes + "." + dia);
        } catch (Exception e)
        {
        }
        return edad;
    }

    public static Integer restaDias(Calendar fechaActual, Date fechaInicioPa)
    {
        Calendar fechaInicio = Calendar.getInstance();
        fechaInicio.setTime(fechaInicioPa);
        Integer dia = fechaActual.get(Calendar.DAY_OF_YEAR) - fechaInicio.get(Calendar.DAY_OF_YEAR);
        return dia;
    }

    public static String restaHoras(String horaIni, String horaFin) throws Exception
    {
        Date horas = parsearFecha("01/01/2000", FORMATODDMMYYYY);
        Date diaSiguiente = parsearFecha("02/01/2000", FORMATODDMMYYYY);
        Date horaInicio = parsearFecha(horaIni, FORMATOHHMM);
        Date horaFinal = parsearFecha(horaFin, FORMATOHHMM);
        if (horaFinal.getTime() >= horaInicio.getTime())
        {
            horas.setTime(horas.getTime() + horaFinal.getTime() - horaInicio.getTime());
        }
        else
        {
            horas.setTime(horas.getTime() + (diaSiguiente.getTime() + horaFinal.getTime()) - (horas.getTime() + horaInicio.getTime()));;
        }

        return formatearFecha(horas, FORMATOHHMM);
    }

    public static float dosDecimales(float monto)
    {
        DecimalFormat formateador = new DecimalFormat("########0.00");
        return Float.parseFloat(formateador.format(monto));
    }

    public static String stringDosDecimales(float monto)
    {
        DecimalFormat formateador = new DecimalFormat("########0.00");
        Float cantidad = Float.parseFloat(formateador.format(monto));
        return cantidad.toString();
    }
    
    public static String stringDosDecimalesSeparador(float monto)
    {
        DecimalFormat formateador = new DecimalFormat("###,###,##0.00");
        return formateador.format(monto);
    }

    public static String darFormatoMoneda(float monto)
    {
        DecimalFormat dosdec = new DecimalFormat("###,###,##0.00");
        return "$" + dosdec.format(monto);
    }

    public static String agregarCeros(int numero, int tamanioCadena)
    {
        String cadena = String.valueOf(numero);
        for (int i = 0; i < tamanioCadena; i++)
        {
            if (cadena.length() < tamanioCadena)
            {
                cadena = "0" + cadena;
            }
        }
        return cadena;
    }

    public static String formatoPrimeraMayu(String cadena)
    {
        char[] chars = cadena.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++)
        {
            if (!found && Character.isLetter(chars[i]))
            {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            }
            else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
            { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String splitEspacio(String[] cadena)
    {
        String concatena = "";
        for (int i = 0; i < cadena.length; i++)
        {
            if (cadena[i].length() >= 2)
            {
                concatena += (i + 1 == cadena.length) ? cadena[i] : cadena[i] + " ";
            }
            else if (cadena[i].length() == 1)
            {
                int codigoAscii = cadena[i].codePointAt(0);
                if ((codigoAscii >= 64 && codigoAscii <= 90) || (codigoAscii >= 97 && codigoAscii <= 122))
                {
                    concatena += (i + 1 == cadena.length) ? cadena[i] : cadena[i] + " ";
                }
            }
        }

        return concatena.toUpperCase();
    }

    public static long diasPrescripcion(Date fechaInicio, Date fechaTermino)
    {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(fechaInicio);
        cal2.setTime(fechaTermino);
        long milliseconds1 = cal1.getTimeInMillis();
        long milliseconds2 = cal2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }

    public static String getHorasMinEntreFechas(Date fechaInicla, Date fechaFinal)
    {
        String diff = "";
        long timeDiff = Math.abs(fechaFinal.getTime() - fechaInicla.getTime());
        diff = String.format("%d horas(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        return diff;
    }

    public static void unZip(String zipFile, String outputFolder, String uniqueFileName) throws Exception
    {
        byte[] buffer = new byte[1024];

        //create output directory is not exists
        File folder = new File(outputFolder);
        if (!folder.exists())
        {
            folder.mkdir();
        }

        //get the zip file content
        ZipInputStream zis
                = new ZipInputStream(new FileInputStream(zipFile));
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while (ze != null)
        {
            
            String name = uniqueFileName == null ? ze.getName() : uniqueFileName;
            File newFile = new File(outputFolder + File.separator + name);

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }
    
    public static String CalculaEdadSolicitudFormatoConPuntos(Date nacimiento, Date fechaSolicitud)
    {
        String edad = "";
        String mes;
        String dia;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            final Date birthday = nacimiento;
            long lbirthday = birthday.getTime();
            long lbirSolicitud = fechaSolicitud.getTime();

            Calendar age = Calendar.getInstance();
            age.setTimeInMillis(Math.abs(lbirthday - lbirSolicitud));
            if (age.get(Calendar.MONTH) < 10)
            {
                mes = "0" + age.get(Calendar.MONTH);
            }
            else
            {
                mes = "" + age.get(Calendar.MONTH);
            }
            if (age.get(Calendar.DAY_OF_MONTH) < 10)
            {
                dia = "0" + age.get(Calendar.DAY_OF_MONTH);
            }
            else
            {
                dia = "" + age.get(Calendar.DAY_OF_MONTH);
            }
            edad = (age.get(Calendar.YEAR) - 1970 + "." + mes + "." + dia);
        } catch (Exception e)
        {
        }
        return edad;
    }
}
