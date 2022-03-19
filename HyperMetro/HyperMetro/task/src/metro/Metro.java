package metro;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Metro {

    @SerializedName("Metro-Railway")
    private TreeMap<String, String> stations1;
    @SerializedName("Hammersmith-and-City")
    private TreeMap<String, String> stations2;

    public Metro() {
    }


    public Map<String, String> getStations1() {
        return stations1;
    }

    public Map<String, String> getStations2() {
        return stations2;
    }

    public LinkedList<String> getListStation(Map<String,String> stations) {
       return new LinkedList<>(stations.values());
    }
}
