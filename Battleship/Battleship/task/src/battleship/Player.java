package battleship;

public class Player {

    public Player(String name, char[][] field, char[][] fogOnField) {
        this.name = name;
        this.field = field;
        this.fogOnField = fogOnField;
    }

    private final String name;

    private final char[][] field;
    private char[][] fogOnField;


    public char[][] getField() {
        return field;
    }

    public char[][] getFogOnField() {
        return fogOnField;
    }

    public void setFogOnField(char[][] fogOnField) {
        this.fogOnField = fogOnField;
    }

    public String getName() {
        return name;
    }
}
