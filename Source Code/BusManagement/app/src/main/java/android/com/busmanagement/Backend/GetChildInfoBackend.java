package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.ViewAddedChildActivity;
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

public class GetChildInfoBackend {
    private Context context;
    private ViewAddedChildActivity viewAddedChildActivity;

    public GetChildInfoBackend(Context context) {
        this.context = context;
        this.viewAddedChildActivity = (ViewAddedChildActivity) context;
    }

    public void getChildInfo(){
        viewAddedChildActivity.startLoading();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/getChildInfo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            viewAddedChildActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {
                                viewAddedChildActivity.name.setText(jsonRes.getString("studentName"));
                                viewAddedChildActivity.regNo.setText(jsonRes.getString("studentRegNo"));
                                viewAddedChildActivity.email.setText(jsonRes.getString("studentEmail"));
                                viewAddedChildActivity.cnic.setText(jsonRes.getString("studentCnic"));
                                viewAddedChildActivity.phoneNo.setText(jsonRes.getString("studentPhoneNo"));

                            }else{
                                Toast.makeText(GetChildInfoBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        viewAddedChildActivity.stopLoading();
                        Toast.makeText( GetChildInfoBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(GetChildInfoBackend.this.context);
                params.put("parentId", sharedPrefManager.getStringVar("parentId"));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}