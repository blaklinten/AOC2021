package aoc.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        part1(lines);
      } else if (part.equals("part2")) {
        part2(lines);
      }
    } catch (IOException e) {
      System.err.println("Could not read from file \"" + input + "\"");
    }
  }

  void part1(List<String> input) {
    System.out.println("part1");
    List<String> outPutvalues = input.stream().map(line -> line.split("\\s\\|\\s")[1]).collect(Collectors.toList());
    int count = outPutvalues.stream().reduce("", (acc, next) -> acc + getUniqueDigits(next)).length();
    System.out.println("Unique number of digits used is " + count);
    }

  String getUniqueDigits(String values){
      return Arrays.asList(values.split(" ")).stream().reduce("", (acc, value) -> acc + countUniqeDigits(value));
  }

  String countUniqeDigits(String digit){
      return isTargetLength(digit) ? "1" : "";
  }

  boolean isTargetLength(String string){
      int length = string.length();
      return length == 2 || length == 3 || length == 4 || length == 7;
  }

  void part2(List<String> input) {
    System.out.println("part2");
  }
}
