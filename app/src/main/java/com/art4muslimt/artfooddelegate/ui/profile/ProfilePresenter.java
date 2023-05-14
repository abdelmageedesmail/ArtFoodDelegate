package com.art4muslimt.artfooddelegate.ui.profile;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDataResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDetails;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ProfilePresenter {

    ProfileView view;

    public void setView(ProfileView view) {
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

    public void getUserData(Context context, String id) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_USER_DATA)
                .addBodyParameter("id", id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        UserDataResponse loginResponse = new Gson().fromJson(response.toString(), new TypeToken<UserDataResponse>() {
                        }.getType());
                        if (loginResponse.getStatus()) {
                            view.getUserData(loginResponse.getData());
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


    public void editUser(Context context, String id, String name, String mobile, String email, File image, String lat, String lng,
                         File idphoto, String nationality, String city, String typevehicle,
                         File license, String vehicleplatenumber, String bankname, String iban) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Urls.UPDATE_PROFILE);
        upload.addMultipartParameter("name", name)
                .addMultipartParameter("id", id)
                .addMultipartParameter("mobile", mobile)
                .addMultipartParameter("email", email)
                .addMultipartParameter("lat", lat)
                .addMultipartParameter("lng", lng)
                .addMultipartParameter("city", city)
                .addMultipartParameter("bankname", bankname)
                .addMultipartParameter("nationality", nationality)
                .addMultipartParameter("typevehicle", typevehicle)
                .addMultipartParameter("vehicleplatenumber", vehicleplatenumber)
                .addMultipartParameter("iban", iban);
        if (image != null) {
            upload.addMultipartFile("image", image);
        }
        if (idphoto != null) {
            upload.addMultipartFile("idphoto", idphoto);
        }
        if (license != null) {
            upload.addMultipartFile("license", license);
        }

        upload.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            UserDetails userDataResponse = new Gson().fromJson(response.toString(), new TypeToken<UserDetails>() {
                            }.getType());
                            view.getUpdatedData(userDataResponse.getUser_details());
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

    public void editPassword(Context context, String id, String oldpassword, String newpassword) {
        ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.UPDATE_PASSWORD);

        post.addBodyParameter("id", id)
                .addBodyParameter("oldpassword", oldpassword)
                .addBodyParameter("newpassword", newpassword);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()) {
                            view.passwordUpdated(true);
                        } else {
                            view.passwordUpdated(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });


    }


}
