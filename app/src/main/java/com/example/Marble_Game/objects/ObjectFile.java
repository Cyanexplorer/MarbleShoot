package com.example.Marble_Game.objects;

import android.content.Context;
import android.util.Log;

import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.data.VertexArray;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.MatrixState;

import java.util.List;

import static com.example.Marble_Game.util.Constants.F_FRAG;
import static com.example.Marble_Game.util.Constants.NORMAL_COMPONENT_COUNT;
import static com.example.Marble_Game.util.Constants.POSITION_COMPONENT_COUNT;
import static com.example.Marble_Game.util.Constants.STRIDE;
import static com.example.Marble_Game.util.Constants.S_FRAG;
import static com.example.Marble_Game.util.Constants.TEXTURE_COMPONENT_COUNT;

/**
 * Created by g3863 on 2017/10/20.
 */

public class ObjectFile {
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;
    private int texture;
    //public ObjectBuilder.GeneratedData generatedData;
    private Coordinate coordinate;
    private MoveMent moveMent;
    private PointLightShaderProgram TSP;
    public int state;
    public final static int CREATE = 1;
    public final static int WORKING = 2;
    public final static int DESTROY = 3;
    public final static int DEAD = 4;

    public ObjectFile(Context context, ObjectBuilder.GeneratedData generatedData){
        //this.generatedData = generatedData;
        this.vertexArray = new VertexArray(generatedData.vertexData);
        this.drawList = generatedData.drawList;
        this.coordinate = new Coordinate(0,0,0,0);
        this.state = CREATE;
    }

    public ObjectFile(Context context, ObjectBuilder.GeneratedData generatedData,int texture,PointLightShaderProgram TSP){
        //this.generatedData = generatedData;
        this.TSP = TSP;
        this.vertexArray = new VertexArray(generatedData.vertexData);
        this.drawList = generatedData.drawList;
        this.texture = texture;
        this.state = CREATE;
        this.coordinate = new Coordinate(0,0,0,0);
        this.moveMent = new MoveMent(0,0,0);
    }

    public ObjectFile setCoord(Coordinate coordinate){
        this.coordinate = coordinate;
        return this;
    }

    public ObjectFile setMove(MoveMent move){
        this.moveMent = move;
        return this;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    public int getAlive(){
        return state;
    }
    public float getDistance(){
        return (float) Math.sqrt(Math.pow(coordinate.getx(),2)+Math.pow(coordinate.gety(),2)+Math.pow(coordinate.getz(),2));
    }

    public void setState(int state){
        this.state = state;
    }

    public MoveMent getMoveMent(){
        return moveMent;
    }

    public void drawObject(){
        MatrixState.pushMatrix();
        if(state == CREATE){
            onObjCreate(coordinate,moveMent);
        }

        else if(state == WORKING){
            Log.i("test","working");
            onObjAction(coordinate,moveMent);
        }

        else if(state == DESTROY){
            Log.i("test","destroy");
            onObjDestroy(coordinate,moveMent);
        }

        TSP.setUniforms(texture);
        bindData(TSP);
        draw();
        MatrixState.popMatrix();
    }

    public void bindData(PointLightShaderProgram lightprogram) {
        //vertice to glsl
        vertexArray.setVertexAttribPointer(0,
                lightprogram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);
        //texture to glsl
        vertexArray.setVertexAttribPointer(F_FRAG,
                lightprogram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COMPONENT_COUNT,STRIDE);
        //vector to glsl
        vertexArray.setVertexAttribPointer(S_FRAG,
                lightprogram.getNormalAttributeLocation(),NORMAL_COMPONENT_COUNT,
                STRIDE);
    }

    public void tranform(float mx,float my, float mz, float rx, float ry, float rz, float angle){
        MatrixState.translate(mx,my,mz);
        MatrixState.rotate(angle, rx,ry,rz);
    }

    public void tranform(float distance,float anglexz,float angleh){
        MatrixState.translate(
                (float) (distance*Math.cos(angleh)*Math.cos(anglexz)),
                (float) (distance*Math.sin(angleh)),
                (float) (distance*Math.cos(angleh)*Math.sin(anglexz))
                );
    }

    //store operation
    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }

    public void onObjCreate(Coordinate coordinate, MoveMent moveMent){}
    public void onObjAction(Coordinate coordinate,MoveMent moveMent){}
    public void onObjDestroy(Coordinate coordinate,MoveMent moveMent){}

}
