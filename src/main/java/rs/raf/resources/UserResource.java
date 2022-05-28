package rs.raf.resources;

import rs.raf.models.User;
import rs.raf.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(@PathParam("id") Integer userId){
        return this.userService.findUser(userId);
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserByEmail(@PathParam("email") String email){
        return this.userService.findUserByEmail(email);
    }

    //todo find user by email

    //todo login

    //    @POST
    //    @Path("/login")
    //    @Produces(MediaType.APPLICATION_JSON)
    //    public Response login(@Valid LoginRequest loginRequest) {
    //        Map<String, String> response = new HashMap<>();
    //
    //        String jwt = this.korisnikService.login(loginRequest.getEmail(), loginRequest.getLozinka());
    //        if (jwt == null) {
    //            response.put("message", "No such email and password combination");
    //            return Response.status(422, "Unprocessable entity").entity(response).build();
    //        }
    //        response.put("jwt", jwt);
    //
    //        return Response.ok(response).build();
    //    }
}
