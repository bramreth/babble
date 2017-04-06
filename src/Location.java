import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bramreth on 4/6/17.
 */
public class Location implements Serializable {
    private String name;
    private String description;
    private ArrayList<Item> items;
    public Location(String nameIn, String descriptionIn){
        name = nameIn;
        description = descriptionIn;
        items = new ArrayList<>();
    }
    public Location(String nameIn, String descriptionIn, ArrayList<Item> itemList){
        name = nameIn;
        description = descriptionIn;
        items = itemList;
    }

    public void addItem(Item itemIn){
        items.add(itemIn);
    }

    public String getName() {
        return name;
    }
}
