import java.io.Serializable;

/**
 * Created by bramreth on 4/6/17.
 */
public class Item extends Noun implements Serializable {
    private int weight;
    public Item(String nameIn, String descriptionIn, int weightIn){
        super.name = nameIn;
        super.description = descriptionIn;
        weight = weightIn;
    }

    public int getWeight() {
        return weight;
    }


}
