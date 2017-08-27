package service;


import database.MongoAccess;
import util.CloudConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/login/{username}/{password}")
public class LoginService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@PathParam("username") String username, @PathParam("password") String password) {
        return MongoAccess.login(username, password);
    }
}
