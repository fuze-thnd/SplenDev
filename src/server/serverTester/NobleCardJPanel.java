package server.serverTester;

import javax.swing.*;
import java.awt.*;

public class NobleCardJPanel extends JPanel {
    private Image backgroundImage;

    public NobleCardJPanel(LayoutManager layout) {
        super(layout, true);
        this.setOpaque(false);
    }

    public void setImage(String imageName){
        this.backgroundImage = new ImageIcon("NobleCardImage/" + imageName+".png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
