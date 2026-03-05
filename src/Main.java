import server.*;
import client.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // test
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("p1"));
        players.add(new Player("p2"));
        new GameManager(players);
    }
}
