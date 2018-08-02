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


    @Id
    @Column(name = "id")
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


    public Score(){}


    public Score(String id,Integer value, String comment, String transactionID, String userCuil, String creator){
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.transactionID = transactionID;
        this.userCuil = userCuil;
        this.creator = creator;
    }




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
}
