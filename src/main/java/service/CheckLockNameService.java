package service;


import database.MongoAccess;
import util.CloudConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/checklockname/{lockName}/{address}")
public class CheckLockNameService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkUser(@PathParam("lockName") String lockName,
                            @PathParam("address") String address) {
        boolean lockNameExists = false;
        try {
            lockNameExists = MongoAccess.checkIfLockExists(lockName, address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lockNameExists) {
            return CloudConstants.LOCK_NAME_EXISTS;
        } else {
            return CloudConstants.LOCK_NAME_AVAILABLE;
        }
    }
}
