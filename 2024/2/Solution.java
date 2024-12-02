import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    final var input = readInput("./2024/2/input.txt");
    if (input.isPresent()) {
      final var solution1 = part1(input.get());
      System.out.printf("Solution to part 1: %d\n", solution1);

      final var solution2 = part2(input.get());
      System.out.printf("Solution to part 2: %d\n", solution2);
    }
  }

  public static int part1(List<List<Integer>> input) {
    return (int) IntStream.range(0, input.size()).filter(i -> isSafe.test(input.get(i))).count();
  }

  public static int part2(List<List<Integer>> input) {
    return (int) IntStream.range(0, input.size()).filter(i -> problemDampener.test(input.get(i)))
        .count();
  }

  static Predicate<List<Integer>> isAscending = list -> IntStream.range(0, list.size() - 1)
      .allMatch(i -> list.get(i + 1) > list.get(i) && list.get(i + 1) - list.get(i) <= 3);

  static Predicate<List<Integer>> isDescending = list -> IntStream.range(0, list.size() - 1)
      .allMatch(i -> list.get(i) > list.get(i + 1) && list.get(i) - list.get(i + 1) <= 3);

  // test if list satisfies ascending or descending conditions
  static Predicate<List<Integer>> isSafe =
      list -> isAscending.test(list) || isDescending.test(list);

  // test if list satisfies problem dampener conditions
  static Predicate<List<Integer>> problemDampener = list -> {
    return IntStream
        // iterate through every element in list
        .range(0, list.size())
        // iterate through each element i
        .anyMatch(i -> {
          // create duplicate of list
          final var duplicate = new ArrayList<Integer>(list);
          // remove element from duplicate
          duplicate.remove(i);
          // test duplicate as normal
          return isSafe.test(duplicate);
        });
  };

  public static Optional<List<List<Integer>>> readInput(String path) {
    try {
      return Optional.of(Files
          // split each line into a series of Strings
          .lines(Paths.get(path)).map(line -> Arrays.stream(line.split("\\s+"))
              // parse Integers from Strings and collect into List
              .map(Integer::parseInt).collect(Collectors.toList()))
          // collect these Lists into another List
          .collect(Collectors.toList()));
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
