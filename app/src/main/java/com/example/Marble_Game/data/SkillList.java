package com.example.Marble_Game.data;

/**
 * Created by g3863 on 2017/10/14.
 */

public class SkillList {
    private void ContiShoot(){
        new DrawCommand(){
            @Override
            public void draw() {

            }
        };
    }

    public interface DrawCommand{
        void draw();
    }
}
