import model.CurrentAccount;
import model.User;
import model.Vehicle;
import model.builder.CurrentAccountBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 27/03/18.
 */
public class UserTest {


    @Test
    public void userAddOneVehicleTest(){
        User user = new UserBuilder().setCuil(123456789).setName("Lalo").setSurname("Landa").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        user.addVehicle(vehicle);
        Assert.assertTrue(user.getVehicles().size() == 1);
    }


    @Test
    public void userReputationIsGoodTest(){
        User user = new UserBuilder().setCuil(123456789).setName("Lalo").setSurname("Landa").build();
        user.scoreUser(3d);
        user.scoreUser(5d);
        Assert.assertTrue(user.evalReputation() > 3);
    }



    @Test
    public void userPayCreditsAndSubstractToYourCurrentAccount(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(100);
        user.payCredit(50);
        Assert.assertTrue(user.getBalance() == 50);
    }


    @Test
    public void userReceiveCreditsAndAggregateToYourCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.receiveCredit(200);
        Assert.assertTrue(user.getBalance() == 200);
    }



    @Test
    public void userInsufficientCreditsToRentACar(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).setCost(80).build();
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(50);
        Assert.assertFalse(user.canPayForThis(vehicle));
    }


}
