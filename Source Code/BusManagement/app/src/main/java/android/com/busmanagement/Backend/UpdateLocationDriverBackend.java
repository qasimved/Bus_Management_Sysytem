package android.com.busmanagement.Backend;

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

import java.util.HashMap;
import java.util.Map;


public class UpdateLocationDriverBackend {
    private Context context;
    private String driverId,driverLatLng,routeStatusPickupDrop;


    public UpdateLocationDriverBackend(Context context) {
        this.context = context;
    }

    public void updateLocation(String driverId, String driverLatLng, String routeStatusPickupDrop){
        this.driverId =driverId;
        this.driverLatLng = driverLatLng;
        this.routeStatusPickupDrop= routeStatusPickupDrop;


        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/updateLocationDriver.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(UpdateLocationDriverBackend.this.context,"Location Updated",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverId", UpdateLocationDriverBackend.this.driverId);
                params.put("driverLatLng", UpdateLocationDriverBackend.this.driverLatLng);
                params.put("routeStatusPickupDrop", UpdateLocationDriverBackend.this.routeStatusPickupDrop);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);

    }
}
