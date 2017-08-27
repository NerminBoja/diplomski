package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.LockAndroid;
import util.CloudConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/removelock")
public class RemoveLockService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String removeLock(String lockJSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LockAndroid lock = mapper.readValue(lockJSON, LockAndroid.class);
        boolean removed = MongoAccess.removeLock(lock);
        if (removed){
            return CloudConstants.LOCK_REMOVED;
        }
        else{
            return CloudConstants.LOCK_NOT_FOUND;
        }

    }
}
