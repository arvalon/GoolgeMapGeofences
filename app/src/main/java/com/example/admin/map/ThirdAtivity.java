package com.example.admin.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ThirdAtivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<Status>
{

    private GoogleApiClient googleApiClient;
    private static int REQUEST_FINE_LOCATION = 45;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private int mMapType = GoogleMap.MAP_TYPE_NORMAL;
    private LatLng latLng;
    private float radius = 200;

    private static boolean noLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("happy", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void findMoscow(View view) {
        if(mMap != null)
        {
            LatLng moscow = new LatLng(
                    55.7558,
                    37.6173
            );

            mMap.addMarker(
                    new MarkerOptions()
                    .position(moscow)
                    .title("Moscow")
                    .snippet("Population: around 14 mils")
            );
            mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            moscow,
                            15
                    )
            );
        }
    }

    public void findMoscow() {
        if(mMap != null)
        {
            LatLng moscow = new LatLng(
                    55.7558,
                    37.6173
            );

            mMap.addMarker(
                    new MarkerOptions()
                            .position(moscow)
                            .title("Moscow")
                            .snippet("Population: around 14 mils")
            );
            mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            moscow,
                            15
                    )
            );
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("happy", "onMapReady");
        mMap = googleMap;
        if(mMap != null)
        {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    addFence(latLng, 200);
                }
            });
        }
    }

    private void addFence(LatLng latLng, int i) {
        if(googleApiClient.isConnected())
        {
            this.latLng = latLng;
            if(
                ActivityCompat.checkSelfPermission(
                        ThirdAtivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )    != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }

            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    getGeofenceRequest(latLng, 200),
                    getPendingIntent()
            ).setResultCallback(ThirdAtivity.this);
        }
    }

    private GeofencingRequest getGeofenceRequest(LatLng latLng, int i) {
        Geofence fence = new Geofence.Builder()
                .setRequestId("123")
                .setCircularRegion(
                        latLng.latitude,
                        latLng.longitude,
                        i
                )
                .setExpirationDuration(60000)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT
                )
                .build();

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(fence);

        return  builder.build();
    }

    private PendingIntent getPendingIntent() {
        Intent i = new Intent(this, MyReceiver.class);
        return PendingIntent.getBroadcast(
                this,
                0,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    public void changeMapType(View view) {
        if(mMap != null)
        {
            mMapType =
                    mMapType == GoogleMap.MAP_TYPE_NORMAL ?
                            GoogleMap.MAP_TYPE_SATELLITE :
                            GoogleMap.MAP_TYPE_NORMAL;

            mMap.setMapType(mMapType);
        }
    }

    public void findMelbourne(View view) {
        if(mMap != null)
        {
            LatLng melbourne = new LatLng(
                    -37.47,
                    144.58
            );

            MarkerOptions m = new MarkerOptions();
            Parcelable p = m;



            mMap.addMarker(
                    new MarkerOptions()
                            .position(melbourne)
                            .title("Melbourne")
            );
            mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            melbourne,
                            15
                    )
            );
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("happy", "onConnected");
        if (!noLocations){
            requestLocationUpdates();
        }else findMoscow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("happy", "onRequestPermissionsResult");
        if (requestCode == REQUEST_FINE_LOCATION
                && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (googleApiClient.isConnected())
                requestLocationUpdates();
        }else{
            Log.d("happy", "Права не дали");
            noLocations=true;
        }
        return;
    }

    @TargetApi(Build.VERSION_CODES.M)
    void requestLocationUpdates(){
        Log.d("happy", "requestLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            Log.d("happy", "Права есть, работаем");
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, new LocationRequest().setInterval(3000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),this);
        }else{
            Log.d("happy", "Запрашиваем права");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_FINE_LOCATION);
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
        if(mMap != null)
        {
            LatLng point = new LatLng(
                    location.getLatitude(),
                    location.getLongitude()
            );

            mMap.animateCamera(
                    CameraUpdateFactory.newLatLng(point)
            );

            mMap.addCircle(
                    new CircleOptions()
                    .radius(5)
                    .center(point)
                    .fillColor(0x550000ff)
                    .strokeColor(0x550000ff)
            );
        }
    }

    @Override
    protected void onResume() {
        Log.d("happy", "onResume");
        super.onResume();
        if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        Log.d("happy", "onPause");
        super.onPause();
        if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.d("happy", "onResult");
        if (status.isSuccess())
        {
            if(mMap != null)
            {
                mMap.addCircle(
                        new CircleOptions()
                        .center(latLng)
                        .radius(radius)
                        .fillColor(0x5500ff00)
                        .strokeColor(0x5500ff00)
                );
            }
        }
        else
        {
            Log.d("happy", "onResult " + status.toString());
        }
    }
}
