package com.example.Marble_Game.User;

import static com.example.Marble_Game.util.Constants.sklLimitCount;

/**
 * Created by g3863 on 2017/10/14.
 */

public class UserAbi {
    private String playerName;
    private int hp_max;
    private int sp_max;
    private int mb_max;
    private float mbChargeTime;
    private Boolean[] skill = new Boolean[sklLimitCount];

    public UserAbi(String s0, int a,int b,int c,float f0,Boolean [] b0){
        this.playerName = s0;
        this.hp_max = a;
        this.mb_max = b;
        this.sp_max = c;
        this.mbChargeTime = f0;

        System.arraycopy(b0,0,skill,0,b0.length);
    }

    public int getHp_max(){
        return hp_max;
    }
    public int getSp_max(){
        return sp_max;
    }
    public int getMb_max(){
        return mb_max;
    }
}
