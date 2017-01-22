/**
 * Created by yasen on 1/22/17.
 */
public class Main {
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(4);
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        for (Integer integer:deque) {
            System.out.println(integer);
        }
    }
}
