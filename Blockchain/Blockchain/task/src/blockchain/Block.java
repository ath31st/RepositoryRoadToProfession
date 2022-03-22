package blockchain;

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
    private int numberOfMiner;
    private LinkedList<String> blockData;

    public Block(int id, String hashOfPreviousBlock, int numberOfZeroes, int numberOfMiner) {
        timeGenerateBlock = System.currentTimeMillis();
        this.numberOfMiner = numberOfMiner;
        this.numberOfZeroes = numberOfZeroes;
        this.id = id;
        this.magicNumber = (int) (Math.random() * 9999 + 1);
        this.timestamp = new Date().getTime();
        this.hashOfBlock = applySha256(String.valueOf(magicNumber), numberOfZeroes);
        this.hashOfPreviousBlock = hashOfPreviousBlock;
        this.blockData = new LinkedList<>();
        timeGenerateBlock = System.currentTimeMillis() - timeGenerateBlock;
    }

    public String getHashOfBlock() {
        return hashOfBlock;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Block: " + "\n" +
                "Created by miner # " + numberOfMiner + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block: " + "\n" + hashOfPreviousBlock + "\n" +
                "Hash of the block: " + "\n" + hashOfBlock + "\n" +
                "Block data: " + "\n" +
                "Block was generating for " + TimeUnit.MILLISECONDS.toSeconds(timeGenerateBlock) + " seconds" + "\n" +
                "N was increased to " + numberOfZeroes + "\n";
    }

    public void printResult() {
        System.out.println("Block: ");
        System.out.println("Created by miner # " + numberOfMiner);
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block: " + "\n" + hashOfPreviousBlock);
        System.out.println("Hash of the block: " + "\n" + hashOfBlock);
        System.out.println("Block data: ");
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
