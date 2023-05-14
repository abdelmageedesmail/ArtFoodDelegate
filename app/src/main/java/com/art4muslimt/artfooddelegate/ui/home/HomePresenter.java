package com.art4muslimt.artfooddelegate.ui.home;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

public class HomePresenter {

    HomeView view;

    public void setView(HomeView view) {
        this.view = view;
    }

    public void getVicheles(Context context) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(Urls.GET_VEHICLES)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            VechiliesResponse vechiliesResponse = new Gson().fromJson(response.toString(), new TypeToken<VechiliesResponse>() {
                            }.getType());
                            view.getVechilies(vechiliesResponse.getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }


    public void setDriverStatus(Context context, String id_user, String hourfrom, String hourto, String active, String lat, String lng,
                                String address, String typevehicle) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.UPDATE_DRIVER_STATUS)
                .addBodyParameter("id_user", id_user)
                .addBodyParameter("hourfrom", hourfrom)
                .addBodyParameter("hourto", hourto)
                .addBodyParameter("active", active)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .addBodyParameter("address", address)
                .addBodyParameter("typevehicle", typevehicle)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.isUpdated(true);
                        } else {
                            view.isUpdated(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }


    public void getCurrentOrders(Context context, String user_id, String lat, String lng) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_NEW_ORDER)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            Log.e("response", "" + response);
                            OrderResponse orderResponse = new Gson().fromJson(response.toString(), new TypeToken<OrderResponse>() {
                            }.getType());
                            view.getWaitingOrders(orderResponse.getData());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    public void acceptOrder(Context context, String order_id, String driver_id) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.ACCEPT_ORDER)
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
                            view.changeStatus(true, "accept");
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


    public void refuseOrder(Context context, String order_id, String driver_id) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.REFUSE_ORDER)
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
                            view.changeStatus(true, "refuse");
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
