package rs.raf.resources;

import rs.raf.models.User;
import rs.raf.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/aa")
public class UserResource {

    @Inject
    private UserService userService;

//    @GET
//    @Path("/user_id")
//    @Produces(MediaType.APPLICATION_JSON)
//    public User getUser (@PathParam("id") Integer userId){
//        return this.userService.findUser(userId);
//    }
}
