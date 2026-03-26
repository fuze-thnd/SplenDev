package server.serverTester;

import javax.swing.*;
import java.awt.*;

public class DevelopmentCardJPanel extends JPanel {
    private Image backgroundImage;

    public DevelopmentCardJPanel(LayoutManager layout) {
        super(layout, true);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public void setImage(String imageName){
        this.backgroundImage = new ImageIcon("DevelopmentCardImage/" + imageName+".jpeg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
