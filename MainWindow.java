/**
 * Main window containing all the panels.
 * From here the user can move through all the panels
 * and select the renge price they desire.
 *
 * @author Alessandro Amantini
 * @version 1 16-03-2018
 */ 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainWindow {
    private JFrame frame; //main and only frame
    private JPanel mainPanel; //frame content pane
    
    //main panel's panels
    private PricePanel pricePanel; //panel to insert price range
    private MiddlePanel middlePanel; //panel in the middle
    private ArrowPanel arrowPanel; //panel with arrows back and forward
    
    //middle panel's utilities
    private ArrayList<JPanel> panels; //collection of all the panels contained in middlePanel
    private int pointer; //pointer to the current panel of panels arrayList
    
    //middle panel's panels
    private WelcomePanel welcomePanel; //welcome panel
    private MapPanel mapPanel; //panel containing the map of London
    private StatisticsPanel statsPanel; //statistic panel
    private ChallengePanel challengePanel; //challenge panel
    
    //bnb lists
    private AirbnbDataLoader dl; // AirbnbDataLoader object
    private ArrayList<AirbnbListing> bnbList;//collection of bnbs
    private ArrayList<AirbnbListing> bnbListFiltered; //collection of bnbs filtered by price range
    private ArrayList<Neighbourhood> filteredNeighbourhoods; //collection of filtered neighbourhoods generated after price filtering of listings
    
    //costants
    private final static String STRING_RANGE_PREFIX = "Range: "; //prefix for the string output on the top-left
    private final static Color BACKGROUND_COLOR = Color.WHITE; //default background colour

    /**
     * public constructor to initialize the window
     */
    public MainWindow(){
        frame = new JFrame("London Property Marketplace");
        makeBnbList();
        //setting the contents
        contentSettings();
        activateMainWindow();

        frameSettings();
    }

    /**
     * Initialize bnb list
     */
    private void makeBnbList(){
        //initialing and filling up the collection with all the properties
        dl = new AirbnbDataLoader(); 
        bnbListFiltered = new ArrayList<>();
        bnbList = dl.load();
        filteredNeighbourhoods = new ArrayList<>();
    }

    /**
     * define all the settings for the frame.
     */
    private void frameSettings(){
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);
        frame.setBackground(BACKGROUND_COLOR);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true); //change to false later, needs to be true to work on lab computers
    }

    /**
     * Initialize mainPanel and setting the background colour
     */
    private void contentSettings(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
    }

    /**
     * Window to move through the GUI. activated after welcomeWindow gets
     * closed.
     */
    private void activateMainWindow(){
        //building the gui adding the components.
        createPricePanel();

        createMiddlePanel();

        createArrowPanel();

        frame.add(mainPanel);
        frame.setSize(new Dimension(1119,990));
        frame.setLocationRelativeTo(null);
        //refreshing the page
        refresh();
    }

    /**
     * update the page. to use after a component is either added or removed
     */
    private void refresh(){
        frame.validate();
        frame.repaint();
    }

    /**
     * initialize the pricePanel and put it into the mainPanel
     */
    private void createPricePanel(){
        pricePanel = new PricePanel();

        //set prices from 0 to 7000
        pricePanel.setPrices(25,280);

        pricePanel.getFromBox().addActionListener(e -> priceCheck());
        pricePanel.getToBox().addActionListener(e -> priceCheck());
        mainPanel.add(pricePanel,BorderLayout.NORTH);
    }

    /**
     * initialize the middlePanel and put it into the mainPanel.
     */
    private void createMiddlePanel(){
        middlePanel = new MiddlePanel();

        makePanelsList();
        
        //first content automatically shown in the middlePanel
        middlePanel.innerAdd(welcomePanel);

        mainPanel.add(middlePanel);
    }

    /**
     * initialize all the panels to be shown in the middlePanel, and put them into
     * an arrayList setting them as not visible.
     */
    private void makePanelsList(){
        panels = new ArrayList<>();

        //initializing the collection of panels
        welcomePanel = new WelcomePanel();
        mapPanel = new MapPanel(filteredNeighbourhoods);
        statsPanel = new StatisticsPanel(bnbListFiltered, filteredNeighbourhoods);
        challengePanel = new ChallengePanel();
        

        panels.add(welcomePanel);
        panels.add(mapPanel);
        panels.add(statsPanel);
        panels.add(challengePanel);

        pointer = 0;
    }

    /**
     * initialize the arrowPanel and put it into the mainPanel
     */
    private void createArrowPanel(){
        arrowPanel = new ArrowPanel();

        arrowPanel.getForwardButton().addActionListener(e -> nextPanel(1));
        arrowPanel.getBackButton().addActionListener(e -> nextPanel(-1));
        mainPanel.add(arrowPanel,BorderLayout.SOUTH); 
    }

    /**
     * Prints the list of neighbourhoods at the specified price range 
     */
    public void listNeighbourhoods(){
        System.out.println(filteredNeighbourhoods.size() + "");
        for(Neighbourhood n: filteredNeighbourhoods){
            System.out.println(n.getName());
            System.out.println(n.getNoOfListings() + "");
        } 
    }  

    /**
     * Check the difference between floor and ceiling prices, in case
     * prints out an error.
     */
    private void priceCheck(){
        int fromPrice = pricePanel.getFromPrice();
        int toPrice = pricePanel.getToPrice();
        boolean backFlag = false;
        boolean forwardFlag = false;
        if(fromPrice>toPrice){
            pricePanel.setText("ERROR: Ceiling price must be higher than floor price.");
            arrowPanel.getForwardButton().setEnabled(false);
            arrowPanel.getBackButton().setEnabled(false);
        }else{
            pricePanel.setText(STRING_RANGE_PREFIX + fromPrice + " - " + toPrice);
            setList();
            //listNeighbourhoods(); //uncomment to use to check neighbourhoods
            mapPanel.setNewFilter(filteredNeighbourhoods);
            enableArrows();
        }
        refresh();
    }

    /**
     * uplaoding all the bnbs in the list, and the filtering those not
     * respectring the price range
     */
    private void setList(){
        bnbListFiltered = bnbList.stream()
        .filter(s -> s.getPrice()>=pricePanel.getFromPrice())
        .filter(s -> s.getPrice()<=pricePanel.getToPrice())
        .collect(Collectors.toCollection(ArrayList::new));
        fillNeighbourhoods();
        statsPanel.updatePropertiesAndNeighbourhoods(bnbListFiltered, filteredNeighbourhoods);


        System.out.println("FILTERED " + bnbListFiltered.size() + " ALL " + bnbList.size());

    } 

    /**
     * populates the collections of neighbourhoods  given the price range
     */
    private void fillNeighbourhoods(){
        filteredNeighbourhoods.clear();
        ArrayList<String> known = new ArrayList<>();
        for(AirbnbListing l: bnbListFiltered){
            if(known.contains(l.getNeighbourhood())){
                Neighbourhood h = getNHoodFromString(l.getNeighbourhood());
                h.addListingHere(l);
            }
            if(!known.contains(l.getNeighbourhood())){
                Neighbourhood h = new Neighbourhood(l.getNeighbourhood());
                h.addListingHere(l);
                known.add(l.getNeighbourhood());
                filteredNeighbourhoods.add(h);
            }

        }

        for(Neighbourhood n: filteredNeighbourhoods){
            n.countListings();
        }

    }

    /**
     * Returns the Neighbourhood which has the same name as the provided string
     * @param name, string to search for
     * @return neighbourhood with name equal to name
     */
    private Neighbourhood getNHoodFromString(String name){
        for(Neighbourhood nh: filteredNeighbourhoods){
            if(nh.getName().equals(name)){
                return nh;
            }
        } 
        return null;
    }

    /**
     * changes the panel showed in the middle panel. In order to allow both the arrow buttons to call this
     * method, it takes an int. if the int is positive, forward arrow has been pressed, else back arrow has.
     * this method works on the arrayList(containing all the middle panel).
     * 
     * @param i arrow reference
     */
    private void nextPanel(int i){ 
        middlePanel.innerRemoveAll(); 
        if(i>0){
            if(pointer<panels.size()-1){
                pointer++;
            }
        }else{
            if(pointer>0){
                pointer--;
            }
        }
        middlePanel.innerAdd(panels.get(pointer));
        enableArrows();

        refresh();
    }
    
    /**
     * Check the pointer for panels, if it reached the end or the top of the list,
     * rspectively forward and back buttons are disabled, else enabled.
     */
    private void enableArrows(){

        //if no more rightward panels exist, forward button is disabled
        boolean forwardFlag = false;
        if(pointer < panels.size()-1){
            forwardFlag = true;
        }
        
        //if no more leftward panels exist, leftward button is disabled
        boolean backFlag = false;
        if(pointer > 0){
            backFlag = true;
        }
        
        arrowPanel.getBackButton().setEnabled(backFlag);
        arrowPanel.getForwardButton().setEnabled(forwardFlag);
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }
}
