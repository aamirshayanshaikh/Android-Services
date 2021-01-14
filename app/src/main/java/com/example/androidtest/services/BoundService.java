package com.example.androidtest.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.androidtest.MainActivity;
import com.example.androidtest.R;

public class BoundService extends Service {

    public static final String MUSIC_COMPLETE_ACTION = "musicCompete";
    private Binder mBinder = new MyBindService();
   private MediaPlayer mPlayer;


    public class MyBindService extends Binder{
        public BoundService myService(){
            return BoundService.this;
        }
    }

    public BoundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.TAG, "onCreate: ");
        mPlayer = MediaPlayer.create(this, R.raw.song);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(MUSIC_COMPLETE_ACTION);
                intent.putExtra(MainActivity.KEY, "Done");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                stopSelf();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    // Public client methods
    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

     public void play(){
        mPlayer.start();
     }

     public void pause(){
        mPlayer.pause();
     }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }



}
