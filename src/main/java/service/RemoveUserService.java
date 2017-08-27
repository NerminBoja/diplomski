package service;

import database.MongoAccess;
import util.CloudConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/removeuser/{username}")
public class RemoveUserService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String removeUser(@PathParam("username") String username){
        boolean removed = MongoAccess.removeUser(username);
        if (removed){
            return CloudConstants.USER_REMOVED;
        }
        else{
            return CloudConstants.USER_NOT_FOUND;
        }
    }
}
