package com.example.androidtest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.androidtest.DownloadThread;
import com.example.androidtest.MainActivity;

public class MyService extends Service {

    private static final String TAG = "MyResult";
    private DownloadThread thread;


    public MyService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");

        thread = new DownloadThread();
        thread.start();

        while (thread.mHandler == null){

        }

        thread.mHandler.setService(this);
        thread.mHandler.setmContext(getApplicationContext());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: Called");
        //Log.d(TAG, "onStartCommand: called: Song Name: "+intent.getStringExtra(MainActivity.MESSAGE_KEY));
        //Log.d(TAG, "onStartCommand: called: Intent Id: "+startId);
        final String songName=intent.getStringExtra(MainActivity.KEY);


        Message message = Message.obtain();
        message.obj = songName;
        message.arg1 = startId;
        thread.mHandler.handleMessage(message);

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }


}