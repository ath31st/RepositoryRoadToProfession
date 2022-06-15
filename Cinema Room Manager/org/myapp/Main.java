package org.myapp;

import java.util.Scanner;

class Main {
    static int currentIncome = 0;
    static int soldSeats = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        char[][] room = setCinemaRoom(rows, seats);

        boolean controlFlag = true;
        while (controlFlag) {
            showMenu();
            int menuInt = scanner.nextInt();
            switch (menuInt) {
                case 1:
                    printCinemaRoom(room);
                    break;

                case 2:

                    soldTickets(room, rows, seats);
                    break;

                case 3:
                    statistic(rows, seats);
                    break;

                case 0:
                default:
                    controlFlag = false;
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
    }

    public static char[][] setCinemaRoom(int rows, int seats) {
        char[][] room = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                room[i][j] = 'S';
            }
        }
        return room;
    }

    public static void printCinemaRoom(char[][] room) {
        System.out.println("Cinema:");
        for (int i = 0; i < room[0].length; i++) {
            if (i == 0) System.out.print("  ");
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < room.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < room[i].length; j++) {
                System.out.print(room[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void soldTickets(char[][] room, int rows, int seats) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int row = scan.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scan.nextInt();
        try {
            if (room[row - 1][seat - 1] == 'S') {
                room[row - 1][seat - 1] = 'B';
                int price = 0;
                if (rows * seats <= 60) price = 10;
                else if (rows * seats > 60) {
                    price = (rows + 1) / 2 > row ? 10 : 8;
                }
                currentIncome += price;
                soldSeats++;
                System.out.println("\n" + "Ticket price: $" + price);
            } else {
                System.out.println("\n" + "That ticket has already been purchased!");
                soldTickets(room, rows, seats);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input!");
            soldTickets(room, rows, seats);
        }
    }


    public static void statistic(int rows, int seats) {
        int totalIncome = 0;
        double percentage = 0;
        if (rows * seats <= 60) {
            totalIncome = (rows * seats) * 10;
        } else if (rows * seats > 60) {
            if (rows % 2 != 0) {
                totalIncome = ((rows / 2 + 1) * seats) * 8 + ((rows / 2) * seats) * 10;
            } else if (rows % 2 == 0) {
                totalIncome = (rows / 2 * seats) * 8 + (rows / 2 * seats) * 10;
            }
        }
        percentage = (soldSeats / (double) (rows * seats)) * 100;
        System.out.printf("Number of purchased tickets: %d" + "\n", soldSeats);
        System.out.printf("Percentage: %.2f", percentage);
        System.out.println("%");
        System.out.printf("Current income: $%d" + "\n", currentIncome);
        System.out.printf("Total income: $%d" + "\n", totalIncome);
    }
}

