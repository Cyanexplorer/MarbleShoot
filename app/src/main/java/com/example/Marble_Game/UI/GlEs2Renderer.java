/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.Marble_Game.UI;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.example.Marble_Game.Activity.MainGamePage;
import com.example.Marble_Game.Builder.EnvironmentBuilder;
import com.example.Marble_Game.Builder.MarbleBuilder;
import com.example.Marble_Game.Builder.SkillBuilder;
import com.example.Marble_Game.Builder.TargetBuilder;
import com.example.Marble_Game.util.MatrixState;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class GlEs2Renderer implements Renderer{
    //private final FloatBuffer vertexData;
    private final Context context;

    //View Rotate Angle
    private float xa,ya,za,aa;
    private float xm;

    public GlEs2Renderer(Context context) {
        this.context = context;

    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        EnvironmentBuilder.setEnvironmentBuilder(context);
        SkillBuilder.setSkillBuilder(context);
        MarbleBuilder.setMarbleBuilder(context);
        TargetBuilder.setTargetBuilder(context);
        //PlygenBuilder.setBuilder(context,"ball");

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {                
        // Set the OpenGL viewport to fill the entire surface.
        float Ratio=1f;
        glViewport(0, 0, width, height);
        final float aspectRatio = (float) width / (float) height;

        MatrixState.setProjectFrustum(-aspectRatio*Ratio,aspectRatio*Ratio,-Ratio,Ratio,1.5f,800f);
        //MatrixState.setCamera(0f, 100f, 0f, 0f, 2f, 2f, 0f, 1f, 0f);
        MatrixState.setCamera(0f, 12f, 0f, 0f, 10f, 20f, 0f, 1f, 0f);
        MatrixState.setInitStack();
        MatrixState.setLightLocation(0f,50f,0f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.

        GLES20.glClearDepthf(1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        MatrixState.pushMatrix();
        MatrixState.rotate(-xm,0f,1f,0f);
        MatrixState.pushMatrix();
        //旋轉鏡頭方向



        //PlygenBuilder.FieldBuild();
        EnvironmentBuilder.FieldBuild();
        MarbleBuilder.MarbleDrawer();
        //SkillBuilder.MarbleDrawer();
        //TargetBuilder.TargetCreate();
        MatrixState.popMatrix();
        MatrixState.popMatrix();

        MainGamePage.profile.setMarble(MarbleBuilder.getAvailAmount());
    }

    public void setAngle(float x,float y,float z){
        xa=x;
        ya=y;
        za=z;
        xm+=x;
    }

    public void Marble_Launcher(int select){
        switch (select){
            case 0:
                MarbleBuilder.MBadder(-xm);
                break;
            case 1:
                SkillBuilder.MBadder(-xm);
                break;
            default:break;
        }
    }

    public void setEnableShooting(Boolean sw){
        //buildTarget();
        MarbleBuilder.setEnable(sw);
        SkillBuilder.setEnable(sw);
    }

    //Enermy create
    private Timer timer;
    public void start_create_target(){
        TargetBuilder.setEnable(true);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clear_target();
                build_target();
            }
        },0,8000);
    }

    public void build_target(){
        float tra = (float) (Math.random()* 5);
        for(int i=0;i<10;i++){
            float r=(float)((int)(Math.random()* 36)*10)+tra;
            TargetBuilder.TGadder(-r,120f);
        }
    }

    public void clear_target(){
        TargetBuilder.TGClear();
    }
    public void clear_marble(){
        MarbleBuilder.MBClear();
    }

    public void stopRender(){
        timer.cancel();
        clear_target();
        clear_marble();
    }




}