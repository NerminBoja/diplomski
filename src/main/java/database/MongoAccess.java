package database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import model.*;
import util.CloudConstants;
import util.MongoConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MongoAccess {

    public static MongoDatabase getDB() {

        MongoCredential credential = MongoCredential.createCredential(
                MongoConstants.MONGO_USERNAME,
                MongoConstants.MONGO_DATABASE,
                MongoConstants.MONGO_PASSWORD.toCharArray());

        MongoClient client = new MongoClient(new ServerAddress(
                MongoConstants.MONGO_SERVER,
                MongoConstants.MONGO_PORT),
                Arrays.asList(credential));

        MongoDatabase db = client.getDatabase(MongoConstants.MONGO_DATABASE);

        return db;
    }

    public static boolean saveUser(User user) {
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        if (users.find(user).hasNext()) {
            return false;
        } else {
            users.save(user);
            users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
            if (users.find(user).hasNext()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String login(String username, String password) {
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        BasicDBObject login = new BasicDBObject();
        login.append("userName", username);
        login.append("password", password);
        DBCursor find = users.find(login);
        try {
            if (find.hasNext()) {
                String json = find.next().toString();
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(json, User.class);
                if (user.isOwnerAccount()) {
                    return CloudConstants.OWNER;
                } else {
                    return CloudConstants.USER;
                }
            } else {
                return CloudConstants.WRONG_USERNAME_PASSOWRD;
            }

        } catch (Exception e) {
            return e.toString();
        } finally {
            System.gc();
        }
    }

    public static boolean checkUser(String username) {
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        BasicDBObject find = new BasicDBObject();
        find.append("userName", username);
        if (users.find(find).hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean addLock(Lock lockToAdd) {
        DBCollection locks = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
        BasicDBObject find = new BasicDBObject();
        find.append("name", lockToAdd.getName());
        find.append("ipAddress", lockToAdd.getIpAddress());
        if (locks.find(find).hasNext()) {
            return false;
        } else {
            locks.insert(lockToAdd);
            locks = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
            if (locks.find(lockToAdd).hasNext()) {
                return true;
            } else {
                return false;
            }
        }

    }

    public static boolean addLocation(Location locationToAdd) {
        DBCollection locations = (DBCollection) getDB().getCollection(MongoConstants.LOCATIONS);
        locations.insert(locationToAdd);
        locations = (DBCollection) getDB().getCollection(MongoConstants.LOCATIONS);
        if (locations.find(locationToAdd).hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    public static User getUserByUsername(String username) throws IOException {
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        BasicDBObject find = new BasicDBObject();
        find.append("userName", username);
        DBCursor userFind = users.find(find);
        if (userFind.hasNext()) {
            String userJson = userFind.next().toString();
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(userJson, User.class);
            return user;
        }
        return null;
    }

    public static List<Location> getLocationsForOwner(String userName) throws IOException {
        DBCollection locations = (DBCollection) getDB().getCollection(MongoConstants.LOCATIONS);
        List<Location> allLocations = new ArrayList<>();
        BasicDBObject find = new BasicDBObject();
        find.append("owner.userName", userName);
        DBCursor ownerLocations = locations.find(find);
        ObjectMapper mapper = new ObjectMapper();
        while (ownerLocations.hasNext()) {
            Location location = mapper.readValue(ownerLocations.next().toString(), Location.class);
            allLocations.add(location);
        }
        return allLocations;

    }

    public static List<User> getAllUsernames() {
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        List<User> allUsers = new ArrayList<User>();
        DBCursor dbObjects = users.find();
        ObjectMapper mapper = new ObjectMapper();
        while (dbObjects.hasNext()) {
            try {
                User user = mapper.readValue(dbObjects.next().toString(), User.class);
                allUsers.add(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allUsers;
    }

    public static Lock getLockByNameAndAddress(String lockName, String address) throws IOException {
        DBCollection locks = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
        BasicDBObject find = new BasicDBObject();
        find.append("name", lockName);
        find.append("location.address", address);
        DBCursor dbObjects = locks.find(find);
        if (dbObjects.hasNext()) {
            ObjectMapper mapper = new ObjectMapper();
            Lock lock = mapper.readValue(dbObjects.next().toString(), Lock.class);
            return lock;
        } else {
            return null;
        }
    }

    public static boolean checkIfLockExists(String name, String address) throws IOException {
        Lock lock = getLockByNameAndAddress(name, address);
        if (lock != null) {
            return true;
        } else {
            return false;
        }

    }

    public static List<Lock> getAllLocks() throws IOException {
        List<Lock> allLocks = new ArrayList<Lock>();
        DBCollection locks = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
        DBCursor dbObjects = locks.find();
        for (int i = 0; i < dbObjects.size(); i++) {
            ObjectMapper mapper = new ObjectMapper();
            Lock lock = mapper.readValue(dbObjects.next().toString(), Lock.class);
            allLocks.add(lock);

        }
        return allLocks;
    }

    public static List<Lock> getLocksForOwner(String userName) throws IOException {
        List<Lock> locksForUser = new ArrayList<Lock>();
        DBCollection locks = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
        BasicDBObject find = new BasicDBObject();
        find.append("owner.userName", userName);
        DBCursor dbObjects = locks.find(find);
        while (dbObjects.hasNext()) {
            ObjectMapper mapper = new ObjectMapper();
            Lock lock = mapper.readValue(dbObjects.next().toString(), Lock.class);
            locksForUser.add(lock);
        }
        return locksForUser;
    }

    public static List<UserLock> getLocksForUser(String userName) throws IOException {
        DBCollection userLockCollection = (DBCollection) getDB().getCollection(MongoConstants.USER_LOCK);
        List<UserLock> userLockList = new ArrayList<>();
        BasicDBObject find = new BasicDBObject();
        find.append("userName", userName);
        DBCursor dbObjects = userLockCollection.find(find);
        while (dbObjects.hasNext()) {
            String userLockJson = dbObjects.next().toString();
            ObjectMapper mapper = new ObjectMapper();
            UserLock userLock = mapper.readValue(userLockJson, UserLock.class);
            userLockList.add(userLock);

        }
        return userLockList;
    }

    public static List<User> searchUser(String search) throws IOException {
        List<User> returnUsers = new ArrayList<>();
        DBCollection users = (DBCollection) getDB().getCollection(MongoConstants.USERS);
        BasicDBObject find = new BasicDBObject();
        find.put("firstName", Pattern.compile(search));
        DBCursor dbObjects = users.find(find);
        while (dbObjects.hasNext()) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(dbObjects.next().toString(), User.class);
            returnUsers.add(user);
        }
        return returnUsers;
    }

    public static boolean addUserToLock(UserLock userLock) {
        DBCollection collection = (DBCollection) getDB().getCollection(MongoConstants.USER_LOCK);
        BasicDBObject find = new BasicDBObject();
        find.append("userName", userLock.getUserName());
        find.append("lockName", userLock.getLockName());
        find.append("lockAddress", userLock.getLockAddress());
        if (collection.find(find).hasNext()) {
            return false;
        } else {
            collection.insert(userLock);
            return true;
        }
    }

    public static boolean checkIfUserHasAccessRightsToLock(UserLock userLock) {
        DBCollection collection = (DBCollection) getDB().getCollection(MongoConstants.USER_LOCK);
        BasicDBObject find = new BasicDBObject();
        find.append("userName", userLock.getUserName());
        find.append("lockName", userLock.getLockName());
        find.append("lockAddress", userLock.getLockAddress());
        if (collection.find(find).hasNext()) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean removeLocation(LocationAndroid location) throws IOException {
        MongoDatabase db = getDB();
        DBCollection locationCollection = (DBCollection) db.getCollection(MongoConstants.LOCATIONS);
        DBCollection lockCollection = (DBCollection) db.getCollection(MongoConstants.LOCKS);
        db.getCollection(MongoConstants.LOCKS);
        BasicDBObject locationFind = new BasicDBObject();
        locationFind.append("address", location.getAddress());
        locationFind.append("name", location.getName());
        locationFind.append("owner.userName", location.getOwner().getUserName());
        if (!locationCollection.find(locationFind).hasNext()) {
            return false;
        } else {
            BasicDBObject lockFind = new BasicDBObject();
            lockFind.append("location.address", location.getAddress());
            lockFind.append("location.name", location.getName());
            lockFind.append("location.owner.userName", location.getOwner().getUserName());
            lockCollection.remove(lockFind);
            locationCollection.remove(locationFind);
            if (locationCollection.find(locationFind).hasNext()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean removeLock(LockAndroid lock) {
        DBCollection lockCollection = (DBCollection) getDB().getCollection(MongoConstants.LOCKS);
        BasicDBObject lockFind = new BasicDBObject();
        lockFind.append("name", lock.getName());
        lockFind.append("ipAddress", lock.getIpAddress());
        lockFind.append("port", lock.getPort());
        if (lockCollection.find(lockFind).hasNext()) {
            lockCollection.remove(lockFind);
            return true;
        } else {
            return false;
        }

    }

    public static boolean removeUser(String username) {
        MongoDatabase db = getDB();
        DBCollection users = (DBCollection) db.getCollection(MongoConstants.USERS);
        BasicDBObject find = new BasicDBObject();
        find.append("userName", username);
        if (!users.find(find).hasNext()) {
            return false;
        } else {
            users.remove(find);
            return true;
        }
    }
}