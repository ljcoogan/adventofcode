import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    final var input = readInput("./2024/1/input.txt");
    if (input.isPresent()) {
      final var solution1 = part1(input.get());
      System.out.printf("Solution to part 1: %d\n", solution1);
      final var solution2 = part2(input.get());
      System.out.printf("Solution to part 2: %d\n", solution2);
    }
  }

  public static int part1(List<List<Integer>> input) {
    return IntStream
        // iterate from 0 to size of each list
        .range(0, input.get(0).size())
        // for each pair of two IDs, calculate absolute difference
        .map(i -> Math.abs(input.get(0).get(i) - input.get(1).get(i)))
        // sum these differences
        .sum();
  }

  public static int part2(List<List<Integer>> input) {
    return input
        // iterate through each ID in the left list
        .get(0).stream()
        // for each ID in the left list, count occurences in right list and multiply by said ID
        .mapToInt(i -> (int) input.get(1).stream().filter(j -> j.equals(i)).count() * i)
        // sum these occurences
        .sum();
  }

  public static Optional<List<List<Integer>>> readInput(String path) {
    try {
      final List<List<Integer>> pairs = Files.lines(Paths.get(path))
          // split every line into Strings by removing all spaces
          .map(line -> line.split("\\s+"))
          // parse Integers from the Strings, and put each pair into an immutable List
          .map(part -> List.of(Integer.parseInt(part[0]), Integer.parseInt(part[1])))
          // collect these Lists into a single List
          .collect(Collectors.toList());

      // form sorted List of location IDs on the left
      final List<Integer> left =
          pairs.stream().map(pair -> pair.get(0)).sorted().collect(Collectors.toList());

      // form sorted List of location IDs on the right
      final List<Integer> right =
          pairs.stream().map(pair -> pair.get(1)).sorted().collect(Collectors.toList());

      // return both left and right lists
      return Optional.of(List.of(left, right));
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
