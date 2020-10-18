package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.com.busmanagement.Backend.AddRouteBackend;
import android.com.busmanagement.R;
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
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

import mehdi.sakout.fancybuttons.FancyButton;



public class AddStopsInRoute extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private Location location;
    private Marker marker;
    private FancyButton save;
    private TextView guideLabel;
    public String routeNo,routeTitle,routeEndpointLatLng,routeStopsJson;
    private ArrayList<MarkerOptions> mMarkerArray = new ArrayList<MarkerOptions>();
    private Marker markerS,markerD;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops_in_route);

        save = (FancyButton) findViewById(R.id.saveBtn);
        guideLabel = (TextView) findViewById(R.id.guideLabel);

        Intent intent = getIntent();
        routeNo = intent.getStringExtra("routeNo");
        routeTitle = intent.getStringExtra("routeTitle");
        routeEndpointLatLng = intent.getStringExtra("routeEndpointLatLng");




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

            AlertDialog.Builder dialog = new AlertDialog.Builder(AddStopsInRoute.this);
            dialog.setTitle("Improve location accurancy?");
            dialog.setMessage("This app wants to change your device setting:");
            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 1);
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                }
            });
            dialog.show();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObjectOuter = new JSONObject();
                JSONArray jsonArray = new JSONArray();



                for (MarkerOptions markeroption : mMarkerArray) {
                    try {
                        JSONObject jsonObjectInner = new JSONObject();
                        jsonObjectInner.put("stopName",getAddress(markeroption.getPosition().latitude,markeroption.getPosition().longitude));
                        jsonObjectInner.put("stopLatLng",markeroption.getPosition().latitude+","+markeroption.getPosition().longitude);
                        jsonArray.put(jsonObjectInner);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    jsonObjectOuter.put("allStops",jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                routeStopsJson = jsonObjectOuter.toString();


                AddRouteBackend addRouteBackend = new AddRouteBackend(AddStopsInRoute.this);
                addRouteBackend.addRoute();







            }
        });


        setupProgressDialog();

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

        Double sourceLat = 33.6519535;
        Double sourceLng = 73.1554936;
        String[] splitedArrat = routeEndpointLatLng.split(",");
        Double destinationLat = Double.valueOf(splitedArrat[0]);
        Double destinationLng = Double.valueOf(splitedArrat[1]);

        LatLng latLngSource = new LatLng(sourceLat, sourceLng);
        LatLng latLngDestination = new LatLng(destinationLat, destinationLng);


        this.markerS = mMap.addMarker(new MarkerOptions().position(latLngSource).title("CIIT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        this.markerD = mMap.addMarker(new MarkerOptions().position(latLngDestination).title("Route End Point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLngSource);
        builder.include(latLngDestination);
        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500,500,5));


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker

                String locationName =  AddStopsInRoute.this.getAddress(latLng.latitude,latLng.longitude);


                if(locationName == null) {
                    markerOptions.title("Unknown location");

                }
                else{
                    markerOptions.title(locationName);
                }

                // Clears the previously touched position
//                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);


                mMarkerArray.add(markerOptions);

                AddStopsInRoute.this.save.setVisibility(View.VISIBLE);




            }
        });


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
        Geocoder geocoder = new Geocoder(AddStopsInRoute.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getAdminArea();
            return add.toString();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }



    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Adding New Route");
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





}
