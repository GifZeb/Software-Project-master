package com.example.n01204206.milestone;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    String mylocation;

    private static  LatLng toronto = new LatLng(43.653908, -79.384293);
    private static final LatLng brampton= new LatLng(43.683334, -79.766670);
    private static final LatLng etobicoke = new LatLng(43.620495, -79.513199);
    private static final LatLng mississuga = new LatLng(43.595310, -79.640579);

    private Marker mbrampton;
    private Marker mtoronto;
    private Marker metobicoke;
    private Marker mmississauga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        /* flag to indicate google maps is loaded */
        double a=0,b=0;

        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {


            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            System.out.println("Provider "+provider);
            Location location = locationManager.getLastKnownLocation(provider);




            if(location!=null){

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                toronto = new LatLng(latitude, longitude);
                mylocation = "\nLatitude: "+latitude+"\nLongitude: "+longitude;
            }


        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        List<Marker> markerList = new ArrayList<>();


        mtoronto = mMap.addMarker(new MarkerOptions()
                .position(toronto)
                .title("Your Location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mtoronto.setTag(0);
        markerList.add(mtoronto);

        mbrampton = mMap.addMarker(new MarkerOptions()
                .position(brampton)
                .title("Your Location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mbrampton.setTag(0);
        markerList.add(mbrampton);

        metobicoke = mMap.addMarker(new MarkerOptions()
                .position(etobicoke)
                .title("Your Location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        metobicoke.setTag(0);
        markerList.add(metobicoke);

        mmississauga = mMap.addMarker(new MarkerOptions()
                .position(mississuga)
                .title("Your Location!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mmississauga.setTag(0);
        markerList.add(mmississauga);

        mMap.setOnMarkerClickListener(this); // this will register the clicks


        for (Marker m : markerList){
            LatLng latLng = new LatLng(m.getPosition().latitude,m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng ,10));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,2));



        }
    }


    // Add a marker in Sydney and move the camera
//        LatLng mississauga = new LatLng(43.636815, -79.618315);
//        mMap.addMarker(new MarkerOptions().position(mississauga).title("Marker on animal1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mississauga));
//    }
    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer clickedCount = (Integer)marker.getTag();
        if(clickedCount != null){


            marker.setTag(clickedCount);
            Toast.makeText(this,marker.getTitle()+" "+mylocation, Toast.LENGTH_LONG).show();
        }
        return false;
    }
}

