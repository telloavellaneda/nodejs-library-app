package com.mg43.service;

import com.mg43.model.Departamento;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.HashMap;

public class SendEmail {
    @SuppressWarnings("unused")
	private final String application;

	public static Logger log;
    private final String folder;

    static {
        log = Logger.getLogger(SendEmail.class.getName());
    }

    public SendEmail(String folder, String application) {
        this.folder = folder;
        this.application = application;
    }

    public void sendEmail(HashMap<String, Departamento> directorio) throws Exception {
        log.info("");
        log.info("Inicia el envio por correo electronico");

        for (Departamento departamento : directorio.values()) {
            String body = "<span style=\"font-family:Arial; font-size:12px;\">"
                    + "Apreciable <strong>Cond&oacute;mino</strong>,"
                    + "<br><br>"
                    + "Adjunto encontrar&aacute;s el recibo de cuotas de mantenimiento del Departamento <strong>" + departamento.getDepartamento() + "</strong>, "
                    + "correspondiente al mes de <strong>" + departamento.getMes() + "</strong>."
                    + "<br>"
                    + "En la segunda p&aacute;gina podr&aacute;s observar el <strong>resumen financiero</strong>, con la informaci&oacute;n de dep&oacute;sitos y retiros de la cuenta. <i>(Favor de revisar la secci&oacute;n de <strong>Notas</strong>).</i>"
                    + "<br>"
                    + "En la tercer y &uacute;ltima p&aacute;gina, encontrar&aacute;s el <strong>resumen de cobranza</strong> al corte del Mes. <i>(Cualquier duda que tengas, por favor no dudes en contactar a la Administraci&oacute;n).</i>"
                    + "<br><br>"
                    + "La emisi&oacute;n de este documento fue cotejada con el Estado de Cuenta bancario del Mes al que hace referencia."
                    + "<br><br>"
                    + "Cualquier duda al respecto por favor comp&aacute;rtela por este medio."
                    + "<br><br>"
                    + "Recibe un saludo."
                    + "<br><br>"
                    + "<strong>Maricopa 43</strong> | Administraci&oacute;n | administracion@maricopa43.com"
                    + "</span>";

            String prependedSubject = parseAdeudo(departamento.getAdeudo());

            com.mg43.model.Email email = new com.mg43.model.Email();
            email.setTo((departamento.getEmail() + "," + departamento.getEmailComplemento()).split(","));
            // email.setTo(new String[] { "sateav@gmail.com" });
            email.setReply(new String[]{"administracion@maricopa43.com"});
            email.setFrom("administracion@maricopa43.com");
            email.setFromLabel("Administracion M43");
            email.setSubject( /*"FE DE ERRATAS -> " + */ prependedSubject + "[Depto. " + departamento.getDepartamento() + "] Recibo del Mes de " + departamento.getMes());
            email.setBody(body);
            email.setFileNames(new String[]{departamento.getArchivo()});
            email.setFileNamesDir(folder + "/output/");

//             if (departamento.getDepartamento().equals("702") )
                new com.mg43.google.GmailSender(folder).sendEmail(com.mg43.google.GmailSender.getGmailService(application), email);

            log.info("Enviado a " + departamento.getDepartamento() + "\t\t" + departamento.getEmail() + " | " + departamento.getEmailComplemento());
        }
    }

    private String parseAdeudo(String value) {
        try {
            double temp = new DecimalFormat("#,##0.00").parse(value).doubleValue();
            if ( temp > 0 )
                return "¡ADEUDO! ";
            else if ( temp < 0 )
                return "¡SALDO A FAVOR! ";
            else
                return "";
        } catch (Exception exception) {
            return "";
        }
    }
}