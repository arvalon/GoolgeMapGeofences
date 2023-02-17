package com.example.admin.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;


public class NavigationActivity
        extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView longitude;
    private TextView latitude;


    private GoogleApiClient googleApiClient;
    private static int REQUEST_FINE_LOCATION = 45;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("happy","onCreate");
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_navigation);

        //longitude = (TextView) findViewById(R.id.longitude);
        //latitude = (TextView) findViewById(R.id.latitude);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume() {
        Log.d("happy","onResume");
        super.onResume();
        if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        Log.d("happy","onPause");
        super.onPause();
        if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("happy","onConnected");
        requestLocationUpdates();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("happy","onRequestPermissionsResult");
        if (requestCode == REQUEST_FINE_LOCATION
                && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (googleApiClient.isConnected())
                requestLocationUpdates();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    void requestLocationUpdates() {
        Log.d("happy","requestLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    REQUEST_FINE_LOCATION
            );
        } else {
            /*
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    new LocationRequest()
                            .setInterval(3000)
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                    this
            );
            */
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

        Log.d("happy", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("happy", "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("happy","onLocationChanged");
        latitude.setText(""+location.getLatitude());
        longitude.setText(""+location.getLatitude());
        Log.d("happy","Lat: "+latitude+"Lnd: "+longitude);
    }
}
