package persistence;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.dao.support.DataAccessUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 10/06/18.
 */
public class RentalRepository extends HibernateGenericDAO<Rental> implements GenericRepository<Rental> {


    public static Logger log = Logger.getLogger(RentalRepository.class);

    private static final long serialVersionUID = -4035372110L;



    @Override
    protected Class<Rental> getDomainClass() {
        return Rental.class;
    }


    @Override
    public Rental findById(Serializable id) {
        return super.findById(id);
    }


    @Override
    public void save(Rental rental){
        super.save(rental);
    }



    public Transaction findTransactionById(String id){
        Transaction transaction = (Transaction) this.getHibernateTemplate().get(Transaction.class, id);
        return transaction;
    }


    public void saveTransaction(Transaction transaction){
        this.getHibernateTemplate().save(transaction);
        this.getHibernateTemplate().flush();
    }


    public void updateTransaction(Transaction transaction){
        this.getHibernateTemplate().update(transaction);
        this.getHibernateTemplate().flush();
    }


    public List<Rental> rentalsByCuil(String cuil){
        List<Rental> rentals = (List<Rental>) this.getHibernateTemplate().
                find("select r from " + this.persistentClass.getName() + " r"
                          + " where r.ownerCuil = " + cuil);
        return rentals;
    }

    public List<Rental> rentalsByClientCuil(String cuil){
        List<Rental> rentals = (List<Rental>) this.getHibernateTemplate().
                find("select r from " + this.persistentClass.getName() + " r"
                        + " where r.clientCuil = " + cuil);
        return rentals;
    }

    public String findMailByCuil(String cuil){
        String mail = (String) DataAccessUtils.singleResult((this.getHibernateTemplate().find("select email from User u" +
                " where u.cuil =" +cuil)));
        return mail;
    }


    public void saveScore(Score score){
        this.getHibernateTemplate().save(score);
        this.getHibernateTemplate().flush();
    }



    public List<Rental> activeRentals(String vehicleId){
        String query = "FROM Rental as r WHERE r.state IN ('CONFIRM', 'DONE', 'IN_USE') AND r.vehicleID = :vehicleID";
        List<Rental> rentals = new ArrayList<Rental>();
        rentals = (List<Rental>) this.getHibernateTemplate().findByNamedParam(query, "vehicleID", vehicleId);
//                (List<Rental>) this.getHibernateTemplate().
//                find("select r from " + this.persistentClass.getName() + " r"
//                        + " where r.state in ('CONFIRM', 'DONE', 'IN_USE') and r.vehicleID = " + "+'vehicleID'+");
        return rentals;
    }



    public User findClienteByCuil(String cuil){
        User user = null;
        String query = "FROM User as u WHERE u.cuil = :cuil";
        List<User> users = (List<User>) this.getHibernateTemplate().findByNamedParam(query, "cuil", cuil);
        if(users != null && !users.isEmpty()){
            user = users.get(0);
        }
        return user;
    }


}
