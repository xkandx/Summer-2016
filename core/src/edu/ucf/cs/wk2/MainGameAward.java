package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Seminar on 6/29/2016.
 */
public class MainGameAward implements Screen{

    GameClass game;
    SpriteBatch batch;
    OrthographicCamera camera;
    int windowW, windowH;
    int border = 5;
    boolean isPaused = false;
    float r,g,b;
    Texture bg;

    //character
    LittleRobot robot;
    ArrayList<Bullet> bullet = new ArrayList<Bullet>();                     //bullet
    ArrayList<SpiderRobot> spiders = new ArrayList<SpiderRobot>();      //spider
    ArrayList<MovingWall> wall = new ArrayList<MovingWall>();           //walls
    ArrayList<MovingWall> wallDown = new ArrayList<MovingWall>();
    float timer = 0;

    //score
    Trophies trophy;
    int kills = 0;
    int count = 0;

    ArrowControl arrow;
    float arrowX, arrowY;

    float time = 0;

    Rectangle mouse;

    //sound effects
    Sound laser;
    Sound crawlingSpider;
    Sound collide;

    public MainGameAward(GameClass game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        windowW = 800;
        windowH = 700;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, windowW, windowH);

        bg = new Texture("blueCircuitBoard.jpg");

        robot = new LittleRobot(200, 100);

        spiders.add(new SpiderRobot((float)Math.random()*bg.getWidth(),(float)Math.random()*bg.getHeight()));
        wall.add(new MovingWall(true, -10, (float) Math.random() * bg.getHeight() - 100));
        wallDown.add(new MovingWall(false, (float)Math.random()*bg.getWidth()-100, bg.getHeight()+10));

        trophy = new Trophies((float)Math.random()*500,(float)Math.random()*500);

        arrow = new ArrowControl();

        mouse = new Rectangle(-1000, -1000, 6, 6);

