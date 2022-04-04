package com.example.Marble_Game.data;

/**
 * Created by g3863 on 2017/10/14.
 */

public class Skill {
    private int ballCount;
    private int sklTime;
    private int stRange;
    private int cost;
    private int wtTime;
    private int atk;
    private int dft;

    private Skill(int a, int b, int c, int d, int e, int f, int g){
        this.ballCount = a;
        this.sklTime = b;
        this.stRange = c;
        this.cost = d;
        this.wtTime = e;
        this.atk = f;
        this.dft = g;
    }
}
