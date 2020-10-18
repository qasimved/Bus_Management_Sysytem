package android.com.busmanagement.Frontend;

import android.app.ProgressDialog;
import android.com.busmanagement.Backend.StudentSignupBackend;
import android.com.busmanagement.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class StudentSignUpActivity extends AppCompatActivity implements View.OnClickListener{
   public Button sign_up_btn;
    public String reg_no,name,email,password,confirm_password,cnic_no,mobile_no;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        sign_up_btn=(Button)findViewById(R.id.sign_up_button_std);
        sign_up_btn.setOnClickListener(this);

        setupProgressDialog();
    }

    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Registering Student");
        progressDialog.setMessage("Loading.........");

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setIndeterminate(false);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.sign_up_button_std)
        {
            reg_no=((EditText)findViewById(R.id.regstd_signup)).getText().toString();
            name=((EditText)findViewById(R.id.username_std_signup)).getText().toString();
            email=((EditText)findViewById(R.id.email_std_signup)).getText().toString();
            password=((EditText)findViewById(R.id.password_std_signup)).getText().toString();
            confirm_password=((EditText)findViewById(R.id.confirm_password_std_signup)).getText().toString();
            cnic_no=((EditText)findViewById(R.id.cnic_std_signup)).getText().toString();
            mobile_no=((EditText)findViewById(R.id.mobile_std_signup)).getText().toString();
            if(reg_no.equals(""))
            {
                Toast.makeText(this, "Enter Registration Number", Toast.LENGTH_LONG).show();
            }
            else if(name.equals(""))
            {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("[A-Za-z]").matcher(name).find())
            {
                Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_LONG).show();
            }
            else if(name.length()<3)
            {
                Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_LONG).show();
            }

            else if(email.equals(""))
            {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).find())
            {
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_LONG).show();
            }
            else if(password.equals(""))
            {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$").matcher(password).find())
            {
                Toast.makeText(this,"Password must contain one lower,one capital,one special character and at least one digit",Toast.LENGTH_LONG).show();
            }
            else if(confirm_password.equals(""))
            {
                Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_LONG).show();
            }
            else if(!password.equals(confirm_password))
            {
                Toast.makeText(this, "Confirm Password does not match with password", Toast.LENGTH_LONG).show();
            }
            else if(cnic_no.equals(""))
            {
                Toast.makeText(this, "Enter CNIC No", Toast.LENGTH_LONG).show();
            }
            else if(cnic_no.length()<13)
            {
                Toast.makeText(this, "Enter valid cnic no", Toast.LENGTH_LONG).show();
            }
            else if(cnic_no.length()>13)
            {
                Toast.makeText(this, "Enter valid cnic no", Toast.LENGTH_LONG).show();
            }
            else if(mobile_no.equals(""))
            {
                Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_LONG).show();
            }
            else if(!Pattern.compile("03[0-9]{2}(?!1234567)(?!1111111)(?!7654321)[0-9]{7}").matcher(mobile_no).find() )
            {
                Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            }
            else if(mobile_no.length()>11)
            {
                Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            }
            else
            {
                StudentSignupBackend studentSignupBackend = new StudentSignupBackend(this);
                studentSignupBackend.signup();
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

