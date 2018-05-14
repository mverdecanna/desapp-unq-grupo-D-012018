package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.*;


/**
 * Created by mariano on 27/03/18.
 */
@Entity
@Table(name="users", schema = "carpnd")
@XmlRootElement(name="User")
public class User implements Serializable {

    @Id
    @Column(name="cuil")
    private String cuil;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;


/*    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="current_account",
            joinColumns={@JoinColumn(name="cuil", referencedColumnName="cuil")},
            inverseJoinColumns={@JoinColumn(name="cuil", referencedColumnName="cuil")})   */
@Transient
    private CurrentAccount currentAccount;

    @Transient
    private List<Score> puntuations = new ArrayList<Score>();

    @Transient
    public List<Vehicle> vehicles = new ArrayList<Vehicle>();


    public User(){
        super();
    }


    public User(String cuil, String name, String surname, String address, String email){
        this.cuil = cuil;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
    }


    public User(String cuil, String name, String surname, String address, String email,
                CurrentAccount currentAccount, List<Score> puntuations, List<Vehicle> vehicles){
        this.cuil = cuil;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.currentAccount = currentAccount;
        this.puntuations = puntuations;
        this.vehicles = vehicles;
    }


    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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


    public Integer balance(){
        return this.currentAccount.getCredits();
    }


    /**
     * recorre las puntuaciones del usuario y retorna el promedio
     * @return
     */
    public Integer evalReputation(){
        /*
        Double result = 0d;
        if(this.puntuations.size() > 0){
            result = this.puntuations.stream().mapToDouble(f -> f.doubleValue()).sum();
        }
        */
        Integer result = 0;
        if(this.puntuations.size() > 0){
            result = this.puntuations.stream().filter(o -> o.getValue() != null).mapToInt(Score::getValue).sum();
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
     * agrega un Score al usuario, con un valor y un comentario
     * @param value
     * @param comment
     */
    public void scoreUser(Integer value, String comment){
        Score score = new Score(value, comment);
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
        return (this.balance() >= vehicle.getCost());
    }



    public Boolean isValidName(String name){
        return (name.length() >= 4) && (name.length() <= 50);
    }

    public Boolean isValidSurname(String surname){
        return (surname.length() >= 4) && (surname.length() <= 50);
    }


    public Boolean isValidMail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }







}
