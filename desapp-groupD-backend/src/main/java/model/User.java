package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 27/03/18.
 */
public class User {


    public User(){}


    public User(String name, String surname, Integer cuil){
        this.name = name;
        this.surname = surname;
        this.cuil = cuil;
    }


    private Integer cuil;
    private String name;
    private String surname;
    private String address;
    private String email;

    private List<Double> puntuations = new ArrayList<Double>();

    public List<Vehicle> vehicles = new ArrayList<Vehicle>();


    public Integer getCuil() {
        return cuil;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }


    public void addVehicle(Vehicle vehicle){
        this.vehicles.add(vehicle);
    }


    public void scoreUser(Double score){
        this.puntuations.add(score);
    }


    public Double evalReputation(){
        Double result = 0d;
        if(this.puntuations.size() > 0){
            result = this.puntuations.stream().mapToDouble(f -> f.doubleValue()).sum();
        }
        return (result / this.puntuations.size());
    }


}
