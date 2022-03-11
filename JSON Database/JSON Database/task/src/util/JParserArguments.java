package util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

//-t set -i 148 -m Here is some text to store on the server
//-t is the type of the request, and -i is the index of the cell.
//-m is the value to save in the database: you only need it in case of a set request.

public class JParserArguments {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-t", description = "is the type of the request")
    private static String typeRequest;

    @Parameter(names = "-k", description = "is the key")
    private static String key;

    @Parameter(names = "-v", description = "is the value to save in the database")
    private static String valueForDataBase;

    public static String getTypeRequest() {
        return typeRequest;
    }

    public static String getKey() {
        return key;
    }

    public static String getValueForDataBase() {
        return valueForDataBase;
    }

    public static void parse(String[] args) {
        JParserArguments parser = new JParserArguments();
        JCommander.newBuilder()
                .addObject(parser)
                .build()
                .parse(args);
    }
}

