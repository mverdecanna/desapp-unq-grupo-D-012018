import model.CurrentAccount;
import model.Score;
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
        User user = new UserBuilder().setCuil(123456789l).setName("Lalo").setSurname("Landa").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        user.addVehicle(vehicle);
        Assert.assertTrue(user.getVehicles().size() == 1);
    }


    @Test
    public void userReputationIsGoodTest(){
        User user = new UserBuilder().setCuil(123456789l).setName("Lalo").setSurname("Landa").build();
        //Score score3 = new Score(3, "3 puntos");
        //Score score5 = new Score(5, "5 puntos");
        user.scoreUser(3, "3 puntos");
        user.scoreUser(5, "5 puntos");
        Assert.assertTrue(user.evalReputation() > 3);
    }



    @Test
    public void userPayCreditsAndSubstractToYourCurrentAccount(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789l).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(100);
        user.payCredit(50);
        Assert.assertTrue(user.getBalance() == 50);
    }


    @Test
    public void userReceiveCreditsAndAggregateToYourCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789l).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.receiveCredit(200);
        Assert.assertTrue(user.getBalance() == 200);
    }



    @Test
    public void userInsufficientCreditsToRentACar(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).setCost(80).build();
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        User user = new UserBuilder().setCuil(123456789l).setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(50);
        Assert.assertFalse(user.canPayForThis(vehicle));
    }


    @Test
    public void userNameIsValidForANormalNames(){
        String name = "Mariano Carlos";
        User user = new UserBuilder().setCuil(123456789l).build();
        Assert.assertTrue(user.isValidName(name));
    }

    @Test
    public void userSurnameIsNotValidBecauseIsVeryShort(){
        String name = "Cuo";
        User user = new UserBuilder().setCuil(123456789l).build();
        Assert.assertFalse(user.isValidSurname(name));
    }


    @Test
    public void userCuilIsValidWithACorrectLength(){
        Long cuil = 99123456780l;
        User user = new UserBuilder().build();
        Assert.assertTrue(user.isValidCuil(cuil));
    }


    @Test
    public void userCuilIsNotValidWithAIncorrectLength(){
        Long cuil = 123456780l;
        User user = new UserBuilder().build();
        Assert.assertFalse(user.isValidCuil(cuil));
    }


    @Test
    public void userMailIsValidWithACorrectFormat(){
        String mail = "lalala@carpnd.com.ar";
        User user = new UserBuilder().build();
        Assert.assertTrue(user.isValidMail(mail));
    }


    @Test
    public void userMailIsNotValidWithAIncorrectFormat(){
        String mail = "la!&@carpnd.com.ar";
        User user = new UserBuilder().build();
        Assert.assertFalse(user.isValidMail(mail));
    }


}
