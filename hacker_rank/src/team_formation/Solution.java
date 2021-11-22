package team_formation;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int t = scanner.nextInt();
    for (int i = 0; i < t; i++) {
      runInstance(scanner);
    }
  }

  private static void runInstance(Scanner scanner) {
    int n = scanner.nextInt();
    if (n == 0) {
      System.out.println(0);
      return;
    }
    Queue<Integer> contestants = new PriorityQueue<>(n);

    for (int i = 0; i < n; i++) {
      contestants.add(scanner.nextInt());
    }

    Map<Integer, Queue<Group>> groupsMap = new HashMap<>();

    while (!contestants.isEmpty()) {
      int currentContestant = contestants.poll();
      if (groupsMap.containsKey(currentContestant - 1)
          && !groupsMap.get(currentContestant - 1).isEmpty()) {
        Group smallestGroup = groupsMap.get(currentContestant - 1).poll();
        smallestGroup.incrementSize();
        smallestGroup.incrementEndSkill();

        if (groupsMap.containsKey(smallestGroup.getEndSkill())) {
          groupsMap.get(smallestGroup.getEndSkill()).add(smallestGroup);
        } else {
          Queue<Group> groupsQueue = new PriorityQueue<>();
          groupsQueue.add(smallestGroup);
          groupsMap.put(currentContestant, groupsQueue);
        }
      } else {
        if (groupsMap.containsKey(currentContestant)) {
          groupsMap.get(currentContestant).add(new Group(currentContestant));
        } else {
          Queue<Group> groupsQueue = new PriorityQueue<>();
          groupsQueue.add(new Group(currentContestant));
          groupsMap.put(currentContestant, groupsQueue);
        }
      }
    }

    int minGroup = Integer.MAX_VALUE;
    for (Queue<Group> queue : groupsMap.values()) {
      if (!queue.isEmpty() && queue.peek().size < minGroup) {
        minGroup = queue.peek().size;
      }
    }

    System.out.println(minGroup);
  }

  private static class Group implements Comparable<Group> {

    private int size;
    private int endSkill;

    public Group(int endSkill) {
      this.endSkill = endSkill;
      this.size = 1;
    }

    public int getSize() {
      return size;
    }

    public int getEndSkill() {
      return endSkill;
    }

    @Override
    public int compareTo(Group o) {
      return Integer.compare(this.size, o.size);
    }

    public void incrementSize() {
      this.size++;
    }

    public void incrementEndSkill() {
      this.endSkill++;
    }
  }
}
