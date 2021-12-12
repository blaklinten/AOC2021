package aoc.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution {

  static int COLUMN_SIZE = 5;
  static int ROW_SIZE = 5;
  static int BOARD_SIZE = COLUMN_SIZE * ROW_SIZE;

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

  List<BingoBoard> getBingoBoards(List<String> inputLines) {
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
    for (Integer drawnNumber : numbers) {
      List<BingoBoard> winners = getWinners(bingoBoardList, drawnNumber);
      if (winners.size() > 0) {
        Integer score = calculateScore(winners.get(0), drawnNumber);
        System.out.println("Wins with score " + score);
        break;
      }
    }
  }

  void part2(List<String> input) {
    System.out.println("part2");
    List<BingoBoard> bingoBoardList = new ArrayList<>();
    List<Integer> numbers =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    input.remove(0);
    bingoBoardList = getBingoBoards(input);
    BingoBoard lastWinner = null;
    Integer lastWinningNumber = null;
    for (Integer drawnNumber : numbers) {
      List<BingoBoard> winners = getWinners(bingoBoardList, drawnNumber);
      if (winners.size() > 0) {
        lastWinner = new BingoBoard(winners.get(0));
        lastWinningNumber = drawnNumber;
        if (bingoBoardList.size() == 1) {
          break;
        }
        for (BingoBoard board : winners) {
          bingoBoardList.remove(board);
        }
      }
    }
    Integer score = calculateScore(lastWinner, lastWinningNumber);
    System.out.println("With score: " + score);
  }

  Integer calculateScore(BingoBoard winner, Integer drawnNumber) {
    return winner.allNumbers.stream().reduce(0, Integer::sum) * drawnNumber;
  }

  List<BingoBoard> getWinners(List<BingoBoard> boards, Integer drawnNumber) {
    List<BingoBoard> winners = new ArrayList<>();
    for (BingoBoard board : boards) {
      boolean won = board.wins(drawnNumber);
      if (won) {
        winners.add(board);
      }
    }
    return winners;
  }

  static class BingoBoard {
    private final List<Integer> allNumbers;
    private final List<Row> rows = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();

    public BingoBoard(BingoBoard toCopy) {
      allNumbers = new ArrayList<>(toCopy.allNumbers);

      for (int column = 0; column < COLUMN_SIZE; column++) {
        List<Integer> tempColumn = new ArrayList<>();
        for (int index = 0; index < COLUMN_SIZE; index++) {
          tempColumn.add(toCopy.columns.get(column).numbers.get(index));
        }
        columns.add(new Column(tempColumn));
        tempColumn.clear();
      }

      for (int row = 0; row < ROW_SIZE; row++) {
        List<Integer> tempRow = new ArrayList<>();
        for (int index = 0; index < ROW_SIZE; index++) {
          tempRow.add(toCopy.rows.get(row).numbers.get(index));
        }
        rows.add(new Row(tempRow));
        tempRow.clear();
      }
    }

    public BingoBoard(List<Integer> numbers) {
      allNumbers = new ArrayList<>(numbers);
      for (int column = 0; column < COLUMN_SIZE; column++) {
        List<Integer> tempColumn = new ArrayList<>();
        for (int index = 0; index < COLUMN_SIZE; index++) {
          tempColumn.add(numbers.get(column + index * COLUMN_SIZE));
        }
        columns.add(new Column(tempColumn));
        tempColumn.clear();
      }

      for (int row = 0; row < ROW_SIZE; row++) {
        List<Integer> tempRow = new ArrayList<>();
        for (int index = 0; index < ROW_SIZE; index++) {
          tempRow.add(numbers.get(row * ROW_SIZE + index));
        }
        rows.add(new Row(tempRow));
        tempRow.clear();
      }
    }

    void printBoard() {
      for (Row row : rows) {
        for (Integer num : row.numbers) {
          System.out.print(num + " ");
        }
        System.out.println();
      }
    }

    boolean wins(Integer drawnNumber) {
      if (!allNumbers.contains(drawnNumber)) {
        return false;
      }
      while (allNumbers.remove(drawnNumber)) {}
      for (Row row : rows) {
        List<Integer> numbers = row.numbers;
        for (int index = 0; index < numbers.size(); index++) {
          if (numbers.get(index) != null && numbers.get(index).equals(drawnNumber)) {
            row.clearNumber(index);
            boolean allNull = row.numbers.stream().allMatch(Objects::isNull);
            if (allNull) {
              return true;
            }
          }
        }
      }

      for (Column column : columns) {
        List<Integer> numbers = column.numbers;
        for (int index = 0; index < numbers.size(); index++) {
          if (numbers.get(index) != null && numbers.get(index).equals(drawnNumber)) {
            column.clearNumber(index);
            boolean allNull = column.numbers.stream().allMatch(Objects::isNull);
            if (allNull) {
              return true;
            }
          }
        }
      }
      return false;
    }

    static class Row {
      List<Integer> numbers;

      Row(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
      }

      void clearNumber(int index) {
        numbers.set(index, null);
      }
    }

    static class Column {
      List<Integer> numbers;

      Column(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
      }

      void clearNumber(int index) {
        numbers.set(index, null);
      }
    }
  }
}
