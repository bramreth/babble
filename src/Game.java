import java.io.*;
import java.util.ArrayList;

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
                return "What is your name then?";
            case 2:
                return "here's the world then you crazy wanker";
        }
        return "";
    }

    public String processInput(String input){
        SimpleInstruction ins = process(input);
        if(!ins.isValid()){
            return "I don't understand what you mean";
        }
        switch(saveSlot.getPlotInfo()){
            case 0:
                if(ins.result != null){
                    saveSlot.movePlot(1);
                    buffer = input;
                    return "So its " + input + " then?";
                }
                return "Oi give me just one name, I don't care for more";
            case 1:
                if(processYes(input)){
                    saveSlot.addName(buffer);
                    saveSlot.movePlot(2);
                    return getPlot();
                }else {
                    saveSlot.movePlot(0);
                    return getPlot();
                }

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

    public class SimpleInstruction {
        private boolean valid;
        private String verb;
        private String noun;
        private String result;
        private boolean interjection;

        public SimpleInstruction() {
            valid = false;
        }

        public SimpleInstruction(String verbIn, String nounIn) {
            verb = verbIn;
            noun = nounIn;
            valid = true;
        }

        public SimpleInstruction(String interjectionIn) {
            if (interjectionIn.contains("yes") || interjectionIn.contains("certainly") || interjectionIn.contains("affirmative")) {
                interjection = true;
            } else if (interjectionIn.contains("no") || interjectionIn.contains("negative")) {
                interjection = false;
            }
            result = interjectionIn;
            valid = true;
        }

        public String getNoun() {
            return noun;
        }

        public String getVerb() {
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
