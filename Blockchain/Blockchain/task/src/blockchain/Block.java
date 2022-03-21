package blockchain;

import java.security.MessageDigest;
import java.util.Date;
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

    public Block(int id, String hashOfPreviousBlock, int numberOfZeroes, int numberOfMiner) {
        timeGenerateBlock = System.currentTimeMillis();
        this.numberOfMiner = numberOfMiner;
        this.numberOfZeroes = numberOfZeroes;
        this.id = id;
        this.magicNumber = (int)(Math.random() * 9999 + 1);
        this.timestamp = new Date().getTime();
        this.hashOfBlock = applySha256(String.valueOf(magicNumber),numberOfZeroes);
        this.hashOfPreviousBlock = hashOfPreviousBlock;
        timeGenerateBlock = System.currentTimeMillis() - timeGenerateBlock;
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHashOfBlock() {
        return hashOfBlock;
    }

    public String getHashOfPreviousBlock() {
        return hashOfPreviousBlock;
    }

    public int getMagicNumber() {
        return magicNumber;
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
                "Block was generating for " + TimeUnit.MILLISECONDS.toSeconds(timeGenerateBlock) + " seconds" + "\n" +
                "N was increased to " + numberOfZeroes + "\n";
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
}
