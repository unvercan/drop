package tr.unvercanunlu.games.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

        // configuration
        configuration.setTitle(Config.TITLE);
        configuration.setForegroundFPS(Config.FPS);
        configuration.useVsync(true);
        configuration.setWindowedMode(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        DropGame dropGame = new DropGame();

        new Lwjgl3Application(dropGame, configuration);
    }
}
