package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LockAndroid {

    private Location location;

    private String name;

    private String ipAddress;

    private String port;

    private User owner;

    public LockAndroid(Location location, User owner, String name, String ipAddress, String port) {
        this.location = location;
        this.name = name;
        this.ipAddress = ipAddress;
        this.owner = owner;
        this.port = port;

    }

    public LockAndroid() {

    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
