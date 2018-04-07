package model;

import model.builder.UserBuilder;
import model.builder.VehicleBuilder;

import java.util.*;

/**
 * Created by mariano on 03/04/18.
 */
public class Orchestrator {

    Map<Long,User> userSystem;
    Map<Integer,Transaction> transactionsSystem;
    MailSender sendMail;
    Map<Integer,Rental> rentalSystem;



    public Orchestrator(){
        this.userSystem=new HashMap<Long, User>();
        this.transactionsSystem= new HashMap<Integer,Transaction>();
        this.sendMail= new MailSender();
        this.rentalSystem=new HashMap<Integer,Rental>();
    }

    public Map<Long, User> getUserSystem() {
        return userSystem;
    }

    public void setUserSystem(Map<Long, User> userSystem) {
        this.userSystem = userSystem;
    }

    public Map<Integer, Transaction> getTransactionsSystem() {
        return transactionsSystem;
    }

    public void setTransactionsSystem(Map<Integer, Transaction> transactionsSystem) {
        this.transactionsSystem = transactionsSystem;
    }

    public MailSender getSendMail() {
        return sendMail;
    }

    public void setSendMail(MailSender sendMail) {
        this.sendMail = sendMail;
    }

    public Map<Integer, Rental> getRentalSystem() {
        return rentalSystem;
    }

    public void setRentalSystem(Map<Integer, Rental> rentalSystem) {
        this.rentalSystem = rentalSystem;
    }

    protected User getUser(Long cuil){
        return userSystem.get(cuil);
    }
    protected void addUser(Long cuil, String name, String surname, String address, String email){
      userSystem.put(cuil,new UserBuilder(cuil,name,surname,address,email).build());

    }
    protected void addVehicle(Integer userCuil, Vehicle.VehicleType type, Integer capacity, String location, String retirementAddress, String returnAddress, String description, String phone, Integer cost){
        userSystem.get(userCuil).addVehicle(new VehicleBuilder(type,capacity,location,retirementAddress,returnAddress,description,phone,cost).build());

    }
//    protected void generateRental(User owner, User ){
//
//    }

}
