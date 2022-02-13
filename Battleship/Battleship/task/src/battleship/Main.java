package battleship;


import java.io.IOException;
import java.util.*;


public class Main {
    private static final int rows = 10;
    private static final int colons = 10;

    public static void main(String[] args) {
        Player player1 = new Player("Player 1", setField(rows, colons), setField(rows, colons));
        Player player2 = new Player("Player 2", setField(rows, colons), setField(rows, colons));

        System.out.printf("\n%s, place your ships on the game field\n\n", player1.getName());
        printField(player1.getField());
        shipsOnField(player1.getField(), player1);

        promptEnterKey();

        System.out.printf("\n%s, place your ships on the game field\n\n", player2.getName());
        printField(player2.getField());
        shipsOnField(player2.getField(), player2);

        promptEnterKey();

        while (checkLiveShips(player1.getField()) | checkLiveShips(player2.getField())) {
            takeAShot(player1.getField(), player2.getFogOnField(), player1, player2.getField());
            promptEnterKey();
            takeAShot(player2.getField(), player1.getFogOnField(), player2, player1.getField());
            promptEnterKey();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");


    }


    public static char[][] setField(int rows, int colons) {
        char[][] field = new char[rows][colons];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colons; j++) {
                field[i][j] = '~';
            }
        }
        return field;
    }

    public static void printField(char[][] field) {
        for (int i = 0; i < field[0].length; i++) {
            if (i == 0) System.out.print("  ");
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[] setCoordinates() {
        int[] arrCoordinates = new int[4];
        Scanner scanner = new Scanner(System.in);
        String[] coordinates = scanner.nextLine().split(" ");
        String[] temp1 = coordinates[0].split("");
        arrCoordinates[0] = temp1[0].charAt(0) - 64;
        if (temp1.length < 3) {
            arrCoordinates[1] = Integer.parseInt(temp1[1]);
        } else if (temp1.length > 2 && Integer.parseInt(temp1[1]) == 1
                && Integer.parseInt(temp1[2]) == 0) arrCoordinates[1] = 10;

        if (coordinates.length > 1) {
            String[] temp2 = coordinates[1].split("");
            arrCoordinates[2] = temp2[0].charAt(0) - 64;
            if (temp2.length < 3) {
                arrCoordinates[3] = Integer.parseInt(temp2[1]);
            } else if (temp2.length > 2 && Integer.parseInt(temp2[1]) == 1
                    && Integer.parseInt(temp2[2]) == 0) arrCoordinates[3] = 10;
        }
        return arrCoordinates;
    }

    public static boolean checkField(char[][] field, int[] coord, int cells) {
        boolean check = false;

        //HORIZONTAL
        if (coord[0] == coord[2]) {
            for (int j = -1; j < cells + 1; j++) {
                if (field[coord[0] - 1][Math.min(coord[1], coord[3]) + j - 1] == 'O') {
                    check = true;
                    break;
                    //check -1 line
                } else if (field[coord[0] - 2][Math.min(coord[1], coord[3]) + j - 1] == 'O') {
                    check = true;
                    break;
                    //check +1 line
                } else if (field[coord[0]][Math.min(coord[1], coord[3]) + j - 1] == 'O') {
                    check = true;
                    break;
                }
            }
            //VERTICAL
        } else if (coord[1] == coord[3]) {
            for (int j = -1; j < cells + 1; j++) {
                if (field[Math.min(coord[0], coord[2]) + j - 1][coord[1] - 1] == 'O') {
                    check = true;
                    break;
                    //check -1 colon
                } else if (field[Math.min(coord[0], coord[2]) + j - 1][coord[1] - 2] == 'O') {
                    check = true;
                    break;
                    //check +1 colon
                } else if (field[Math.min(coord[0], coord[2]) + j - 1][coord[1]] == 'O') {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public static void shipsOnField(char[][] field, Player player) {
        LinkedList<Ships> list = new LinkedList<>(List.of(Ships.values()));
        while (!list.isEmpty()) {
            Ships ship = list.peek();
            System.out.printf("\nEnter the coordinates of the %s (%d cells):" + "\n", ship.getNameShip(), ship.getCells());

            int[] coordinates = setCoordinates();
            boolean checkField = false;

            try {
                checkField = checkField(field, coordinates, ship.getCells());
            } catch (Exception e) {
                System.out.println();
            }
            if (!checkField) {
                //HORIZONTAL
                if (coordinates[0] == coordinates[2] && Math.abs(coordinates[1] - coordinates[3]) + 1 == ship.getCells()) {
                    for (int j = 0; j < ship.getCells(); j++) {
                        field[coordinates[0] - 1][Math.min(coordinates[1], coordinates[3]) + j - 1] = 'O';
                    }
                    //VERTICAL
                } else if (coordinates[1] == coordinates[3] && Math.abs(coordinates[0] - coordinates[2]) + 1 == ship.getCells()) {
                    for (int j = 0; j < ship.getCells(); j++) {
                        field[Math.min(coordinates[0], coordinates[2]) + j - 1][coordinates[1] - 1] = 'O';
                    }
                } else {
                    checkField = true;
                }
            }
            if (checkField) {
                System.out.println("Error! Try again:");
            } else {
                printField(field);
                list.poll();
            }
        }
    }

    public static void takeAShot(char[][] field, char[][] fogOnField, Player player, char[][] foeField) {
        printField(fogOnField);
        System.out.println("---------------------");
        printField(field);
        System.out.printf("\n%s, it's your turn:\n", player.getName());
        int[] coordinates = setCoordinates();
        try {
            if (foeField[coordinates[0] - 1][coordinates[1] - 1] == 'O') {
                foeField[coordinates[0] - 1][coordinates[1] - 1] = 'X';
                fogOnField[coordinates[0] - 1][coordinates[1] - 1] = 'X';
                if (!checkLiveShips(foeField)) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else if (isSank(foeField, coordinates)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("\nYou hit a ship!\n");
                }
            } else if (foeField[coordinates[0] - 1][coordinates[1] - 1] == 'X') {
                System.out.println("\nYou sank a ship! Specify a new target:\n");
            } else {
                foeField[coordinates[0] - 1][coordinates[1] - 1] = 'M';
                fogOnField[coordinates[0] - 1][coordinates[1] - 1] = 'M';
                System.out.println("\nYou missed!\n");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error! You entered the wrong coordinates!");
        }
        //System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public static boolean checkLiveShips(char[][] field) {
        boolean check = false;
        for (char[] arr : field) {
            for (char c : arr) {
                if (c == 'O') {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSank(char[][] foeField, int[] coord) {
        boolean sank = false;
        //if (foeField[coord[0] - 1][coord[1] - 2] == 'O' | foeField[coord[0] - 1][coord[1]] == 'O'
        try {
            if (foeField[coord[0] - 1][coord[1] - 1] == 'O' | foeField[coord[0] - 1][coord[1]] == 'O'
                    | foeField[coord[0] - 2][coord[1] - 1] == 'O' | foeField[coord[0] - 1][coord[1]] == 'O') {
                sank = false;
            } else {
                sank = true;
            }
        } catch (Exception e) {
            System.out.print("");
        }
        return sank;
    }
}
