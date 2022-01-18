package org.myapp;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        char[][] room = setCinemaRoom(rows, seats);

        boolean controlFlag = true;
        while (controlFlag){
            showMenu();
            int menuInt = scanner.nextInt();
            switch (menuInt) {
                case 1:
                    printCinemaRoom(room);
                    break;
                case 2:
                    System.out.println("Enter a row number:");
                    int row = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seat = scanner.nextInt();
                    costTicket(row, seat, rows, seats);
                    soldTickets(room, row, seat);
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

    public static void costTicket(int row, int seat, int rows, int seats) {
        int price = 0;
        if (rows * seats <= 60) price = 10;
        else if (rows * seats > 60) {
            price = (rows + 1) / 2 > row ? 10 : 8;
        }
        System.out.println("Ticket price: $" + price + "\n");
    }

    public static void soldTickets(char[][] room, int row, int seat) {
        if (room[row - 1][seat - 1] == 'S') {
            room[row - 1][seat - 1] = 'B';
        }
    }
}