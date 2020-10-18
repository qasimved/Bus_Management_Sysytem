package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.ViewSelectedRouteStudent;
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


public class GetStudentedRegisteredRouteInfo {
        private Context context;
        private ViewSelectedRouteStudent viewSelectedRouteStudent;

        public GetStudentedRegisteredRouteInfo(Context context) {
            this.context = context;
            this.viewSelectedRouteStudent = (ViewSelectedRouteStudent) context;
        }

        public void viewRoute(){
            viewSelectedRouteStudent.startLoading();
            RequestQueue requestQueue = Volley.newRequestQueue(this.context);
            String url = "https://universirybusmanagement.000webhostapp.com/Apis/getStudentRegisteredRouteInfo.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRes = new JSONObject(response);
                                viewSelectedRouteStudent.stopLoading();
                                if(jsonRes.getString("status").equals("success")) {
                                    String routeNo = jsonRes.getString("routeNo");
                                    String routeTitle = jsonRes.getString("routeTitle");
                                    String studentStopLatLng = jsonRes.getString("studentStopLatLng");
                                    String routeStopsJson = jsonRes.getString("routeStopsJson");
                                    String routeEndpointLatLng = jsonRes.getString("routeEndpointLatLng");


                                    GetStudentedRegisteredRouteInfo.this.viewSelectedRouteStudent.setupSpinner(routeNo,routeTitle,studentStopLatLng,routeEndpointLatLng,routeStopsJson);





                                }else{
                                    Toast.makeText(GetStudentedRegisteredRouteInfo.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            viewSelectedRouteStudent.stopLoading();
                            Toast.makeText( GetStudentedRegisteredRouteInfo.this.context,"Server Error",Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(GetStudentedRegisteredRouteInfo.this.context);
                    params.put("studentId", sharedPrefManager.getStringVar("studentId"));
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
            requestQueue.add(stringRequest);
        }
}