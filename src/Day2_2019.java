import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2_2019 {
    private static String COMMA_DELIMITER = ",";

    private static List<BigInteger> getRecordFromLine(String line) {
        List<BigInteger> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add((BigInteger.valueOf(rowScanner.nextInt())));
            }
        }
        return values;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<BigInteger> program;
        try (Scanner scanner = new Scanner(new File("resources/input_day2.txt"))) {
            // only first line gives input
            program = (getRecordFromLine(scanner.nextLine()));
        }
        System.out.println(program);

        // part 1
        System.out.println(program.get(12));
        System.out.println(program.get(2));
        IntCodeCompiler myCompiler = new IntCodeCompiler(new ArrayList<>(program));
        while (myCompiler.executeNextInstruction(true) == IntCodeCompiler.RETURNCODE_OK);
        System.out.println(myCompiler.getProgram());

        outerloop:
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                program.set(1, BigInteger.valueOf(i));
                program.set(2, BigInteger.valueOf(j));
                //System.out.println("program (i,j): (" + i + "," + j + "): " + program);
                myCompiler = new IntCodeCompiler(new ArrayList<>(program));
                while (myCompiler.executeNextInstruction(true) == IntCodeCompiler.RETURNCODE_OK);
                BigInteger result = myCompiler.getProgram().get(0);
                if (result.intValue() == 19690720) {
                    System.out.println("Result found with values " + i + " and " + j + ". Answer = " + (i*100+j));
                    break outerloop;
                }
            }
        }

    }
}
