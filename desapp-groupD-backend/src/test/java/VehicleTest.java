import model.Vehicle;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 27/03/18.
 */
public class VehicleTest {


    @Test
    public void vehicleIsAVanType(){
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        Assert.assertTrue(vehicle.getTypeName().equals("VAN"));
    }



}
