
import client.GameClient;
import client.ui.GameWindow;
import client.GameController;

public class Main {
    public static void main(String[] args) {
        // test
        GameWindow gw = new GameWindow();
        GameController gc = new GameController(gw, GameClient.getInstance());
        gw.showOverlay("ACTION");
    }
}
