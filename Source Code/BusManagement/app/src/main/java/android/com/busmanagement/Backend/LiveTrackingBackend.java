package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.LiveTracking;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;

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

public class LiveTrackingBackend {
    private Context context;
    private LiveTracking liveTracking;

    public LiveTrackingBackend(Context context) {
        this.context = context;
        this.liveTracking = (LiveTracking) context;
    }

    public void viewRoute(){
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/liveTracking.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);

                            if(jsonRes.getString("status").equals("success")) {

                                if(jsonRes.getString("routeStatus").equals("0")){
                                    LiveTrackingBackend.this.liveTracking.status.setText("Pickup");
                                }
                                else{
                                    LiveTrackingBackend.this.liveTracking.status.setText("Drop");
                                }
                                LiveTrackingBackend.this.liveTracking.updateBuslocation(jsonRes.getString("driverLatLng"));
                            }else{
//                                Toast.makeText(LiveTrackingBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText( LiveTrackingBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(LiveTrackingBackend.this.context);
                params.put("studentId", sharedPrefManager.getStringVar("studentId"));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,0, 0));
        requestQueue.add(stringRequest);
    }
}
