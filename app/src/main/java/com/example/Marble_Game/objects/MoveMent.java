package com.example.Marble_Game.objects;

/**
 * Created by g3863 on 2017/10/22.
 */

public class MoveMent {
    private float speed=0.5f;
    private float acc=0.002f;
    private float ori;

    public MoveMent(float speed,float acc,float ori){
        this.speed = speed;
        this.acc = acc;
        this.ori = ori;
    }

    public float getSpeed(){return speed;}
    public float getAcc(){return acc;}
    public float getOri(){return ori;}

    public void setSpeed(float speed){this.speed =speed;}
    public void setAcc(float acc){this.acc = acc;}
    public void setOri(float ori){this.ori = ori;}

}
