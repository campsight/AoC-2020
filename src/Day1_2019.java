import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1_2019 {
    public static int calculateFuelSimple(int mass) {
        return (mass / 3) - 2;
    }

    public static int calculateFuel(int mass) {
        int next = calculateFuelSimple(mass);
        return (next > 0 ? (next + calculateFuel(next)) : 0);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("resources\\input_day1.txt"));
        int totalFuel = 0;
        while (input.hasNext()) {
            int module_mass = input.nextInt();
            totalFuel += calculateFuel(module_mass);
        }

        System.out.println(totalFuel);
    }
}
