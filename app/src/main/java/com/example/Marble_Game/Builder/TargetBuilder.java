package com.example.Marble_Game.Builder;

import android.content.Context;
import android.util.Log;

import com.example.Marble_Game.UI.Get_Score;
import com.example.Marble_Game.objects.Coordinate;
import com.example.Marble_Game.objects.ObjectFile;
import com.example.Marble_Game.objects.Target;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


/**
 * Created by User on 2016/12/19.
 */

public class TargetBuilder {
    private final static int maxAmount=10;
    private final static int score=10;
    private static boolean Enable;
    private static List<ObjectFile> target;
    public static Get_Score<Float> GSore=null;
    public static Target targetTmp;

    public static void setTargetBuilder(Context context){
        targetTmp = Target.createTmp(context);
        Enable=false;
        target=new ArrayList<>();
    }

    public static void TargetCreate(){
        if(target.size()>0){
            for(int i=0;i<target.size();i++){
                if(target.size()>0){
                    target.get(i).drawObject();
                }
            }
        }
    }

    public static boolean checkhit(int order,float x, float y, float z){
        if(order>=target.size()){
            return false;
        }
        ObjectFile tg = target.get(order);
        if(tg.getAlive() == ObjectFile.WORKING){
            Coordinate coordinate = tg.getCoordinate();
            float distance=(float) sqrt(pow((coordinate.getx()-x),2)+pow((coordinate.gety()-y),2)+pow((coordinate.getz()-z),2));
            Log.i("test",String.valueOf(distance));
            return distance<20f;
        }
        else return false;

    }

    public static void TGadder(float orientation, float distance){
        if(target.size()<maxAmount && Enable){
            Coordinate coordinate = new Coordinate(
                    distance*(float)Math.cos((float)Math.PI*(orientation+90)/180),
                    0f,
                    distance*(float)Math.sin((float)Math.PI*(orientation+90)/180),
                    orientation
            );
            target.add(targetTmp.generate().setCoord(coordinate));
        }
    }

    public static void TGremove(int i){
        target.remove(i);
    }

    public static void TGClear(){
        while(target!=null && target.size()>0) {
            target.remove(0);
        }
    }

    public static int getCurrAmount() {
        return target.size();
    }
    public static void setEnable(boolean status){
        Enable=status;
    }
    public static int getAvailAmount(){return maxAmount-target.size();}
    public static int getScore(int order){
        return score;
    }

}
