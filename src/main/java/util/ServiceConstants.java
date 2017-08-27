package util;

public class ServiceConstants {

    private static final String UNLOCK_ENPOINT = "/IoTService/service/lockunlock/UNLOCK";

    public static String unlock(String ipAddress, String port){
        return "http://" + ipAddress + ":" + port + UNLOCK_ENPOINT;
    }
}