        SoundManage.loadMainGame();
        laser = SoundManage.laser;
        crawlingSpider = SoundManage.spider;
        collide = SoundManage.collide;


    }

    @Override
    public void render(float delta) {

        time += delta;

        r = (float)Math.random();
        b = (float)Math.random();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera();
        score();

        batch.begin();
        batch.draw(bg, 0,0);
        for(Bullet b: bullet)
            b.draw(batch);
        for(SpiderRobot s: spiders)
            s.draw(batch);
        for(MovingWall w: wall)
            w.draw(batch);
        for(MovingWall w: wallDown)
            w.draw(batch);
        robot.draw(batch);
        if(GameClass.onScreenControls)
            arrow.draw(batch,arrowX,arrowY);
        if(kills > 20)
            trophy.draw(batch);
        batch.end();

        play();

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

    public void play(){
        if(!isPaused) {
            moveRobot();
            spider();
            for (Bullet b : bullet)
                b.update();
            for(int x = 0; x < 3; x++)
                shoot(x);
            deleteBullet();
            for(MovingWall w: wall)
                w.toRight(1);
            for(MovingWall w: wallDown)
                w.down(1);
            wall();
        }
        if(GameClass.onScreenControls)
            arrowPos();
        window();
    }

    public void moveRobot(){//moving with arrows or WSAD keys without getting out of bound
        if((Gdx.input.isKeyPressed(Input.Keys.W) || arrow.getUp().getBoundingRectangle().overlaps(mouse)) && robot.getY() < bg.getHeight()-robot.robotS.getHeight()){
            robot.turnBack();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.S) || arrow.getDown().getBoundingRectangle().overlaps(mouse)) && robot.getY() > 0){
            robot.turnFront();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.A) || arrow.getLeft().getBoundingRectangle().overlaps(mouse)) && robot.getX() > 0){
            robot.turnLeft();
        }
        if((Gdx.input.isKeyPressed(Input.Keys.D) || arrow.getRight().getBoundingRectangle().overlaps(mouse)) && robot.getX() < bg.getWidth()-robot.robotS.getWidth()){
            robot.turnRight();
        }
    }

    public void spider(){
        for(int x = 0; x < spiders.size(); x++) {
            if (spiders.get(x).getX() > robot.getX()) {
                spiders.get(x).right();
            }
            if (spiders.get(x).getX() < robot.getX()) {
                spiders.get(x).left();
            }
            if (spiders.get(x).getY() > robot.getY()) {
                spiders.get(x).up();
            }
            if (spiders.get(x).getY() < robot.getY()) {
                spiders.get(x).down();
            }
            if(spiders.get(x).getX() < 0 || spiders.get(x).getX() > bg.getWidth()
                || spiders.get(x).getY() < 0 || spiders.get(x).getY() > bg.getHeight()){
                spiders.remove(x);
            }
        }
        createSpider();
        hitSpider();
    }

    public void createSpider(){
        if(spiders.size() < 15){
            spiders.add(new SpiderRobot((int)(Math.random()*bg.getWidth()),(int)(Math.random()*bg.getHeight())));
        }
    }

    public void hitSpider(){
        for(int x = bullet.size()-1; x >= 0; x--) {
            boolean hit = false;
            for (int i = spiders.size()-1; i >= 0; i--) {
                if (bullet.get(x).bulletS.getBoundingRectangle().overlaps(spiders.get(i).spiderS.getBoundingRectangle())) {
                    hit = true;
                    spiders.remove(i);
                    kills++;
                }
            }
            if(hit){
                bullet.remove(x);
            }
        }
    }

    public void shoot(int x){
        if(Gdx.input.isTouched(x)) {//setting mouse position to check if the rectangle overlaps the arrows
            Vector3 touch3 = new Vector3(Gdx.input.getX(x), Gdx.input.getY(x), 0);
            camera.unproject(touch3);
            mouse.x = touch3.x - 3;
            mouse.y = touch3.y - 3;
            if (!(arrow.getDown().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getUp().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getRight().getBoundingRectangle().overlaps(mouse) ||
                    arrow.getLeft().getBoundingRectangle().overlaps(mouse))){
                if (bullet.size() < 20) {
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

    public void score(){
        if(robot.robotS.getBoundingRectangle().overlaps(trophy.sprite.getBoundingRectangle())){
            trophy = new Trophies((float)Math.random()*(bg.getWidth()-trophy.getWidth()), (float)Math.random()*(bg.getHeight()-trophy.getHeight()));
            kills = 0;
            count ++;
        }
    }

    public void deleteBullet(){
        for(int i = 0; i < wall.size(); i++) {
            for (int x = 0; x < bullet.size(); x++) {
                if (bullet.get(x).getX() > bg.getWidth()
                        || bullet.get(x).getX() < 0
                        || bullet.get(x).getY() > bg.getHeight()
                        || bullet.get(x).getY() < 0
                        || bullet.get(x).bulletS.getBoundingRectangle().overlaps(wall.get(i).sprite.getBoundingRectangle())) {
                    bullet.remove(x);
                }
            }
        }
        for(int i = 0; i < wallDown.size(); i++) {
            for (int x = 0; x < bullet.size(); x++) {
                if (bullet.get(x).getX() > bg.getWidth()
                        || bullet.get(x).getX() < 0
                        || bullet.get(x).getY() > bg.getHeight()
                        || bullet.get(x).getY() < 0
                        || bullet.get(x).bulletS.getBoundingRectangle().overlaps(wallDown.get(i).sprite.getBoundingRectangle())) {
                    bullet.remove(x);
                }
            }
        }
    }

    public void wall(){
        for(int i = 0; i < wall.size(); i++) {
            if (robot.robotS.getBoundingRectangle().overlaps(wall.get(i).sprite.getBoundingRectangle())
                    && wall.get(i).getX() < bg.getWidth()-(robot.getWidth()*2) ) {
                robot.setPosition(wall.get(i).getX()+wall.get(i).sprite.getWidth(),robot.getY());
            }
        }
        for(int i = 0; i < wallDown.size(); i++) {
            if (robot.robotS.getBoundingRectangle().overlaps(wallDown.get(i).sprite.getBoundingRectangle())
                    && wallDown.get(i).getY() > (robot.getHeight()*2) ) {
                robot.setPosition(robot.getX(),wallDown.get(i).getY()-robot.getHeight());
            }
        }
        deleteWall();
        createWall();
    }

    public void deleteWall(){
        for(int i =  wall.size()-1; i >= 0; i--){
            if (wall.get(i).getX() > bg.getWidth())
                wall.remove(i);
        }
        for(int i =  wallDown.size()-1; i >= 0; i--) {
            if (wallDown.get(i).getY() < -10) {
                wallDown.remove(i);
            }
        }
    }

    public void createWall(){
        if(timer + 4 < time) {
            if (wall.size() < 4) {//time: for the final score
                wall.add(new MovingWall(true, -10, (float) Math.random() * bg.getHeight() - 100));
            }
            if (wallDown.size() < 4 ){
                wallDown.add(new MovingWall(false, (float)Math.random()*bg.getWidth()-100, bg.getHeight()+10));
            }
            timer = time;
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

    public void camera(){
        batch.setProjectionMatrix(camera.combined);
        camera.position.x = robot.getX() + robot.robotS.getWidth()/2;
        camera.position.y = robot.getY() + robot.robotS.getHeight()/2;
        if(camera.position.x < camera.viewportWidth/2-border){
            camera.position.x = camera.viewportWidth/2-border;
        }
        if(camera.position.x > bg.getWidth() - camera.viewportWidth/2+border){
            camera.position.x = bg.getWidth() - camera.viewportWidth/2+border;
        }
        if(camera.position.y < camera.viewportHeight/2-border){
            camera.position.y = camera.viewportHeight/2-border;
        }
        if(camera.position.y > bg.getHeight() - camera.viewportHeight/2+border){
            camera.position.y = bg.getHeight() - camera.viewportHeight/2+border;
        }
        camera.update();
    }

    public void window(){//check: index
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q) || count > 5){//to quit
            game.setScreen(new GameOver(game, time, "Time: "));
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
}
