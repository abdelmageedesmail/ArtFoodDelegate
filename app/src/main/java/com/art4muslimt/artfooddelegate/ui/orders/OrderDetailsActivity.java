package com.art4muslimt.artfooddelegate.ui.orders;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends AppCompatActivity implements OrderView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvOrderId)
    TextView tvOrderId;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.tvFamilyName)
    TextView tvFamilyName;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvFamilyAddress)
    TextView tvFamilyAddress;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvClientName)
    TextView tvClientName;
    @BindView(R.id.clientAddress)
    TextView clientAddress;
    @BindView(R.id.tvClientDistance)
    TextView tvClientDistance;
    @BindView(R.id.tvDeliveryCost)
    TextView tvDeliveryCost;
    @BindView(R.id.tvPaymentMethod)
    TextView tvPaymentMethod;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tvAmountWallet)
    TextView tvAmountWallet;
    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tvMealPrice)
    TextView tvMealPrice;
    @BindView(R.id.btnOrderDone)
    Button btnOrderDone;
    @Inject
    OrderPresenter presenter;
    private OrderResponse.DataEntity model;
    PrefrencesStorage storage;
    GPSTracker mGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<OrderResponse.DataEntity>() {
        }.getType());
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        mGps = new GPSTracker(this);
        tvOrderId.setText("# " + model.getId());
        tvFamilyName.setText(model.getTradename());
        tvFamilyAddress.setText(getAddress(Double.parseDouble(model.getTrade_lat()), Double.parseDouble(model.getTrade_lng())));
        String format = String.format("%.2f", model.getDistance_driver_trader());

        tvDistance.setText("" + format);
        tvClientName.setText(model.getClientname());
        clientAddress.setText(getAddress(Double.parseDouble(model.getClient_lat()), Double.parseDouble(model.getClient_lng())));
        String format1 = String.format("%.2f", model.getDistance_client_driver());

        if (getIntent().getExtras().getString("type").equals("current")) {
            btnOrderDone.setVisibility(View.VISIBLE);
        } else {
            btnOrderDone.setVisibility(View.GONE);
        }
        if (model.getDeliverydate().isEmpty()) {
            tvDate.setText("" + model.getDate().split(" ")[0]);

        } else {
            tvDate.setText(model.getDeliverydate());
//            holder.tvOrderDate.setText("" + list.get(position).getDeliverydate() + " " + context.getString(R.string.from) + list.get(position).getHourfrom() + " " + context.getString(R.string.to) + list.get(position).getHourto());
        }

        tvClientDistance.setText("" + format1);
        tvDeliveryCost.setText(model.getShipping() + " " + getString(R.string.currency));
        if (model.getPaymentmethod().equals("0")) {
            tvPaymentMethod.setText(getString(R.string.cash));
        } else if (model.getPaymentmethod().equals("1")) {
            tvPaymentMethod.setText(getString(R.string.electronicPayment));
        } else {
            tvPaymentMethod.setText(getString(R.string.wallet));
        }

        double v = Double.parseDouble(model.getTotalprise()) + Double.parseDouble(model.getShipping());

        tvMealPrice.setText("" + model.getTotalprise() + " " + getString(R.string.currency));
        tvTotalPrice.setText("" + v + " " + getString(R.string.currency));

        if (Utils.getLang(this).equals("ar")) {
            tvOrderStatus.setText(model.getStatus_name());
        } else {
            tvOrderStatus.setText(model.getStatus_nameen());
        }


        tvAmountWallet.setText(model.getTotalprise());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.forLanguageTag(Utils.getLang(this)));
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

    @OnClick({R.id.ivBack, R.id.btnCall, R.id.btnOrderDone, R.id.btnFamilyLocation, R.id.btnClientLocation, R.id.btnCallFamily})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnCall:
                Utils.callPhone(this, model.getClient_mobile());
                break;
            case R.id.btnOrderDone:
                presenter.orderDone(this, model.getId(), storage.getId());
                break;
            case R.id.btnFamilyLocation:
                openDirections("" + mGps.getLatitude(), "" + mGps.getLongitude(), model.getTrade_lat(), model.getTrade_lng());
                break;
            case R.id.btnClientLocation:
                openDirections("" + mGps.getLatitude(), "" + mGps.getLongitude(), model.getClient_lat(), model.getClient_lng());
                break;
            case R.id.btnCallFamily:
                Utils.callPhone(this, model.getTrade_mobile());
                break;
        }
    }


    private void openDirections(String targetLat, String targetLng, String destLat, String destLng) {
        String uri = "http://maps.google.com/maps?saddr=" + targetLat + "," + targetLng + "&daddr=" + destLat + "," + destLng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void getAcceptedOrder(List<OrderResponse.DataEntity> data) {

    }

    @Override
    public void changeStatus(boolean b, String status) {
        if (b) {
            if (status.equals("done")) {
                Toast.makeText(this, getString(R.string.orderDoneSuccessfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        }
    }

    @Override
    public void getFinishedOrder(List<OrderResponse.DataEntity> data) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class).putExtra("from", "details"));
        finish();
    }
}