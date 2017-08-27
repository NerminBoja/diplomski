package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BasicDBObject {

    private String firstName;

    private String lastName;

    private String eMailAddress;

    private String userName;

    private String password;

    private String address;

    private String phone;

    private String company;

    private boolean ownerAccount;

    public User(String firstName,
                String lastName,
                String eMailAddress,
                String username,
                String password,
                String address,
                String phone,
                String company,
                boolean ownerAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMailAddress = eMailAddress;
        this.userName = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.company = company;
        this.ownerAccount = ownerAccount;

        put("firstName", firstName);
        put("lastName", lastName);
        put("eMailAddress", eMailAddress);
        put("username", username);
        put("password", password);
        put("address", address);
        put("phone", phone);
        put("company", company);
        put("ownerAccount", ownerAccount);
    }

    public User() {

    }

    public String getFirstName() {
        return getString("firstName");
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        put("firstName", firstName);
    }

    public String getLastName() {
        return getString("lastName");
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        put("lastName", lastName);
    }

    public String geteMailAddress() {
        return getString("eMailAddress");
    }

    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
        put("eMailAddress", eMailAddress);
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        this.userName = userName;
        put("userName", userName);
    }

    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String password) {
        this.password = password;
        put("password", password);
    }

    public String getCompany() {
        return getString("company");
    }

    public void setCompany(String company) {
        this.company = company;
        put("company", company);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        this.address = address;
        put("address", address);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        this.phone = phone;
        put("phone", phone);
    }

    public boolean isOwnerAccount() {
        return getBoolean("ownerAccount");
    }

    public void setOwnerAccount(boolean ownerAccount) {
        this.ownerAccount = ownerAccount;
        put("ownerAccount", ownerAccount);
    }
}
