import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.out;

public class Day3 {
    static List<String> batteries = new ArrayList<>();
    public static void main(String[] args) {
        initializeInput();
        solve1();



    }
    public static void initializeInput(){
        try {
            File file = new File("puzzles/day3.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                batteries.add(line);
            }
        } catch (FileNotFoundException e) {

        }
    }
    public static void solve1(){
        long sum=0L;
        for (String battery: batteries){
            int result = maxJoltage(battery);
            out.println(result);
            sum += result;
        }
        out.println(sum);
    }

    public static int maxJoltage(String s) {
        int n = s.length();
        int[] suffixMax = new int[n];

        // Build suffixMax: largest digit from position i to end
        suffixMax[n - 1] = s.charAt(n - 1) - '0';
        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(s.charAt(i) - '0', suffixMax[i + 1]);
        }

        int best = 0;

        // Try each digit as the tens place
        for (int i = 0; i < n - 1; i++) {
            int tens = s.charAt(i) - '0';
            int ones = suffixMax[i + 1];  // best digit after i
            int value = tens * 10 + ones;
            best = Math.max(best, value);
        }

        return best;
    }


}
