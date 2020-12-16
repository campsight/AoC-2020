public class Rule {
    private String name;
    private int s1 = 0;
    private int e1 = 0;
    private int s2 = 0;
    private int e2 = 0;

    public Rule(String name, String range) {
        this.name = name;
        processRange(range);
    }

    public boolean isValid(int number) {
        return (((number >= s1) && (number <= e1)) || ((number >= s2) && (number <= e2)));
    }

    private void processRange(String range) {
        String[] ranges = range.split("or");
        String[] range1 = ranges[0].trim().split("-");
        String[] range2 = ranges[1].trim().split("-");
        s1 = Integer.parseInt(range1[0]);
        e1 = Integer.parseInt(range1[1]);
        s2 = Integer.parseInt(range2[0]);
        e2 = Integer.parseInt(range2[1]);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
