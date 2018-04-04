package model;

import java.util.Date;

/**
 * Created by mariano on 27/03/18.
 */
public class Transaction {


    public enum StateTransaction{
        RESERVATION, CONFIRM, RETIRED, IN_USE, FINALIZED, CLOSED, CANCEL, REJECTED
    }

    private Long id;
    private Integer cost;
    private Date date;
    private StateTransaction state;
    private Rental rental;


    public Transaction(){}


    public Transaction(Integer cost){
        this.state = StateTransaction.RESERVATION;
        this.cost = cost;
    }






}
