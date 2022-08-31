package v.kiselev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import v.kiselev.GamePhysic;
import v.kiselev.MyAnimation;
import v.kiselev.MyCoolGame;

public class GameScreen implements Screen {

    private final MyCoolGame game;

    private final SpriteBatch batch;

    private final OrthographicCamera camera;

    private final TiledMap map;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final float step = 2;


    private final int[] bg;

    private final int[] l1;

    private final GamePhysic physic;

    private final Body body;

    private final Rectangle heroSize;

    private final MyAnimation animation;

    private boolean flip = true;

    public GameScreen(MyCoolGame game) {
        this.game = game;
        batch = new SpriteBatch();
        animation = new MyAnimation("sprite.png", 6, 1, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("map/myMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera.zoom = 0.2f;

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("back");

        l1 = new int[2];

        physic = new GamePhysic();


        Array<RectangleMapObject> objects = map.getLayers()
                .get("objects").getObjects().getByType(RectangleMapObject.class);

        for (RectangleMapObject object : objects) {
            physic.addObject(object);
        }

        RectangleMapObject hero = (RectangleMapObject) map.getLayers()
                .get("settings").getObjects().get("hero");
        heroSize = hero.getRectangle();

        body = physic.addObject(hero);

        l1[0] =map.getLayers().getIndex("layer1");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) body.applyForceToCenter(new Vector2(-10000, 0), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) body.applyForceToCenter(new Vector2(10000, 0), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) camera.position.y -= step;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) camera.position.y += step;

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) camera.zoom += 0.01;
        if (Gdx.input.isKeyJustPressed(Input.Keys.O) && camera.zoom > 0) camera.zoom -= 0.01;

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;

        animation.setTime(Gdx.graphics.getDeltaTime());

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        heroSize.x = body.getPosition().x - heroSize.getWidth() / 2;
        heroSize.y = body.getPosition().y - heroSize.getHeight() / 2;
        batch.begin();


        mapRenderer.setView(camera);
        mapRenderer.render();
        if(flip) {
            animation.getFrame().flip(true,false);
            flip= false;
            System.out.println("flip ");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuForScreen(game));
        }
        batch.draw(animation.getFrame(), heroSize.getX(), heroSize.getY(),
                heroSize.getWidth(), heroSize.getHeight());
        batch.end();
        physic.step();
        physic.debugDraw(camera);

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
