/*
 * Copyright (C) 2014 Ashley Heyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import java.util.List;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EnvioCorreoServiceImpl implements EnvioCorreoService
{
    private static final Log log = LogFactory.getLog(EnvioCorreoServiceImpl.class);
    private JavaMailSenderImpl mailSender;
    private String from;
    public boolean active = true;

    public void enviarMensaje(String asunto, String mensaje, String correosTO, String correosCC, String correosCCO, List<FileSystemResource> archivos, boolean html) throws Exception
    {

        if (log.isDebugEnabled())
        {
            final boolean usingPassword = !"".equals(mailSender.getPassword());
            log.debug("Sending email to: '" + correosTO + "' [through host: '" + mailSender.getHost() + ":"
                    + mailSender.getPort() + "', username: '" + mailSender.getUsername() + "' usingPassword:"
                    + usingPassword + "].");
            log.debug("isActive: " + active);
        }

        if (!active)
        {
            return;
        }

        final MimeMessage message = mailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        FileSystemResource file = new FileSystemResource("C:\\log.txt");
//        helper.addAttachment(file.getFilename(), file);
        helper.setTo(InternetAddress.parse(correosTO));
        helper.setCc(InternetAddress.parse(correosCC));
        helper.setBcc(InternetAddress.parse(correosCCO));
        helper.setSubject(asunto);
        helper.setFrom(getFrom());
        
        if (html)
        {
            helper.setText( mensaje, true);
        }
        else
        {
            helper.setText(mensaje);
        }
        
        if (archivos != null)
        {
            for (FileSystemResource file : archivos)
            {
                helper.addAttachment(file.getFilename(), file);
            }
        }
        // el envío  
        this.mailSender.send(message);
    }

    public void setMailSender(JavaMailSenderImpl mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getFrom()
    {
        return from;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
