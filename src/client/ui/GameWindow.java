package client.ui;

import client.Player;
import shared.DevelopmentCards;
import shared.Gems;
import shared.NobleCards;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

    public class GameWindow {
        private JFrame frame;
        private JPanel mainPanel;
        private JPanel leftPanel;
        private JPanel centerPanel;
        private JPanel gemPanel;
        private JPanel rightPanel;
        private JPanel playerspanel;
        private JPanel glassPane;
        private JPanel actionPanel;
        private JPanel confirmationPanel;
        private JLabel winnerLabel;
        private JButton confirmBtn;
        private JButton resetBtn;
        private List<JButton> nobleCardLst = new ArrayList<>();
        private List<JButton> level1CardLst = new ArrayList<>();
        private List<JButton> level2CardLst = new ArrayList<>();
        private List<JButton> level3CardLst = new ArrayList<>();
        private List<JButton> gemCardLst = new ArrayList<>();
        private List<JButton> actionButtons = new ArrayList<>();

        public GameWindow(){
            //ตั้งค่าLayout MainWindow
            frame = new JFrame();
            frame.setResizable(false);
            mainPanel = new JPanel();
            leftPanel = new JPanel();
            centerPanel = new JPanel();
            rightPanel = new JPanel();

            frame.add(mainPanel);
//            frame.setResizable(false);

            //หน้าจอหลักปรับlayoutUI
            mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            //กำหนดขนาดฝั่งที่มีการ์ด
            leftPanel.setLayout(new FlowLayout());
            leftPanel.setPreferredSize(new Dimension(700,700));

            // กำหนดขนาดฝั่งเจม
            centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            centerPanel.setPreferredSize(new Dimension(60,610));

            //กำหนดขนาดฝั่งplayer
//            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
            rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            rightPanel.setPreferredSize(new Dimension(450,730));

            //สร้างLayout Card
            for(int i=0; i<5; i++){
                leftPanel.add(createNobleCard());
            }

            // Development Card lv3
            // กองสุ่ม
            leftPanel.add(createShuffleCard(3));

            for(int i=0; i<4; i++) {
                leftPanel.add(createDeveloptmentCard());
            }

            // Development Card lv2
            // กองสุ่ม
            leftPanel.add(createShuffleCard(2));
            for(int i=0; i<4; i++) {
                leftPanel.add(createDeveloptmentCard());
            }
            // Development Card lv1
            // กองสุ่ม
            leftPanel.add(createShuffleCard(1));

            for(int i=0; i<4; i++) {
                leftPanel.add(createDeveloptmentCard());
            }
            gemPanel = new JPanel(new GridLayout(6,1,20,50));
            for(int i=0; i<6; i++) {
                JButton gemCard = new JButton();
                gemCard.setPreferredSize(new Dimension(60,60));
                try {
                    ImageIcon gemPic = new ImageIcon("coin" + i + ".png");
                    Image sizeImg = gemPic.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH);
                    gemCard.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    gemCard.setText("No Pic");
                }
                gemCardLst.add(gemCard);
                gemPanel.add(gemCard);
            }

            
        //players panel
            playerspanel = new JPanel();
            playerspanel.setLayout(new BoxLayout(playerspanel, BoxLayout.Y_AXIS));
            playerspanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

            JPanel p1 = new JPanel();
            p1.setBorder(new LineBorder(Color.WHITE, 2, true));
            p1.setLayout(new BorderLayout());
            p1.setBackground(Color.GRAY);
            p1.setSize(300,150);
            JPanel topp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topp1.setBackground(Color.GRAY);
            JLabel player1 = new JLabel("Player1");
            player1.setFont(new Font("Arial", Font.PLAIN, 15));
            player1.setForeground(Color.WHITE);
            topp1.add(player1);
            p1.add(topp1, BorderLayout.NORTH);
            //inp1 คือ ช่องข้างในเอาไว้ใส่การ์ด
            JPanel inp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inp1.setPreferredSize(new Dimension(300,70));
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
            p2.setSize(300,150);
            JPanel topp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topp2.setBackground(Color.GRAY);
            JLabel player2 = new JLabel("Player2");
            player2.setFont(new Font("Arial", Font.PLAIN, 15));
            player2.setForeground(Color.WHITE);
            topp2.add(player2);
            p2.add(topp2, BorderLayout.NORTH);
            JPanel inp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inp2.setPreferredSize(new Dimension(300,70));
            inp2.setBackground(Color.GRAY);
            p2.add(inp2, BorderLayout.CENTER);
            JPanel lowp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lowp2.setBackground(Color.GRAY);
            p2.add(lowp2, BorderLayout.SOUTH);

            JPanel p3 = new JPanel();
            p3.setBorder(new LineBorder(Color.WHITE, 2, true));
            p3.setLayout(new BorderLayout());
            p3.setBackground(Color.GRAY);
            p3.setSize(300,150);
            JPanel topp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topp3.setBackground(Color.GRAY);
            p3.add(topp3, BorderLayout.NORTH);
            JLabel player3 = new JLabel("Player3");
            player3.setFont(new Font("Arial", Font.PLAIN, 15));
            player3.setForeground(Color.WHITE);
            topp3.add(player3);
            JPanel inp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inp3.setPreferredSize(new Dimension(300,70));
            inp3.setBackground(Color.GRAY);
            p3.add(inp3, BorderLayout.CENTER);
            JPanel lowp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lowp3.setBackground(Color.GRAY);
            p3.add(lowp3, BorderLayout.SOUTH);

            JPanel p4 = new JPanel();
            p4.setBorder(new LineBorder(Color.WHITE, 2, true));
            p4.setLayout(new BorderLayout());
            p4.setBackground(Color.GRAY);
            p4.setSize(300,150);
            JPanel topp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topp4.setBackground(Color.GRAY);
            p4.add(topp4, BorderLayout.NORTH);
            JLabel player4 = new JLabel("Player4");
            player4.setForeground(Color.WHITE);
            player4.setFont(new Font("Arial", Font.PLAIN, 15));
            topp4.add(player4);
            JPanel inp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inp4.setPreferredSize(new Dimension(300,70));
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


            //reserve panel
            JPanel reservepanel = new JPanel();
            reservepanel.setBorder(new LineBorder(Color.WHITE, 2, true));
            reservepanel.setLayout(new BorderLayout());
            reservepanel.setBackground(Color.GRAY);
            reservepanel.setSize(300,220);
            JPanel topreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topreserve.setBackground(Color.GRAY);
            reservepanel.add(topreserve, BorderLayout.NORTH);
            JLabel reservecards = new JLabel("Reserve Cards");
            reservecards.setForeground(Color.WHITE);
            reservecards.setFont(new Font("Arial", Font.PLAIN, 15));
            topreserve.add(reservecards);
            JPanel inreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inreserve.setPreferredSize(new Dimension(300,220));
            inreserve.setBackground(Color.GRAY);
            reservepanel.add(inreserve, BorderLayout.CENTER);
            JPanel lowreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lowreserve.setBackground(Color.GRAY);
            reservepanel.add(lowreserve, BorderLayout.SOUTH);

            //reserve cards
            ImageIcon reservecard1pic = new ImageIcon("การ์ดสำรอง1.jpg");
            JButton reservecard1 = new JButton(reservecard1pic);
            reservecard1.setPreferredSize(new Dimension(70,85));
            inreserve.add(reservecard1);
            ImageIcon reservecard2pic = new ImageIcon("การ์ดสำรอง2.jpg");
            JButton reservecard2 = new JButton(reservecard2pic);
            reservecard2.setPreferredSize(new Dimension(70,85));
            inreserve.add(reservecard2);
            ImageIcon reservecard3pic = new ImageIcon("การ์ดสำรอง3.jpg");
            JButton reservecard3 = new JButton(reservecard3pic);
            reservecard3.setPreferredSize(new Dimension(70,85));
            inreserve.add(reservecard3);



            playerspanel.add(p1);
            playerspanel.add(Box.createVerticalStrut(10));
            playerspanel.add(p2);
            playerspanel.add(Box.createVerticalStrut(10));
            playerspanel.add(p3);
            playerspanel.add(Box.createVerticalStrut(10));
            playerspanel.add(p4);
            playerspanel.add(Box.createVerticalStrut(10));
            playerspanel.add(reservepanel);

            //players panel
            mainPanel.add(leftPanel);
            mainPanel.add(centerPanel);
            mainPanel.add(rightPanel);
            centerPanel.add(gemPanel);
            rightPanel.add(playerspanel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Glass Paneสำหรับขึ้นมาเมื่อถึงตาเล่นของผู้เล่น
            glassPane = (JPanel) frame.getGlassPane();
            glassPane.setLayout(new BorderLayout());
            glassPane.setBackground(new Color(0, 0, 0, 100));
            glassPane.setVisible(false);

            actionPanel = new JPanel(new GridLayout(1, 4));
            String[] button = {"Reserve", "Take 3 Tokens", "Take 2 Tokens", "Purchase"};
            for (int i = 0; i < button.length ; i++){
                JButton button1 = new JButton(button[i]);
                actionButtons.add(button1);
                actionPanel.add(button1);
            }
            
            //ConfirmationPanel
            resetBtn = new JButton("Reset Selection");
            resetBtn.setBackground(new Color(255, 100, 100));
            resetBtn.setForeground(Color.WHITE);
            resetBtn.setFocusPainted(false);

            confirmBtn = new JButton("Confirm Action");
            confirmBtn.setBackground(new Color(50, 205, 50));
            confirmBtn.setFocusPainted(false);
            confirmBtn.setEnabled(false);

            //YouWin glass บอกผู้ชนะ
            winnerLabel = new JLabel("You Win!");
            winnerLabel.setFont(new Font("Arial", Font.BOLD, 40));
            winnerLabel.setForeground(new Color(255, 215, 0)); // สีทอง
            winnerLabel.setOpaque(true);
            winnerLabel.setBackground(new Color(0,0,0,180)); // กึ่งโปร่ง
            winnerLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4)); // ขอบสีเหลือง
            winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            winnerLabel.setVerticalAlignment(SwingConstants.CENTER);

            frame.pack();
        }
        
        public void showOverlay(String type) {
            glassPane.removeAll();
            
            if (type.equals("ACTION")) {
                glassPane.add(actionPanel, BorderLayout.CENTER);
            } else if (type.equals("CONFIRM")) {
                JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
                southPanel.setOpaque(false);
                southPanel.add(resetBtn);
                southPanel.add(confirmBtn);
                
                glassPane.add(southPanel, BorderLayout.SOUTH);
            } else if (type.equals("WIN")) {
                glassPane.add(winnerLabel, BorderLayout.CENTER);
            }
            
            if (type.equals("NONE")) {
                glassPane.setVisible(false);
            } else {
                glassPane.setVisible(true);
                glassPane.revalidate();
                glassPane.repaint();
            }
        }
        
        public void setHighlight(JButton btn, boolean isSelected) {
            if (btn == null) return;
            
            if (isSelected) {
                btn.setBorder(new javax.swing.border.LineBorder(java.awt.Color.YELLOW, 5));
            } else {
                btn.setBorder(new javax.swing.border.LineBorder(java.awt.Color.BLACK, 1));
            }
        }
        
        public List<JButton> getActionButtons() {
            return actionButtons;
        }
        public JButton getConfirmBtn() { return confirmBtn; }
        public JButton getResetBtn() { return resetBtn; }

        private JButton createShuffleCard(int level) {
            JButton drawCard = new JButton();
            drawCard.setOpaque(false);
            drawCard.setPreferredSize(new Dimension(130,180));
            try {
                ImageIcon level1Pic = new ImageIcon("DevelopmentCardImage/ShuffleCard/cardrow" + level + ".png");
                Image sizeImg = level1Pic.getImage().getScaledInstance(130,180,Image.SCALE_SMOOTH);
                drawCard.setIcon(new ImageIcon(sizeImg));
            } catch (Exception e) {
                drawCard.setText("No Pic");
            }

            return drawCard;
        }

        private JButton createNobleCard(){
            JButton nobleCard = new JButton();
            nobleCard.setLayout(new BorderLayout());

            OutlineJLabel pointLabel =  new OutlineJLabel("7");
            pointLabel.setOutlineColor(Color.black);
            pointLabel.setForeground(Color.WHITE);
            pointLabel.setOpaque(false);

            nobleCard.add(pointLabel, BorderLayout.NORTH);

            JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
            costPanel.setOpaque(false);
            for(int a = 0; a<3; a++){
                OutlineJLabel costLabel =  new OutlineJLabel("7", SwingConstants.CENTER);
                costLabel.setPreferredSize(new Dimension(20, 20));
                costLabel.setOutlineColor(Color.black);
                costLabel.setForeground(Color.WHITE);
                costLabel.setBackground(Color.GREEN);
                costLabel.setOpaque(true);
                costPanel.add(costLabel);
            }

            nobleCard.add(costPanel, BorderLayout.SOUTH);

            nobleCard.setPreferredSize(new Dimension(130,130));
            try {
                ImageIcon noblePic = new ImageIcon("NobleCardImage/noble1" + ".png");
                Image sizeImg = noblePic.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
                nobleCard.setIcon(new ImageIcon(sizeImg));
            } catch (Exception e) {
                nobleCard.setText("No Pic");
            }
            nobleCardLst.add(nobleCard);

            return nobleCard;
        }

        private JButton createNobleCard(NobleCards nc){
            JButton nobleCard = new JButton();
            nobleCard.setLayout(new BorderLayout());

            OutlineJLabel pointLabel =  new OutlineJLabel(nc.getPrestigePoints() + "");
            pointLabel.setOutlineColor(Color.black);
            pointLabel.setForeground(Color.WHITE);
            pointLabel.setOpaque(false);

            nobleCard.add(pointLabel, BorderLayout.NORTH);

            JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
            costPanel.setOpaque(false);
            for(Gems.gemsColor color : Gems.gemsColor.values()){
                if(color.equals(Gems.gemsColor.Gold)) continue;

                Integer costAmount = (Integer) nc.getCost().get(color);
                if(costAmount != null && costAmount > 0){
                    OutlineJLabel costLabel =  new OutlineJLabel(costAmount + "", SwingConstants.CENTER);
                    costLabel.setPreferredSize(new Dimension(20, 20));
                    costLabel.setOutlineColor(Color.black);
                    costLabel.setForeground(Color.WHITE);
                    costLabel.setBackground(getGemColor(color));
                    costLabel.setOpaque(true);
                    costPanel.add(costLabel);
                }
            }

            nobleCard.add(costPanel, BorderLayout.SOUTH);

            nobleCard.setPreferredSize(new Dimension(130,130));
            try {
                ImageIcon noblePic = new ImageIcon("NobleCardImage/" + nc.getImageName() + ".png");
                Image sizeImg = noblePic.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
                nobleCard.setIcon(new ImageIcon(sizeImg));
            } catch (Exception e) {
                nobleCard.setText("No Pic");
            }
            nobleCardLst.add(nobleCard);

            return nobleCard;
        }

        private JButton createDeveloptmentCard(){
            JButton dvCard = new JButton();
            dvCard.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);
            OutlineJLabel pointLabel =  new OutlineJLabel("7");
            pointLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            pointLabel.setOutlineColor(Color.black);
            pointLabel.setForeground(Color.WHITE);
            pointLabel.setOpaque(false);
            topPanel.add(pointLabel, BorderLayout.WEST);

            OutlineJLabel bonusGemLabel =  new OutlineJLabel("", SwingConstants.CENTER);
            bonusGemLabel.setPreferredSize(new Dimension(20, 20));
            bonusGemLabel.setOutlineColor(Color.black);
            bonusGemLabel.setForeground(Color.WHITE);
            bonusGemLabel.setBackground(Color.GREEN);
            bonusGemLabel.setOpaque(true);
            topPanel.add(bonusGemLabel, BorderLayout.EAST);
            dvCard.add(topPanel, BorderLayout.NORTH);

            // Cost Panel ราคา
            JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
            costPanel.setOpaque(false);
            for(int a = 0; a<3; a++){
                OutlineJLabel costLabel =  new OutlineJLabel("7", SwingConstants.CENTER);
                costLabel.setPreferredSize(new Dimension(20, 20));
                costLabel.setOutlineColor(Color.black);
                costLabel.setForeground(Color.WHITE);
                costLabel.setBackground(Color.GREEN);
                costLabel.setOpaque(true);
                costPanel.add(costLabel);
            }
            dvCard.add(costPanel, BorderLayout.SOUTH);

            dvCard.setPreferredSize(new Dimension(130,180));
            try {
                ImageIcon level1Pic = new ImageIcon("DevelopmentCardImage/card" + 0 + ".jpeg");
                Image sizeImg = level1Pic.getImage().getScaledInstance(130,180,Image.SCALE_SMOOTH);
                dvCard.setIcon(new ImageIcon(sizeImg));
            } catch (Exception e) {
                dvCard.setText("No Pic");
            }

            return dvCard;
        }

        private JButton createDeveloptmentCard(DevelopmentCards dc) {
            JButton dvCard = new JButton();
            dvCard.setLayout(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);

            if(dc.getPrestigePoints() > 0){
                OutlineJLabel pointLabel =  new OutlineJLabel(String.valueOf(dc.getPrestigePoints()));
                pointLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                pointLabel.setOutlineColor(Color.black);
                pointLabel.setForeground(Color.WHITE);
                pointLabel.setOpaque(false);
                topPanel.add(pointLabel, BorderLayout.WEST);
            }

            OutlineJLabel bonusGemLabel =  new OutlineJLabel("", SwingConstants.CENTER);
            bonusGemLabel.setPreferredSize(new Dimension(20, 20));
            bonusGemLabel.setOutlineColor(Color.black);
            bonusGemLabel.setForeground(Color.WHITE);
            bonusGemLabel.setBackground(getGemColor(dc.getGemsColor()));
            bonusGemLabel.setOpaque(true);
            topPanel.add(bonusGemLabel, BorderLayout.EAST);
            dvCard.add(topPanel, BorderLayout.NORTH);

            // Cost Panel ราคา
            JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
            costPanel.setOpaque(false);
            for(Gems.gemsColor color : Gems.gemsColor.values()){
                if (color.equals(Gems.gemsColor.Gold)) continue;

                Integer costAmount = (Integer) dc.getCost().get(color);
                if(costAmount != null && costAmount > 0){
                    OutlineJLabel costLabel =  new OutlineJLabel(costAmount + "", SwingConstants.CENTER);
                    costLabel.setPreferredSize(new Dimension(20, 20));
                    costLabel.setOutlineColor(Color.black);
                    costLabel.setForeground(Color.WHITE);
                    costLabel.setBackground(getGemColor(color));
                    costLabel.setOpaque(true);
                    costPanel.add(costLabel);
                }
            }
            dvCard.add(costPanel, BorderLayout.SOUTH);

            dvCard.setPreferredSize(new Dimension(130,180));
            try {
                ImageIcon level1Pic = new ImageIcon("DevelopmentCardImage/"  + dc.getImageName() + ".jpeg");
                Image sizeImg = level1Pic.getImage().getScaledInstance(130,180,Image.SCALE_SMOOTH);
                dvCard.setIcon(new ImageIcon(sizeImg));
            } catch (Exception e) {
                dvCard.setText("No Pic");
            }

            return dvCard;
        }

        private Color getGemColor(Gems.gemsColor color){
            switch (color){
                case White:
                    return Color.WHITE;
                case Black:
                    return Color.BLACK;
                case Blue:
                    return Color.BLUE;
                case Green:
                    return Color.GREEN;
                case Red:
                    return Color.RED;
                case Gold:
                    return Color.YELLOW;
            }

            return null;
        }

        private Gems.gemsColor getGemColor(Color color) {
            if (Color.WHITE.equals(color)) {
                return Gems.gemsColor.White;
            } else if (Color.BLACK.equals(color)) {
                return Gems.gemsColor.Black;
            } else if (Color.BLUE.equals(color)) {
                return Gems.gemsColor.Blue;
            } else if (Color.GREEN.equals(color)) {
                return Gems.gemsColor.Green;
            } else if (Color.RED.equals(color)) {
                return Gems.gemsColor.Red;
            } else if (Color.YELLOW.equals(color)) {
                return Gems.gemsColor.Gold;
            } else if (Color.ORANGE.equals(color))
                return Gems.gemsColor.Gold;
            return null;
        }
        public JLabel getWinnerLabel() {
            return winnerLabel;
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
        
        public void refreshUiLeftPanel(ArrayList<NobleCards> nb, DevelopmentCards[][] dc) {
            leftPanel.removeAll();
            level1CardLst.clear();
            level2CardLst.clear();
            level3CardLst.clear();
            nobleCardLst.clear();
            for (int i=0; i<nb.size(); i++){
                leftPanel.add(createNobleCard(nb.get(i)));
            }
            for (int i=0; i<(5-nb.size()); i++){
                leftPanel.add(createNobleCard());
            }

            // Development Card lv3
            // กองสุ่ม
            leftPanel.add(createShuffleCard(3));
            for(int i=0; i<4; i++) {
                JButton lv3card = createDeveloptmentCard(dc[2][i]);
                leftPanel.add(lv3card);
                level3CardLst.add(lv3card);
            }

            // Development Card lv2
            // กองสุ่ม
            leftPanel.add(createShuffleCard(2));
            for(int i=0; i<4; i++) {
                JButton lv2card = createDeveloptmentCard(dc[1][i]);
                leftPanel.add(lv2card);
                level2CardLst.add(lv2card);
            }
            // Development Card lv1
            // กองสุ่ม
            leftPanel.add(createShuffleCard(1));

            for(int i=0; i<4; i++) {
                JButton lv1card = createDeveloptmentCard(dc[0][i]);
                leftPanel.add(lv1card);
                level1CardLst.add(lv1card);
            }
            leftPanel.revalidate();
            leftPanel.repaint();
        }
        
        public void refreshUiCenterPanel(HashMap<Gems.gemsColor, Integer> bankGems) {
            gemPanel.removeAll();
            gemCardLst.clear();
            Gems.gemsColor[] color = {
                Gems.gemsColor.Black,
                Gems.gemsColor.Blue, 
                Gems.gemsColor.Green, 
                Gems.gemsColor.Red, 
                Gems.gemsColor.White,
                Gems.gemsColor.Gold};
            for (int i=0; i<6; i++) {
                JButton gemCard = new JButton(String.valueOf(bankGems.get(color[i])));
                gemCard.setPreferredSize(new Dimension(60,60));
                
                gemCard.setHorizontalTextPosition(SwingConstants.CENTER);
                gemCard.setVerticalTextPosition(SwingConstants.CENTER);

                gemCard.setFont(new Font("Arial", Font.BOLD, 24)); 
                try {
                    ImageIcon gemPic = new ImageIcon("Gems/coin" + i + ".png");
                    Image sizeImg = gemPic.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH);
                    gemCard.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    gemCard.setText("No Pic");
                }
                gemCardLst.add(gemCard);
                gemPanel.add(gemCard);
            }
            centerPanel.revalidate();
            centerPanel.repaint();
        }
        
        public void refreshUiRightPanel(ArrayList<Player> players) {
            playerspanel.removeAll();

            // อาเรย์สีสำหรับการ์ด และ เหรียญ เพื่อนำไปใช้ในลูป
            Color[] cardColors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.BLACK};
            Color[] gemColors = {Color.ORANGE, Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.BLACK};

            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);

                JPanel playerMainPanel = new JPanel();
                playerMainPanel.setBorder(new LineBorder(Color.WHITE, 2, true));
                playerMainPanel.setLayout(new BorderLayout());
                playerMainPanel.setBackground(Color.GRAY);
                playerMainPanel.setPreferredSize(new Dimension(300, 150));

                // 1. ส่วนหัว (ชื่อผู้เล่น)
                JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                topPanel.setBackground(Color.GRAY);
                JLabel playerNameLabel = new JLabel(currentPlayer.getName());
                playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                playerNameLabel.setForeground(Color.WHITE);
                topPanel.add(playerNameLabel);
                playerMainPanel.add(topPanel, BorderLayout.NORTH);

                // 2. ส่วนกลาง (ช่องใส่ Development Cards)
                JPanel inPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                inPanel.setPreferredSize(new Dimension(300, 70));
                inPanel.setBackground(Color.GRAY);
                
                // --- ลูปสร้างช่องการ์ด 5 สี ---
                for (Color cColor : cardColors) {
                    JPanel cardPanel = new JPanel();
                    cardPanel.setPreferredSize(new Dimension(50, 100));
                    cardPanel.setBackground(cColor);
                    
                    // TODO: ตรงนี้สามารถดึงจำนวนการ์ดจริงๆ จาก currentPlayer ได้ในอนาคต
                    // 
                    JLabel cardCount = new JLabel(String.valueOf(currentPlayer.getBonusGems().get(getGemColor(cColor)))); 
                    if (cColor.equals(Color.BLACK)) {
                        cardCount.setForeground(Color.WHITE); // ถ้าพื้นดำ ให้ตัวหนังสือสีขาว
                    }
                    cardPanel.add(cardCount);
                    inPanel.add(cardPanel);
                }
                playerMainPanel.add(inPanel, BorderLayout.CENTER);

                // 3. ส่วนล่าง (ช่องใส่เหรียญ Gems)
                JPanel lowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                lowPanel.setBackground(Color.GRAY);

                // --- ลูปสร้างช่องเหรียญ 6 สี ---
                for (Color gColor : gemColors) {
                    JPanel gemMiniPanel = new JPanel();
                    gemMiniPanel.setBackground(gColor);
                    
                    // TODO: ดึงจำนวนเหรียญจาก currentPlayer 
                    JLabel gemCount = new JLabel(String.valueOf(currentPlayer.getGems().get(getGemColor(gColor))));
                    if (gColor.equals(Color.BLACK)) {
                        gemCount.setForeground(Color.WHITE);
                    }
                    gemMiniPanel.add(gemCount);
                    lowPanel.add(gemMiniPanel);
                }

                // --- เพิ่มคะแนนรวม (Total Points) ---
                JLabel totalPoints = new JLabel("Total points: " + currentPlayer.getPrestigePoints());
                totalPoints.setForeground(Color.WHITE);
                lowPanel.add(totalPoints);

                playerMainPanel.add(lowPanel, BorderLayout.SOUTH);

                // นำ Panel ของผู้เล่นใส่เข้าไปในหน้าต่างหลัก
                playerspanel.add(playerMainPanel);
                if (i < players.size() - 1) {
                    playerspanel.add(Box.createVerticalStrut(10));
                }
            }

            JPanel reservepanel = new JPanel();
            reservepanel.setBorder(new LineBorder(Color.WHITE, 2, true));
            reservepanel.setLayout(new BorderLayout());
            reservepanel.setBackground(Color.GRAY);
            reservepanel.setPreferredSize(new Dimension(300, 220));

            JPanel topreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topreserve.setBackground(Color.GRAY);
            reservepanel.add(topreserve, BorderLayout.NORTH);
            JLabel reservecards = new JLabel("Reserve Cards");
            reservecards.setForeground(Color.WHITE);
            reservecards.setFont(new Font("Arial", Font.PLAIN, 15));
            topreserve.add(reservecards);

            JPanel inreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inreserve.setPreferredSize(new Dimension(300, 220));
            inreserve.setBackground(Color.GRAY);
            reservepanel.add(inreserve, BorderLayout.CENTER);

            JPanel lowreserve = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lowreserve.setBackground(Color.GRAY);
            reservepanel.add(lowreserve, BorderLayout.SOUTH);

            // ใช้ลูปสร้างปุ่ม Reserve Cards 3 ใบ เพื่อลดความซ้ำซ้อนของโค้ด
            for (int i = 1; i <= 3; i++) {
                JButton reserveCard = new JButton();
                reserveCard.setPreferredSize(new Dimension(70, 85));
                try {
                    ImageIcon reservePic = new ImageIcon("การ์ดสำรอง" + i + ".jpg");
                    // เพิ่มโค้ดปรับสเกลรูปภาพให้พอดีกับปุ่ม
                    Image sizeImg = reservePic.getImage().getScaledInstance(70, 85, Image.SCALE_SMOOTH);
                    reserveCard.setIcon(new ImageIcon(sizeImg));
                } catch (Exception e) {
                    reserveCard.setText("No Pic");
                }
                inreserve.add(reserveCard);
            }

            playerspanel.add(reservepanel);

            playerspanel.revalidate();
            playerspanel.repaint();
        }

        public static void main(String[] args) {
            new GameWindow();
        }
    }