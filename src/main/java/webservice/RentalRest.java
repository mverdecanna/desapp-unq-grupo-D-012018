package webservice;

import model.Rental;
import model.Transaction;
import model.exceptions.InsufficientBalanceException;
import org.joda.time.DateTime;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import service.MailSenderService;
import service.RentalService;

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

    //private MailSenderService mailSenderService;


    public RentalService getRentalService() {
        return rentalService;
    }

    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
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
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Rental newRental = this.rentalService.createRental(rental);
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




/*
    @GET
    @Async
    @Path("/send")
    @Produces("application/json")
    public void sendMail() throws Exception {
        Thread t = new Thread(){

            public void run(MailSenderService mailSenderService){
                try{
                    //Response jaxrs = Response.ok("basic").type(MediaType.TEXT_PLAIN).build();
                    //asyncResponse.setResponse(jaxrs);
                    //asyncResponse.resume(jaxrs);
                    mailSenderService.sendMail("mverdecanna@gmail.com", SUBJECT_CONFIRM_RENTAL_OWNER, BODY_CONFIRM_RENTAL_OWNER);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();

        this.mailSenderService.sendMail("mverdecanna@gmail.com", SUBJECT_CONFIRM_RENTAL_OWNER, BODY_CONFIRM_RENTAL_OWNER);
    }
*/





}
