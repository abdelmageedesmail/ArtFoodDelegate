package com.art4muslimt.artfooddelegate.ui.orders;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.home.AlarmReciever;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.home.HomePresenter;
import com.art4muslimt.artfooddelegate.ui.home.UpdateUserLocationService;
import com.art4muslimt.artfooddelegate.ui.home.UpdateView;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.HelperClass;
import com.art4muslimt.artfooddelegate.utils.ItemClick;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersFragment extends Fragment implements OrderView {

    @BindView(R.id.btnPastOrders)
    Button btnPastOrders;
    @BindView(R.id.btnCurrentOrders)
    Button btnCurrentOrders;
    @BindView(R.id.rvOrders)
    RecyclerView rvOrders;
    @BindView(R.id.noData)
    View noData;
    @Inject
    OrderPresenter presenter;
    PrefrencesStorage storage;
    GPSTracker mGps;
    String orderId, userId;
    double lat, lng;
    private String type = "current";
    private OrderAdapter orderAdapter;
    private List<OrderResponse.DataEntity> list = new ArrayList<>();
    HomeActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        ((ArtFood) getActivity().getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        mGps = new GPSTracker(getActivity());
        storage = new PrefrencesStorage(getActivity());
        userId = storage.getId();
        lat = mGps.getLatitude();
        lng = mGps.getLongitude();
        presenter.getAcceptedOrders(getActivity(), storage.getId(), "" + mGps.getLatitude(), "" + mGps.getLongitude());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    private void startBroadCast() {
        if (list.size() > 0) {
            Intent i = new Intent(getActivity(), UpdateUserLocationService.class);
            i.putExtra("orderId", list.get(0).getId());
            getActivity().startService(i);
        }

//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.putExtra("orderId", list.get(0).getId());
//        broadcastIntent.setClass(getActivity(), AlarmReciever.class);
    }

    private void stopService() {
//        Intent i = new Intent(getActivity(), UpdateUserLocationService.class);
//        i.putExtra("orderId", "0");
//        getActivity().stopService(i);
////        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.putExtra("orderId", list.get(0).getId());
//        broadcastIntent.setClass(getActivity(), AlarmReciever.class);
    }


    @Override
    public void onDestroyView() {
        if (list.size() > 0) {
//            Intent broadcastIntent = new Intent();
//            broadcastIntent.setAction("restartservice");
//            broadcastIntent.setClass(getActivity(), AlarmReciever.class);
//            getActivity().sendBroadcast(broadcastIntent);
        }

        super.onDestroyView();
    }

    @OnClick({R.id.btnPastOrders, R.id.btnCurrentOrders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPastOrders:
                type = "past";
                presenter.getFinishedOrder(getActivity(), storage.getId(), "" + mGps.getLatitude(), "" + mGps.getLongitude());
                btnCurrentOrders.setBackgroundColor(Color.parseColor("#562815"));
                btnPastOrders.setBackgroundColor(Color.parseColor("#EFB94D"));
                break;
            case R.id.btnCurrentOrders:
                type = "current";
                presenter.getAcceptedOrders(getActivity(), storage.getId(), "" + mGps.getLatitude(), "" + mGps.getLongitude());
                btnPastOrders.setBackgroundColor(Color.parseColor("#562815"));
                btnCurrentOrders.setBackgroundColor(Color.parseColor("#EFB94D"));
                break;
        }
    }

    @Override
    public void getAcceptedOrder(List<OrderResponse.DataEntity> data) {
        list = data;
        if (list.size() > 0) {
            orderId = list.get(0).getId();
            startBroadCast();
            noData.setVisibility(View.GONE);
            orderAdapter = new OrderAdapter(getActivity(), list, type);
            rvOrders.setAdapter(orderAdapter);
            rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
            orderAdapter.setItemClick(new ItemClick() {
                @Override
                public void onClick(int pos) {

                }

                @Override
                public void onClickStatus(int pos, String status) {
                    presenter.orderDone(getActivity(), list.get(pos).getId(), storage.getId());

                }
            });
        } else {
            stopService();
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void changeStatus(boolean b, String status) {
        if (b) {
            if (status.equals("done")) {
                Toast.makeText(getActivity(), getString(R.string.orderDoneSuccessfully), Toast.LENGTH_SHORT).show();
                HelperClass.replaceFragmentWithoutBack(getActivity().getSupportFragmentManager().beginTransaction(), new OrdersFragment());
            }
        }
    }

    @Override
    public void getFinishedOrder(List<OrderResponse.DataEntity> data) {

    }

//    @Override
//    public void isUpdated(boolean isUpdated) {
//        if (isUpdated) {
//            Log.e("updated", "updatedd");
////            presenter.updateLocation(getActivity(), orderId, userId, "" + lat, "" + lng);
//            Toast.makeText(activity, "" + userId + ".." + orderId + "..." + lat, Toast.LENGTH_SHORT).show();
//        }
//    }
}