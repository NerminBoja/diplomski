package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserLock;
import util.CloudConstants;
import util.ServiceConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;


@Path("/unlock")
public class UnlockService {
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(String userLock) {
        boolean succcess = true;
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserLock lock = mapper.readValue(userLock, UserLock.class);
            try {
                if (lock.getIpAddress() != null && lock.getPort() != null) {
                    URL url = new URL(ServiceConstants.unlock(lock.getIpAddress(), lock.getPort()));
                    URLConnection urlConnection = url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    is.close();
                } else {
                    return CloudConstants.IP_ADDRES_OR_PORT_NULL;
                }
            } catch (IOException e) {
                succcess = false;
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                return sw.toString();
            }
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
        if (succcess) {
            return CloudConstants.UNLOCKED;
        } else {
            return CloudConstants.ACCESS_DENIED;
        }
    }
}