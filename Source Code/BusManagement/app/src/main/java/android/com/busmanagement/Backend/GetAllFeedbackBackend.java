package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.ViewFeedBack;
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

public class GetAllFeedbackBackend {
    private Context context;
    private ViewFeedBack viewFeedBack;

    public GetAllFeedbackBackend(Context context) {
        this.context = context;
        this.viewFeedBack = (ViewFeedBack) context;
    }

    public void viewRoute(){
        viewFeedBack.startLoading();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/getAllFeedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            viewFeedBack.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {
                                JSONArray allFeedbackArray = jsonRes.getJSONArray("Feedback");
                                if (allFeedbackArray.length() > 0) {
                                    GetAllFeedbackBackend.this.viewFeedBack.listArrList = new ArrayList<AllFeedbackListModel>();
                                    for (int i = 0; i < allFeedbackArray.length(); i++) {
                                        JSONObject allFeedbackArrayJSONObject = allFeedbackArray.getJSONObject(i);
                                        AllFeedbackListModel allFeedbackListModel = new AllFeedbackListModel();
                                        allFeedbackListModel.setFeedbackId(allFeedbackArrayJSONObject.getString("feedbackId"));
                                        allFeedbackListModel.setFeedbaackDescription(allFeedbackArrayJSONObject.getString("feedbaackDescription"));
                                        allFeedbackListModel.setParentName(allFeedbackArrayJSONObject.getString("parentName"));
                                        allFeedbackListModel.setRouteNo(allFeedbackArrayJSONObject.getString("routeNo"));
                                        GetAllFeedbackBackend.this.viewFeedBack.listArrList.add(allFeedbackListModel);
                                    }
                                    GetAllFeedbackBackend.this.viewFeedBack.updateFeedbackList();
                                }
                            }else{
                                Toast.makeText(GetAllFeedbackBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        viewFeedBack.stopLoading();
                        Toast.makeText( GetAllFeedbackBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();
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