package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.AddStopsInRoute;
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



public class AddRouteBackend {
    private Context context;
    private AddStopsInRoute addStopsInRoute;


    public AddRouteBackend(Context context) {
        this.context = context;
        this.addStopsInRoute = (AddStopsInRoute) context;

    }


    public void addRoute(){

        addStopsInRoute.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/addRouteAdmin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            addStopsInRoute.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {


                                Toast.makeText(AddRouteBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                AddRouteBackend.this.addStopsInRoute.onBackPressed();



                            }else{

                                Toast.makeText(AddRouteBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        addStopsInRoute.stopLoading();

                        Toast.makeText( AddRouteBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("routeNo", AddRouteBackend.this.addStopsInRoute.routeNo);
                params.put("routeTitle", AddRouteBackend.this.addStopsInRoute.routeTitle);
                params.put("routeEndpointLatLng", AddRouteBackend.this.addStopsInRoute.routeEndpointLatLng);
                params.put("routeStopsJson", AddRouteBackend.this.addStopsInRoute.routeStopsJson);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);

    }







}
