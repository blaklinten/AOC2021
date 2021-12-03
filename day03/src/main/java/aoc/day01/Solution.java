package aoc.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    private final static int BINARY_WIDTH = 12;

  public void solve(String input) {
    List<String> numbers = new ArrayList<>();
    try {
      numbers = Files.lines(Path.of(input)).collect(Collectors.toList());
    } catch (IOException e) {
      System.err.println("Could not read from file \"" + input + "\"");
    }

    String part = System.getenv("part");
    if (part.equals("part1")) {
      part1(numbers);
    } else if (part.equals("part2")) {
      part2(numbers);
    }
  }

  private void part1(List<String> input) {
    System.out.println("part1");

    int numberOfLines = input.size();

    Integer totalSum = input.stream()
        .map(line -> Integer.parseInt(line, 2))
        .reduce(0, (a, b) -> a + b);

    int bit = BINARY_WIDTH - 1;
    while (bit-- >= 0){
      double bitTotal = (Math.pow(2, bit))*numberOfLines;
      if (bitTotal >)
    }
  }

  private void part2(List<String> input) {
    System.out.println("part2");
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("test_input.txt");
  }
}
