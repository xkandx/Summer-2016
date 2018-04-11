package edu.ucf.cs.wk2;

import com.badlogic.gdx.Game;

/**
 * Created by Seminar on 6/20/2016.
 */
public class GameClass extends Game {

    public static boolean onScreenControls;

    public GameClass(boolean onScreen){
        onScreenControls = onScreen;
    }

    @Override
    public void create() {
        this.setScreen(new Menu(this));
    }
}
