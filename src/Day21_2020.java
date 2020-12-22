import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21_2020 {
    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day221.txt"));

        HashMap<String, ArrayList<String>> allergenMap = new HashMap<>();
        ArrayList<String> foodList = new ArrayList<>();
        ArrayList<String> allergenList = new ArrayList<>();
        ArrayList<Recipe> recipeList = new ArrayList<>();
        HashMap<String, Integer> foodCount = new HashMap<>();
        for (String line : inputLines) {
            String[] parts = line.split(" \\(contains ");
            String[] foods = parts[0].split(" ");
            String[] allergens = parts[1].substring(0, parts[1].length()-1).split(", ");
            recipeList.add(new Recipe(allergens, foods));
            for (String food : foods) {
                if (!foodList.contains(food)) {
                    foodList.add(food);
                    foodCount.put(food, 1);
                } else {
                    foodCount.put(food, foodCount.get(food)+1);
                }
            }
            for (String allergen : allergens) {
                if (!allergenList.contains(allergen)) allergenList.add(allergen);
            }
        }


        for (String allergen : allergenList) {
            allergenMap.put(allergen, new ArrayList<>(foodList));
        }
        for (Recipe recipe : recipeList) {
            ArrayList<String> recipeFood = recipe.getFoods();
            ArrayList<String> recipeAllergens = recipe.getAllergens();
            for (String allergen : recipeAllergens) {
                for (String food : foodList) {
                    if (!recipeFood.contains(food)) { allergenMap.get(allergen).remove(food); }
                }
            }
        }
        System.out.println(allergenMap);

        ArrayList<String> okList = new ArrayList<>(foodList);
        for (ArrayList<String> foods : allergenMap.values()) {
            for (String food : foods) {
                okList.remove(food);
            }
        }
        System.out.println(okList);
        int count = 0;
        for (String food : okList) {
            count += foodCount.get(food);
        }
        System.out.println("Part 1: " + count);

        boolean solutionFound = false;
        while (!solutionFound) {
            solutionFound = true;
            ArrayList<String> singleIngredientList = new ArrayList<>();
            for (ArrayList<String> ingredients : allergenMap.values()) {
                if (ingredients.size() == 1) {
                    String ingr = ingredients.get(0);
                    singleIngredientList.add(ingr);
                } else {
                    solutionFound = false;
                }
            }
            for (String allergen : allergenMap.keySet()) {
                if (allergenMap.get(allergen).size() > 1) {
                    allergenMap.get(allergen).removeAll(singleIngredientList);
                }
            }
        }
        System.out.println(allergenMap);
        StringBuilder sbSol = new StringBuilder();
        Collections.sort(allergenList);
        for (String allergen : allergenList) {
            sbSol.append(allergenMap.get(allergen).get(0)).append(",");
        }
        System.out.println("Part 2: " + sbSol.substring(0, sbSol.length()-1));
    }

}
