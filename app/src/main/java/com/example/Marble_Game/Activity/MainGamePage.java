/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.Marble_Game.Activity;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Marble_Game.LocaDBFunc.SqlDbModifier;
import com.example.Marble_Game.R;
import com.example.Marble_Game.UI.GlEs2Renderer;
import com.example.Marble_Game.UI.SensorUpdate;
import com.example.Marble_Game.User.UserProfile;
import com.example.Marble_Game.User.UserState;
import com.example.Marble_Game.util.SensorBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.Marble_Game.util.Constants.DBname;
import static com.example.Marble_Game.util.Constants.TBname;

public class MainGamePage extends AppCompatActivity implements UserProfile.UpdateData {
    /**
     * Hold a reference to our GLSurfaceView
     */

    private SensorBuilder Sbuilder;
    private static final int totalPlayTime=60;
    public static UserProfile profile;
    private int DownCounter=3;


    private TextView timeView,Warn;
    private TextView Score;
    private ProgressBar MBSeekBar, SKSeekBar;

    private static int min,sec,tsec;
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private GlEs2Renderer GlEs2Renderer;

    LayoutInflater layoutInflater;
    View dv;

    Timer timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        //initial view element
        glSurfaceView = (GLSurfaceView)findViewById(R.id.GLsf);
        timeView = (TextView)findViewById(R.id.TimeView);
        Warn = (TextView)findViewById(R.id.Warn);
        Score = (TextView)findViewById(R.id.Score);
        MBSeekBar = (ProgressBar) findViewById(R.id.mb_skbar);
        SKSeekBar = (ProgressBar) findViewById(R.id.skll_skbar);

        profile = UserProfile.stdProfile();
        profile.setUpdateData(this);
        update();

