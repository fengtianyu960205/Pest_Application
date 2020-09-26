package com.example.pest_application.UI.showAll;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pest_application.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity_pest extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int idnum;
    private MapPest_viewMoel mapPest_viewMoel;
    private ArrayList<String[]> longlatilist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_pest);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        idnum =intent.getIntExtra("Idnum",0);
        Log.d("sign_in", "jsonloction: " + idnum);
        mapPest_viewMoel = new ViewModelProvider(this).get(MapPest_viewMoel.class);
        mapPest_viewMoel.GetPestLocaTa(idnum);



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

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
       //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mapPest_viewMoel.getPestLoca().observe(this, new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                longlatilist = arrayList;
                for(int i = 0;i< arrayList.size();i++){
                    String lngstr = longlatilist.get(i)[0];
                    String latstr = longlatilist.get(i)[1];
                    String location = longlatilist.get(i)[2];
                    Double lng = Double.parseDouble(lngstr);
                    Double lat = Double.parseDouble(latstr);
                    Log.d("sign_in", "latitude: " + lat);
                    Log.d("sign_in", "longtitude: " + lng);
                    LatLng latLng = new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title(location));
                            //.snippet("Latitude: " + lat + ", Longitude: " + lng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });

    }
}
