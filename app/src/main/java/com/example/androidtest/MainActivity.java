package com.example.androidtest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.androidtest.services.BoundService;
import com.example.androidtest.services.MyIntentService;
import com.example.androidtest.services.MyService;


public class MainActivity extends AppCompatActivity  {

    public static final String TAG = "MyResult";
    public static final String KEY = "Key";
    private Button mBtnPlay;
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    private BoundService mBoundService;
    private boolean mBound = false;

    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyBindService serviceBinder = (BoundService.MyBindService) iBinder;
            mBoundService = serviceBinder.myService();
            mBound = true;
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    private BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra(KEY) != null){
                if(intent.getStringExtra(KEY).equals("Done")){
                    mBtnPlay.setText("Play");
                }
            }
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, BoundService.class);
        bindService(intent, myServiceConnection, BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mReciever, new IntentFilter(BoundService.MUSIC_COMPLETE_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(myServiceConnection);
            mBound = false;
        }

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReciever);

    }


    public void onBtnMusicClicked(View view){
        if(mBound){
            if(mBoundService.isPlaying()){
                mBoundService.pause();
                mBtnPlay.setText("Play");
            }else {
                Intent intent = new Intent(MainActivity.this, BoundService.class);
                startService(intent);
                mBoundService.play();
                mBtnPlay.setText("Pause");
            }
        }
    }

    public void runCode(View v) {


        log("Playing musing buddy");

        /*for (String song:PlayList.songs){
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            intent.putExtra(KEY, song);
            startService(intent);
        }*/

    }



    private void initViews() {
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mBtnPlay = (Button) findViewById(R.id.play);
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