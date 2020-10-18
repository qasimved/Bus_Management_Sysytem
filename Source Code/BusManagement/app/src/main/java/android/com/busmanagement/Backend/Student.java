package android.com.busmanagement.Backend;

import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;



public class Student {
    private static String studentId;
    private static String studentName;
    private static String studentRegNo;
    private static String studentEmail;
    private static String studentPassword;
    private static String studentCnic;
    private static String studentPhoneNo;
    private static String studentLatLng;
    private static String routeId;
    private SharedPrefManager sharedPrefManager;



    public Student(String studentId, String studentName, String studentRegNo, String studentEmail, String studentPassword, String studentCnic, String studentPhoneNo, String studentLatLng, String routeId, Context context)
     {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRegNo = studentRegNo;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.studentCnic = studentCnic;
        this.studentPhoneNo = studentPhoneNo;
        this.studentLatLng = studentLatLng;
        if(routeId.equals("0")){
            this.routeId = "";
        }
        else{
            this.routeId = routeId;
        }

        sharedPrefManager = SharedPrefManager.getInstance(context);

         this.saveUserdata();
    }

    public void saveUserdata(){

        sharedPrefManager.setStringVar("studentId",studentId);
        sharedPrefManager.setStringVar("studentName",studentName);
        sharedPrefManager.setStringVar("studentRegNo",studentRegNo);
        sharedPrefManager.setStringVar("studentEmail",studentEmail);
        sharedPrefManager.setStringVar("studentPassword",studentPassword);
        sharedPrefManager.setStringVar("studentCnic",studentCnic);
        sharedPrefManager.setStringVar("studentPhoneNo",studentPhoneNo);
        sharedPrefManager.setStringVar("studentLatLng",studentLatLng);
        sharedPrefManager.setStringVar("routeId",routeId);
        sharedPrefManager.setStringVar("loginAs", "student");
    }


    public Student(Context context) {
        sharedPrefManager = SharedPrefManager.getInstance(context);
        this.studentId=sharedPrefManager.getStringVar("studentId");
        this.studentName=sharedPrefManager.getStringVar("studentName");
        this.studentRegNo=sharedPrefManager.getStringVar("studentRegNo");
        this.studentEmail=sharedPrefManager.getStringVar("studentEmail");
        this.studentPassword=sharedPrefManager.getStringVar("studentPassword");
        this.studentCnic=sharedPrefManager.getStringVar("studentCnic");
        this.studentPhoneNo=sharedPrefManager.getStringVar("studentPhoneNo");
        this.studentLatLng=sharedPrefManager.getStringVar("studentLatLng");
        this.routeId=sharedPrefManager.getStringVar("routeId");
    }

    public static String getStudentId() {
        return studentId;
    }

    public static void setStudentId(String studentId) {
        Student.studentId = studentId;
    }

    public static String getStudentName() {
        return studentName;
    }

    public static void setStudentName(String studentName) {
        Student.studentName = studentName;
    }

    public static String getStudentRegNo() {
        return studentRegNo;
    }

    public static void setStudentRegNo(String studentRegNo) {
        Student.studentRegNo = studentRegNo;
    }

    public static String getStudentEmail() {
        return studentEmail;
    }

    public static void setStudentEmail(String studentEmail) {
        Student.studentEmail = studentEmail;
    }

    public static String getStudentPassword() {
        return studentPassword;
    }

    public static void setStudentPassword(String studentPassword) {
        Student.studentPassword = studentPassword;
    }

    public static String getStudentCnic() {
        return studentCnic;
    }

    public static void setStudentCnic(String studentCnic) {
        Student.studentCnic = studentCnic;
    }

    public static String getStudentPhoneNo() {
        return studentPhoneNo;
    }

    public static void setStudentPhoneNo(String studentPhoneNo) {
        Student.studentPhoneNo = studentPhoneNo;
    }

    public static String getStudentLatLng() {
        return studentLatLng;
    }

    public static void setStudentLatLng(String studentLatLng) {
        Student.studentLatLng = studentLatLng;
    }

    public static String getRouteId() {
        return routeId;
    }

    public static void setRouteId(String routeId) {
        Student.routeId = routeId;
    }
}
