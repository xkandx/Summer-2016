package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Seminar on 6/20/2016.
 */
public class MainGameInfinite implements Screen{

    GameClass game;
    SpriteBatch batch;
    OrthographicCamera camera;
    int border = 5;
    int windowW, windowH;
    Texture bg;     //background picture
    boolean isPaused = false;      //pause

    //characters
    LittleRobot robot;
    int lives = 200;
    SpiderRobot spider;
    ArrayList<SpiderRobot> spiders = new ArrayList<SpiderRobot>();

    int kills = 0; //score: number of spiders killed

    ArrayList<Bullet> bullet = new ArrayList<Bullet>();

    //border color
    float r = (float)Math.random();
    float g = (float)Math.random();
    float b = (float)Math.random();

    //arrows for androids
    ArrowControl arrow;
    float arrowX, arrowY;

    //click
    Rectangle mouse;

    //sound effects
    Sound laser;
    Sound crawlingSpider;
    Sound collide;

    public MainGameInfinite(GameClass theGame){
        game = theGame;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        windowW = 800;
        windowH = 700;

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,windowW,windowH);

        //robot
        robot = new LittleRobot(windowW/2,windowH/2);
        //robot.setSize(windowW/500);

        //spider
        spider = new SpiderRobot(windowW,windowH);
        spiders.add(spider);

        //background
        bg = new Texture("circuitBoard.jpg");

        arrow = new ArrowControl();

        mouse = new Rectangle(-1000, -1000, 6, 6);

