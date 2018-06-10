package webservice;

import model.Rental;
import org.joda.time.DateTime;
import service.RentalService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

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
        this.rentalService.save(rental);
        return Response.ok(rental).build();
    }




}
