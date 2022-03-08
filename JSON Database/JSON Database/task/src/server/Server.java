package server;

import client.Client;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 34552;

    public static void createServerSocket() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            //while (true)
                try (
                        Socket socket = server.accept();
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = inputStream.readUTF();
                    System.out.println("Received: " + msg);
                    String outputMsg = "A record # 12 was sent!";
                    outputStream.writeUTF(outputMsg);
                    System.out.println("Sent: " + outputMsg);
                }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Problem with server");
        }
    }
}
