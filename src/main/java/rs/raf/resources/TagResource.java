package rs.raf.resources;

import rs.raf.models.User;
import rs.raf.services.TagService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET//radi
    @Produces(MediaType.APPLICATION_JSON)
    public Response allTags() {
        return Response.ok(this.tagService.allTags()).build();
    }

}
