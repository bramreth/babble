import java.io.File;
import java.util.ArrayList;

/**
 * needs implementing; will store information for adventure save slot taht can be saved to and read
 * from a permanent file
 * Created by bramreth on 4/5/17.
 */
public class Save {
    private int plotInfo;
    private ArrayList<String> saveInfo;
    private String name;
    public Save(){
        plotInfo = 0;
        saveInfo = new ArrayList<>();
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
}
