package v.kiselev;


import com.badlogic.gdx.physics.box2d.*;
import v.kiselev.screens.GameScreen;

public class MyContactListener implements ContactListener {

    private int count;

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("hero") && tmpB.equals("vasya")) {
                GameScreen.bodies.add(b.getBody());
            }

            if (tmpB.equals("hero") && tmpA.equals("vasya")) {
                GameScreen.bodies.add(a.getBody());
            }

            if (tmpA.equals("hero") && tmpB.equals("wall")) {
                count++;
                System.out.println(count);
            }

            if (tmpB.equals("hero") && tmpA.equals("wall")) {
                count++;
                System.out.println(count);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("hero") && tmpB.equals("wall")) {
                count--;
            }

            if (tmpB.equals("hero") && tmpA.equals("wall")) {
                count--;
            }
        }
    }

    public boolean isOnGround() {
        return count > 0;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
