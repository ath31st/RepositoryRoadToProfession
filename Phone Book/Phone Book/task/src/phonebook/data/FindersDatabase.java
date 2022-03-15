package phonebook.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FindersDatabase {
    private static final File FIND_FILE = new File("C:\\Users\\Fractal\\Desktop\\find1.txt");
    private static final Path PATH_FINDERS = FIND_FILE.toPath();

    public static File init() {
        return FIND_FILE;
    }
}
