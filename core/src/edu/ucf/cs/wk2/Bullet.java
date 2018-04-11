package edu.ucf.cs.wk2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Seminar on 6/22/2016.
 */
public class Bullet {


    Texture bullet;
    public Sprite bulletS;

    double vx = 4,
        vy = 4;
    int speed = 5;

    public Bullet (){
        bullet = new Texture("bullet.png");
        bulletS = new Sprite(bullet);
    }

    public void draw(SpriteBatch batch){
        bulletS.draw(batch);
    }

    public void setPosition(LittleRobot robot){
        bulletS.setPosition(robot.getX()+robot.robotS.getWidth()/2, robot.getY()+30);
    }

    public void shoot(float x1, float y1, float x2, float y2){
        float xd = x2-x1;
        float yd = y2-y1;
        double theta = Math.atan2(yd,xd);
        vx = Math.cos(theta) * speed;
        vy = Math.sin(theta) * speed;
    }

    public void update(){
        bulletS.translateX((float)vx);
        bulletS.translateY((float)vy);
    }

    public float getX(){
        return bulletS.getX();
    }

    public float getY(){
        return bulletS.getY();
    }

}
