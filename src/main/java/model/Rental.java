package model;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mariano on 03/04/18.
 */

@Entity
@Table(name="rental", schema = "carpnd")
@XmlRootElement(name="Rental")
public class Rental implements Serializable {

    public enum RentalState{
        WAIT_CONFIRM, CONFIRM, IN_USE, DONE, REJECTED, CANCEL, RETURNED, SCORED
        // cuando el cliente solicita el alquiler, se crea (y mientras espera que lo confirmen)  -->  WAIT_CONFIRM
        // si rechazan la solicitud de alquiler  -->  REJECTED
        // cuando lo confirman y se crea la Transaction  -->  CONFIRM
        // cuando retira el vehiculo -->   IN_USE
        // cuando paga  -->  DONE
        // cuando devuelve el vehiculo  -->  RETURNED
        // cuando ambos usuarios se puntuan  -->  SCORED
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="startDate")
    private DateTime startDate;

    @Column(name="endDate")
    private DateTime endDate;

    @Column(name="ownerCuil")
    private String ownerCuil;

    @Column(name="clientCuil")
    private String clientCuil;

    @Column(name="vehicle_id")
    private String vehicleID;

    @Column(name="state", length = 12)
    @Enumerated(EnumType.STRING)
    private RentalState state;


    public Rental(){}


    public Rental(String id, DateTime startDate, DateTime endDate, String ownerCuil, String clientCuil, String vehicleID){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = RentalState.WAIT_CONFIRM;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
    }


    public Rental(String ownerCuil, String clientCuil, String vehicleID){
        this.state = RentalState.WAIT_CONFIRM;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
    }

    public Rental(DateTime startDate, DateTime endDate, String ownerCuil, String clientCuil, String vehicleID){
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = RentalState.WAIT_CONFIRM;
        this.ownerCuil = ownerCuil;
        this.clientCuil = clientCuil;
        this.vehicleID = vehicleID;
    }



///  init  getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getOwnerCuil() {
        return ownerCuil;
    }

    public void setOwnerCuil(String ownerCuil) {
        this.ownerCuil = ownerCuil;
    }

    public String getClientCuil() {
        return clientCuil;
    }

    public void setClientCuil(String clientCuil) {
        this.clientCuil = clientCuil;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public RentalState getState() {
        return state;
    }

    public String stateName() {
        return state.name();
    }

    public void setState(RentalState state) {
        this.state = state;
    }


    ///  end  getters and setters



    public void initRental(){
        this.setState(RentalState.CONFIRM);
    }


    public void cancelRental(){
        this.setState(RentalState.CANCEL);
    }


    public void rejectRental(){
        this.setState(RentalState.REJECTED);
    }


    public void catchTheVehicle(){
        this.setState(RentalState.IN_USE);
    }


    public void isDone(){
        this.setState(RentalState.DONE);
    }


    public void returnedVehicle(){
        this.setState(RentalState.DONE);
    }


}
