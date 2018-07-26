package model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * Created by mariano on 27/03/18.
 */
@Entity
@Table(name="vehicles", schema = "carpnd")
@XmlRootElement(name="Vehicle")
public class Vehicle implements Serializable {


    public enum VehicleType{
        MOTORCYCLE, CAR, VAN, TRUCK
    }

    @Id
    @Column(name="id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    @Column(name="type", length = 10)
    @Enumerated(EnumType.STRING)
    private VehicleType type;


    @Column(name="capacity")
    private Integer capacity;


    @Column(name="location")
    private String location;


    @Column(name="retirementAddress")
    private String retirementAddress;   //  MAPA


    @Column(name="returnAddress")
    private String returnAddress;   //  MAPA  -  List


    @Column(name="description")
    private String description;


    @Column(name="phone")
    private String phone;


    @Column(name="cost")
    private Integer cost;


    @Column(name="ownerCuil")
    private String ownerCuil;


    @Column(name="photo")
    private String photo;



    public Vehicle(){}

    public Vehicle(VehicleType type){
        this.type = type;
    }

    public Vehicle(String id, VehicleType type, Integer capacity, String location, String retirementAddress,
                   String returnAddress, String description, String phone, Integer cost, String ownerCuil, String photo){
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.retirementAddress = retirementAddress;
        this.returnAddress = returnAddress;
        this.description = description;
        this.phone = phone;
        this.cost = cost;
        this.ownerCuil = ownerCuil;
        this.photo = photo;
    }



    public VehicleType getType() {
        return type;
    }

    public String typeName(){
        return type.name();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRetirementAddress() {
        return retirementAddress;
    }

    public void setRetirementAddress(String retirementAddress) {
        this.retirementAddress = retirementAddress;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
/*
    public String getPlanilla() {
        return planilla;
    }

    public void setPlanilla(String planilla) {
        this.planilla = planilla;
    }
*/
    public Integer getCost() {
        return cost;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public void revalueateCost(Integer newcost){
        this.setCost(newcost);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOwnerCuil() {
        return ownerCuil;
    }

    public void setOwnerCuil(String ownerCuil) {
        this.ownerCuil = ownerCuil;
    }

    public Boolean isValidDescription(String name){
        return (name.length() >= 30) && (name.length() <= 200);
    }


    public Boolean isValidPhoneNumber(String phoneNumber){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phone = new PhoneNumber();
        try {
            phone = phoneUtil.parse(phoneNumber, "ES");
        } catch (NumberParseException e) {
            System.err.println("InvalidPhoneNumberException was thrown: " + e.toString());
        }
        //phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        return phoneUtil.isValidNumber(phone);
    }



    public void setCost(Integer cost) {
        this.cost = cost;
    }

}
