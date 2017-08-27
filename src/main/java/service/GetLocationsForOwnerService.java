package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


@Path("/getlocations/{userName}")
public class GetLocationsForOwnerService {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getLocations(@PathParam("userName") String userName) {
        try {
            List<Location> locationsForUser = MongoAccess.getLocationsForOwner(userName);
            if (locationsForUser != null && !locationsForUser.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                final StringWriter out = new StringWriter();
                mapper.writeValue(out, locationsForUser);
                out.close();
                return out.toString();
            } else {
                return "EMPTY";
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.toString());
        }
        finally {
            System.gc();
        }
        return null;
    }
}
