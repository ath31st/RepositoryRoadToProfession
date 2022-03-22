package blockchain;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<User> users = List.of(
                new User("Nick", "Nick: Hey Tom, nice chat"),
                new User("Sarah", "Sarah: You always will be first because it is your blockchain!"),
                new User("Tom", "Tom: You're welcome :)"));
        blockChain = blockChain.stream().map(block -> {
            if (!(block.getId() == 1)) {
                block.setValueIntoBlockData(users.get(2).setMessage());
            }
            return block;
        }).collect(Collectors.toCollection(LinkedList::new));

        for (Block b : blockChain) {
            b.printResult();
        }
    }
}
