import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Day2 {

    static List<String> ranges = new ArrayList<>();

    public static void main(String[] args) {
        initializeInput();
        solve();
    }

    public static void initializeInput() {
        try {
            Stream<String> lineStream = Files.lines(Paths.get("puzzles/day2.txt"));
            lineStream.flatMap(line -> Stream.of(line.split(","))) // Split each line into ranges and flatten
                    .forEach(ranges::add); // Add each resulting range to the list
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void solve() {
        // A single stream to process ALL ranges and ALL numbers.
        long[] results = ranges.stream()
                // 1. Process ranges into (start, end) pairs
                .map(range -> {
                    String[] parts = range.split("-");
                    return new long[] {Long.parseLong(parts[0]), Long.parseLong(parts[1])};
                })
                // 2. FlatMap: Convert the stream of range-pairs into a stream of ALL numbers
                .flatMapToLong(startEnd ->
                                       LongStream.rangeClosed(startEnd[0], startEnd[1]))
                // 3. Collect and sum in a single pass
                .collect(
                        () -> new long[2], // Supplier: Create a two-element array [sum1, sum2]
                        (sums, number) -> { // Accumulator: Update sums for each number
                            if (Day2.isRepeatedTwice(number)) {
                                sums[0] += number;
                            }
                            if (Day2.isRepeatedNumber(number)) {
                                sums[1] += number;
                            }
                        },
                        (sums1, sums2) -> { // Combiner (for parallel streams): Merge two partial sum arrays
                            sums1[0] += sums2[0];
                            sums1[1] += sums2[1];
                        }
                );

        out.println("The answer for part 1 is: " + results[0]);
        out.println("The answer for part 2 is: " + results[1]);
    }

    // checks if there is a number within the number that is repeated exactly twice
    // e.g. 11: 1 is repeated twice, 1212: 12 is repeated twice, 123123: 123 is repeated twice
    // logic: a number that matches this condition must be even, so we split it at the middle and compare
    // the two ends, start and end.
    // e.g. 11 start 1 end 1 -> true
    // 1212 start 12 end 12 -> true
    // 123123 start 123 end 123 -> true
    public static boolean isRepeatedTwice(long num) {
        String s = String.valueOf(num);
        if (s.length() % 2 != 0) return false;

        int mid = s.length() / 2;
        String first = s.substring(0, mid);
        String second = s.substring(mid);

        return first.equals(second);
    }

    // Check if number is made of repeated substring numbers
    // e.g 151515 is made up of 15 x3 times
    // The logic relies on a fundamental property of strings that are repetitions of a smaller substring:
    // A string s is a repetition of a substring if and only if s is found within the string created
    // by concatenating s with itself, and then removing the first and last characters.
    // E.g: If S = 151515, the repeating substring is 15.
    // doubled 151515151515
    // trimmed 5151515151
    // 15 must be present in the trimmed string for condition to be true
    // if number size is one, eg. 1 trimmed is "" and method returns false
    public static boolean isRepeatedNumber(long num) {
        String s = String.valueOf(num);
        String doubled = s + s;
        String trimmed = doubled.substring(1, doubled.length() - 1);
        return trimmed.contains(s);
    }
}
