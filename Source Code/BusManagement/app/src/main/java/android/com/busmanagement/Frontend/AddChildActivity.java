package android.com.busmanagement.Frontend;

import android.app.ProgressDialog;
import android.com.busmanagement.Backend.AddChildBackend;
import android.com.busmanagement.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddChildActivity extends AppCompatActivity {
    public TextInputLayout email,password;
    public FancyButton addChildBtn;
    public String emailStr,passwordStr;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setupProgressDialog();
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        addChildBtn = (FancyButton) findViewById(R.id.addChildBtn);
        addChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailStr = email.getEditText().getText().toString();
                passwordStr = password.getEditText().getText().toString();

                if(emailStr.trim().isEmpty()||passwordStr.trim().isEmpty()){

                    Toast.makeText(AddChildActivity.this,"Fill the fields first",Toast.LENGTH_LONG).show();
                }
                else{
                    AddChildBackend addChildBackend = new AddChildBackend(AddChildActivity.this);
                    addChildBackend.addChild();
                }
            }
        });
    }



    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Registering your child");
        progressDialog.setMessage("Loading.........");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
    }


    public void startLoading(){
        progressDialog.show();
    }


    public void stopLoading(){
        progressDialog.dismiss();
    }


}
