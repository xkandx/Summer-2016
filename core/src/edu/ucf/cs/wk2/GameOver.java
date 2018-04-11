package edu.ucf.cs.wk2;

import com.badlogic.gdx.Game;
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
public class GameOver implements Screen {

    GameClass game;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture spider;
    float score;      //score from the main game
    String str;
    BitmapFont bmf;
    Sprite cont,quit;
    Rectangle click; //mouse
    Sound sound;

    public GameOver(GameClass theGame, float theScore, String type){
        game = theGame;
        score = theScore;
        str = type + score;

    }

    @Override
    public void show() {
        ButtonManage.loadGameOver();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,700,700);
        spider = new Texture("gameOverSpider.png");

        cont = new Sprite(ButtonManage.playAgain);
        cont.setPosition(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/3);
        quit = new Sprite(ButtonManage.quit);

        click = new Rectangle(-1000, -1000, 6, 6); //mouse
        bmf = new BitmapFont(Gdx.files.internal("txt1.fnt"),Gdx.files.internal("txt1.png"),false); //score

        SoundManage.loadGameOver();
        sound = SoundManage.gameOver;
        sound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,.1f,.4f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        bmf.setColor(1,1,1,1);

        batch.begin();
        batch.draw(spider,10,150);
        cont.draw(batch);
        quit.draw(batch);
        bmf.draw(batch, str, 100, 150);
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
        spider.dispose();
        ButtonManage.playAgain.dispose();
        ButtonManage.quit.dispose();
        sound.dispose();
        batch.dispose();
        bmf.dispose();
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new Menu(game));
            dispose();
        }
        //exit the game
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
                click.overlaps(quit.getBoundingRectangle())){
            Gdx.app.exit();
            dispose();
        }
        if(click.overlaps(cont.getBoundingRectangle())){
            game.setScreen(new Menu(game));
            dispose();
        }
    }
}
