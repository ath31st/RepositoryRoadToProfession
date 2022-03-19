package metro.util;

import java.util.LinkedList;

public class PrintResult {

    public static void print(LinkedList<String> stations) {
        stations.addFirst("depot");
        stations.addLast("depot");
        while (stations.size() > 2) {
            System.out.print(stations.removeFirst());
            stations.stream().limit(2).forEach(e -> System.out.print(" - " + e));
            System.out.println();
        }
    }
}
