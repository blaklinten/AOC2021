package aoc.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("input.txt");
  }

  void solve(String input) {
    try {
      List<String> lines = Files.readAllLines(Path.of(input));
      String part = System.getenv("part");
      if (part.equals("part1")) {
        part1(Arrays.asList(lines.get(0).split(",")));
      } else if (part.equals("part2")) {
        part2(Arrays.asList(lines.get(0).split(",")));
      }
    } catch (IOException e) {
      System.err.println("Could not read from file \"" + input + "\"");
    }
  }

  void part1(List<String> input) {
    System.out.println("part1");

    List<Integer> sorted = input.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

    int targetIndex = Math.floorDiv(sorted.size(), 2);
    Integer targetPosition = sorted.get(targetIndex);

    int totalCost = sorted.stream().reduce(0, (acc, next) -> acc + (Math.abs(next - targetPosition)));
    System.out.println("Total cost is: " + totalCost);
  }

  void part2(List<String> input) {
    System.out.println("part2");
  }
}
