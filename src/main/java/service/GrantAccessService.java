package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.UserLock;
import util.CloudConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/grantaccess")
public class GrantAccessService {
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUserToLock(String userLockJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserLock userLock = mapper.readValue(userLockJson, UserLock.class);
        boolean success = MongoAccess.addUserToLock(userLock);
        if (success) {
            return CloudConstants.ACCESS_GRANTED;
        } else {
            return CloudConstants.ALREADY_HAS_ACCESS;
        }
    }
}