package com.example.Marble_Game.User;

/**
 * Created by g3863 on 2017/10/14.
 */

public class UserRecord {
    private int currentScore;
    private int highestScore;
    private int totalKlEn;

    public UserRecord(int a,int b,int c){
        this.currentScore = a;
        this.highestScore = b;
        this.totalKlEn = c;
    }

    public void setCurrentScore(int score){
        this.currentScore = score;
    }
    public void setHighestScore(int score){
        this.highestScore = score;
    }
    public void setTotalKlEn(int en){
        this.totalKlEn = en;
    }

    public int getCurrentScore(){
        return currentScore;
    }
    public int getHighestScore(){
        return highestScore;
    }
    public int getTotalKlEn(){
        return totalKlEn;
    }


}
