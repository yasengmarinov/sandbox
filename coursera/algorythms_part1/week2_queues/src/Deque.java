import java.util.NoSuchElementException;

/**
 * Created by yasen on 1/22/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Element first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Element toAdd = new Element(item);
        if (this.isEmpty()) {
            first = toAdd;
            last = toAdd;
        } else {
            first.previous = toAdd;
            toAdd.next = first;
            first = toAdd;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Element toAdd = new Element(item);
        if (this.isEmpty()) {
            first = toAdd;
            last = toAdd;
        } else {
            last.next = toAdd;
            toAdd.previous = last;
            last = toAdd;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item toReturn = first.item;
        first = first.next;
        size--;
        if (!this.isEmpty()) {
            first.next.previous = first;
        } else {
            last = null;
        }
        return toReturn;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item toReturn = last.item;
        last = last.previous;
        last.next = null;
        size--;
        if (!this.isEmpty()) {
            last.previous.next = last;
        } else {
            first = null;
        }
        return  toReturn;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>();
    }

    private class Iterator<Item> implements java.util.Iterator<Item> {

        private Element current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    private class Element {
        Item item;
        Element next;
        Element previous;

        public Element (Item item) {
            this.item = item;
            this.next = null;
        }
    }
    // unit testing (optional)
    public static void main(String[] args) {

    }
}