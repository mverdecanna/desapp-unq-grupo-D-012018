package service;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import model.exceptions.*;
import org.springframework.transaction.annotation.Transactional;
import persistence.RentalRepository;

import java.util.Date;
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
    public Rental createRental(Rental rental) throws VehicleAssociatedToActiveRentalException, BadReputationException {
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
        Transaction newTransaction = rentalRepository.findTransactionById(transaction.getRental().getId());
        this.validateRejectTransaction(newTransaction.getRental());
        newTransaction.rejectTransaction();
        rentalRepository.saveOrUpdateTransaction(newTransaction);
        return newTransaction;
    }


    @Transactional
    public Transaction findTransaction(String id) {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction transaction = rentalRepository.findTransactionById(id);
        return transaction;
    }


    @Transactional
    public Transaction collectVehicleAndAdvance(Transaction transaction) throws CollectOutOfTermException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = rentalRepository.findTransactionById(transaction.getRental().getId());
        this.validateCollectInTerm(newTransaction.getRental());
        newTransaction.markCollectVehicle();
        rentalRepository.updateTransaction(newTransaction);
        return newTransaction;
    }


    @Transactional
    public Transaction payAndAdvance(Transaction transaction) throws InsufficientBalanceException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = rentalRepository.findTransactionById(transaction.getRental().getId());
        newTransaction.payTransaction();
        rentalRepository.updateTransaction(newTransaction);
        return newTransaction;
    }



    @Transactional
    public Transaction returnedVehicleAndAdvance(Transaction transaction) throws ReturnedOutOfTermException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = rentalRepository.findTransactionById(transaction.getRental().getId());
        this.validateReturnedInTerm(newTransaction.getRental());
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
            this.scoredOwner(transaction);
        }else if(transaction.getRental().getState().equals(Rental.RentalState.SCORED)){
            this.scoredClient(transaction);
        }else{
            throw new InvalidStatusToScoredException(SCORED_INAVALID_STATUS_MESSAGE);
        }
    }


    private void scoredOwner(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.scoreRental();
        rentalRepository.updateTransaction(newTransaction);
    }


    private void scoredClient(Transaction transaction){
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        Transaction newTransaction = transaction;
        newTransaction.finalizeTransaction();
        rentalRepository.updateTransaction(newTransaction);
    }


    private void validateCreationRental(Rental newRental) throws VehicleAssociatedToActiveRentalException, BadReputationException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        this.validatePuntuation(newRental.getClientCuil());
        List<Rental> rentals = rentalRepository.activeRentals(newRental.getVehicleID());
        for(Rental rental: rentals){
            if(newRental.getStartDate().equals(rental.getEndDate()) || newRental.getStartDate().before(rental.getEndDate())){
                throw new VehicleAssociatedToActiveRentalException(VEHICLE_INAVALID_RENTAL_MESSAGE);
            }
        }
    }


    private void validatePuntuation(String clientCuil) throws BadReputationException {
        RentalRepository rentalRepository = (RentalRepository) getRepository();
        User client = rentalRepository.findClienteByCuil(clientCuil);
        if(!client.getPuntuations().isEmpty() && client.hasNecessaryReputation()){
            throw new BadReputationException(BAD_REPUTATION_MESSAGE);
        }
    }



    private void validateRejectTransaction(Rental rental) throws InvalidStatusToCancelOperationException {
        if( !rental.getState().equals(Rental.RentalState.WAIT_CONFIRM) || !rental.getState().equals(Rental.RentalState.CONFIRM) ){
            throw new InvalidStatusToCancelOperationException(RENTAL_REJECT_INAVALID_STATUS_MESSAGE);
        }
    }


    private void validateCollectInTerm(Rental rental) throws CollectOutOfTermException {
        Date today = new Date();
        //if(rental.getStartDate().compareTo(today) != 0){
        if(false){
            throw new CollectOutOfTermException(COLLECT_OUT_OF_TERM_MESSAGE);
        }
    }



    private void validateReturnedInTerm(Rental rental) throws ReturnedOutOfTermException {
        Date today = new Date();
        //if(rental.getEndDate().after(today)){
        if(false){
            throw new ReturnedOutOfTermException(RETURNED_OUT_OF_TERM_MESSAGE);
        }
    }




}
