package v.kiselev;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GamePhysic {

    private final World world;

    private final Box2DDebugRenderer debugRenderer;

    public GamePhysic() {
        this.world = new World(new Vector2(0, -9.81f), true);

        this.debugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rectangle = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        if(type.equals("StaticBody")) {
            def.type = BodyDef.BodyType.StaticBody;
        }

        if(type.equals("DynamicBody")) {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape polygon = new PolygonShape();

        def.position.set(rectangle.x + rectangle.getWidth() / 2,
                rectangle.y + rectangle.getHeight() / 2);

        def.gravityScale = (float) object.getProperties().get("GravityScale");

        polygon.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);

        fdef.shape = polygon;
        fdef.friction = 0;
        fdef.density = 1;
        fdef.restitution = (float) object.getProperties().get("Restitution");

        Body body = world.createBody(def);
        body.createFixture(fdef).setUserData("wall");
        polygon.dispose();

        return body;
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
