package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.Lock;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Path("/getlocksforowner/{userName}")
public class GetLocksForOwnerService {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getLocations(@PathParam("userName") String userName) {

        try {
            List<Lock> locksForUser = MongoAccess.getLocksForOwner(userName);
            if (locksForUser != null && !locksForUser.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                String returnJson = mapper.writeValueAsString(locksForUser);
                return returnJson;
            }
            else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.gc();
        }
        return null;
    }
}
