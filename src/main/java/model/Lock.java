package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.BasicDBObject;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lock extends BasicDBObject {

    private Location location;

    private String name;

    private String ipAddress;

    private String port;

    private User owner;

    public Lock(Location location, User owner, String name, String ipAddress, String port) {
        this.location = location;
        this.name = name;
        this.ipAddress = ipAddress;
        this.owner = owner;
        this.port = port;
        put("location", location);
        put("name", name);
        put("ipAddress", ipAddress);
        put("owner", owner);
        put("port", port);
    }

    public Lock() {

    }

    public Location getLocation() {
        return (Location) get("location");
    }

    public void setLocation(Location location) {
        this.location = location;
        put("location", location);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        this.name = name;
        put("name", name);
    }

    public String getIpAddress() {
        return getString("ipAddress");
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        put("ipAddress", ipAddress);
    }

    public User getOwner() throws IOException {
        return (User) get("owner");
    }

    public void setOwner(User owner) {
        this.owner = owner;
        put("owner", owner);
    }

    public String getPort() {
        return getString("port");
    }

    public void setPort(String port) {
        this.port = port;
        put("port", port);
    }
}
