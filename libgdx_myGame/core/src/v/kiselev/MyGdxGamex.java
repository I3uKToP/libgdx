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

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		animation = new MyAnimation("sprite.png", 14, 5, Animation.PlayMode.LOOP);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		animation.setTime(Gdx.graphics.getDeltaTime());
		int x = Gdx.input.getX() - animation.getFrame().getRegionWidth()/2;
		int y = Gdx.graphics.getHeight() - Gdx.input.getY() - animation.getFrame().getRegionHeight()/2;

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			count++;
		}
		Gdx.graphics.setTitle("Count click mouse " + count);


		if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			dir = true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			dir = false;
		}

		if (!animation.getFrame().isFlipX() && !dir) {
			animation.getFrame().flip(true, false);
		}
		if (animation.getFrame().isFlipX() && dir) {
			animation.getFrame().flip(false, false);
		}
		batch.begin();
//		batch.draw(regions0[0][0],0,0);
//		batch.draw(animation.getKeyFrame(time),0,0);
		batch.draw(animation.getFrame(),0,0);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		animation.dispose();
	}
}
