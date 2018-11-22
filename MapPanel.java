import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

/**
 * Logic behind the map panel
 *
 * @author William Boulton
 * @version 22-03-18
 */
public class MapPanel  extends JPanel
{
    private AirbnbDataLoader dl = new AirbnbDataLoader();
    private ArrayList<Neighbourhood> hoods = new ArrayList<>();
    private int numberOfNeighbourhoods = 0;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private HashMap<JButton, Neighbourhood> buttonHood = new HashMap<>();
  
    private static final int ICON_SIZE = 10;
    private static final int MAX_SIZE = 60;
    public MapPanel(ArrayList<Neighbourhood> hoods){
        makeContainer();
        this.hoods = hoods;
        makeButtons();
        setSize(1100,849);
    }

    /**
     * Filters Lists of listitngs and neighbourhoods according to the price range chosen
     * @param min the lowest price possible
     * @param max the max price possible
     */
    public void setNewFilter(ArrayList<Neighbourhood> neighbourhoods){
        for(JButton b: buttons){
            remove(b);
        } 
        this.hoods = null;
        this.hoods = neighbourhoods;
        makeButtons();
        revalidate();
        repaint();
    }

    /**
     * Apply icons to the buttons and set their sizes
     */
    private void formatButtons(){
        for(JButton j: buttons){
            try {
                Neighbourhood n = buttonHood.get(j);
                double xpos = j.getBounds().x;
                int x = (int) xpos;
                double ypos = j.getBounds().y;
                int y = (int) ypos;
                int listingNumber = n.getNoOfListings();
                int scale = 0;
                if(ICON_SIZE + listingNumber/20 > MAX_SIZE){
                    scale = MAX_SIZE;
                }
                else {
                    scale = ICON_SIZE + listingNumber/20;
                }
                j.setSize(scale,scale);
                Image img = ImageIO.read(getClass().getResource("map-marker-icon.png"));
                Image scaled = img.getScaledInstance(scale,scale,Image.SCALE_SMOOTH);
                ImageIcon icon =new ImageIcon(scaled);
                j.setIcon(icon);
                j.setOpaque(false);
                j.setContentAreaFilled(false);
                j.setBorderPainted(false);
                j.setLocation(x - (scale/2), y-(scale));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * 
     */
    public int getNumOfHoods(){
        return numberOfNeighbourhoods;
    }

    /**
     * creates and initialize the container and its content
     */
    private void makeContainer(){
        setLayout(null);
    }

    /**
     * makes a button for every neighbourhood in the collection of neighbourhoods
     */
    public void makeButtons(){
        buttons.clear();
        for(Neighbourhood h: hoods){
            JButton button = new JButton();
            switch (h.getName()){
                case "Kingston upon Thames":
                button.setBounds(302,630,ICON_SIZE,ICON_SIZE);
                break;
                case "Croydon":
                button.setBounds(578,683,ICON_SIZE,ICON_SIZE);
                break;
                case "Bromley":
                button.setBounds(748,734,ICON_SIZE,ICON_SIZE);
                break;
                case "Hounslow":
                button.setBounds(174,506,ICON_SIZE,ICON_SIZE);
                break;
                case "Ealing":
                button.setBounds(242,362,ICON_SIZE,ICON_SIZE);
                break;
                case "Havering":
                button.setBounds(941,334,ICON_SIZE,ICON_SIZE);
                break;
                case "Hillingdon":
                button.setBounds(119,365,ICON_SIZE,ICON_SIZE);
                break;
                case "Harrow":
                button.setBounds(247,259,ICON_SIZE,ICON_SIZE);
                break;
                case "Brent":
                button.setBounds(357,307,ICON_SIZE,ICON_SIZE);
                break;
                case "Barnet":
                button.setBounds(405,168,ICON_SIZE,ICON_SIZE);
                break;
                case "Enfield":
                button.setBounds(550,99,ICON_SIZE,ICON_SIZE);
                break;
                case "Waltham Forest":
                button.setBounds(646,238,ICON_SIZE,ICON_SIZE);
                break;
                case "Redbridge":
                button.setBounds(760,256,ICON_SIZE,ICON_SIZE);
                break;
                case "Sutton":
                button.setBounds(459,701,ICON_SIZE,ICON_SIZE);
                break;
                case "Lambeth":
                button.setBounds(528,515,ICON_SIZE,ICON_SIZE);
                break;
                case "Southwark":
                button.setBounds(582,466,ICON_SIZE,ICON_SIZE);
                break;
                case "Lewisham":
                button.setBounds(647,507,ICON_SIZE,ICON_SIZE);
                break;
                case "Greenwich":
                button.setBounds(738,483,ICON_SIZE,ICON_SIZE);
                break;
                case "Bexley":
                button.setBounds(847,506,ICON_SIZE,ICON_SIZE);
                break;
                case "Richmond upon Thames":
                button.setBounds(256,545,ICON_SIZE,ICON_SIZE);
                break;
                case "Merton":
                button.setBounds(427,595,ICON_SIZE,ICON_SIZE);
                break;
                case "Wandsworth":
                button.setBounds(473,538,ICON_SIZE,ICON_SIZE);
                break;
                case "Hammersmith and Fulham":
                button.setBounds(409,438,ICON_SIZE,ICON_SIZE);
                break;
                case "Kensington and Chelsea":
                button.setBounds(430,408,ICON_SIZE,ICON_SIZE);
                break;
                case "City of London":
                button.setBounds(550,380,ICON_SIZE,ICON_SIZE);
                break;
                case "Westminster":
                button.setBounds(480,395,ICON_SIZE,ICON_SIZE);
                break;
                case "Camden":
                button.setBounds(479,300,ICON_SIZE,ICON_SIZE);
                break;
                case "Tower Hamlets":
                button.setBounds(642,384,ICON_SIZE,ICON_SIZE);
                break;
                case "Islington":
                button.setBounds(539,317,ICON_SIZE,ICON_SIZE);
                break;
                case "Hackney":
                button.setBounds(590,300,ICON_SIZE,ICON_SIZE);
                break;
                case "Haringey":
                button.setBounds(532,238,ICON_SIZE,ICON_SIZE);
                break;
                case "Newham":
                button.setBounds(721,383,ICON_SIZE,ICON_SIZE);
                break;
                case "Barking and Dagenham":
                button.setBounds(836,324,ICON_SIZE,ICON_SIZE);
                break;

            }
            buttonHood.put(button,h);
            buttons.add(button);

            button.addActionListener(l ->{ 
                    new ListOfSightings(h);
                }); 
        }
        for(JButton b: buttons){
            add(b);
        }
        formatButtons();
    } 

    /**
     * Overrides Component's paintComponent method to place the map image as the background of the MapPanel class
     */
    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("final_map.png"));
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        } catch (IOException e) {
            System.out.println("image load failed");
        }
    }
}
