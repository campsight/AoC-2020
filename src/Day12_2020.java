import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day12_2020 {
    public static void main(String[] args) throws IOException, IllegalStateException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day212.txt"));

        Ship myShip = new Ship();
        WaypointShip mySecondShip = new WaypointShip();
        for (String line : inputLines) {
            myShip.navigate(line);
            //System.out.println(line);
            mySecondShip.navigate(line);
            //System.out.println(mySecondShip);
        }

        System.out.println(myShip.getManhattanDist());
        System.out.println(mySecondShip.getManhattanDist());
    }
}
