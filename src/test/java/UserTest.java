import model.CurrentAccount;
import model.Score;
import model.User;
import model.Vehicle;
import model.builder.CurrentAccountBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import model.util.CuilValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 27/03/18.
 */
public class UserTest {

    @Test
    public void  userConstructorTest(){
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").setAddress("Avenida SiempreViva 111").setEmail("desapp@unq.com").build();
        Assert.assertTrue(user.getCuil().equals("20320231680") && user.getName().equals("Lalo") && user.getAddress().equals("Avenida SiempreViva 111")
                && user.getEmail().equals("desapp@unq.com") && user.getSurname().equals("Landa"));
    }
    @Test
    public void userAddListVehicleTest(){
        List<Vehicle> vehicles= new ArrayList<Vehicle>();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        vehicles.add(vehicle);
        User user = new UserBuilder().setVehicles(vehicles).build();
        Assert.assertTrue(user.getVehicles().contains(vehicle));
    }
    @Test
    public void changeEmailAccountTest(){
        User user = new UserBuilder().setEmail("desapp@unq.com.ar").build();
        Assert.assertEquals(user.getEmail(),"desapp@unq.com.ar");
        user.setEmail("lulalivre@unq.com.ar");
        Assert.assertEquals(user.getEmail(),"lulalivre@unq.com.ar");
    }
    @Test
    public void setCurrentAccountAndModifiedTest(){
        CurrentAccount account = new CurrentAccountBuilder().setId("1").build();
        CurrentAccount account1 = new CurrentAccountBuilder().setId("2").build();
        User user = new UserBuilder().setCurrentAccount(account).build();
        Assert.assertEquals(user.getCurrentAccount().getCuil(),account.getCuil());
        user.setCurrentAccount(account1);
        Assert.assertEquals(user.getCurrentAccount().getCuil(),account1.getCuil());

    }
    @Test
    public void userAddOneVehicleTest(){
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        user.addVehicle(vehicle);
        Assert.assertTrue(user.getVehicles().size() == 1);
    }


    @Test
    public void userReputationIsGoodTest(){
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").build();
        Score score3 = new Score("1", 3, "3 puntos", "22", "2032023168");
        Score score5 = new Score("2", 5, "5 puntos", "23", "2032023168");
        user.scoreUser(score3);
        user.scoreUser(score5);
        Assert.assertTrue(user.evalReputation() > 3);
        Assert.assertTrue(user.evalReputation() == 4);
    }



    @Test
    public void userPayCreditsAndSubstractToYourCurrentAccount(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId("11").build();
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(100);
        user.payCredit(50);
        Assert.assertTrue(user.balance() == 50);
    }


    @Test
    public void userReceiveCreditsAndAggregateToYourCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId("11").build();
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.receiveCredit(200);
        Assert.assertTrue(user.balance() == 200);
    }



    @Test
    public void userInsufficientCreditsToRentACar(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).setCost(80).build();
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId("11").build();
        User user = new UserBuilder().setCuil("20320231680").setName("Lalo").setSurname("Landa").setCurrentAccount(currentAccount).build();
        user.addCreditInMyAccount(50);
        Assert.assertFalse(user.canPayForThis(vehicle.getCost()));
    }


    @Test
    public void userNameIsValidForANormalNames(){
        String name = "Mariano Carlos";
        User user = new UserBuilder().setCuil("20320231680").build();
        Assert.assertTrue(user.isValidName(name));
    }

    @Test
    public void userSurnameIsNotValidBecauseIsVeryShort(){
        String name = "Cuo";
        User user = new UserBuilder().setCuil("20320231680").build();
        Assert.assertFalse(user.isValidSurname(name));
    }


    @Test
    public void userCuilIsValidWithACorrectLength(){
        String cuil = "20320231680";
        Assert.assertTrue(CuilValidator.isValid(cuil));
    }


    @Test
    public void userCuilIsNotValidWithAIncorrectLength(){
        String cuil = "123456780";
        Assert.assertFalse(CuilValidator.isValid(cuil));
    }


    @Test
    public void userMailIsValidWithACorrectFormat(){
        User user = new UserBuilder().setEmail("lalala@carpnd.com.ar").build();
        Assert.assertTrue(user.validMail());
    }


    @Test
    public void userMailIsNotValidWithAIncorrectFormat(){
        User user = new UserBuilder().setEmail("la!&@carpnd.com.ar").build();
        Assert.assertFalse(user.validMail());
    }


}
