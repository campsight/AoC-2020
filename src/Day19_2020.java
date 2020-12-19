import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19_2020 {
    private static Map<Integer, String> rules = new HashMap<>();
    private static Map<Integer, String> regexRules = new HashMap<>();
    private static int maxDepth900 = 10;
    private static int maxDepth901 = 10;

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day219.txt"));
        int endRuleIndex = inputLines.indexOf("");

        for (int i = 0; i < endRuleIndex; i++) {
            String[] rule = inputLines.get(i).split(": ");
            rules.put(Integer.parseInt(rule[0]), rule[1]);
        }

        System.out.println(buildRegex(0,0,0));
        String regex = "^" + buildRegex(0,0,0) + "$";
        int count = 0;
        for (int i = endRuleIndex+1; i < inputLines.size(); i++) {
            count += (inputLines.get(i).matches(regex) ? 1 : 0);
        }
        System.out.println("Part 1: " + count);

        // Part 2: update the rules
        rules.put(899, "900 901");
        rules.put(900, "42 | 42 900"); //8th
        rules.put(901, "42 31 | 42 901 31"); //11th

        String newRegex = "^" + buildRegex(899, 0, 0) + "$";
        System.out.println(newRegex);
        count = 0;
        for (int i = endRuleIndex+1; i < inputLines.size(); i++) {
            count += (inputLines.get(i).matches(newRegex) ? 1 : 0);
        }
        System.out.println("Part 2: " + count);

        // Alternative Part 2 - seems to be faster :)
        String regex42 = regexRules.get(42);
        String regex31 = regexRules.get(31);
        String pre = "^(" + regex42 + ")+(" + regex42 + "){";
        String mid = "}(" + regex31 + "){";
        String post = "}$";
        ArrayList<String> regexes = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            regexes.add(pre + i + mid + i + post);
        }
        count = 0;
        for (int i = endRuleIndex+1; i < inputLines.size(); i++) {
            for (int k = 0; k < regexes.size(); k++) {
                boolean matches = inputLines.get(i).matches(regexes.get(k));
                if (matches) {
                    count++;
                    break;
                }
            }
        }
        System.out.println("Part 2b: " + count);

    }


    public static String buildRegex(int level, int depth900, int depth901) {
        // this is for part 2: stop at max recursion depth
        if (level == 900) {
            depth900++;
            if (depth900 >= maxDepth900) return regexRules.get(8);
        } else if (level == 901) {
            depth901++;
            if (depth901 >= maxDepth901) return regexRules.get(11);
        }

        // working for both parts
        // we already built the regex for the rule => just return it
        if (regexRules.containsKey(level)) { return regexRules.get(level); }

        // get the rule
        String rule = rules.get(level);

        // if it is just a character => this is the regex, so add it and return it
        if (rule.startsWith("\"")) {
            rule = rule.substring(1,2);
            regexRules.put(level, rule);
            return rule;
        }

        // if not, process the rule
        StringBuilder sb = new StringBuilder("(");
        String[] subRules = rule.split(" ");
        for (String ithRule : subRules) {
            if (ithRule.equals("|")) { sb.append("|"); }
            else try {
                int ithRuleNb = Integer.parseInt(ithRule);
                sb.append(buildRegex(ithRuleNb, depth900, depth901));
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
        }
        sb.append(")");

        regexRules.put(level, sb.toString());

        return sb.toString();
    }

}

