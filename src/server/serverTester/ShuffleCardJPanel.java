package server.serverTester;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ShuffleCardJPanel extends JPanel {
    private Image backgroundImage;

    public ShuffleCardJPanel(LayoutManager layout) {
        super(layout, true);
        this.setOpaque(false);

        backgroundImage = new ImageIcon("shuffle_card.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
