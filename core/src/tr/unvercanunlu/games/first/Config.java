package tr.unvercanunlu.games.first;

public interface Config {

    interface Screen {
        int WIDTH = 800;
        int HEIGHT = 600;
    }

    interface Bucket {
        int WIDTH = 64;
        int HEIGHT = 64;
        int SPEED = 200;
    }

    interface RainDrop {
        int WIDTH = 64;
        int HEIGHT = 64;
        int SPEED = 200;
    }
}
