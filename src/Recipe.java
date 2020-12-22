import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {
    ArrayList<String> allergens;
    ArrayList<String> foods;

    public Recipe(String[] allergens, String[] foods) {
        this.allergens = new ArrayList<>(Arrays.asList(allergens));
        this.foods = new ArrayList<>(Arrays.asList(foods));
    }

    public ArrayList<String> getAllergens() {
        return allergens;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }
}
