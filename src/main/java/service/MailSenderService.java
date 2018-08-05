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
public abstract class MailSenderService extends GenericService<Rental>{


   // private static final long serialVersionUID = 123L;



    public abstract void sendMail(String mailTo, String subject, String body);


    public abstract void notificateUsers(String ownerMail, String clientMail, String ownerSubject, String clientSubject, String ownerBody, String clientBody);



}
