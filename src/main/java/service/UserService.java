package service;

import model.CurrentAccount;
import model.Score;
import model.User;
import model.dto.UserDto;
import model.exceptions.InvalidRegisterParameterException;
import model.util.CuilValidator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.UserRepository;

import static model.util.Constants.*;

/**
 * Created by mariano on 14/04/18.
 */
//@Service
//@Component
public class UserService extends GenericService<User> {

    private static final long serialVersionUID = 2131359482422367092L;



    @Transactional
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

    @Transactional
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


    @Transactional
    public void saveTransactionUsers(User owner, User client){
        this.save(owner);
        this.save(client);
    }


    @Transactional
    public void registerNewUser(User user) throws InvalidRegisterParameterException {
        this.validateNewUser(user);
        user.initializeUser();
        this.save(user);
    }


    private void validateNewUser(User user) throws InvalidRegisterParameterException {
        this.validateUserMail(user);
        if(this.registeredCuil(user.getCuil())){
            throw new InvalidRegisterParameterException(CUIL_ALREADY_EXISTS_MESSAGE);
        }
        if(!CuilValidator.isValid(user.getCuil())){
            throw new InvalidRegisterParameterException(INVALID_CUIL_MESSAGE);
        }
    }


    @Transactional
    public Boolean registeredEmail(String email){
        UserRepository userRepository = (UserRepository) getRepository();
        Integer find = userRepository.existMail(email);
        return find > 0;
    }



    @Transactional
    public Boolean registeredCuil(String cuil){
        UserRepository userRepository = (UserRepository) getRepository();
        Integer find = userRepository.existCuil(cuil);
        return find > 0;
    }


    @Transactional
    public void updateUser(User user) throws InvalidRegisterParameterException {
        //this.validateUserMail(user);
        this.update(user);
    }


    private void validateUserMail(User user) throws InvalidRegisterParameterException {
        if (!user.validMail()) {
            throw new InvalidRegisterParameterException(INVALID_EMAIL_MESSAGE);
        }
        if (this.registeredEmail(user.getEmail())) {
            throw new InvalidRegisterParameterException(EMAIL_ALREADY_EXISTS_MESSAGE);
        }
    }




}
