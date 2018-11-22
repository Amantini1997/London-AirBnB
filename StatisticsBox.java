//TO DO: 
//1. Ido's extra statistic. 

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * 4 statistic boxes are contained within the statistics panel. Each box has functionalities to display the current statistic, to update the current statistic, and to change
 * the type of the current statistic. 
 * 
 * Statistics are based on the collection of properties/listings from the given price range as selected by the user. The same type of statistic cannot appear more than once.
 *
 * @author David Mahgerefteh
 * @version 21/03/2018
 */
public class StatisticsBox extends JPanel
{
    private static ArrayList<Integer> shownIndexes; //Current statistics visible to the user.    

    static {    //This block is executed just once out of all instances.
        shownIndexes = new ArrayList<Integer>();
        shownIndexes.add(0);    //Setting the default statistics that are automatically shown when the panel is first opened.
        shownIndexes.add(1);
        shownIndexes.add(2);
        shownIndexes.add(3);
    }

    private JLabel title;
    private JLabel statistic;
    private int index; //Numbering system to handle statistic changing.
    private ArrayList<AirbnbListing> properties; //These are the properties/listings from the selected price range that statistics are based on.
    private ArrayList<Neighbourhood> neighbourhoods; //These are the neighbourhoods from the properties/listings from the selected price range.

    /**
     * Constructor for objects of class StatisticsBox.
     * 
     * @param title The title of the statistic.
     * @param statistic The value of the statistic.
     * @param index The index of the statistic box.
     * @param properties The collection of properties/listings based on the price range selected.
     * @param neighbourhoods The collection of neighbourhoods based on the price range selected.
     */
    public StatisticsBox(String title, String statistic, int index, ArrayList<AirbnbListing> properties, ArrayList<Neighbourhood> neighbourhoods)
    {
        super(new BorderLayout());
        this.title = new JLabel(title);
        this.title.setFont(new Font("Arial", Font.BOLD, 20));
        this.statistic = new JLabel(statistic);
        this.statistic.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(Box.createVerticalGlue());

        textPanel.add(this.title);
        textPanel.add(Box.createRigidArea(new Dimension(0, 75)));
        textPanel.add(this.statistic);
        this.title.setAlignmentX(this.title.CENTER_ALIGNMENT);
        this.statistic.setAlignmentX(this.statistic.CENTER_ALIGNMENT);
        textPanel.add(Box.createVerticalGlue());

        JButton back = new JButton("<");
        back.setPreferredSize(new Dimension(65,65));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial", Font.BOLD, 27));
        JButton forward = new JButton(">");
        forward.setPreferredSize(new Dimension(65,65));
        forward.setBackground(Color.BLACK);
        forward.setForeground(Color.WHITE);
        forward.setFont(new Font("Arial", Font.BOLD, 27));
        back.addActionListener(e -> changeContents("back"));
        forward.addActionListener(e -> changeContents("forward"));
        this.add(back, BorderLayout.WEST);
        this.add(forward, BorderLayout.EAST);
        this.add(textPanel, BorderLayout.CENTER);

        this.index = index;
        this.properties = properties;
        this.neighbourhoods = neighbourhoods;
    }

    /**
     * Sets the title of the statistic.
     *
     * @param title The new title for the new statistic.
     */
    public void setTitle(String title)
    {
        this.title.setText(title);
    }

    /**
     * Sets the statistic.
     *
     * @param statistic The new value for the statistic.
     */
    public void setStatistic(String statistic)
    {
        this.statistic.setText(statistic);
    }

    /**
     * Manages the calling of the appropriate statistic method based on the index of the statistic box.
     */
    public void selectStatistic()
    {
        if(index == 0){
            setTitle("Average Number of Reviews");
            setStatistic(getAverageNumberOfReviews(properties));
        }
        else if(index == 1){
            setTitle("Total Number of Available Properties");
            setStatistic(getNumberOfProperties(properties));            
        }
        else if(index == 2){
            setTitle("Number of Entire Homes and Apartments");
            setStatistic(getNumberOfEntireHomesAndAppartments(properties));    
        }
        else if(index == 3){
            setTitle("Priciest Neighbourhood");
            setStatistic(getPriciestNeighbourhood(neighbourhoods));             
        } 
        else if(index == 4){
            setTitle("Minimum Nights Required");            //David Mahgerefteh's extra statistic.
            setStatistic(getMinimumNightsRequired(properties));
        }
        else if(index == 5){
            setTitle("Most Frequently Reviewed Property");  //Will Boulton's extra statistic.
            setStatistic(MostFrequentlyReviewed(properties));
        }
        else if(index == 6){
            setTitle("TEST3");  //Ido, set this to the title of your extra statistic. Please do not touch.
            setStatistic(idoExtra(properties));
        }
        else if(index == 7){
            setTitle("Properties with 365 Availablility");  //Alessandro Amantini's extra statistic.
            setStatistic(getAllYearAvailable(properties));
        } 
    }      

    /**
     * Updates the properties/listings/neighbourhoods that the statistics are based on when a new price range is selected.
     *
     * @param properties The new collection of properties/listings from the updated price range.
     * @param neighbourhoods The collection of neighbourhoods based on the price range selected.
     */
    public void updatePropertiesAndNeighbourhoods(ArrayList<AirbnbListing> properties, ArrayList<Neighbourhood> neighbourhoods)
    {
        this.properties = properties;
        this.neighbourhoods = neighbourhoods;
        selectStatistic();
    }    

