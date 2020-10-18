package android.com.busmanagement.Frontend;

import android.com.busmanagement.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddRouteDetails extends AppCompatActivity implements View.OnClickListener{
    FancyButton next;
    EditText routeNo,routeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route_details);

        routeNo = (EditText) findViewById(R.id.route_no);
        routeTitle = (EditText) findViewById(R.id.route_title);



        next = (FancyButton) findViewById(R.id.route_dtails_next_btn);

        next.setOnClickListener(this);


    }


    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(AddRouteDetails.this,AddRoute.class);
        intent.putExtra("routeNo",routeNo.getText().toString());
        intent.putExtra("routeTitle",routeTitle.getText().toString());
        startActivity(intent);
    }
}