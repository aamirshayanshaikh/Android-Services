package com.example.androidtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class DownloadThread extends Thread {

    private static final String TAG = "MyTag";
    public DownloadHandler mHandler;

    public DownloadThread() {
    }

    @Override
    public void run() {

        Looper.prepare(); // Creates Message Queue
        mHandler=new DownloadHandler();
        Looper.loop(); // Loops Handeler through message queue to perform tasks

    }
}

