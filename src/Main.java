import server.*;
import client.*;
import java.util.*;
import shared.GameState;
import shared.Gems.gemsColor;

public class Main {
    public static void main(String[] args) {
        // test
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("p1");
        players.add(p1);
        players.add(new Player("p2"));
        players.add(new Player("p3"));
        GameManager gm = new GameManager(players);
        p1.addGems(gemsColor.Black, 10);
        p1.addGems(gemsColor.Red, 0);
        p1.addGems(gemsColor.Blue, 10);
        p1.addGems(gemsColor.White, 0);
        p1.addGems(gemsColor.Green, 10);
        System.out.println(gm.canPlayerBuyCard(p1, gm.state.getCardsOnBoard()[0][0]));
    }
}
