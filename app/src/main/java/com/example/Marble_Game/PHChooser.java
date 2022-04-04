package com.example.Marble_Game;


/**
 * Created by g3863 on 2017/10/15.
 */

public class PHChooser {
    private static workProcess process;

    public enum LEVEL{
        EASY,NORMAL,HARD
    }
    public PHChooser(LEVEL lv){
        switch (lv){
            case EASY:
                process = SimplePH;
                break;
            case NORMAL:
                process = NormalPH;
                break;
            case HARD:
                process = HardlePH;
                break;
            default:
                break;
        }
    }

    private workProcess SimplePH = new workProcess() {
        @Override
        public void operate(int i) {
            switch (i){
                case 60:
                    break;
                case 30:
                    break;
            }
        }
    };

    private workProcess NormalPH = new workProcess() {
        @Override
        public void operate(int i) {
            switch (i){
                case 60:
                    break;
                case 30:
                    break;
            }
        }
    };

    private workProcess HardlePH = new workProcess() {
        @Override
        public void operate(int i) {
            switch (i){
                case 60:
                    break;
                case 30:
                    break;
            }
        }
    };

    private interface workProcess{
        void operate(int i);
    }
}
