package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.Lock;
import model.UserLock;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Path("/getlocksforuser/{userName}")
public class GetLocksForUserService {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getLocations(@PathParam("userName") String userName) {
        try {
            List<UserLock> locksForUser = MongoAccess.getLocksForUser(userName);
            ObjectMapper mapper = new ObjectMapper();
            String lockForUserJson = mapper.writeValueAsString(locksForUser);
            return lockForUserJson;
        } catch (IOException e) {
            return e.getMessage();
        }
        finally {
            System.gc();
        }
    }
}
