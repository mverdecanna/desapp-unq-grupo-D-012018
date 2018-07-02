package service;

import model.Rental;
import model.Transaction;
import persistence.RentalRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mariano on 10/06/18.
 */
public class RentalService extends GenericService<Rental> {


    private static final long serialVersionUID = 2131482422367092L;


    @Transactional
    public Rental createRental(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Rental newRental = new Rental(rental.getOwnerCuil(), rental.getClientCuil(), rental.getVehicleID());
        newRental.setStartDate(rental.getStartDate());
        newRental.setEndDate(rental.getEndDate());
        rentalRepository.save(newRental);
        return newRental;
    }



    @Transactional
    public Transaction createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = new Transaction(transaction.getCost(), transaction.getRental());
        rentalRepository.saveTransaction(transaction);
        return newTransaction;
    }



    public Transaction findTransaction(String id) {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(id);
        return transaction;
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



    public List<Rental> findRentalsByCuil(String cuil){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        List<Rental> rentals = rentalRepository.rentalsByCuil(cuil);
        return rentals;
    }



}
