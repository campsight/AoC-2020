import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Day23_2020 {
    public static LinkedList<Integer> cups;
    public static int pointer;
    public static int nbCups;

    public static void main(String[] args) throws IOException {
        // read input
        String input = "974618352";
        //String input = "389125467"; // example
        cups = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            cups.add(Integer.valueOf(input.substring(i, i+1)));
        }
        pointer = 0;

        //System.out.println(cups);
        for (int i = 0; i < 100; i++) {
            playRound();
            //System.out.println(cups);
        }
        System.out.println("Part 1: " + calculateResult());

    }

    private static String calculateResult() {
        boolean found = false;
        while (!found) {
            if (cups.get(pointer) == 1) {
                found = true;
            }
            pointer = (pointer + 1) % cups.size();
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < (cups.size() - 1); i++) {
            result.append(cups.get((pointer+i)%cups.size()));
        }
        return result.toString();
    }

    private static void playRound() {
        LinkedList<Integer> tempList = new LinkedList<>();
        int remPos = (pointer+1) % cups.size();
        for (int i = 0; i < 3; i++) {
            if (remPos == cups.size()) {
                remPos = 0;
            };
            tempList.add(cups.remove(remPos));
        }
        int nxtNumber = cups.get(remPos % cups.size());
        int iPos = nextPos(pointer, cups) + 1;
        cups.addAll(iPos, tempList);
        pointer = nextPointer(nxtNumber);
        //pointer +=1;
    }

    private static int nextPointer(int nxtNumber) {
        for (int i = 0; i < cups.size(); i++) {
            if (cups.get(i) == nxtNumber) return i;
        }
        return -1;
    }

    private static int nextPos(int cPos, LinkedList<Integer> list) {
        int nb = list.get(Math.min(cPos, list.size()-1)) - 1;
        if (nb < 0) { nb = 9; }
        boolean found = false;
        int result = -1;
        do {
            for (int i = 1; i <= list.size(); i++) {
                int nextNb = list.get((cPos+i)%list.size());
                if (nextNb == nb) {
                    found = true;
                    result = (cPos+i)%list.size();
                    break;
                }
            }
            if (!found) {
                nb -=1;
                if (nb < 0) { nb = 9; }
            }
        } while (!found);
        return result;
    }

}
