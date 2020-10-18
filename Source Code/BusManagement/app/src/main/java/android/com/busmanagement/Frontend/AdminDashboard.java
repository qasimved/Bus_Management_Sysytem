package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.com.busmanagement.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener{
    ImageView view_routes,add_route,addDriver,add_driver,view_feedback,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        view_routes=(ImageView)findViewById(R.id.view_routes_admin_btn);
        add_route=(ImageView)findViewById(R.id.add_route_admin_btn);
        addDriver=(ImageView)findViewById(R.id.addDriver);
        add_driver=(ImageView)findViewById(R.id.add_drive_admin_btn);
        logout=(ImageView)findViewById(R.id.logout_admin_btn);
        view_feedback=(ImageView)findViewById(R.id.view_feedback_btn);
        view_routes.setOnClickListener(this);
        add_route.setOnClickListener(this);
        addDriver.setOnClickListener(this);
        logout.setOnClickListener(this);
        add_driver.setOnClickListener(this);
        view_feedback.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.view_routes_admin_btn)
        {
            startActivity(new Intent(AdminDashboard.this,ViewRoutesInfoAdmin.class));
        }
        else if(view.getId()==R.id.add_route_admin_btn)
        {
            startActivity(new Intent(AdminDashboard.this,AddRouteDetails.class));
        }
        else if(view.getId()==R.id.addDriver)
        {
            startActivity(new Intent(AdminDashboard.this,AddDriverActivity.class));
        }
        if(view.getId()==R.id.add_drive_admin_btn)
        {
            startActivity(new Intent(AdminDashboard.this,AddDriverToRoute.class));

        }
        if(view.getId()==R.id.view_feedback_btn)
        {
            startActivity(new Intent(AdminDashboard.this,ViewFeedBack.class));
        }
        if(view.getId()==R.id.logout_admin_btn)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Really Logout?")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(AdminDashboard.this,LoginActivity.class));
                            finish();
                        }
                    }).create().show();
        }

    }
}
