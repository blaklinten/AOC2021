package aoc.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

  protected final String FORWARD = "forward";
  protected final String UP = "up";
  protected final String DOWN = "down";

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
    int posX = 0, posY = 0;

    List<Command> commands = new ArrayList<>();

    for (String line : input) {
      String[] parts = line.split(" ");
      commands.add(new Command(parts));
    }

    for (Command command : commands) {
      switch (command.getDirection()) {
        case FORWARD:
          posX += command.getValue();
          break;
        case UP:
          posY -= command.getValue();
          break;
        case DOWN:
          posY += command.getValue();
          break;
      }
    }

    int total = posX * posY;
    System.out.println(total);
  }

  private void part2(List<String> input) {
    System.out.println("part2");

    int posX = 0, posY = 0, aim = 0;

    ArrayList<Command> commands = new ArrayList<>();

    for (String line : input) {
      String[] parts = line.split(" ");
      commands.add(new Command(parts));
    }

    for (Command command : commands) {
      int directionalValue = command.getValue();
      switch (command.getDirection()) {
        case FORWARD:
          posX += directionalValue;
          posY += directionalValue * aim;
          break;
        case UP:
          aim -= directionalValue;
          break;
        case DOWN:
          aim += directionalValue;
          break;
      }
    }

    int total = posX * posY;
    System.out.println(total);
  }

  private static class Command {
    String direction;
    int value;

    public Command(String[] parts) {
      this.direction = parts[0];
      this.value = Integer.parseInt(parts[1]);
    }

    public String getDirection() {
      return direction;
    }

    public int getValue() {
      return value;
    }
  }
}
