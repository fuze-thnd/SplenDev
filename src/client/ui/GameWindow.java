import java.awt.*;
    import java.util.ArrayList;
    import java.util.List;
    import javax.swing.*;
    import javax.swing.border.LineBorder;

    public class GameWindow {
        private JFrame frame;
        private JPanel mainPanel;
        private JPanel leftPanel;
        private JPanel centerPanel;
        private JPanel rightPanel;
        private List<JButton> nobleCardLst = new ArrayList<>();
        private List<JButton> level1CardLst = new ArrayList<>();
        private List<JButton> level2CardLst = new ArrayList<>();
        private List<JButton> level3CardLst = new ArrayList<>();
        private List<JButton> gemCardLst = new ArrayList<>();

        public GameWindow(){
            //ตั้งค่่Layout MainWindow
            frame = new JFrame();
            mainPanel = new JPanel();
            leftPanel = new JPanel();
            centerPanel = new JPanel();
            rightPanel = new JPanel();

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
            centerPanel.setPreferredSize(new Dimension(60,610));

            //กำหนดขนาดฝั่งplayer
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
            rightPanel.setPreferredSize(new Dimension(450,450));

            //สร้างLayout Card
            JPanel noblePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
            for(int i=0; i<5; i++){
                JButton nobleCard = new JButton();
                nobleCard.setPreferredSize(new Dimension(100,100));
                nobleCardLst.add(nobleCard);
                noblePanel.add(nobleCard);
            }
            JPanel cardPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

            JPanel cardPanel2 = new JPanel(new GridLayout(3,5,20,20));
            for(int i=0; i<5; i++) {
                JButton level1Card = new JButton();
                level1CardLst.add(level1Card);
                level1Card.setPreferredSize(new Dimension(100,150));
                cardPanel2.add(level1Card);
            }
            for(int i=0; i<5; i++) {
                JButton level2Card = new JButton();
                level2Card.setPreferredSize(new Dimension(100,150));
                level2CardLst.add(level2Card);
                cardPanel2.add(level2Card);
            }
            for(int i=0; i<5; i++) {
                JButton level3Card = new JButton();
                level3Card.setPreferredSize(new Dimension(100,150));
                level3CardLst.add(level3Card);
                cardPanel2.add(level3Card);
            }
            JPanel gemPanel = new JPanel(new GridLayout(6,1,20,50    ));
            for(int i=0; i<6; i++) {
                JButton gemCard = new JButton();
                gemCard.setPreferredSize(new Dimension(60,60));
                gemCardLst.add(gemCard);
                gemPanel.add(gemCard);
            }

            
        ///players panel
            JPanel playerspanel = new JPanel();
        playerspanel.setLayout(new BoxLayout(playerspanel, BoxLayout.Y_AXIS));
        playerspanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 0));
        
        JPanel p1 = new JPanel();
        p1.setBorder(new LineBorder(Color.WHITE, 2, true));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.GRAY);
        p1.setSize(300,100);
        JPanel topp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topp1.setBackground(Color.GRAY);
        JLabel player1 = new JLabel("Player1");
        player1.setFont(new Font("Arial", Font.PLAIN, 15));
        player1.setForeground(Color.WHITE);
        topp1.add(player1);
        p1.add(topp1, BorderLayout.NORTH);
        ///inp1 คือ ช่องข้างในเอาไว้ใส่การ์ด
        JPanel inp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp1.setPreferredSize(new Dimension(300,250));
        inp1.setBackground(Color.GRAY);
        p1.add(inp1, BorderLayout.CENTER);
        ///lowp1 คือ ช่องล่างสุดเอาไว้ใส่gem
        JPanel lowp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowp1.setBackground(Color.GRAY);
        p1.add(lowp1, BorderLayout.SOUTH);
        
        JPanel p2 = new JPanel();
        p2.setBorder(new LineBorder(Color.WHITE, 2, true));
        p2.setLayout(new BorderLayout());
        p2.setBackground(Color.GRAY);
        p2.setSize(300,100);
        JPanel topp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topp2.setBackground(Color.GRAY);
        JLabel player2 = new JLabel("Player2");
        player2.setFont(new Font("Arial", Font.PLAIN, 15));
        player2.setForeground(Color.WHITE);
        topp2.add(player2);
        p2.add(topp2, BorderLayout.NORTH);
        JPanel inp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp2.setPreferredSize(new Dimension(300,250));
        inp2.setBackground(Color.GRAY);
        p2.add(inp2, BorderLayout.CENTER);
        JPanel lowp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowp2.setBackground(Color.GRAY);
        p2.add(lowp2, BorderLayout.SOUTH);
        
        JPanel p3 = new JPanel();
        p3.setBorder(new LineBorder(Color.WHITE, 2, true));
        p3.setLayout(new BorderLayout());
        p3.setBackground(Color.GRAY);
        p3.setSize(300,100);
        JPanel topp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topp3.setBackground(Color.GRAY);
        p3.add(topp3, BorderLayout.NORTH);
        JLabel player3 = new JLabel("Player3");
        player3.setFont(new Font("Arial", Font.PLAIN, 15));
        player3.setForeground(Color.WHITE);
        topp3.add(player3);
        JPanel inp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp3.setPreferredSize(new Dimension(300,250));
        inp3.setBackground(Color.GRAY);
        p3.add(inp3, BorderLayout.CENTER);
        JPanel lowp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowp3.setBackground(Color.GRAY);
        p3.add(lowp3, BorderLayout.SOUTH);
        
        JPanel p4 = new JPanel();
        p4.setBorder(new LineBorder(Color.WHITE, 2, true));
        p4.setLayout(new BorderLayout());
        p4.setBackground(Color.GRAY);
        p4.setSize(300,100);
        JPanel topp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topp4.setBackground(Color.GRAY);
        p4.add(topp4, BorderLayout.NORTH);
        JLabel player4 = new JLabel("Player4");
        player4.setForeground(Color.WHITE);
        player4.setFont(new Font("Arial", Font.PLAIN, 15));
        topp4.add(player4);
        JPanel inp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp4.setPreferredSize(new Dimension(300,250));
        inp4.setBackground(Color.GRAY);
        p4.add(inp4, BorderLayout.CENTER);
        JPanel lowp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowp4.setBackground(Color.GRAY);
        p4.add(lowp4, BorderLayout.SOUTH);
        
        
        
        
   
        
        playerspanel.add(p1);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p2);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p3);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p4);
        
        ///players panel




            
            mainPanel.add(leftPanel);
            mainPanel.add(centerPanel);
            mainPanel.add(rightPanel);
            cardPanel1.add(cardPanel2);
            leftPanel.add(noblePanel,BorderLayout.NORTH);
            leftPanel.add(cardPanel1,BorderLayout.CENTER);
            centerPanel.add(gemPanel);
            rightPanel.add(playerspanel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
        public List<JButton> getNobleCardLst(){
            return nobleCardLst;
        }
        public List<JButton> getLevel1CardLst(){
            return level1CardLst;
        }
        public List<JButton> getLevel2CardLst(){
            return level2CardLst;
        }
        public List<JButton> getLevel3CardLst(){
            return level3CardLst;
        }
        public List<JButton> getGemCardLst(){
            return gemCardLst;
        }


        public static void main(String[] args) {
            new GameWindow();
        }
    }
