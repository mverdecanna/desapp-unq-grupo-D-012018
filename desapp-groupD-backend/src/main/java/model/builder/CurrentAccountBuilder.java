package model.builder;

import model.CurrentAccount;

/**
 * Created by mariano on 01/04/18.
 */
public class CurrentAccountBuilder {

    private Integer id;
    private Integer credits = 0;


    public CurrentAccountBuilder(){}


    public CurrentAccountBuilder(Integer id){
        this.id = id;
    }


    public CurrentAccountBuilder setId(Integer id){
        this.id = id;
        return this;
    }


    public CurrentAccount build(){
        return new CurrentAccount(this.id);
    }


}
