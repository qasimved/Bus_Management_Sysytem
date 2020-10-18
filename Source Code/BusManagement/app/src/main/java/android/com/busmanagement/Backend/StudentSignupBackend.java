package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.LoginActivity;
import android.com.busmanagement.Frontend.StudentSignUpActivity;
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


public class StudentSignupBackend {

    private Context context;
    private StudentSignUpActivity studentSignUpActivity;

    public StudentSignupBackend(Context context) {
        this.context = context;
        this.studentSignUpActivity = (StudentSignUpActivity) context;
    }


    public void signup(){

        studentSignUpActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/studentSignup.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            studentSignUpActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {

                                Toast.makeText(StudentSignupBackend.this.context,"Successfully Registered",Toast.LENGTH_LONG).show();

                                StudentSignupBackend.this.studentSignUpActivity.finish();

                                studentSignUpActivity.startActivity(new Intent(studentSignUpActivity, LoginActivity.class));
                                



                            }else{

                                Toast.makeText(StudentSignupBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        studentSignUpActivity.stopLoading();

                        Toast.makeText( StudentSignupBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("studentRegNo",StudentSignupBackend.this.studentSignUpActivity.reg_no );
                params.put("studentName", StudentSignupBackend.this.studentSignUpActivity.name);
                params.put("studentEmail", StudentSignupBackend.this.studentSignUpActivity.email);
                params.put("studentPassword", StudentSignupBackend.this.studentSignUpActivity.password);
                params.put("studentCnic", StudentSignupBackend.this.studentSignUpActivity.cnic_no);
                params.put("studentPhoneNo", StudentSignupBackend.this.studentSignUpActivity.mobile_no);

                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);





    }



}