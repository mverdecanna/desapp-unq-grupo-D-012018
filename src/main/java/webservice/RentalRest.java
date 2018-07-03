package webservice;

import model.Rental;
import model.Transaction;
import org.joda.time.DateTime;
import service.RentalService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by mariano on 10/06/18.
 */
@Path("/rental")
public class RentalRest {


    private RentalService rentalService;


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
    public Response collectVehicle(Rental rental) {
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        this.rentalService.payAndAdvance(rental);
        return Response.ok(rental).build();
    }



    @PUT
    @Path("/rental/pay")
    @Produces("application/json")
    @Consumes("application/json")
    public Response payRental(Rental rental) {
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        this.rentalService.collectVehicleAndAdvance(rental);
        return Response.ok(rental).build();
    }



    @PUT
    @Path("/rental/returned")
    @Produces("application/json")
    @Consumes("application/json")
    public Response returnedVehicle(Rental rental) {
        if(rental == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        this.rentalService.returnedVehicleAndAdvance(rental);
        return Response.ok(rental).build();
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
    public Response createTransaction(Transaction transaction) {
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





}
