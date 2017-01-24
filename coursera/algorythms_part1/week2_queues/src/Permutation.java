import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by b06514a on 1/23/2017.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        Iterator<String> iterator = randomizedQueue.iterator();

        for (int i = 0; i < k; i++) {
            StdOut.print(iterator.next() + "\n");
        }
    }

}
