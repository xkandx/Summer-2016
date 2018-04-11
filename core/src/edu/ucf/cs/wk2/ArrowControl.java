package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Seminar on 6/23/2016.
 */
public class ArrowControl {

    int up = 0, down = 1, right = 2, left = 3;
    Texture[] arrows = new Texture[4];
    Sprite[] sprite = new Sprite[4];
    int spacing = 82;

    public ArrowControl(){
        arrows[up] = new Texture("upArrow.png");
        arrows[down] = new Texture("downArrow.png");
        arrows[right] = new Texture("rightArrow.png");
        arrows[left] = new Texture("leftArrow.png");
        sprite[up] = new Sprite(arrows[up]);
        sprite[down] = new Sprite(arrows[down]);
        sprite[right] = new Sprite(arrows[right]);
        sprite[left] = new Sprite(arrows[left]);
    }

    public void draw(SpriteBatch batch, float x, float y){
        sprite[up].setPosition(x+spacing,y+spacing);
        sprite[down].setPosition(x+spacing,y);
        sprite[left].setPosition(x,y);
        sprite[right].setPosition(x+spacing*2,y);
        sprite[up].draw(batch);
        sprite[down].draw(batch);
        sprite[left].draw(batch);
        sprite[right].draw(batch);
    }

    public Sprite getUp(){
        return sprite[up];
    }

    public Sprite getDown(){
        return sprite[down];
    }

    public Sprite getRight(){
        return sprite[right];
    }

    public Sprite getLeft(){
        return sprite[left];
    }

}
