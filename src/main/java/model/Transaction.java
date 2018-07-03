package model;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by mariano on 27/03/18.
 */

@Entity
@Table(name="transactions", schema = "carpnd")
@XmlRootElement(name="Transaction")
public class Transaction {


    public enum StateTransaction{
        RESERVATION, REJECTED, CANCEL, PAID, COMPLETE, FINALIZED
        // cuando se crea la transaccion (cuando el dueño confirma)  --> RESERVATION
        // cuando el cliente paga  -->  PAID
        // cuando devuelve el vehiculo  -->  COMPLETE
        // cuando puntua al otro usuario  -->  FINALIZED
        // los estados  de REJECTED o CANCEL  hay que ver ante que evento pueden ir
    }


    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="cost")
    private Integer cost;

    @Column(name="created")
    private Date create;

    @Column(name="lastUpdated")
    private Date lastUpdate;

    @Column(name="state")
    private StateTransaction state;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name = "rental", joinColumns = {
            @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "id") })
    private Rental rental;


    public Transaction(){}


    public Transaction(String id, Integer cost, Date create, Date lastUpdate, Rental rental){
        this.id = id;
        this.cost = cost;
        this.create = create;
        this.lastUpdate = lastUpdate;
        this.state = StateTransaction.RESERVATION;
        this.rental = rental;
    }


    public Transaction(Integer cost, Rental rental){
        this.state = StateTransaction.RESERVATION;
        this.create = new Date();
        this.lastUpdate = new Date();
        this.cost = cost;
        this.rental = rental;
    }


    ///  init  getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public StateTransaction getState() {
        return state;
    }

    public String stateName() {
        return state.name();
    }

    public void setState(StateTransaction state) {
        this.state = state;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }


    ///  end  getters and setters


    public void payTransaction(){
        this.setState(StateTransaction.PAID);
        this.rental.isDone();
        this.lastUpdate = new Date();
    }


    public void cancelTransaction(){
        this.setState(StateTransaction.CANCEL);
        this.rental.cancelRental();
        this.lastUpdate = new Date();
    }


    public void completeTransaction(){
        this.setState(StateTransaction.COMPLETE);
        this.rental.returnedVehicle();
        this.lastUpdate = new Date();
    }


    public void finalizeTransaction(){
        this.setState(StateTransaction.FINALIZED);
        this.lastUpdate = new Date();
    }


    public void initializeTransaction(){
        this.setState(StateTransaction.RESERVATION);
        this.rental.initRental();
        this.lastUpdate = new Date();
    }


    public void markCollectVehicle(){
        this.rental.catchTheVehicle();
        this.lastUpdate = new Date();
    }




}
