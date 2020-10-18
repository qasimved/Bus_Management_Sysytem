package android.com.busmanagement.Frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.com.busmanagement.Backend.UpdateLocationDriverBackend;
import android.com.busmanagement.R;
import android.com.busmanagement.Utils.SharedPrefManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverDashboard extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private  LocationManager locationManager;
    private Location location;
    private LocationCallback mLocationCallback;
    private SwitchCompat simpleSwitchPickup,simpleSwitchDrop;
    private Marker marker;
    private String routeStatusPickupDrop = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);


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

            AlertDialog.Builder dialog = new AlertDialog.Builder(DriverDashboard.this);
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


        createLocationCallback();


        simpleSwitchPickup = (SwitchCompat) findViewById(R.id.pickup); // initiate Switch

        simpleSwitchPickup.setTextOn("On");
        simpleSwitchPickup.setTextOff("Off");


        simpleSwitchDrop = (SwitchCompat) findViewById(R.id.drop); // initiate Switch

        simpleSwitchDrop.setTextOn("On");
        simpleSwitchDrop.setTextOff("Off");



        simpleSwitchPickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    simpleSwitchDrop.setChecked(false);
                    routeStatusPickupDrop = "0";
                }
                else{
                    simpleSwitchDrop.setChecked(true);
                    routeStatusPickupDrop = "1";
                }

            }
        });


        simpleSwitchDrop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    simpleSwitchPickup.setChecked(false);
                    routeStatusPickupDrop = "1";
                }
                else{
                    simpleSwitchPickup.setChecked(true);
                    routeStatusPickupDrop = "0";
                }

            }
        });



    }

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(15000);

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int result = ContextCompat.checkSelfPermission(DriverDashboard.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DriverDashboard.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                } else {

                    mFusedLocationClient.requestLocationUpdates(locationRequest,mLocationCallback , Looper.myLooper() );


                }
            } else {

                mFusedLocationClient.requestLocationUpdates(locationRequest,mLocationCallback , Looper.myLooper() );


            }

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        @Override
        public void onLocationChanged(Location location) {

            Toast.makeText(this,"Location chnaged is called",Toast.LENGTH_SHORT).show();

            if (location == null) {

                Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT);

            }

            else {





            }

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng latLng = new LatLng(33.7296538, 73.0373085);
            this.marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();

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


    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                location = locationResult.getLastLocation();


                DriverDashboard.this.marker.remove();

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    DriverDashboard.this.marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(DriverDashboard.this);

                String driverLatLng = location.getLatitude()+","+location.getLongitude();

                UpdateLocationDriverBackend updateLocationDriverBackend = new UpdateLocationDriverBackend(DriverDashboard.this);
                updateLocationDriverBackend.updateLocation(sharedPrefManager.getStringVar("driverId"),driverLatLng,routeStatusPickupDrop);



            }
        };
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Logout?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(DriverDashboard.this,LoginActivity.class));
                        finish();
                    }
                }).create().show();
    }
}
