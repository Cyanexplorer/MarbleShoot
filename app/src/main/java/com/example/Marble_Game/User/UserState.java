package com.example.Marble_Game.User;

/**
 * Created by g3863 on 2017/10/14.
 */

public class UserState {
    private int sp;
    private float hp;
    private int mb;

    public UserState(float hp, int sp,  int mb){
        this.hp = hp;
        this.sp = sp;
        this.mb = mb;
    }

    public int getMb(){
        return mb;
    }
    public int getSp(){
        return sp;
    }
    public void setMb(int c){
        mb = c;
    }
    public void setSp(int c){
        sp = c;
    }
}
