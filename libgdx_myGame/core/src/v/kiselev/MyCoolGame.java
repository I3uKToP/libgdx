package v.kiselev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import v.kiselev.screens.MenuForScreen;

public class MyCoolGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuForScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
