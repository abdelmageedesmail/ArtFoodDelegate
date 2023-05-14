package com.art4muslimt.artfooddelegate.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.orders.OrdersFragment;
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
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderMapFragment extends Fragment implements OnMapReadyCallback, HomeView {
    @BindView(R.id.map_view)
    MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap gMap;
    GPSTracker mGps;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.tvFamilyAddress)
    TextView tvFamilyAddress;
    @BindView(R.id.tvClientAddress)
    TextView tvClientAddress;
    private Bundle mapViewBundle;
    @Inject
    HomePresenter presenter;
    PrefrencesStorage storage;
    private String orderId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_map, container, false);
        ButterKnife.bind(this, view);
        ((ArtFood) getActivity().getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        mGps = new GPSTracker(getActivity());
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        mapView.setClickable(false);
        mapView.onResume();
        storage = new PrefrencesStorage(getActivity());
        mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        Log.e("locations", "" + mGps.getLatitude() + "," + mGps.getLongitude());
        tvAddress.setText(getAddress(mGps.getLatitude(), mGps.getLongitude()));
        presenter.getCurrentOrders(getActivity(), storage.getId(), "" + mGps.getLatitude(), "" + mGps.getLongitude());
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
        if (btnDone.getText().toString().equals(getString(R.string.acceptOrder))) {
            presenter.acceptOrder(getActivity(), orderId, storage.getId());
        } else {
            presenter.orderDone(getActivity(), orderId, storage.getId());
        }
    }

    @OnClick(R.id.btnCancel)
    public void onViewCancelClicked() {

        presenter.refuseOrder(getActivity(), orderId, storage.getId());

    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {

    }

    @Override
    public void isUpdated(boolean b) {

    }

    @Override
    public void getWaitingOrders(List<OrderResponse.DataEntity> data) {

        if (data.size() > 0) {
            orderId = data.get(0).getId();
            tvClientAddress.setText(getAddress(Double.parseDouble(data.get(0).getClient_lat()), Double.parseDouble(data.get(0).getClient_lng())));
            tvFamilyAddress.setText(getAddress(Double.parseDouble(data.get(0).getTrade_lat()), Double.parseDouble(data.get(0).getTrade_lng())));
            gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(data.get(0).getClient_lat()), Double.parseDouble(data.get(0).getClient_lng()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.client_pin)));
            gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(data.get(0).getTrade_lat()), Double.parseDouble(data.get(0).getTrade_lng()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        } else {
            HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrdersFragment());
        }

    }

    @Override
    public void changeStatus(boolean b, String status) {
        if (b) {
            if (status.equals("accept")) {
                btnDone.setText(getString(R.string.clientRecieved));
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2.0f
                );
                btnDone.setLayoutParams(param);
                btnCancel.setVisibility(View.GONE);
                HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrdersFragment());

            } else if (status.equals("refuse")) {
                Toast.makeText(getActivity(), getString(R.string.orderCanceledSuccessfully), Toast.LENGTH_SHORT).show();
                HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrdersFragment());
            } else if (status.equals("done")) {
                Toast.makeText(getActivity(), getString(R.string.orderDoneSuccessfully), Toast.LENGTH_SHORT).show();
                HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrdersFragment());
            }
        }

    }
}
