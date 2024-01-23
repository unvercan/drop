package tr.unvercanunlu.games.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class DropGameScreen implements Screen {

    // game
    final DropGame game;

    // last created time for rain drops
    long lastRainDropTime;

    // assets
    final Texture rainDropImage;
    final Texture bucketImage;
    final Sound rainDropSound;
    final Music rainMusic;

    // camera
    final OrthographicCamera camera;

    // objects
    final Rectangle bucket;
    final Array<Rectangle> rainDrops;

    // score
    int rainDropsGot = 0;

    public DropGameScreen(DropGame game) {
        this.game = game;

        // assets
        rainDropImage = new Texture(Gdx.files.internal("rainDrop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        rainDropSound = Gdx.audio.newSound(Gdx.files.internal("rainDrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        // Objects
        bucket = new Rectangle();
        bucket.x = (Config.SCREEN_WIDTH / 2) - (Config.BUCKET_WIDTH / 2);
        bucket.y = 0;
        bucket.width = Config.BUCKET_WIDTH;
        bucket.height = Config.BUCKET_HEIGHT;

        rainDrops = new Array<>();
    }

    @Override
    public void show() {
        // nothing
    }

    @Override
    public void render(float delta) {
        // clearing screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // updating camera position automatically
        camera.update();

        // rendering according to camera coordinate system
        game.spriteBatch.setProjectionMatrix(camera.combined);

        // starting to draw
        game.spriteBatch.begin();

        // drawing score
        game.bitmapFont.draw(game.spriteBatch, "Drops Collected: " + rainDropsGot, 0, 480);

        // drawing bucket
        game.spriteBatch.draw(bucketImage, bucket.x, bucket.y);

        // drawing rain drops
        for (Rectangle rainDrop : rainDrops) {
            game.spriteBatch.draw(rainDropImage, rainDrop.x, rainDrop.y);
        }

        // ending to draw
        game.spriteBatch.end();

        // moving bucket left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= Config.BUCKET_SPEED * Gdx.graphics.getDeltaTime();
        }

        // moving bucket right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += Config.BUCKET_SPEED * Gdx.graphics.getDeltaTime();
        }

        // limiting bucket movement
        bucket.x = Math.max(bucket.x, 0);
        bucket.x = Math.min(bucket.x, (Config.SCREEN_WIDTH - Config.BUCKET_WIDTH));

        // creating new rain drops if enough time passed
        if ((TimeUtils.nanoTime() - lastRainDropTime) > 1000000000) {
            spawnRainDrop();
        }

        // looping over rain drops
        Iterator<Rectangle> iterator = rainDrops.iterator();
        while (iterator.hasNext()) {
            Rectangle raindrop = iterator.next();

            // falling rain drop
            raindrop.y -= Config.RAIN_DROP_SPEED * Gdx.graphics.getDeltaTime();

            // removing rain drop if reach to the ground
            if ((raindrop.y + Config.RAIN_DROP_HEIGHT) < 0) {
                iterator.remove();
            }

            // removing rain drop if bucket collects it
            if (raindrop.overlaps(bucket)) {
                iterator.remove();

                rainDropsGot++;

                // playing sound effect
                rainDropSound.play();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
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
        bucketImage.dispose();
        rainDropImage.dispose();
        rainDropSound.dispose();
        rainMusic.dispose();
    }

    void spawnRainDrop() {
        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, (Config.SCREEN_WIDTH - Config.RAIN_DROP_WIDTH));
        rainDrop.y = Config.SCREEN_HEIGHT;
        rainDrop.width = Config.RAIN_DROP_WIDTH;
        rainDrop.height = Config.RAIN_DROP_HEIGHT;

        rainDrops.add(rainDrop);
        lastRainDropTime = TimeUtils.nanoTime();
    }
}
