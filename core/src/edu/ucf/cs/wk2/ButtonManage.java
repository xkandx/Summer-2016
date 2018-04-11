package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Seminar on 6/29/2016.
 */
public class ButtonManage {

    public static Texture start;
    public static Texture infinite;
    public static Texture timing;
    public static Texture quit, playAgain;

    public static void loadMenu(){
        start = new Texture("startBlackBG.png");
        quit = new Texture("quit.png");
    }

    public static void loadModes(){
        infinite = new Texture("infinite.png");
        timing = new Texture("timing.png");
    }

    public static void loadGameOver(){
        quit = new Texture("quit.png");
        playAgain = new Texture("playAgain.png");

    }

}
