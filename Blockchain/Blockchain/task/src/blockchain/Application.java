package blockchain;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private static int numberOfZeros;

    public static void run() {

        Blockchain blockchain = Blockchain.getInstance();
        LinkedList<Block> blockChain = blockchain.getBlockChain();

        Miner miner9 = new Miner("miner9");

        int count = 1;
        while (count < 16) {
            if (count == 1) {
                blockChain.add(new Block(count, "0", numberOfZeros, miner9.getName()));
                miner9.setScore(miner9.getScore() + 100);
            } else {
                blockChain.add(new Block(count, blockChain.getLast().getHashOfBlock(), numberOfZeros, miner9.getName()));
                miner9.setScore(miner9.getScore() + 100);
            }
            count++;
        }
        List<User> users = List.of(
                new User("Nick", "Nick: Hey Tom, nice chat"),
                new User("Sarah", "Sarah: You always will be first because it is your blockchain!"),
                new User("Tom", "Tom: You're welcome :)"));
        blockChain = blockChain.stream().map(block -> {
            if (block.getId() != 1) {
                block.setValueIntoBlockData(users.get(2).setMessage());
            }
            if (block.getId() != 1 & block.getId() != 2) {
                block.setValueIntoBlockData(users.get(0).setMessage());
            }
            return block;
        }).collect(Collectors.toCollection(LinkedList::new));

        for (Block b : blockChain) {
            b.printResult();
        }
    }
}
