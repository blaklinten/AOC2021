package aoc.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.util.Pair;

public class Solution {

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("input.txt");
  }

  public void solve(String input) {
    List<String> commands = new ArrayList<>();
    try {
      commands = Files.lines(Path.of(input)).collect(Collectors.toList());
    } catch (IOException e) {
      System.err.println("Could not read from file \"" + input + "\"");
    }

    String part = System.getenv("part");
    if (part.equals("part1")) {
      part1(commands);
    } else if (part.equals("part2")) {
      part2(commands);
    }
  }

  private void part1(List<String> input) {
    System.out.println("part1");
    List<Integer> initialTTR =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

    List<LanternFish> family = new ArrayList<>();
    initialTTR.forEach(TTR -> family.add(new LanternFish(TTR)));

    int numberOfDays = 80;

    while (numberOfDays > 0) {
      for (int i = family.size() - 1; i >= 0; i--) {
        LanternFish current = family.get(i);
        current.step(family);
      }
      numberOfDays--;
    }
    System.out.println("Final size is: " + family.size());
  }

  private void printFamily(List<LanternFish> family) {
    family.forEach(fish -> System.out.print(fish.timeToReproduce + ","));
    System.out.println("");
  }

  private void part2(List<String> input) {
    System.out.println("part2");
    List<Integer> initialTTR =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

    int numberOfDays = 256;
    long population = initialTTR.size();
    Map<Pair<Integer, Integer>, Long> table = getChildrenCache(numberOfDays);
    for (Integer TTR : initialTTR) {
      Pair<Integer, Integer> cacheKey = new Pair<>(TTR, numberOfDays);
      Long children = table.get(cacheKey);
      population = population + children;
    }
    System.out.println("Final family size is " + population);
  }

  private Map<Pair<Integer, Integer>, Long> getChildrenCache(int numberOfDays) {
    Map<Pair<Integer, Integer>, Long> table = new HashMap<>();

    for (int ttr = 0; ttr < 8; ttr++) {
      for (int days = 0; days <= numberOfDays; days++) {
        Pair<Integer, Integer> entry = new Pair<>(ttr, days);
        if (table.containsKey(entry)) {
          continue;
        }
        Long numberOfChildren = calculateNumberOfChildren(table, entry);
        table.put(entry, numberOfChildren);
      }
    }
    return table;
  }

  private Long calculateNumberOfChildren(
      Map<Pair<Integer, Integer>, Long> table, Pair<Integer, Integer> entry) {
    if (table.containsKey(entry)) {
      return table.get(entry);
    }
    Integer TTR = entry.getFirst();
    Integer daysToGo = entry.getSecond();
    if (daysToGo == 0) {
      return 0L;
    }
    if (TTR == 0) {
      daysToGo--;
      Pair<Integer, Integer> child = new Pair<>(8, daysToGo);
      Pair<Integer, Integer> gettingOlder = new Pair<>(6, daysToGo);
      return 1
          + calculateNumberOfChildren(table, gettingOlder)
          + calculateNumberOfChildren(table, child);
    }
    Pair<Integer, Integer> gettingOlder = new Pair<>(--TTR, --daysToGo);
    return calculateNumberOfChildren(table, gettingOlder);
  }

  static class LanternFish {
    private int timeToReproduce;
    private List<LanternFish> family;

    public LanternFish(int TTR) {
      this.timeToReproduce = TTR;
    }

    public void step(List<LanternFish> family) {
      if (timeToReproduce == 0) {
        makeBaby(family);
      } else {
        timeToReproduce--;
      }
    }

    private void makeBaby(List<LanternFish> family) {
      LanternFish baby = new LanternFish(8);
      family.add(baby);
      timeToReproduce = 6;
    }

    public Integer spawn(int daysToGo) {
      if (daysToGo < 1) {
        return 0;
      }
      if (timeToReproduce == 0) {
        timeToReproduce = 6;
        System.out.println("Spawning a child");
        return spawn(--daysToGo) + 1 + new LanternFish(8).spawn(--daysToGo);
      } else {
        System.out.println("Does not spawning a child...");
        timeToReproduce--;
        return spawn(--daysToGo);
      }
    }
  }
}
