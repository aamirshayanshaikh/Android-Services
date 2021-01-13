package com.example.androidtest.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.androidtest.DownloadThread;
import com.example.androidtest.MainActivity;
import com.example.androidtest.PlayList;

public class MyService extends Service {

    private static final String TAG = "MyResult";

    //this is started service

    public MyService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: Called");
        //Log.d(TAG, "onStartCommand: called: Song Name: "+intent.getStringExtra(MainActivity.MESSAGE_KEY));
        //Log.d(TAG, "onStartCommand: called: Intent Id: "+startId);
        final String songName=intent.getStringExtra(MainActivity.KEY);

        MyDownloadTask myDownloadTask = new MyDownloadTask();
        myDownloadTask.execute(songName);

        //return START_STICKY;  // On Application crash restarts service without passing intent
        //return START_NOT_STICKY;  // Service doesn’t start automatically, intent is also null
        return START_REDELIVER_INTENT; // Service restarts automatically and intent is also passed and is not null
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


    class MyDownloadTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            for (String song: strings) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(song);
            }



            return "All songs downloaded";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate: Song Downloded "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);
        }
    }
}