package edu.ucf.cs.wk2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Seminar on 6/29/2016.
 */
public class ModeSelector implements Screen{

    GameClass game;
    OrthographicCamera camera;
    SpriteBatch batch;

    Sprite infinite, timing;
    String strInf, strTim;
    BitmapFont bmf;

    Rectangle mouse;

    public ModeSelector(GameClass game){
        this.game = game;
    }

    @Override
    public void show() {
        ButtonManage.loadModes();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        infinite = new Sprite(ButtonManage.infinite);
        infinite.setPosition(camera.viewportWidth/2-infinite.getWidth()*2f ,camera.viewportHeight/1.5f);
        timing = new Sprite(ButtonManage.timing);
        timing.setPosition(camera.viewportWidth/2+infinite.getWidth(), camera.viewportHeight/1.5f);
        strInf = "Infinite spiders \nkilling";
        strTim = "Kill spiders to \ncollect stars";
        bmf = new BitmapFont(Gdx.files.internal("txt2.fnt"),Gdx.files.internal("txt2.png"),false);

        mouse = new Rectangle(-1000, -1000, 6, 6);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera();

        batch.begin();
        infinite.draw(batch);
        bmf.draw(batch,strInf,infinite.getX(),infinite.getY()-infinite.getHeight());
        timing.draw(batch);
        bmf.draw(batch, strTim, timing.getX(),timing.getY()-timing.getHeight());
        batch.end();

        mouse();
        window();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bmf.dispose();
    }

    public void camera(){
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    public void mouse(){
        if(Gdx.input.isTouched()){
            Vector3 touch3 = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touch3);
            mouse.x = touch3.x - 3;
            mouse.y = touch3.y - 3;
        }
    }

    public void window(){
        if(mouse.overlaps(infinite.getBoundingRectangle())){
            game.setScreen(new MainGameInfinite(game));
            dispose();
        }
        if(mouse.overlaps(timing.getBoundingRectangle())){
            game.setScreen(new MainGameAward(game));
            dispose();
        }
    }
}
