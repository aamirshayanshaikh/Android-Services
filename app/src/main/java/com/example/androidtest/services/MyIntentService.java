package com.example.androidtest.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.androidtest.MainActivity;

public class MyIntentService extends IntentService {


    public static final String BROADCAST_INTENT_ACTION = "broadcastIntentAction";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String songName = intent.getStringExtra(MainActivity.KEY);
        downloadSong(songName);


        Intent intent1 = new Intent(BROADCAST_INTENT_ACTION);
        intent1.putExtra(MainActivity.KEY,songName);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

    }


    private void downloadSong(final String songName){
        Log.d(MainActivity.TAG, "run: staring download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(MainActivity.TAG, "downloadSong: "+songName+" Downloaded...");
    }
}
