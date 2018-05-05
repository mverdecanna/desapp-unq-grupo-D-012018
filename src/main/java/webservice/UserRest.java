package webservice;

import persistence.UserRepository;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by mariano on 05/05/18.
 */

@Path("/user")
public class UserRest {

    public static final int NUMBER_OF_POST = 10;


    private UserRepository userRepository;


    @GET
    @Path("/count")
    @Produces("application/json")
    public Integer countUsers() {
        return this.userRepository.countUser();
    }



}
