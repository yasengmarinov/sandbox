package course3.week1;

import java.util.Comparator;

/**
 * Created by b06514a on 5/12/2017.
 */
public class Job {

    private int weight, length;

    public Job(int weight, int length) {
        this.weight = weight;
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public String toString() {
        return String.valueOf(weight/length);
    }

    public static Comparator<Job> differenceComparator() {
        return (o2, o1) -> Integer.compare(o1.getWeight() - o1.getLength(), o2.getWeight() - o2.getLength()) == 0?
                Integer.compare(o1.weight, o2.weight) : Integer.compare(o1.getWeight() - o1.getLength(), o2.getWeight() - o2.getLength());
    }

    public static Comparator<Job> ratioComparator() {
        return (o2, o1) -> Double.compare((double)o1.getWeight() / o1.getLength(), (double)o2.getWeight() / o2.getLength());
    }
}
