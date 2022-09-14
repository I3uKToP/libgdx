package v.kiselev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import v.kiselev.physic.GamePhysic;
import v.kiselev.MyAnimation;
import v.kiselev.MyCoolGame;

import java.util.ArrayList;

import static v.kiselev.physic.GamePhysic.PPM;

public class GameScreen implements Screen {

    private final MyCoolGame game;

    private final SpriteBatch batch;

    private final OrthographicCamera camera;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final int[] bg;

    private final int[] l1;

    private final GamePhysic physic;

    private final Body body;

    private final Rectangle heroSize;

    private final MyAnimation animation;

    public static ArrayList<Body> bodies;

    private final BitmapFont font;


    public GameScreen(MyCoolGame game) {
        this.game = game;
        batch = new SpriteBatch();
        animation = new MyAnimation("sprite.png", 6, 1, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("map/myMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera.zoom = 0.2f;

        bg = new int[1];
        bg[0] = map.getLayers().getIndex("back");

        l1 = new int[1];

        physic = new GamePhysic();

        bodies = new ArrayList<>();

        Array<RectangleMapObject> objects = map.getLayers()
                .get("objects").getObjects().getByType(RectangleMapObject.class);

        for (RectangleMapObject object : objects) {
            physic.addObject(object);
        }

        RectangleMapObject hero = (RectangleMapObject) map.getLayers()
                .get("settings").getObjects().get("hero");
        heroSize = hero.getRectangle();

        body = physic.addObject(hero);

        l1[0] = map.getLayers().getIndex("layer1");

        font = new BitmapFont();
        font.setColor(Color.RED);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) body.applyForceToCenter(new Vector2(-1.5f, 0), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) body.applyForceToCenter(new Vector2(1.5f, 0), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && physic.listener.isOnGround())
            body.applyForceToCenter(new Vector2(0, 5f), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) camera.zoom += 0.01;
        if (Gdx.input.isKeyJustPressed(Input.Keys.O) && camera.zoom > 0) camera.zoom -= 0.01;

        camera.position.x = body.getPosition().x * PPM;
        camera.position.y = body.getPosition().y * PPM;

        animation.setTime(Gdx.graphics.getDeltaTime());

        camera.update();
        heroSize.x = body.getPosition().x - heroSize.getWidth() / 2;
        heroSize.y = body.getPosition().y - heroSize.getHeight() / 2;

        if (body.getLinearVelocity().x < 0 && !animation.getFrame().isFlipX()) {
            animation.getFrame().flip(true, false);
        }
        if (body.getLinearVelocity().x > 0 && animation.getFrame().isFlipX()) {
            animation.getFrame().flip(true, false);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuForScreen(game));
        }
        float x = Gdx.graphics.getWidth() / 2f - heroSize.getWidth() / 2 / camera.zoom;
        float y = Gdx.graphics.getHeight() / 2f - heroSize.getHeight() / 2 / camera.zoom;

        mapRenderer.setView(camera);
        mapRenderer.render(bg);
        mapRenderer.render(l1);


        batch.begin();
        batch.draw(animation.getFrame(), x, y, heroSize.getWidth() * 5, heroSize.getHeight() * 5);
        batch.end();

//        TextureRegion imgT = animation.getFrame();
//        batch.begin();
//        Array<Body> ab = physic.getBodies("wall");
//        for (Body b : ab) {
//
//        }
        physic.debugDraw(camera);
//
//        batch.begin();
//        font.draw(batch, "health", 0, 30);
//        batch.end();

        for (Body body1 : bodies) {
            physic.deleteBody(body1);
        }
        bodies.clear();

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
        font.dispose();
    }
}
