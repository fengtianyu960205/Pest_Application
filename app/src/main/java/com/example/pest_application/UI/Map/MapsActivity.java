package com.example.pest_application.UI.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pest_application.R;
import com.example.pest_application.UI.showAll.MapPest_viewMoel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private  GeofencingHelper geofencingHelper;
    private  float GeofencingRadius = 200;
    private GetAllPestLocationViewMOdel getAllPestLocationViewMOdel;
    private ArrayList<String[]> longlatilist = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;
    private String state,idd;
    private LatLng latLng ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
        getAllPestLocationViewMOdel = new ViewModelProvider(this).get(GetAllPestLocationViewMOdel.class);
        geofencingHelper = new GeofencingHelper(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4));
        enableUserLocation();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);

    }

    public void enableUserLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                approvePermission();
            }
            else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
                    ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1001);
                }
                else{
                    ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1001);
                }
            }

        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
                if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    approvePermission();
                }
                else{
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
                        ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1001);
                    }
                    else{
                        ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1001);
                    }
                }


            }
            else{
                state = "nostate";
                getAllPestLocationViewMOdel.GetPestLocaTa(state);
                getpestAlllocation();

            }
        }
        if(requestCode == 1001){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Geofence permit ", Toast.LENGTH_SHORT).show();
                approvePermission();
            }
            //else if(grantResults.length >0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            //    Toast.makeText(this, "Geofence permit ", Toast.LENGTH_SHORT).show();
            //    approvePermission();
            //}
            else{
                Toast.makeText(this, "you must choose allow all the time then the geo fence can work ", Toast.LENGTH_SHORT).show();
                state = "nostate";
                getAllPestLocationViewMOdel.GetPestLocaTa(state);
                getpestAlllocation();
            }
        }
    }

    public void getpestAlllocation(){
        getAllPestLocationViewMOdel.getPestLoca().observe(this, new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                longlatilist = arrayList;
                for(int i = 0;i< arrayList.size();i++){
                    String lngstr = longlatilist.get(i)[4];
                    String latstr = longlatilist.get(i)[3];
                    String location = longlatilist.get(i)[2];
                    String name = longlatilist.get(i)[0];
                    String category = longlatilist.get(i)[1];
                    Double lng = Double.parseDouble(lngstr);
                    Double lat = Double.parseDouble(latstr);
                    //Log.d("sign_in", "latitude: " + lat);
                    // Log.d("sign_in", "longtitude: " + lng);
                    latLng = new LatLng(lat,lng);
                    if (category.equals("Invasive species")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .title(name)
                                .snippet("Category: "+category+ " "+"City: "+location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                    else if (category.equals("Pest")){
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                .title(name)
                                .snippet("Category: "+category+ " "+"City: "+location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                    else{
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                .title(name)
                                .snippet("Category: "+category+ " "+"City: "+location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }
                }

            }
        });
    }
    public void getpestlocation(){
        getAllPestLocationViewMOdel.getPestLoca().observe(this, new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                longlatilist = arrayList;
                for(int i = 0;i< arrayList.size();i++){
                    String lngstr = longlatilist.get(i)[4];
                    String latstr = longlatilist.get(i)[3];
                    String location = longlatilist.get(i)[2];
                    String name = longlatilist.get(i)[0];
                    String category = longlatilist.get(i)[1];
                    Double lng = Double.parseDouble(lngstr);
                    Double lat = Double.parseDouble(latstr);
                    //Log.d("sign_in", "latitude: " + lat);
                    // Log.d("sign_in", "longtitude: " + lng);
                    latLng = new LatLng(lat,lng);
                    Integer ii = i;
                     idd = ii.toString();
                    addmarker(latLng,category,name,location);
                    addGeofencing(idd,latLng,GeofencingRadius);
                    addcircle(latLng,GeofencingRadius);





                }

            }
        });
    }

    public void approvePermission(){
        getLocation();
    }

    private void getLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String[] pestLocation = addresses.get(0).getAddressLine(0).split(",");
                        String[] cityState = pestLocation[1].split(" ");
                        state =  cityState[cityState.length-2];
                        getAllPestLocationViewMOdel.GetPestLocaTa(state);
                        getpestlocation();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //addressLocation,stateLocation,cityLocation;

                }
            }
        });
    }

    private void addGeofencing(String id,LatLng latLng1, float radius){
        //Geofence geofence = geofencingHelper.getGeofence("1", latLng1,radius,Geofence.GEOFENCE_TRANSITION_ENTER);
        Geofence geofence = geofencingHelper.getGeofence(id, latLng1,radius,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_DWELL|Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofencingHelper.geofencingRequest(geofence);
        PendingIntent pendingIntent = geofencingHelper.getPendingIntent();
        geofencingClient.addGeofences(geofencingRequest,pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "hghghgjjh: " + "aaaaaaaa");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofencingHelper.getErroeString(e);
                    }
                });

    }

    private void addcircle(LatLng latLng1, float radius){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng1);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255,255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }

    public void addmarker(LatLng latLng1,String strCategory,String strname,String strLocation){
        if (strCategory.equals("Invasive species")) {
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title(strname)
                    .snippet("Category: "+strCategory+ " "+"City: "+strLocation));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        else if (strCategory.equals("Pest")){
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(strname)
                    .snippet("Category: "+strCategory+ " "+"City: "+strLocation));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        else{
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title(strname)
                    .snippet("Category: "+strCategory+ " "+"City: "+strLocation));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }
}
