package v.kiselev;


import com.badlogic.gdx.physics.box2d.*;
import v.kiselev.screens.GameScreen;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if(tmpA.equals("hero") && tmpB.equals("vasya")) {
                GameScreen.bodies.add(b.getBody());
            }

            if(tmpB.equals("hero") && tmpA.equals("vasya")) {
                GameScreen.bodies.add(a.getBody());
            }

            if(tmpA.equals("hero") && tmpB.equals("wall")) {
                GameScreen.isCanJump = true;
            }

            if(tmpB.equals("hero") && tmpA.equals("wall")) {
                GameScreen.isCanJump = true;
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

            if(tmpA.equals("hero") && tmpB.equals("wall")) {
                GameScreen.isCanJump = false;
            }

            if(tmpB.equals("hero") && tmpA.equals("wall")) {
                GameScreen.isCanJump = false;
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
