package android.com.busmanagement.Frontend;

import android.app.ProgressDialog;
import android.com.busmanagement.Backend.ChangePasswordBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    public String current_password,new_password,confirm_password,savedPassword,userId,userType;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        FancyButton update_password=(FancyButton)findViewById(R.id.update_password_btn);
        update_password.setOnClickListener(this);


        getSharedPrefData();

        setupProgressDialog();



    }


    public void getSharedPrefData(){
        sharedPrefManager = SharedPrefManager.getInstance(this);

        if(sharedPrefManager.getStringVar("loginAs").equalsIgnoreCase("student")){
            savedPassword = sharedPrefManager.getStringVar("studentPassword");
            userId = sharedPrefManager.getStringVar("studentId");
            userType = "1";

        }
        else if(sharedPrefManager.getStringVar("loginAs").equalsIgnoreCase("parent")){

            savedPassword = sharedPrefManager.getStringVar("parentPassword");
            userId = sharedPrefManager.getStringVar("parentId");
            userType = "2";
        }
        else{

            savedPassword = sharedPrefManager.getStringVar("driverPassword");
            userId = sharedPrefManager.getStringVar("driverId");
            userType = "3";

        }

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
        if(view.getId()==R.id.update_password_btn)
        {
            current_password=((EditText)findViewById(R.id.current_password)).getText().toString();
            new_password=((EditText)findViewById(R.id.new_password)).getText().toString();
            confirm_password=((EditText)findViewById(R.id.new_confirm_password)).getText().toString();
            if(current_password.equals(""))
            {
                Toast.makeText(this, "Enter Current Password", Toast.LENGTH_LONG).show();
            }
            else if(new_password.equals(""))
            {
                Toast.makeText(this, "Enter Current Password", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$").matcher(current_password).find())
            {
                Toast.makeText(this,"Password must contain one lower,one capital,one special character and at least one digit",Toast.LENGTH_LONG).show();
            }
            else if(confirm_password.equals(""))
            {
                Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_LONG).show();
            }
            else if(!current_password.equals(savedPassword))
            {
                Toast.makeText(this, "Invalid current password", Toast.LENGTH_LONG).show();
            }

            else if(!new_password.equals(confirm_password))
            {
                Toast.makeText(this, "Confirm password does not match with new password", Toast.LENGTH_LONG).show();
            }
            else
            {
                ChangePasswordBackend changePasswordBackend = new ChangePasswordBackend(this);
                changePasswordBackend.changePassword();

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
