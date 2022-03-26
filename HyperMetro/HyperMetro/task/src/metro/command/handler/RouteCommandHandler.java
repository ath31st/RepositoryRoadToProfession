package hypermetro.command.handler;

import hypermetro.Lane;
import hypermetro.Station;
import hypermetro.command.CommandHandler;
import hypermetro.command.CommandSyntax;
import hypermetro.command.InvalidSyntaxException;

import java.time.temporal.JulianFields;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouteCommandHandler extends CommandHandler {

    private static CommandHandler instance = new RouteCommandHandler();

    public static CommandHandler getInstance() { return instance; }

    Pattern commandPattern = Pattern.compile(CommandSyntax.routeCommand);

    @Override
    public void executeCommand(Map<String, Lane> laneMap, String commandText) {
        Matcher matcher = commandPattern.matcher(commandText);
        if (!matcher.find()) throw new InvalidSyntaxException("Invalid command");

        String fromLaneString = sanitizeString(matcher.group("fromLane"));
        String fromStationString = sanitizeString(matcher.group("fromStation"));
        String toLaneString = sanitizeString(matcher.group("toLane"));
        String toStationString = sanitizeString(matcher.group("toStation"));

        Lane fromLane = laneMap.get(fromLaneString);
        Lane toLane = laneMap.get(toLaneString);

        Station fromStation = fromLane.findStation(fromStationString);
        Station toStation = toLane.findStation(toStationString);

        List<String> path = findShortestRoute(fromLaneString, fromStation, toLaneString, toStation);
        path.forEach(System.out::println);
    }

    private List<String> findShortestRoute(String fromLane, Station fromStation, String toLane, Station toStation ) {

        Queue<Journey> queue = new LinkedList<>();
        Map<Station, Journey> map = new HashMap<>();

        Journey journey = new Journey(null, fromStation, fromLane);
        queue.add(journey);
        map.put(fromStation, journey);

        while (!queue.isEmpty()) {
            Journey currJourney = queue.poll();
            String currLane = currJourney.getCurrLane();
            Station currStation = currJourney.getCurrStation();

            if (currLane.equals(toLane) && currStation.equals(toStation)) {
               return backTrackPath(map, currJourney);
            }

            addNeighbours(currStation, currLane, queue, map);
        }
        return null;
    }

    private void addNeighbours(Station currStation, String lane, Queue<Journey> queue, Map<Station, Journey> map) {
        Station prevStation = currStation.getPrevStation();
        Station nextStation = currStation.getNextStation();
        Map<String, List<Station>> transfers = currStation.getTransfer();

        if (transfers != null) {
            for (var transfer : transfers.entrySet()) {
                transfer.getValue().forEach(station -> addToMapIfAbsent(map, currStation, station, transfer.getKey(), queue));
            }
        }

        addToMapIfAbsent(map, currStation, prevStation, lane, queue);
        addToMapIfAbsent(map, currStation, nextStation, lane, queue);
    }

    private List<String> backTrackPath(Map<Station, Journey> map, Journey journey) {

        LinkedList<String> path = new LinkedList<>();

        while (true) {

            Station currStation = journey.getCurrStation();
            String currLane = journey.getCurrLane();

            Station prevStation = journey.getPreviousStation();
            path.addFirst(currStation.getName());

            if (prevStation == null) {
                break;
            }

            Journey prevJourney = map.getOrDefault(prevStation, null);
            String prevLane = prevJourney.getCurrLane();

            if (!prevLane.equals(currLane)) path.addFirst(String.format("Transition to line %s", currLane));

            journey = prevJourney;
        }
        return path;
    }

    private void addToMapIfAbsent(Map<Station, Journey> map, Station prevStation, Station currStation, String currLane, Queue<Journey> queue) {
        if (map.containsKey(currStation)) return;
        Journey journey = new Journey(prevStation, currStation, currLane);
        map.put(currStation, journey);
        queue.add(journey);
    }

}

class Journey {

    private Station previousStation;
    private Station currStation;
    private String currLane;

    public Journey(Station previousStation, Station currStation, String currLane) {
        this.previousStation = previousStation;
        this.currStation = currStation;
        this.currLane = currLane;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public Station getCurrStation() {
        return currStation;
    }

    public String getCurrLane() {
        return currLane;
    }
}