import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Day14_2020 {
    private static int[] mask = new int[36];
    private static HashMap<Integer, Long> memory = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day214_nele.txt"));

        for (String line : inputLines) {
            if (line.indexOf("mask") == 0) {
                setMask(line.split("=")[1].trim());
            } else {
                String[] command = line.split("=");
                int end = command[0].indexOf(']');
                putValue(Integer.parseInt(command[0].substring(4,end)), Long.parseLong(command[1].trim()));
            }
        }

        System.out.println("Part 1: " + getTotalValue());

    }

    public static void setMask(String s) {
        //System.out.println("SetMask: " + s);
        char[] chars = s.toCharArray();
        for (int i = 0; i < 36; i++) {
            String c = Character.toString(chars[i]);
            mask[i] = c.equalsIgnoreCase("x") ? -1 : Integer.parseInt(c);
        }
    }

    public static void putValue(int pos, long value) {
        //System.out.println("putValue(" + pos + ", " + value + ").");
        StringBuilder sbValue = new StringBuilder(intToBinStr(value));
        for (int i = 0; i < 36; i++) {
            if (mask[i] == 0) {
                sbValue.setCharAt(i, '0');
            } else if (mask[i] == 1) {
                sbValue.setCharAt(i, '1');
            }
        }
        //System.out.println(sbValue.toString() + ", " + Long.parseLong(sbValue.toString(), 2) + " at pos " + pos);
        memory.put(pos, Long.parseLong(sbValue.toString(), 2));
    }

    public static String intToBinStr(long number) {
        String binary = Long.toBinaryString(number);
        return String.format("%36s", binary).replace(' ', '0');
    }

    public static String maskString() {
        StringBuilder sb = new StringBuilder();
        for (int m : mask) {
            if (m == -1) sb.append('X'); else sb.append(m);
        }
        return sb.toString();
    }

    public static long getTotalValue() {
        long sum = 0;
        for (long v : memory.values()) {
            sum += v;
        }
        return sum;
    }
}
