package aoc.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {


  public void solve(String input) {
    List<String> numbers = new ArrayList<>();
    List<List<List<String>>> bingoBoardList = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(Path.of(input));
      numbers = Arrays.asList(lines.get(0).split("\\s+"));
      bingoBoardList = getBingoBoards(lines);
    } catch (IOException e) {
      System.err.println("Could not read from file \"" + input + "\"");
    }

    String part = System.getenv("part");
    if (part.equals("part1")) {
      part1(bingoBoardList, numbers);
    } else if (part.equals("part2")) {
      part2(bingoBoardList, numbers);
    }

/*    System.out.println(bingoBoardList.get(0).get(0));
    System.out.println(bingoBoardList.get(0).get(1));
    System.out.println(bingoBoardList.get(0).get(2));
    System.out.println(bingoBoardList.get(0).get(3));
    System.out.println(bingoBoardList.get(0).get(4));
    System.out.println("");
    System.out.println(bingoBoardList.get(1).get(0));
    System.out.println(bingoBoardList.get(1).get(1));
    System.out.println(bingoBoardList.get(1).get(2));
    System.out.println(bingoBoardList.get(1).get(3));
    System.out.println(bingoBoardList.get(1).get(4));
    System.out.println("");
    System.out.println(bingoBoardList.get(2).get(0));
    System.out.println(bingoBoardList.get(2).get(1));
    System.out.println(bingoBoardList.get(2).get(2));
    System.out.println(bingoBoardList.get(2).get(3));
    System.out.println(bingoBoardList.get(2).get(4));  */
  }


  private List<List<List<String>>> getBingoBoards(List<String> inputLines){
    inputLines.remove(0);

    List<List<String>> tempBoard = new ArrayList<>();
    List<List<List<String>>> bingoBoardList = new ArrayList<>();

    for (int i = 0; i <inputLines.size() ; i++) {
      String line = inputLines.get(i);
      if(!line.equals("")) {
        ArrayList<String> lineNumbers = new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
        tempBoard.add(lineNumbers);
        if (tempBoard.size() == 5) {
          bingoBoardList.add(new ArrayList<>(tempBoard));
          tempBoard.clear();
        }
      }
    }
    return bingoBoardList;
  }

  void part1(List<List<List<String>>> input, List<String> numbers) {
    System.out.println("part1");

  }

  void part2(List<List<List<String>>> input, List<String> numbers) {
    System.out.println("part2");
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("test_input.txt");
  }
}
