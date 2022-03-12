package util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class JParserArguments {

    @Parameter(names = "-t", description = "is the type of the request")
    private static String typeRequest;

    @Parameter(names = "-in", description = "argument followed by the file name was provided")
    private static String fileRequest;

    @Parameter(names = "-k", description = "is the key")
    private static String key;

    @Parameter(names = "-v", description = "is the value to save in the database")
    private static String valueForDataBase;

    public static String getFileRequest() {
        return fileRequest;
    }

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

