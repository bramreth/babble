import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by bramreth on 4/4/17.
 */
public class Display {
    private final Color bgColour = new Color(40, 50, 56);
    private final Color buttonColour = new Color(56, 67, 76);
    private final Color textColour = new Color(0, 204, 0);
    private Font normalFont = new Font("sans-serif", Font.PLAIN, 12);
    private Font titleFont = new Font("sans-serif", Font.BOLD, 13);
    private JTextArea log;
    private JTextField input;
    private JButton adventureButton;
    private JButton optionsButton;
    private JButton quitButton;
    private JFrame frame;
    private JPanel cards;
    public ActionListener listener;
    public ActionListener inputListener;
    public Display(ActionListener listenerIn, ActionListener inputListenerIn){
        listener = listenerIn;
        inputListener = inputListenerIn;
        frame = new JFrame("Yuconz File App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Create and set up the content pane.
        setupPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setSize(new Dimension(600, 250));
        centerFrame();
        frame.setVisible(true);
    }

    /**
     * centers the frame
     */
    public void centerFrame()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int yPos = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(xPos, yPos);
    }

    /**
     * setupPane
     */
    public void setupPane(Container contentPane){
        JPanel headerPane = new JPanel();
        headerPane.setBackground(bgColour);
        JLabel headerLbl = new JLabel("Babble");
        headerLbl.setFont(titleFont);
        headerLbl.setForeground(textColour);
        headerPane.add(headerLbl);

        JPanel adventureCard = createAdventureCard();
        JPanel menuCard = createMenuCard();
        JPanel optionsCard = createOptionsCard();
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(menuCard, "Menu");
        cards.add(adventureCard, "Adventure");
        cards.add(optionsCard, "Options");

        contentPane.add(headerPane, BorderLayout.PAGE_START);
        contentPane.add(cards, BorderLayout.CENTER);
    }

    public JPanel createAdventureCard(){
        JPanel adventurePanel = new JPanel(new GridLayout(2,1));
        JPanel bottomPanel = new JPanel(new GridLayout(2,1));
        adventurePanel.setBackground(bgColour);
        log = new JTextArea();
        log.setBackground(bgColour);
        log.setForeground(textColour);
        log.setEditable(false);
        JScrollPane sp = new JScrollPane(log);
        input = new JTextField();
        input.setBackground(buttonColour);
        input.setForeground(textColour);
        input.addActionListener(inputListener);
        JButton backButton = new JButton("Back");
        backButton.setBackground(buttonColour);
        backButton.setForeground(textColour);
        backButton.setFont(normalFont);
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);
        adventurePanel.add(sp);
        bottomPanel.add(input);
        bottomPanel.add(backButton);
        adventurePanel.add(bottomPanel);
        return adventurePanel;
    }

    public JPanel createMenuCard(){
        JPanel menuPanel = new JPanel(new GridLayout(3,1));
        menuPanel.setBackground(bgColour);
        adventureButton = new JButton("Enter the world of incoherence");
        adventureButton.setBackground(buttonColour);
        adventureButton.setForeground(textColour);
        adventureButton.setFont(normalFont);
        adventureButton.setActionCommand("adventure");
        adventureButton.addActionListener(listener);
        optionsButton = new JButton("Options");
        optionsButton.setBackground(buttonColour);
        optionsButton.setForeground(textColour);
        optionsButton.setFont(normalFont);
        optionsButton.setActionCommand("options");
        optionsButton.addActionListener(listener);
        quitButton = new JButton("Quit");
        quitButton.setBackground(buttonColour);
        quitButton.setForeground(textColour);
        quitButton.setFont(normalFont);
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(listener);
        menuPanel.add(adventureButton);
        menuPanel.add(optionsButton);
        menuPanel.add(quitButton);
        return menuPanel;
    }
    public JPanel createOptionsCard(){
        JPanel optionsPanel = new JPanel(new GridLayout(6,1));
        optionsPanel.setBackground(bgColour);
        JLabel temp1 = new JLabel("placeholder");
        temp1.setFont(normalFont);
        temp1.setForeground(textColour);
        JLabel temp2 = new JLabel("placeholder");
        temp2.setFont(normalFont);
        temp2.setForeground(textColour);
        JLabel temp3 = new JLabel("placeholder");
        temp3.setFont(normalFont);
        temp3.setForeground(textColour);
        JLabel temp4 = new JLabel("placeholder");
        temp4.setFont(normalFont);
        temp4.setForeground(textColour);
        JLabel temp5 = new JLabel("placeholder");
        temp5.setFont(normalFont);
        temp5.setForeground(textColour);
        JButton backButton = new JButton("Back");
        backButton.setBackground(buttonColour);
        backButton.setForeground(textColour);
        backButton.setFont(normalFont);
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);
        optionsPanel.add(temp1);
        optionsPanel.add(temp2);
        optionsPanel.add(temp3);
        optionsPanel.add(temp4);
        optionsPanel.add(temp5);
        optionsPanel.add(backButton);
        return optionsPanel;
    }

    public void changeCard(State stateIn){
        CardLayout cl = (CardLayout) (cards.getLayout());
        switch (stateIn){
            case MENU:
                cl.show(cards, "Menu");
                break;
            case ADVENTURE:
                cl.show(cards, "Adventure");
                break;
            case OPTIONS:
                cl.show(cards, "Options");
                break;
        }
    }

    public void clearInput(){
        input.setText("");
    }

    public void logInput(String input){
        log.setText(log.getText() + "\nuser: " + input);
    }

    public void focus(){
        input.requestFocus();
    }

    public enum State{
        MENU, ADVENTURE, OPTIONS
    }
}
