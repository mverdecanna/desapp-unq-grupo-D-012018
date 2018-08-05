package service;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import model.exceptions.InsufficientBalanceException;
import model.exceptions.VehicleAssociatedToActiveRentalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import persistence.RentalRepository;

import java.util.List;

import static model.util.Constants.*;


/**
 * Created by mariano on 10/06/18.
 */
//@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml"})
   // @Service
public class RentalService extends GenericService<Rental> {


    private static final long serialVersionUID = 213148242236709L;


//    @Autowired
//    private UserService userService;

//    @Autowired
//    private MailSenderService mailSenderService;



    @Transactional
    public Rental createRental(Rental rental) {//throws VehicleAssociatedToActiveRentalException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
    //    this.validateCreationRental(rental);
        Rental newRental = this.makeNewReantal(rental);
//        User owner = this.userService.findById("20320231680");
        rentalRepository.save(newRental);
        String ownerMail = this.mailByCuil(newRental.getOwnerCuil());
        String clientMail = this.mailByCuil(newRental.getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_CREATE_RENTAL_OWNER, SUBJECT_CREATE_RENTAL_CLIENT,
//                BODY_CREATE_RENTAL_OWNER, BODY_CREATE_RENTAL_CLIENT);
        return newRental;
    }


    @Transactional
    public Transaction createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        transaction.initializeTransaction();
        rentalRepository.saveTransaction(transaction);
        String ownerMail = this.mailByCuil(transaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(transaction.getRental().getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_CONFIRM_RENTAL_OWNER,
//                SUBJECT_CONFIRM_RENTAL_CLIENT, BODY_CONFIRM_RENTAL_OWNER, BODY_CONFIRM_RENTAL_CLIENT);
        return transaction;
    }


    @Transactional
    public Transaction rejectTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = new Transaction(transaction.getCost(), transaction.getRental());
        newTransaction.rejectTransaction();
        rentalRepository.saveTransaction(newTransaction);
        String ownerMail = this.mailByCuil(newTransaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(newTransaction.getRental().getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_REJECTED_RENTAL_OWNER, SUBJECT_REJECTED_RENTAL_CLIENT,
//                BODY_REJECTED_RENTAL_OWNER, BODY_REJECTED_RENTAL_CLIENT);
        return newTransaction;
    }


    @Transactional
    public Transaction findTransaction(String id) {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(id);
        return transaction;
    }


    @Transactional
    public Transaction collectVehicleAndAdvance(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.markCollectVehicle();
        rentalRepository.updateTransaction(newTransaction);
        String ownerMail = this.mailByCuil(newTransaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(newTransaction.getRental().getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_COLLECT_RENTAL_OWNER,
//                SUBJECT_COLLECT_RENTAL_CLIENT, BODY_COLLECT_RENTAL_OWNER, BODY_COLLECT_RENTAL_CLIENT);
        return newTransaction;
    }


    @Transactional
    public Transaction payAndAdvance(Transaction transaction) throws InsufficientBalanceException {
/*
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        User owner = this.userService.findById(transaction.getRental().getOwnerCuil());
        User client = this.userService.findById(transaction.getRental().getClientCuil());
        if(!owner.canPayForThis(transaction.getCost())){
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
        }
        owner.payCredit(transaction.getCost());
        client.receiveCredit(transaction.getCost());
        this.userService.saveTransactionUsers(owner, client);
        transaction.payTransaction();
        rentalRepository.updateTransaction(transaction);
//        this.mailSenderService.notificateUsers(owner.getEmail(), client.getEmail(), SUBJECT_PAID_RENTAL_OWNER, SUBJECT_PAID_RENTAL_CLIENT,
//                BODY_PAID_RENTAL_OWNER_START + transaction.getCost().toString() + BODY_PAID_RENTAL_OWNER_END,
//                BODY_PAID_RENTAL_CLIENT_START + transaction.getCost().toString() + BODY_PAID_RENTAL_CLIENT_END);
*/
        return transaction;
    }



    @Transactional
    public Transaction returnedVehicleAndAdvance(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.completeTransaction();
        rentalRepository.updateTransaction(newTransaction);
        String ownerMail = this.mailByCuil(newTransaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(newTransaction.getRental().getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_RETURNED_RENTAL_OWNER,
//                SUBJECT_RETURNED_RENTAL_CLIENT, BODY_RETURNED_RENTAL_OWNER, BODY_RETURNED_RENTAL_CLIENT);
        return newTransaction;
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



    @Transactional
    public Score createScore(Score score){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        rentalRepository.saveScore(score);
        return score;
    }



    private void validateCreationRental(Rental newRental) throws VehicleAssociatedToActiveRentalException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        List<Rental> rentals = rentalRepository.activeRentals(newRental.getVehicleID());
        for(Rental rental: rentals){
            if(newRental.getStartDate().equals(rental.getEndDate()) || newRental.getStartDate().before(rental.getEndDate())){
                throw new VehicleAssociatedToActiveRentalException(VEHICLE_INAVALID_RENTAL_MESSAGE);
            }
        }
    }



}
