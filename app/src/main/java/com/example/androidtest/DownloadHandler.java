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


    private ResultReceiver resultReceiver;
    private Service service;


    public DownloadHandler() {

    }

    public void setResultReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }


    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handleMessage(Message msg) {

        service.stopSelf(msg.arg1);
        downloadSong(msg.obj.toString());
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY, msg.obj.toString());
        resultReceiver.send(MainActivity.RESULT_OK, bundle);

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
