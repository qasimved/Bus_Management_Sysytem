package android.com.busmanagement.Frontend;

import android.app.ProgressDialog;
import android.com.busmanagement.Backend.SigninBackend;
import android.com.busmanagement.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public String role;
    public Button login_btn;
    public Spinner role_spinner;
    public String email,password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        role_spinner = (Spinner) findViewById(R.id.role_spinner);
        TextView signup_text = (TextView) findViewById(R.id.signup_text);
        signup_text.setOnClickListener((View.OnClickListener) this);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);


        setupProgressDialog();

    }

    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Loading.........");

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setIndeterminate(false);
    }

    public void onClick(View view)
    {
        role = role_spinner.getSelectedItem().toString();
        email=((EditText)findViewById(R.id.email_login)).getText().toString();
        password=((EditText)findViewById(R.id.password_login)).getText().toString();
        if (view.getId() == R.id.login_btn)
        {
            if(email.equals(""))
            {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show();
            }
            else if(password.equals(""))
            {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
            }
            else if (role.equalsIgnoreCase("Select role"))
            {

                Toast.makeText(this, "Select Role", Toast.LENGTH_LONG).show();
            }
            else if (role.equalsIgnoreCase("student"))
            {

                SigninBackend signinBackend = new SigninBackend(this,1);
                signinBackend.login();

            }
            else if (role.equalsIgnoreCase("parent")) {

                SigninBackend signinBackend = new SigninBackend(this,2);
                signinBackend.login();

            }
            else if (role.equalsIgnoreCase("driver")) {
                SigninBackend signinBackend = new SigninBackend(this,3);
                signinBackend.login();
            }
            else if (role.equalsIgnoreCase("Admin")) {


                if(email.equalsIgnoreCase("admin")&&password.equalsIgnoreCase("admin")){
                    finish();
                    startActivity(new Intent(this,AdminDashboard.class));


                }
                else {
                    Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                }


            }
        }
        else if (view.getId() == R.id.signup_text) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

        }
        else
        {
            //start login code from database here
        }

    }


    public void startLoading(){

        progressDialog.show();
    }


    public void stopLoading(){

        progressDialog.dismiss();
    }

}