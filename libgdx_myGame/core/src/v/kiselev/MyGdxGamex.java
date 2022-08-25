package v.kiselev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGamex extends ApplicationAdapter {
    SpriteBatch batch;
    int count;
    MyAnimation animation;
    boolean dir;
    float positionX = 0;
    int speed = 50;


    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new MyAnimation("sprite.png", 6, 1, Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        animation.setTime(Gdx.graphics.getDeltaTime());
        int x = Gdx.input.getX() - animation.getFrame().getRegionWidth() / 2;
        int y = Gdx.graphics.getHeight() - Gdx.input.getY() - animation.getFrame().getRegionHeight() / 2;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            speed++;
            count++;
        }
        Gdx.graphics.setTitle("Count click mouse " + count);

        if (positionX > animation.getFrame().getRegionWidth() * 1.5) {
            dir = false;
        }
        if (positionX < 0) {
            dir = true;
        }

        if (dir) {
            positionX += Gdx.graphics.getDeltaTime() * speed;
        } else {
            positionX -= Gdx.graphics.getDeltaTime() * speed;
        }



        batch.begin();
        batch.draw(animation.getFrame(), positionX, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }
}
