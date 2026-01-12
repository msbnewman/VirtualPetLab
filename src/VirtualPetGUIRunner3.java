/**
 * A GUI class to run the Virtual Pet Lab
 * Requires javax.swing and java.awt
 * @author Kim Hermans
 * Image Credit: J.P. O'Hara & Jamie O'Hara
 * @November 2024
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

public class VirtualPetGUIRunner3 extends JFrame
{
    // Edit this value to change speed of update
    private final int INTERVAL_IN_SECONDS = 10;
    
    private String info;
    private int timerSeconds, animationTimerSeconds;
    private boolean isEating, isPlaying;
    private VirtualPet pet3;
    private Food apple, cupcake, broccoli, potato;
    private Game coinToss, hoopJumping, guessingGame;
    private ImageIcon imageHappy, imageSad, imageEat, imagePlay;
    private JFrame frame;
    private JPanel petPanel, menuPanel, mainPanel, statsPanel, timerPanel;
    private JLabel petLabel,statsLabel, messageLabel, timerLabel;
    private JButton foodButton, playButton;
    private JRadioButton appleButton, cupcakeButton, broccoliButton, potatoButton, coinButton, hoopButton, guessButton;
    private ButtonGroup foodGroup, gameGroup;
    private JMenu newPetMenu;
    private JMenuItem petRunner3, petRunner4;
    private JMenuBar menuBar;
    
    public VirtualPetGUIRunner3(String name) throws IOException
    {
        // Initializes VirtualPet, Food, and Game objects
        initVirtualPetObjects(name);
        animationTimerSeconds = -1;
        
        // Automatically calls update after INTERVAL_IN_SECONDS time
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> { pet3.updateStatus(); }, 10, INTERVAL_IN_SECONDS, TimeUnit.SECONDS);
        
        // Calls helper methods to initialize components of GUI
        initTimerPanel();   
        initPetDisplayPanel();
        initStatsPanel();
        initFoodButtons();
        initGameButtons();
        initMenuPanel();
        fillLayout();
        initMenuBar();
        
        // Initializes the frame
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(400, 500));
        frame.setTitle("Virtual Pet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adds all components to the frame and makes visible
        frame.add(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }
    
    // Initializes VirtualPet, Food, and Game objects
    public void initVirtualPetObjects(String name)
    {
        pet3 = new VirtualPet(name);
        // TODO: initialize Food objects according to your implementation of the Food class
        apple = new Food("Apple", 2, 1, 1); 
        cupcake = new Food("Cupcake", 1, 2, 2);
        broccoli = new Food("Broccoli", 3, -1, 1);
        potato = new Food("Potato", 2, 0, 2);
        // TODO: initialize Game objects according to your implementation of the Game class
        coinToss = new Game("your parameters here!");
        hoopJumping = new Game("your parameters here!");
        guessingGame = new Game("your parameters here!");

        isEating = false;
        isPlaying = false;
    }
    
    // Initializes the timer that is displayed 
    public void initTimerPanel()
    {
        timerSeconds = 0;
        timerLabel = new JLabel("00:00");
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerSeconds++;
                int minutes = timerSeconds / 60;
                int remainingSeconds = timerSeconds % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, remainingSeconds));
                info = "<html>" + pet3.toString().replaceAll("\n", "<br>") + "</html>";
                statsLabel.setText(info);
                if(!isPlaying && !isEating)
                {
                    if((ImageIcon)petLabel.getIcon() != imageSad && (pet3.getEnergyLevel() < 5 || pet3.getHappinessLevel() < 5))
                        petLabel.setIcon(imageSad);
                    else if((ImageIcon)petLabel.getIcon() != imageHappy && (pet3.getEnergyLevel() >= 5 && pet3.getHappinessLevel() >= 5))
                        petLabel.setIcon(imageHappy);
                }
            }
        });
        timer.start();
        timerPanel = new JPanel();
        timerPanel.add(timerLabel);
        timerPanel.setLayout(new GridBagLayout());
    }
    
    // Initializes the panel that displays the VirtualPet
    public void initPetDisplayPanel() 
    {
        imageHappy = new ImageIcon("img/petHappy.gif");
        imageSad = new ImageIcon("img/petSad.gif");
        imageEat = new ImageIcon("img/petEat.gif");
        imagePlay = new ImageIcon("img/petPlay.gif");
        
        petLabel = new JLabel(imageHappy);
        petPanel = new JPanel(new BorderLayout());
        petPanel.add(petLabel);
    }
    
    // Initializes the panel that contains the information text
    public void initStatsPanel()
    {
        info = "<html>" + pet3.toString().replaceAll("\n", "<br>") + "</html>";
        statsLabel = new JLabel(info);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 0));
        
        messageLabel = new JLabel("Happy Birthday, " + pet3.getName() + "!");
        messageLabel.setForeground(Color.blue);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 0));
        
        statsPanel = new JPanel(new GridLayout(2,1));
        statsPanel.add(messageLabel);
        statsPanel.add(statsLabel);
    }
    
    // Initializes the Food and food option buttons
    public void initFoodButtons()
    {
        foodButton = new JButton("Feed");
        foodButton.addActionListener(new FoodButtonListener());
        foodButton.setPreferredSize(new Dimension(80, 30));
        foodButton.setMaximumSize(new Dimension(80, 30));
        
        // TODO: change these button labels to display your Food options
        appleButton = new JRadioButton("Apple");
        cupcakeButton = new JRadioButton("Cupcake");
        broccoliButton = new JRadioButton("Broccoli");
        potatoButton = new JRadioButton("Potato");
        
        foodGroup = new ButtonGroup();
        foodGroup.add(appleButton);
        foodGroup.add(cupcakeButton);
        foodGroup.add(broccoliButton);
        foodGroup.add(potatoButton);
        appleButton.setSelected(true);
    }
    
    // Initializes the Game and game option buttons
    public void initGameButtons()
    {
        playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        playButton.setPreferredSize(new Dimension(80, 30));
        playButton.setMaximumSize(new Dimension(80, 30));
        // TODO: change these button labels to display your Game options
        coinButton = new JRadioButton("Coin Toss");
        hoopButton = new JRadioButton("Hoop Jump");
        guessButton = new JRadioButton("Guessing Game");
        gameGroup = new ButtonGroup();
        gameGroup.add(coinButton);
        gameGroup.add(hoopButton);
        gameGroup.add(guessButton);
        coinButton.setSelected(true);
    }
    
    // Initializes the menu panel that contains all buttons
    public void initMenuPanel()
    {
        GridBagConstraints c = new GridBagConstraints();
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); 
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(foodButton, c);
        menuPanel.add(appleButton, c);
        menuPanel.add(cupcakeButton, c);
        menuPanel.add(broccoliButton, c);
        menuPanel.add(potatoButton, c);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(playButton, c);
        menuPanel.add(coinButton, c);
        menuPanel.add(hoopButton, c);
        menuPanel.add(guessButton, c);
        menuPanel.add(Box.createVerticalStrut(10));
    }
    
    // Initializes the Menu Bar that allows you to create a new Virtual Pet
    public void initMenuBar()
    {
        petRunner3 = new JMenuItem("VirtualPet2");
        petRunner3.addActionListener(new NewPet2Listener());
        petRunner4 = new JMenuItem("VirtualPet3");
        petRunner4.addActionListener(new NewPet3Listener());
        newPetMenu = new JMenu("New Pet");
        newPetMenu.add(petRunner3);
        newPetMenu.add(petRunner4);
        menuBar = new JMenuBar();
        menuBar.add(newPetMenu);
    }
    
    // Organizes the layout for the main panel
    public void fillLayout()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 60;
        c.weighty = 50;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(petPanel, c);
        c.weightx = 40;
        c.gridx = 1;
        mainPanel.add(menuPanel, c);
        c.weightx = 60;
        c.gridx = 0; 
        c.gridy = 1;
        mainPanel.add(statsPanel, c);
        c.weightx = 40;
        c.gridx = 1;
        mainPanel.add(timerPanel, c);
    }

    // Defines the action when the Feed button is clicked
    class FoodButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Food f = apple;
            if(cupcakeButton.isSelected())
                f = cupcake;
            else if(broccoliButton.isSelected())
                f = broccoli;
            else if(potatoButton.isSelected())
                f = potato;
            pet3.feed(f);
            messageLabel.setText("You have fed " + pet3.getName() + " 1 " + f.getName());
            info = "<html>" + pet3.toString().replaceAll("\n", "<br>") + "</html>";
            statsLabel.setText(info);
            animationTimerSeconds = 0;
            isEating = true;
            petLabel.setIcon(imageEat);
            // Timer to show eating image for 2 seconds
            Timer animationTimer = new Timer(1000, new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    animationTimerSeconds++;
                    if(animationTimerSeconds >= 2)
                    {
                        if(pet3.getEnergyLevel() < 5 || pet3.getHappinessLevel() < 5)
                            petLabel.setIcon(imageSad);
                        else
                            petLabel.setIcon(imageHappy);
                        isEating = false;
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            animationTimer.start();
            animationTimerSeconds = -1;
        }
    }
   
    // Defines the action when the Play button is clicked
    class PlayButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Game g = coinToss;
            if(hoopButton.isSelected())
                g = hoopJumping;
            else if(guessButton.isSelected())
                g = guessingGame;
            if(pet3.play(g))
                messageLabel.setText("<html>You have played " + g.getName()+ " with<br>" + pet3.getName() + ", and won!</html>");
            else
                messageLabel.setText("<html>You have played " + g.getName()+ " with<br>" + pet3.getName() + ", and lost!</html>");
            info = "<html>" + pet3.toString().replaceAll("\n", "<br>") + "</html>";
            statsLabel.setText(info);
            animationTimerSeconds = 0;
            isPlaying = true;
            petLabel.setIcon(imagePlay);
            // Timer to show playing image for 2 seconds
            Timer animationTimer = new Timer(1000, new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    animationTimerSeconds++;
                    if(animationTimerSeconds >= 2)
                    {
                        if(pet3.getEnergyLevel() < 5 || pet3.getHappinessLevel() < 5)
                            petLabel.setIcon(imageSad);
                        else
                            petLabel.setIcon(imageHappy);
                        isPlaying = false;
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            animationTimer.start();
        }
    }
    
    // Defines the action when a New Pet from Activity 3 is clicked
    class NewPet2Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            try
            {
                String input = JOptionPane.showInputDialog("Enter your virtual pet's name:");
                VirtualPetGUIRunner init = new VirtualPetGUIRunner(input);
                frame.dispose();
            }
            catch(Exception err)
            {
                System.out.println(err);
            }
        }
    }
    
    // Defines the action when a New Pet from VirtualPet3 is clicked
    class NewPet3Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            try
            {
                String input = JOptionPane.showInputDialog("Enter your virtual pet's name:");
                VirtualPetGUIRunner3 init = new VirtualPetGUIRunner3(input);
                frame.dispose();
            }
            catch(Exception err)
            {
                System.out.println(err);
            }
        }
    }
    
    public static void main(String [] args) throws IOException
    {
        String input = JOptionPane.showInputDialog("Enter your virtual pet's name:");
        VirtualPetGUIRunner3 init = new VirtualPetGUIRunner3(input);
    }
}