package bullscows;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<String> secretCode;
    static List<String> grader;
    static int bulls = 0;
    static int cows = 0;
    static int turn = 1;
    static int range = 0;
    static int length = 0;

    public static void main(String[] args) {
        try {
            System.out.println("Input the length of the secret code:");
            Scanner scanner = new Scanner(System.in);
            length = scanner.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            range = scanner.nextInt();

            if (length <= range && length <= 36 && range <= 36 && length > 0) {
                secretCode = generateSecretCode(length, range);
                while (bulls != secretCode.size()) {
                    System.out.printf("Turn %d:" + "\n", turn);
                    grader = getGrader();

                    bulls = checkBulls(grader, secretCode);
                    cows = checkCows(grader, secretCode);
                    printResult(bulls, cows);
                    turn++;
                    if (bulls == secretCode.size()) {
                        System.out.println("Congratulations! You guessed the secret code.");
                    }
                }
            } else {
                System.out.printf("Error: it's not possible to generate" +
                        " a code with a length of %d with %d unique symbols.", length, range);
            }
        } catch (InputMismatchException inputMismatchException) {
            System.out.printf("Error: isn't %d + %d a valid number.", length, range);
        }
    }


    static List<String> getGrader() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split("");
        return Arrays.stream(s).collect(Collectors.toList());
    }

    static List<String> generateSecretCode(int length, int range) {
        StringBuilder sb = new StringBuilder();
        sb.append("*".repeat(Math.max(0, length)));
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            int n = i < 10 ? i + 48 : i + 87;
            numbers.add(String.valueOf((char) n));
        }
        if (range <= 10) {
            System.out.printf("The secret is prepared: %s (0-%d)." + "\n", sb, range - 1);
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%c)." + "\n", sb, range + 86);
        }
        Collections.shuffle(numbers);
        return numbers.stream().limit(length).collect(Collectors.toList());
    }

    static int checkBulls(List<String> grader, List<String> secretCode) {
        int bulls = 0;
        for (int i = 0; i < grader.size(); i++) {
            if (Objects.equals(grader.get(i), secretCode.get(i))) {
                bulls++;
            }
        }
        return bulls;
    }

    static int checkCows(List<String> grader, List<String> secretCode) {
        int cows = 0;
        for (int i = 0; i < grader.size(); i++) {
            for (int j = 0; j < secretCode.size(); j++) {
                if (i != j) {
                    if (Objects.equals(secretCode.get(j), grader.get(i))) {
                        cows++;
                    }
                }
            }
        }
        return cows;
    }

    static void printResult(int bulls, int cows) {
        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None.");
        } else if (bulls == 0 && cows > 0) {
            System.out.printf("Grade: %d cow(s)." + "\n", cows);
        } else if (bulls > 0 && cows == 0) {
            System.out.printf("Grade: %d bull(s)." + "\n", bulls);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s)." + "\n", bulls, cows);
        }

    }
}

