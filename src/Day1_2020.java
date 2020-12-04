import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1_2020 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("resources\\input_day201.txt"));
        ArrayList<Integer> numbers = new ArrayList<>();
        while (input.hasNext()) {
            numbers.add(input.nextInt());
        }
        int solution;
        outerloop:
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i+1; j < numbers.size(); j++) {
                int n1 = numbers.get(i);
                int n2 = numbers.get(j);
                if ((n1 + n2) == 2020) {
                    solution = n1 * n2;
                    System.out.println("Solution: " + solution + " (" + n1 + ", " + n2 + ").");
                    break outerloop;
                }
            }
        }


    }
}
