package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.User;
import util.CloudConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;


@Path("/searchuser/{search}")
public class SearchUsersService {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String searchUser(@PathParam("search") String search){
        try {
            List<User> users = MongoAccess.searchUser(search);
            ObjectMapper mapper = new ObjectMapper();
            String usersJson = mapper.writeValueAsString(users);
            return usersJson;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
