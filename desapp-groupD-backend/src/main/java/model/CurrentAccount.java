package model;

/**
 * Created by mariano on 29/03/18.
 */
public class CurrentAccount {


    private Integer id;
    private Integer credits = 0;


    public CurrentAccount(){}


    public CurrentAccount(Integer id){
        this.id = id;
    }



    public Integer getId() {
        return id;
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


}
