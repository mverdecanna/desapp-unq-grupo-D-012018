package webservice;

import model.User;
import model.Vehicle;
import model.exceptions.InvalidRegisterParameterException;
import org.apache.cxf.jaxrs.ext.PATCH;
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



    @POST
    @Path("/save")
    @Produces("application/json")
    @Consumes("application/json")
    public Response saveVehicles(Vehicle vehicle) {
        Response response = null;
        if(vehicle == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            Vehicle newVehicle = this.vehicleService.saveVehicle(vehicle);
            response = Response.ok(newVehicle).build();
        } catch (InvalidRegisterParameterException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }


    @DELETE
    @Path("/delete/{id}")
    @Produces("application/json")
    public Response deleteVehicles(@PathParam("id") final String idVehicle){
        Vehicle vehicle = vehicleService.findById(idVehicle);
        if(vehicle == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        this.vehicleService.delete(vehicle);
        return Response.ok().build();
    }


    @PUT
    @Path("/update")
    @Produces("application/json")
    public Response updateVehicles(Vehicle vehicle){
        this.vehicleService.update(vehicle);
        return Response.ok().build();
    }


    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Response vehiclesByUser(@PathParam("id") final String idUser) {
        List<Vehicle> vehicles = vehicleService.vehicleList(idUser);
        if (vehicles == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(vehicles).build();
    }



    @GET
    @Path("/notUser/{id}")
    @Produces("application/json")
    public Response vehiclesOfOthers(@PathParam("id") final String idUser) {
        List<Vehicle> vehicles = vehicleService.othersVehicles(idUser);
        if (vehicles == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(vehicles).build();
    }



}
