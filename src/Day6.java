import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.out;

public class Day6{
    static Map<Integer,List<String>> map = new HashMap<>();

    public static void main(String[] args){
        solve1();
        solve2();
        solve3();
    }

    public static void initializeInput(){
        try {
            File file = new File("puzzles/day6.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // Use regex "\\s+" to match one or more whitespace characters
                // and replace them with a single space.
                String line=scanner.nextLine().trim().replaceAll("\\s+", " ");;
                String[] splitLine = line.split(" ");

                for ( int i=0, splitLineLength=splitLine.length; i<splitLineLength; i++ ) {
                    String str=splitLine[i];
                    if( map.containsKey(i) ) map.get(i).add(str);
                    else map.put(i, new ArrayList<>(Collections.singletonList(str)));
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void solve1(){
//        printMap(map);
        initializeInput();
        long answer= 0L;
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            List<String> value = entry.getValue();
            String operation = value.get(value.size()-1);
            answer+= calculate(value.subList(0, value.size()-1), operation);
        }
        out.println("The answer for part one is :" + answer);
    }

    public static void printMap(Map<Integer, List<String>> map) {
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println("Key: " + key + " -> Values: " + value);
        }
    }

    static long calculate(List<String> values, String operator){
        long answer = 0;
        long tempMultiply = 1;
        for ( String s : values ) {
            String value=s.trim();
            long valueNumber=Long.parseLong(value);
            if( operator.equals("*") ) tempMultiply*=valueNumber;
            if( operator.equals("+") ) answer+=valueNumber;
        }
        if( operator.equals("*") )  answer +=tempMultiply;
        return answer;
    }

    public static List<List<String>> parseInput2() {
        List<List<String>> temp = new ArrayList<>();
        try {
            File file = new File("puzzles/day6.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains("*")) {
                    temp.add(new ArrayList<>(Arrays.asList(line.split("")))); // mutable
                } else {
                    String rep = line.replaceAll("\\s+", " ");
                    String[] splitLine = rep.split(" ");
                    temp.add(new ArrayList<>(Arrays.asList(splitLine))); // mutable
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    static void solve2(){
        List<List<String>> problems = parseInput2();
        pad(problems);

        List<List<String>> master = new ArrayList<>();

        List<String> cephalopodNumbers= new ArrayList<>();
        for ( int i=0, tempSize=problems.get(0).size(); i<tempSize; i++ ) {
            List<String> tempDigits= new ArrayList<>();

            for ( int j=0; j<problems.size()-1; j++ ) {
                tempDigits.add(problems.get(j).get(i));
            }

            if ( tempDigits.stream().allMatch(" "::equals)) {
                master.add(cephalopodNumbers);
                cephalopodNumbers= new ArrayList<>();
            }
            else {
                StringBuilder joined =new StringBuilder();
                for ( String tempDigit: tempDigits ) {
                    joined.append(tempDigit);
                }
                cephalopodNumbers.add(joined.toString());
            }
        }

        long answer = 0L;
        for ( int i=0; i<master.size(); i++ ) {
            List<String> values = master.get(i);
            String operator = problems.get(problems.size()-1).get(i);
            answer += calculate(values, operator);
        }
        out.println("The answer for part two is :" + answer);
    }
    static void solve3() {
        List<List<String>> problems = parseInput2();
        pad(problems);

        // contains a list of problems whose numbers have converted to cephalopod numbers
        List<List<String>> master = new ArrayList<>();

        // temporary number storage of a problem after being converted to cephalopod numbers
        List<String> cephalopodNumbers = new ArrayList<>();

        int rowCount = problems.size();
        int colCount = problems.get(0).size();

        for (int i = 0; i < colCount; i++) {
            // collect the vertical column (excluding last row: operators)
            List<String> column = new ArrayList<>();
            for (int j = 0; j < rowCount - 1; j++) {
                column.add(problems.get(j).get(i));
            }

            // check if the column is all spaces
            boolean allSpaces = column.stream().allMatch(" "::equals);

            if (allSpaces) {
                master.add(cephalopodNumbers);
                cephalopodNumbers = new ArrayList<>();
            } else {
                // join digits into a single string
                cephalopodNumbers.add(String.join("", column));
            }
        }

        long answer = 0L;
        List<String> operators = problems.get(rowCount - 1); // last row contains operators
        for (int i = 0; i < master.size(); i++) {
            answer += calculate(master.get(i), operators.get(i));
        }

        out.println("The answer for part two is: " + answer);
    }


    public static void pad(List<List<String>> problems){
        // Find the maximum row size
        int maxSize=0;
        for ( List<String> row : problems ) maxSize=Math.max(maxSize, row.size());
        // Add 1 to ensure last element is empty string
        maxSize+=1;
        // Pad each row
        for ( int i=0; i<problems.size()-1; i++ )
            while(problems.get(i).size()<maxSize) problems.get(i).add(" "); // add empty space until it reaches max size
    }

}