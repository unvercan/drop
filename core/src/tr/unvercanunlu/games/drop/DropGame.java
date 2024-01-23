package tr.unvercanunlu.games.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DropGame extends Game {

    // sprite batch
    SpriteBatch spriteBatch;

    // bitmap font
    BitmapFont bitmapFont;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont();

        MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
        spriteBatch.dispose();
    }
}
