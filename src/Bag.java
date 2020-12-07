import java.util.ArrayList;
import java.util.Iterator;

public class Bag {
    private String color;
    private ArrayList<Bag> innerBags;

    public Bag(String color) {
        this.color = color.trim();
        innerBags = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color.trim();
    }

    public ArrayList<Bag> getInnerBags() {
        return innerBags;
    }

    public void addInnerBag(Bag bag, int nb) {
        for (int i = 0; i < nb; i++) {
            this.innerBags.add(bag);
        }
    }

    public boolean isEmpty() {
        return (innerBags.size() == 0);
    }

    public boolean sameColor(String color) {
        return this.color.equalsIgnoreCase(color.trim());
    }

    public boolean contains(String color) {
        return nbOfColor(color) > 0;
    }

    public int nbOfColor(String color) {
        return (isEmpty() ? 0 : (int) (innerBags.stream().filter(x -> x.sameColor(color)).count()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bag (" + color + ") contains ");
        if (isEmpty()) return sb.append(" nothing.").toString();
        Iterator<Bag> it = innerBags.iterator();
        while (it.hasNext()) sb.append(it.next().getColor() + ", ");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return sameColor(((Bag) obj).getColor());
        } catch (Exception e) {
            return false;
        }
    }
}
