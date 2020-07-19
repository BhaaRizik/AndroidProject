package com.bhaa.finalproject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverCall extends BroadcastReceiver {
    final String tag="finalProject.bhaa";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(tag,"onReceive");
        context.startService(new Intent(context, PopupService.class));

    }
}