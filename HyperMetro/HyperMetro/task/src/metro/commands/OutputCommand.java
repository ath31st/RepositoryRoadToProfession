package metro.commands;

import metro.database.Subway;

public class OutputCommand implements Command {
    private final String lineName;
    private final Subway subway;

    public OutputCommand(String lineName, Subway subway) {
        this.lineName = lineName;
        this.subway = subway;
    }

    @Override
    public void execute() {
        subway.output(lineName);
    }
}
