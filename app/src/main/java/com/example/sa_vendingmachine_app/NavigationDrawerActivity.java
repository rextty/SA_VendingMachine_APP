package com.example.sa_vendingmachine_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.databinding.ActivityNavigationDrawerBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawerActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private static final String TAG = "Debugger ";

    // Google Map
    private GoogleMap map;
    private MarkerOptions locationMarker;

    //GPS
    private Criteria criteria;
    private Location myLocation;
    private LocationManager myLocationManager;

    private double schoolLatitude;
    private double schoolLongitude;

    private String bestProvider;
    private double lastKnowLatitude;
    private double lastKnowLongitude;

    private boolean hasLastLatitude = false;
    private boolean hasLastLongitude = false;

    // SideBar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    // UI Binding
    private ActivityNavigationDrawerBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UI Binding
        UI = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        schoolLatitude = 23.694377;
        schoolLongitude = 120.5347449;

        // init Navigation Drawer
        initNavigationDrawer();

        // Google Map init
        initGoogleMap();

        locationInitialize();
    }

    private void locationInitialize() {
        myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setCostAllowed(true); // 付費
        criteria.setSpeedRequired(true); // 移動速度
        criteria.setBearingRequired(true); // 方位
        criteria.setAltitudeRequired(true); // 高度
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 精準度
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 耗電

        if (myLocationManager != null) {
            if (checkLocationPermission())
                return;

            bestProvider = myLocationManager.getBestProvider(criteria, true);
            myLocation = myLocationManager.getLastKnownLocation(bestProvider); //TODO:LocationManager.NETWORK_PROVIDER

            Log.e(TAG, "locationInitialize-BestProvider: " + bestProvider);
        }
    }

    private void initGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initNavigationDrawer() {
        // SideBar
        drawerLayout = UI.drawerLayout;
        navigationView = UI.navigationView;
        toolbar = UI.toolbar;

        // Toolbar init
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            int id = item.getItemId();
            if (id == R.id.action_home) {
                Toast.makeText(NavigationDrawerActivity.this, "首頁", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_help) {
                Toast.makeText(NavigationDrawerActivity.this, "使用說明", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    // 取得緯度
    public double getLatitude() {
        if (myLocation == null) {
            if (hasLastLatitude)
                return lastKnowLatitude;

            return schoolLatitude;
        }

        hasLastLatitude = true;
        lastKnowLatitude = myLocation.getLatitude();
        return lastKnowLatitude;
    }

    // 取得經度
    public double getLongitude() {
        if (myLocation == null) {
            if (hasLastLongitude)
                return lastKnowLongitude;

            return schoolLongitude;
        }

        hasLastLongitude = true;
        lastKnowLongitude = myLocation.getLongitude();
        return lastKnowLongitude;
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        float zoomLevel = 16.0f;
        LatLng sydney = new LatLng(schoolLatitude,schoolLongitude);

        locationMarker = new MarkerOptions().position(sydney).title("Marker in Sydney");

        map.addMarker(locationMarker);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;

        float zoomLevel = 16.0f;
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

        locationMarker = new MarkerOptions().position(sydney).title("Marker in Sydney");

        map.clear();
        map.addMarker(locationMarker);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

        Log.d(TAG + "位置改變 ", "緯度:" + location.getLatitude() + " 經度:" + location.getLongitude());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermission())
            return;

        myLocationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}