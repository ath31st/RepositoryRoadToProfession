package metro.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class ParserInputCommand {

    @Parameter
    private String command;

    @Parameter
    private String lineName;

    @Parameter
    private String stationName;



    public void parse(String inputLine) {
        ParserInputCommand parserInputCommand = new ParserInputCommand();
        JCommander.newBuilder()
                .addObject(parserInputCommand)
                .build()
                .parse(inputLine);
    }
}
