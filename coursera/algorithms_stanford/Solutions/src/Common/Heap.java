package Common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by b06514a on 5/4/2017.
 */
public class Heap<T> {

    private List<T> array;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        array = new ArrayList<>();
        this.comparator = comparator;
    }

    public void put(T o) {
        array.add(o);
        bubbleUp(array.size());
    }

    public T getFirst() {
        if (array.isEmpty()) return null;

        return getElement(1);
    }

    public T removeFirst() {
        if (array.isEmpty()) return null;
        swap(1, array.size());
        T tmp = array.remove(array.size() - 1);
        sinkDown(1);

        return tmp;
    }

    public int size() {
        return array.size();
    }

    private void bubbleUp(int index) {
        if (index < 2) return;
        if (comparator.compare(getElement(index), getParent(index)) == -1) {
            swap(index, getParentIndex(index));
            bubbleUp(getParentIndex(index));
        }
    }

    private void sinkDown(int index) {
        if (getFirstChildIndex(index) > array.size()) {
            return;
        }

        if (comparator.compare(getElement(index), (getSmallestChild(index))) == 1) {
            int smallestChildIndex = getSmallestChildIndex(index);
            swap(index, smallestChildIndex);
            sinkDown(smallestChildIndex);
        }

    }

    private void swap(int index1, int index2) {
        T tmp = getElement(index1);
        array.set(index1 - 1, getElement(index2));
        array.set(index2 - 1, tmp);
    }

    private T getElement(int index) {
        return array.get(index - 1);
    }

    private T getParent(int index) {
        return array.get(getParentIndex(index) - 1);
    }

    private int getParentIndex(int index) {
        return (index % 2 == 0)? index / 2 : (index - 1) / 2;
    }

    private T getFirstChild(int index) {
        return getElement(getFirstChildIndex(index));
    }

    private int getFirstChildIndex(int index) {
        return index * 2;
    }

    private T getSecondChild(int index) {
        return getElement(getSecondChildIndex(index));
    }

    private int getSecondChildIndex(int index) {
        return index * 2 + 1;
    }

    private T getSmallestChild(int index) {
        return getElement(getSmallestChildIndex(index));
    }

    private int getSmallestChildIndex(int index) {
        return (getSecondChildIndex(index) > array.size() || comparator.compare(getFirstChild(index), (getSecondChild(index))) <= 0 ?
                getFirstChildIndex(index) : getSecondChildIndex(index));
    }

    public static <T1 extends Comparable<? super T1>> Comparator<T1> getMinComparator() {
        return ((o1, o2) -> o1.compareTo(o2));
    }

    public static <T1 extends Comparable<? super T1>> Comparator<T1> getMaxComparator() {
        return ((o1, o2) -> o2.compareTo(o1));
    }
}
