/**
 * Created by bramreth on 4/5/17.
 */
public class Game {
    private Save saveSlot;
    private String buffer;
    /**
     * new game constructor
     */
    public Game(){
        saveSlot = new Save();
    }

    /**
     * existing save slot constructor
     * @param saveIn
     */
    public Game(Save saveIn){
        saveSlot = saveIn;
    }

    public String getPlot(){
        switch(saveSlot.getPlotInfo()){
            case 0:
                saveSlot.movePlot(1);
                return "What is your name then?";
            case 3:
                return "here's the world then you crazy wanker";
        }
        return "";
    }

    public String processInput(String input){
        switch(saveSlot.getPlotInfo()){
            case 1:
                saveSlot.movePlot(2);
                buffer = input;
                return "So its " + input + " then?";
            case 2:
                if(processYes(input)){
                    saveSlot.addName(buffer);
                    saveSlot.movePlot(3);
                    return getPlot();
                }else {
                    saveSlot.movePlot(0);
                    return getPlot();
                }

        }
        return "";
    }

    public boolean processYes(String input){
        if(input.contains("yes")||input.contains("certainly")||input.contains("affirmative")) {
            return true;
        }
        return false;
    }
}