        SoundManage.loadMainGame();
        laser = SoundManage.laser;
        crawlingSpider = SoundManage.spider;
        collide = SoundManage.collide;
    }

    @Override
    public void render(float delta) {

        r = (float)Math.random();
        g = (float)Math.random();
        b = (float)Math.random();

        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera();

        batch.begin();
        batch.draw(bg, 0, 0);     //drawing background
        for(SpiderRobot s: spiders)
            s.draw(batch);     //drawing spider
        robot.draw(batch);      //drawing the robot
        for(Bullet b: bullet)
            b.draw(batch);
        if(GameClass.onScreenControls)
            arrow.draw(batch,arrowX,arrowY);
        batch.end();

        if (!isPaused) {
            moveRobot();
            spiderRun();
            for (Bullet b : bullet)
                b.update();
            hitSpider();
            deleteBullet();
            for(int x = 0; x < 3; x++)
                shoot(x);
        }
        if(GameClass.onScreenControls)
            arrowPos();
        window();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        bg.dispose();
        spiders.clear();
        bullet.clear();
        laser.dispose();
        batch.dispose();
        crawlingSpider.dispose();
    }

    //moving the robot with WSAD keys
    public void moveRobot(){
        if((Gdx.input.isKeyPressed(Input.Keys.W) || arrow.getUp().getBoundingRectangle().overlaps(mouse))
                && robot.getY() < bg.getHeight()-robot.robotS.getHeight()){
            robot.turnBack();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.S) || arrow.getDown().getBoundingRectangle().overlaps(mouse))
                && robot.getY() > 0){
            robot.turnFront();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.A) || arrow.getLeft().getBoundingRectangle().overlaps(mouse))
                && robot.getX() > 0){
            robot.turnLeft();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.D) || arrow.getRight().getBoundingRectangle().overlaps(mouse))
                && robot.getX() < bg.getWidth()-robot.robotS.getWidth()){
            robot.turnRight();
        }
    }

    public void spiderRun(){
        for(int x = 0; x < spiders.size(); x++) {
            if (spiders.get(x).getX() > robot.getX()) {
                spiders.get(x).left();
                if (robot.robotS.getBoundingRectangle().overlaps(spiders.get(x).spiderS.getBoundingRectangle())) {
                    robot.robotS.translateX(-10);
                    lives--;
                    collide.play();
                }
            }
            if (spiders.get(x).getX() < robot.getX()) {
                spiders.get(x).right();
                if (robot.robotS.getBoundingRectangle().overlaps(spiders.get(x).spiderS.getBoundingRectangle())) {
                    robot.robotS.translateX(10);
                    lives--;
                    collide.play();
                }
            }
            if (spiders.get(x).getY() > robot.getY()) {
                spiders.get(x).down();
                if (robot.robotS.getBoundingRectangle().overlaps(spiders.get(x).spiderS.getBoundingRectangle())) {
                    robot.robotS.translateY(-10);
                    lives--;
                    collide.play();
                }
            }
            if (spiders.get(x).getY() < robot.getY()) {
                spiders.get(x).up();
                if (robot.robotS.getBoundingRectangle().overlaps(spiders.get(x).spiderS.getBoundingRectangle())) {
                    robot.robotS.translateY(10);
                    lives--;
                    collide.play();
                }
            }
        }
    }

    public void hitSpider(){
        for(int x = bullet.size()-1; x >= 0; x--) {
            boolean hit = false;
            for (int i = spiders.size()-1; i >= 0; i--) {
                if (bullet.get(x).bulletS.getBoundingRectangle().overlaps(spiders.get(i).spiderS.getBoundingRectangle())) {
                    hit = true;
                    spiders.remove(i);
                    for(int j = 0; j < 2; j++){
                        spiders.add(new SpiderRobot((int)(Math.random()*bg.getWidth()),(int)(Math.random()*bg.getHeight())));
                        crawlingSpider.play();
                    }
                }
            }
            if(hit){
                bullet.remove(x);
                kills++;
            }
        }
    }

    //bullets: max 50 within the region of the background picture
    //spider: new spiders at random spot
    public void shoot(int x){
        //setting mouse position to check if the rectangle overlaps the arrows
        if(Gdx.input.isTouched(x)) {
            Vector3 touch3 = new Vector3(Gdx.input.getX(x), Gdx.input.getY(x), 0);
            camera.unproject(touch3);
            mouse.x = touch3.x - 3;
            mouse.y = touch3.y - 3;
            if (!(arrow.getDown().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getUp().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getRight().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getLeft().getBoundingRectangle().overlaps(mouse))){
                if (bullet.size() < 50) {
                    if (Gdx.input.isTouched(x)) {
                        Bullet b = new Bullet();
                        b.setPosition(robot);
                        b.shoot(robot.getX() + robot.robotS.getWidth() / 2, robot.getY() + 30, touch3.x, touch3.y);
                        bullet.add(b);
                        laser.play();
                        //change the direction of the robot on, depend on the direction of shooting
                        float w = Math.abs(touch3.x - (robot.getX() + robot.robotS.getWidth() / 2));
                        float h = Math.abs(touch3.y - (robot.getY() + robot.robotS.getHeight()/2));
                        if (w > h) {
                            if (touch3.x > robot.getX() + robot.robotS.getWidth() / 2) {
                                robot.animation = 4;//face right
                            } else {
                                robot.animation = 3;//face left
                            }
                        } else {
                            if (touch3.y > robot.getY()) {
                                robot.animation = 1;//face up
                            } else {
                                robot.animation = 2;//face down
                            }
                        }
                    }
                }
            }
        }
        if(!Gdx.input.isTouched()){
            mouse.setPosition(-1000,-1000);
        }
    }

    public void deleteBullet(){
        for(int x = 0; x < bullet.size(); x++){
            if(bullet.get(x).getX() > bg.getWidth()
                    || bullet.get(x).getX() < 0
                    || bullet.get(x).getY() > bg.getHeight()
                    || bullet.get(x).getY() < 0){
                bullet.remove(x);
            }
        }
    }

    //update the camera
    public void camera(){
        batch.setProjectionMatrix(camera.combined);
        camera.position.x = robot.getX() + robot.robotS.getWidth()/2;
        camera.position.y = robot.getY() + robot.robotS.getHeight()/2;
        if(camera.position.x < windowW/2-border){
            camera.position.x = windowW/2-border;
        }
        if(camera.position.x > bg.getWidth()- windowW/2+border){
            camera.position.x = bg.getWidth()- windowW/2+border;
        }
        if(camera.position.y < windowH/2-border){
            camera.position.y = windowH/2-border;
        }
        if(camera.position.y > bg.getHeight() - windowH/2+border){
            camera.position.y = bg.getHeight() - windowH/2+border;
        }
        camera.update();
    }

    //change the screen, exit or pause the game
    public void window(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)//to quit
                || lives < 0
                || robot.getX() > bg.getWidth()+10
                || robot.getX() < 0 - 10
                || robot.getY() > bg.getHeight()
                || robot.getY() < 0 - 10){
            game.setScreen(new GameOver(game, kills, "Score: "));
            dispose();
        }//to exit
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }//to pause
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            isPaused = !isPaused;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            GameClass.onScreenControls = !GameClass.onScreenControls;
        }
    }

    public void arrowPos() {
        arrowX = robot.getX() - windowW / 2.25f;
        arrowY = robot.getY() - windowH / 2.35f;
        if (arrowX < windowW/25) {
            arrowX = windowW/25 - border;
        }
        if (arrowX > bg.getWidth() - (windowW-windowW/27)) {
            arrowX = bg.getWidth() - windowW + windowW/25;
        }
        if (arrowY < windowH/25) {
            arrowY = windowH/25 - border;
        }
        if (robot.getY()+20 > bg.getHeight() - windowH/2) {
            arrowY = bg.getHeight() - windowH + windowH/25;
        }
    }
}
