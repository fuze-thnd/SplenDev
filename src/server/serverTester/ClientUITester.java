//package server.serverTester;
//
//import server.PlayerHandler;
//import server.RoomHandler;
//import shared.DevelopmentCards;
//import shared.Gems;
//import shared.NobleCards;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class ClientUITester implements ActionListener {
//    private JFrame frame;
//    private JPanel mainPanel;
//    private CardLayout cardLayout;
//    private JPanel enterNamePage, roomListPage,lobbyPage,scrollPanel, lobbyPlayerListPanel, gamePage, playersPanel, tokensPanel, developmentCardsPanel;
//    private JTextField enterNameTextField;
//    private JButton enterNameButton, enterCodeButton, createRoomButton, refreshButton, disconnectButton;
//    private JTextArea logTextArea;
//    private ObjectOutputStream outToServer;
//    private ObjectInputStream inFromServer;
//    private Thread listeningThread;
//    private Socket socket;
//    private boolean isRunning = false;
//    private String name;
//    private ArrayList<RoomHandler> rooms;
//    private RoomHandler currentRoom;
//
//    public ClientUITester(){
//        frame = new JFrame("Client UI Tester");
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//
//        enterNamePage = createEnterNamePage();
//        roomListPage = createRoomListPage();
//        lobbyPage = createLobbyPage();
//        gamePage = createGamePage();
//
//        mainPanel.add(enterNamePage, "login_page");
//        mainPanel.add(roomListPage, "room_list_page");
//        mainPanel.add(lobbyPage, "lobby_page");
//        mainPanel.add(gamePage, "game_page");
//
//        frame.add(mainPanel);
//        cardLayout.show(mainPanel, "login_page");
//        frame.setSize(1500, 1000);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        frame.setResizable(false);
//    }
//
//    private void joinServer(){
//        try{
//            socket = new Socket("localhost", 8080);
//            outToServer = new ObjectOutputStream(socket.getOutputStream());
//            outToServer.flush();
//            inFromServer = new ObjectInputStream(socket.getInputStream());
//
//            isRunning = true;
//            sendCommandToServer("INIT:" + this.name);
//            startListeningThread();
//        }catch (IOException e){
//            JOptionPane.showMessageDialog(frame, "Could not join server: " + e.toString());
//        }
//    }
//
//    private void refreshGamePage(){
////        GamePage
////        PlayerPanel
//
//        playersPanel.removeAll();
//        for(PlayerHandler p : currentRoom.getPlayersHandler()){
//            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//            panel.setBackground(Color.GRAY);
//            panel.setPreferredSize(new Dimension(530, 210));
//            JLabel playerNameLabel = new JLabel(p.getName());
//            playerNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//            panel.add(playerNameLabel);
//            playersPanel.add(panel);
//        }
//
//        playersPanel.revalidate();
//        playersPanel.repaint();
//
////        TokenPanel
//
//        tokensPanel.removeAll();
//        Color[] tokensColor = { Color.WHITE,Color.BLUE, Color.GREEN, Color.RED, Color.BLACK, Color.YELLOW  };
//
//        for (int i = 0;i < 6;i++){
//            JPanel panel = new JPanel(new BorderLayout());
//            JLabel label = new JLabel("20", SwingConstants.CENTER);
//
//            label.setText(currentRoom.getGems().get(getGemColor(tokensColor[i])).toString());
//
//            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//            panel.add(label, BorderLayout.CENTER);
//            panel.setBackground(tokensColor[i]);
//            panel.setPreferredSize(new Dimension(80, 80));
//            tokensPanel.add(panel);
//        }
//
//        tokensPanel.revalidate();
//        tokensPanel.repaint();
//
////        Noble card
//        developmentCardsPanel.removeAll();
//        for(NobleCards nc : currentRoom.getShowNobleCard()){
//            developmentCardsPanel.add(createNobleCard(nc));
//        }
//
//        //        Dvclv3 Card
//        developmentCardsPanel.add(createDevelopmentCardsPanel());
//
//        for(DevelopmentCards dc : currentRoom.getShowCardsLv3()){
//            developmentCardsPanel.add(createDevelopmentCardsPanel(dc));
//        }
//
//        //        Dvclv2 Card
//        developmentCardsPanel.add(createDevelopmentCardsPanel());
//        for(DevelopmentCards dc : currentRoom.getShowCardsLv2()){
//            developmentCardsPanel.add(createDevelopmentCardsPanel(dc));
//        }
//
//        //        Dvclv1 Card
//        developmentCardsPanel.add(createDevelopmentCardsPanel());
//        for(DevelopmentCards dc : currentRoom.getShowCardsLv1()){
//            developmentCardsPanel.add(createDevelopmentCardsPanel(dc));
//        }
//
//        developmentCardsPanel.revalidate();
//        developmentCardsPanel.repaint();
//    }
//
//    private Color getGemColor(Gems.gemsColor color){
//        switch (color){
//            case White:
//                return Color.WHITE;
//            case Black:
//                return Color.BLACK;
//            case Blue:
//                return Color.BLUE;
//            case Green:
//                return Color.GREEN;
//            case Red:
//                return Color.RED;
//            case Gold:
//                return Color.YELLOW;
//        }
//
//        return null;
//    }
//
//    private Gems.gemsColor getGemColor(Color color) {
//        if (Color.WHITE.equals(color)) {
//            return Gems.gemsColor.White;
//        } else if (Color.BLACK.equals(color)) {
//            return Gems.gemsColor.Black;
//        } else if (Color.BLUE.equals(color)) {
//            return Gems.gemsColor.Blue;
//        } else if (Color.GREEN.equals(color)) {
//            return Gems.gemsColor.Green;
//        } else if (Color.RED.equals(color)) {
//            return Gems.gemsColor.Red;
//        } else if (Color.YELLOW.equals(color)) {
//            return Gems.gemsColor.Gold;
//        }
//
//        return null;
//    }
//
//    private JPanel createNobleCard(NobleCards nc){
//        NobleCardJPanel panel = new NobleCardJPanel(new BorderLayout());
//        panel.setImage(nc.getImageName());
//        panel.setPreferredSize(new Dimension(150, 150));
//
//        OutlineJLabel pointsLabel = new OutlineJLabel(nc.getPrestigePoints() + "", SwingConstants.LEFT);
//        pointsLabel.setOutlineColor(Color.black);
//        pointsLabel.setForeground(Color.WHITE);
//        pointsLabel.setOpaque(false);
//        pointsLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//        pointsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
//        panel.add(pointsLabel, BorderLayout.NORTH);
//
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
//        bottomPanel.setOpaque(false);
//
//        for(Gems.gemsColor color : Gems.gemsColor.values()){
//            if(color.equals(Gems.gemsColor.Gold)) continue;
//
//            Integer costAmount = (Integer) nc.getCost().get(color);
//            if(costAmount != null && costAmount > 0){
//                JLabel label = new JLabel(costAmount + "", SwingConstants.CENTER);
//                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
//
//                label.setPreferredSize(new Dimension(25, 35));
//                label.setBackground(getGemColor(color));
//                label.setOpaque(true);
//
//                if(color.equals(Gems.gemsColor.Blue) || color.equals(Gems.gemsColor.Black)){
//                    label.setForeground(Color.WHITE);
//                }else{
//                    label.setForeground(Color.BLACK);
//                }
//
//                bottomPanel.add(label);
//            }
//        }
//        panel.add(bottomPanel, BorderLayout.SOUTH);
//        return panel;
//    }
//
//    private JPanel createNobleCard(){
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        panel.setBackground(Color.white);
//        panel.setPreferredSize(new Dimension(150, 150));
//        return panel;
//    }
//
//    private JPanel createDevelopmentCardsPanel(){
//        ShuffleCardJPanel panel = new ShuffleCardJPanel(new FlowLayout(FlowLayout.LEFT));
//        panel.setBackground(Color.white);
//        panel.setPreferredSize(new Dimension(150, 210));
//        return panel;
//    }
//
//    private JPanel createDevelopmentCardsPanel(DevelopmentCards dc){
//        DevelopmentCardJPanel panel = new DevelopmentCardJPanel(new BorderLayout());
//        panel.setImage(dc.getImageName());
//        panel.setBackground(Color.white);
//        panel.setPreferredSize(new Dimension(150, 210));
//
//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setOpaque(false);
//
//        if (dc.getPrestigePoints() > 0) {
//            OutlineJLabel pointsLabel = new OutlineJLabel(dc.getPrestigePoints() + "", SwingConstants.LEFT);
//            pointsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
//            pointsLabel.setOutlineColor(Color.black);
//            pointsLabel.setForeground(Color.WHITE);
//            pointsLabel.setOpaque(false);
//            pointsLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
//            topPanel.add(pointsLabel, BorderLayout.WEST);
//        }
//
//        JPanel bonusPanel = new JPanel(new BorderLayout());
//        bonusPanel.setPreferredSize(new Dimension(40, 40));
//        bonusPanel.setBackground(getGemColor(dc.getGemsColor()));
//        bonusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        topPanel.add(bonusPanel, BorderLayout.EAST);
//
//        panel.add(topPanel, BorderLayout.NORTH);
//
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
//        bottomPanel.setOpaque(false);
//
//        for (Gems.gemsColor color : Gems.gemsColor.values()) {
//            if (color.equals(Gems.gemsColor.Gold)) continue;
//
//            Integer costAmount = (Integer) dc.getCost().get(color);
//            if (costAmount != null && costAmount > 0) {
//                JLabel label = new JLabel(costAmount + "", SwingConstants.CENTER);
//                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
//                label.setPreferredSize(new Dimension(25, 35));
//                label.setBackground(getGemColor(color));
//                label.setOpaque(true);
//                if(color.equals(Gems.gemsColor.Blue) || color.equals(Gems.gemsColor.Black)){
//                    label.setForeground(Color.WHITE);
//                }else{
//                    label.setForeground(Color.BLACK);
//                }
//
//                bottomPanel.add(label);
//            }
//        }
//        panel.add(bottomPanel, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    private JPanel createGamePage(){
//        JPanel gamePagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//        developmentCardsPanel = new  JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
//        developmentCardsPanel.setBackground(Color.LIGHT_GRAY);
//        developmentCardsPanel.setPreferredSize(new Dimension(800, 1000));
//
//        //        Noble Card
//        for(int i = 0;i < 5;i++){
//            developmentCardsPanel.add(createNobleCard());
//        }
//
//        //        Dvclv3 Card
//        for(int i = 0;i < 5;i++){
//            developmentCardsPanel.add(createDevelopmentCardsPanel());
//        }
//
//        //        Dvclv2 Card
//        for(int i = 0;i < 5;i++){
//            developmentCardsPanel.add(createDevelopmentCardsPanel());
//        }
//
//        //        Dvclv1 Card
//        for(int i = 0;i < 5;i++){
//            developmentCardsPanel.add(createDevelopmentCardsPanel());
//        }
//
//        tokensPanel = new  JPanel();
//        tokensPanel.setBackground(Color.LIGHT_GRAY);
//        tokensPanel.setPreferredSize(new Dimension(100, 1000));
//
//        Color[] tokensColor = { Color.WHITE,Color.BLUE, Color.GREEN, Color.RED, Color.BLACK, Color.YELLOW  };
//
//        for (int i = 0;i < 6;i++){
//            JPanel panel = new JPanel(new BorderLayout());
//            JLabel label = new JLabel("20", SwingConstants.CENTER);
//            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//            panel.add(label, BorderLayout.CENTER);
//            panel.setBackground(tokensColor[i]);
//            panel.setPreferredSize(new Dimension(80, 80));
//            tokensPanel.add(panel);
//        }
//
//        playersPanel = new  JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
//        playersPanel.setBackground(Color.LIGHT_GRAY);
//        playersPanel.setPreferredSize(new Dimension(560, 1000));
//
//        for(int i = 1;i < 5;i++){
//            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//            panel.setBackground(Color.GRAY);
//            panel.setPreferredSize(new Dimension(530, 210));
//            JLabel playerNameLabel = new JLabel("Player" + i);
//            playerNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//            panel.add(playerNameLabel);
//            playersPanel.add(panel);
//        }
//
//        gamePagePanel.add(developmentCardsPanel);
//        gamePagePanel.add(tokensPanel);
//        gamePagePanel.add(playersPanel);
//        return gamePagePanel;
//    }
//
//    private void refreshRoomList(){
//        scrollPanel.removeAll();
//
//        for (RoomHandler room : rooms) {
//            RoomListItem ri = new RoomListItem(room.getRoomName(), room.getCode(), this);
//            scrollPanel.add(ri);
//            scrollPanel.add(Box.createVerticalStrut(10));
//        }
//
//        scrollPanel.revalidate();
//        scrollPanel.repaint();
//
//        System.out.println("Refreshed room list.");
//    }
//
//    private void startListeningThread(){
//        listeningThread = new Thread(() -> {
//            try{
//                while(isRunning){
//                    Object object = inFromServer.readObject();
//
//                    if(object instanceof String command){
//                        handleCommandFromServer(command);
//                    }
//                }
//            }catch (IOException | ClassNotFoundException e){
//                JOptionPane.showMessageDialog(frame, "Error occurred: " + e);
//            }
//        });
//
//        listeningThread.start();
//    }
//
//    private void handleCommandFromServer(String command) throws IOException, ClassNotFoundException {
//        String[] parts = command.split(":");
//
//        switch (parts[0]){
//            case "MESSAGE":
//                JOptionPane.showMessageDialog(frame, parts[1]);
//                break;
//            case "KICK":
//                cardLayout.show(mainPanel, "room_list_page");
//                JOptionPane.showMessageDialog(frame, "You got kicked from the room!");
//                break;
//            case "CONNECTED":
//                cardLayout.show(mainPanel, "room_list_page");
//                break;
//            case "START_GAME":
//                cardLayout.show(mainPanel, "game_page");
//                refreshGamePage();
//                break;
//            case "REFRESH_ROOM":
//                Object object = inFromServer.readObject();
//                if(object instanceof ArrayList<?> r){
//                    rooms =  (ArrayList<RoomHandler>) r;
//                    System.out.println(rooms.size());
//                }
//
//                SwingUtilities.invokeLater(this::refreshRoomList);
//                break;
//            case "LOBBY_LOG_MESSAGE":
//                this.logTextArea.setText(logTextArea.getText() + parts[1] + "\n");
//                break;
//            case "REFRESH_LOBBY":
//                currentRoom = (RoomHandler) inFromServer.readObject();
//                refreshLobbyData();
//                break;
//            case "JOIN_ROOM_SUCCESS":
//                currentRoom = (RoomHandler) inFromServer.readObject();
//                cardLayout.show(mainPanel, "lobby_page");
//                System.out.println("joined room successfully");
//                SwingUtilities.invokeLater(this::refreshLobbyData);
//                System.out.println(currentRoom.getShowNobleCard());
//                break;
//            case "ERROR":
//                JOptionPane.showMessageDialog(frame, parts[1], "Error", JOptionPane.WARNING_MESSAGE);
//                break;
//        }
//    }
//
//    public void joinRoom(String code){
//        logTextArea.setText("");
//        sendCommandToServer("JOIN:" + code);
//    }
//
//    private void refreshLobbyData(){
//        lobbyPlayerListPanel.removeAll();
//
//        for(PlayerHandler p : currentRoom.getPlayersHandler()){
//            JPanel playerItemPanel = new JPanel(new BorderLayout());
//            playerItemPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//            playerItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
//            JLabel label = new JLabel(p.getName());
//            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
//
//            playerItemPanel.add(label, BorderLayout.WEST);
//            lobbyPlayerListPanel.add(playerItemPanel);
//            lobbyPlayerListPanel.add(Box.createVerticalStrut(10));
//        }
//
//        lobbyPlayerListPanel.revalidate();
//        lobbyPlayerListPanel.repaint();
//    }
//
//    private void sendCommandToServer(String command){
//        try{
//            outToServer.writeObject(command);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(frame, e.toString());
//        }
//    }
//
//    private JPanel createRoomListPage(){
//        JPanel roomListPanel = new JPanel(new BorderLayout());
//        scrollPanel = new JPanel();
//        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
//        scrollPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        for (int i = 1; i <= 5; i++) {
//            RoomListItem ri = new RoomListItem("Room " + i, "ANSK2", this);
//            scrollPanel.add(ri);
//            scrollPanel.add(Box.createVerticalStrut(10));
//        }
//        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        enterCodeButton = new JButton("Enter Code");
//        enterCodeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
//        enterCodeButton.addActionListener(this);
//
//        createRoomButton = new JButton("Create Room");
//        createRoomButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
//        createRoomButton.addActionListener(this);
//
//        refreshButton = new JButton("Refresh");
//        refreshButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
//        refreshButton.addActionListener(this);
//
//        buttonsPanel.add(enterCodeButton);
//        buttonsPanel.add(createRoomButton);
//        buttonsPanel.add(refreshButton);
//
//        roomListPanel.add(scrollPane, BorderLayout.CENTER);
//        roomListPanel.add(buttonsPanel, BorderLayout.SOUTH);
//        return roomListPanel;
//    }
//
//    private JPanel createLobbyPage(){
//        JPanel lobbyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//        JPanel leftPanel = new JPanel(new BorderLayout());
//        leftPanel.setPreferredSize(new Dimension(730, 950));
//        JLabel playerLabel = new JLabel("Player");
//        playerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//        lobbyPlayerListPanel = new JPanel();
//        lobbyPlayerListPanel.setLayout(new BoxLayout(lobbyPlayerListPanel, BoxLayout.Y_AXIS));
//        lobbyPlayerListPanel.setBackground(Color.LIGHT_GRAY);
//        lobbyPlayerListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//        leftPanel.add(playerLabel, BorderLayout.NORTH);
//        leftPanel.add(lobbyPlayerListPanel, BorderLayout.CENTER);
//
//        for(int i = 1; i < 5;i++){
//            JPanel playerItemPanel = new JPanel(new BorderLayout());
//            playerItemPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//            playerItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
//            JLabel label = new JLabel("Player " + i);
//            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
//
//            playerItemPanel.add(label, BorderLayout.WEST);
//            lobbyPlayerListPanel.add(playerItemPanel);
//            lobbyPlayerListPanel.add(Box.createVerticalStrut(10));
//        }
//
//
//        JPanel rightPanel = new JPanel(new BorderLayout());
//        rightPanel.setPreferredSize(new Dimension(730, 950));
//        JLabel logLabel = new JLabel("Log");
//        logLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//        logTextArea = new  JTextArea();
//        logTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
//        logTextArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//        logTextArea.setEditable(false);
//
//        JPanel buttonsPanel = new  JPanel(new FlowLayout(FlowLayout.LEFT));
//        disconnectButton = new JButton("Disconnect");
//        disconnectButton.addActionListener(this);
//        disconnectButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
//        buttonsPanel.add(disconnectButton);
//
//        rightPanel.add(logLabel, BorderLayout.NORTH);
//        rightPanel.add(logTextArea, BorderLayout.CENTER);
//        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);
//
//        lobbyPanel.add(leftPanel);
//        lobbyPanel.add(rightPanel);
//        return lobbyPanel;
//    }
//
//    private JPanel createEnterNamePage(){
//        JPanel enterNamePage = new JPanel(new GridBagLayout());
//
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
//        inputPanel.setSize(500, 500);
//        JLabel enterNameLabel = new JLabel("Please enter your name");
//        enterNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//        enterNameTextField = new JTextField(20);
//        enterNameTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
//        enterNameButton = new JButton("Enter");
//        enterNameButton.addActionListener(this);
//        enterNameButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
//        enterNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enterNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enterNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        inputPanel.add(enterNameLabel);
//        inputPanel.add(Box.createVerticalStrut(10));
//        inputPanel.add(enterNameTextField);
//        inputPanel.add(Box.createVerticalStrut(10));
//        inputPanel.add(enterNameButton);
//
//        enterNamePage.add(inputPanel);
//        return enterNamePage;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource().equals(enterCodeButton)){
//            String code = JOptionPane.showInputDialog(frame, "Enter Room Code", "Enter Room Code", JOptionPane.PLAIN_MESSAGE);
//            if(!code.isEmpty()){
//                logTextArea.setText("");
//                joinRoom(code);
//            }
//        }else if(e.getSource().equals(createRoomButton)){
//            String roomName = JOptionPane.showInputDialog(frame, "Create Room", "Enter Room Name", JOptionPane.PLAIN_MESSAGE);
//            if (!roomName.isEmpty()) {
//                logTextArea.setText("");
//                sendCommandToServer("CREATE_ROOM:" + roomName);
//            }
//        }else if(e.getSource().equals(enterNameButton)){
//            if(!enterNameTextField.getText().isEmpty()){
//                this.name =  enterNameTextField.getText();
//                joinServer();
//            }
//        }else if(e.getSource().equals(disconnectButton)){
//            sendCommandToServer("DISCONNECT_FROM_ROOM");
//            cardLayout.show(mainPanel, "room_list_page");
//        }
//    }
//
//    public static void main(String[] args) {
//        new ClientUITester();
//    }
//}
