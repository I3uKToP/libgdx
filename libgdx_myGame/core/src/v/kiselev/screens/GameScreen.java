package v.kiselev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import v.kiselev.MyAnimation;
import v.kiselev.MyCoolGame;

public class GameScreen implements Screen {

    private final MyCoolGame game;

    private final SpriteBatch batch;

    private final OrthographicCamera camera;

    private final TiledMap map;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final float step = 2;

    private final Rectangle cameraBoard;

    private final ShapeRenderer shape;

    private int count;
    private final MyAnimation animation;
    private boolean dir;
    private float positionX = 0;
    private int speed = 100;

    public GameScreen(MyCoolGame game) {
        this.game = game;
        batch = new SpriteBatch();
        animation = new MyAnimation("sprite.png", 6, 1, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("map/myMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        RectangleMapObject point = (RectangleMapObject) map.getLayers()
                .get("objects").getObjects().get("point");
        camera.position.x = point.getRectangle().getX();
        camera.position.y = point.getRectangle().getY();

        RectangleMapObject board = (RectangleMapObject) map.getLayers()
                .get("objects").getObjects().get("board");
        cameraBoard = board.getRectangle();
        shape = new ShapeRenderer();
        camera.zoom=0.5f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) camera.position.x += step;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) camera.position.x -= step;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) camera.position.y -= step;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) camera.position.y += step;

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) camera.zoom += 0.01;
        if (Gdx.input.isKeyJustPressed(Input.Keys.O) && camera.zoom > 0) camera.zoom -= 0.01;

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
        float scale = animation.getFrame().getRegionWidth()/step;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(animation.getFrame(), positionX, 0, scale,scale);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuForScreen(game));
        }
        //for debug
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        shape.rect(cameraBoard.x, cameraBoard.y, cameraBoard.width, cameraBoard.height);
        shape.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
