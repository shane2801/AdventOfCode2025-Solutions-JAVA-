import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.out;

public class Day2 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1(){
        // invalid id if digit repeats itself
        // starts with 0 and contains only 1 or 0 -> invalid

        List<String> ranges = parseInput();
        out.println(ranges);
        Set<Long> result= new HashSet<>();
        for (String range: ranges){
            List<Long> numbers = listNumbers(range);
            for (long n: numbers){
                // check double
                if (isRepeatedTwice(n)) result.add(n);

            }

        }
        out.println(result);
        Long answer = 0L;
        for (long n: result){
            answer += n;
        }
        out.println(answer);
    }

    public static void part2(){
        // invalid id if digit repeats itself
        // starts with 0 and contains only 1 or 0 -> invalid

        List<String> ranges = parseInput();
        out.println(ranges);
        Set<Long> result= new HashSet<>();
        for (String range: ranges){
            List<Long> numbers = listNumbers(range);
            for (long n: numbers){

                // check double
                if (isRepeatedNumber(n)) result.add(n);

            }

        }
        out.println(result);
        Long answer = 0L;
        for (long n: result){
            answer += n;
        }
        out.println(answer);
    }



    // Check if number is made of repeated substrings
    public static boolean isRepeatedNumber(long num) {
        String s = String.valueOf(num);
        String doubled = s + s;
        String trimmed = doubled.substring(1, doubled.length() - 1);
        return trimmed.contains(s);
    }

    public static boolean isRepeatedTwice(long id) {
        String s = String.valueOf(id);
        // must be even length
        if (s.length() % 2 != 0) return false;

        int mid = s.length() / 2;
        String first = s.substring(0, mid);
        String second = s.substring(mid);

        return first.equals(second);
    }
    public static List<Long> listNumbers(String range){
        List<Long> numbers = new ArrayList<>();
        String[] startEnd = range.split("-");
        long start = Long.parseLong(startEnd[0]);
        long end = Long.parseLong(startEnd[1]);

//        out.prlong("start: " + start + " end: " + end);
        for (long i=start;i<=end;i++){
            numbers.add(i);
        }
//        out.prlongln(numbers);
        return numbers;
    }

    public static List<String> parseInput(){
        List<String> ranges = new ArrayList<>();
        try {
            File file = new File("puzzles/day2.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                String[] tempRanges = line.split(",");
                ranges.addAll(Arrays.asList(tempRanges));
            }
        } catch (FileNotFoundException e) {

        }
        return ranges;
    }

}
