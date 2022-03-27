package metro.reserv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    static final String singleWord = "[\\w\\.&-]+";
    static final String quotesSingleWord = String.format("\"%s\"", singleWord);
    static final String quotesManyWords = String.format("\"[%s\\s]+%s\"", singleWord, singleWord);
    static final String name = String.format("(%s|%s|%s)", singleWord, quotesSingleWord, quotesManyWords);
    static Pattern namePattern = Pattern.compile(name);
    static Pattern quotePattern = Pattern.compile("\"");
    public String action;
    public String arg1;
    public String arg2;
    public String arg3;
    public String arg4;

    public InputParser(String first, String second) {
        parseInput(first, second);
    }

    public void parseInput(String first, String second) {
        action = first;
        Matcher matcher = namePattern.matcher(second);
        if (matcher.find()) {
            arg1 = matcher.group();
            arg1 = quotePattern.matcher(arg1).replaceAll("");
        }
        if (matcher.find()) {
            arg2 = matcher.group();
            arg2 = quotePattern.matcher(arg2).replaceAll("");
        }
        if (matcher.find()) {
            arg3 = matcher.group();
            arg3 = quotePattern.matcher(arg3).replaceAll("");
        }
        if (matcher.find()) {
            arg4 = matcher.group();
            arg4 = quotePattern.matcher(arg4).replaceAll("");
        }
    }
}