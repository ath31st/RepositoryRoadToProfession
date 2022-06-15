package maze.ui;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.text.MessageFormat.format;

public class Menu implements Runnable {

    protected final LinkedHashMap<String, MenuEntry> menu = new LinkedHashMap<>();
    private final Map<Property, String> properties = new EnumMap<>(Property.class);

    protected boolean isOnlyOnce;

    public Menu() {

    }

    public Menu(String title) {
        set(Property.TITLE, title);
    }

    public Menu set(Property key, String value) {
        properties.put(key, value);
        return this;
    }

    public Menu add(String key, String description, Runnable action) {
        menu.put(key, new MenuEntry(description, action));
        return this;
    }

    public Menu add(String description, Runnable action) {
        return this.add(String.valueOf(menu.size() + 1), description, action);
    }

    public Menu onlyOnce() {
        isOnlyOnce = true;
        return this;
    }

    public Menu addExit() {
        menu.put(get(Property.EXIT_KEY), new MenuEntry(get(Property.EXIT), this::onlyOnce));
        return this;
    }

    public void clear() {
        menu.clear();
    }

    @Override
    public void run() {
        do {
            System.out.println();
            System.out.println(get(Property.TITLE));
            menu.forEach((key, entry) -> System.out.println(format(get(Property.FORMAT), key, entry)));
            final var key = new Scanner(System.in).nextLine().toLowerCase();
            System.out.println();
            menu.getOrDefault(key, new MenuEntry("Error",
                    () -> System.out.println(format(get(Property.ERROR), menu.size())))
            ).run();
        } while (!isOnlyOnce);
    }

    protected String get(Property property) {
        return properties.getOrDefault(property, property.getValue());
    }

    public enum Property {
        TITLE("Choose your action:"),
        FORMAT("{0}. {1}"),
        ERROR("Please enter the number from 0 up to {0}"),
        EXIT("Exit"),
        EXIT_KEY("0");

        private final String value;

        Property(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    protected static final class MenuEntry implements Runnable {
        private final String description;
        private final Runnable action;

        MenuEntry(final String description, final Runnable action) {
            this.description = description;
            this.action = action;
        }

        @Override
        public String toString() {
            return description;
        }

        @Override
        public void run() {
            action.run();
        }
    }

}