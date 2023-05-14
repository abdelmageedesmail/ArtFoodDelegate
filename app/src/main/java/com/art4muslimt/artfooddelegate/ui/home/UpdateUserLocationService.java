package com.art4muslimt.artfooddelegate.ui.home;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.ServerResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.orders.OrdersFragment;
import com.art4muslimt.artfooddelegate.ui.registeration.VehiclesSpinnerAdapter;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.Observer;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.ProgressLoading;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class UpdateUserLocationService extends Service implements HomeView {

    @Inject
    HomePresenter presenter;
    String orderId;
    GPSTracker mGps;
    PrefrencesStorage storage;
    UpdateView updateView;

    Handler handler = new Handler();


    @Override
    public void onCreate() {
        super.onCreate();
        storage = new PrefrencesStorage(this);
        mGps = new GPSTracker(this);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
        }
    }


    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.art4muslimt.artfooddelegate";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);

        presenter.setView(this);
        UpdateLocationHelper updateLocationHelper = new UpdateLocationHelper(this);
        final Runnable r = new Runnable() {
            public void run() {
                updateLocationHelper.isUpdated(true, storage.getId(), orderId, "" + mGps.getLatitude(), "" + mGps.getLongitude());
                handler.postDelayed(this, 1000 * 3 * 60);
            }
        };

        handler.postDelayed(r, 1000 * 3 * 60);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        orderId = intent.getExtras().getString("orderId");

        onTaskRemoved(intent);

        return START_STICKY;
    }

    public void getCurrentOrders(String user_id, String lat, String lng) {
        ProgressLoading loading = new ProgressLoading(this);
        loading.showLoading();
        AndroidNetworking.initialize(this);
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

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Intent restartServiceIntent = new Intent(this, AlarmReciever.class);

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(),
                restartServicePendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, HomeActivity.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {

    }

    @Override
    public void isUpdated(boolean b) {

    }

    @Override
    public void getWaitingOrders(List<OrderResponse.DataEntity> data) {

    }

    @Override
    public void changeStatus(boolean b, String accept) {
        if (b) {
            if (accept.equals("locationUpdated")) {
                Log.e("updated", "...");
            }
        }

    }
}
