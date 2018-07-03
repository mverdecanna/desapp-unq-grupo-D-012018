package model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import static model.util.Constants.CARPND_MAIL;
import static model.util.Constants.CARPND_PASSWORD;


/**
 * Created by mariano on 27/03/18.
 */

public class AppMail {




    public static void sendMail(String mailTo, String subject, String body){

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
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo,false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
    }






    public static void main(String [] args) {

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
         msg.setFrom(new InternetAddress("mverdecanna@gmail.com"));
         msg.setRecipients(Message.RecipientType.TO,
                 InternetAddress.parse("mverdecanna@gmail.com",false));
         msg.setSubject("Hello");
         msg.setText("How are you");
         msg.setSentDate(new Date());
         Transport.send(msg);
         System.out.println("Message sent.");
     }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
        }



}




