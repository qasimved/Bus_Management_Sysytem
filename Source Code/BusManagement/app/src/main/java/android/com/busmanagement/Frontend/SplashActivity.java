package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.com.busmanagement.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSION_ALL = 1;

    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION};

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent startActivity = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(startActivity);
                    finish();
                }
            }, 2000);
        }


    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent startActivity = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(startActivity);
                    finish();


                } else {

                    new AlertDialog.Builder(this)
                            .setTitle("Location permission denied?")
                            .setMessage("You can't use this application without granting location permission!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS, PERMISSION_ALL);
                                }
                            }).create().show();

                }
                break;

        }

    }



}