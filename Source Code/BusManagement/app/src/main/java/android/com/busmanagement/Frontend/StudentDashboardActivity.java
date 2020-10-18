package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class StudentDashboardActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView view_routes,add_route,live_tracking,account_setting,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        view_routes=(ImageView)findViewById(R.id.view_routes_std_btn);
        add_route=(ImageView)findViewById(R.id.select_route_std_btn);
        live_tracking=(ImageView)findViewById(R.id.live_tracking_std_btn);
        account_setting=(ImageView)findViewById(R.id.account_setting_std_btn);
        logout=(ImageView)findViewById(R.id.logout_std_btn);
        view_routes.setOnClickListener(this);
        add_route.setOnClickListener(this);
        live_tracking.setOnClickListener(this);
        logout.setOnClickListener(this);
        account_setting.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.view_routes_std_btn)
        {
            startActivity(new Intent(StudentDashboardActivity.this,ViewRoutesInfoAdmin.class));

        }
        else if(view.getId()==R.id.select_route_std_btn)
        {

            SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
            if(sharedPrefManager.getStringVar("routeId").equals("")){
                startActivity(new Intent(StudentDashboardActivity.this,SelectRouteStudent.class));
            }
            else{

                startActivity(new Intent(StudentDashboardActivity.this,ViewSelectedRouteStudent.class));

            }

        }
        else if(view.getId()==R.id.live_tracking_std_btn)
        {
            startActivity(new Intent(StudentDashboardActivity.this,LiveTracking.class));

        }
        else if(view.getId()==R.id.account_setting_std_btn)
        {

            startActivity(new Intent(StudentDashboardActivity.this,AccountSettingActivity.class));
        }
        else if(view.getId()==R.id.logout_std_btn)
        {

            new AlertDialog.Builder(this)
                    .setTitle("Really Logout?")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(StudentDashboardActivity.this,LoginActivity.class));
                            finish();
                        }
                    }).create().show();
        }

    }
}
