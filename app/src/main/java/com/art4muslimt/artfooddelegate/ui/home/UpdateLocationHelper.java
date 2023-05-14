package com.art4muslimt.artfooddelegate.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class UpdateLocationHelper implements UpdateView {
    Context context;

    public UpdateLocationHelper(Context context) {
        this.context = context;
    }


    @Override
    public void isUpdated(boolean isUpdated, String userId, String orderId, String lat, String lng) {
        updateLocation(orderId, userId, lat, lng);
    }

    public void updateLocation(String order_id, String driver_id, String lat, String lng) {

        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.DRIVER_CHANGE_LOCATION)
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("driver_id", driver_id)
                .addBodyParameter("lat", lat)
                .addBodyParameter("lng", lng)
                .addBodyParameter("lang", "ar")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                        } else {
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        loading.cancelLoading();
                    }
                });
    }

}
