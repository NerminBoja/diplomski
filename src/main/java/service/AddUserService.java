package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.User;
import util.CloudConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/adduser")
public class AddUserService {
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(String user) {
        User userToAdd = new User();
        ObjectMapper mapper = new ObjectMapper();
        try {
            userToAdd = mapper.readValue(user, User.class);
            if (userToAdd != null) {
                try {
                    boolean added = MongoAccess.saveUser(userToAdd);
                    if (added) {
                        return CloudConstants.REGISTRATION_SUCCESS;
                    } else {
                        return CloudConstants.REGISTRATION_FAILURE;
                    }
                } catch (Exception e) {
                    return e.toString();
                }
            } else {
                return "USER IS NULL";
            }
        } catch (IOException e) {
            return e.toString();
        }
        finally {
            System.gc();
        }

    }
}
