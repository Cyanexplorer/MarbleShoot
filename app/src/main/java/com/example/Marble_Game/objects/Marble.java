package com.example.Marble_Game.objects;

import android.content.Context;
import android.util.Log;

import com.example.Marble_Game.Builder.MarbleBuilder;
import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.R;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.MatrixState;
import com.example.Marble_Game.util.TextureHelper;

/**
 * Created by User on 2016/12/26.
 */

public class Marble {

    private ObjectBuilder.GeneratedData generatedData;
    private int texture;
    private Context context;
    private static PointLightShaderProgram TSP;

    public static Marble createTmp(Context context){
        return new Marble(
                ObjectBuilder.createPlygen(context,"ball.obj",0f),
                TextureHelper.loadTexture(context, R.drawable.target),
                context
        );
    }

    private Marble(ObjectBuilder.GeneratedData generatedData,int texture,Context context){
        TSP = new PointLightShaderProgram(context);
        TSP.useProgram();
        this.generatedData = generatedData;
        this.texture = texture;
        this.context = context;
    }

    public ObjectFile generate(){
        return new ObjectFile(context,generatedData,texture,TSP){
            MoveAnimation moveAnimation;

            @Override
            public void onObjCreate(Coordinate coordinate, MoveMent moveMent) {
                super.onObjCreate(coordinate, moveMent);
                moveAnimation = MoveAnimation.initial()
                        .settLength(5000)
                        .setAnimation(new MoveAnimation.Animation() {
                            @Override
                            public void animate() {
                                coordinate.setX(coordinate.getx()+moveMent.getSpeed()*(float)Math.cos((float)Math.PI*(moveMent.getOri()+90f)/180f));
                                coordinate.setZ(coordinate.getz()+moveMent.getSpeed()*(float)Math.sin((float)Math.PI*(moveMent.getOri()+90f)/180f));
                            }

                            @Override
                            public void finish() {

                            }
                        });
                moveAnimation.startAnimate();
                setState(WORKING);
            }

            @Override
            public void onObjAction(Coordinate coordinate,MoveMent moveMent) {
                super.onObjAction(coordinate,moveMent);
                MatrixState.translate(coordinate.getx(),coordinate.gety(),coordinate.getz());
                MatrixState.rotate(-coordinate.getOrient(),0f,1f,0f);
                //speed+=acc;
            }

            @Override
            public void onObjDestroy(Coordinate coordinate, MoveMent moveMent) {
                super.onObjDestroy(coordinate, moveMent);
                if(moveAnimation!=null){
                    Log.i("test","stop");
                    moveAnimation.stopAnimate();
                }
                setState(DEAD);
            }
        };

    }

}
