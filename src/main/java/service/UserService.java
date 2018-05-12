package service;

import model.User;
import persistence.UserRepository;

/**
 * Created by mariano on 14/04/18.
 */
public class UserService extends GenericService<User> {

    private static final long serialVersionUID = 2131359482422367092L;


    private UserRepository userRepository;


    public Integer nUsuers(){
        return this.userRepository.countUser();
    }



}
