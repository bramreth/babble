import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bramreth on 4/4/17.
 */
public class Babble {
    private BabbleListener listener;
    private BabbleInputListener inputListener;
    private boolean running;
    private Display gui;
    private Game gameState = new Game();
    public Babble(){
        listener = new BabbleListener();
        inputListener = new BabbleInputListener();
        gui = new Display(listener, inputListener);
        running = true;
        gui.logConsole(gameState.getPlot());
    }

    /**
     * Created by bramreth on 4/4/17.
     */
    public class BabbleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            switch (e.getActionCommand()){
                case "adventure":
                    gui.changeCard(Display.State.ADVENTURE);
                    gui.focus();
                    break;
                case "options":
                    gui.changeCard(Display.State.OPTIONS);
                    break;
                case "quit":
                    System.exit(0);
                    break;
                case "save":
                    gui.updateSaves(Game.getSlots());
                    gui.changeCard(Display.State.SAVE);
                    break;
                case "load":
                    gui.updateSaves(Game.getSlots());
                    gui.changeCard(Display.State.LOAD);
                    break;
                case "menu":
                    gui.changeCard(Display.State.MENU);
                    break;
                case "load slot":
                    if(gameState.loadGameDataFromFile(gui.getLoadSlot())){
                        gui.update(gameState.getPlot());
                        gui.changeCard(Display.State.ADVENTURE);
                    }
                    break;
                case "save to slot":
                    gameState.saveGameDataToFile(gui.getSlot());
                    gui.updateSaves(Game.getSlots());
                    gui.changeCard(Display.State.ADVENTURE);
                    break;
                case "delete":
                    gameState.deleteSave(gui.getSlot());
                    gui.updateSaves(Game.getSlots());
                    break;
            }
        }
    }

    public class BabbleInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if(e.getActionCommand().length() < 48) {
                gui.logInput(e.getActionCommand());
                gui.logConsole(gameState.processInput(e.getActionCommand()));
                gameState.process(e.getActionCommand());
            }else{
                gui.logInput("tried to say much too much");
            }
            gui.clearInput();
        }
    }
}
