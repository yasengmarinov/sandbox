import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by b06514a on 1/23/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_SIZE = 10;

    private Item[] array;
    private int end;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[INITIAL_SIZE];
        end = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return end == 0;
    }

    // return the number of items on the queue
    public int size() {
        return end;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (end == array.length) {
            Item[] newArray = (Item[]) new Object[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[end++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (end == array.length / 4) {
            Item[] newArray = (Item[]) new Object[array.length / 4];
            for (int i = 0; i < end; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        int randomIndex = StdRandom.uniform(0, end);
        Item toReturn = array[randomIndex];
        for (int i = randomIndex; i < end - 1; i++) {
            array[i] = array[i+1];
        }
        array[--end] = null;
        return toReturn;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(0, end);
        return array[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] randomizedArray;
        int index;

        public RandomizedQueueIterator() {
            randomizedArray = (Item[]) new Object[end];
            for (int i = 0; i < end; i++) {
                randomizedArray[i] = array[i];
            }
            StdRandom.shuffle(randomizedArray);
            index = end;
        }
        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return randomizedArray[--index];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}