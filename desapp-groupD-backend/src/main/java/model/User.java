package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 27/03/18.
 */
public class User {


    public User(){
    }


    public User(Integer cuil, String name, String surname, String address, String email,
                CurrentAccount currentAccount, List<Double> puntuations, List<Vehicle> vehicles){
        this.cuil = cuil;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.currentAccount = currentAccount;
        this.puntuations = puntuations;
        this.vehicles = vehicles;
    }


    private Integer cuil;
    private String name;
    private String surname;
    private String address;
    private String email;
    private CurrentAccount currentAccount;

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




    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }


    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
    }


    public Integer getBalance(){
        return this.currentAccount.getCredits();
    }


    /**
     * recorre las puntuaciones del usuario y retorna el promedio
     * @return
     */
    public Double evalReputation(){
        Double result = 0d;
        if(this.puntuations.size() > 0){
            result = this.puntuations.stream().mapToDouble(f -> f.doubleValue()).sum();
        }
        return (result / this.puntuations.size());
    }


    /**
     * el usuario paga una cantidad de creditos
     * @param credit
     */
    public void payCredit(Integer credit){
        this.currentAccount.subtractCredit(credit);
    }

    /**
     * el usuario recibe un pago de creditos
     * @param credit
     */
    public void receiveCredit(Integer credit){
        this.currentAccount.addCredit(credit);
    }


    /**
     * el usuario carga credito en su cuenta
     * @param credit
     */
    public void addCreditInMyAccount(Integer credit){
        this.currentAccount.addCredit(credit);
    }


    /**
     * agrega un vehiculo a la lista del usuario
     * @param vehicle
     */
    public void addVehicle(Vehicle vehicle){
        this.vehicles.add(vehicle);
    }

    /**
     * agrega una puntuacion al usuario
     * @param score
     */
    public void scoreUser(Double score){
        this.puntuations.add(score);
    }


    /**
     * evalua si tiene saldo suficiente para pagar el costo del vehiculo
     * @param vehicle
     * @return
     *
     *    el metodo va a cambiar si el costo es un valor calculado
     */
    public Boolean canPayForThis(Vehicle vehicle){
        return (this.getBalance() >= vehicle.getCost());
    }


}
