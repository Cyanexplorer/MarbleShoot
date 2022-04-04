package com.example.Marble_Game.objects;

import android.content.Context;

import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.programs.PointLightShaderProgram;

/**
 * Created by g3863 on 2017/10/22.
 */

public class ObjTmp {

    private ObjectBuilder.GeneratedData generatedData;
    private int texture;
    private Context context;
    private static PointLightShaderProgram TSP;

    public static ObjTmp createTmp(ObjectBuilder.GeneratedData generatedData,int texture,Context context){
        return new ObjTmp(generatedData,texture,context);
    }

    public ObjTmp(ObjectBuilder.GeneratedData generatedData, int texture, Context context){
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
