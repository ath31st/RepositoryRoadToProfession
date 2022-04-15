package metro.commands;

import metro.database.Subway;

public class ConnectCommand implements Command {
    private final String line1;
    private final String station1;
    private final String line2;
    private final String station2;
    private final Subway subway;

    public ConnectCommand(String line1, String station1, String line2, String station2, Subway subway) {
        this.line1 = line1;
        this.station1 = station1;
        this.line2 = line2;
        this.station2 = station2;
        this.subway = subway;
    }

    @Override
    public void execute() {
        subway.connect(line1, station1, line2, station2);
    }
}
