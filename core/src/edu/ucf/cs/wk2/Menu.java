package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Seminar on 6/20/2016.
 */
public class Menu implements Screen {

    GameClass game;
    SpriteBatch batch;
    OrthographicCamera camera;
    int windowW, windowH;

    Texture bg;
    Sprite start, quit;

    //mouse
    Rectangle click;

    Sound sound;

    public Menu(GameClass theGame){
        game = theGame;
    }

    @Override
    public void show() {
        ButtonManage.loadMenu();
        batch = new SpriteBatch();
        windowW = Gdx.graphics.getWidth();
        windowH = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,windowW,windowH);

        batch = new SpriteBatch();

        bg = new Texture("futuristicCityNight.jpg");
        start = new Sprite(ButtonManage.start);
        start.setPosition(windowW/2-start.getWidth()/2,windowH/1.5f);
        quit = new Sprite(ButtonManage.quit);
        quit.setPosition(windowW-quit.getWidth()*1.3f,quit.getHeight());

        click = new Rectangle(-1000, -1000, 6, 6);

        SoundManage.loadMenu();
        sound = SoundManage.menu;
        sound.play();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(bg,0,30,windowW,windowH);
        start.draw(batch);
        quit.draw(batch);
        batch.end();

        mouseClick();

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
        batch.dispose();
        sound.dispose();
    }

    public void mouseClick(){
        if(Gdx.input.isTouched()){
            Vector3 touch3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touch3);
            click.x = touch3.x - 3;
            click.y = touch3.y - 3;
        }
    }

    public void window(){
        //change the screen
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || click.overlaps(start.getBoundingRectangle())){
            game.setScreen(new ModeSelector(game));
            dispose();
        }
        //exit the game
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || click.overlaps(quit.getBoundingRectangle())){
            Gdx.app.exit();
        }
    }
}
