package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.com.busmanagement.Backend.Parent;
import android.com.busmanagement.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ParentDashboardActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView view_routes,add_child,live_tracking,account_setting,feedback,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        view_routes=(ImageView)findViewById(R.id.view_routes_parent_btn);
        add_child=(ImageView)findViewById(R.id.add_child_btn);
        live_tracking=(ImageView)findViewById(R.id.live_tracking_parent_btn);
        feedback=(ImageView)findViewById(R.id.feedback_btn);
        account_setting=(ImageView)findViewById(R.id.account_setting_parent_btn);
        logout=(ImageView)findViewById(R.id.logout_parent_btn);
        view_routes.setOnClickListener(this);
        add_child.setOnClickListener(this);
        live_tracking.setOnClickListener(this);
        feedback.setOnClickListener(this);
        logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.view_routes_parent_btn)
        {
            startActivity(new Intent(ParentDashboardActivity.this,ViewRoutesInfoAdmin.class));
        }
        else if(view.getId()==R.id.add_child_btn)
        {
            Parent parent = new Parent(this);
            if(Parent.getStudenId().equals("0")){
                startActivity(new Intent(ParentDashboardActivity.this,AddChildActivity.class));
            }
            else{
                startActivity(new Intent(ParentDashboardActivity.this,ViewAddedChildActivity.class));
            }
        }
        else if(view.getId()==R.id.live_tracking_parent_btn)
        {
            startActivity(new Intent(ParentDashboardActivity.this,LiveTracking.class));
        }
        else if(view.getId()==R.id.account_setting_parent_btn)
        {
           startActivity(new Intent(ParentDashboardActivity.this,AccountSettingActivity.class));

        }
        else if(view.getId()==R.id.logout_parent_btn)
        {

            new AlertDialog.Builder(this)
                    .setTitle("Really Logout?")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(ParentDashboardActivity.this,LoginActivity.class));
                            finish();
                        }
                    }).create().show();

        }
        else if(view.getId()==R.id.feedback_btn)
        {
            startActivity(new Intent(ParentDashboardActivity.this,AddFeedBack.class));
        }
        else
        {
//            start inserting values in data base from here
        }

    }
}
