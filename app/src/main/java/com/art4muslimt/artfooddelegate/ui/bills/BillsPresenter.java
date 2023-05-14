package com.art4muslimt.artfooddelegate.ui.bills;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.BillsModelResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class BillsPresenter {

    BillsView view;

    public void setView(BillsView view) {
        this.view = view;
    }

    public void getBills(Context context, String userId) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_TRANSFERED)
                .addBodyParameter("user_id", userId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            BillsModelResponse billsModelResponse = new Gson().fromJson(response.toString(), new TypeToken<BillsModelResponse>() {
                            }.getType());
                            view.getTransferBalance(billsModelResponse.getData(), billsModelResponse.getBalance());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

    public void addRequest(Context context, String userId, String price) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.ADD_CHARGE_REQUEST)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("price", price)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.requestSent(true);
                        } else {
                            view.requestSent(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

}
