import exception.InvalidOperatorException;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18_2020 {
    private static final String plusOp = "+";
    private static final String mulOp = "*";

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day218.txt"));

        BigInteger result = BigInteger.ZERO;
        BigInteger resultPart2 = BigInteger.ZERO;
        for (String line : inputLines) {
            result = result.add(evaluateLine(line, false));
            resultPart2 = resultPart2.add(evaluateLine(line, true));
        }

        System.out.println("Part 1: " + result);
        System.out.println("Part 2: " + resultPart2);
    }

    public static BigInteger evaluateLine(String line, boolean advanced) {
        int depth = 0;
        int start = -1;
        int end = -1;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                if (depth == 0) {
                    start = i;
                    depth++;
                } else {
                    depth++;
                }
            } else if (line.charAt(i) == ')') {
                depth--;
                if (depth == 0) {
                    end = i;
                    BigInteger replacingBI = evaluateLine(line.substring(start + 1, end).trim(), advanced);
                    String newLine = line.substring(0, start) + replacingBI + line.substring(end + 1);
                    return evaluateLine(newLine, advanced);
                }
            }
        }

        return (advanced) ? evaluateWithoutPAdv(line) : evaluateWithoutP(line);
    }

    public static int parseNumber(String s) {
        return (s.indexOf(")") > 0) ? Integer.parseInt(s.substring(0,s.indexOf(")"))) : Integer.parseInt(s);
    }

    public static BigInteger executeOp(BigInteger bi, String op, int nb) throws InvalidOperatorException {
        switch(op) {
            case plusOp -> { return bi.add(BigInteger.valueOf(nb)); }
            case mulOp -> { return bi.multiply(BigInteger.valueOf(nb)); }
        }
        throw new InvalidOperatorException(op);
    }

    public static BigInteger evaluateWithoutP(String exp) {
        String[] parts = exp.split(" ");
        BigInteger result = BigInteger.valueOf(Integer.parseInt(parts[0]));
        int i = 1;
        while (i < parts.length) {
            result = executeOp(result, parts[i], Integer.parseInt(parts[i+1]));
            i += 2;
        }
        return result;
    }

    public static BigInteger evaluateWithoutPAdv(String exp) {
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(exp.split(" ")));

        // first execute additions
        int i = 1;
        while (i < parts.size()) {
            String nextOp = parts.get(i);
            if (nextOp.equalsIgnoreCase(plusOp)) {
                BigInteger sum = new BigInteger(parts.get(i-1));
                sum = sum.add(new BigInteger(parts.get(i+1)));
                //result = first ? sum : result.add(sum);
                parts.set(i-1, sum.toString());
                parts.remove(i); // remove operator
                parts.remove(i); // remove next number
            } else {
                i += 2;
            }
        }

        // next the multiplications
        i = 1;
        while (i < parts.size()) {
            String nextOp = parts.get(i);
            if (nextOp.equalsIgnoreCase(mulOp)) {
                BigInteger prod = new BigInteger(parts.get(i-1));
                prod = prod.multiply(new BigInteger(parts.get(i+1)));
                //result = first ? sum : result.add(sum);
                parts.set(i-1, prod.toString());
                parts.remove(i); // remove operator
                parts.remove(i); // remove next number
            } else {
                i += 2;
            }
        }

        return new BigInteger(parts.get(0));
    }
}
