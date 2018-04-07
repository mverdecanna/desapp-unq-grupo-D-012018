package model.exception;

/**
 * Created by mariano on 07/04/18.
 */
public class InvalidPhoneNumberException extends Exception{

    public InvalidPhoneNumberException(){}

    public InvalidPhoneNumberException(String message){
        super(message);
    }

}
