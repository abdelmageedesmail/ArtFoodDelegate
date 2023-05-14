package com.art4muslimt.artfooddelegate.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.HelperClass;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends Fragment implements OnMapReadyCallback, HomeView {

    @BindView(R.id.map_view)
    MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap gMap;
    GPSTracker mGps;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnDone)
    Button btnDone;
    private Bundle mapViewBundle;
    private String currentDate;
    private String fromTime, toTime, vehicleId;
    @Inject
    HomePresenter presenter;
    PrefrencesStorage storage;
    private String toTimeHours;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mGps = new GPSTracker(getActivity());
        storage = new PrefrencesStorage(getActivity());
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        mapView.setClickable(false);
        mapView.onResume();
        mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        currentDate = df.format(new Date());

        fromTime = getArguments().getString("fromTime");
        toTime = getArguments().getString("toTime");
        vehicleId = getArguments().getString("vehicleId");
        toTimeHours = getArguments().getString("toTimeHours");
        tvAddress.setText(getAddress(mGps.getLatitude(), mGps.getLongitude()));
        ((ArtFood) getActivity().getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.forLanguageTag(Utils.getLang(getActivity())));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng ny = new LatLng(mGps.getLatitude(), mGps.getLongitude());
        gMap.addMarker(new MarkerOptions().position(ny).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        gMap.getUiSettings().setMapToolbarEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(8).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        presenter.setDriverStatus(getActivity(), storage.getId(), fromTime, toTime, "0", "" + mGps.getLatitude(), "" + mGps.getLongitude(), tvAddress.getText().toString(), vehicleId);


    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {

    }

    @Override
    public void isUpdated(boolean b) {
        if (b) {
            storage.storeKey("currentDate", currentDate);
            storage.storeKey("lastTime", toTimeHours);
            HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrderMapFragment());
        }
    }

    @Override
    public void getWaitingOrders(List<OrderResponse.DataEntity> data) {

    }

    @Override
    public void changeStatus(boolean b, String accept) {

    }

}
