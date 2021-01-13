package com.example.androidtest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler {



    public DownloadHandler() {
    }

    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
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
