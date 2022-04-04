package com.example.Marble_Game.objects;

/**
 * Created by g3863 on 2017/10/20.
 */

public class Coordinate {
    private float x,y,z;
    private float orient;

    public Coordinate(float x,float y,float z,float orient){
        this.x = x;
        this.y = y;
        this.z = z;
        this.orient = orient;
    }
    public void setX(float nx){
        x = nx;
    }
    public void setY(float ny){
        y = ny;
    }
    public void setZ(float nz){
        z = nz;
    }
    public void setOrient(float orient){
        this.orient = orient;
    }
    public float getx(){
        return this.x;
    }
    public float gety(){
        return this.y;
    }
    public float getz(){
        return this.z;
    }
    public float getOx(){
        return (float) (x* Math.cos(orient));
    }

    public float getOY(){
        return (float) (y*Math.sin(orient));
    }

    public float getDistance(){
        return (float) Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
    }

    public float getOrient(){
        return orient;
    }
}
