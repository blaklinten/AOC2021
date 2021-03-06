package aoc.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionTest {
  public static List<String> testInput = new ArrayList<>();
  Solution solution = new Solution();
  int[] count;
  boolean oxygen = true;
  boolean co2 = false;

  @BeforeEach
  public void init() {
    testInput.clear();
    testInput.add("11001");
    testInput.add("10001");
    testInput.add("00011");
    testInput.add("01111");
  }

  @Test
  public void filterTest() {
    List<char[]> res = solution.filter(solution.toBinary(testInput), '1', 1);
    assertEquals(res.size(), 2);
    assertEquals(res.get(0), testInput.get(0));
    assertEquals(res.get(1), testInput.get(1));
  }

  @Test
  public void getRatingTest_oxygen() {
    char[] oxygenResult = solution.getRating(solution.toBinary(testInput), oxygen);
    assertEquals("11001", oxygenResult);
  }

  @Test
  public void getRatingTest_co2() {
    char[] co2Result = solution.getRating(solution.toBinary(testInput), co2);
    assertEquals("00011", co2Result);
  }
}
