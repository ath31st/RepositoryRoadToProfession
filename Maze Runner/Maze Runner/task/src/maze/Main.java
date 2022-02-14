
package maze;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);
        System.out.println("Please, enter the size of a maze");
        final var height = scanner.nextInt();
        final var width = scanner.nextInt();

        final var maze = new Maze(height, width);

        System.out.println(maze);
    }
}
