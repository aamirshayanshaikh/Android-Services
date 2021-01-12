package com.example.androidtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidtest.services.MyService;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    public static final String TAG = "MyResult";
    public static final String KEY = "Key";

    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    public void runCode(View v) {


        for (String song:PlayList.songs){
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra(KEY, song);
            startService(intent);
        }

    }



    private void initViews() {
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void clearOutput(View v) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);
        mLog.setText("");
        scrollTextToEnd();
    }

    public void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }





}