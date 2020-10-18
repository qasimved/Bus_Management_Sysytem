package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.DriverDashboard;
import android.com.busmanagement.Frontend.LoginActivity;
import android.com.busmanagement.Frontend.ParentDashboardActivity;
import android.com.busmanagement.Frontend.StudentDashboardActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class SigninBackend {
    private Context context;
    private LoginActivity loginActivity;
    private int role;


    public SigninBackend(Context context,int role) {
        this.context = context;
        this.role = role;
        this.loginActivity = (LoginActivity) context;
    }

    public void login(){

        loginActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/signin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonRes = new JSONObject(response);
                            loginActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {

                                Toast.makeText(SigninBackend.this.context,"Successfully LoggedIn",Toast.LENGTH_LONG).show();


                                if(SigninBackend.this.role == 1){
                                    Student student = new Student(jsonRes.getString("studentId"),jsonRes.getString("studentName"),jsonRes.getString("studentRegNo"),jsonRes.getString("studentEmail"),jsonRes.getString("studentPassword"),jsonRes.getString("studentCnic"),jsonRes.getString("studentPhoneNo"),jsonRes.getString("studentStopLatLng"),jsonRes.getString("routeId"),SigninBackend.this.context);

                                    SigninBackend.this.loginActivity.finish();

                                    loginActivity.startActivity(new Intent(loginActivity, StudentDashboardActivity.class));



                                }
                                else if(SigninBackend.this.role == 2){
                                    Parent parent = new Parent(jsonRes.getString("parentId"),jsonRes.getString("parentName"),jsonRes.getString("parentEmail"),jsonRes.getString("parentPassword"),jsonRes.getString("parentCnic"),jsonRes.getString("parentPhoneNo"),jsonRes.getString("studentId"),SigninBackend.this.context);

                                    SigninBackend.this.loginActivity.finish();

                                    loginActivity.startActivity(new Intent(loginActivity, ParentDashboardActivity.class));

                                }
                                else{
                                    Driver driver = new Driver(jsonRes.getString("driverId"),jsonRes.getString("driverName"),jsonRes.getString("driverCnic"),jsonRes.getString("driverphoneNo"),jsonRes.getString("driverEmail"),jsonRes.getString("driverPassword"),jsonRes.getString("driverLatLng"),SigninBackend.this.context);

                                    SigninBackend.this.loginActivity.finish();

                                    loginActivity.startActivity(new Intent(loginActivity, DriverDashboard.class));

                                }


                            }else{

                                Toast.makeText(SigninBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loginActivity.stopLoading();

                        Toast.makeText( SigninBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", SigninBackend.this.loginActivity.email);
                params.put("password", SigninBackend.this.loginActivity.password);
                params.put("role", String.valueOf(SigninBackend.this.role));


                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,1, 1));
        requestQueue.add(stringRequest);


    }


}
