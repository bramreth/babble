import java.awt.*;
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
                case "back":
                    gui.changeCard(Display.State.MENU);
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
            }else{
                gui.logInput("tried to say much too much");
            }
            gui.clearInput();
        }
    }
}
