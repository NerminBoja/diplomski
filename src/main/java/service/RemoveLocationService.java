package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.LocationAndroid;
import util.CloudConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/removelocation")
public class RemoveLocationService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String removeLocation(String locationJSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LocationAndroid location = mapper.readValue(locationJSON, LocationAndroid.class);
        boolean removed = MongoAccess.removeLocation(location);
        if (removed){
            return CloudConstants.LOCATION_REMOVED;
        }
        else{
            return CloudConstants.LOCATION_NOT_FOUND;
        }

    }
}
