package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34552;

    public static void createClientSocket() {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
//            Scanner scanner = new Scanner(System.in);
//            String msg = scanner.nextLine();

            String msg = "Give me a record # 12";
            output.writeUTF(msg); // sending message to the server
            System.out.println("Sent: " + msg);
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with client");
        }
    }
}
