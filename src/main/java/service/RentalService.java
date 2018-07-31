package service;

import model.AppMail;
import model.Rental;
import model.Transaction;
import model.User;
import model.exceptions.InsufficientBalanceException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.RentalRepository;

import org.springframework.transaction.annotation.Transactional;
import persistence.UserRepository;

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


    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

/*
    public MailSenderService getMailSenderService() {
        return mailSenderService;
    }

    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }
*/


    @Transactional
    public Rental createRental(Rental rental){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Rental newRental = this.makeNewReantal(rental);
        rentalRepository.save(newRental);
        String ownerMail = this.mailByCuil(newRental.getOwnerCuil());
        String clientMail = this.mailByCuil(newRental.getClientCuil());
        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_CREATE_RENTAL_OWNER, SUBJECT_CREATE_RENTAL_CLIENT,
                BODY_CREATE_RENTAL_OWNER, BODY_CREATE_RENTAL_CLIENT);
        return newRental;
    }


    @Transactional
    public Transaction createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        transaction.initializeTransaction();
        rentalRepository.saveTransaction(transaction);
        User user = this.userService.findById(transaction.getRental().getOwnerCuil());
        String ownerMail = this.mailByCuil(transaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(transaction.getRental().getClientCuil());
        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_CONFIRM_RENTAL_OWNER,
                SUBJECT_CONFIRM_RENTAL_CLIENT, BODY_CONFIRM_RENTAL_OWNER, BODY_CONFIRM_RENTAL_CLIENT);
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
        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_REJECTED_RENTAL_OWNER, SUBJECT_REJECTED_RENTAL_CLIENT,
                BODY_REJECTED_RENTAL_OWNER, BODY_REJECTED_RENTAL_CLIENT);
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
        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_COLLECT_RENTAL_OWNER,
                SUBJECT_COLLECT_RENTAL_CLIENT, BODY_COLLECT_RENTAL_OWNER, BODY_COLLECT_RENTAL_CLIENT);
        return newTransaction;
    }


    @Transactional
    public Transaction payAndAdvance(Transaction transaction) throws InsufficientBalanceException {
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
        String ownerMail = this.mailByCuil(transaction.getRental().getOwnerCuil());
        String clientMail = this.mailByCuil(transaction.getRental().getClientCuil());
        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_PAID_RENTAL_OWNER, SUBJECT_PAID_RENTAL_CLIENT,
                BODY_PAID_RENTAL_OWNER_START + transaction.getCost().toString() + BODY_PAID_RENTAL_OWNER_END,
                BODY_PAID_RENTAL_CLIENT_START + transaction.getCost().toString() + BODY_PAID_RENTAL_CLIENT_END);
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
        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_RETURNED_RENTAL_OWNER,
                SUBJECT_RETURNED_RENTAL_CLIENT, BODY_RETURNED_RENTAL_OWNER, BODY_RETURNED_RENTAL_CLIENT);
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



}
