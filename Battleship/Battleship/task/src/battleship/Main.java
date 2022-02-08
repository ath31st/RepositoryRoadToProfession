package battleship;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static int rows = 10;
    private static int colons = 10;

    public enum Ships {
        AIRCRAFT_CARRIER("Aircraft Carrier", 5),
        BATTLESHIP("Battleship", 4),
        SUBMARINE("Submarine", 3),
        CRUISER("Cruiser", 3),
        DESTROYER("Destroyer", 2);

        private String nameShip;
        private int cells;

        Ships(String nameShip, int cells) {
            this.nameShip = nameShip;
            this.cells = cells;
        }

        public String getNameShip() {
            return nameShip;
        }

        public int getCells() {
            return cells;
        }
    }

    public static void main(String[] args) {
        char[][] field = setField(rows, colons);
        printField(field);
        //controlPanel();
        shipsOnField(field);
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
        } else arrCoordinates[1] = 10;

        String[] temp2 = coordinates[1].split("");
        arrCoordinates[2] = temp2[0].charAt(0) - 64;
        if (temp2.length < 3) {
            arrCoordinates[3] = Integer.parseInt(temp2[1]);
        } else arrCoordinates[3] = 10;

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

    public static void shipsOnField(char[][] field) {
        LinkedList<Ships> list = new LinkedList<>(List.of(Ships.values()));
        while (!list.isEmpty()) {
            Ships ship = list.peek();
            System.out.printf("\nEnter the coordinates of the %s (%d cells):" + "\n", ship.getNameShip(), ship.getCells());

            int[] coordinates = setCoordinates();
            boolean checkField = false;

            try {
                checkField = checkField(field, coordinates, ship.getCells());
            } catch (Exception e) {
                System.out.println("");
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

    public static void controlPanel() {
        boolean controlFlag = true;
        while (controlFlag) {

        }
    }
}
