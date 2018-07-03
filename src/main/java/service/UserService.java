package service;

import model.CurrentAccount;
import model.User;
import model.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import persistence.UserRepository;

/**
 * Created by mariano on 14/04/18.
 */
public class UserService extends GenericService<User> {

    private static final long serialVersionUID = 2131359482422367092L;


    // no esta bien mapeado en userRepository, por eso rompe...

    public Integer nUsuers() {
        return getRepository().count();
    }


    public CurrentAccount findCurrentAccount(String cuil) {
        UserRepository userRepository = (UserRepository) getRepository();
        CurrentAccount currentAccount = userRepository.findCurrentAccountByCuil(cuil);
        return currentAccount;
    }


    @Transactional
    public void addCreditToCurrentAccount(CurrentAccount currentAccount, Integer credit) {
        UserRepository userRepository = (UserRepository) getRepository();
        currentAccount.addCredit(credit);
        userRepository.updateCurrentAccount(currentAccount);
    }


    @Transactional
    public void subtractCreditToCurrentAccount(CurrentAccount currentAccount, Integer credit) {
        UserRepository userRepository = (UserRepository) getRepository();
        currentAccount.subtractCredit(credit);
        userRepository.updateCurrentAccount(currentAccount);
    }


    public UserDto findByMail(String mail){
        UserRepository userRepository = (UserRepository) getRepository();
        User user = userRepository.findUserByMail(mail);
        return this.makeUserDto(user);
    }



    private UserDto makeUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setRegister(Boolean.FALSE);
        if(user != null){
            userDto.setAddress(user.getAddress());
            userDto.setCuil(user.getCuil());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setSurname(user.getSurname());
            userDto.setRegister(Boolean.TRUE);
        }
        return userDto;
    }





}