    /**
     * The action listener for the back and forward buttons on each statistic box. Determines the new index/statistic to be shown.
     *
     * @param direction If the back or forward button has been pressed.
     */
    private void changeContents(String direction)
    {
        shownIndexes.remove(shownIndexes.indexOf(index));  //Remove the current index from shownIndexes as this statistic is no longer being shown.

        if(direction.equals("forward")){
            index = (index + 1) % 8; //Index goes from 0 to 7.
            while (shownIndexes.contains(index)) {  //Do not select this index/statistic if it is already shown, as in the requirements.
                index = (index + 1) % 8;
            }
            shownIndexes.add(index);
        }
        else{
            index--;
            if (index < 0){
                index = 7;
            }
            while (shownIndexes.contains(index)) {
                index--;
                if (index < 0){
                    index = 7;
                }
            }
            shownIndexes.add(index);
        }

        selectStatistic();  //Once an index is determined, output the corresponding statistic.
    }

    /**
     * Calculates the average number of reviews across all the properties/listings in the price range.
     *
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the average number of reviews across all the properties/listings in the price range.
     */
    public static String getAverageNumberOfReviews(ArrayList<AirbnbListing> properties)
    {
        if(properties.size() == 0){ //Cannot divide by 0.  
            return 0 + "";    
        }
        else{
            int numberOfReviews = 0;

            for(AirbnbListing listing: properties){
                numberOfReviews = numberOfReviews + listing.getNumberOfReviews();
            }

            int averageNumberOfReviews = numberOfReviews/(properties.size()); 

            return averageNumberOfReviews + "";
        }
    }

    /**
     * Calculates the number of available properties/listings from the given price range.
     *
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the number of available properties/listings.
     */

    public static String getNumberOfProperties(ArrayList<AirbnbListing> properties) 
    {
        return properties.size() + ""; 
    }

    /**
     * Calculates the number of available properties/listings from the given price range that are either entire homes or appartments.
     *
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the number of available properties/listings that are not of type "Private Room".
     */

    public static String getNumberOfEntireHomesAndAppartments(ArrayList<AirbnbListing> properties)
    {
        int numberOfEntireHomesAndAppartments = 0;

        for(AirbnbListing listing : properties){

            if(!listing.getRoom_type().equals("Private room")){
                numberOfEntireHomesAndAppartments++;
            }

        }
        return numberOfEntireHomesAndAppartments + "";
    }

    /**
     * Calculates the priciest neighbourhood, taking into account the minimum number of nights required in order to stay there.
     *
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the priciest neighbourhood.
     */
    public static String getPriciestNeighbourhood(ArrayList<Neighbourhood> neighbourhoods)
    {
        if(neighbourhoods.size() == 0){
            return "N/A";    
        }
        else{

            Neighbourhood max = neighbourhoods.get(0);

            for(Neighbourhood neighbourhood: neighbourhoods){
                if(neighbourhood.getAvgMinSpend() > max.getAvgMinSpend()){
                    max = neighbourhood;   
                }
            }

            return max.getName();
        }
    }

    /**
     * David Mahgerefteh's extra statistic.
     *
     * This calculates the minimum number of nights you must stay in order to be able to book any of the listings/properties out of the listings/properties from the price range.
     * 
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the minimum number of nights required in order to book a listing.
     */
    public static String getMinimumNightsRequired(ArrayList<AirbnbListing> properties)
    {
        if(properties.size() == 0){
            return "N/A";    
        }
        else{

            int minimumNightsRequired = Integer.MAX_VALUE;

            for(AirbnbListing listing: properties){
                if(listing.getMinimumNights() < minimumNightsRequired){
                    minimumNightsRequired = listing.getMinimumNights();
                }
            }

            return minimumNightsRequired + "";
        }
    }

    /**
     * Will Boulton's extra statistic.
     * 
     * Returns the most frequently reviewed property/listing.
     * 
     * @param properties The collection of properties/listings based on the price range selected.
     * @return the most frequently reviewed property/listing.
     */
    public static String MostFrequentlyReviewed(ArrayList<AirbnbListing> properties)
    {
        if(properties.size() == 0){
            return "N/A";    
        }
        else{

            AirbnbListing mostFrequentlyReviewed = properties.get(0);
            for(AirbnbListing listing: properties){
                if(listing.getReviewsPerMonth() > mostFrequentlyReviewed.getReviewsPerMonth()){
                    mostFrequentlyReviewed = listing;
                }
            }

            return mostFrequentlyReviewed.getName();
        }
    }

    /**
     * Ido Ben-Zvi's extra statistic. Please do not do this until I have spoken to you.
     * 
     * @param properties The collection of properties/listings based on the price range selected.
     * @return your statistic
     */
    public static String idoExtra(ArrayList<AirbnbListing> properties)
    {
        return "abc";
    }

    /**
     * Alessandro Amantini's extra statistic.
     * 
     * Return the total number of rooms available 365 days a year.
     * 
     * @param properties The collection of properties/listings based on the price range selected.
     * @return The number of room available throughout the year.
     */
    public static String getAllYearAvailable(ArrayList<AirbnbListing> properties)
    {
        int toReturn = (int) properties.stream()
            .filter(e-> e.getAvailability365()==365)
            .count();
        return String.valueOf(toReturn);
    }
}