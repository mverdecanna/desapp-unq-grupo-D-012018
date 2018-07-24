package service;

import model.AppMail;
import model.Rental;
import model.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.RentalRepository;

import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static model.util.Constants.*;


/**
 * Created by mariano on 10/06/18.
 */
public class RentalService extends GenericService<Rental> {


    private static final long serialVersionUID = 2131482422367092L;


    @Transactional
    public Rental createRental(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Rental newRental = this.makeNewReantal(rental);
        rentalRepository.save(newRental);
        return newRental;
    }


    @Transactional
    public Transaction createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Rental rental = transaction.getRental();
        rental.initRental();
        Transaction newTransaction = new Transaction(transaction.getCost(), rental);
        rentalRepository.saveTransaction(newTransaction);
        return newTransaction;
    }


    @Transactional
    public Transaction rejectTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Rental rental = transaction.getRental();
        Transaction newTransaction = new Transaction(transaction.getCost(), rental);
        newTransaction.rejectTransaction();
        rentalRepository.saveTransaction(newTransaction);
        return transaction;
    }


    @Transactional
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


    @Transactional
    public List<Rental> findRentalsByCuil(String cuil){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        List<Rental> rentals = rentalRepository.rentalsByCuil(cuil);
        return rentals;
    }

    @Transactional
    public List<Rental> findRentalsByClientCuil(String cuil){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        List<Rental> rentals = rentalRepository.rentalsByClientCuil(cuil);
        return rentals;
    }



    private Rental makeNewReantal(Rental rental){
        Rental newRental = new Rental(rental.getOwnerCuil(), rental.getClientCuil(), rental.getVehicleID());
/*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

        String start_string = rental.getStartDate().toString();
        String end_string = rental.getEndDate().toString();

  //      String fecha = formatter.parseDateTime(start_string).toDate().toString();

        Date date = new Date();
        try {
            date = sdf.parse(start_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DateTime start = formatter.parseDateTime(start_string);
        //DateTime end = formatter.parseDateTime(end_string);
*/
        newRental.setStartDate(rental.getStartDate());
        newRental.setEndDate(rental.getEndDate());
        return newRental;
    }


    @Transactional
    public String mailByCuil(String cuil){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        String mail = rentalRepository.findMailByCuil(cuil);
        return mail;
    }


}
