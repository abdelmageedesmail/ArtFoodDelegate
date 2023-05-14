package com.art4muslimt.artfooddelegate.ui.orders;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.ItemClick;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    Context context;
    String type;
    List<OrderResponse.DataEntity> list;
    ItemClick itemClick;
    GPSTracker mGps;


    public OrderAdapter(@NonNull Context context, List<OrderResponse.DataEntity> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
        mGps = new GPSTracker(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orders, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (type.equals("current")) {
            holder.liCurrentOrder.setVisibility(View.VISIBLE);
        } else {
            holder.liCurrentOrder.setVisibility(View.GONE);
        }
        holder.tvFamilyName.setText(list.get(position).getTradename());
        holder.tvOrderId.setText(list.get(position).getId());
        holder.tvAddress.setText(getAddress(Double.parseDouble(list.get(position).getTrade_lat()), Double.parseDouble(list.get(position).getTrade_lng())));


//        Location locationTrader = new Location("PTrader");
//        locationTrader.setLatitude(Double.parseDouble(list.get(position).getTrade_lat()));
//        locationTrader.setLongitude(Double.parseDouble(list.get(position).getTrade_lng()));
//        float v = locationTrader.distanceTo(mGps.getLocation());
//        double v4 = Utils.meterDistanceBetweenPoints(Double.parseDouble(list.get(position).getTrade_lat()), Double.parseDouble(list.get(position).getTrade_lng()), mGps.getLatitude(), mGps.getLongitude());
//        double v2 = v4 / 1000;

        String format = String.format("%.2f", list.get(position).getDistance_driver_trader());
        holder.tvDistance.setText("" + format);

        holder.tvClientName.setText(list.get(position).getClientname());
        holder.clientAddress.setText(getAddress(Double.parseDouble(list.get(position).getClient_lat()), Double.parseDouble(list.get(position).getClient_lng())));

//        Location locationClient = new Location("PTrader");
//        locationClient.setLatitude(Double.parseDouble(list.get(position).getClient_lat()));
//        locationClient.setLongitude(Double.parseDouble(list.get(position).getClient_lng()));
//        float vClient = locationClient.distanceTo(mGps.getLocation());
//        float v3 = vClient / 1000;
//        String format1 = String.format("%.2f", v3);

        holder.tvClientDistance.setText("" + list.get(position).getDistance_client_driver());

        holder.tvDeliveryCost.setText(list.get(position).getShipping() + " " + context.getString(R.string.currency));
        if (list.get(position).getPaymentmethod().equals("0")) {
            holder.tvPaymentMethod.setText(context.getString(R.string.cash));
        } else if (list.get(position).getPaymentmethod().equals("1")) {
            holder.tvPaymentMethod.setText(context.getString(R.string.electronicPayment));
        } else {
            holder.tvPaymentMethod.setText(context.getString(R.string.wallet));
        }

        double v1 = Double.parseDouble(list.get(position).getTotalprise()) + Double.parseDouble(list.get(position).getShipping());
        holder.tvMealPrice.setText("" + list.get(position).getTotalprise() + " " + context.getString(R.string.currency));
        holder.tvTotalPrice.setText("" + v1 + " " + context.getString(R.string.currency));


//        holder.tvAmountWallet.setText(list.get(position).get());

        holder.btnOrderDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClickStatus(holder.getAdapterPosition(), "done");


                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(list.get(holder.getAdapterPosition()));
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("model", jsonStr);
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.forLanguageTag(Utils.getLang(context)));
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
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFamilyName)
        TextView tvFamilyName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
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
        @BindView(R.id.tvOrderId)
        TextView tvOrderId;
        @BindView(R.id.tvMealPrice)
        TextView tvMealPrice;
        @BindView(R.id.btnOrderDone)
        Button btnOrderDone;
        @BindView(R.id.liCurrentOrder)
        LinearLayout liCurrentOrder;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
