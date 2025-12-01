import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.out;

public class Day1{

    static List<String> input = new ArrayList<>();

    public static void main(String[] args){
        // initialize input
        parseInput();

        part1();
        part2();
    }

    public static void parseInput(){
        try {
            File file = new File("puzzles/day1.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void part1(){
        int current = 50;
        int count =0;
        for ( String instruction: input) {
            int next = rotateDial(instruction, current);
            // Count how many times the dial hits 0
            if( next == 0 ) count +=1;
            current = next;
        }

        out.println("Answer for part 1 is : " + count);
    }

    public static int rotateDial(String instruction, int current){
        char direction = instruction.charAt(0);
        int value = Integer.parseInt(instruction.substring(1));

        int next;
        if( direction == 'R' ){
            // start = 50
            // r48 -> 50+48= 98 mod 100 = end 98
            // r96 -> 50+96 = 146 mod 100 = end 46
            next = (current + value) %100;
        }
        else{ // if direction == L
            // add 100 if number is -ve, does not affect +ve as we take only the remainder
            // start = 50
            // l68 -> 50-68=-18+100 = end 82
            // l42 -> 50-42=8+100 = end 8
            next = (current - value + 100) %100;
        }
        return next;
    }

    public static void part2(){
        int current = 50;
        int count = 0;

        for (String instruction : input) {
            char direction = instruction.charAt(0);
            int value = Integer.parseInt(instruction.substring(1));

            int[] temp = simulateEachClick(current, direction, value);
            // number of times passed through or ended on 0
            count+=temp[0];
            // Update current position
            current = temp[1];
        }

        System.out.println("The answer for part 2 is : " + count); // This now counts every click hitting 0
    }

    // simulate every click
    public static int[] simulateEachClick(int start, char direction, int steps) {
        int zeroHits = 0;
        int current = start;

        for (int i = 0; i < steps; i++) {
            if (direction == 'R') current=(current+1) % 100;
            else current=(current-1) % 100; // direction == 'L'
            if (current == 0) zeroHits++;
        }
        return new int[]{zeroHits, current};
    }

}
