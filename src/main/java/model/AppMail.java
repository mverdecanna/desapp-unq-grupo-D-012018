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



/**
 * Created by mariano on 27/03/18.
 */

public class AppMail {






    public static void main(String [] args) {
/*
            // Recipient's email ID needs to be mentioned.
            String to = "mverdecanna@gmail.com";

            // Sender's email ID needs to be mentioned
            String from = "mverdecanna@gmail.com";

            // Assuming you are sending email from localhost
            String host = "localhost";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);

            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties);

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("This is the Subject Line!");

                // Now set the actual message
                message.setText("This is actual message");

                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
        }
*/



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

     final String username = "carpnd.2018.grupo.d@gmail.com";
     final String password = "traemelacopamessi";


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




