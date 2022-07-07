package rs.raf.auth;

import rs.raf.resources.cms.CmsArticleResource;
import rs.raf.resources.cms.CmsCategoryResource;
import rs.raf.resources.cms.CmsUserResource;
import rs.raf.services.UserService;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }
        System.out.println("is author required");

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof CmsCategoryResource || matchedResource instanceof CmsUserResource ||
                    matchedResource instanceof CmsArticleResource) {
                return true;
            }
        }

        return false;
    }
}