        Sbuilder=new SensorBuilder(this);
        Sbuilder.SUP=new Update();
        layoutInflater=LayoutInflater.from(MainGamePage.this);
        dv=layoutInflater.inflate(R.layout.activity_save_page,null);
        // Check if the system supports OpenGL ES 2.0.
        ActivityManager activityManager =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager
            .getDeviceConfigurationInfo();
        // Even though the latest emulator supports OpenGL ES 2.0,
        // it has a bug where it doesn't set the reqGlEsVersion so
        // the above check doesn't work. The below will detect if the
        // app is running on an emulator, and assume that it supports
        // OpenGL ES 2.0.
        final boolean supportsEs2 =
            configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                 && (Build.FINGERPRINT.startsWith("generic")
                  || Build.FINGERPRINT.startsWith("unknown")
                  || Build.MODEL.contains("google_sdk")
                  || Build.MODEL.contains("Emulator")
                  || Build.MODEL.contains("Android SDK built for x86")));

        GlEs2Renderer = new GlEs2Renderer(this);
        if (supportsEs2) {
            // ...
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setEGLConfigChooser(true);
            // Assign our renderer.
            glSurfaceView.setRenderer(GlEs2Renderer);
            rendererSet = true;
        } else {
            /*
             * This is where you could create an OpenGL ES 1.x compatible
             * renderer if you wanted to support both ES 1 and ES 2. Since 
             * we're not doing anything, the app will crash if the device 
             * doesn't support OpenGL ES 2.0. If we publish on the market, we 
             * should also add the following to AndroidManifest.xml:
             * 
             * <uses-feature android:glEsVersion="0x00020000"
             * android:required="true" />
             * 
             * This hides our app from those devices which don't support OpenGL
             * ES 2.0.
             */
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                Toast.LENGTH_LONG).show();
            return;
        }

        glSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlEs2Renderer.Marble_Launcher(0);
            }
        });

        glSurfaceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                GlEs2Renderer.Marble_Launcher(1);
                return true;
            }
        });
        tsec=0;
        DownCounter=3;
        timer=new Timer();
        timer.schedule(timerTask,0,1000);
    }

    @Override
    public void update() {
        getViewhandler.sendEmptyMessage(0);
    }

    private class Update implements SensorUpdate{
        @Override
        public void Update(float[] asix) {
            GlEs2Renderer.setAngle(asix[0],asix[1],asix[2]);
            glSurfaceView.requestRender();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Sbuilder.Onstop();

        //timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sbuilder.Onstart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    private Handler eventhandler=new Handler() {
        public void handleMessage(Message message){
            super.handleMessage(message);
            if(tsec<=4){
                if(DownCounter>0){
                    Warn.setText(String.format(Locale.ENGLISH,"%d",DownCounter));
                    DownCounter=(DownCounter==0?0:(DownCounter-1));
                }
                else Warn.setText(String.format(Locale.ENGLISH,"%s","GO~Shooting!!"));
            }
            if(tsec>3&&tsec<=3+totalPlayTime){
                //開始時間
                if(tsec==4){
                    GlEs2Renderer.setEnableShooting(true);
                    GlEs2Renderer.start_create_target();
                }
                if(tsec==6){
                    Warn.setText("");
                }
                sec=(totalPlayTime+4-tsec)%60;
                min=(totalPlayTime+4-tsec)/60;
                timeView.setText(String.format(Locale.ENGLISH,"Time:%02d:%02d",min,sec));
            }
            if(tsec>3+totalPlayTime&&tsec<=5+totalPlayTime){
                sec=0;
                min=0;
                timeView.setText(String.format(Locale.ENGLISH,"Time:%02d:%02d",min,sec));
                GlEs2Renderer.stopRender();
                glSurfaceView.setClickable(false);
                GlEs2Renderer.setEnableShooting(false);
                Warn.setText(String.format(Locale.ENGLISH,"%s","Finish!!"));
            }
            if(tsec>5+totalPlayTime){
                timer.cancel();
                String[] titleArray=getResources().getStringArray(R.array.rate);
                String title;
                int score = profile.getRecord().getCurrentScore();
                if(score<256)title=titleArray[0] ;
                else if(score<493)title=titleArray[1];
                else if(score<600)title=titleArray[2];
                else if(score<720)title=titleArray[3];
                else title=titleArray[4];
                new AlertDialog.Builder(MainGamePage.this)
                        .setCancelable(false)
                        .setTitle(title)
                        .setMessage("Your Score:"+Score.getText())
                        .setPositiveButton("Ok", AskStore)
                        .show();
            }
        }
    };

    private DialogInterface.OnClickListener AskStore=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            AlertDialog AskStore=new AlertDialog.Builder(MainGamePage.this)
                    .setCancelable(false)
                    .setMessage(String.format(Locale.ENGLISH,"%s","Do you want to store your score?"))
                    .setPositiveButton("Yes", SaveStor)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainGamePage.this.finish();
                        }
                    }).show();
        }
    };

    private DialogInterface.OnClickListener SaveStor=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            new AlertDialog.Builder(MainGamePage.this)
                    .setCancelable(false)
                    .setView(dv)
                    .setTitle("~你的名子~")
                    .setPositiveButton("Save",recheck)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainGamePage.this.finish();
                        }
                    }).show();
        }
    };


    private DialogInterface.OnClickListener recheck=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //retrieve current time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dt=new Date();
            String s=sdf.format(dt);

            final SqlDbModifier DBM=new SqlDbModifier(MainGamePage.this,DBname);
            final ContentValues values=new ContentValues();

            TextView PN = (TextView)dv.findViewById(R.id.PlayerName);
            values.put("PlayerName",PN.getText().toString());
            values.put("Score",profile.getRecord().getCurrentScore());
            values.put("Time",s);

            if(DBM.DBFull(TBname)){
                new AlertDialog.Builder(MainGamePage.this)
                        .setCancelable(false)
                        .setMessage("There are alredy 10 records, and the new one will\n"
                                + "overwrite the oldest one.\n" +
                                "Are you sure?")
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                while(DBM.DBFull(TBname)){
                                    DBM.DeleteDBfile(TBname);
                                    Log.i("Delete","Top item");
                                }
                                Log.i("Delet","finish");
                                DBM.AddDBfile(values,TBname);
                                MainGamePage.this.finish();
                            }
                        })
                        .setNegativeButton("Regret", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainGamePage.this.finish();
                            }
                        })
                        .show();
            }
            else{
                DBM.AddDBfile(values,TBname);
                MainGamePage.this.finish();
            }
        }
    };

    private  TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            tsec++;
            eventhandler.sendEmptyMessage(0);
        }
    };


    Handler getViewhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UserState state = profile.getState();

            SKSeekBar.setProgress(state.getSp());
            MBSeekBar.setProgress(state.getMb());
            Score.setText(String.format(Locale.ENGLISH,"Score: %2d",profile.getRecord().getCurrentScore()));
        }
    };

    //forbid back button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
    }
}