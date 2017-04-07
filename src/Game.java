import java.io.*;
import java.util.ArrayList;

/**
 * Created by bramreth on 4/5/17.
 */
public class Game {
    private Save saveSlot;
    private String buffer;
    private ArrayList<Location> localeList;
    /**
     * new game constructor
     */
    public Game(){
        saveSlot = new Save();
        initLocales();
    }

    /**
     * existing save slot constructor
     * @param saveIn
     */
    public Game(Save saveIn){
        saveSlot = saveIn;
        initLocales();
    }

    public void initLocales(){
        localeList = new ArrayList<>();
        localeList.add(new Location("your cell", "A sickly green interstellar detention cell"));
    }

    public String getPlot(){
        switch(saveSlot.getPlotInfo()){
            case 0:
                return "|narrator| What is your name then?";
            case 2:
                return "|narrator| Okay " + saveSlot.getName() + ", simple rules."
                +"\nconsole: |narrator| If you want to do something, tell me what you want to do"
                    +"\nconsole: |narrator| and what you want to do it to."
                        +"\nconsole: |narrator| Try it, open your eyes.";
            case 3:
                saveSlot.setCurrentLocation(localeList.get(0));
                return "Artificial light floods your eyes. An all to familiar room is before you,"
                        +"\nconsole: the sickly pale green tone of a human interstellar detention cell."
                        +"\nconsole: You are lying down on a flimsy plastic bed."
                        +"\nconsole: |intercom| Inmate " + saveSlot.getName().substring(0,1)
                        + "221, please stand up for inspection.";
            case 4:
                return "That's all folks!";
        }
        return "";
    }

    public String processInput(String input){
        SimpleInstruction ins = process(input);
        if(!ins.isValid()){
            return "|narrator| " + ins.error;
        }
        switch(saveSlot.getPlotInfo()){
            case 0:
                if(ins.result != null){
                    saveSlot.movePlot(1);
                    buffer = input;
                    return "|narrator| So its " + input + " then?";
                }
                return "|narrator| Oi give me just one name, I don't care for more";
            case 1:
                if(processYes(input)){
                    saveSlot.addName(buffer);
                    saveSlot.movePlot(2);
                    return getPlot();
                }else {
                    saveSlot.movePlot(0);
                    return getPlot();
                }
            case 2:
                SimpleInstruction temp = process(input);
                if(temp.getVerb() == Verb.OPEN && temp.isValid()){
                    saveSlot.movePlot(3);
                    return getPlot();
                }else {
                    return "|narrator| That's hard with your eyes shut~";
                }
            case 3:
                String actionResult = makeAction(ins);
                System.out.println(makeAction(ins));
                return actionResult;


        }
        return "";
    }

    public SimpleInstruction process(String input){
        String[] words = input.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for(String word: words){
            System.out.println(word);
        }
        if(words.length == 1) {
            return new SimpleInstruction(words[0]);
        }
        if(words.length == 2) {
            return new SimpleInstruction(words[0], words[1]);
        }
        if(words.length == 3) {
            return new SimpleInstruction(words[0], words[2]);
        }
        return new SimpleInstruction();
    }

