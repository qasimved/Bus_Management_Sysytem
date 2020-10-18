package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.com.busmanagement.Backend.AllRoutesListModel;
import android.com.busmanagement.Backend.GetAllRoutesForStudentStopSelection;
import android.com.busmanagement.Backend.SelectRouteForStudentBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.NetUtils;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectRouteStudent extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {
    private ProgressDialog progressDialog;
    public ArrayList<AllRoutesListModel> listArrList;
    public Spinner spinner,stopSpinner;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private Location location;
    private Marker markerSelectedStop;
    private TextView routeNo,routeDescription;
    private int checkIndex;
    private boolean first = true;
    public String stopLatLng, routeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route_for_student);

        setupProgressDialog();


        spinner = (Spinner) findViewById(R.id.spinner);

        stopSpinner = (Spinner) findViewById(R.id.stopSpinner);

        routeNo = (TextView) findViewById(R.id.routeNo);
        routeDescription = (TextView) findViewById(R.id.routeTitle);





        if (googleServicesAvailable()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(SelectRouteStudent.this);
            dialog.setTitle("Improve location accurancy?");
            dialog.setMessage("This app wants to change your device setting:");
            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 1);
                    //get gps
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                }
            });
            dialog.show();
        }






    }

    private void getALLRoutes() {

        if(NetUtils.isNetConnected(this)){
            GetAllRoutesForStudentStopSelection getAllRoutesForStudentStopSelection = new GetAllRoutesForStudentStopSelection(this);
            getAllRoutesForStudentStopSelection.viewRoute();


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
        progressDialog.setTitle("Downloading All routes");
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


    public void setupSpinner(){
        List<String> routes = new ArrayList<String>();

        for (AllRoutesListModel allRoutesListModel: listArrList) {

            routes.add(allRoutesListModel.getRouteTitle());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, routes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                checkIndex = i;



                AllRoutesListModel allRoutesListModel = listArrList.get(i);

                SelectRouteStudent.this.routeNo.setText(allRoutesListModel.getRouteNo());
                SelectRouteStudent.this.routeDescription.setText(allRoutesListModel.getRouteTitle());

                SelectRouteStudent.this.routeId = allRoutesListModel.getRouteId();


                Double sourceLat = 33.6519535;
                Double sourceLng = 73.1554936;
                String[] splitedArrat = allRoutesListModel.getRouteEndpointLatLng().split(",");
                Double destinationLat = Double.valueOf(splitedArrat[0]);
                Double destinationLng = Double.valueOf(splitedArrat[1]);

                LatLng latLngSource = new LatLng(sourceLat, sourceLng);
                LatLng latLngDestination = new LatLng(destinationLat, destinationLng);


                Marker markerS = mMap.addMarker(new MarkerOptions().position(latLngSource).title("CIIT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                Marker markerD = mMap.addMarker(new MarkerOptions().position(latLngDestination).title("Route End Point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                try {
                    JSONObject allStopsJsonObject = new JSONObject(allRoutesListModel.getRouteStopsJson());

                    JSONArray allStopsJsonArray = allStopsJsonObject.getJSONArray("allStops");

                    List<String> stops = new ArrayList<String>();

                    for (int x = 0; x < allStopsJsonArray.length(); x++) {
                        JSONObject stopsJsonArrayJSONObject = allStopsJsonArray.getJSONObject(x);
                        String[] splitedArrayForStops = stopsJsonArrayJSONObject.getString("stopLatLng").split(",");
                        Double latForStops = Double.valueOf(splitedArrayForStops[0]);
                        Double lngForStops = Double.valueOf(splitedArrayForStops[1]);

                        LatLng latLng = new LatLng(latForStops, lngForStops);

                        String stopTitle = stopsJsonArrayJSONObject.getString("stopName");

                        MarkerOptions stopMarker = new MarkerOptions().position(latLng).title(stopTitle);

                        mMap.addMarker(stopMarker);



                        stops.add(stopTitle);



                    }

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SelectRouteStudent.this,
                                android.R.layout.simple_spinner_item, stops);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SelectRouteStudent.this.stopSpinner.setAdapter(dataAdapter2);



                    SelectRouteStudent.this.stopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(!first){
                                markerSelectedStop.remove();
                            }

                            first = false;


                            AllRoutesListModel allRoutesListModel = listArrList.get(SelectRouteStudent.this.checkIndex);

                            try {
                                JSONObject allStopsJsonObject = new JSONObject(allRoutesListModel.getRouteStopsJson());
                                JSONArray allStopsJsonArray = allStopsJsonObject.getJSONArray("allStops");
                                JSONObject stopsJsonArrayJSONObject = allStopsJsonArray.getJSONObject(i);
                                String[] splitedArrayForStops = stopsJsonArrayJSONObject.getString("stopLatLng").split(",");
                                Double latForStops = Double.valueOf(splitedArrayForStops[0]);
                                Double lngForStops = Double.valueOf(splitedArrayForStops[1]);

                                LatLng latLng = new LatLng(latForStops, lngForStops);

                                MarkerOptions stopMarker = new MarkerOptions().position(latLng).title("Your Selected Stop").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                                markerSelectedStop =  mMap.addMarker(stopMarker);

                                SelectRouteStudent.this.stopLatLng = stopsJsonArrayJSONObject.getString("stopLatLng");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });






                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(latLngSource);
                    builder.include(latLngDestination);
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500,500,5));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }




    public void setupSpinner2(){
        List<String> routes = new ArrayList<String>();

        for (AllRoutesListModel allRoutesListModel: listArrList) {

            routes.add(allRoutesListModel.getRouteTitle());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, routes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                checkIndex = i;



                AllRoutesListModel allRoutesListModel = listArrList.get(i);

                SelectRouteStudent.this.routeNo.setText(allRoutesListModel.getRouteNo());
                SelectRouteStudent.this.routeDescription.setText(allRoutesListModel.getRouteTitle());

                SelectRouteStudent.this.routeId = allRoutesListModel.getRouteId();


                Double sourceLat = 33.6519535;
                Double sourceLng = 73.1554936;
                String[] splitedArrat = allRoutesListModel.getRouteEndpointLatLng().split(",");
                Double destinationLat = Double.valueOf(splitedArrat[0]);
                Double destinationLng = Double.valueOf(splitedArrat[1]);

                LatLng latLngSource = new LatLng(sourceLat, sourceLng);
                LatLng latLngDestination = new LatLng(destinationLat, destinationLng);


                Marker markerS = mMap.addMarker(new MarkerOptions().position(latLngSource).title("CIIT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                Marker markerD = mMap.addMarker(new MarkerOptions().position(latLngDestination).title("Route End Point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                try {
                    JSONObject allStopsJsonObject = new JSONObject(allRoutesListModel.getRouteStopsJson());

                    JSONArray allStopsJsonArray = allStopsJsonObject.getJSONArray("allStops");

                    List<String> stops = new ArrayList<String>();

                    for (int x = 0; x < allStopsJsonArray.length(); x++) {
                        JSONObject stopsJsonArrayJSONObject = allStopsJsonArray.getJSONObject(x);
                        String[] splitedArrayForStops = stopsJsonArrayJSONObject.getString("stopLatLng").split(",");
                        Double latForStops = Double.valueOf(splitedArrayForStops[0]);
                        Double lngForStops = Double.valueOf(splitedArrayForStops[1]);

                        LatLng latLng = new LatLng(latForStops, lngForStops);

                        String stopTitle = stopsJsonArrayJSONObject.getString("stopName");

                        MarkerOptions stopMarker = new MarkerOptions().position(latLng).title(stopTitle);

                        mMap.addMarker(stopMarker);



                        stops.add(stopTitle);



                    }

                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SelectRouteStudent.this,
                            android.R.layout.simple_spinner_item, stops);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SelectRouteStudent.this.stopSpinner.setAdapter(dataAdapter2);



                    SelectRouteStudent.this.stopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(!first){
                                markerSelectedStop.remove();
                            }

                            first = false;


                            AllRoutesListModel allRoutesListModel = listArrList.get(SelectRouteStudent.this.checkIndex);

                            try {
                                JSONObject allStopsJsonObject = new JSONObject(allRoutesListModel.getRouteStopsJson());
                                JSONArray allStopsJsonArray = allStopsJsonObject.getJSONArray("allStops");
                                JSONObject stopsJsonArrayJSONObject = allStopsJsonArray.getJSONObject(i);
                                String[] splitedArrayForStops = stopsJsonArrayJSONObject.getString("stopLatLng").split(",");
                                Double latForStops = Double.valueOf(splitedArrayForStops[0]);
                                Double lngForStops = Double.valueOf(splitedArrayForStops[1]);

                                LatLng latLng = new LatLng(latForStops, lngForStops);

                                MarkerOptions stopMarker = new MarkerOptions().position(latLng).title("Your Selected Stop").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                                markerSelectedStop =  mMap.addMarker(stopMarker);

                                SelectRouteStudent.this.stopLatLng = stopsJsonArrayJSONObject.getString("stopLatLng");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });






                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(latLngSource);
                    builder.include(latLngDestination);
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500,500,5));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();



        getALLRoutes();



    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else if (googleApiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                    finish();

                }
                break;
        }

    }



    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(SelectRouteStudent.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getSubAdminArea();

            return add.toString();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }


    public void saveRoute(View view){
        SelectRouteForStudentBackend selectRouteForStudentBackend = new SelectRouteForStudentBackend(this);
        selectRouteForStudentBackend.saveRoute();
    }
}
