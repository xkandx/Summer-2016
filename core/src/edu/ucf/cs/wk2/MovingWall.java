package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Seminar on 6/29/2016.
 */
public class MovingWall {

    public Sprite sprite;

    public MovingWall(boolean vertical, float x, float y){
        if(vertical)
            sprite = new Sprite(new Texture("wallV.png"));
        else{
            sprite = new Sprite(new Texture("wallH.png"));
        }
        setPosition(x,y);
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void toRight(int x){
        sprite.translateX(x);
    }
    public void down(int x){
        sprite.translateY(-x);
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }

}
