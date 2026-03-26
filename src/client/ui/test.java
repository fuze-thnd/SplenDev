package client.ui;

import java.awt.*;
import javax.swing.*;
public class test extends JFrame {
    public test(){
        JLabel winnerLabel = new JLabel("You Win!");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        winnerLabel.setForeground(new Color(255, 215, 0)); // สีทอง
        winnerLabel.setOpaque(true);
        winnerLabel.setBackground(new Color(0,0,0,180)); // กึ่งโปร่ง
        winnerLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4)); // ขอบสีเหลือง
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(winnerLabel);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new test();
    }
}
