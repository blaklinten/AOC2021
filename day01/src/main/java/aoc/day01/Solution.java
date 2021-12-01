package aoc.day01;

import org.apache.commons.math3.primes.Primes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

  public void solve(String input) {
    List<Integer> numbers = new ArrayList<>();
    try {
      numbers = Files.lines(Path.of(input)).map(Integer::parseInt).collect(Collectors.toList());
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

  private void part1(List<Integer> input) {
    System.out.println("part1");
    Integer previous = input.get(0);
    Integer count = 0;
    for (int i = 1; i < input.size(); i++) {
      Integer current = input.get(i);
      if (current > previous) {
        count++;
      }
      previous = current;
    }
    System.out.println(count);
  }

  private boolean isPrime(int i) {
    return Primes.isPrime(i);
  }

  private void part2(List<Integer> input) {
    System.out.println("part2");
    final int START_INDEX = 2;
    Integer previous = getWindow(input, START_INDEX);
    Integer count = 0;
    for (int i = START_INDEX; i < input.size(); i++) {
      Integer current = getWindow(input, i);
      if ( current > previous) {
        count++;
      }
      previous = current;
    }
    System.out.println(count);
  }

  private Integer getWindow(List<Integer> input, int index){
    return input.get(index - 2) +
            input.get(index - 1) +
            input.get(index);
  }

  private boolean isEven(int i) {
    return i % 2 == 0;
  }

  private boolean isNotPrime(int i) {
    return !isPrime(i);
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("input.txt");
  }
}
