import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day4_2020 {

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day204.txt"));
        Iterator<String> lineIterator = inputLines.iterator();

        // create list of passports based on the input
        ArrayList<Passport> passports = new ArrayList<>();
        String ppRead = "";
        while(lineIterator.hasNext()) {
            String line = lineIterator.next();
            if ((line == null) || line.length() < 3) {
                //System.out.println(ppRead);
                //System.out.println(new Passport(ppRead));
                passports.add(new Passport(ppRead));
                ppRead = "";
            } else {
                ppRead = ppRead + " " + line;
            }
        }
        if (ppRead.length() > 1) passports.add(new Passport(ppRead));

        // validate passports
        int validPassports = 0; // part 1
        int fullyValidPassports = 0; // part 2
        for (Passport pp : passports) {
            if (pp.isValid()) {
                validPassports += 1;
                if (pp.isValidFull()) fullyValidPassports += 1;
            }

        }
        System.out.println("Part 1: " + validPassports);
        System.out.println("Part 2: " + fullyValidPassports);

    }
}
