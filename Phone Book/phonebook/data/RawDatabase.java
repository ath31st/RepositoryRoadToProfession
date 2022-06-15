package phonebook.data;

import java.io.File;
import java.nio.file.Path;

public class RawDatabase {
    private static final File FILE = new File("C:\\Users\\Fractal\\Desktop\\directory1.txt");
    private static final Path PATH_RAW_DB = FILE.toPath();

    public static File init() {
        return FILE;
    }
}
