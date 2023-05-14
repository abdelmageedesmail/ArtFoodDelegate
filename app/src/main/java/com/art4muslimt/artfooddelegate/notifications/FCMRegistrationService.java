package com.art4muslimt.artfooddelegate.notifications;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;


/**
 * Created by abdelmageed on 30/03/17.
 */

public class FCMRegistrationService extends IntentService {

    Context context;
    SharedPreferences sh;
    String id, userType;

    public static String success, token;
    private String api_token;
    private String user_id, macAddress;
    private String userToken;
    private PrefrencesStorage localeShared;

    public FCMRegistrationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        localeShared = new PrefrencesStorage(this);
        user_id = localeShared.getId();
        userToken = localeShared.getToken();
//        token = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<String> task) {
                        token = task.getResult();
                        Log.e("token", "" + token);
                        refreshToken();
                    }
                });
        Log.e("token", "" + token);
//        FCMTokenRefreshListenerService.refreshed=true;
//        if (FCMTokenRefreshListenerService.refreshed){
//        refreshToken();
//        }
    }

    private void refreshToken() {

        AndroidNetworking.initialize(this);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.STORE_TOKEN);
        post.addBodyParameter("id_user", user_id);
        post.addBodyParameter("token", token);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", "" + response);
                        localeShared.storeKey("oldToken", token);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }
}
