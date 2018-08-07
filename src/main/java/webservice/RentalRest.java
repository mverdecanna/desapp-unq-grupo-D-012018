package webservice;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import model.exceptions.*;
import org.joda.time.DateTime;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import service.MailSenderService;
import service.RentalService;
import service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static model.util.Constants.*;

/**
 * Created by mariano on 10/06/18.
 */
@Path("/rental")
public class RentalRest {


    private RentalService rentalService;

    private MailSenderService mailSenderService;

    private UserService userService;


    public RentalService getRentalService() {
        return rentalService;
    }

    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public MailSenderService getMailSenderService() {
        return mailSenderService;
    }

    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @GET
    @Path("/all/{cuil}")
    @Produces("application/json")
    public Response findAll(@PathParam("cuil") final String cuil) {
        List<Rental> rentals = rentalService.findRentalsByCuil(cuil);
        if (rentals == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(rentals).build();
    }

    @GET
    @Path("/allclient/{cuil}")
    @Produces("application/json")
    public Response findAllClient(@PathParam("cuil") final String cuil) {
        List<Rental> rentals = rentalService.findRentalsByClientCuil(cuil);
        if (rentals == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(rentals).build();
    }


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findRental(@PathParam("id") final String rentalID) {
        Rental rental = rentalService.findById(rentalID);
        if (rental == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(rental).build();
    }




    @POST
    @Path("/create")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createRental(Rental rental) {
        Response response = null;
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Rental newRental = null;
        try {
            newRental = this.rentalService.createRental(rental);
            String ownerMail = this.rentalService.mailByCuil(newRental.getOwnerCuil());
            String clientMail = this.rentalService.mailByCuil(newRental.getClientCuil());
            this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_CREATE_RENTAL_OWNER, SUBJECT_CREATE_RENTAL_CLIENT,
                    BODY_CREATE_RENTAL_OWNER, BODY_CREATE_RENTAL_CLIENT);
            response = Response.ok(newRental).build();
        } catch (VehicleAssociatedToActiveRentalException | BadReputationException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }



    @PUT
    @Path("/rental/collect")
    @Produces("application/json")
    @Consumes("application/json")
    public Response collectVehicle(Transaction transaction) {
        Response response = null;
        Transaction newTransaction = null;
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            newTransaction = this.rentalService.collectVehicleAndAdvance(transaction);
            String ownerMail = this.rentalService.mailByCuil(newTransaction.getRental().getOwnerCuil());
            String clientMail = this.rentalService.mailByCuil(newTransaction.getRental().getClientCuil());
            this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_COLLECT_RENTAL_OWNER,
                    SUBJECT_COLLECT_RENTAL_CLIENT, BODY_COLLECT_RENTAL_OWNER, BODY_COLLECT_RENTAL_CLIENT);
            response = Response.ok(newTransaction).build();
        } catch (CollectOutOfTermException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }



    @PUT
    @Path("/rental/pay")
    @Produces("application/json")
    @Consumes("application/json")
    public Response payRental(Transaction transaction) {
        Response response = null;
        Transaction newTransaction = null;
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            this.administrateCredits(transaction);
            newTransaction = this.rentalService.payAndAdvance(transaction);
            String ownerMail = this.rentalService.mailByCuil(newTransaction.getRental().getOwnerCuil());
            String clientMail = this.rentalService.mailByCuil(newTransaction.getRental().getClientCuil());
            this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_PAID_RENTAL_OWNER, SUBJECT_PAID_RENTAL_CLIENT,
                    BODY_PAID_RENTAL_OWNER_START + transaction.getCost().toString() + BODY_PAID_RENTAL_OWNER_END,
                    BODY_PAID_RENTAL_CLIENT_START + transaction.getCost().toString() + BODY_PAID_RENTAL_CLIENT_END);
            response = Response.ok(newTransaction).build();
        } catch (InsufficientBalanceException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }


    private void administrateCredits(Transaction transaction) throws InsufficientBalanceException {
        User owner = this.userService.findById(transaction.getRental().getOwnerCuil());
        User client = this.userService.findById(transaction.getRental().getClientCuil());
        if(!client.canPayForThis(transaction.getCost())){
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
        }
        client.payCredit(transaction.getCost());
        owner.receiveCredit(transaction.getCost());
        this.userService.saveTransactionUsers(owner, client);
    }


    @PUT
    @Path("/rental/returned")
    @Produces("application/json")
    @Consumes("application/json")
    public Response returnedVehicle(Transaction transaction) {
        Response response = null;
        Transaction newTransaction = null;
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            newTransaction = this.rentalService.returnedVehicleAndAdvance(transaction);
            String ownerMail = this.rentalService.mailByCuil(newTransaction.getRental().getOwnerCuil());
            String clientMail = this.rentalService.mailByCuil(newTransaction.getRental().getClientCuil());
            this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_RETURNED_RENTAL_OWNER,
                    SUBJECT_RETURNED_RENTAL_CLIENT, BODY_RETURNED_RENTAL_OWNER, BODY_RETURNED_RENTAL_CLIENT);
            response = Response.ok(newTransaction).build();
        } catch (ReturnedOutOfTermException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }




