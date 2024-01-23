package tr.unvercanunlu.games.drop;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {

    // game
    public static final String TITLE = "Drop";

    // display
    public static final int FPS = 60;

    // screen
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // bucket
    public static final int BUCKET_WIDTH = 64;
    public static final int BUCKET_HEIGHT = 64;
    public static final int BUCKET_SPEED = 200;

    // rain drop
    public static final int RAIN_DROP_WIDTH = 64;
    public static final int RAIN_DROP_HEIGHT = 64;
    public static final int RAIN_DROP_SPEED = 200;
}
