package com.art4muslimt.artfooddelegate.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        Intent serviceIntent = new Intent(context, UpdateUserLocationService.class);
        serviceIntent.putExtra("orderId", "" + intent.getExtras().getString("orderId"));
        context.startService(serviceIntent);

    }
}
