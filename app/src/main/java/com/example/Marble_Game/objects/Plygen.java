package com.example.Marble_Game.objects;

import android.content.Context;

import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.R;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.TextureHelper;

/**
 * Created by User on 2016/12/26.
 */

public class Plygen{
    private ObjectBuilder.GeneratedData generatedData;
    private int texture;
    private Context context;
    private static PointLightShaderProgram TSP;

    public static Plygen createTmp(Context context,String path){
        return new Plygen( ObjectBuilder.createPlygen(context,path,0f),
                TextureHelper.loadTexture(context, R.drawable.target),
                context);
    }

    public Plygen(ObjectBuilder.GeneratedData generatedData,int texture, Context context) {
        TSP = new PointLightShaderProgram(context);
        this.context = context;
        this.generatedData = generatedData;
        this.texture = texture;
    }

    public ObjectFile generate(){
        ObjectFile file = new ObjectFile(context,generatedData,texture,TSP);

        return file;
    }

    public void onObjCreate(ObjectFile file){

        file.drawObject();
    }

    public void onObjAction(ObjectFile file){
        file.drawObject();
    }

    public void onObjDestry(ObjectFile file){
        file.drawObject();
    }
}
