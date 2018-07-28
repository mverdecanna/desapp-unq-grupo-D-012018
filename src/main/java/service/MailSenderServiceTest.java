package service;

import java.util.logging.Logger;

/**
 * Created by mariano on 28/07/18.
 */
public class MailSenderServiceTest extends MailSenderService {

    private Logger logger = Logger.getLogger(MailSenderServiceTest.class.getName());


    public void sendMail(String mailTo, String subject, String body){
        logger.info("MAIL: "+mailTo+" Subject: "+"Body: "+body);
    }


    public void notificateUsers(String ownerMail, String clientMail, String ownerSubject, String clientSubject, String ownerBody, String clientBody){
        this.sendMail(ownerMail, ownerSubject, ownerBody);
        this.sendMail(clientMail, clientSubject, clientBody);
    }



}
