package metro.util;

import com.google.gson.Gson;
import metro.Metro;

import java.io.FileReader;

public class GsonParser {
    public Metro parser(FileReader fileReader) {
        Gson gson = new Gson();
        Metro metro = gson.fromJson(fileReader, Metro.class);
        return metro;
    }
}
