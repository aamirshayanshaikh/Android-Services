package com.example.androidtest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class DownloadHandler extends Handler {


    public static final String SEND_DATA_ACTION = "sendAction";
    private Service service;
    private Context mContext;


    public DownloadHandler() {

    }




    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handleMessage(Message msg) {


        downloadSong(msg.obj.toString());
        service.stopSelf(msg.arg1);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY, msg.obj.toString());
        sendDatatoUI(msg.obj.toString());

    }


    private void sendDatatoUI(String s){
        Intent intent = new Intent(SEND_DATA_ACTION);
        intent.putExtra(MainActivity.KEY, s);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
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

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
