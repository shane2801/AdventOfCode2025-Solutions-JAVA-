import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.*;

public class Day5 {

    static List<String> ranges = new ArrayList<>();
    static List<String> ingredientIds = new ArrayList<>();

    static Set<String> setRangesCopy = new HashSet<>();

    public static void main(String[] args) {
        initializeInput();
        solve1();
        solve2();
    }

    public static void initializeInput(){
        boolean part = true;
        try {
            File file = new File("puzzles/day5.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                if (line.isEmpty()) {
                    part = false;
                    continue;
                }
                if (part) ranges.add(line);
                else ingredientIds.add(line);
            }
        } catch (FileNotFoundException _) {

        }
    }

    public static void  solve1(){
        Set<String> fresh = new HashSet<>();
        for (String ingredientId : ingredientIds){
            if (!fresh.contains(ingredientId)) {
                if (isInRange(Long.parseLong(ingredientId))) fresh.add(ingredientId);
            }
        }
        out.printf("The answer for part one is :%d%n", fresh.size());
    }
    public static boolean isInRange(Long ingredientID){
        for (String range:ranges){
            String[] startEnd = range.split("-");
            long start = Long.parseLong(startEnd[0]);
            long end = Long.parseLong(startEnd[1]);
            if (ingredientID >= start && ingredientID <=end ) return true;
        }
        return false;
    }
    public static void solve2(){
        long total = 0L;
        Deque<String> temp = new ArrayDeque<>();
        for (String range: ranges){
            temp.push(range);
            setRangesCopy.add(range);
        }

        while(!temp.isEmpty()){
            String currentRange = temp.pop();
            for (String originalRange : setRangesCopy){
                if (currentRange.equals(originalRange)) continue;
                String[] mergedRange = mergeRanges(originalRange, currentRange);
                if (mergedRange[0].equals("T")) {
                    temp.push(mergedRange[1]);
                    updateCopyT(originalRange, mergedRange[1]);
                    break;
                }
            }
        }

        for (String range: setRangesCopy){
            String[] startEnd = range.split("-");
            String start = startEnd[0];
            String end = startEnd[1];
            total += ((Long.parseLong(end)-Long.parseLong(start))+1);
        }

        out.printf("The answer for part two is :%d%n", total);
    }


    public static String[] mergeRanges(String original, String toCompare) {

        String[] original_temp = original.split("-");
        long start_O = Long.parseLong(original_temp[0]);
        long end_O = Long.parseLong(original_temp[1]);

        String[] toCompare_temp = toCompare.split("-");
        long start_C = Long.parseLong(toCompare_temp[0]);
        long end_C = Long.parseLong(toCompare_temp[1]);

        String newRange = "";
        String changed = "F";

        // CASE 5 — C is completely to the right of O (no overlap)
        if (start_C > end_O) {
            return new String[]{changed, newRange};
        }

        // CASE 6 — C is completely to the left of O (no overlap)
        if (end_C < start_O) {
            return new String[]{changed, newRange};
        }

        // ---------- Overlapping cases (1–4) ----------

        changed = "T"; // all remaining cases include overlap

        long mergedStart = Math.min(start_O, start_C);
        long mergedEnd   = Math.max(end_O, end_C);

        newRange = "%d-%d".formatted(mergedStart, mergedEnd);

        return new String[]{changed, newRange};
    }

    public static void updateCopyT(String key, String replacement){
        setRangesCopy.remove(key);
        setRangesCopy.add(replacement);
    }


}
