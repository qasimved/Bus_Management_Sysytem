package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.LoginActivity;
import android.com.busmanagement.Frontend.ParentSignUpActivity;
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



public class ParentSignupBackend {

    private Context context;
    private ParentSignUpActivity parentSignUpActivity;

    public ParentSignupBackend(Context context) {
        this.context = context;
        this.parentSignUpActivity = (ParentSignUpActivity) context;
    }


    public void signup(){

        parentSignUpActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/parentSignup.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            parentSignUpActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {

                                Toast.makeText(ParentSignupBackend.this.context,"Successfully Registered",Toast.LENGTH_LONG).show();

                                ParentSignupBackend.this.parentSignUpActivity.finish();

                                parentSignUpActivity.startActivity(new Intent(parentSignUpActivity, LoginActivity.class));



                            }else{

                                Toast.makeText(ParentSignupBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        parentSignUpActivity.stopLoading();

                        Toast.makeText( ParentSignupBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("parentName", ParentSignupBackend.this.parentSignUpActivity.name);
                params.put("parentEmail", ParentSignupBackend.this.parentSignUpActivity.email);
                params.put("parentPassword", ParentSignupBackend.this.parentSignUpActivity.password);
                params.put("parentCnic", ParentSignupBackend.this.parentSignUpActivity.cnic_no);
                params.put("parentPhoneNo", ParentSignupBackend.this.parentSignUpActivity.mobile_no);

                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);





    }



}
