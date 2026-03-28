package client;

import shared.NetworkMessage;
import java.io.*;
import java.net.Socket;
import server.RoomHandler;
import client.lobby.LobbyWindow;

public class GameClient {
    // 🌟 1. สร้างตัวแปร static เพื่อเก็บ Client ตัวเดียวของระบบ
    private static GameClient instance;
    private LobbyWindow currentLobbyWindow;
    
    public void setLobbyWindow(LobbyWindow window) {
        this.currentLobbyWindow = window;
    }

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean connected = false;

    // 🌟 2. เมธอดสำหรับเรียกใช้ Client จากหน้าต่างไหนก็ได้
    public static GameClient getInstance() {
        if (instance == null) {
            instance = new GameClient();
        }
        return instance;
    }

    // เมธอดเชื่อมต่อ
    public void connect(String playerName) {
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;

            // ส่งชื่อไปหา Server ทันทีที่ต่อติด
            sendToServer("INIT:"+playerName);

            // (ส่วนของการรับข้อมูลจาก Server แนะนำให้ทำเป็น Thread แยกไว้ต่างหากเมื่อพร้อมครับ)
            System.out.println("Connected to server as: " + playerName);
            startListening();
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    
    private void startListening() {
        new Thread(() -> {
            try {
                while (connected) {
                    // รอรับข้อมูลจาก Server ตลอดเวลา
                    Object obj = in.readObject();

                    // --- กรณีที่ 1: รับข้อความแบบ String (เช่น ระบบ Lobby) ---
                    if (obj instanceof String) {
                        String message = (String) obj;
                        System.out.println("[Server -> Client] ข้อความ: " + message);
                        
                        // ตัวอย่าง: ถ้า Server บอกให้เริ่มเกม
                        if (message.equals("START_GAME")) {
                            System.out.println("🎉 เย้! เซิร์ฟเวอร์สั่งให้เริ่มเกมแล้ว!");
                            // TODO: สั่งเปิดหน้าต่าง GameWindow ตรงนี้
                        }
                    } 
                    // --- กรณีที่ 2: รับข้อความแบบ NetworkMessage (เช่น ตอนเล่นเกม) ---
                    else if (obj instanceof NetworkMessage) {
                        NetworkMessage msg = (NetworkMessage) obj;
                        System.out.println("[Server -> Client] คำสั่งเกม: " + msg.getCommand());
                        
                        if (msg.getCommand().equals("UPDATE_GAME_STATE")) {
                            System.out.println("-> ได้รับข้อมูลกระดานเกมใหม่แล้ว");
                        }
                    } else if (obj instanceof RoomHandler) {
                        RoomHandler room = (RoomHandler) obj;
                        System.out.println("[Server -> Client] อัปเดตข้อมูลห้อง: " + room.getRoomName());
                        
                        if (currentLobbyWindow != null) {
                            currentLobbyWindow.updateRoomData(room);
                        }
                    }
                    // --- กรณีที่ 3: รับ Object อื่นๆ (เช่น ตอน Server ส่งข้อมูลห้องมา) ---
                    else {
                        System.out.println("[Server -> Client] ได้รับ Object ชนิด: " + obj.getClass().getSimpleName());
                    }
                }
            } catch (Exception e) {
                System.err.println("การเชื่อมต่อถูกตัดขาด: " + e.getMessage());
                connected = false;
            }
        }).start(); // เริ่มรัน Thread
    }

    // เมธอดสำหรับส่งข้อมูล
    public void sendToServer(NetworkMessage msg) {
        if (connected && out != null) {
            try {
                out.reset();
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                System.err.println("Send Error: " + e.getMessage());
            }
        }
    }
    
    public void sendToServer(String msg) {
        if (connected && out != null) {
            try {
                out.reset();
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                System.err.println("Send Error: " + e.getMessage());
            }
        }
    }
}