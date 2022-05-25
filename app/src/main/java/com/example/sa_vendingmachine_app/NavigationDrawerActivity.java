package com.example.sa_vendingmachine_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sa_vendingmachine_app.Model.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.SQLExecuteTypeEnum;
import com.example.sa_vendingmachine_app.Model.VendingMachineDAL;
import com.example.sa_vendingmachine_app.databinding.ActivityNavigationDrawerBinding;
import com.example.sa_vendingmachine_app.databinding.VendingMachineInfoBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NavigationDrawerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "Debugger ";

    // Google Map
    private GoogleMap map;
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;

    private final LatLng defaultLocation = new LatLng(23.694377, 120.5347449);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationProviderClient;

    // Last Data
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // SideBar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // UI Binding
    private ActivityNavigationDrawerBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get last data.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // UI Binding
        UI = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(UI.getRoot());

        Places.initialize(getApplicationContext(), "AIzaSyA1QHyXbqjLUehGJhYcUQ8am0r2LZVMrBA");
        placesClient = Places.createClient(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // init Navigation Drawer
        initNavigationDrawer();

        // Google Map init
        initGoogleMap();
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

        // Toolbar init
        Toolbar toolbar = UI.toolbar;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Listener
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

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private void getLocationPermission() {
        List<String> permissionList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)    // GPS
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        else
            locationPermissionGranted = true;

        if (!permissionList.isEmpty())
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void initVendingMachineMaker() {
        String sql = "SELECT name,ST_X(location),ST_Y(location),state FROM vending_machine.vending_machine;";

        ExecuteSQL executeSQL = new ExecuteSQL();
        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        ResultSet rs = executeSQL.getResultSet();
        try {
            while (rs.next()) {
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(
                        rs.getDouble("ST_X(location)"), rs.getDouble("ST_Y(location)")
                );
                markerOptions.title(rs.getString("name"));
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vending_machine2));
                markerOptions.snippet(rs.getString("state"));
                map.addMarker(markerOptions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setPadding(0, 130, 0, 0);

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        initVendingMachineMaker();

        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                VendingMachineInfoBinding ui = VendingMachineInfoBinding.inflate(getLayoutInflater());
                ui.vendingNameTextView.setText(marker.getTitle());
                ui.vendingStateTextView.setText(marker.getSnippet());

                if (Objects.equals(marker.getSnippet(), "維修中"))
                    ui.vendingStateTextView.setBackground(ContextCompat.getDrawable(NavigationDrawerActivity.this, R.drawable.red_background));

                return ui.getRoot();
            }
        });

        this.map.setOnInfoWindowClickListener(marker -> {
            ArrayList<String> data = new ArrayList<>();
            data.add(marker.getTitle());
            data.add(marker.getSnippet());

            if (Objects.equals(marker.getSnippet(), "維修中")) {
                Toast.makeText(this, "This Vending Machine is in maintenance.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(this, VendingMachine.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                if (checkLocationPermission())
                    return;

                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }

        if (checkLocationPermission())
            return;

        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}