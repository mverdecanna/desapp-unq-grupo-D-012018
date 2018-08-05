package webservice;

import model.Rental;
import model.Score;
import model.Transaction;
import model.User;
import model.exceptions.InsufficientBalanceException;
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



/*
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
            response = Response.ok(newRental).build();
        } catch (VehicleAssociatedToActiveRentalException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

     */



    @POST
    @Path("/create")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createRental(Rental rental) {
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        //User user = this.userService.findById(rental.getOwnerCuil());
        Rental newRental = this.rentalService.createRental(rental);
        String ownerMail = this.rentalService.mailByCuil(newRental.getOwnerCuil());
        String clientMail = this.rentalService.mailByCuil(newRental.getClientCuil());
        this.mailSenderService.notificateUsers(ownerMail, clientMail, SUBJECT_CREATE_RENTAL_OWNER, SUBJECT_CREATE_RENTAL_CLIENT,
                BODY_CREATE_RENTAL_OWNER, BODY_CREATE_RENTAL_CLIENT);
        return Response.ok(newRental).build();
    }



    @PUT
    @Path("/rental/collect")
    @Produces("application/json")
    @Consumes("application/json")
    public Response collectVehicle(Transaction transaction) {
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transaction newTransaction = this.rentalService.collectVehicleAndAdvance(transaction);
        return Response.ok(newTransaction).build();
    }



    @PUT
    @Path("/rental/pay")
    @Produces("application/json")
    @Consumes("application/json")
    public Response payRental(Transaction transaction) {
        Response response = null;
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transaction newTransaction = null;
        try {
            newTransaction = this.rentalService.payAndAdvance(transaction);
            response = Response.ok(newTransaction).build();
        } catch (InsufficientBalanceException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }



    @PUT
    @Path("/rental/returned")
    @Produces("application/json")
    @Consumes("application/json")
    public Response returnedVehicle(Transaction transaction) {
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transaction newTransaction = this.rentalService.returnedVehicleAndAdvance(transaction);
        return Response.ok(newTransaction).build();
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
        return Response.ok(newTransaction).build();

    }



    @POST
    @Path("/transaction/reject")
    @Produces("application/json")
    @Consumes("application/json")
    public Response rejectTransaction(Transaction transaction) {
        if(transaction == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transaction newTransaction = this.rentalService.rejectTransaction(transaction);
        return Response.ok(newTransaction).build();
    }



    @POST
    @Path("/score")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createScore(Score score) {
        if (score == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Score newScore = this.rentalService.createScore(score);
        return Response.ok(newScore).build();
    }



}
