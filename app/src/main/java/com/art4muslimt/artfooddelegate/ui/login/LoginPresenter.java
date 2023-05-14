package com.art4muslimt.artfooddelegate.ui.login;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.LoginResponse;
import com.art4muslimt.artfooddelegate.notifications.FCMRegistrationService;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {

    LoginView view;

    public void setView(LoginView view) {
        this.view = view;
    }

    public void login(Context context, String mobile, String password) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.LOGIN)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("password", password)
                .addBodyParameter("lang", Utils.getLang(context))
                .addBodyParameter("token", FCMRegistrationService.token)
                .addBodyParameter("type", "4")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        LoginResponse loginResponse = new Gson().fromJson(response.toString(), new TypeToken<LoginResponse>() {
                        }.getType());
                        if (loginResponse.getStatus()) {
                            view.getUserData(loginResponse);
                        } else {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });

    }
}
