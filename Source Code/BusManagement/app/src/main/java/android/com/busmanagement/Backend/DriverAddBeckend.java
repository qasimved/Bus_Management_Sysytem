package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.AddDriverActivity;
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



public class DriverAddBeckend {
    private Context context;
    private AddDriverActivity addDriverActivity;


    public DriverAddBeckend(Context context) {
        this.context = context;
        this.addDriverActivity = (AddDriverActivity) context;

    }


    public void addDriver(){

        addDriverActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/addDriver.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            addDriverActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {


                                Toast.makeText(DriverAddBeckend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                DriverAddBeckend.this.addDriverActivity.onBackPressed();



                            }else{

                                Toast.makeText(DriverAddBeckend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        addDriverActivity.stopLoading();

                        Toast.makeText( DriverAddBeckend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverName", DriverAddBeckend.this.addDriverActivity.name);
                params.put("driverEmail", DriverAddBeckend.this.addDriverActivity.email);
                params.put("driverPassword", DriverAddBeckend.this.addDriverActivity.password);
                params.put("driverCnic", DriverAddBeckend.this.addDriverActivity.cnic_no);
                params.put("driverPhoneNo", DriverAddBeckend.this.addDriverActivity.mobile_no);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}
