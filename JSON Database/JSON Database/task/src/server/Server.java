package server;

import util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static util.JParserArguments.*;

public class Server {
    private static final int PORT = 34552;
    private static HashMap<Integer, String> db = new HashMap<>(1000);

    public static void createServerSocket() {
        Controller controller = new Controller();
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            boolean isServerActive = true;
            while (isServerActive) {
                try (
                        Socket socket = server.accept();
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = inputStream.readUTF();
                    String outputMsg;
                    if (!msg.equals("-t;exit")) {
                        Command command;
                        parse(msg.split(";"));
                        if (Integer.parseInt(getIndexCell()) < 1000 & Integer.parseInt(getIndexCell()) > 0) {
                            if (getTypeRequest().equals("get")) {
                                command = new Get(db, Integer.parseInt(getIndexCell()));
                                controller.setCommand(command);
                                controller.executeCommand();
                                if (Get.result.equals("")) {
                                    outputStream.writeUTF("ERROR");
                                } else outputStream.writeUTF(Get.result);

                            } else if (getTypeRequest().equals("set")) {
                                command = new Set(db, Integer.parseInt(getIndexCell()), getValueForDataBase());
                                controller.setCommand(command);
                                controller.executeCommand();
                                outputStream.writeUTF("OK");
                            } else if (getTypeRequest().equals("delete")) {
                                command = new Delete(db, Integer.parseInt(getIndexCell()));
                                controller.setCommand(command);
                                controller.executeCommand();
                                outputStream.writeUTF("OK");
                            }
                        } else outputStream.writeUTF("ERROR");

                    } else {
                        System.out.println("OK");
                        isServerActive = false;
                    }
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Problem with server");
        }
    }
}
