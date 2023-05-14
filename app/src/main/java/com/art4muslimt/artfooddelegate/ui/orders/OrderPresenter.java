package com.art4muslimt.artfooddelegate.ui.orders;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class OrderPresenter {

    OrderView view;

    public void setView(OrderView view) {
        this.view = view;
    }

    public void getAcceptedOrders(Context context, String user_id, String lat, String lng) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_ACCEPTED_ORDER)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        Log.e("response", "" + response);
                        OrderResponse orderResponse = new Gson().fromJson(response.toString(), new TypeToken<OrderResponse>() {
                        }.getType());
                        view.getAcceptedOrder(orderResponse.getData());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    public void getFinishedOrder(Context context, String user_id, String lat, String lng) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_FINISHED_ORDER)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        Log.e("response", "" + response);
                        OrderResponse orderResponse = new Gson().fromJson(response.toString(), new TypeToken<OrderResponse>() {
                        }.getType());
                        view.getAcceptedOrder(orderResponse.getData());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    public void orderDone(Context context, String order_id, String driver_id) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.ORDER_DONE)
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("driver_id", driver_id)
                .addBodyParameter("lang", Utils.getLang(context))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.changeStatus(true, "done");
                        } else {
                            view.changeStatus(false, "");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

    public void updateLocation(Context context, String order_id, String driver_id, String lat, String lng) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.DRIVER_CHANGE_LOCATION)
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("driver_id", driver_id)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .addBodyParameter("lang", Utils.getLang(context))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.changeStatus(true, "locationUpdated");
                        } else {
                            view.changeStatus(false, "");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

}
