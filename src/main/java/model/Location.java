package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends BasicDBObject{

    private String name;

    private String address;

    private User owner;

    public Location(){

    }

    public Location(String name, String address, User owner){
        this.name = name;
        this.address = address;
        this.owner = owner;
        put("name", name);
        put("address", address);
        put("owner", owner);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        this.name = name;
        put("name", name);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        this.address = address;
        put("address", address);
    }

    public User getOwner() throws IOException {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        put("owner", owner);
    }
}
