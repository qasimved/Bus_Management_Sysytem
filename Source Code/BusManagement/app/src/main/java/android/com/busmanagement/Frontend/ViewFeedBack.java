package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.com.busmanagement.Backend.AllFeedbackListModel;
import android.com.busmanagement.Backend.FeedListAdapter;
import android.com.busmanagement.Backend.GetAllFeedbackBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.NetUtils;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewFeedBack extends AppCompatActivity {
    public ArrayList<AllFeedbackListModel> listArrList;
    private ProgressDialog progressDialog;
    private ListView feedbackListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed_back);
        setupProgressDialog();
        feedbackListview = (ListView) findViewById(R.id.feedbackListview);
        getALLFeedback();
    }

    private void getALLFeedback() {
        if(NetUtils.isNetConnected(this)){
            GetAllFeedbackBackend getAllFeedbackBackend = new GetAllFeedbackBackend(this);
            getAllFeedbackBackend.viewRoute();
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
                getALLFeedback();
            }
        });
        dialog.show();
    }



    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Getting all feddback");
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


    public void updateFeedbackList(){


        if(listArrList.size()<1){
            feedbackListview.setVisibility(View.GONE);
        }else {
            FeedListAdapter feedListAdapter = new FeedListAdapter(this, listArrList);
            this.feedbackListview.setAdapter(feedListAdapter);
            feedListAdapter.notifyDataSetChanged();
        }
    }

}
