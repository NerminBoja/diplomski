package model;

import com.mongodb.BasicDBObject;

public class UserLock extends BasicDBObject {

    private String userName;

    private String lockName;

    private String lockAddress;

    private String ipAddress;

    private String port;

    public UserLock(String userName, String lockName, String lockAddress, String ipAddress, String port) {
        this.userName = userName;
        this.lockName = lockName;
        this.lockAddress = lockAddress;
        this.ipAddress = ipAddress;
        this.port = port;
        put("userName", userName);
        put("lockName", lockName);
        put("lockAddress", lockAddress);
        put("ipAddress", ipAddress);
        put("port", port);
    }

    public UserLock() {
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        this.userName = userName;
        put("userName", userName);
    }

    public String getLockName() {
        return getString("lockName");
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
        put("lockName", lockName);
    }

    public String getLockAddress() {
        return getString("lockAddress");
    }

    public void setLockAddress(String lockAddress) {
        this.lockAddress = lockAddress;
        put("lockAddress", lockAddress);
    }

    public String getIpAddress() {
        return getString("ipAddress");
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        put("ipAddress", ipAddress);
    }

    public String getPort() {
        return getString("port");
    }

    public void setPort(String port) {
        this.port = port;
        put("port", port);
    }
}