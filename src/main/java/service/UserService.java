package service;

import model.User;
import persistence.UserRepository;

/**
 * Created by mariano on 14/04/18.
 */
public class UserService extends GenericService<User> {

    private static final long serialVersionUID = 2131359482422367092L;

/*
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
*/

    // no esta bien mapeado en userRepository, por eso rompe...

    public Integer nUsuers(){
        return getRepository().count();
    }

/*
    public Long testService(){
        return super.getRepository().
    }
*/


}
