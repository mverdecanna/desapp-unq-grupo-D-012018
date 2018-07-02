import model.Rental;
import model.User;
import model.Vehicle;
import model.builder.RentalBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class RentalTest {


    @Test
    public void  rentalConstructorTest(){
        //DateTime date = new DateTime();
        Date date = new Date();
        User userOwner = new UserBuilder().setName("Pepe").setCuil("22334455").build();
        User userClient = new UserBuilder().setName("Lolo").setCuil("22334455").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.TRUCK).build();
        Rental rental = new RentalBuilder().setId("2").setStartDate(date).setOwner(userOwner.getCuil())
                .setClient(userClient.getCuil()).setVehicle(vehicle.getId()).setState(Rental.RentalState.WAIT_CONFIRM).build();
        rental.setEndDate(date);
        Assert.assertTrue(rental.getStartDate().equals(date) && rental.getEndDate().equals(date)
                && rental.getOwnerCuil().equals(userOwner.getCuil()) && rental.getClientCuil().equals(userClient.getCuil())
                && rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
    }


    @Test
    public void initRentalChangeStateToInProgressTest(){
        User userOwner = new UserBuilder().setName("Pepe").build();
        User userClient = new UserBuilder().setName("Lolo").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        Rental rental = new Rental(userOwner.getCuil(), userClient.getCuil(), vehicle.getId());
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
        rental.initRental();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.CONFIRM));
    }


    @Test
    public void cancelRentalChangeStateToCancelTest(){
        User userOwner = new UserBuilder().setName("Charlie").build();
        User userClient = new UserBuilder().setName("Vladimir").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.TRUCK).build();
        Rental rental = new RentalBuilder().setOwner(userOwner.getCuil()).setClient(userClient.getCuil()).setVehicle(vehicle.getId()).build();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
        rental.cancelRental();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.CANCEL));
    }




}
