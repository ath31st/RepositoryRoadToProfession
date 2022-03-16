package metro.util;

import java.io.File;

public class FileWithStationsNames {
    private static File file = new File(ParserCommandLine.getPath());

    public static File getFile() {
        return file;
    }
}
