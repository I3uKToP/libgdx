package v.kiselev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import v.kiselev.MyAnimation;
import v.kiselev.MyCoolGame;

public class GameScreen implements Screen {

    private final MyCoolGame game;

    private final SpriteBatch batch;

    private int count;
    private final MyAnimation animation;
    private boolean dir;
    private float positionX = 0;
    private int speed = 100;

    public GameScreen(MyCoolGame game) {
        this.game = game;
        batch = new SpriteBatch();
        animation = new MyAnimation("sprite.png", 6, 1, Animation.PlayMode.LOOP);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        animation.setTime(Gdx.graphics.getDeltaTime());
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            speed++;
            count++;
        }
        Gdx.graphics.setTitle("Count click mouse " + count);

        if (positionX > animation.getFrame().getRegionWidth() * 1.5) {
            dir = false;
            animation.getFrame().flip(true, false);
        }
        if (positionX < 0) {
            dir = true;
            animation.getFrame().flip(true, false);
        }

        if (dir) {
            positionX += Gdx.graphics.getDeltaTime() * speed;
        } else {
            positionX -= Gdx.graphics.getDeltaTime() * speed;
        }


        batch.begin();
        batch.draw(animation.getFrame(), positionX, 0);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            dispose();
            game.setScreen(new MenuForScreen(game));
        }
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
        batch.dispose();
    }
}
