package server.serverTester;

import server.RoomHandler;
import shared.DevelopmentCards;
import shared.NobleCards;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientTester {
    private Socket socket;
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;
    private Thread readThread;
    private String name;
    private boolean isRunning = true;
    private Scanner sc;
    private ArrayList<DevelopmentCards> developmentCards;
    private ArrayList<NobleCards> nobleCards;

    public ClientTester(String name) {
        this.sc = new Scanner(System.in);
        this.name = name;
    }

    private void start(){
        try{
            socket = new Socket("localhost", 8080);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            outToServer.flush();
            inFromServer = new ObjectInputStream(socket.getInputStream());
            log("Connected to server");

            startListeningThread();

            sendCommandToServer("INIT:" + this.name);

            System.out.print("> ");
            while(true){
                String command = sc.nextLine();
                sendCommandToServer(command);
                System.out.print("> ");
            }
        }catch (IOException e){
            error(e.toString());
            startListeningThread();
        }
    }

    private void sendCommandToServer(String command){
        try{
            outToServer.writeObject(command);
        } catch (IOException e) {
            error(e.toString());
        }
    }

    private void startListeningThread(){
        readThread = new Thread(() -> {
            try{
                while(isRunning){
                    Object object = inFromServer.readObject();

                    if(object instanceof String command){
                        handleCommandFromServer(command);
                    }else if(object instanceof ArrayList<?> r){
                        log("Received list of items from server size " + r.size());

                        for(int i = 0;i < r.size();i++){
                            if (r.get(i) instanceof RoomHandler room){
                                System.out.println(i + 1 + ". Name:" + room.getRoomName() + " Code: " + room.getCode());
                            }
                        }
                    }
                }
            }catch (IOException | ClassNotFoundException e){
                error(e.toString());
            }
        });
        readThread.start();
    }

    private void handleCommandFromServer(String command){
        String[] parts = command.split(":");

        switch (parts[0]){
            case "MESSAGE":
                log(parts[1]);
                break;
            case "ERROR":
                error(parts[1]);
                break;
        }
    }

    private void error(String message){
        System.out.println("[ Client Error ] " + message);
        System.out.print("> ");
    }

    public void log(String message){
        System.out.println("[ Client ] " + message);
        System.out.print("> ");
    }

    public static void main(String[] args) {
        System.out.print("Please enter your name: ");
        String name = new Scanner(System.in).nextLine();

        ClientTester tester = new ClientTester(name);
        tester.start();
    }
}
