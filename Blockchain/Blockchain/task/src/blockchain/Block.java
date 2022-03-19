package blockchain;

public class Block {
    private final int id;
    private final long timestamp;
    private final String hashOfBlock;
    private final String hashOfPreviousBlock;

    public Block(int id, long timestamp, String hashOfBlock, String hashOfPreviousBlock) {
        this.id = id;
        this.timestamp = timestamp;
        this.hashOfBlock = hashOfBlock;
        this.hashOfPreviousBlock = hashOfPreviousBlock;
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

    @Override
    public String toString() {
        return "Block: " + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Hash of the previous block: " + "\n" + hashOfPreviousBlock + "\n" +
                "Hash of the block: " + "\n" + hashOfBlock + "\n";
    }
}
