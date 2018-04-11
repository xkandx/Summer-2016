package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Seminar on 6/21/2016.
 */
public class SpiderRobot {

    Sprite spiderS;

    Texture[] spider = new Texture[4];
    int  down= 0, up = 1, left = 2, right = 3;

    public SpiderRobot(float x, float y){
        spider[down] = new Texture("spiderDown.png");
        spider[up] = new Texture("spiderUp.png");
        spider[left] = new Texture("spiderLeft.png");
        spider[right] = new Texture("spiderRight.png");
        spiderS = new Sprite(spider[down]);
        setPosition(x,y);
    }
    public void setPosition(float x, float y){
        spiderS.setX(x);
        spiderS.setY(y);
        spiderS.setPosition(x,y);
    }

    public void draw(SpriteBatch batch){
        spiderS.draw(batch);
    }

    //moving spider
    public void up(){
        spiderS.setTexture(spider[up]);
        spiderS.translateY(1);
    }

    public void down(){
        spiderS.setTexture(spider[down]);
        spiderS.translateY(-1);
    }

    public void left(){
        spiderS.setTexture(spider[left]);
        spiderS.translateX(-1);
    }

    public void right(){
        spiderS.setTexture(spider[right]);
        spiderS.translateX(1);
    }

    public float getX(){
        return spiderS.getX();
    }

    public float getY(){
        return spiderS.getY();
    }

}
