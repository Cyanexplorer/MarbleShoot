package com.example.Marble_Game.Builder;

import android.content.Context;

import com.example.Marble_Game.objects.Coordinate;
import com.example.Marble_Game.objects.Marble;
import com.example.Marble_Game.objects.ObjectFile;
import com.example.Marble_Game.programs.PointLightShaderProgram;

import java.util.ArrayList;


/**
 * Created by User on 2016/12/19.
 */

public class SkillBuilder {
    private final static int maxAmount=60;
    public final static int SkillRange=13;

    private final static int shootangle=120;
    private static int Skill_time=3;
    private static boolean Enable=true;
    private static ArrayList<ObjectFile> marbles;
    private static Marble marbleTmp;
    private static PointLightShaderProgram TSP;
    public static void setSkillBuilder(Context context){

        marbleTmp = Marble.createTmp(context);
        Enable=false;
        marbles =new ArrayList<>();
        Skill_time=3;

    }

    public static void SkillReward(){
        Skill_time+=2;
    }

    public static void MarbleShooter(){
        if(marbles.size()>0){
            for(int i = 0; i< marbles.size(); i++){
                if(marbles.get(i).getDistance()>150)MBremove(i);
                else if(marbles.size()>0){
                    ObjectFile m= marbles.get(i);
                    m.drawObject();
                }
            }
        }
    }

    public static void MBadder(float orientation){
        if(Skill_time>0 && Enable){
            for(int i=0;i<=SkillRange;i++){
                Coordinate coordinate = new Coordinate(
                        0,0,0,
                        orientation-shootangle/2+shootangle/SkillRange*i
                );
                ObjectFile m = marbleTmp.generate().setCoord(coordinate);
                marbles.add(m);
            }
            Skill_time--;
        }
    }

    public static void MBremove(int i){
        marbles.remove(i);
    }

    public static int getCurrAmount() {
        return marbles.size();
    }

    public static int getSkill_Amount(){return Skill_time;}

    public static void setEnable(boolean status){
        Enable=status;
    }

    public static int getAvailAmount(){return maxAmount- marbles.size();}

    public static ArrayList<ObjectFile> getMarbleArr(){
        return marbles;
    }
}
