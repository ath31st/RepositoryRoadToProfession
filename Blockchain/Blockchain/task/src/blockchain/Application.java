package blockchain;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.Scanner;

public class Application {

    private static int numberOfZeros;

    public static void run() {

        try (Scanner scanner = new Scanner(System.in)) {
            numberOfZeros = scanner.nextInt();
            System.out.println("Enter how many zeros the hash must start with: " + numberOfZeros);
        }

        LinkedList<Block> blockChain = new LinkedList<>();

        int count = 1;
        while (count < 6) {
            if (count == 1) {
                blockChain.add(new Block(count, "0", numberOfZeros));
            } else {
                blockChain.add(new Block(count, blockChain.getLast().getHashOfBlock(), numberOfZeros));

            }
            count++;
        }
        for (Block b : blockChain) {
            System.out.println(b.toString());
        }
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
