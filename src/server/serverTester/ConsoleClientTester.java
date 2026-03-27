package server.serverTester;

import shared.NetworkMessage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleClientTester {
    // 🌟 เพิ่มตัวแปรเก็บชื่อตัวเอง
    private static String myName = "Unknown";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to Server!");

            // Thread ดักฟังข้อมูลจาก Server
            new Thread(() -> {
                try {
                    while (true) {
                        Object obj = in.readObject();

                        if (obj instanceof NetworkMessage) {
                            NetworkMessage msg = (NetworkMessage) obj;

                            if (msg.getCommand().equals("UPDATE_GAME_STATE")) {
                                shared.GameState state = (shared.GameState) msg.getData();

                                System.out.println("\n======= CARDS ON BOARD =======");
                                shared.DevelopmentCards[][] board = state.getCardsOnBoard();
                                for (int i = 0; i < 3; i++) {
                                    System.out.println("--- Level " + (i+1) + " ---");
                                    for (int j = 0; j < 4; j++) {
                                        shared.DevelopmentCards card = board[i][j];
                                        if (card != null) {
                                            System.out.println("ID: " + card.getId() +
                                                    " | โบนัส: " + card.getGemsColor() +
                                                    " | ราคา: " + card.getCost());
                                        }
                                    }
                                }

                                // 🌟 ส่วนที่เพิ่มใหม่: โชว์ข้อมูลของตัวเอง 🌟
                                System.out.println("\n======= MY STATUS (" + myName + ") =======");
                                boolean foundMe = false;
                                for (client.Player p : state.getPlayers()) {
                                    if (p.getName().equals(myName)) {
                                        foundMe = true;
                                        System.out.println("แต้ม (Prestige Points): " + p.getPrestigePoints());
                                        System.out.println("เหรียญในมือ (Gems): " + p.getGems());
                                        System.out.println("โบนัสการ์ด (Bonus): " + p.getBonusGems());

                                        // โชว์การ์ดที่จองไว้
                                        System.out.println("การ์ดที่จองไว้ (Reserved): " + p.getReservedCard().size() + " ใบ");
                                        for (shared.DevelopmentCards rc : p.getReservedCard()) {
                                            System.out.println("   -> [จอง] ID: " + rc.getId() + " | ราคา: " + rc.getCost());
                                        }
                                        break;
                                    }
                                }
                                if (!foundMe) {
                                    System.out.println("ยังไม่พบข้อมูลผู้เล่น (คุณได้พิมพ์ INIT หรือยัง?)");
                                }
                                System.out.println("=========================================\n");
                            }
                            else {
                                System.out.println("\n[SERVER -> ME] NetworkMessage: " + msg.getCommand());
                            }

                        }
                        else if (obj instanceof String) {
                            System.out.println("\n[SERVER -> ME] String: " + obj);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Type command to send (e.g., INIT:Fuse, CREATE_ROOM:Room1, TAKE_3_GEMS:Red:Blue:Black)");

            while (true) {
                String input = scanner.nextLine();
                if (input.isEmpty()) continue;

                String[] parts = input.split(":");
                String command = parts[0];

                try {
                    // 🌟 ดักจำชื่อตัวเองเอาไว้ตอนพิมพ์ INIT
                    if (command.equals("INIT") && parts.length > 1) {
                        myName = parts[1];
                    }

                    // ส่งคำสั่งเกม
                    if (command.equals("TAKE_3_GEMS") || command.equals("BUY_CARD") || command.equals("TAKE_2_GEMS") || command.equals("RESERVE_CARD")) {
                        ArrayList<String> dataList = new ArrayList<>();
                        for (String part : parts) {
                            dataList.add(part);
                        }
                        out.writeObject(new NetworkMessage(command, dataList));
                        System.out.println("[ME -> SERVER] Sent Game Action: " + command);
                    }
                    // ส่งคำสั่ง Lobby
                    else {
                        out.writeObject(input);
                        System.out.println("[ME -> SERVER] Sent Lobby Command: " + input);
                    }
                } catch (Exception e) {
                    System.out.println("Error sending message.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}