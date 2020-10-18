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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class GetUnassignedRoutesAndDrivers {
    private Context context;
    private AddDriverToRoute addDriverToRoute;

    public GetUnassignedRoutesAndDrivers(Context context) {
        this.context = context;
        this.addDriverToRoute = (AddDriverToRoute) context;
    }

    public void viewRoute(){
        addDriverToRoute.startLoading();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/getUnassignedRoutesAndDrivers.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            addDriverToRoute.stopLoading();
                            if (jsonRes.getString("status").equals("success")) {
                                JSONArray allRoutesArray = jsonRes.getJSONArray("Routes");
                                GetUnassignedRoutesAndDrivers.this.addDriverToRoute.listArrList = new ArrayList<AllRoutesListModel>();
                                if (allRoutesArray.length() > 0) {

                                    for (int i = 0; i < allRoutesArray.length(); i++) {
                                        JSONObject allRoutesArrayJSONObject = allRoutesArray.getJSONObject(i);
                                        AllRoutesListModel allRoutesListModel = new AllRoutesListModel();
                                        allRoutesListModel.setRouteId(allRoutesArrayJSONObject.getString("routeId"));
                                        allRoutesListModel.setRouteTitle(allRoutesArrayJSONObject.getString("routeTitle"));
                                        allRoutesListModel.setRouteNo(allRoutesArrayJSONObject.getString("routeNo"));
                                        allRoutesListModel.setRouteEndpointLatLng(allRoutesArrayJSONObject.getString("routeEndpointLatLng"));
                                        allRoutesListModel.setRouteStopsJson(allRoutesArrayJSONObject.getString("routeStopsJson"));
                                        GetUnassignedRoutesAndDrivers.this.addDriverToRoute.listArrList.add(allRoutesListModel);
                                    }
//                                    GetUnassignedRoutesAndDrivers.this.addDriverToRoute.setupSpinner();
                                }
                            } else {
                                Toast.makeText(GetUnassignedRoutesAndDrivers.this.context, jsonRes.getString("message"), Toast.LENGTH_LONG).show();
                            }

                            JSONArray allDriversArray = jsonRes.getJSONArray("Drivers");
                            GetUnassignedRoutesAndDrivers.this.addDriverToRoute.listArrListDriver = new ArrayList<AllDriversListModel>();
                            if (allDriversArray.length() > 0) {

                                for (int i = 0; i < allDriversArray.length(); i++) {
                                    JSONObject allRoutesArrayJSONObject = allDriversArray.getJSONObject(i);
                                    AllDriversListModel allDriversListModel = new AllDriversListModel();
                                    allDriversListModel.setDriverId(allRoutesArrayJSONObject.getString("driverId"));
                                    allDriversListModel.setDriverName(allRoutesArrayJSONObject.getString("driverName"));
                                    GetUnassignedRoutesAndDrivers.this.addDriverToRoute.listArrListDriver.add(allDriversListModel);
                                }
                                GetUnassignedRoutesAndDrivers.this.addDriverToRoute.setupSpinner();
                            }
                        else{
                            Toast.makeText(GetUnassignedRoutesAndDrivers.this.context, jsonRes.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText( GetUnassignedRoutesAndDrivers.this.context,"Server Error",Toast.LENGTH_LONG).show();
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