package service;


import database.MongoAccess;
import util.CloudConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/checkusername/{username}")
public class CheckUsernameService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkUser(@PathParam("username") String username) {
        boolean checkUser = MongoAccess.checkUser(username);
        if (checkUser) {
            return CloudConstants.USER_EXISTS;
        } else {
            return CloudConstants.USERNAME_AVAILABLE;
        }
    }
}
