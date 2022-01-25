package bullscows;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int[] secretCode;
    static int bulls = 0;
    static int cows = 0;
    static int turn = 1;

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        if (size <= 10) {
            secretCode = generateSecretCode(size);
            while (bulls != secretCode.length) {
                System.out.printf("Turn %d:" + "\n", turn);
                int[] grader = getGrader();

                bulls = checkBulls(grader, secretCode);
                cows = checkCows(grader, secretCode);
                printResult(bulls, cows);
                turn++;
                if (bulls == secretCode.length) {
                    System.out.println("Congratulations! You guessed the secret code.");
                }
            }
        } else {
            System.out.println("Error");
        }
    }


    static int[] getGrader() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split("");
        int[] grader = new int[secretCode.length];
        for (int i = 0; i < grader.length; i++) {
            grader[i] = Integer.parseInt(s[i]);
        }
        //scanner.close();
        return grader;
    }

    static int[] generateSecretCode(int size) {
        //scanner.close();
        IntStream stream = IntStream.generate(()
                -> {
            return (int) (Math.random() * 10);
        }).limit(100).distinct().limit(size);
        System.out.println("Okay, let's start a game!");
        return stream.toArray();
    }

    static int checkBulls(int[] grader, int[] secretCode) {
        int bulls = 0;
        for (int i = 0; i < grader.length; i++) {
            if (grader[i] == secretCode[i]) {
                bulls++;
            }
        }
        return bulls;
    }

    static int checkCows(int[] grader, int[] secretCode) {
        int cows = 0;
        for (int i = 0; i < grader.length; i++) {
            for (int j = 0; j < secretCode.length; j++) {
                if (i != j) {
                    if (secretCode[j] == grader[i]) {
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

