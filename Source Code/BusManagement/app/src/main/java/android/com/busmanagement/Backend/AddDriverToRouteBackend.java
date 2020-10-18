package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.AddDriverToRoute;
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


public class AddDriverToRouteBackend {
    private Context context;
    private AddDriverToRoute addDriverToRoute;


    public AddDriverToRouteBackend(Context context) {
        this.context = context;
        this.addDriverToRoute = (AddDriverToRoute) context;

    }


    public void addDriverToRoute(){

        addDriverToRoute.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/addDriverToRoute.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            addDriverToRoute.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {


                                Toast.makeText(AddDriverToRouteBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                AddDriverToRouteBackend.this.addDriverToRoute.onBackPressed();



                            }else{

                                Toast.makeText(AddDriverToRouteBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        addDriverToRoute.stopLoading();

                        Toast.makeText( AddDriverToRouteBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverId", AddDriverToRouteBackend.this.addDriverToRoute.driverId);
                params.put("routeId", AddDriverToRouteBackend.this.addDriverToRoute.routeId);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}

