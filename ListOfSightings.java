import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class presents a list of all properties in the chosen price range for a chosen
 * neighbourhood.
 *
 * @Ido Ben-zvi
 * @19.03.18
 */
public class ListOfSightings
{
    private Neighbourhood neighbourhood;
    private ArrayList<AirbnbListing> sightings;
    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JComboBox sortBox;

    /**
     * Constructor for objects of class ListOfSightings
     */
    public ListOfSightings(Neighbourhood neighbourhood)
    {
        this.neighbourhood = neighbourhood;
        sightings = neighbourhood.getListings();
        createWindow();
    }

    private void createWindow()
    {
        frame = new JFrame(neighbourhood.getName());
        panel = new JPanel(new GridLayout((sightings.size()-1)*2,1));
        frame.add(panel);

        dropDownMenu();
        createButtons(sightings);

        frameSettings();
    }

    private void frameSettings()
    {
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1400, 1400);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
        frame.pack();
    }

    private void createButtons(ArrayList<AirbnbListing> sightings)
    {
        //JScrollPane scrollPane = new JScrollPane();
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setPreferredSize(new Dimension(900,900));
        //scrollPane.setBounds(50, 30, 300, 50);
        //panel.add(scrollPane);

        for(AirbnbListing sighting : sightings){
            JButton button = new JButton("Host name: " + sighting.getHost_name() +
                    "  Minimum stay: " + sighting.getMinimumNights() + "  Price: " + 
                    sighting.getPrice() + "  Number of reviews: " + sighting.getNumberOfReviews());
            button.addActionListener( e -> popupWindow(sighting));
            button.setPreferredSize(new Dimension(200, 50));

            panel.add(button);
        }
    }

    private void popupWindow(AirbnbListing property)
    {
        JOptionPane.showMessageDialog(frame, property.description());
    }

    private void dropDownMenu()
    {
        JComboBox sortBox = new JComboBox();

        String price = "Price";
        String numReviews = "Reviews Number";
        String hostName = "Host Name";

        sortBox.addItem(price);
        sortBox.addItem(numReviews);
        sortBox.addItem(hostName);

        sortBox.addActionListener(e -> actionListener(sightings));

        panel.add(sortBox);
    }

    private void actionListener(ArrayList<AirbnbListing> properties)
    {
        Object s = (String) sortBox.getSelectedItem();

        if(s.equals("price")){
            sortByPrice(properties);
        }
        if(s.equals("numReviews")){
            sortByReviews(properties);
        }
        else if(s.equals("hostName")){
            System.out.println("Soon...");
        }

    }

    private void sortByPrice(ArrayList<AirbnbListing> properties)
    {
        ArrayList<AirbnbListing> newList = new ArrayList<>();
        while(properties.size()>0){
            AirbnbListing temp = properties.get(0);
            for(AirbnbListing property : properties){
                if (property.getPrice()<temp.getPrice()){
                    temp = property;
                }
            }
            newList.add(temp);
            properties.remove(temp);
        }
        sightings = newList;
        createWindow();
    }

    private void sortByReviews(ArrayList<AirbnbListing> properties)
    {
        ArrayList<AirbnbListing> newList = new ArrayList<>();
        while(properties.size()>0){
            AirbnbListing temp = properties.get(0);
            for(AirbnbListing property : properties){
                if (property.getNumberOfReviews()<temp.getNumberOfReviews()){
                    temp = property;
                }
            }
            newList.add(temp);
            properties.remove(temp);
        }
        sightings = newList;
        createWindow();
    }
}
