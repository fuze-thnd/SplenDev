package client.ui;

import client.GameClient;
import shared.GameState;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private BoardPanel boardPanel;

    public GameWindow(GameClient client) {
        setTitle("Splendor");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel(client);

        add(new NoblePanel(), BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(new TokenPanel(), BorderLayout.WEST);

        setVisible(true);

        //mock
        GameState mock = createMockState();
        boardPanel.update(mock);
    }

    private GameState createMockState() {
        GameState state = new GameState();

        var cards = new shared.DevelopmentCards[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                cards[i][j] = new shared.DevelopmentCards();
            }
        }

        state.setCardsOnBoard(cards);
        return state;
    }
}
