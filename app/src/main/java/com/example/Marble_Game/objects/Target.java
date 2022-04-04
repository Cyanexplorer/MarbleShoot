package com.example.Marble_Game.objects;

import android.content.Context;

import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.R;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.MatrixState;
import com.example.Marble_Game.util.TextureHelper;

/**
 * Created by User on 2016/12/26.
 */

public class Target{
    private ObjectBuilder.GeneratedData generatedData;
    private int texture;
    private Context context;
    private static PointLightShaderProgram TSP;

    public static Target createTmp(Context context){
        return new Target(
                ObjectBuilder.createPlygen(context,"target.obj",0f),
                TextureHelper.loadTexture(context, R.drawable.target),
                context
        );
    }

    private Target(ObjectBuilder.GeneratedData generatedData,int texture,Context context){
        TSP = new PointLightShaderProgram(context);
        TSP.useProgram();
        this.generatedData = generatedData;
        this.texture = texture;
        this.context = context;
    }

    public ObjectFile generate(){
        ObjectFile file = new ObjectFile(context,generatedData,texture,TSP){
            @Override
            public void onObjAction(Coordinate coordinate,MoveMent moveMent) {
                super.onObjAction(coordinate,moveMent);
                MatrixState.translate(coordinate.getx(),coordinate.gety(),coordinate.getz());
                MatrixState.rotate(-coordinate.getOrient(),0f,1f,0f);
            }
        };
        return file;
    }

}
