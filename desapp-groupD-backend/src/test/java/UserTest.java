import model.User;
import model.Vehicle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 27/03/18.
 */
public class UserTest {


    @Test
    public void userAddOneVehicleTest(){
        User user = new User("Lalo","Landa", 123456789);
        Vehicle vehicle = new Vehicle(Vehicle.VehicleType.CAR);
        user.addVehicle(vehicle);
        Assert.assertTrue(user.getVehicles().size() == 1);
    }


    @Test
    public void userReputationIsGoodTest(){
        User user = new User("Lalo","Landa", 123456789);
        user.scoreUser(3d);
        user.scoreUser(5d);
        Assert.assertTrue(user.evalReputation() > 3);
    }


}
