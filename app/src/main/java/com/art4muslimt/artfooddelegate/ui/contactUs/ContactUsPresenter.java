package com.art4muslimt.artfooddelegate.ui.contactUs;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class ContactUsPresenter {

    ContactUsView view;

    public void setView(ContactUsView view) {
        this.view = view;
    }

    public void contactUs(Context context, String name, String comment, String email, String type, String mobile) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.CONTACT_US)
                .addBodyParameter("name", name)
                .addBodyParameter("comment", comment)
                .addBodyParameter("email", email)
                .addBodyParameter("lang", Utils.getLang(context))
                .addBodyParameter("type", type)
                .addBodyParameter("mobile", mobile)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.isAdded(true);
                        } else {
                            view.isAdded(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });

    }
}
