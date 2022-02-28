package analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveMethod {
    public static boolean naiveSearch(String searchingSignature, String text) {
        Pattern pattern = Pattern.compile(searchingSignature);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
        //return text.contains(searchingSignature);
    }
}
