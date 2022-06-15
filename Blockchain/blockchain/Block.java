package blockchain;

import org.w3c.dom.ls.LSOutput;

import java.security.MessageDigest;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Block {
    private final int id;
    private final long timestamp;
    private final String hashOfBlock;
    private final String hashOfPreviousBlock;
    private final int magicNumber;
    private long timeGenerateBlock;
    private int numberOfZeroes;
    private String nameOfMiner;
    private LinkedList<String> blockData;
    private LinkedList<String> transaction;

    public Block(int id, String hashOfPreviousBlock, int numberOfZeroes, String nameOfMiner) {
        timeGenerateBlock = System.currentTimeMillis();
        this.numberOfZeroes = numberOfZeroes;
        this.nameOfMiner = nameOfMiner;
        this.id = id;
        this.magicNumber = (int) (Math.random() * 9999 + 1);
        this.timestamp = new Date().getTime();
        this.hashOfBlock = applySha256(String.valueOf(magicNumber), numberOfZeroes);
        this.hashOfPreviousBlock = hashOfPreviousBlock;
        this.blockData = new LinkedList<>();
        this.transaction = new LinkedList<>();
        timeGenerateBlock = System.currentTimeMillis() - timeGenerateBlock;
    }

    public String getHashOfBlock() {
        return hashOfBlock;
    }

    public int getId() {
        return id;
    }

    public void printResult() {
        System.out.println("Block: ");
        System.out.println("Created by: " + nameOfMiner);
        System.out.println(nameOfMiner + " gets 100 VC");
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block: " + "\n" + hashOfPreviousBlock);
        System.out.println("Hash of the block: " + "\n" + hashOfBlock);
        System.out.println("Block data: ");
        if (transaction.isEmpty()) {
            System.out.println("No transactions");
        } else {
            printData(transaction);
        }
        if (!(blockData.isEmpty())) printData(blockData);
        System.out.println("Block was generating for " + TimeUnit.MILLISECONDS.toSeconds(timeGenerateBlock) + " seconds");
        System.out.println("N was increased to " + numberOfZeroes + "\n");

    }

    public static String applySha256(String input, int numberOfZeroes) {
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
            return "0".repeat(numberOfZeroes) + hexString.toString().substring(numberOfZeroes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setValueIntoBlockData(String value) {
        this.blockData = blockData;
        blockData.add(value);
    }

    private static void printData(LinkedList<String> blockData) {
        blockData.forEach(System.out::println);
    }
}
