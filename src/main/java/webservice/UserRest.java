package webservice;

import persistence.UserRepository;
import service.UserService;

import javax.ws.rs.*;
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
        //return this.userService.nUsuers();
        return 111111;
    }


}
