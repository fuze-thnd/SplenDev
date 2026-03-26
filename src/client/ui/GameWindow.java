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
            rightPanel.setPreferredSize(new Dimension(450,560));

            //สร้างLayout Card
            JPanel noblePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
            for(int i=0; i<5; i++){
                JButton nobleCard = new JButton();
                nobleCard.setPreferredSize(new Dimension(100,100));
                try {
                    ImageIcon noblePic = new ImageIcon("noble" + i + ".jpeg");
                    Image sizeImg = noblePic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    nobleCard.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    nobleCard.setText("No Pic");
                }
                nobleCardLst.add(nobleCard);
                noblePanel.add(nobleCard);
            }
            JPanel cardPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

            JPanel cardPanel2 = new JPanel(new GridLayout(3,5,20,20));
            for(int i=0; i<5; i++) {
                JButton level1Card = new JButton();
                level1CardLst.add(level1Card);
                level1Card.setPreferredSize(new Dimension(100,150));
                try {
                    ImageIcon level1Pic = new ImageIcon("noble" + i + ".jpeg");
                    Image sizeImg = level1Pic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    level1Card.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    level1Card.setText("No Pic");
                }
                cardPanel2.add(level1Card);
            }
            for(int i=0; i<5; i++) {
                JButton level2Card = new JButton();
                level2Card.setPreferredSize(new Dimension(100,150));
                try {
                    ImageIcon level2Pic = new ImageIcon("noble" + i + ".jpeg");
                    Image sizeImg = level2Pic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    level2Card.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    level2Card.setText("No Pic");
                }
                level2CardLst.add(level2Card);
                cardPanel2.add(level2Card);
            }
            for(int i=0; i<5; i++) {
                JButton level3Card = new JButton();
                level3Card.setPreferredSize(new Dimension(100,150));
                try {
                    ImageIcon level3Pic = new ImageIcon("noble" + i + ".jpeg");
                    Image sizeImg = level3Pic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    level3Card.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    level3Card.setText("No Pic");
                }
                level3CardLst.add(level3Card);
                cardPanel2.add(level3Card);
            }
            JPanel gemPanel = new JPanel(new GridLayout(6,1,20,50    ));
            for(int i=0; i<6; i++) {
                JButton gemCard = new JButton();
                gemCard.setPreferredSize(new Dimension(60,60));
                try {
                    ImageIcon gemPic = new ImageIcon("noble" + i + ".jpeg");
                    Image sizeImg = gemPic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    gemCard.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    gemCard.setText("No Pic");
                }
                gemCardLst.add(gemCard);
                gemPanel.add(gemCard);
            }

            
        //players panel
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
        //inp1 คือ ช่องข้างในเอาไว้ใส่การ์ด
        JPanel inp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inp1.setPreferredSize(new Dimension(300,250));
        inp1.setBackground(Color.GRAY);
        p1.add(inp1, BorderLayout.CENTER);
        //lowp1 คือ ช่องล่างสุดเอาไว้ใส่gem
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

            //cards and gems in players panel

            //player1
            JPanel p1whitecard = new JPanel();
            p1whitecard.setPreferredSize(new Dimension(50,100));
            p1whitecard.setBackground(Color.WHITE);
            inp1.add(p1whitecard);
            JLabel p1whitecardcount = new JLabel("0");
            p1whitecard.add(p1whitecardcount);
            JPanel p1bluecard = new JPanel();
            p1bluecard.setPreferredSize(new Dimension(50,100));
            inp1.add(p1bluecard);
            p1bluecard.setBackground(Color.BLUE);
            JLabel p1bluecardcount = new JLabel("0");
            p1bluecard.add(p1bluecardcount);
            JPanel p1greencard = new JPanel();
            p1greencard.setPreferredSize(new Dimension(50,100));
            p1greencard.setBackground(Color.GREEN);
            inp1.add(p1greencard);
            JLabel p1greencardcount = new JLabel("0");
            p1greencard.add(p1greencardcount);
            JPanel p1redcard = new JPanel();
            p1redcard.setPreferredSize(new Dimension(50,100));
            p1redcard.setBackground(Color.RED);
            inp1.add(p1redcard);
            JLabel p1redcardcount = new JLabel("0");
            p1redcard.add(p1redcardcount);
            JPanel p1blackcard = new JPanel();
            p1blackcard.setPreferredSize(new Dimension(50,100));
            p1blackcard.setBackground(Color.BLACK);
            inp1.add(p1blackcard);
            JLabel p1blackcardcount = new JLabel("0");
            p1blackcardcount.setForeground(Color.WHITE);
            p1blackcard.add(p1blackcardcount);

            JPanel p1gemorange = new JPanel();
            p1gemorange.setBackground(Color.ORANGE);
            lowp1.add(p1gemorange);
            JLabel p1gemorangecount = new JLabel("0");
            p1gemorange.add(p1gemorangecount);
            JPanel p1gemwhite = new JPanel();
            p1gemwhite.setBackground(Color.WHITE);
            lowp1.add(p1gemwhite);
            JLabel p1gemwhitecount = new JLabel("0");
            p1gemwhite.add(p1gemwhitecount);
            JPanel p1gemblue = new JPanel();
            p1gemblue.setBackground(Color.BLUE);
            lowp1.add(p1gemblue);
            JLabel p1gembluecount = new JLabel("0");
            p1gemblue.add(p1gembluecount);
            JPanel p1gemgreen = new JPanel();
            p1gemgreen.setBackground(Color.GREEN);
            lowp1.add(p1gemgreen);
            JLabel p1gemgreencount = new JLabel("0");
            p1gemgreen.add(p1gemgreencount);
            JPanel p1gemred = new JPanel();
            p1gemred.setBackground(Color.RED);
            lowp1.add(p1gemred);
            JLabel p1gemredcount = new JLabel("0");
            p1gemred.add(p1gemredcount);
            JPanel p1gemblack = new JPanel();
            p1gemblack.setBackground(Color.BLACK);
            lowp1.add(p1gemblack);
            JLabel p1gemblackcount = new JLabel("0");
            p1gemblackcount.setForeground(Color.WHITE);
            p1gemblack.add(p1gemblackcount);

            //player2
            JPanel p2whitecard = new JPanel();
            p2whitecard.setPreferredSize(new Dimension(50,100));
            p2whitecard.setBackground(Color.WHITE);
            inp2.add(p2whitecard);
            JLabel p2whitecardcount = new JLabel("0");
            p2whitecard.add(p2whitecardcount);
            JPanel p2bluecard = new JPanel();
            p2bluecard.setPreferredSize(new Dimension(50,100));
            inp2.add(p2bluecard);
            p2bluecard.setBackground(Color.BLUE);
            JLabel p2bluecardcount = new JLabel("0");
            p2bluecard.add(p2bluecardcount);
            JPanel p2greencard = new JPanel();
            p2greencard.setPreferredSize(new Dimension(50,100));
            p2greencard.setBackground(Color.GREEN);
            inp2.add(p2greencard);
            JLabel p2greencardcount = new JLabel("0");
            p2greencard.add(p2greencardcount);
            JPanel p2redcard = new JPanel();
            p2redcard.setPreferredSize(new Dimension(50,100));
            p2redcard.setBackground(Color.RED);
            inp2.add(p2redcard);
            JLabel p2redcardcount = new JLabel("0");
            p2redcard.add(p2redcardcount);
            JPanel p2blackcard = new JPanel();
            p2blackcard.setPreferredSize(new Dimension(50,100));
            p2blackcard.setBackground(Color.BLACK);
            inp2.add(p2blackcard);
            JLabel p2blackcardcount = new JLabel("0");
            p2blackcardcount.setForeground(Color.WHITE);
            p2blackcard.add(p2blackcardcount);

            JPanel p2gemorange = new JPanel();
            p2gemorange.setBackground(Color.ORANGE);
            lowp2.add(p2gemorange);
            JLabel p2gemorangecount = new JLabel("0");
            p2gemorange.add(p2gemorangecount);
            JPanel p2gemwhite = new JPanel();
            p2gemwhite.setBackground(Color.WHITE);
            lowp2.add(p2gemwhite);
            JLabel p2gemwhitecount = new JLabel("0");
            p2gemwhite.add(p2gemwhitecount);
            JPanel p2gemblue = new JPanel();
            p2gemblue.setBackground(Color.BLUE);
            lowp2.add(p2gemblue);
            JLabel p2gembluecount = new JLabel("0");
            p2gemblue.add(p2gembluecount);
            JPanel p2gemgreen = new JPanel();
            p2gemgreen.setBackground(Color.GREEN);
            lowp2.add(p2gemgreen);
            JLabel p2gemgreencount = new JLabel("0");
            p2gemgreen.add(p2gemgreencount);
            JPanel p2gemred = new JPanel();
            p2gemred.setBackground(Color.RED);
            lowp2.add(p2gemred);
            JLabel p2gemredcount = new JLabel("0");
            p2gemred.add(p2gemredcount);
            JPanel p2gemblack = new JPanel();
            p2gemblack.setBackground(Color.BLACK);
            lowp2.add(p2gemblack);
            JLabel p2gemblackcount = new JLabel("0");
            p2gemblackcount.setForeground(Color.WHITE);
            p2gemblack.add(p2gemblackcount);

            //player3
            JPanel p3whitecard = new JPanel();
            p3whitecard.setPreferredSize(new Dimension(50,100));
            p3whitecard.setBackground(Color.WHITE);
            inp3.add(p3whitecard);
            JLabel p3whitecardcount = new JLabel("0");
            p3whitecard.add(p3whitecardcount);
            JPanel p3bluecard = new JPanel();
            p3bluecard.setPreferredSize(new Dimension(50,100));
            inp3.add(p3bluecard);
            p3bluecard.setBackground(Color.BLUE);
            JLabel p3bluecardcount = new JLabel("0");
            p3bluecard.add(p3bluecardcount);
            JPanel p3greencard = new JPanel();
            p3greencard.setPreferredSize(new Dimension(50,100));
            p3greencard.setBackground(Color.GREEN);
            inp3.add(p3greencard);
            JLabel p3greencardcount = new JLabel("0");
            p3greencard.add(p3greencardcount);
            JPanel p3redcard = new JPanel();
            p3redcard.setPreferredSize(new Dimension(50,100));
            p3redcard.setBackground(Color.RED);
            inp3.add(p3redcard);
            JLabel p3redcardcount = new JLabel("0");
            p3redcard.add(p3redcardcount);
            JPanel p3blackcard = new JPanel();
            p3blackcard.setPreferredSize(new Dimension(50,100));
            p3blackcard.setBackground(Color.BLACK);
            inp3.add(p3blackcard);
            JLabel p3blackcardcount = new JLabel("0");
            p3blackcardcount.setForeground(Color.WHITE);
            p3blackcard.add(p3blackcardcount);

            JPanel p3gemorange = new JPanel();
            p3gemorange.setBackground(Color.ORANGE);
            lowp3.add(p3gemorange);
            JLabel p3gemorangecount = new JLabel("0");
            p3gemorange.add(p3gemorangecount);
            JPanel p3gemwhite = new JPanel();
            p3gemwhite.setBackground(Color.WHITE);
            lowp3.add(p3gemwhite);
            JLabel p3gemwhitecount = new JLabel("0");
            p3gemwhite.add(p3gemwhitecount);
            JPanel p3gemblue = new JPanel();
            p3gemblue.setBackground(Color.BLUE);
            lowp3.add(p3gemblue);
            JLabel p3gembluecount = new JLabel("0");
            p3gemblue.add(p3gembluecount);
            JPanel p3gemgreen = new JPanel();
            p3gemgreen.setBackground(Color.GREEN);
            lowp3.add(p3gemgreen);
            JLabel p3gemgreencount = new JLabel("0");
            p3gemgreen.add(p3gemgreencount);
            JPanel p3gemred = new JPanel();
            p3gemred.setBackground(Color.RED);
            lowp3.add(p3gemred);
            JLabel p3gemredcount = new JLabel("0");
            p3gemred.add(p3gemredcount);
            JPanel p3gemblack = new JPanel();
            p3gemblack.setBackground(Color.BLACK);
            lowp3.add(p3gemblack);
            JLabel p3gemblackcount = new JLabel("0");
            p3gemblackcount.setForeground(Color.WHITE);
            p3gemblack.add(p3gemblackcount);

            //player4
            JPanel p4whitecard = new JPanel();
            p4whitecard.setPreferredSize(new Dimension(50,100));
            p4whitecard.setBackground(Color.WHITE);
            inp4.add(p4whitecard);
            JLabel p4whitecardcount = new JLabel("0");
            p4whitecard.add(p4whitecardcount);
            JPanel p4bluecard = new JPanel();
            p4bluecard.setPreferredSize(new Dimension(50,100));
            inp4.add(p4bluecard);
            p4bluecard.setBackground(Color.BLUE);
            JLabel p4bluecardcount = new JLabel("0");
            p4bluecard.add(p4bluecardcount);
            JPanel p4greencard = new JPanel();
            p4greencard.setPreferredSize(new Dimension(50,100));
            p4greencard.setBackground(Color.GREEN);
            inp4.add(p4greencard);
            JLabel p4greencardcount = new JLabel("0");
            p4greencard.add(p4greencardcount);
            JPanel p4redcard = new JPanel();
            p4redcard.setPreferredSize(new Dimension(50,100));
            p4redcard.setBackground(Color.RED);
            inp4.add(p4redcard);
            JLabel p4redcardcount = new JLabel("0");
            p4redcard.add(p4redcardcount);
            JPanel p4blackcard = new JPanel();
            p4blackcard.setPreferredSize(new Dimension(50,100));
            p4blackcard.setBackground(Color.BLACK);
            inp4.add(p4blackcard);
            JLabel p4blackcardcount = new JLabel("0");
            p4blackcardcount.setForeground(Color.WHITE);
            p4blackcard.add(p4blackcardcount);

            JPanel p4gemorange = new JPanel();
            p4gemorange.setBackground(Color.ORANGE);
            lowp4.add(p4gemorange);
            JLabel p4gemorangecount = new JLabel("0");
            p4gemorange.add(p4gemorangecount);
            JPanel p4gemwhite = new JPanel();
            p4gemwhite.setBackground(Color.WHITE);
            lowp4.add(p4gemwhite);
            JLabel p4gemwhitecount = new JLabel("0");
            p4gemwhite.add(p4gemwhitecount);
            JPanel p4gemblue = new JPanel();
            p4gemblue.setBackground(Color.BLUE);
            lowp4.add(p4gemblue);
            JLabel p4gembluecount = new JLabel("0");
            p4gemblue.add(p4gembluecount);
            JPanel p4gemgreen = new JPanel();
            p4gemgreen.setBackground(Color.GREEN);
            lowp4.add(p4gemgreen);
            JLabel p4gemgreencount = new JLabel("0");
            p4gemgreen.add(p4gemgreencount);
            JPanel p4gemred = new JPanel();
            p4gemred.setBackground(Color.RED);
            lowp4.add(p4gemred);
            JLabel p4gemredcount = new JLabel("0");
            p4gemred.add(p4gemredcount);
            JPanel p4gemblack = new JPanel();
            p4gemblack.setBackground(Color.BLACK);
            lowp4.add(p4gemblack);
            JLabel p4gemblackcount = new JLabel("0");
            p4gemblackcount.setForeground(Color.WHITE);
            p4gemblack.add(p4gemblackcount);

            //players total points
            JLabel p1totalpoints = new JLabel("Total points: 0");
            p1totalpoints.setForeground(Color.WHITE);
            lowp1.add(p1totalpoints);
            JLabel p2totalpoints = new JLabel("Total points: 0");
            p2totalpoints.setForeground(Color.WHITE);
            lowp2.add(p2totalpoints);
            JLabel p3totalpoints = new JLabel("Total points: 0");
            p3totalpoints.setForeground(Color.WHITE);
            lowp3.add(p3totalpoints);
            JLabel p4totalpoints = new JLabel("Total points: 0");
            p4totalpoints.setForeground(Color.WHITE);
            lowp4.add(p4totalpoints);
        
        
   
        
        playerspanel.add(p1);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p2);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p3);
        playerspanel.add(Box.createVerticalStrut(10));
        playerspanel.add(p4);
        
        //players panel




            
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
