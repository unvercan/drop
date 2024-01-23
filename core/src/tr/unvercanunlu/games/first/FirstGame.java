package tr.unvercanunlu.games.first;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static tr.unvercanunlu.games.first.Config.*;

public class FirstGame extends ApplicationAdapter {

    private long lastRainDropTime;

    // assets
    private Texture rainDropImage;
    private Texture bucketImage;
    private Sound rainDropSound;
    private Music rainMusic;

    // camera
    private OrthographicCamera camera;

    // sprite batch
    private SpriteBatch spriteBatch;

    // objects
    private Rectangle bucket;
    private Array<Rectangle> rainDrops;

    @Override
    public void create() {
        // assets
        this.rainDropImage = new Texture(Gdx.files.internal("rainDrop.png"));
        this.bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        this.rainDropSound = Gdx.audio.newSound(Gdx.files.internal("rainDrop.wav"));

        this.rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        this.rainMusic.setLooping(true);
        this.rainMusic.play();

        // camera
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Screen.WIDTH, Screen.HEIGHT);

        // sprite batch
        this.spriteBatch = new SpriteBatch();

        // Objects
        this.bucket = new Rectangle();
        this.bucket.x = (Screen.WIDTH / 2) - (Bucket.WIDTH / 2);
        this.bucket.y = 0;
        this.bucket.width = Bucket.WIDTH;
        this.bucket.height = Bucket.HEIGHT;

        this.rainDrops = new Array<>();
    }

    @Override
    public void render() {
        // clear screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // update camera position
        this.camera.update();

        // render according to camera coordinate system
        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        // start drawing
        this.spriteBatch.begin();

        // drawing bucket
        this.spriteBatch.draw(this.bucketImage, this.bucket.x, this.bucket.y);

        // drawing rain drops
        for (Rectangle rainDrop : this.rainDrops) {
            this.spriteBatch.draw(this.rainDropImage, rainDrop.x, rainDrop.y);
        }

        // end drawing
        this.spriteBatch.end();

        // move bucket
        this.handleBucketMove();

        // create new rain drop
        if ((TimeUtils.nanoTime() - this.lastRainDropTime) > 1000000000) {
            this.spawnRainDrop();
        }

        Iterator<Rectangle> iterator = this.rainDrops.iterator();
        while (iterator.hasNext()) {
            Rectangle raindrop = iterator.next();
            raindrop.y -= RainDrop.SPEED * Gdx.graphics.getDeltaTime();

            if ((raindrop.y + RainDrop.HEIGHT) < 0) {
                iterator.remove();
            }

            if (raindrop.overlaps(this.bucket)) {
                this.rainDropSound.play();
                iterator.remove();
            }
        }
    }

    @Override
    public void dispose() {
        this.bucketImage.dispose();
        this.rainDropImage.dispose();
        this.rainDropSound.dispose();
        this.rainMusic.dispose();
        this.spriteBatch.dispose();
    }

    private void spawnRainDrop() {
        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, (Screen.WIDTH - RainDrop.WIDTH));
        rainDrop.y = Screen.HEIGHT;
        rainDrop.width = RainDrop.WIDTH;
        rainDrop.height = RainDrop.HEIGHT;

        this.rainDrops.add(rainDrop);
        this.lastRainDropTime = TimeUtils.nanoTime();
    }

    private void handleBucketMove() {
        // move left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.bucket.x -= Bucket.SPEED * Gdx.graphics.getDeltaTime();
        }

        // move right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.bucket.x += Bucket.SPEED * Gdx.graphics.getDeltaTime();
        }

        // limit bucket movement
        this.bucket.x = Math.max(this.bucket.x, 0);
        this.bucket.x = Math.min(this.bucket.x, (Screen.WIDTH - Bucket.WIDTH));
    }
}
