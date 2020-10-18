package android.com.busmanagement.Frontend;

import android.com.busmanagement.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;

public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        FancyButton update_mobile_number = (FancyButton) findViewById(R.id.edit_mobile_no);
        FancyButton update_password = (FancyButton) findViewById(R.id.change_password);
        update_mobile_number.setOnClickListener(this);
        update_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.edit_mobile_no)
        {
            startActivity(new Intent(AccountSettingActivity.this,EditMobileActivity.class));
        }
        else if(view.getId()==R.id.change_password)
        {
            startActivity(new Intent(AccountSettingActivity.this,ChangePasswordActivity.class));
        }

    }
}
