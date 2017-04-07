import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bramreth on 4/6/17.
 */
public class Location extends Noun implements Serializable {
    private ArrayList<Location> exits;
    private ArrayList<Item> items;
    public Location(String nameIn, String descriptionIn){
        super.name = nameIn;
        super.description = descriptionIn;
        items = new ArrayList<>();
        exits = new ArrayList<>();
    }
    public Location(String nameIn, String descriptionIn, ArrayList<Item> itemList){
        name = nameIn;
        description = descriptionIn;
        items = itemList;
        exits = new ArrayList<>();
    }

    public void addExits(Location exitIn){
        exits.add(exitIn);
    }
    public ArrayList<Location> getExits(){
        return exits;
    }
    public void addItem(Item itemIn){
        items.add(itemIn);
    }


    public ArrayList<Item> getItems() {
        return items;
    }
}
