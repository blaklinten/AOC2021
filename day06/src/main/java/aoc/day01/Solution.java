package aoc.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

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
    initialTTR.stream().forEach(TTR -> family.add(new LanternFish(TTR)));

    int numberOfDays = 80;

    while(numberOfDays > 0){
      for(int i = family.size() - 1; i >= 0; i--){
        LanternFish current = family.get(i);
        current.step(family);
      }
      System.out.println(family.size());
      numberOfDays--;
    }
    System.out.println("Final size is: " + family.size());
  }

  private void printFamily(List<LanternFish> family){
    family.forEach(fish -> System.out.print(fish.timeToReproduce + ","));
    System.out.println("");
  }

  private void part2(List<String> input) {
    System.out.println("part2");
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("input.txt");
  }

  class LanternFish {
    private int timeToReproduce;
    private List<LanternFish> family;

    public LanternFish(int TTR) {
      this.timeToReproduce = TTR;
    }

    public int step(List<LanternFish> family) {
      return timeToReproduce == 0 ? makeBaby(family) : timeToReproduce--;
    }

    private int makeBaby(List<LanternFish> family) {
      LanternFish baby = new LanternFish(8);
      family.add(baby);
      return timeToReproduce = 6;
    }
  }
}
