package server;

import client.GsonClientObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static final int PORT = 34552;
    private static HashMap<String, String> db = new HashMap<>();
    static boolean isServerActive = true;

    public static void setIsServerActive(boolean isServerActive) {
        Server.isServerActive = isServerActive;
    }

    public static void createServerSocket() {
        Controller controller = new Controller();
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");

            while (isServerActive) {
                try (
                        Socket socket = server.accept();
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
                ) {
                    GsonClientObject gsonClientObject = new Gson().fromJson(inputStream.readUTF(), GsonClientObject.class);
                    GsonServerObject gsonServerObject = new GsonServerObject();
                    controller.setCommand(switchCommand(gsonClientObject, gsonServerObject));
                    controller.executeCommand();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder
                            //.setPrettyPrinting()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create();
                    outputStream.writeUTF(gson.toJson(gsonServerObject));
                }
            }
        } catch (
                IOException e) {
            //e.printStackTrace();
            System.out.println("Problem with server");
        }
    }

    public static Command switchCommand(GsonClientObject gsonClientObject, GsonServerObject gsonServerObject) {
        Command command;
        if (gsonClientObject.getType().equals("get")) {
            return command = new GetCommand(db, gsonClientObject, gsonServerObject);
        } else if (gsonClientObject.getType().equals("set")) {
            return command = new SetCommand(db, gsonClientObject, gsonServerObject);
        } else if (gsonClientObject.getType().equals("delete")) {
            return command = new DeleteCommand(db, gsonClientObject, gsonServerObject);
        } else {
            return command = new ExitCommand();
        }
    }
}
