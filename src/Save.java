import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * needs implementing; will store information for adventure save slot taht can be saved to and read
 * from a permanent file
 * Created by bramreth on 4/5/17.
 */
public class Save implements Serializable {
    private int plotInfo;
    private String saveInfo;
    private String name;
    private int carryWeight;
    private Location currentLocation;
    private ArrayList<Item> heldItems;
    private Color colour;
    public Save(){
        plotInfo = 0;
        saveInfo = "temp";
        carryWeight = 10;
        currentLocation = new Location("???","it looks confusing");
    }

    public String getName(){
        return name;
    }

    public int getPlotInfo(){
        return plotInfo;
    }

    public void movePlot(int newPlot){
        plotInfo = newPlot;
    }

    public void addName(String nameIn){
        name = nameIn;
    }

    public boolean addItem(Item itemIn){
        int w = 0;
        for(Item i: heldItems){
            w+= i.getWeight();
        }
        if(w + itemIn.getWeight() <= carryWeight){
            heldItems.add(itemIn);
            return true;
        }
        return false;
    }

    public void setCurrentLocation(Location locationIn){
        currentLocation = locationIn;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public ArrayList<Item> getHeldItems() {
        return heldItems;
    }
}
