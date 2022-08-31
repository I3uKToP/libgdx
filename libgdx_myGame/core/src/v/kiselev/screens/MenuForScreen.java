package v.kiselev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import v.kiselev.MyCoolGame;

public class MenuForScreen implements Screen {

    private final MyCoolGame game;

    private final SpriteBatch batch;

    private final Texture texture;

    private final Rectangle rectangle;

    private final ShapeRenderer shape;

    private final Music music;

    private final Sound sound;


    public MenuForScreen(MyCoolGame myCoolGame) {
        game = myCoolGame;
        batch = new SpriteBatch();
        texture = new Texture("menu.jpg");
        rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
        shape = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Louis Armstrong - What A Wonderful World (megasongs.me).mp3"));
        music.setLooping(true);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("music/awolnation-run-highsociety-remix.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BROWN);

        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        shape.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shape.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (rectangle.contains(x, y)) {
                dispose();
                game.setScreen(new GameScreen(game));
            } else {
                music.stop();
                sound.play();
            }
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
        texture.dispose();
        music.dispose();
    }
}
