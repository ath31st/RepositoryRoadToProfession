package calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static final Scanner SCANNER = new Scanner(System.in);
    static final Map<String, BigDecimal> vars = new LinkedHashMap<>(); // variables in memory

    static final String EQUATION_REGEX = "([A-Za-z]+\\s*=)?(\\s*[-+(]*\\s*)*(((\\d+(\\.\\d+)?)|[A-Za-z]+)(((\\s*[-+()]+\\s*)+)|(\\s*[()]*\\s*[*/^]+\\s*[()]*\\s*)))*(\\d+(\\.\\d+)?|[A-Za-z]+)\\)*";
    static final String DECIMAL_REGEX = "\\d+(\\.\\d+)?";
    static final String DECIMAL_WITH_MINUS_REGEX = "-?" + DECIMAL_REGEX;
    static final String VARIABLE_REGEX = "[A-Za-z]+";
    static final String OPERATOR_REGEX = "[-+*/^()]";

    static final String COMMAND_SIGN = "/";
    static final String EXIT = "exit";
    static final String HELP = "help";

    public static void main(String[] args) {
        while (true) {
            String order = clean(SCANNER.nextLine());
            if(order.equals("paas")) {
                System.out.println("Invalid expression");
            }

            else if  (order.matches(EQUATION_REGEX)) {
                try {
                    if (order.contains("=")) {
                        String[] eq = order.split("=");
                        if (vars.containsKey(eq[0] )) {
                            vars.replace(eq[0] , eval(eq[1]));
                        } else {
                            vars.put(eq[0] , eval(eq[1]));
                        }
                    } else {
                        System.out.println(eval(order));
                    }
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage());
                }
            } else if (order.equals(COMMAND_SIGN + EXIT)) {
                System.out.println("Bye!");
                break;
            } else if (order.equals(COMMAND_SIGN + HELP)) {
                System.out.println("The program calculates equations");
            } else if (order.isBlank()) {

            } else {
                if (order.startsWith(COMMAND_SIGN)) {
                    System.out.println("Unknown command");
                } else if (order.contains("=")) {
                    System.out.println("Invalid identifier");
                } else {
                    System.out.println("Invalid expression");
                }
            }
        }
    }

    // decode existing variables
    private static Map<Integer, String> decode(Map<Integer, String> variables) {
        for (var entry : variables.entrySet()) {
            Integer k = entry.getKey();
            String v = entry.getValue();
            if (vars.containsKey(v)) {
                variables.replace(k, String.valueOf(vars.get(v)));
            } else {
                throw new IllegalStateException("Unknown variable");
            }
        }
        return variables;
    }

    // prepare data structure from command and get result
    private static BigDecimal eval(String command) throws IllegalStateException {
        Map<Integer, String> map = new TreeMap<>();
        map.putAll(find(DECIMAL_REGEX, command));
        map.putAll(decode(find(VARIABLE_REGEX, command)));
        map.putAll(find(OPERATOR_REGEX, command));
        List<String> list = Util.postfixNotation(map.values());
        return Util.calculatePostfix(list);
    }

    // return map with positions and found regex result
    private static Map<Integer, String> find(String regex, String command) {
        Map<Integer, String> results = new TreeMap<>();
        Matcher matcher = Pattern.compile(regex).matcher(command);
        while (matcher.find()) {
            results.put(matcher.start(), matcher.group());
        }
        return results;
    }

    // remove whitespaces and doubled signs
    private static String clean(String command) {
        if(command.matches(".*\\*{3,}.*") || command.matches(".*/{2,}.*")) {
            //throw new IllegalStateException("Invalid expression");
            //System.out.println("Invalid expression");
            return "paas";
        }

        return command.replaceAll("\\s+", "")
                .replaceAll("(--)+", "+")
                .replaceAll("\\++", "+")
                .replaceAll("(\\+-)+", "-")
                .replaceAll("(-\\+)+", "-")
                .replaceAll("\\*+", "*")
                .replaceAll("/+", "/");
    }
}

class Util {
    
    static List<String> postfixNotation(Collection<String> values) throws IllegalStateException {
        ArrayDeque<String> stack = new ArrayDeque<>();
        ArrayDeque<String> braces = new ArrayDeque<>();
        ArrayList<String> result = new ArrayList<>();
        for (String val : values) {
            if (val.matches(Main.DECIMAL_WITH_MINUS_REGEX)) { // add all numbers
                result.add(val);
            }  else if (val.matches("[-+*/^]")) { // operators
                if (stack.isEmpty() || stack.getLast().equals("(")) {
                    stack.offerLast(val);
                } else if (getPriority(val) > getPriority(stack.getLast())) {
                    stack.offerLast(val);
                } else if (getPriority(val) <= getPriority(stack.getLast())){
                    while (!stack.isEmpty()) {
                        String last = stack.pollLast();
                        if (getPriority(last) < getPriority(val) || last.equals("(")) {
                            break;
                        }
                        if (last.matches("[-+*/^]")) {
                            result.add(last);
                        }
                    }
                    stack.offerLast(val);
                }
            } else if (val.equals("(")) { // bracket "("
                stack.offerLast(val);
                braces.offerLast(val);
            } else if (val.equals(")")) { // bracket ")"
                if (braces.pollLast() == null) { // illegal brackets quantity
                    throw new IllegalStateException("Invalid expression");
                }
                while (!stack.isEmpty()) {
                    String last = stack.pollLast();
                    if (last.equals("(")) {
                        break;
                    }
                    if (last.matches("[-+*/^]")) {
                        result.add(last);
                    }
                }
            } else { // ?
                throw new IllegalStateException("Invalid identifier");
            }
        }
        stack.descendingIterator().forEachRemaining(s -> {
            if (s.matches("[-+*/^]")) {
                result.add(s);
            }
        });
        if (braces.size() != 0) { // illegal brackets quantity
            throw new IllegalStateException("Invalid expression");
        }
        return result;
    }

    /**
     * calculate result of postfix equation
     * @param values expression in postfix
     * @return result as long
     */
    static BigDecimal calculatePostfix(List<String> values) {
        ArrayDeque<BigDecimal> stack = new ArrayDeque<>();
        for (String val : values) {
            if (val.matches(Main.DECIMAL_WITH_MINUS_REGEX)) {
                stack.offerLast(new BigDecimal(val));
            } else {
                BigDecimal a = stack.pollLast();
                BigDecimal b = stack.pollLast();
                if (a == null) {
                    a = BigDecimal.ZERO;
                }
                if (b == null) {
                    b = BigDecimal.ZERO;
                }
                switch (val) {
                    case "+":
                        stack.offerLast(b.add(a));
                        break;
                    case "-":
                        stack.offerLast(b.subtract(a));
                        break;
                    case "*":
                        stack.offerLast(b.multiply(a));
                        break;
                    case "/":
                        stack.offerLast(b.divide(a, RoundingMode.CEILING));
                        break;
                    case "^":
                        stack.offerLast(b.pow(a.intValue()));
                        break;
                }
            }
        }
        return stack.removeLast();
    }

    /**
     * sets a priority for operator
     * @param operator operation sign
     * @return priority as integer
     */
    static int getPriority(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        if (operator.equals("^")) {
            return 3;
        }
        return -1;
    }
}