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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendFeedbackBackend {
    private Context context;
    private AddFeedBack addFeedBack;


    public SendFeedbackBackend(Context context) {
        this.context = context;
        this.addFeedBack = (AddFeedBack) context;

    }


    public void sendFeedBack(){

        addFeedBack.startLoading2();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/sendFeedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRes = new JSONObject(response);
                            addFeedBack.stopLoading2();
                            if(jsonRes.getString("status").equals("success")) {
                                Toast.makeText(SendFeedbackBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(SendFeedbackBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addFeedBack.stopLoading2();
                        Toast.makeText( SendFeedbackBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Parent parent = new Parent(SendFeedbackBackend.this.context);
                Map<String, String> params = new HashMap<String, String>();
                params.put("routeId", SendFeedbackBackend.this.addFeedBack.routeId);
                params.put("feedback", SendFeedbackBackend.this.addFeedBack.feedbackText);
                params.put("parentId", parent.getParentId());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}
