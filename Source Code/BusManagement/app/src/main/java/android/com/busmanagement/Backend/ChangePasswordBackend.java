package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.ChangePasswordActivity;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;
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




public class ChangePasswordBackend {
    private Context context;
    private ChangePasswordActivity changePasswordActivity;
    private SharedPrefManager sharedPrefManager;

    public ChangePasswordBackend(Context context) {
        this.context = context;
        this.changePasswordActivity = (ChangePasswordActivity) context;
        sharedPrefManager = SharedPrefManager.getInstance(this.context);
    }


    public void changePassword(){

        changePasswordActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/changePassword.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            changePasswordActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {

                                if(ChangePasswordBackend.this.changePasswordActivity.userType.equals("1")){

                                    sharedPrefManager.setStringVar("studentPassword",ChangePasswordBackend.this.changePasswordActivity.new_password);

                                    ChangePasswordBackend.this.changePasswordActivity.getSharedPrefData();


                                }
                                else if(ChangePasswordBackend.this.changePasswordActivity.userType.equals("2")){

                                    sharedPrefManager.setStringVar("parentPassword",ChangePasswordBackend.this.changePasswordActivity.new_password);

                                    ChangePasswordBackend.this.changePasswordActivity.getSharedPrefData();
                                }
                                else{

                                    sharedPrefManager.setStringVar("driverPassword",ChangePasswordBackend.this.changePasswordActivity.new_password);

                                    ChangePasswordBackend.this.changePasswordActivity.getSharedPrefData();
                                }


                                Toast.makeText(ChangePasswordBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                ChangePasswordBackend.this.changePasswordActivity.onBackPressed();



                            }else{

                                Toast.makeText(ChangePasswordBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        changePasswordActivity.stopLoading();

                        Toast.makeText( ChangePasswordBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", ChangePasswordBackend.this.changePasswordActivity.userId);
                params.put("password", ChangePasswordBackend.this.changePasswordActivity.new_password);
                params.put("role", ChangePasswordBackend.this.changePasswordActivity.userType);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);





    }



}
