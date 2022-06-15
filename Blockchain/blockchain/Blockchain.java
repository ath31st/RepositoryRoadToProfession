package blockchain;

import java.util.LinkedList;

public class Blockchain {
    private final LinkedList<Block> blockChain = new LinkedList<>();
    private static Blockchain INSTANCE;

    private Blockchain() { }

    public static Blockchain getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Blockchain();
        }
        return INSTANCE;
    }

    public LinkedList<Block> getBlockChain() {
        return blockChain;
    }
}
