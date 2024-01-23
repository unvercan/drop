package tr.unvercanunlu.games.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final DropGame game;

    final OrthographicCamera camera;

    public MainMenuScreen(DropGame game) {
        this.game = game;

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        // nothing
    }

    @Override
    public void render(float v) {
        // clear screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // update camera position automatically
        camera.update();

        // render according to camera coordinate system
        game.spriteBatch.setProjectionMatrix(camera.combined);

        // start drawing
        game.spriteBatch.begin();

        // prompts
        game.bitmapFont.draw(game.spriteBatch, "Welcome to Drop!!! ", 100, 150);
        game.bitmapFont.draw(game.spriteBatch, "Tap anywhere to begin!", 100, 100);

        // end drawing
        game.spriteBatch.end();

        // handling inputs
        if (Gdx.input.isTouched()) {
            // creating game screen
            DropGameScreen dropGameScreen = new DropGameScreen(game);

            // activate game screen
            game.setScreen(dropGameScreen);
        }
    }

    @Override
    public void resize(int i, int i1) {
        // nothing
    }

    @Override
    public void pause() {
        // nothing
    }

    @Override
    public void resume() {
        // nothing
    }

    @Override
    public void hide() {
        // nothing
    }

    @Override
    public void dispose() {
       // nothing
    }
}
