import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day16_2020 {
    private static ArrayList<Rule> rules = new ArrayList<>();
    private static ArrayList<int[]> tickets = new ArrayList<>();
    private static ArrayList<ArrayList<Rule>> potentialFields = new ArrayList<>();
    private static HashMap<Integer, Rule> rulesProcessed = new HashMap<>();
    private static int[] myTicket;

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day216.txt"));
        int endRuleIndex = inputLines.indexOf("");

        // read rules
        for (int i = 0; i < endRuleIndex; i++) {
            String[] line = inputLines.get(i).split(":");
            rules.add(new Rule(line[0].trim(), line[1].trim()));
        }

        // Part 1: count invalid numbers
        long result = 0;
        for (int i = (endRuleIndex + 5); i < inputLines.size(); i++) {
            long l = checkRule(inputLines.get(i));
            if (l >= 0) {
                result += l;
            } else {
                int[] ticket = makeTicket(inputLines.get(i).split(","));
                tickets.add(ticket);
                if (ticket[15] == 0) {
                    System.out.println("ERROR " + l);
                }
            }

        }
        System.out.println("Part 1: " + result);

        // Part 2: determine rules
        potentialFields = new ArrayList<>(rules.size());
        for (int i = 0; i < rules.size(); i++) {
            ArrayList<Rule> matchingRules = getMatchingRules(i); // get all the rules that could match this field
            potentialFields.add(matchingRules); // and add them to the potential rules for that field
        }

        do {
            // get the next empty rule (field with only one rule applicable)
            Rule r = addNextEmptyRule();
            // next, remove the rule from the potential ones
            for (int i = 0; i < rules.size(); i++) {
                ArrayList<Rule> ithRules = potentialFields.get(i);
                if (ithRules != null) ithRules.remove(r);
            }
        } while (rulesProcessed.size() < rules.size());

        // let's look at our ticket
        myTicket = makeTicket(inputLines.get(endRuleIndex+2).split(","));

        // and calculate the result
        BigInteger solution = BigInteger.ONE;
        for (int i = 0; i < rules.size(); i++) {
            Rule r = rulesProcessed.get(i);
            if (r.getName().contains("departure")) {
                System.out.println("Field " + i + ": " + r);
                solution = solution.multiply(BigInteger.valueOf(myTicket[i]));
            }
        }
        System.out.println("Part 2: " + solution);

    }

    private static Rule addNextEmptyRule() {
        for (int i = 0; i < rules.size(); i++) {
            ArrayList<Rule> ithRules = potentialFields.get(i);
            if ((ithRules != null) && (ithRules.size() == 1)) {
                rulesProcessed.put(i, ithRules.get(0));
                potentialFields.set(i, null);
                return ithRules.get(0);
            }
        }
        return null;
    }

    private static ArrayList<Rule> getMatchingRules(int fieldNumber) {
        ArrayList<Rule> result = new ArrayList<>();
        for (Rule r : rules) {
            boolean matches = true;
            for (int[] ticket : tickets) { // check for each ticket if the rule matches; if so => add the rule
                if (!r.isValid(ticket[fieldNumber])) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                result.add(r);
            }
        }
        return result;
    }

    private static long checkRule(String fields) {
        String[] numbers = fields.split(",");
        long result = -1;
        for (String s : numbers) {
            int n = Integer.parseInt(s);
            if (! checkRules(n)) {
                if ((n == 0) && (result < 0)) {
                    result = 0;
                }
                result += n;
            }
        }
        return result;
    }

    private static boolean checkRules(int number) {
        boolean result = false;
        for (Rule r : rules) {
            if (r.isValid(number)) {
               result = true;
            }
        }
        return result;
    }

    private static int[] makeTicket(String[] line) {
        int[] result = new int[line.length];
        for (int i = 0; i < line.length; i++) {
            result[i] = Integer.parseInt(line[i]);
        }
        return result;
    }
}
