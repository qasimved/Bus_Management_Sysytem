package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.com.busmanagement.Backend.GetChildInfoBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.NetUtils;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ViewAddedChildActivity extends AppCompatActivity {
    public TextView name,email,regNo,cnic,phoneNo;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_added_child);
        setupProgressDialog();
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        regNo = (TextView) findViewById(R.id.regNo);
        cnic = (TextView) findViewById(R.id.cnic);
        phoneNo = (TextView) findViewById(R.id.phoneNo);
        getChildInfo();
    }

    private void getChildInfo() {
        if(NetUtils.isNetConnected(this)){
            GetChildInfoBackend getChildInfoBackend = new GetChildInfoBackend(this);
            getChildInfoBackend.getChildInfo();
        }
        else{
            retryDialog();
        }
    }

    public void retryDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("No Internet!");
        dialog.setMessage("Intenet connection required to update subservices list");
        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                getChildInfo();
            }
        });
        dialog.show();
    }



    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Getting Child Info");
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

    public void changeBtnClick(View view){
        startActivity(new Intent(ViewAddedChildActivity.this,AddChildActivity.class));
    }


}
