package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.com.busmanagement.Backend.AllRoutesListModel;
import android.com.busmanagement.Backend.GettAllRoutesListForFeedBack;
import android.com.busmanagement.Backend.SendFeedbackBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.NetUtils;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddFeedBack extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private ProgressDialog progressDialog,progressDialog2;
    public ArrayList<AllRoutesListModel> listArrList;
    public String routeId,feedbackText;
    private FancyButton sendFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed_back);
        setupProgressDialog();
        spinner = (Spinner) findViewById(R.id.spinner);
        getALLRoutes();
        sendFeedBack = (FancyButton) findViewById(R.id.seendFeedback);
        sendFeedBack.setOnClickListener(this);
    }

    private void getALLRoutes() {
        if(NetUtils.isNetConnected(this)){
            GettAllRoutesListForFeedBack gettAllRoutesListForFeedBack = new GettAllRoutesListForFeedBack(this);
            gettAllRoutesListForFeedBack.viewRoute();
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
                getALLRoutes();
            }
        });
        dialog.show();
    }



    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Getting all routes list");
        progressDialog.setMessage("Loading.........");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setIndeterminate(false);



        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setTitle("Sending Feedback");
        progressDialog2.setMessage("Loading.........");
        progressDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#96120a")));
        progressDialog2.setCanceledOnTouchOutside(false);

        progressDialog2.setIndeterminate(false);
    }


    public void startLoading(){
        progressDialog.show();
    }


    public void stopLoading(){
        progressDialog.dismiss();
    }

    public void startLoading2(){
        progressDialog.show();
    }


    public void stopLoading2(){
        progressDialog.dismiss();
    }


    public void setupSpinner(){
        List<String> routes = new ArrayList<String>();

        for (AllRoutesListModel allRoutesListModel: listArrList) {

            routes.add(allRoutesListModel.getRouteTitle());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, routes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    public void onClick(View view)
    {
        routeId = String.valueOf(listArrList.get(spinner.getSelectedItemPosition()).getRouteId());
        feedbackText=((EditText)findViewById(R.id.feedBack)).getText().toString();
            if(feedbackText.trim().equals(""))
            {
                Toast.makeText(this, "Enter feedBack first", Toast.LENGTH_LONG).show();
            }
            else{
                SendFeedbackBackend sendFeedbackBackend = new SendFeedbackBackend(this);
                sendFeedbackBackend.sendFeedBack();

            }
    }
}
