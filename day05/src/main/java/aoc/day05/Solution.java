package aoc.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
    List<Line> strightLines = input.stream().map(this::getStraightLine).filter(line -> line != null).collect(Collectors.toList());
    Map<String, Integer> count = new HashMap<>();
    strightLines.stream().forEach(line -> addAllPoints(line, count));
    List<Map.Entry<String, Integer>> dangerousPoints = count.entrySet().stream().filter(entry -> entry.getValue() > 1).collect(Collectors.toList());
    System.out.println("Number of dangerous points: " + dangerousPoints.size());
  }


  void addAllPoints(Line line, Map<String, Integer> count){
      for(Point point : line.points){
          if(count.containsKey(point.toString())){
              Integer newCount = count.get(point.toString()) + 1;
              count.put(point.toString(), newCount);
          } else {
              count.put(point.toString(), 1);
          }
      }
  }

  Line getStraightLine(String line){
      String[] points = line.split(" -> ");
      String[] startCoordinates = points[0].split(",");
      String[] endCoordinates = points[1].split(",");
      Point start = new Point(Integer.parseInt(startCoordinates[0]), Integer.parseInt(startCoordinates[1]));
      Point end = new Point(Integer.parseInt(endCoordinates[0]), Integer.parseInt(endCoordinates[1]));
      if (Line.isStraight(start, end)){
          return new Line(start, end);
      }
      return null;
  }

  void part2(List<String> input) {
    System.out.println("part2");
  }

  static class Line {
      enum DIRECTION {
          Xpos,
          Xneg,
          Ypos,
          Yneg,
      }

      Point start;
      Point end;
      List<Point> points;
      DIRECTION direction;


      public Line (Point start, Point end){
          this.start = start;
          this.end = end;
          this.direction = this.getDirection();
          this.points = getIntermittentPoints(start, end);
      }

	  DIRECTION getDirection(){
          if (start.x == end.x && start.y > end.y){
              return DIRECTION.Yneg;
          }

          if (start.x == end.x && start.y < end.y){
              return DIRECTION.Ypos;
          }

          if (start.y == end.y && start.x < end.x){
              return DIRECTION.Xpos;
          }

          if (start.y == end.y && start.x > end.x){
              return DIRECTION.Xneg;
          }

          throw new RuntimeException("DIRECTION can not be determined, probably not a straight line.");
      }

      List<Point> getIntermittentPoints(Point start, Point end){
          ArrayList<Point> points = new ArrayList<>();
          Point next = start;
          while (next.notEquals(end)){
              points.add(next);
              next = getNextPointInDirection(next, direction);
          }
          points.add(end);
          return points;
      }

      private Point getNextPointInDirection(Point current, DIRECTION direction) {
          switch(direction) {
              case Xpos: return new Point(current.x + 1, current.y);
              case Xneg: return new Point(current.x - 1, current.y);
              case Ypos: return new Point(current.x, current.y + 1);
              case Yneg: return new Point(current.x, current.y - 1);
              default: throw new RuntimeException("DIRECTION is invalid");
          }
	}

    static boolean isStraight(Point start, Point end){
        return start.x == end.x || start.y == end.y;
    }

    @Override
    public String toString(){
        StringBuilder representation =  new StringBuilder()
            .append("Start: " + start.x +","+ start.y + "\n")
            .append("Points:\n");
        for (Point point : this.points){
            representation.append(point.x + "," + point.y + "\n");
        }
            representation.append("End: " + end.x +","+ end.y + "\n");
        return representation.toString();
    }
  }

  static class Point {
      int x;
      int y;

      Point( int x, int y){
          this.x = x;
          this.y = y;
      }

      boolean notEquals (Point compareWith){
          return !this.equals(compareWith);
      }

      boolean equals(Point compareWith){
          return this.x == compareWith.x && this.y == compareWith.y;
      }

      @Override
      public String toString(){
          return this.x + "," + this.y;
      }
  }
}
