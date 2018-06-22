package persistence;

import model.CurrentAccount;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.hibernate.Session;


import java.io.Serializable;
import java.util.List;

import static model.util.Constants.TABLE_CURRENT_ACCOUNT;

/**
 * Created by mariano on 23/04/18.
 */
public class UserRepository extends HibernateGenericDAO<User> implements GenericRepository<User> {

    public static Logger log = Logger.getLogger(UserRepository.class);

    private static final long serialVersionUID = -4036535812105672110L;


    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }


    @Override
    public User findById(Serializable id) {
        return super.findById(id);
    }


    @Override
    public List<User> findAll() {
        return super.findAll();
    }


    @Override
    public Integer count(){
        return super.count();
    }


    @Override
    public void save(User user){
        super.save(user);
    }


    public CurrentAccount findCurrentAccountByCuil(String cuil){
        CurrentAccount currentAccount = (CurrentAccount) this.getHibernateTemplate().get(CurrentAccount.class, cuil);
        return currentAccount;
    }

    public void updateCurrentAccount(CurrentAccount currentAccount){
        this.getHibernateTemplate().update(currentAccount);
        this.getHibernateTemplate().flush();
    }


    public User findUserByMail(String mail){
        User user = null;
        String selectQuery = "FROM User as user WHERE user.email = :email";
        List<User> users = (List<User>) this.getHibernateTemplate().findByNamedParam(selectQuery, "email", mail);
        if(users != null && !users.isEmpty()){
            user = users.get(0);
        }
        return user;
    }


}
