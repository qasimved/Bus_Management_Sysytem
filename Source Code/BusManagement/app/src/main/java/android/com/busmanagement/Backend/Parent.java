package android.com.busmanagement.Backend;

import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;


public class Parent {
    private static String parentId;
    private static String parentName;
    private static String parentEmail;
    private static String parentPassword;
    private static String parentCnic;
    private static String parentPhoneNo;
    private static String studenId;
    private SharedPrefManager sharedPrefManager;

    public Parent(String parentId, String parentName, String parentEmail, String parentPassword, String parentCnic, String parentPhoneNo, String studenId, Context context) {
        this.parentId = parentId;
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
        this.parentCnic = parentCnic;
        this.parentPhoneNo = parentPhoneNo;
        this.studenId = studenId;
        sharedPrefManager = SharedPrefManager.getInstance(context);

        this.saveUserdata();
    }


    public void saveUserdata() {

        sharedPrefManager.setStringVar("parentId", parentId);
        sharedPrefManager.setStringVar("parentName", parentName);
        sharedPrefManager.setStringVar("parentEmail", parentEmail);
        sharedPrefManager.setStringVar("parentPassword", parentPassword);
        sharedPrefManager.setStringVar("parentCnic", parentCnic);
        sharedPrefManager.setStringVar("parentPhoneNo", parentPhoneNo);
        sharedPrefManager.setStringVar("studenId", studenId);
        sharedPrefManager.setStringVar("loginAs", "parent");
    }


    public Parent(Context context) {
        sharedPrefManager = SharedPrefManager.getInstance(context);
        this.parentId = sharedPrefManager.getStringVar("parentId");
        this.parentName = sharedPrefManager.getStringVar("parentName");
        this.parentEmail = sharedPrefManager.getStringVar("parentEmail");
        this.parentPassword = sharedPrefManager.getStringVar("parentPassword");
        this.parentCnic = sharedPrefManager.getStringVar("parentCnic");
        this.parentPhoneNo = sharedPrefManager.getStringVar("parentPhoneNo");
        this.studenId = sharedPrefManager.getStringVar("studenId");

    }

    public static String getParentId() {
        return parentId;
    }

    public static void setParentId(String parentId) {
        Parent.parentId = parentId;
    }

    public static String getParentName() {
        return parentName;
    }

    public static void setParentName(String parentName) {
        Parent.parentName = parentName;
    }

    public static String getParentEmail() {
        return parentEmail;
    }

    public static void setParentEmail(String parentEmail) {
        Parent.parentEmail = parentEmail;
    }

    public static String getParentPassword() {
        return parentPassword;
    }

    public static void setParentPassword(String parentPassword) {
        Parent.parentPassword = parentPassword;
    }

    public static String getParentCnic() {
        return parentCnic;
    }

    public static void setParentCnic(String parentCnic) {
        Parent.parentCnic = parentCnic;
    }

    public static String getParentPhoneNo() {
        return parentPhoneNo;
    }

    public static void setParentPhoneNo(String parentPhoneNo) {
        Parent.parentPhoneNo = parentPhoneNo;
    }

    public static String getStudenId() {
        return studenId;
    }

    public static void setStudenId(String studenId) {
        Parent.studenId = studenId;
    }
}
