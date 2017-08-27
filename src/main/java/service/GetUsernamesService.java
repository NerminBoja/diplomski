package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import database.MongoAccess;
import model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


@Path("/getusernames")
public class GetUsernamesService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllUsernames() {
        List<User> allUsers = MongoAccess.getAllUsernames();
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writter = new StringWriter();
        try {
            mapper.writeValue(writter, allUsers);
            writter.close();
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writter.toString();
    }
}
