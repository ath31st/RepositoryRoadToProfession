package metro.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class ReadSubwayData {
    public static boolean readFromFile(String inputFile, Map<String, Line> allLines) {
        if (!isFileFormatCorrect(inputFile)) {
            return false;
        }

        JsonObject jsonObject;
        try (Reader reader = Files.newBufferedReader(Path.of(inputFile))) {
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            System.out.println("Error! Such a file doesn't exist!");
            return false;
        }

        fillMap(jsonObject, allLines);
        return true;
    }

    private static boolean isFileFormatCorrect(String inputFile) {
        String fileName = new File(inputFile).getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            System.out.println("Error! Incorrect file!");
            return false;
        } else if (!fileName.substring(dotIndex + 1).equals("json")) {
            System.out.println("Error! Incorrect file!");
            return false;
        }
        return true;
    }

    private static void fillMap(JsonObject jsonObject, Map<String, Line> allLines) {
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

        // create stations first
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String lineName = entry.getKey();
            JsonArray lineJsonArray = entry.getValue().getAsJsonArray();

            for (int i = 0; i < lineJsonArray.size(); i++) {
                JsonObject stationJsonObject = lineJsonArray.get(i).getAsJsonObject();
                String stationName = stationJsonObject.get("name").getAsString();
                Integer time = null;
                if (stationJsonObject.has("time")) {
                    time = stationJsonObject.get("time").getAsInt();
                }
                Station station = Station.getInstance(stationName, lineName, time);
            }
        }

        // now add transfers, prev, next stations
        // also create Line objects and add start, end stations
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String lineName = entry.getKey();
            Line line = new Line(lineName);
            allLines.put(lineName, line);
            JsonArray lineJsonArray = entry.getValue().getAsJsonArray();

            for (int i = 0; i < lineJsonArray.size(); i++) {
                JsonObject stationJsonObject = lineJsonArray.get(i).getAsJsonObject();
                String stationName = stationJsonObject.get("name").getAsString();
                Station station = Station.checkInstance(stationName, lineName);

                JsonArray transferJsonArray = stationJsonObject.get("transfer").getAsJsonArray();
                if (!transferJsonArray.isEmpty()) {
                    for (int j = 0; j < transferJsonArray.size(); j++) {
                        JsonObject transfer = transferJsonArray.get(j).getAsJsonObject();
                        Station transferStation = Station.checkInstance(transfer.get("station").getAsString(), transfer.get("line").getAsString());
                        station.addTransfer(new Transfer(transferStation));
                    }
                }

                JsonArray prevJsonArray = stationJsonObject.get("prev").getAsJsonArray();
                if (!prevJsonArray.isEmpty()) {
                    for (int k = 0; k < prevJsonArray.size(); k++) {
                        String prevStationName = prevJsonArray.get(k).getAsString();
                        Station prevStation = Station.checkInstance(prevStationName, lineName);
                        station.addPrevious(prevStation);
                    }
                } else {
                    line.addStartStation(station);
                }

                JsonArray nextJsonArray = stationJsonObject.get("next").getAsJsonArray();
                if (!nextJsonArray.isEmpty()) {
                    for (int k = 0; k < nextJsonArray.size(); k++) {
                        String nextStationName = nextJsonArray.get(k).getAsString();
                        Station nextStation = Station.checkInstance(nextStationName, lineName);
                        station.addNext(nextStation);
                    }
                } else {
                    line.addEndStation(station);
                }
            }
        }
    }
}
