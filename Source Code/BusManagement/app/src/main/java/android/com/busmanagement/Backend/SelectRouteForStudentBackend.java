package android.com.busmanagement.Backend;


import android.com.busmanagement.Frontend.SelectRouteStudent;
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

public class SelectRouteForStudentBackend  {
    private Context context;
    private SelectRouteStudent selectRouteStudent;


    public SelectRouteForStudentBackend(Context context) {
        this.context = context;
        this.selectRouteStudent = (SelectRouteStudent) context;

    }


    public void saveRoute(){

        selectRouteStudent.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/selectEditRouteForStudent.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            selectRouteStudent.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {

                                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(SelectRouteForStudentBackend.this.context);
                                sharedPrefManager.setStringVar("routeId",SelectRouteForStudentBackend.this.selectRouteStudent.routeId);
                                sharedPrefManager.setStringVar("studentLatLng",SelectRouteForStudentBackend.this.selectRouteStudent.stopLatLng);


                                Toast.makeText(SelectRouteForStudentBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                SelectRouteForStudentBackend.this.selectRouteStudent.onBackPressed();



                            }else{

                                Toast.makeText(SelectRouteForStudentBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        selectRouteStudent.stopLoading();

                        Toast.makeText( SelectRouteForStudentBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(SelectRouteForStudentBackend.this.context);
                params.put("studentId", sharedPrefManager.getStringVar("studentId"));
                params.put("routeId", SelectRouteForStudentBackend.this.selectRouteStudent.routeId);
                params.put("studentStopLatLng", SelectRouteForStudentBackend.this.selectRouteStudent.stopLatLng);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);

    }







}