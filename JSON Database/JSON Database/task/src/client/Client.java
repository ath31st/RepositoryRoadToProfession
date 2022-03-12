package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.JParserArguments;

import java.io.*;
import java.net.Socket;

import static util.JParserArguments.*;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34552;

    public static void createClientSocket(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            String jsonGsonObject;
            JParserArguments.parse(args);

            if (getFileRequest() == null) {
                GsonClientObject gsonClientObject = new GsonClientObject(getTypeRequest(), getKey(), getValueForDataBase());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder
                        //.setPrettyPrinting()
                        //.serializeNulls()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                jsonGsonObject = gson.toJson(gsonClientObject);
            } else {
                FileReader fileReader = new FileReader("src/client/data/" + getFileRequest());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                jsonGsonObject = bufferedReader.readLine();
            }

            output.writeUTF(jsonGsonObject); // sending message to the server
            System.out.println("Sent: " + jsonGsonObject);
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with client");
        }
    }
}
