package webservice;

import model.CurrentAccount;
import model.User;
import model.Vehicle;
import model.builder.UserBuilder;
import model.dto.UserDto;
import org.apache.cxf.jaxrs.ext.PATCH;
import persistence.UserRepository;
import service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by mariano on 05/05/18.
 */

@Path("/user")
public class UserRest {




    public static final int NUMBER_OF_POST = 10;


    private UserService userService;


    public void setUserService(final UserService service) {
        this.userService = service;
    }


    public UserService getUserService() {
        return userService;
    }


    @GET
    @Path("/count")
    @Produces("application/json")
    public Integer countUsers() {
        Integer resp = this.userService.count();
        if(resp == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return resp;
    }




    @GET
    @Path("/mail/{mail}")
    @Produces("application/json")
    public Response findCuilByMail(@PathParam("mail") final String mail) {
        UserDto userDto = userService.findByMail(mail);
        if (userDto == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(userDto).build();
    }



    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findUser(@PathParam("id") final String idUser) {
        User user = userService.findById(idUser);
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(user).build();
    }



    @GET
    @Path("/all")
    @Produces("application/json")
    public Response findAllUsers() {
        List<User> users = userService.retriveAll();
        if (users == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(users).build();
    }


    @POST
    @Path("/save")
    @Produces("application/json")
    @Consumes("application/json")
    public Response saveUser(User user) {
        if(user == null){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        this.userService.save(user);
        return Response.ok(user).build();
    }


    @PUT
    @Path("/update")
    @Produces("application/json")
    public Response updateUser(User user){
        this.userService.update(user);
        return Response.ok(user).build();
    }



    @GET
    @Path("/currentAccount/{cuil}")
    @Produces("application/json")
    public Response findCurrentAccount(@PathParam("cuil") final String cuil) {
        CurrentAccount currentAccount = this.userService.findCurrentAccount(cuil);
        if (currentAccount == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok(currentAccount).build();
    }


    @PUT
    @Path("/currentAccount/charge/{credit}")
    @Produces("application/json")
    public Response chargeCredit(CurrentAccount currentAccount, @PathParam("credit") final Integer credit){
        this.userService.addCreditToCurrentAccount(currentAccount, credit);
        return Response.ok(currentAccount).build();
    }


    @PUT
    @Path("/currentAccount/pay/{credit}")
    @Produces("application/json")
    public Response payCredit(CurrentAccount currentAccount, @PathParam("credit") final Integer credit){
        this.userService.subtractCreditToCurrentAccount(currentAccount, credit);
        return Response.ok(currentAccount).build();
    }



}
