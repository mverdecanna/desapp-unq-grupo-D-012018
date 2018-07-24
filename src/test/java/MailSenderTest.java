import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.MailSenderService;
import service.RentalService;

import static org.mockito.Mockito.*;

/**
 * Created by mariano on 19/07/18.
 */


@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class MailSenderTest {


    @Autowired
    private MailSenderService mailSenderService;


    @Test
    public void myTest(){
        //MailSenderService spy = spy(mailSenderService);
        MailSenderService mock = mock(MailSenderService.class);
        doNothing().when(mock).sendMail("mverdecanna@gmail.com", "TEST", ".............................");
        //Mockito.doNothing().when(spy).sendMail("mverdecanna@gmail.com", "TEST", ".............................");
        Assert.assertTrue(true);
    }



}
