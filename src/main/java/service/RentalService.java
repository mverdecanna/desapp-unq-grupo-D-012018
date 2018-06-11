package service;

import model.Rental;
import model.Transaction;
import persistence.RentalRepository;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mariano on 10/06/18.
 */
public class RentalService extends GenericService<Rental> {


    private static final long serialVersionUID = 2131482422367092L;



    public Transaction findTransaction(String id) {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(id);
        return transaction;
    }


    @Transactional
    public void createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        rentalRepository.saveTransaction(transaction);
    }


    @Transactional
    public void collectVehicleAndAdvance(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(rental.getId());
        transaction.setRental(rental);
        transaction.markCollectVehicle();
        rentalRepository.updateTransaction(transaction);
    }


    @Transactional
    public void payAndAdvance(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(rental.getId());
        transaction.setRental(rental);
        transaction.payTransaction();
        rentalRepository.updateTransaction(transaction);
    }



    @Transactional
    public void returnedVehicleAndAdvance(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(rental.getId());
        transaction.setRental(rental);
        transaction.completeTransaction();
        rentalRepository.updateTransaction(transaction);
    }



}
