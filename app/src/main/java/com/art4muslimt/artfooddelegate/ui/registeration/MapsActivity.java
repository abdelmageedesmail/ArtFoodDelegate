package com.art4muslimt.artfooddelegate.ui.registeration;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.ivMarker)
    ImageView ivMarker;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.liBottomAddress)
    LinearLayout liBottomAddress;
    private GoogleMap mMap;
    GPSTracker mGps;
    private CameraPosition cameraPosition;
    public static double latitu, longitu;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGps = new GPSTracker(this);

    }

    private void selectLocation() {
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                final LatLng midLatLng = mMap.getCameraPosition().target;
                final BitmapDescriptor iconLocation = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker);
                latitu = midLatLng.latitude;
                longitu = midLatLng.longitude;
                tvAddress.setText(getAddress(latitu, longitu));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getAddress(double latitude, double longitude) {
        geocoder = new Geocoder(this, Locale.forLanguageTag(Utils.getLang(this)));
        String loc = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                loc = city + " , " + country;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
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
        selectLocation();
        LatLng ny = new LatLng(mGps.getLatitude(), mGps.getLongitude());
        // Add a marker in Sydney and move the camera

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
//        gMap.clear();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        cameraPosition = new CameraPosition.Builder().target(ny).zoom(11).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        onBackPressed();
    }
}