package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Seminar on 6/24/2016.
 */
public class SoundManage {

    public static Sound gameOver;
    public static Sound menu;
    public static Sound laser;
    public static Sound spider;
    public static Sound collide;
//    public static Sound trophy;

    public static void loadGameOver(){
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameOver.wav"));
    }

    public static void loadMenu(){
        menu = Gdx.audio.newSound(Gdx.files.internal("electronicNoise.wav"));
    }

    public static void loadMainGame(){
        laser = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        spider = Gdx.audio.newSound(Gdx.files.internal("spiderCrawling.mp3"));
        collide = Gdx.audio.newSound(Gdx.files.internal("collide.wav"));

    }
}
