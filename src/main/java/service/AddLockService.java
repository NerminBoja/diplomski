package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.Lock;
import util.CloudConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/addlock")
public class AddLockService {
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addLock(String lock) throws IOException {
        Lock lockToAdd = new Lock();
        ObjectMapper mapper = new ObjectMapper();
        lockToAdd = mapper.readValue(lock, Lock.class);
        if (lockToAdd != null) {
            boolean added = MongoAccess.addLock(lockToAdd);
            if (added) {
                return CloudConstants.LOCK_ADDED;
            } else {
                return CloudConstants.LOCK_EXISTS;
            }
        }
        return null;

    }
}
