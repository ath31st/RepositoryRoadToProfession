package maze;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import maze.ui.Menu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    private static final ObjectMapper MAPPER = new XmlMapper();

    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu("=== Menu ===")
            .set(Menu.Property.ERROR, "Incorrect option. Please try again");
    private Maze maze;

    public void startMenu() {
        addStartMenu();
        menu.addExit();
        menu.run();
    }

    private void addStartMenu() {
        menu.add("Generate a new maze", this::generateMaze);
        menu.add("Load a maze", this::loadMaze);
    }

    private void enableFullMenu() {
        menu.clear();
        addStartMenu();
        menu.add("Save the maze", this::save);
        menu.add("Display the maze", () -> System.out.println(maze));
        menu.add("Find the escape", () -> System.out.println(maze.getPath()));
        menu.addExit();
    }

    private void generateMaze() {
        System.out.println("Please, enter the size of a maze");
        final var size = Integer.parseInt(scanner.nextLine());
        maze = new Maze(size, size).generate();
        System.out.println(maze);
        enableFullMenu();
    }

    private void loadMaze() {
        try {
            maze = MAPPER.readValue(askFile(), Maze.class);
            LOG.info("The maze has loaded successful.");
            enableFullMenu();
        } catch (IOException error) {
            LOG.log(Level.WARNING, "The maze has not been loaded.", error);
        }
    }

    private void save() {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(askFile(), maze);
            LOG.info("The maze has saved successful.");
        } catch (IOException error) {
            LOG.log(Level.SEVERE, "Could not save the maze.", error);
        }
    }

    private File askFile() {
        System.out.println("Enter the file name:");
        final var fileName = scanner.nextLine();
        LOG.log(Level.INFO, "The file name is {0}", fileName);
        return new File(fileName);
    }
}