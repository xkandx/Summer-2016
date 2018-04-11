package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Seminar on 6/30/2016.
 */
public class Trophies {

    public Sprite sprite;

    public Trophies(float x, float y){
        sprite = new Sprite(new Texture("Tstar.png"));
        setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
    }

    public float getWidth(){
        return sprite.getWidth();
    }

    public float getHeight(){
        return sprite.getHeight();
    }
}
