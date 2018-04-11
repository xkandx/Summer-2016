package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by seminar on 6/20/2016.
 */
public class LittleRobot {

    public Sprite robotS;

    Texture[] robot = new Texture[8];
    int front = 0, back = 1, left = 2, right = 3,
         front1 = 4, back1 = 5, left1 = 6, right1 = 7;
    int count = 0;
    int animation = 1;
    int frameRate = 4;


    public LittleRobot(float x, float y){
        robot[front] = new Texture("robotFront1.png");
        robot[front1] = new Texture("robotFront2.png");
        robot[back] = new Texture("robotBack1.png");
        robot[back1] = new Texture("robotBack2.png");
        robot[left] = new Texture("robotLeft1.png");
        robot[left1] = new Texture("robotLeft2.png");
        robot[right] = new Texture("robotRight1.png");
        robot[right1] = new Texture("robotRight2.png");
        robotS = new Sprite(robot[front]);
        setPosition(x,y);
    }

    public void setPosition(float x, float y){
        robotS.setPosition(x,y);
    }

    public void setSize(float size){
        robotS.setSize(robotS.getWidth()*size, robotS.getHeight()*size);
    }

    public void draw(SpriteBatch batch){
        switch(animation){
            case 1: // back
                if(count%frameRate > 1) {
                    robotS.setTexture(robot[back]);
                }
                else{
                    robotS.setTexture(robot[back1]);
                }
                break;
            case 2: //front
                if(count%frameRate > 1) {
                    robotS.setTexture(robot[front]);
                }
                else{
                    robotS.setTexture(robot[front1]);
                }
                break;
            case 3: //left
                if(count%frameRate > 1) {
                    robotS.setTexture(robot[left]);
                }
                else{
                    robotS.setTexture(robot[left1]);
                }
                break;
            case 4: //right
                if(count%frameRate > 1) {
                    robotS.setTexture(robot[right]);
                }
                else{
                    robotS.setTexture(robot[right1]);
                }
                break;
        }
        count = (count+1)%frameRate;
        robotS.draw(batch);
    }

    //moving the robots
    public void turnBack(){
        robotS.translateY(3);
        animation = 1;
    }

    public void turnFront(){
        robotS.translateY(-3);
        animation = 2;
    }

    public void turnRight(){
        robotS.translateX(3);
        animation = 4;
    }

    public void turnLeft(){
        robotS.translateX(-3);
        animation = 3;
    }

    //return the x and y coordinate
    public float getX(){
       return robotS.getX();
    }

    public float getY(){
        return robotS.getY();
    }

    public float getWidth(){
        return robotS.getWidth();
    }

    public float getHeight(){
        return  robotS.getHeight();
    }

}
