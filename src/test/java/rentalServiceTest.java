import model.*;
import model.builder.RentalBuilder;
import model.builder.TransactionBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import persistence.RentalRepository;
import service.MailSenderService;
import service.RentalService;
import webservice.RentalRest;

import javax.ws.rs.core.Response;
import java.util.Date;

import static model.util.Constants.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * Created by mariano on 09/07/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-test-context.xml"})
public class rentalServiceTest {


    @Autowired
    private RentalService rentalService;


    @Test
    @Transactional
    public void testCreateTransactionSetRentalStatusConfirm(){
        User userOwner = new UserBuilder().setName("Pepe").setCuil("20320231680").build();
        User userClient = new UserBuilder().setName("Lolo").setCuil("20320231680").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).setId("111").build();
        Rental rental = new Rental(userOwner.getCuil(), userClient.getCuil(), vehicle.getId());
        Transaction transaction = new TransactionBuilder().setRental(rental).setCost(100).build();
        Assert.assertEquals(rental.getState(), Rental.RentalState.WAIT_CONFIRM);
        transaction = this.rentalService.createTransaction(transaction);
        Assert.assertEquals(transaction.getRental().getState(), Rental.RentalState.CONFIRM);
        Assert.assertEquals(transaction.getState(), Transaction.StateTransaction.RESERVATION);
    }



}