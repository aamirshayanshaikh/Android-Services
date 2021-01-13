package com.example.androidtest;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class DownloadHandler extends Handler {



    private ResultReceiver mReciever;
    private Service mService;


    public DownloadHandler() {
    }

    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
        mService.stopSelf(msg.arg1);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY, msg.obj.toString());
        mReciever.send(MainActivity.RESULT_OK, bundle);
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

    public void setmService(Service mService) {
        this.mService = mService;
    }


    public void setmReciever(ResultReceiver mReciever) {
        this.mReciever = mReciever;
    }



}
