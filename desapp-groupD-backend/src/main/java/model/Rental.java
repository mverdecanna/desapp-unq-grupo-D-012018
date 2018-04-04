package model;

import java.util.Date;

/**
 * Created by mariano on 03/04/18.
 */
public class Rental {

    public enum RentalState{
        DONE, CANCEL, IN_PROGRESS
    }

    private Long id;
    private Date startDate;
    private Date endDate;
    private User owner;
    private User client;
    private Vehicle vehicle;
    private RentalState state;


    public Rental(){}


    public Rental(User owner, User client, Vehicle vehicle){
        this.state = RentalState.IN_PROGRESS;
    }


}
