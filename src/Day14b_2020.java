import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day14b_2020 {
    private static int[] mask = new int[36];
    private static HashMap<Long, Long> memory = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day214.txt"));

        for (String line : inputLines) {
            if (line.indexOf("mask") == 0) {
                setMask(line.split("=")[1].trim());
            } else {
                String[] command = line.split("=");
                int end = command[0].indexOf(']');
                putValue(Long.parseLong(command[0].substring(4,end)), Long.parseLong(command[1].trim()));
            }
        }
        System.out.println("Part 2: " + getTotalValue());
    }

    public static void setMask(String s) {
        //System.out.println("SetMask: " + s);
        char[] chars = s.toCharArray();
        for (int i = 0; i < 36; i++) {
            String c = Character.toString(chars[i]);
            mask[i] = c.equalsIgnoreCase("x") ? -1 : Integer.parseInt(c);
        }
    }

    public static void putValue(long pos, long value) {
        //System.out.println("putValue(" + pos + ", " + value + ").");
        StringBuilder sbValue = new StringBuilder(intToBinStr(pos));
        for (int i = 0; i < 36; i++) {
            if (mask[i] == -1) {
                sbValue.setCharAt(i, 'X');
            } else if (mask[i] == 1) {
                sbValue.setCharAt(i, '1');
            }
        }
        //System.out.println("memories: " + sbValue.toString());
        long[] memPos = getMemoryValues(sbValue);
        for (long m : memPos) {
            memory.put(m, value);
        }
    }

    public static long[] getMemoryValues(StringBuilder sb) {
        int xpos = sb.indexOf("X");
        int fromIndex;
        ArrayList<Integer> indexes = new ArrayList<>();
        while (xpos >= 0) {
            indexes.add(xpos);
            fromIndex = xpos+1;
            xpos = sb.indexOf("X", fromIndex);
        }
        //System.out.println(indexes);
        int nbDifferences = (int) Math.pow(2, indexes.size());
        long[] result = new long[nbDifferences];
        //System.out.println("values: " + nbDifferences);
        for (int l = 0; l < nbDifferences; l++) {
            String fstr = "%" + indexes.size() + "s";
            StringBuilder sbMem = new StringBuilder(String.format(fstr, Long.toBinaryString(l)).replace(' ', '0'));
            //out.println(sbMem.toString());
            StringBuilder newMemValue = new StringBuilder(sb.toString());
            for (int i = 0; i < indexes.size(); i++) {
                newMemValue.setCharAt(indexes.get(i), sbMem.charAt(i));
            }
            //System.out.println(newMemValue + ", " + Long.parseLong(newMemValue.toString(), 2));
            result[l] = Long.parseLong(newMemValue.toString(), 2);
        }
        return result;
    }

    public static String intToBinStr(long number) {
        String binary = Long.toBinaryString(number);
        return String.format("%36s", binary).replace(' ', '0');
    }

    public static long getTotalValue() {
        long sum = 0;
        for (long v : memory.values()) {
            sum += v;
        }
        return sum;
    }
}
