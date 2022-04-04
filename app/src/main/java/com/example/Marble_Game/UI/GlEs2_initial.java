package com.example.Marble_Game.UI;

/**
 * Created by g3863 on 2017/8/27.
 */

public class GlEs2_initial {

    private int enermy_cou = 0;
    private int marble_cou = 0;
    private int skl_cou = 0;
    private int break_cou = 0;

    public void para_std(){
        enermy_cou = 0;
        marble_cou = 0;
        skl_cou = 0;
        break_cou = 0;
    }

    public void para_std(int ecou, int mcou, int scou, int bcou){
        enermy_cou = ecou;
        marble_cou = mcou;
        skl_cou = scou;
        break_cou = bcou;
    }

    //parameter set
    public void setEnermy_cou( int count ){
        enermy_cou = count;
    }

    public void setMarble_cou( int count ){
        marble_cou = count;
    }

    public void setSkl_cou( int count ){
        skl_cou = count;
    }

    public void setBreak_cou( int count ){
        break_cou = count;
    }

    //parameter read
    public int getEnermy_cou(){
        return enermy_cou;
    }

    public int getMarble_cou(){
        return marble_cou;
    }

    public int getSkl_cou(){
        return skl_cou;
    }

    public int getBreak_cou(){
        return break_cou;
    }

    //increment
    public void enermy_incre(){
        enermy_cou++;
    }

    public void marble_incre(){
        marble_cou++;
    }

    public void skl_incre(){
        skl_cou++;
    }

    public void break_incre(){
        break_cou++;
    }

}
