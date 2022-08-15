package v.kiselev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGamex extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int count;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("smile.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		int x = Gdx.input.getX() - img.getWidth()/2;
		int y = Gdx.graphics.getHeight() - Gdx.input.getY() - img.getHeight()/2;

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			count++;
		}
		Gdx.graphics.setTitle("Count click mouse " + count);
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
