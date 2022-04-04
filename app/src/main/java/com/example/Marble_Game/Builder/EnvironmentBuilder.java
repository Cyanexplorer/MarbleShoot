package com.example.Marble_Game.Builder;

import android.content.Context;

import com.example.Marble_Game.R;
import com.example.Marble_Game.objects.Field;
import com.example.Marble_Game.objects.Wall;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.MatrixState;
import com.example.Marble_Game.util.TextureHelper;


/**
 * Created by User on 2016/12/19.
 */

public class EnvironmentBuilder {
    private static int[] texture=new int[2];
    private final static float radius=200f;
    private final static int vertex=50;
    private static PointLightShaderProgram TSP;
    private static Field field;
    private static Wall wall;

    public static void setEnvironmentBuilder(Context context){
        texture[0]= TextureHelper.loadTexture(context, R.drawable.field);
        texture[1]= TextureHelper.loadTexture(context, R.drawable.back_wall);
        field=new Field(radius,0f,vertex);
        wall=new Wall(radius,0f,vertex);
        TSP=new PointLightShaderProgram(context);
        TSP.useProgram();
    }

    public static void FieldBuild(){
        MatrixState.pushMatrix();
        MatrixState.rotate(0f,0f,0f,1f);
        TSP.setUniforms(texture[0]);
        field.bindData(TSP);
        field.draw();
        TSP.setUniforms(texture[1]);
        wall.bindData(TSP);
        wall.draw();
        MatrixState.popMatrix();
    }

}
