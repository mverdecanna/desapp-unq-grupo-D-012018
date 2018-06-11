package persistence;

import model.Rental;
import model.Transaction;
import org.apache.log4j.Logger;

import java.io.Serializable;

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


}
