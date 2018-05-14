package webservice;

import model.User;
import model.Vehicle;
import service.VehicleService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by mariano on 13/05/18.
 */

@Path("/vehicle")
public class VehicleRest {


    public static final int NUMBER_OF_POST = 11;


    private VehicleService vehicleService;


    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public void setVehicleService(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }



    @GET
    @Path("/count")
    @Produces("application/json")
    public Integer countVehicles() {
        Integer resp = this.vehicleService.count();
        if(resp == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return resp;
    }


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findVehicle(@PathParam("id") final String idVehicle) {
        Vehicle vehicle = vehicleService.findById(idVehicle);
        if (vehicle == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(vehicle).build();
    }



    @GET
    @Path("/all")
    @Produces("application/json")
    public Response findAllVehicles() {
        List<Vehicle> vehicles = vehicleService.retriveAll();
        if (vehicles == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(vehicles).build();
    }


}
