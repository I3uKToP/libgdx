package v.kiselev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {

    private final Texture texture;
    private final Animation<TextureRegion> animation;
    private float time;

    public MyAnimation(String path, int column, int row , Animation.PlayMode playMode) {
        texture = new Texture(path);

        TextureRegion region0 = new TextureRegion(texture);
        int xCnt  = region0.getRegionWidth()/column;
        int yCnt = region0.getRegionHeight()/row;
        TextureRegion[][] regions0 = region0.split(xCnt,yCnt);

        TextureRegion[] regions1 = new TextureRegion[regions0.length*regions0[0].length];
        int cnt = 0;
        for (TextureRegion[] textureRegions : regions0) {
            for (int j = 0; j < regions0[0].length; j++) {
                regions1[cnt++] = textureRegions[j];
            }
        }
        animation = new Animation<>(1/15f,regions1);
        animation.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }

    public TextureRegion getFrame() {
       return animation.getKeyFrame(time);
    }

    public void setTime(float time) {
        this.time += time;
    }
    
    public boolean isAnimationOver() {
        return animation.isAnimationFinished(time);
    }

    public void zeroTime() {
        this.time = 0;
    }

    public void setMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }

    public void dispose() {
        texture.dispose();
    }
}
