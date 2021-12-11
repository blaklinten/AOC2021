package aoc.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

  static int ROW_COLUMN_SIZE = 5;
  static int BOARD_SIZE = ROW_COLUMN_SIZE * ROW_COLUMN_SIZE;

  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.solve("test_input.txt");
  }

  public void solve(String input) {
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

  private List<BingoBoard> getBingoBoards(List<String> inputLines) {
    List<Integer> board = new ArrayList<>();
    List<BingoBoard> bingoBoardList = new ArrayList<>();

    for (String line : inputLines) {
      if (!line.equals("")) {
        board.addAll(convertToIntegers(line));
        if (board.size() == BOARD_SIZE) {
          bingoBoardList.add(new BingoBoard(board));
          board.clear();
        }
      }
    }
    return bingoBoardList;
  }

  List<Integer> convertToIntegers(String input) {
    String[] numbers = input.trim().split("\\s+");
    List<Integer> result = new ArrayList<>();

    for (String number : numbers) {
      result.add(Integer.parseInt(number));
    }
    return result;
  }

  void part1(List<String> input) {
    System.out.println("part1");

    List<BingoBoard> bingoBoardList = new ArrayList<>();
    List<Integer> numbers =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    input.remove(0);
    bingoBoardList = getBingoBoards(input);
    numbers.stream().takeWhile(drawn -> drawn > 0).forEach(System.out::print);
  }

  void part2(List<String> input) {
    System.out.println("part2");
  }

  private static class BingoBoard {
    private final List<Integer> numbers;
    private final List<Row> rows = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();

    public BingoBoard(List<Integer> numbers) {
      this.numbers = new ArrayList<>(numbers);
      for (int column = 0; column < ROW_COLUMN_SIZE; column++) {
        List<Integer> tempColumn = new ArrayList<>();
        for (int index = 0; index < ROW_COLUMN_SIZE; index++) {
          tempColumn.add(numbers.get(column + index * ROW_COLUMN_SIZE));
        }
        columns.add(new Column(tempColumn));
        tempColumn.clear();
      }

      for (int row = 0; row < ROW_COLUMN_SIZE; row++) {
        List<Integer> tempRow = new ArrayList<>();
        for (int index = 0; index < ROW_COLUMN_SIZE; index++) {
          tempRow.add(numbers.get(row * ROW_COLUMN_SIZE + index));
        }
        rows.add(new Row(tempRow));
        tempRow.clear();
      }
    }

    public Row getRow(int index) {
      return rows.get(index);
    }

    public Column getColumn(int index) {
      return columns.get(index);
    }

    private class Row {
      List<Integer> numbers;

      public Row(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
      }

      public List<Integer> getNumbers() {
        return numbers;
      }
    }

    private class Column {
      List<Integer> numbers;

      public Column(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
      }

      public List<Integer> getNumbers() {
        return numbers;
      }
    }
  }
}
