package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mariano on 29/03/18.
 */
@Entity
@Table(name="current_acounts", schema = "carpnd")
@XmlRootElement(name="CurrentAccount")
public class CurrentAccount {


    @Id
    @Column(name="cuil")
    private String cuil;

    @Column(name="credits")
    private Integer credits = 0;


    public CurrentAccount(){}


    public CurrentAccount(String id){
        this.cuil = id;
    }



    public String getId() {
        return cuil;
    }


    public Integer getCredits() {
        return credits;
    }



    public  void addCredit(Integer credit){
        this.credits += credit;
    }


    public void subtractCredit(Integer credit){
        this.credits -= credit;
    }



    // probando el heroku con travis

}
