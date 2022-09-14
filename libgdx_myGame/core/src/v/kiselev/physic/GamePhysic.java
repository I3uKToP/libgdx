package v.kiselev.physic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import v.kiselev.MyContactListener;

import java.util.Iterator;

public class GamePhysic {

    private final World world;

    private final Box2DDebugRenderer debugRenderer;

    public static final float PPM = 100;

    public final MyContactListener listener;

    public GamePhysic() {
        this.world = new World(new Vector2(0, -9.81f), true);

        this.debugRenderer = new Box2DDebugRenderer();

        listener = new MyContactListener();

        this.world.setContactListener(listener);
    }

    public void deleteBody(Body body) {
        world.destroyBody(body);
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rectangle = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        if (type.equals("StaticBody")) {
            def.type = BodyDef.BodyType.StaticBody;
        }

        if (type.equals("DynamicBody")) {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape polygon = new PolygonShape();

        def.position.set((rectangle.x + rectangle.getWidth() / 2) / PPM,
                (rectangle.y + rectangle.getHeight() / 2) / PPM);

        def.gravityScale = (float) object.getProperties().get("GravityScale");

        polygon.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);

        fdef.shape = polygon;
        fdef.friction = 0.8f;
        fdef.density = 1;
        fdef.restitution = (float) object.getProperties().get("Restitution");

        String name = "";
        if(object.getName() != null) name = object.getName();
        Body body;
        body = world.createBody(def);
        body.setUserData(new PhysicBody(new Vector2(rectangle.x, rectangle.y),
                new Vector2(rectangle.getWidth(), rectangle.getHeight()), name));
        body.createFixture(fdef).setUserData(name);
        if (name != null && name.equals("hero")) {
            polygon.setAsBox(rectangle.getWidth() / 3 / PPM, rectangle.getHeight() / 12 / PPM,
                    new Vector2(0, -rectangle.getWidth() / 2), 0);
            body.createFixture(fdef).setUserData("legs");
            body.setFixedRotation(true);
        }

        polygon.dispose();

        return body;
    }

    public Array<Body> getBodies(String name) {
        Array<Body> ab = new Array<>();
        world.getBodies(ab);
        Iterator<Body> it = ab.iterator();
        while (it.hasNext()) {
            String text = ((PhysicBody)it.next().getUserData()).name;
            if(!text.equals(name)) it.remove();
        }
        return  ab;
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public void step() {
        world.step(1 / 60f, 3, 3);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
