package calculator;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean status = true;
        String exp;
        while (status) {
            Scanner scanner = new Scanner(System.in);
            exp = scanner.nextLine();
            if (exp.equals("/help")) System.out.println("The program calculates the sum of numbers");
            if (!exp.equals("/exit")) {
                if (exp.isEmpty()) continue;
                try {
                    exp = exp.replaceAll("[^0-9+-]+", "")
                            .replaceAll("[+]{2,}", "+")
                            .replaceAll("[-]{3}", "-")
                            .replaceAll("[-]{2}", "+");

                    System.out.println(eval(exp));
                } catch (Exception e) {
                    System.out.print("");
                }
            } else status = false;
        }
        System.out.println("Bye!");
    }

    static boolean isDelim(char c) {
        return c == ' ';
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    static int priority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    static void processOperator(LinkedList<Integer> st, char op) {
        int r = st.removeLast();
        int l = st.removeLast();
        switch (op) {
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                st.add(l / r);
                break;
            case '%':
                st.add(l % r);
                break;
        }
    }

    public static int eval(String s) {
        LinkedList<Integer> st = new LinkedList<>();
        LinkedList<Character> op = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st, op.removeLast());
                op.removeLast();
            } else if (isOperator(c)) {
                if (op.isEmpty() && st.isEmpty() && c == '-') {
                    st.add(0);
                }
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    processOperator(st, op.removeLast());
                op.add(c);
            } else {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i)))
                    operand += s.charAt(i++);
                --i;
                st.add(Integer.parseInt(operand));
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);
    }
}
