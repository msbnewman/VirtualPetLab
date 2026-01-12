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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class VirtualPetGUIRunner 
{
    // CHANGE THIS VARIABLE VALUE TO TEST AT A DIFFERENT SPEED
    private final int INTERVAL_IN_SECONDS = 10;
    
    private String info;
    private int timerSeconds, animationTimerSeconds;
    private boolean isEating, isPlaying;
    private VirtualPet pet2;
    private ImageIcon imageHappy, imageSad, imageEat, imagePlay;
    private JFrame frame;
    private JPanel petPanel, menuPanel, mainPanel, statsPanel, timerPanel;
    private JLabel petLabel,statsLabel, messageLabel, timerLabel;
    private JButton foodButton, playButton;
    private JMenu newPetMenu;
    private JMenuItem petRunner3, petRunner4;
    private JMenuBar menuBar;
    
    public VirtualPetGUIRunner(String name) 
    {
        pet2 = new VirtualPet(name);
        isPlaying = isEating = false;
        
        // Automatically calls update after INTERVAL_IN_SECONDS time
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> { pet2.updateStatus(); }, INTERVAL_IN_SECONDS, INTERVAL_IN_SECONDS, TimeUnit.SECONDS);
        
        // Calls helper methods to initialize components of GUI
        initPetDisplayPanel();
        initTimerPanel();
        initStatsPanel();
        initButtons();
        fillLayout();
        initMenuBar();
        
        // Initializes the frame
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(350, 500));
        frame.setTitle("Virtual Pet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adds all components to the frame and makes visible
        frame.add(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }
    
    // Initializes the Feed and Play buttons and the panel that contains them
    public void initButtons()
    {
        GridBagConstraints c = new GridBagConstraints();
        
        foodButton = new JButton("Feed");
        foodButton.addActionListener(new FoodButtonListener());
        foodButton.setPreferredSize(new Dimension(80, 30));
        foodButton.setMaximumSize(new Dimension(80, 30));
        
        playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        playButton.setPreferredSize(new Dimension(80, 30));
        playButton.setMaximumSize(new Dimension(80, 30));
        
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); 
        menuPanel.add(Box.createVerticalStrut(50));
        menuPanel.add(foodButton, c);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(playButton, c);
        menuPanel.add(Box.createVerticalStrut(10));
    }
    
    // Initializes the panel that contains the information text
    public void initStatsPanel()
    {
        info = "" + pet2.toString().replaceAll("\n", "") + "";
        statsLabel = new JLabel(info);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 0));
        
        messageLabel = new JLabel("Happy Birthday, " + pet2.getName() + "!");
        messageLabel.setForeground(Color.blue);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 0));
        
        statsPanel = new JPanel(new GridLayout(2,1));
        statsPanel.add(messageLabel);
        statsPanel.add(statsLabel);
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
                info = "" + pet2.toString().replaceAll("\n", "") + "";
                statsLabel.setText(info);
                if(!isPlaying && !isEating)
                {
                    if((ImageIcon)petLabel.getIcon() != imageSad && (pet2.getEnergyLevel() < 5 || pet2.getHappinessLevel() < 5))
                        petLabel.setIcon(imageSad);
                    else if((ImageIcon)petLabel.getIcon() != imageHappy && (pet2.getEnergyLevel() >= 5 && pet2.getHappinessLevel() >= 5))
                        petLabel.setIcon(imageHappy);
                }
            }
        });
        timer.start();
        timerPanel = new JPanel();
        timerPanel.add(timerLabel);
        timerPanel.setLayout(new GridBagLayout());
    }
    
    // Initializes the Menu Bar that allows you to create a new Virtual Pet
    public void initMenuBar()
    {
        petRunner3 = new JMenuItem("Activity 2");
        petRunner3.addActionListener(new Newpet2Listener());
        petRunner4 = new JMenuItem("Activity 3");
        petRunner4.addActionListener(new NewPet4Listener());
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
        c.weighty = 70;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(petPanel, c);
        c.weightx = 40;
        c.gridx = 1;
        mainPanel.add(menuPanel, c);
        c.weightx = 60;
        c.weighty = 30;
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
            pet2.feed();
            messageLabel.setText("You have fed " + pet2.getName());
            info = "" + pet2.toString().replaceAll("\n", "") + "";
            statsLabel.setText(info);
            animationTimerSeconds = 0;
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
                        if(pet2.getEnergyLevel() < 5 || pet2.getHappinessLevel() < 5)
                            petLabel.setIcon(imageSad);
                        else
                            petLabel.setIcon(imageHappy);
                        isEating = false;
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            animationTimer.start();
        }
    }
    
    // Defines the action when the Play button is clicked
    class PlayButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            pet2.play();
            messageLabel.setText("You have played with " + pet2.getName());
            info = "" + pet2.toString().replaceAll("\n", "") + "";
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
                        if(pet2.getEnergyLevel() < 5 || pet2.getHappinessLevel() < 5)
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
    
    // Defines the action when a New Pet from Activity 2 is clicked
    class Newpet2Listener implements ActionListener
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
    
    // Defines the action when a New Pet from Activity 3 is clicked
    class NewPet4Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
		// "Uncomment" code when you complete Activity 3 and include
		// VirtualPetGUIRunner3 in your project
		/*
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
		*/
        }
    }
    
    public static void main(String [] args) throws IOException
    {
        String input = JOptionPane.showInputDialog("Enter your virtual pet's name:");
        VirtualPetGUIRunner init = new VirtualPetGUIRunner(input);
    }
}