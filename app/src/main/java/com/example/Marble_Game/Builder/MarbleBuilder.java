package com.example.Marble_Game.Builder;

import android.content.Context;
import android.util.Log;

import com.example.Marble_Game.Activity.MainGamePage;
import com.example.Marble_Game.objects.Coordinate;
import com.example.Marble_Game.objects.Marble;
import com.example.Marble_Game.objects.MoveMent;
import com.example.Marble_Game.objects.ObjectFile;

import java.util.ArrayList;


/**
 * Created by User on 2016/12/19.
 */

public class MarbleBuilder{
    private final static int maxAmount=6;
    private static boolean Enable;
    private static ArrayList<ObjectFile> objBuffer;
    private static Marble marbleTmp;

    public static void setMarbleBuilder(Context context){
        marbleTmp = Marble.createTmp(context);
        Enable=false;
        objBuffer =new ArrayList<>();
    }

    public static void MarbleDrawer(){
        detect(objBuffer);
        draw(objBuffer);
    }

    private static void detect(ArrayList<ObjectFile> obj){
        for(int i = 0;i<obj.size();i++){
            ObjectFile m = obj.get(i);
            if(m.getDistance()>150){
                MBRemove(m);
            }
            checkCollution(m);
            if(m.getAlive() == ObjectFile.DEAD){
                Log.i("test","delete");
                objBuffer.remove(m);
                i--;
            }
        }
    }

    private static void draw(ArrayList<ObjectFile> buffer){
        ArrayList<ObjectFile> temp = objBuffer;
        for(int i = 0;i<temp.size();i++){
            temp.get(i).drawObject();
        }
    }

    private static void checkCollution(ObjectFile m){
        int count = TargetBuilder.getCurrAmount();
        Boolean hit = false;

        for(int i=0; i<count;i++){
            Coordinate coordinate = m.getCoordinate();
            if(TargetBuilder.checkhit(i,coordinate.getx(),coordinate.gety(),coordinate.getz())){
                MainGamePage.profile.setRecord(TargetBuilder.getScore(i));
                TargetBuilder.TGremove(i);
                hit = true;
                count--;
                i--;
            }
        }

        if (hit){
            MBRemove(m);
        }

    }


    public static void MBadder(float orientation){
        if(objBuffer.size()<maxAmount && Enable){
            Coordinate coordinate = new Coordinate(0,0,0,orientation);
            ObjectFile m = marbleTmp.generate().setCoord(coordinate).setMove(new MoveMent(2f,0.05f,orientation));
            objBuffer.add(m);
        }
    }


    public static void MBRemove(ObjectFile objectFile){
        if(objectFile.getAlive()<ObjectFile.DESTROY){
            objectFile.setState(ObjectFile.DESTROY);
        }
    }

    public static int getCurrAmount() {
        return objBuffer.size();
    }

    public static void setEnable(boolean status){
        Enable=status;
    }

    public static int getAvailAmount(){return maxAmount- objBuffer.size();}

    public static ArrayList<ObjectFile> getMarbleArr(){
        return objBuffer;
    }
    public static void MBClear(){
        if(objBuffer !=null && objBuffer.size()>0){
            for(ObjectFile objectFile: objBuffer){
                objectFile.setState(ObjectFile.DESTROY);
            }
        }
    }

}
