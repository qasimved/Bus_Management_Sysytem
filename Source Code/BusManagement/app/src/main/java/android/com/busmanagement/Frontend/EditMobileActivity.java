package android.com.busmanagement.Frontend;

import android.app.ProgressDialog;
import android.com.busmanagement.Backend.EditPhoneNoBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

public class EditMobileActivity extends AppCompatActivity implements View.OnClickListener{
    public String updated_phone_no,role,userId;
    private ProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mobile_no);
        FancyButton update_phone=(FancyButton)findViewById(R.id.update_phone_btn);
        update_phone.setOnClickListener(this);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        if(sharedPrefManager.getStringVar("loginAs").equalsIgnoreCase("student")){

            userId = sharedPrefManager.getStringVar("studentId");
            role = "1";

        }
        else if(sharedPrefManager.getStringVar("loginAs").equalsIgnoreCase("parent")){

            userId = sharedPrefManager.getStringVar("parentId");
            role = "2";
        }
        else{

            userId = sharedPrefManager.getStringVar("driverId");
            role = "3";
        }


        setupProgressDialog();
    }


    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Changing Passwords");
        progressDialog.setMessage("Loading.........");

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setIndeterminate(false);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.update_phone_btn)
        {
            updated_phone_no=((EditText)findViewById(R.id.updated_mobile_no)).getText().toString();
            if(updated_phone_no.equals(""))
            {
                Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("03[0-9]{2}(?!1234567)(?!1111111)(?!7654321)[0-9]{7}").matcher(updated_phone_no).find() )
            {
                Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            }
            else if(updated_phone_no.length()>11)
            {
                Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            }
            else
            {
                EditPhoneNoBackend editPhoneNoBackend = new EditPhoneNoBackend(this);
                editPhoneNoBackend.editNumber();
                
            }
        }


    }


    public void startLoading(){

        progressDialog.show();
    }


    public void stopLoading(){

        progressDialog.dismiss();
    }




}
