package aspects;

import model.AppMail;
import model.Rental;
import model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import service.UserService;

@Aspect
public class Mailer {

    private UserService userService;


    public void setUserService(final UserService service) {
        this.userService = service;
    }


    public UserService getUserService() {
        return userService;
    }

    String ownerMail, clientMail;


    @Around("@annotation(MailerNotification)")
    public void executeMailer(JoinPoint jp) {
        System.out.println(jp.getThis());
        System.out.println(jp.getArgs());
        System.out.println(jp.getKind());
        System.out.println(jp.getSignature());
        System.out.println(jp.getStaticPart());
        Rental ren = (Rental) jp.getArgs()[0];
        String ocuil = ren.getOwnerCuil();
        String ccuil = ren.getClientCuil();
        /*User owner = userSeervice.findById(ocuil);
        User client = userServic.findById(ccuil);
        ownerMail = owner.getEmail();
        clientMail = client.getEmail();*/


        for (Object o : jp.getArgs()) {

            System.out.println(o.toString());
        }
        AppMail.sendMail("mverdecanna@gmail.com", "Rental", "User id created a rental");
        // AppMail.sendMail("mverdecanna@gmail.com", "Rental", "A rental was created by a user");
    }



}
