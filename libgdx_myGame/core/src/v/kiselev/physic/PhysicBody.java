package v.kiselev.physic;

import com.badlogic.gdx.math.Vector2;

public class PhysicBody {

    public Vector2 position;

    public Vector2 size;

    public String name;

    public PhysicBody(Vector2 position, Vector2 size, String name) {
        this.position = position;
        this.size = size;
        this.name = name;
    }
}
