package tech.c3n7.keycloak.MyRemoteUserStorageProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
public interface UsersApiService {
    @GET
    @Path("/{username}")
   User getUserDetails(@PathParam("username") String username);
}
