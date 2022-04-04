package com.example.Marble_Game.Builder;

import android.content.Context;

import com.example.Marble_Game.R;
import com.example.Marble_Game.objects.Coordinate;
import com.example.Marble_Game.objects.ObjectFile;
import com.example.Marble_Game.objects.Plygen;
import com.example.Marble_Game.util.TextureHelper;


/**
 * Created by User on 2016/12/19.
 */

public class PlygenBuilder {

    private static Plygen plygen;
    private static ObjectFile objectFile;

    public static void setBuilder(Context context,String path){
        Coordinate coordinate = new Coordinate(0,0,0,0);
        plygen = new Plygen(ObjectBuilder.createPlygen(context,path,0f),TextureHelper.loadTexture(context, R.drawable.target),context);
        objectFile = plygen.generate();
    }

    public static void FieldBuild(){
        objectFile.drawObject();
    }

}
