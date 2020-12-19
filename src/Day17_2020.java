import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day17_2020 {

    // full credits go to my spouse for today's solution;
    // bright idea to not build spaces but only have a list of cubes
    // see https://github.com/custersnele/AoC_2020/tree/master/src/be/ccfun/day17
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("resources/input_day217.txt"));

        // PART 1
        List<Cube> cubes = new ArrayList<>();
        int j = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                Cube cube = new Cube(i, j, 0);
                cube.setState(line.charAt(i) == '#'? State.ACTIVE : State.INACTIVE);
                cubes.add(cube);
            }
            j++;
        }
        int cycle = 0;
        while (cycle < 6) {
            int maxX = cubes.stream().mapToInt(c -> c.getX()).max().getAsInt();
            int minX = cubes.stream().mapToInt(c -> c.getX()).min().getAsInt();
            int maxY = cubes.stream().mapToInt(c -> c.getY()).max().getAsInt();
            int minY = cubes.stream().mapToInt(c -> c.getY()).min().getAsInt();
            int maxZ = cubes.stream().mapToInt(c -> c.getZ()).max().getAsInt();
            int minZ = cubes.stream().mapToInt(c -> c.getZ()).min().getAsInt();
            List<Cube> nextGen = new ArrayList<>();
            for (int z = minZ-1; z <= maxZ+1; z++) {
                for (int x = minX-1; x <= maxX + 1; x++) {
                    for (int y = minY - 1; y <= maxY + 1; y++) {
                        final int fx = x;
                        final int fy = y;
                        final int fz = z;
                        Optional<Cube> anyCube = cubes.stream().filter(c -> c.getX() == fx && c.getY() == fy && c.getZ() == fz).findAny();
                        Cube cube = new Cube(fx,fy,fz);
                        if (anyCube.isPresent()) {
                            cube = anyCube.get();
                        }
                        nextGen.add(cube.createNext(cube.getNeighbours(cubes)));
                    }
                }
            }
            cubes = nextGen;
            //System.out.println("Cycle " + (cycle+1) + ": " + cubes.stream().filter(c -> c.getState() == State.ACTIVE).count());
            cycle++;
        }
        System.out.println("Part 1: " + cubes.stream().filter(c -> c.getState() == State.ACTIVE).count());

        // PART 2
        List<Cube4D> cubes4D = new ArrayList<>();
        j = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                Cube4D cube = new Cube4D(i, j, 0, 0);
                cube.setState(line.charAt(i) == '#' ? State.ACTIVE : State.INACTIVE);
                cubes4D.add(cube);
            }
            j++;
        }
        cycle = 0;
        while (cycle < 6) {
            //System.out.println(cycle);
            int maxX = cubes4D.stream().mapToInt(c -> c.getX()).max().getAsInt();
            int minX = cubes4D.stream().mapToInt(c -> c.getX()).min().getAsInt();
            int maxY = cubes4D.stream().mapToInt(c -> c.getY()).max().getAsInt();
            int minY = cubes4D.stream().mapToInt(c -> c.getY()).min().getAsInt();
            int maxZ = cubes4D.stream().mapToInt(c -> c.getZ()).max().getAsInt();
            int minZ = cubes4D.stream().mapToInt(c -> c.getZ()).min().getAsInt();
            int maxW = cubes4D.stream().mapToInt(c -> c.getW()).max().getAsInt();
            int minW = cubes4D.stream().mapToInt(c -> c.getW()).min().getAsInt();
            List<Cube4D> nextGen = new ArrayList<>();
            for (int w = minW - 1; w <= maxW + 1; w++) {
                for (int z = minZ - 1; z <= maxZ + 1; z++) {
                    for (int x = minX - 1; x <= maxX + 1; x++) {
                        for (int y = minY - 1; y <= maxY + 1; y++) {
                            final int fx = x;
                            final int fy = y;
                            final int fz = z;
                            final int fw = w;
                            Optional<Cube4D> anyCube = cubes4D.stream().filter(c -> c.getX() == fx && c.getY() == fy && c.getZ() == fz && c.getW() == fw).findAny();
                            Cube4D cube = new Cube4D(fx, fy, fz, fw);
                            if (anyCube.isPresent()) {
                                cube = anyCube.get();
                            }
                            nextGen.add(cube.createNext(cube.getNeighbours(cubes4D)));
                        }
                    }
                }
            }
            cubes4D = nextGen;
            //System.out.println(cubes4D.stream().filter(c -> c.getState() == State.ACTIVE).count());
            cycle++;
        }
        System.out.println("Part 2: " + cubes4D.stream().filter(c -> c.getState() == State.ACTIVE).count());

    }

}
