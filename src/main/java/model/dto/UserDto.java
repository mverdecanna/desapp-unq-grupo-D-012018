package model.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mariano on 21/06/18.
 */

@XmlRootElement(name="UserDto")
public class UserDto implements Serializable {


    private String cuil;

    private String name;

    private String surname;

    private String address;

    private String email;


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
}
