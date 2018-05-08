package webservice;

import ch.qos.logback.core.net.SyslogOutputStream;
import model.User;
import service.UserService;


import javax.ws.rs.*;


/**
 * Created by mariano on 05/05/18.
 */

@Path("/user")
public class UserRest {

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    UserService service;


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public User findUser(@PathParam("id") final String idUser) {
        User user = service.findById(idUser);
        return user;
    }

    @POST
    @Path("/save")
    @Consumes("application/json")
    public void saveUser(User usr) {
        System.out.println(usr);
//        service.save(usr);
    }


//    @GET
//    @Path("/byAuthor/{id}")
//    @Produces("application/json")
//    public Response findPostsPublishedByAuthorId(@PathParam("id") final String id) {
//        List<Post> posts = postRepository.getPosts(id);
//        if (posts.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(posts).build();
//    }
//
//    @GET
//    @Path("/count")
//    @Produces("application/json")
//    public Integer countPostsPublishedByBlogId(@DefaultValue(StringUtils.EMPTY) @QueryParam("tag") final String tag) {
//        return postRepository.getcount(tag);
//    }


}
