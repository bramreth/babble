import java.io.Serializable;

/**
 * Created by bramreth on 4/6/17.
 */
public class Item implements Serializable {
    private String name, description;
    private int weight;
    public Item(String nameIn, String descriptionIn, int weightIn){
        name = nameIn;
        description = descriptionIn;
        weight = weightIn;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
