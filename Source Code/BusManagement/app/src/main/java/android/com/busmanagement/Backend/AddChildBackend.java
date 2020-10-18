package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.AddChildActivity;
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

public class AddChildBackend {
    private Context context;
    private AddChildActivity addChildActivity;


    public AddChildBackend(Context context) {
        this.context = context;
        this.addChildActivity = (AddChildActivity) context;

    }


    public void addChild(){

        addChildActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/addChildToParent.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            addChildActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {


                                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(AddChildBackend.this.context);
                                sharedPrefManager.setStringVar("studenId", jsonRes.getString("studentId"));

                                Toast.makeText(AddChildBackend.this.context,"Student successfully added!",Toast.LENGTH_LONG).show();

                                AddChildBackend.this.addChildActivity.onBackPressed();



                            }else{

                                Toast.makeText(AddChildBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        addChildActivity.stopLoading();

                        Toast.makeText( AddChildBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("childEmail", AddChildBackend.this.addChildActivity.emailStr);
                params.put("childPassword", AddChildBackend.this.addChildActivity.passwordStr);
                Parent parent = new Parent(AddChildBackend.this.context);
                params.put("parentId",parent.getParentId());
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);
    }
}
