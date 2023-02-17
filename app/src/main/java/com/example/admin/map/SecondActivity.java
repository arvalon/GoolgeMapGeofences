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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class SecondActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<Status>
{

    //private GoogleApiClient googleApiClient;
    private static int REQUEST_FINE_LOCATION = 45;

    //private SupportMapFragment mapFragment;
    //private GoogleMap mMap;

    //private int mMapType = GoogleMap.MAP_TYPE_NORMAL;
    //private LatLng latLng;
    //private float radius = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("happy", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /*mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("happy", "onMapReady");
        //mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("happy", "onConnected");
        requestLocationUpdates();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("happy", "onRequestPermissionsResult");
        if (requestCode == REQUEST_FINE_LOCATION && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("happy", "onRequestPermissionsResult - get rights");
            requestLocationUpdates();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    void requestLocationUpdates() {
        Log.d("happy", "requestLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        } else {
            Log.d("happy", "requestLocationUpdates - else");
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
        Log.d("happy", "onLocationChanged");
    }

    @Override
    protected void onResume() {
        Log.d("happy", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("happy", "onPause");
        super.onPause();
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.d("happy", "onResult");
    }
}
