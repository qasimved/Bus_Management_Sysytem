package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.AddFeedBack;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GettAllRoutesListForFeedBack {
    private Context context;
    private AddFeedBack addFeedBack;

    public GettAllRoutesListForFeedBack(Context context) {
        this.context = context;
        this.addFeedBack = (AddFeedBack) context;
    }

    public void viewRoute(){
        addFeedBack.startLoading();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/getRoutesDetails.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            addFeedBack.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {
                                JSONArray allRoutesArray = jsonRes.getJSONArray("Routes");
                                if (allRoutesArray.length() > 0) {
                                    GettAllRoutesListForFeedBack.this.addFeedBack.listArrList = new ArrayList<AllRoutesListModel>();
                                    for (int i = 0; i < allRoutesArray.length(); i++) {
                                        JSONObject allRoutesArrayJSONObject = allRoutesArray.getJSONObject(i);
                                        AllRoutesListModel allRoutesListModel = new AllRoutesListModel();
                                        allRoutesListModel.setRouteId(allRoutesArrayJSONObject.getString("routeId"));
                                        allRoutesListModel.setRouteTitle(allRoutesArrayJSONObject.getString("routeTitle"));
                                        allRoutesListModel.setRouteNo(allRoutesArrayJSONObject.getString("routeNo"));
                                        allRoutesListModel.setRouteEndpointLatLng(allRoutesArrayJSONObject.getString("routeEndpointLatLng"));
                                        allRoutesListModel.setRouteStopsJson(allRoutesArrayJSONObject.getString("routeStopsJson"));
                                        GettAllRoutesListForFeedBack.this.addFeedBack.listArrList.add(allRoutesListModel);
                                    }
                                    GettAllRoutesListForFeedBack.this.addFeedBack.setupSpinner();
                                }
                            }else{
                                Toast.makeText(GettAllRoutesListForFeedBack.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addFeedBack.stopLoading();
                        Toast.makeText( GettAllRoutesListForFeedBack.this.context,"Server Error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}