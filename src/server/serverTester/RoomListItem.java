//package server.serverTester;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class RoomListItem extends JPanel implements ActionListener {
//    private JButton joinButton;
//    private ClientUITester client;
//    private String code;
//
//    public RoomListItem(String roomName, String code, ClientUITester client){
//        this.client = client;
//        this.code = code;
//        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setLayout(new BorderLayout());
//        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
//        this.setBackground(Color.LIGHT_GRAY);
//
//        JLabel label = new JLabel("Room name: " + roomName + " | Code: " + code);
//        joinButton = new JButton("Join");
//        joinButton.addActionListener(this);
//
//        this.add(label, BorderLayout.WEST);
//        this.add(joinButton, BorderLayout.EAST);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource().equals(joinButton)) {
//            client.joinRoom(code);
//        }
//    }
//}
