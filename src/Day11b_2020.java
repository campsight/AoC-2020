import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Day11b_2020 {
    private static final String EMPTY_SEAT = "L";
    private static final String OCCUPIED_SEAT = "#";
    private static final String FLOOR = ".";
    private static final int[] adjFields = {-1, 0, 1};

    private static int width = 0;
    private static int height = 0;

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day211.txt"));

        width = inputLines.get(0).length();
        height = inputLines.size();

        ArrayList<String> map = new ArrayList<>(inputLines);
        System.out.println(map);
        ArrayList<String> nextMap = processMap(map);
        System.out.println(nextMap);

        boolean done = matchingMaps(map, nextMap);
        while (!done) {
            ArrayList<String> newMap = processMap(nextMap);
            done = matchingMaps(newMap, nextMap);
            nextMap = newMap;
            System.out.println(nextMap);
        }
        System.out.println("Part 2: " + countOccupiedSeats(nextMap));
    }

    public static int countOccupiedSeats(ArrayList<String> map) {
        int count = 0;
        for (String line : map) {
            count += countMatches(line, OCCUPIED_SEAT);
        }
        return count;
    }

    public static boolean matchingMaps(ArrayList<String> map1, ArrayList<String> map2) {
        for (int i = 0; i < height; i++) {
            if (!map1.get(i).equalsIgnoreCase(map2.get(i))) return false;
        }
        return true;
    }

    public static ArrayList<String> processMap(ArrayList<String> map) {
        ArrayList<String> result = new ArrayList<>(map);
        ArrayList<String> adjSeats;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String s = map.get(i).substring(j, j+1);
                switch (s) {
                    case EMPTY_SEAT -> {
                        adjSeats = getAdjacentSeats2(map, j, i);
                        if (!adjSeats.contains(OCCUPIED_SEAT)) {
                            StringBuilder sb = new StringBuilder(result.get(i));
                            sb.setCharAt(j, OCCUPIED_SEAT.charAt(0));
                            result.set(i, sb.toString());
                        }
                    }
                    case OCCUPIED_SEAT -> {
                        adjSeats = getAdjacentSeats2(map, j, i);
                        long nb_occ = adjSeats.stream().filter(x -> x.equals(OCCUPIED_SEAT)).count();
                        if (nb_occ >= 5) { // 4 for part 1, 5 for part 2
                            StringBuilder sb = new StringBuilder(result.get(i));
                            sb.setCharAt(j, EMPTY_SEAT.charAt(0));
                            result.set(i, sb.toString());
                        }
                    }
                }
            }
        }
        return result;
    }

    public static int countMatches(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){

            lastIndex = str.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    public static ArrayList<String> getAdjacentSeats(ArrayList<String> map, int i, int j) {
        ArrayList<String> result = new ArrayList<>();
        for (int k : adjFields) {
            for (int l : adjFields) {
                int u = i+k;
                int v = j+l;
                if ((inBoundaries(u,width)) && inBoundaries(v,height) && (!((u == i) && (v==j)))) {
                    result.add(map.get(v).substring(u, u+1));
                }
            }
        }
        return result;
    }

    public static ArrayList<String> getAdjacentSeats2(ArrayList<String> map, int i, int j) {
        ArrayList<String> result = new ArrayList<>();
        for (int k : adjFields) {
            for (int l : adjFields) {
                if (!((k==0) && (l == 0))) {
                    result.add(getAdjacentSeatInDirection(map, i, j, k, l));
                }
            }
        }
        return result;
    }

    public static String getAdjacentSeatInDirection(ArrayList<String> map, int i, int j, int direction_i, int direction_j) {
        int u, v;
        u = i + direction_i;
        v = j + direction_j;
        if (inBoundaries(u, width) && inBoundaries(v,height)) {
            String nextSeat = map.get(v).substring(u, u+1);
            return nextSeat.equalsIgnoreCase(FLOOR) ? getAdjacentSeatInDirection(map, u, v, direction_i, direction_j) : nextSeat;
        } else {
            return FLOOR;
        }
    }

    public static boolean inBoundaries(int i, int m) {
        return ((i >= 0) && (i < m));
    }
}
