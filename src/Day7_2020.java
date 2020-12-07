import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day7_2020 {

    private static ArrayList<Bag> getBagsHoldingColor(ArrayList<Bag> bags, String color) {
        return bags.stream().filter(x -> (x.nbOfColor(color) > 0)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day207.txt"));
        Iterator<String> lineIterator = inputLines.iterator();

        // Create list of bags (bag rules)
        ArrayList<Bag> myBags = new ArrayList<>();
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            String [] splitLine = line.split(" bags contain ");
            Bag b = new Bag(splitLine[0]);
            if (splitLine[1].indexOf("other") > 0) {
                myBags.add(b);
            } else {
                String[] contents = splitLine[1].split(",");
                for (String bagDesc : contents) {
                    String color = bagDesc.trim().substring(2,bagDesc.indexOf("bag") - 1).trim();
                    Bag innerBag = new Bag(color);
                    b.addInnerBag(innerBag, Integer.valueOf(bagDesc.trim().substring(0,1)));
                }
                myBags.add(b);
            }
        }

        // part 1
        boolean resultFound = false;
        ArrayList<Bag> okBags = new ArrayList<>(); // the ones we need
        ArrayList<Bag> searchingBags = new ArrayList<>();
        searchingBags.add(new Bag("shiny gold"));
        do {
            resultFound = false;
            ArrayList<Bag> bagsContainingColor = new ArrayList<>();
            // for each bag (color) we search, get all the bags containing these bags
            for (Bag b : searchingBags) {
                bagsContainingColor.addAll(getBagsHoldingColor(myBags, b.getColor()));
            }
            // add them to the ok bags if they're not yet in it
            searchingBags = new ArrayList<>();
            for (Bag b : bagsContainingColor) {
                if (okBags.stream().filter(x -> x.sameColor(b.getColor())).count() == 0) {
                    resultFound = true;
                    okBags.add(b);
                    searchingBags.add(b);
                }
            }
        } while (resultFound);
        System.out.println("Part 1:" + okBags.size());

        // part 2
        Bag goldenBag = myBags.stream().filter(x -> x.sameColor("shiny gold")).collect(Collectors.toCollection(ArrayList::new)).get(0);
        System.out.println("Part 2:" + (getTotalBagContents(myBags, goldenBag) - 1)); // minus one: don't count de golden bag itself
    }

    private static int getTotalBagContents(ArrayList<Bag> bagList, Bag bag) {
        if (bag.isEmpty()) return 1;
        int count = 1;
        for (Bag b : bag.getInnerBags()) {
            count += getTotalBagContents(bagList, bagList.stream().filter(x -> x.sameColor(b.getColor())).collect(Collectors.toCollection(ArrayList::new)).get(0));
        }
        return count;
    }
}
