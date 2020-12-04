import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1_2020b {

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
                for (int k = j+1; k < numbers.size(); k++) {
                    int n1 = numbers.get(i);
                    int n2 = numbers.get(j);
                    int n3 = numbers.get(k);
                    if ((n1 + n2 + n3) == 2020) {
                        solution = n1 * n2 * n3;
                        System.out.println("Solution: " + solution + " (" + n1 + ", " + n2 + ", " + n3 + ")");
                        break outerloop;
                    }
                }
            }
        }


    }
}
