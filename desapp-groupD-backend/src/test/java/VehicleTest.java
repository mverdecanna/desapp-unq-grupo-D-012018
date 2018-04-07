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


    @Test
    public void vehicleDescriptionIsValidWith67Chars(){
        String description = "La moto esta en un excelente estado, y es muy comoda, es un fierro!";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE)
                .setDescription(description).build();
        Assert.assertTrue(vehicle.isValidDescription(description));
    }


    @Test
    public void vehicleDescriptionIsNotValidWithAShortDescription(){
        String description = "La moto esta muy buena";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE)
                .setDescription(description).build();
        Assert.assertFalse(vehicle.isValidDescription(description));
    }


    @Test
    public void vehiclePhoneNumberIsValid(){
        String number = "666444555";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Assert.assertTrue(vehicle.isValidPhoneNumber(number));
    }


    @Test
    public void vehiclePhoneNumberIsNotValidBecauseIsTooLong(){
        String number = "11112222233333444445555555";
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Assert.assertFalse(vehicle.isValidPhoneNumber(number));
    }


}
