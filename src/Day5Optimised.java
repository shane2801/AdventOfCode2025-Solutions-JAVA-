import java.io.*;
import java.util.*;

public class Day5Optimised {

    record Range(long start, long end) {}

    static List<Range> ranges = new ArrayList<>();
    static List<Long> ingredientIds = new ArrayList<>();

    public static void main(String[] args) {
        initializeInput();
        solve1();
        solve2();
    }

    static void initializeInput() {
        try (Scanner sc = new Scanner(new File("puzzles/day5.txt"))) {
            boolean upperPart = true;

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) { upperPart = false; continue; }

                if (upperPart) {
                    String[] t = line.split("-");
                    ranges.add(new Range(Long.parseLong(t[0]), Long.parseLong(t[1])));
                } else ingredientIds.add(Long.parseLong(line));
            }

        } catch (Exception ignored) {}
    }

    // ---------- Part 1 ----------
    static void solve1() {
        long fresh = ingredientIds.stream()
                .filter(id -> isInRanges(id, ranges))
                .count();

        System.out.println("Part 1 = " + fresh);
    }

    static boolean isInRanges(long id, List<Range> ranges) {
        for (Range r : ranges) {
            if (id >= r.start && id <= r.end)
                return true;
        }
        return false;
    }

    // ---------- Part 2 ----------
    static void solve2() {
        long total = mergedRanges(ranges).stream()
                .mapToLong(r -> (r.end - r.start + 1))
                .sum();

        System.out.println("Part 2 = " + total);
    }

    // Merge ranges in optimal O(n log n)
    static List<Range> mergedRanges(List<Range> list) {
        if (list.isEmpty()) return List.of();

        List<Range> sorted = new ArrayList<>(list);
        sorted.sort(Comparator.comparingLong(r -> r.start));

        List<Range> merged = new ArrayList<>();
        Range current = sorted.getFirst();

        for (int i = 1; i < sorted.size(); i++) {
            Range next = sorted.get(i);

            if (next.start <= current.end + 1) {
                // extend current
                current = new Range(current.start, Math.max(current.end, next.end));
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);

        return merged;
    }
}
