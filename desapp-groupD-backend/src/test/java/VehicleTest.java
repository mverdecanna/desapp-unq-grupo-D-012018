import model.Vehicle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 27/03/18.
 */
public class VehicleTest {


    @Test
    public void vehicleIsAVanType(){
        Vehicle vehicle = new Vehicle(Vehicle.VehicleType.VAN);
        Assert.assertTrue(vehicle.getTypeName().equals("VAN"));
    }



}