    public String makeAction(SimpleInstruction in){
        switch (in.getVerb()){
            case GO:
                saveSlot.setCurrentLocation((Location)in.getNoun());
                return "You go to the " + in.getNoun().getName();
            case LOOK:
                System.out.println(in.getNoun().getName());
                System.out.println(in.getNoun().getDescription());
                return in.getNoun().getDescription();
        }
        return in.getResult();
    }
    public boolean processYes(String input){
        if(input.contains("yes")||input.contains("certainly")||input.contains("affirmative")) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> getSlots(){
        ArrayList<String> list = new ArrayList<>();
        Save tempSave;
        for(int x = 1; x < 4; x++) {
            try {
                FileInputStream fileStream = new FileInputStream("slot" + x + ".txt");
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                tempSave = (Save) objectStream.readObject();
                list.add(tempSave.getName());
            } catch (Exception e) {
                e.printStackTrace();
                list.add("empty");
            }
        }
        return list;
    }

    public Save getSaveSlot() {
        return saveSlot;
    }

    public void setSaveSlot(Save saveSlot) {
        this.saveSlot = saveSlot;
    }

    public class SimpleInstruction {
        private boolean valid;
        private Verb verb;
        private Noun noun;
        private String result;
        private String error;
        private boolean interjection;

        public SimpleInstruction() {
            valid = false;
        }

        public SimpleInstruction(String verbIn, String nounIn) {
            valid = false;
            verb = Verb.INVALID;
            switch (verbIn){
                case "walk":
                case "go":
                case "move":
                case "proceed":
                case "run":
                    verb = verb.GO;
                    error = nounIn + "is not a valid place to go to";
                    if(saveSlot.getCurrentLocation().getExits().size() == 1){
                        if(nounIn.equals("door") || nounIn.equals("exit")){
                            noun = saveSlot.getCurrentLocation().getExits().get(0);
                            valid = true;
                        }
                    }
                    for(Location temp: saveSlot.getCurrentLocation().getExits()) {
                        if (nounIn.equals(temp.getName())) {
                            noun = temp;
                            valid = true;
                        }
                    }
                    break;
                case "open":
                    verb = Verb.OPEN;
                    if(nounIn.equals("eyes")){
                        valid = true;
                        error = "your eyes are open though?";
                    }
                    break;
                    //finish implementing
                case "look":
                case "search":
                case "examine":
                    verb = Verb.LOOK;
                    for(Item item :saveSlot.getCurrentLocation().getItems()){
                        if(nounIn.equals(item.getName())){
                            noun = item;
                            valid = true;
                        }
                    }
                    if(nounIn.equals("room") || nounIn.equals("around") || nounIn.equals(saveSlot.getCurrentLocation().getName())){
                        noun = saveSlot.getCurrentLocation();
                        valid = true;
                    }
                    System.out.println(saveSlot.getCurrentLocation().getName());
                    if(!valid){
                        error = " you can't really look at '"+nounIn+"'";
                    }
                    break;
                default:
                    valid = false;
                    error = "that's not a verb I understand";
            }
        }

        public SimpleInstruction(String interjectionIn) {
            if (interjectionIn.contains("yes") || interjectionIn.contains("certainly") || interjectionIn.contains("affirmative")) {
                interjection = true;
            } else if (interjectionIn.contains("no") || interjectionIn.contains("negative")) {
                interjection = false;
                error = "Why would you say that?";
            }
            result = interjectionIn;
            valid = true;
        }

        public Noun getNoun() {
            return noun;
        }

        public Verb getVerb() {
            return verb;
        }

        public String getResult() {
            return result;
        }

        public boolean getInterjection() {
            return interjection;
        }

        public boolean isValid() {
            return valid;
        }


    }

    /**
     * the enumerated list of valid verbs, to be updated
     */
    public enum Verb{
        INVALID, GO, OPEN, LOOK, TAKE, DROP, USE
    }
        public boolean loadGameDataFromFile(int slotNo){
            File load;
            boolean init = false;
            System.out.println(slotNo);
            switch (slotNo){
                case 1:
                    load = new File("slot1.txt");
                    init = true;
                    break;
                case 2:
                    load = new File("slot2.txt");
                    init = true;
                    break;
                case 3:
                    load = new File("slot3.txt");
                    init = true;
                    break;
                default:
                    load = new File("");
            }
            if(init) {
                try {
                    FileInputStream fileStream = new FileInputStream(load);
                    ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                    saveSlot = (Save) objectStream.readObject();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    public void deleteSave(int selectedSaveSlot) {
        File save;
        boolean init = false;
        System.out.println(selectedSaveSlot);
        switch (selectedSaveSlot){
            case 1:
                save = new File("slot1.txt");
                init = true;
                break;
            case 2:
                save = new File("slot2.txt");
                init = true;
                break;
            case 3:
                save = new File("slot3.txt");
                init = true;
                break;
            default:
                save = new File("");
        }
        if(init) {
            try {
                save.delete();
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            System.out.println("File creation failed");
        }
    }
        public void saveGameDataToFile(int selectedSaveSlot) {
            File save;
            boolean init = false;
            System.out.println(selectedSaveSlot);
            switch (selectedSaveSlot){
                case 1:
                    save = new File("slot1.txt");
                    init = true;
                    break;
                case 2:
                    save = new File("slot2.txt");
                    init = true;
                    break;
                case 3:
                    save = new File("slot3.txt");
                    init = true;
                    break;
                default:
                    save = new File("");
            }
            if(init) {
                try {
                    FileOutputStream fileStream = new FileOutputStream(save);
                    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

                    objectStream.writeObject(saveSlot);

                    objectStream.close();
                    fileStream.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }else{
                System.out.println("File creation failed");
            }
        }
}
