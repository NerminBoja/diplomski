package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.Location;
import util.CloudConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/addlocation")
public class AddLocationService {
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(String location) {
        Location locationToAdd = new Location();
        ObjectMapper mapper = new ObjectMapper();
        try {
            locationToAdd = mapper.readValue(location, Location.class);
            try {
                boolean added = MongoAccess.addLocation(locationToAdd);
                if (added) {
                    return CloudConstants.LOCATION_ADDED;
                } else {
                    return CloudConstants.LOCATION_NOT_ADDED;
                }
            } catch (Exception e) {
                return e.toString();
            }
        } catch (IOException e) {
            return e.toString();
        }
        finally {
            System.gc();
        }

    }
}
