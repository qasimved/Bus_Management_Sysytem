package android.com.busmanagement.Frontend;

import android.com.busmanagement.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FancyButton student_signup = (FancyButton) findViewById(R.id.student_signup);
        FancyButton parent_signup = (FancyButton) findViewById(R.id.parent_signup);
        student_signup.setOnClickListener(this);
        parent_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.student_signup) {
            startActivity(new Intent(SignUpActivity.this, StudentSignUpActivity.class));

        } else if (view.getId() == R.id.parent_signup) {
            startActivity(new Intent(SignUpActivity.this, ParentSignUpActivity.class));
        }
    }
}