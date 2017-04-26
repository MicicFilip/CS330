package com.example.micic.cs330_v04;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.*;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Micic on 26-Apr-17.
 */

public class V08Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GoogleApiClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v08);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng kg = new LatLng(44.017, 20.917);
        mMap.addMarker(new MarkerOptions().position(kg).title("Marker u Kragujevcu"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kg));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.addMarker(new MarkerOptions().position(position));
                Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> adr;
                    adr = gk.getFromLocation(position.latitude, position.longitude, 1);
                    String ad = "";
                    if (adr.size() > 0) {
                        for (int i = 0; i < adr.get(0).getMaxAddressLineIndex();
                             i++)
                            ad += adr.get(0).getAddressLine(i) + "\n";
                    }
                    Toast.makeText(getBaseContext(), ad, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            List<Address> adr;
            adr = gk.getFromLocation(kg.latitude, kg.longitude, 1);
            String ad = "";
            if (adr.size() > 0) {
                for (int i = 0; i < adr.get(0).getMaxAddressLineIndex(); i++)
                    ad += adr.get(0).getAddressLine(i) + "\n";
            }
            Toast.makeText(getBaseContext(), ad, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Nastavi da radis ovde

//    @Override
//    public void onStart() {
//        super.onStart();
//        client.connect();
//
//        Action viewAction = Action.
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v08, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.back:
                Intent back = new Intent(this, MainActivity.class);
                this.startActivity(back);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


