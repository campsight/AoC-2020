import java.util.ArrayList;
import java.util.Arrays;

public class Food {
    private String name;
    private int nbOccurences;
    private ArrayList<String> potentialAllergens;

    public Food(String name, String[] potentialAllergens) {
        this.name = name;
        this.nbOccurences = 1;
        this.potentialAllergens = new ArrayList<>(Arrays.asList(potentialAllergens));
    }

    public void matchAllergens(String[] allergens) {
        if (potentialAllergens.size() > 0) {
            ArrayList<String> allergenList = new ArrayList<>(Arrays.asList(allergens));
            ArrayList<String> toRemove = new ArrayList<>();
            for (String a : potentialAllergens) {
                if (!(allergenList.contains(a))) {
                    toRemove.add(a);
                }
            }
            potentialAllergens.removeAll(toRemove);
        }
    }

    public void increaseOccurence() {
        nbOccurences++;
    }

    public String getName() {
        return name;
    }

    public int getNbAllergens() {
        if (potentialAllergens == null) return 0;
        return potentialAllergens.size();
    }

    public int getNbOccurences() {
        return nbOccurences;
    }

    public ArrayList<String> getPotentialAllergens() {
        return potentialAllergens;
    }

    @Override
    public boolean equals(Object obj) {
        obj = obj instanceof Food ? ((Food) obj) : null;
        if (obj == null) return false;
        return name.equalsIgnoreCase(((Food) obj).getName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        //sb.append(" occurs ").append(nbOccurences);
        sb.append(" alg: ");
        for (String allergen : potentialAllergens) {
            sb.append(allergen).append(" ");
        }
        return sb.append(".").toString();
    }
}
