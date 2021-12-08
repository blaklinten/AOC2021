package aoc.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

  static int BINARY_WIDTH;

  public void solve(String input) {
    isTest(input);
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

  void isTest(String input) {
    if (input.equals("test_input.txt")) {
      BINARY_WIDTH = 5;
    } else {
      BINARY_WIDTH = 12;
    }
  }

  void part1(List<String> input) {
    System.out.println("part1");
    List<char[]> binaryNumbers = toBinary(input);
    int[] count = getBitCount(binaryNumbers);
    double[] gammaAndEpsilon = getGammaAndEpsilon(count);
    System.out.println(gammaAndEpsilon[0] * gammaAndEpsilon[1]);
  }

  List<char[]> toBinary(List<String> input) {
    List<char[]> binary = new ArrayList<>(Collections.emptyList());
    for (String binaryNumber : input) {
      char[] bits = binaryNumber.toCharArray();
      binary.add(bits);
    }
    return binary;
  }

  int[] getBitCount(List<char[]> input) {
    int[] count = new int[BINARY_WIDTH];
    for (char[] binaryNumber : input) {
      int index = BINARY_WIDTH - 1;
      while (index >= 0) {
        if (binaryNumber[index] == '1') {
          count[index]++;
        } else {
          count[index]--;
        }
        index--;
      }
    }
    return count;
  }

  double[] getGammaAndEpsilon(int[] count) {
    int exponent = 0;
    double gamma = 0, epsilon = 0;
    for (int index = BINARY_WIDTH - 1; index >= 0; index--, exponent++) {
      if (count[index] > 0) {
        gamma = gamma + Math.pow(2, exponent);
      } else {
        epsilon = epsilon + Math.pow(2, exponent);
      }
    }
    return new double[] {gamma, epsilon};
  }

  void part2(List<String> input) {
    System.out.println("part2");
    List<char[]> binaryNumbers = toBinary(input);
    String oxygenRating = new String (getRating(binaryNumbers, true));
    String co2Rating = new String (getRating(binaryNumbers, false));
    int oxygenRatingInDecimal = Integer.parseInt(oxygenRating, 2);
    int co2RatingInDecimal = Integer.parseInt(co2Rating, 2);
    System.out.println(oxygenRatingInDecimal * co2RatingInDecimal);
  }

  char[] getRating(List<char[]> input, boolean oxygen) {
    int[] count = getBitCount(input);
    int index = 0;
    char wantedValue;
    while (input.size() > 1) {
      if (count[index] > 0) {
         wantedValue = oxygen ? '1' : '0';
      } else if (count[index] < 0) {
        wantedValue = oxygen ? '0' : '1';
      } else {
        wantedValue = oxygen ? '1' : '0';
      }
      input = filter(input, wantedValue, index);
      count = getBitCount(input);
      index++;
    }
    return input.get(0);
  }

  List<char[]> filter(List<char[]> input, char wantedValue, int index) {
    List<char[]> filteredList = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      char[] number = input.get(i);
      if (wantedValue == number[index]) {
        filteredList.add(input.get(i));
      }
    }
    return filteredList;
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("input.txt");
  }
}
