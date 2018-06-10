import model.Rental;
import model.Transaction;
import model.User;
import model.Vehicle;
import model.builder.RentalBuilder;
import model.builder.TransactionBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class TransactionTest {


    @Test
    public void  transactionConstructorTest(){
        DateTime date = new DateTime();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).build();
        Rental rental = new RentalBuilder().setId("2222").setStartDate(date).setEndDate(date).setOwner(userOwner.getCuil())
                .setClient(userClient.getCuil()).setVehicle(vehicle.getId()).build();
        Transaction transaction = new Transaction(500, rental);
        Assert.assertTrue(transaction.getCost().equals(500) && transaction.getRental().equals(rental)
                && transaction.getState().equals(Transaction.StateTransaction.RESERVATION));
    }


    @Test
    public void cancelTransactionChangeStateToCancelAndRentalStateInCancel(){
        DateTime date = new DateTime();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).build();
        Rental rental = new RentalBuilder().setId("2222").setStartDate(date).setEndDate(date).setOwner(userOwner.getCuil())
                .setClient(userClient.getCuil()).setVehicle(vehicle.getId()).build();
        Transaction transaction = new TransactionBuilder().setId(13l).setCost(500).setRental(rental).build();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.RESERVATION)
                && transaction.getRental().getState().equals(Rental.RentalState.WAIT_CONFIRM));
        transaction.cancelTransaction();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.CANCEL) && transaction.getRental().getState().equals(Rental.RentalState.CANCEL));
    }



    @Test
    public void confirmTransactionChangeStateToConfirmAndRentalStateToInProgressTest(){
        DateTime date = new DateTime();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Rental rental = new RentalBuilder().setId("455").setStartDate(date).setEndDate(date).setOwner(userOwner.getCuil())
                .setClient(userClient.getCuil()).setVehicle(vehicle.getId()).build();
        Transaction transaction = new TransactionBuilder().setId(13l).setRental(rental).build();
        transaction.setCost(700);
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.RESERVATION) && transaction.getRental().getState().equals(Rental.RentalState.WAIT_CONFIRM));
        transaction.confirmTransaction();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.CONFIRM) && transaction.getRental().getState().equals(Rental.RentalState.IN_PROGRESS));
    }



}
