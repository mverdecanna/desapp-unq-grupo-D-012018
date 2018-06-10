package model.builder;

import model.Rental;
import model.User;
import model.Vehicle;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class RentalBuilder {

    public enum RentalState{
        IN_PROGRESS, WAIT_CONFIRM, DONE, CANCEL
    }

    private String id;
    private DateTime startDate;
    private DateTime endDate;
    private String ownerCuil;
    private String clientCuil;
    private String vehicleID;
    private Rental.RentalState state;



    public RentalBuilder(){}


    public RentalBuilder(String ownerCuil, String clientCuil, String vehicleID){
        this.state = Rental.RentalState.WAIT_CONFIRM;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
    }



    public RentalBuilder(String id, DateTime startDate, DateTime endDate, String ownerCuil, String clientCuil, String vehicleID){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
        this.state = Rental.RentalState.WAIT_CONFIRM;
    }



    public RentalBuilder(DateTime startDate, DateTime endDate, String ownerCuil, String clientCuil, String vehicleID){
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
        this.state = Rental.RentalState.WAIT_CONFIRM;
    }


    public RentalBuilder setId(String id){
        this.id = id;
        return this;
    }


    public RentalBuilder setState(Rental.RentalState state){
        this.state = state;
        return this;
    }


    public RentalBuilder setStartDate(DateTime startDate){
        this.startDate = startDate;
        return this;
    }


    public RentalBuilder setEndDate(DateTime endDate){
        this.endDate = endDate;
        return this;
    }

    public RentalBuilder setOwner(String ownerCuil){
        this.ownerCuil = ownerCuil;
        return this;
    }


    public RentalBuilder setClient(String clientCuil){
        this.clientCuil = clientCuil;
        return this;
    }

    public RentalBuilder setVehicle(String vehicleID){
        this.vehicleID = vehicleID;
        return this;
    }



    public Rental build(){
        return new Rental(this.id, this.startDate, this.endDate, this.ownerCuil, this.clientCuil, this.vehicleID);
    }


}
