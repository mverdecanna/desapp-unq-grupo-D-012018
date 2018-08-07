package service;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import model.exceptions.InsufficientBalanceException;
import model.exceptions.InvalidStatusToCancelOperationException;
import model.exceptions.InvalidStatusToScoredException;
import model.exceptions.VehicleAssociatedToActiveRentalException;
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
    public Rental createRental(Rental rental) throws VehicleAssociatedToActiveRentalException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        this.validateCreationRental(rental);
        Rental newRental = this.makeNewReantal(rental);
        rentalRepository.save(newRental);
//        String ownerMail = this.mailByCuil(newRental.getOwnerCuil());
//        String clientMail = this.mailByCuil(newRental.getClientCuil());
//        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_CREATE_RENTAL_OWNER, SUBJECT_CREATE_RENTAL_CLIENT,
//                BODY_CREATE_RENTAL_OWNER, BODY_CREATE_RENTAL_CLIENT);
        return newRental;
    }


    @Transactional
    public Transaction createTransaction(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        transaction.initializeTransaction();
        rentalRepository.saveTransaction(transaction);
        return transaction;
    }


    @Transactional
    public Transaction rejectTransaction(Transaction transaction) throws InvalidStatusToCancelOperationException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        this.validateRejectTransaction(transaction.getRental());
        Transaction newTransaction = new Transaction(transaction.getCost(), transaction.getRental());
        newTransaction.rejectTransaction();
        rentalRepository.saveTransaction(newTransaction);
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
        return newTransaction;
    }


    @Transactional
    public Transaction payAndAdvance(Transaction transaction) throws InsufficientBalanceException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        transaction.payTransaction();
        rentalRepository.updateTransaction(transaction);
        return transaction;
    }



    @Transactional
    public Transaction returnedVehicleAndAdvance(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.completeTransaction();
        rentalRepository.updateTransaction(newTransaction);
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
    public Score createScore(Score score) throws InvalidStatusToScoredException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        rentalRepository.saveScore(score);
        this.registerScoreInTransaction(score);
        return score;
    }



    private void registerScoreInTransaction(Score score) throws InvalidStatusToScoredException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(score.getTransactionID());
        if(transaction.getRental().getState().equals(Rental.RentalState.RETURNED)){
            this.scoreOwner(transaction);
        }else if(transaction.getRental().getState().equals(Rental.RentalState.SCORED)){
            this.scoreClient(transaction);
        }else{
            throw new InvalidStatusToScoredException(SCORED_INAVALID_STATUS_MESSAGE);
        }
    }


    private void scoreOwner(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.scoreRental();
        rentalRepository.updateTransaction(newTransaction);
    }


    private void scoreClient(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.finalizeTransaction();
        rentalRepository.updateTransaction(newTransaction);
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



    private void validateRejectTransaction(Rental rental) throws InvalidStatusToCancelOperationException {
        if( !rental.getState().equals(Rental.RentalState.WAIT_CONFIRM) || !rental.getState().equals(Rental.RentalState.CONFIRM) ){
            throw new InvalidStatusToCancelOperationException(RENTAL_REJECT_INAVALID_STATUS_MESSAGE);
        }
    }




}