    @GET
    @Path("/transaction/{id}")
    @Produces("application/json")
    public Response findTransaction(@PathParam("id") final String id) {
        Transaction transaction = rentalService.findTransaction(id);
        if (transaction == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(transaction).build();
    }



    @POST
    @Path("/transaction/create")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createTransaction(Transaction transaction) throws Exception {
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transaction newTransaction = this.rentalService.createTransaction(transaction);
        String ownerMail = this.rentalService.mailByCuil(transaction.getRental().getOwnerCuil());
        String clientMail = this.rentalService.mailByCuil(transaction.getRental().getClientCuil());
        this.mailSenderService.notificateUsers(ownerMail, clientMail,  SUBJECT_CONFIRM_RENTAL_OWNER,
                SUBJECT_CONFIRM_RENTAL_CLIENT, BODY_CONFIRM_RENTAL_OWNER, BODY_CONFIRM_RENTAL_CLIENT);
        return Response.ok(newTransaction).build();
    }



    @POST
    @Path("/transaction/reject")
    @Produces("application/json")
    @Consumes("application/json")
    public Response rejectTransaction(Transaction transaction) {
        Response response = null;
        Transaction newTransaction = null;
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            newTransaction = this.rentalService.rejectTransaction(transaction);
            String ownerMail = this.rentalService.mailByCuil(newTransaction.getRental().getOwnerCuil());
            String clientMail = this.rentalService.mailByCuil(newTransaction.getRental().getClientCuil());
            this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_REJECTED_RENTAL_OWNER, SUBJECT_REJECTED_RENTAL_CLIENT,
                    BODY_REJECTED_RENTAL_OWNER, BODY_REJECTED_RENTAL_CLIENT);
            response = Response.ok(newTransaction).build();
        } catch (InvalidStatusToCancelOperationException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }



    @POST
    @Path("/score")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createScore(Score score) {
        Response response = null;
        Score newScore = null;
        if (score == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            newScore = this.rentalService.createScore(score);
            String creatorMail = this.rentalService.mailByCuil(newScore.getCreator());
            String userMail = this.rentalService.mailByCuil(newScore.getUserCuil());
            this.mailSenderService.notificateUsers(creatorMail, userMail, SUBJECT_SCORED_RENTAL_CREATOR, SUBJECT_SCORED_RENTAL_USER,
                    BODY_SCORED_RENTAL_CREATOR + newScore.getValue().toString(), BODY_SCORED_RENTAL_USER + newScore.getValue().toString());
            response = Response.ok(newScore).build();
        } catch (InvalidStatusToScoredException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }




}
