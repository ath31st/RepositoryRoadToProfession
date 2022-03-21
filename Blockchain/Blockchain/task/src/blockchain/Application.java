package blockchain;

import java.util.LinkedList;

public class Application {

    private static int numberOfZeros;

    public static void run() {

        Blockchain blockchain = Blockchain.getInstance();
        LinkedList<Block> blockChain = blockchain.getBlockChain();

        int count = 1;
        while (count < 6) {
            if (count == 1) {
                blockChain.add(new Block(count, "0", numberOfZeros, 6));
            } else {
                blockChain.add(new Block(count, blockChain.getLast().getHashOfBlock(), numberOfZeros, 6));

            }
            count++;
        }
        for (Block b : blockChain) {
            System.out.println(b.toString());
        }
    }
}
