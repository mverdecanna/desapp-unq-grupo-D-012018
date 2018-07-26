package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mariano on 29/03/18.
 */
@Entity
@Table(name="current_account", schema="carpnd")
@XmlRootElement(name="CurrentAccount")
public class CurrentAccount implements Serializable {


    @Id
    @Column(name="cuil")
    private String cuil;

    @Column(name="credits")
    private Integer credits;


    public CurrentAccount(){}


    public CurrentAccount(String id){
        this.cuil = id;
        this.credits = 0;
    }


    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public  void addCredit(Integer credit){
        this.credits += credit;
    }


    public void subtractCredit(Integer credit){
        this.credits -= credit;
    }



    // probando el heroku con travis

}
