package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.User;
import util.CloudConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/getuser/{username}")
public class GetUserService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkUser(@PathParam("username") String username) {
        try {
            User user = MongoAccess.getUserByUsername(username);
            ObjectMapper mapper = new ObjectMapper();
            String userJson = mapper.writeValueAsString(user);
            return userJson;
        } catch (IOException e) {
            return e.toString();
        }
        finally {
            System.gc();
        }
    }
}
