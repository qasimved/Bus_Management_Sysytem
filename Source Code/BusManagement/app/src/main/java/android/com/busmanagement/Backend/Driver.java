package android.com.busmanagement.Backend;

import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;



public class Driver {
    private static String driverId;
    private static String driverName;
    private static String driverCnic;
    private static String driverphoneNo;
    private static String driverEmail;
    private static String driverPassword;
    private static String driverLatLng;
    private SharedPrefManager sharedPrefManager;

    public Driver(String driverId, String driverName, String driverCnic, String driverphoneNo,String driverEmail, String driverPassword,String driverLatLng,Context context) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverCnic = driverCnic;
        this.driverphoneNo = driverphoneNo;
        this.driverEmail = driverEmail;
        this.driverPassword = driverPassword;
        this.driverLatLng = driverLatLng;
        sharedPrefManager = SharedPrefManager.getInstance(context);

        this.saveUserdata();
    }

    public void saveUserdata() {

        sharedPrefManager.setStringVar("driverId", driverId);
        sharedPrefManager.setStringVar("driverName", driverName);
        sharedPrefManager.setStringVar("driverCnic", driverCnic);
        sharedPrefManager.setStringVar("driverphoneNo", driverphoneNo);
        sharedPrefManager.setStringVar("driverEmail", driverEmail);
        sharedPrefManager.setStringVar("driverPassword", driverPassword);
        sharedPrefManager.setStringVar("driverLatLng", driverLatLng);
        sharedPrefManager.setStringVar("loginAs", "driver");

    }


    public Driver(Context context) {
        sharedPrefManager = SharedPrefManager.getInstance(context);
        this.driverId = sharedPrefManager.getStringVar("driverId");
        this.driverName = sharedPrefManager.getStringVar("driverName");
        this.driverCnic = sharedPrefManager.getStringVar("driverCnic");
        this.driverphoneNo = sharedPrefManager.getStringVar("driverphoneNo");
        this.driverphoneNo = sharedPrefManager.getStringVar("driverEmail");
        this.driverphoneNo = sharedPrefManager.getStringVar("driverPassword");
        this.driverphoneNo = sharedPrefManager.getStringVar("driverLatLng");

    }

    public static String getDriverEmail() {
        return driverEmail;
    }

    public static void setDriverEmail(String driverEmail) {
        Driver.driverEmail = driverEmail;
    }

    public static String getDriverPassword() {
        return driverPassword;
    }

    public static void setDriverPassword(String driverPassword) {
        Driver.driverPassword = driverPassword;
    }

    public static String getDriverLatLng() {
        return driverLatLng;
    }

    public static void setDriverLatLng(String driverLatLng) {
        Driver.driverLatLng = driverLatLng;
    }

    public static String getDriverId() {
        return driverId;
    }

    public static void setDriverId(String driverId) {
        Driver.driverId = driverId;
    }

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        Driver.driverName = driverName;
    }

    public static String getDriverCnic() {
        return driverCnic;
    }

    public static void setDriverCnic(String driverCnic) {
        Driver.driverCnic = driverCnic;
    }

    public static String getDriverphoneNo() {
        return driverphoneNo;
    }

    public static void setDriverphoneNo(String driverphoneNo) {
        Driver.driverphoneNo = driverphoneNo;
    }
}
