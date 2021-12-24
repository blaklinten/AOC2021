package aoc.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    List<Line> strightLines =
        input.stream()
            .map(this::getStraightLine)
            .filter(line -> line != null)
            .collect(Collectors.toList());

    Map<String, Integer> count = new HashMap<>();
    strightLines.stream().forEach(line -> addAllPoints(line, count));

    List<Map.Entry<String, Integer>> dangerousPoints =
        count.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .collect(Collectors.toList());

    System.out.println("Number of dangerous points: " + dangerousPoints.size());
  }

  void addAllPoints(Line line, Map<String, Integer> count) {
    for (Point point : line.points) {
      if (count.containsKey(point.toString())) {
        Integer newCount = count.get(point.toString()) + 1;
        count.put(point.toString(), newCount);
      } else {
        count.put(point.toString(), 1);
      }
    }
  }

  Line getStraightLine(String line) {
    Point[] endPoints = toEndPoints(line);
    Point start = endPoints[0];
    Point end = endPoints[1];
    if (Line.isStraight(start, end)) {
      return new Line(start, end);
    }
    return null;
  }

  Point[] toEndPoints(String line) {
    String[] points = line.split(" -> ");
    String[] startCoordinates = points[0].split(",");
    String[] endCoordinates = points[1].split(",");
    Point start =
        new Point(Integer.parseInt(startCoordinates[0]), Integer.parseInt(startCoordinates[1]));
    Point end = new Point(Integer.parseInt(endCoordinates[0]), Integer.parseInt(endCoordinates[1]));
    return new Point[] {start, end};
  }

  void part2(List<String> input) {
    System.out.println("part2");

    List<Line> validLines =
        input.stream()
            .map(this::getStraightOrDiagonalLine)
            .filter(line -> line != null)
            .collect(Collectors.toList());

    Map<String, Integer> count = new HashMap<>();
    validLines.stream().forEach(line -> addAllPoints(line, count));

    List<Map.Entry<String, Integer>> dangerousPoints =
        count.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .collect(Collectors.toList());

    System.out.println("Number of dangerous points: " + dangerousPoints.size());
  }

  Line getStraightOrDiagonalLine(String line) {
    Point[] endPoints = toEndPoints(line);
    Point start = endPoints[0];
    Point end = endPoints[1];
    if (Line.isStraight(start, end) || Line.isDiagonal(start, end)) {
      return new Line(start, end);
    }
    return null;
  }

  static class Line {
    enum DIRECTION {
      N,
      NE,
      E,
      SE,
      S,
      SW,
      W,
      NW,
    }

    Point start;
    Point end;
    List<Point> points;
    DIRECTION direction;

    public Line(Point start, Point end) {
      this.start = start;
      this.end = end;
      this.direction = this.getDirection();
      this.points = getIntermittentPoints(start, end);
    }

    DIRECTION getDirection() {
      if (start.x == end.x && start.y < end.y) {
        return DIRECTION.N;
      }

      if (start.x < end.x && start.y < end.y) {
        return DIRECTION.NE;
      }

      if (start.x < end.x && start.y == end.y) {
        return DIRECTION.E;
      }

      if (start.x < end.x && start.y > end.y) {
        return DIRECTION.SE;
      }

      if (start.x == end.x && start.y > end.y) {
        return DIRECTION.S;
      }

      if (start.x > end.x && start.y > end.y) {
        return DIRECTION.SW;
      }

      if (start.x > end.x && start.y == end.y) {
        return DIRECTION.W;
      }

      if (start.x > end.x && start.y < end.y) {
        return DIRECTION.NW;
      }

      throw new RuntimeException("DIRECTION can not be determined, probably not a straight line.");
    }

    List<Point> getIntermittentPoints(Point start, Point end) {
      ArrayList<Point> points = new ArrayList<>();
      Point next = start;
      while (next.notEquals(end)) {
        points.add(next);
        next = getNextPointInDirection(next, direction);
      }
      points.add(end);
      return points;
    }

    private Point getNextPointInDirection(Point current, DIRECTION direction) {
      switch (direction) {
        case N:
          return new Point(current.x, current.y + 1);
        case NE:
          return new Point(current.x + 1, current.y + 1);
        case E:
          return new Point(current.x + 1, current.y);
        case SE:
          return new Point(current.x + 1, current.y - 1);
        case S:
          return new Point(current.x, current.y - 1);
        case SW:
          return new Point(current.x - 1, current.y - 1);
        case W:
          return new Point(current.x - 1, current.y);
        case NW:
          return new Point(current.x - 1, current.y + 1);
        default:
          throw new RuntimeException("DIRECTION is invalid");
      }
    }

    static boolean isStraight(Point start, Point end) {
      return start.x == end.x || start.y == end.y;
    }

    static boolean isDiagonal(Point start, Point end) {
      return Math.abs(start.x - end.x) == Math.abs(start.y - end.y);
    }

    @Override
    public String toString() {
      StringBuilder representation =
          new StringBuilder()
              .append("Start: " + start.x + "," + start.y + "\n")
              .append("Points:\n");
      for (Point point : this.points) {
        representation.append(point.x + "," + point.y + "\n");
      }
      representation.append("End: " + end.x + "," + end.y + "\n");
      return representation.toString();
    }
  }

  static class Point {
    int x;
    int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    boolean notEquals(Point compareWith) {
      return !this.equals(compareWith);
    }

    boolean equals(Point compareWith) {
      return this.x == compareWith.x && this.y == compareWith.y;
    }

    @Override
    public String toString() {
      return this.x + "," + this.y;
    }
  }
}
