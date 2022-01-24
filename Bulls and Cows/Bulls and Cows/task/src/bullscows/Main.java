package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int secretCode = 9305;
        int[] grader = getGrader();

        int bulls = checkBulls(grader,getSecretCode());
        int cows = checkCows(grader,getSecretCode());
        printResult(bulls,cows,secretCode);

    }

    static int[] getGrader() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split("");
        int[] grader = new int[4];
        for (int i = 0; i < grader.length; i++) {
            grader[i] = Integer.parseInt(s[i]);
        }
        scanner.close();
        return grader;
    }

    static int[] getSecretCode() {
        return new int[]{9, 3, 0, 5};
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

    static void printResult(int bulls, int cows, int secretCode) {
        if (bulls == 0 && cows == 0) {
            System.out.printf("Grade: None. The secret code is %d.", secretCode);
        } else if (bulls == 0 && cows > 0) {
            System.out.printf("Grade: %d cow(s). The secret code is %d.", cows, secretCode);
        } else if (bulls > 0 && cows == 0) {
            System.out.printf("Grade: %d bull(s). The secret code is %d.", bulls, secretCode);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is %d.", bulls, cows, secretCode);
        }

    }
}
