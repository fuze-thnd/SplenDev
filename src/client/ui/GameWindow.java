
    import java.awt.*;
    import java.util.concurrent.Flow;
    import javax.swing.*;

    public class GameWindow {
        private JFrame frame;
        private JPanel mainPanel;
        private JPanel leftPanel;
        private JPanel centerPanel;


        public GameWindow(){
            //ตั้งค่่Layout MainWindow
            frame = new JFrame();
            mainPanel = new JPanel();
            leftPanel = new JPanel();
            centerPanel = new JPanel();

            frame.add(mainPanel);
            frame.setSize(1200,1000);
            frame.setLocationRelativeTo(null);

            //หน้าจอหลักปรับlayoutUI
            mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

            //กำหนดขนาดฝั่งที่มีการ์ด
            leftPanel.setLayout(new BorderLayout());
            leftPanel.setPreferredSize(new Dimension(620,610));

            // กำหนดขนาดฝั่งเจม
            centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            centerPanel.setPreferredSize(new Dimension(82,610));

            //สร้างLayout Card
            JPanel noblePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
            for(int i=0; i<5; i++){
                JButton nobleCard = new JButton();
                nobleCard.setPreferredSize(new Dimension(100,100));
                noblePanel.add(nobleCard);
            }
            JPanel cardPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

            JPanel cardPanel2 = new JPanel(new GridLayout(3,5,20,20));
            for(int i=0; i<5; i++) {
                JButton level1Card = new JButton();
                level1Card.setPreferredSize(new Dimension(100,150));
                cardPanel2.add(level1Card);
            }
            for(int i=0; i<5; i++) {
                JButton level2Card = new JButton();
                level2Card.setPreferredSize(new Dimension(100,150));
                cardPanel2.add(level2Card);
            }
            for(int i=0; i<5; i++) {
                JButton level3Card = new JButton();
                level3Card.setPreferredSize(new Dimension(100,150));
                cardPanel2.add(level3Card);
            }
            JPanel gemPanel = new JPanel(new GridLayout(5,1,20,50    ));
            for(int i=0; i<5; i++) {
                JButton gem = new JButton();
                gem.setPreferredSize(new Dimension(82,82));
                gemPanel.add(gem);
            }



            mainPanel.add(leftPanel);
            mainPanel.add(centerPanel);
            cardPanel1.add(cardPanel2);
            leftPanel.add(noblePanel,BorderLayout.NORTH);
            leftPanel.add(cardPanel1,BorderLayout.CENTER);
            centerPanel.add(gemPanel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }
        public static void main(String[] args) {
            new GameWindow();
        }
    }
