package service;

import model.AppMail;
import model.Rental;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static model.util.Constants.CARPND_MAIL;
import static model.util.Constants.CARPND_PASSWORD;

/**
 * Created by mariano on 15/07/18.
 */

public class MailSenderService extends GenericService<Rental> {


    private static final long serialVersionUID = 123L;


    @Transactional
    public void sendMail(String mailTo, String subject, String body){

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        final String username = CARPND_MAIL;
        final String password = CARPND_PASSWORD;


        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("carpnd.2018.grupo.d@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo,false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
    }


    @Transactional
    public void notificateUsers(String ownerMail, String clientMail, String ownerSubject, String clientSubject, String ownerBody, String clientBody){
        this.sendMail(ownerMail, ownerSubject, ownerBody);
        this.sendMail(clientMail, clientSubject, clientBody);
    }




}
