package model.builder;

import model.CurrentAccount;

/**
 * Created by mariano on 01/04/18.
 */
public class CurrentAccountBuilder {

    private Long id;
    private Integer credits = 0;


    public CurrentAccountBuilder(){}


    public CurrentAccountBuilder(Long id){
        this.id = id;
    }


    public CurrentAccountBuilder setId(Long id){
        this.id = id;
        return this;
    }


    public CurrentAccount build(){
        return new CurrentAccount(this.id);
    }


}
