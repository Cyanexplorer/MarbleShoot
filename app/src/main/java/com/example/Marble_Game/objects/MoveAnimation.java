package com.example.Marble_Game.objects;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by g3863 on 2017/10/21.
 */

public class MoveAnimation {
    private long tLength;
    private final long tInterval = 20;
    private Timer timer;
    private long counter;
    private TimerTask task;
    private Animation animation;

    private MoveAnimation(){
        tLength = 0;
        timer = new Timer();
    }

    public static MoveAnimation initial(){
        return new MoveAnimation();
    }

    public MoveAnimation settLength(long length){
        this.tLength = length;
        return this;
    }

    public MoveAnimation setAnimation(Animation animation){
        this.animation = animation;
        task = new TimerTask() {
            @Override
            public void run() {
                MoveAnimation.this.animation.animate();
                if(counter < tLength || tLength == -1){
                    counter++;
                }
                else {
                    Log.i("test","clear");
                    animation.finish();
                    cancel();
                }
            }
        };
        return this;
    }

    public void startAnimate(){
        if(tLength > 0 && timer!=null && task!=null){
            timer = new Timer();
            timer.schedule(task,0,tInterval);
        }
        else if(tLength == -1){
            timer = new Timer();
            timer.schedule(task,0,tInterval);
        }
    }

    public void stopAnimate(){
        if(timer!=null && task!=null){
            timer.cancel();
        }
    }

    public Animation getAnimation(){
        return animation;
    }

    public interface Animation{
        void animate();
        void finish();
    }
}
