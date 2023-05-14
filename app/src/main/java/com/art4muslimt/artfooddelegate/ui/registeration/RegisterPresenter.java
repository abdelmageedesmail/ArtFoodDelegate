package com.art4muslimt.artfooddelegate.ui.registeration;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.CategoriesResponse;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class RegisterPresenter {
    RegisterView view;

    public void setView(RegisterView view) {
        this.view = view;
    }


    public void getCities(Context context) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(Urls.GET_CITIES)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            CityResponse cityResponse = new Gson().fromJson(response.toString(), new TypeToken<CityResponse>() {
                            }.getType());
                            view.getCities(cityResponse.getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

    public void getNationalities(Context context) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(Urls.GET_NATIONALITEIS)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            NationalitiesResponse nationalitiesResponse = new Gson().fromJson(response.toString(), new TypeToken<NationalitiesResponse>() {
                            }.getType());
                            view.getNationalitieis(nationalitiesResponse.getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
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

    public void registerUser(Context context, String name, String mobile, String email, File image, String lat, String lng,
                             String password, File idphoto, String nationality, String city, String typevehicle,
                             File license, String vehicleplatenumber, String bankname, String iban) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.upload(Urls.REGISTER)
                .addMultipartParameter("name", name)
                .addMultipartParameter("mobile", mobile)
                .addMultipartParameter("email", email)
                .addMultipartFile("image", image)
                .addMultipartFile("idphoto", idphoto)
                .addMultipartFile("license", license)
                .addMultipartParameter("lat", lat)
                .addMultipartParameter("city", city)
                .addMultipartParameter("lng", lng)
                .addMultipartParameter("password", password)
                .addMultipartParameter("bankname", bankname)
                .addMultipartParameter("nationality", nationality)
                .addMultipartParameter("typevehicle", typevehicle)
                .addMultipartParameter("vehicleplatenumber", vehicleplatenumber)
                .addMultipartParameter("iban", iban)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.isRegistered(true);
                        } else {
                            view.isRegistered(false);
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
