package com.art4muslimt.artfooddelegate.ui.pages;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslimt.artfooddelegate.internet.model.PagesResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class PagesPresenter {

    PagesView view;

    public void setView(PagesView view) {
        this.view = view;
    }

    public void getPages(Context context, String url) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            PagesResponse pagesResponse = new Gson().fromJson(response.toString(), new TypeToken<PagesResponse>() {
                            }.getType());
                            view.getPages(pagesResponse.getData().getText());
                        } else {
                            view.getPages("");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });

    }

}
