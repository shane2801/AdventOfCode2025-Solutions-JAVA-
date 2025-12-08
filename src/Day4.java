import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.*;

public class Day4{
//    static char [][] grid = new char[10][10];
    static char [][] grid = new char[139][139];
    static Deque<int[]> found = new ArrayDeque<>();

    public static void main(String[] args){
        initializeGrid();
        solve();
    }
    
    public static void initializeGrid(){
        try {
            File file = new File("puzzles/day4.txt");
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                for ( int j=0; j<line.length(); j++ ) {
                    grid[i][j] = line.charAt(j);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getAccessibleRollsCount(){
        int count = 0;
        for ( int i=0; i<grid.length; i++ ) {
            for ( int j=0; j<grid[0].length; j++ ) {
                char current = grid[i][j];
                if( current == '@' ){
                    if( checkNeighbour(i,j)) {
                        count+=1;
                        found.push(new int[]{i,j});
                    }
                }
            }
        }
//        out.println(count);
        return count;
    }

    public static boolean checkNeighbour(int i, int j){
        int[] current = new int[2];
        int[][] neighbours= {
                {0,-1}, // left
                {0,1},  // right
                {-1,0}, // top
                {1,0},  // bottom
                {-1,1}, // top right
                {-1,-1}, // top left
                {1,1},  // bottom right
                {1,-1}  // bottom left
        };
        int neighbourCount = 0;
        for ( int[] neighbour: neighbours) {
             current[0] = i + neighbour[0];
             current[1] = j + neighbour[1];
             if( isInBound(current) ){
                 if( grid[current[0]][current[1]] == '@' ) neighbourCount +=1;
             }
        }
        return neighbourCount<4;
    }

    public static boolean isInBound(int[] point){
        return (point[0]<=grid.length-1&&point[0]>=0)&&(point[1]<=grid[0].length-1&&point[1]>=0);
    }

    public static void solve(){
        int total = 0;
        while( true ){
            int accessed = getAccessibleRollsCount();
            if( accessed ==0 ) break;
            if( total==0 )out.println("The answer for part one is :" + accessed);
            total+=accessed;
            updateGrid();
        }
        out.println("The answer for part two is :" +total);
    }

    public static void updateGrid(){
        while(!found.isEmpty()){
            int[] coordinate = found.pop();
            grid[coordinate[0]][coordinate[1]] = 'x';
        }
    }
}

