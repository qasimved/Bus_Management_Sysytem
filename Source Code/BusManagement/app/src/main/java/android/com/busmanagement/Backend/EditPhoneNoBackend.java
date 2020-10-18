package android.com.busmanagement.Backend;

import android.com.busmanagement.Frontend.EditMobileActivity;
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



public class EditPhoneNoBackend {
    private Context context;
    private EditMobileActivity editMobileActivity;

    public EditPhoneNoBackend(Context context) {
        this.context = context;
        this.editMobileActivity = (EditMobileActivity) context;
    }


    public void editNumber(){

        editMobileActivity.startLoading();

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = "https://universirybusmanagement.000webhostapp.com/Apis/editPhoneNo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonRes = new JSONObject(response);
                            editMobileActivity.stopLoading();
                            if(jsonRes.getString("status").equals("success")) {


                                Toast.makeText(EditPhoneNoBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();

                                EditPhoneNoBackend.this.editMobileActivity.onBackPressed();



                            }else{

                                Toast.makeText(EditPhoneNoBackend.this.context,jsonRes.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        editMobileActivity.stopLoading();

                        Toast.makeText( EditPhoneNoBackend.this.context,"Server Error",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", EditPhoneNoBackend.this.editMobileActivity.userId);
                params.put("phoneNo", EditPhoneNoBackend.this.editMobileActivity.updated_phone_no);
                params.put("role", EditPhoneNoBackend.this.editMobileActivity.role);
                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1, 1));
        requestQueue.add(stringRequest);





    }



}
