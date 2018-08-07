package model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mariano on 03/04/18.
 */

@Entity
@Table(name="scores", schema = "carpnd")
@XmlRootElement(name="Score")
public class Score implements Serializable{


/*
    public enum RolScorer{
        OWNER, CLIENT
    }
*/


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "value")
    private Integer value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "transactionID")
    private String transactionID;

    @Column(name = "userCuil")
    private String userCuil;

    @Column(name = "creator")
    private String creator;

//    @Column(name="rol", length = 6)
//    @Enumerated(EnumType.STRING)
//    private RolScorer rol;


    public Score(){}


    public Score(String id,Integer value, String comment, String transactionID, String userCuil, String creator){
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.transactionID = transactionID;
        this.userCuil = userCuil;
        this.creator = creator;
    }



/*
    public String obteinOwnerCuil(){
        String cuil = this.getUserCuil();
        if(this.rol.equals(RolScorer.OWNER)){
            cuil = this.getCreator();
        }
        return cuil;
    }

    public String obteinClientCuil(){
        String cuil = this.getCreator();
        if(this.rol.equals(RolScorer.CLIENT)){
            cuil = this.getUserCuil();
        }
        return cuil;
    }
*/





//  getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }


    public String getUserCuil() {
        return userCuil;
    }

    public void setUserCuil(String userCuil) {
        this.userCuil = userCuil;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

/*
    public RolScorer getRol() {
        return rol;
    }

    public String rolName() {
        return rol.name();
    }

    public void setRol(RolScorer rol) {
        this.rol = rol;
    }
*/


}
