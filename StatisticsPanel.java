import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * The statistics panel is responsible for displaying 4 statistics (at any one time, no two the same) based on the properties/listings from the selected price range.
 * It contains 4 statistic boxes that manage the core functionality of the panel, such as displaying the statistic, updating the statistic and changing the type of the 
 * current statistic.
 *
 * @author David Mahgerefteh
 * @version 21/03/2018
 */
public class StatisticsPanel extends JPanel //Made into a JPanel so that it can easily be added to the main frame.
{
    private StatisticsBox statisticsBox1, statisticsBox2, statisticsBox3, statisticsBox4;
    private ArrayList<AirbnbListing> properties; //These are the properties/listings from the selected price range that statistics are based on.
    private ArrayList<Neighbourhood> neighbourhoods; //These are the neighbourhoods from the properties/listings from the selected price range.

    /**
     * Constructor for objects of class StatisticsPanel.
     * 
     * @param properties The collection of properties/listings based on the price range selected.
     * @param neighbourhoods The collection of neighbourhoods based on the price range selected.
     */
    public StatisticsPanel(ArrayList<AirbnbListing> properties, ArrayList<Neighbourhood> neighbourhoods)
    {
        this.properties = properties;
        this.neighbourhoods = neighbourhoods;
        makePanel();
    }

    /**
     * Manages the drawing/creation of the statistics panel by setting the layout and instantiating 4 statistic boxes.
     */
    public void makePanel()
    {
        setLayout(new GridLayout(2, 2));
        statisticsBox1 = new StatisticsBox("Average Number of Reviews", StatisticsBox.getAverageNumberOfReviews(properties) , 0, properties, neighbourhoods);    //These are the default statistics shown.
        statisticsBox2 = new StatisticsBox("Total Number of Available Properties", StatisticsBox.getNumberOfProperties(properties), 1, properties, neighbourhoods);
        statisticsBox3 = new StatisticsBox("Number of Entire Homes and Apartments", StatisticsBox.getNumberOfEntireHomesAndAppartments(properties), 2, properties, neighbourhoods);
        statisticsBox4 = new StatisticsBox("Priciest Neighbourhood", StatisticsBox.getPriciestNeighbourhood(neighbourhoods), 3, properties, neighbourhoods);

        add(statisticsBox1);
        add(statisticsBox2);
        add(statisticsBox3);
        add(statisticsBox4);
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
        statisticsBox1.updatePropertiesAndNeighbourhoods(properties, neighbourhoods);
        statisticsBox2.updatePropertiesAndNeighbourhoods(properties, neighbourhoods);
        statisticsBox3.updatePropertiesAndNeighbourhoods(properties, neighbourhoods);
        statisticsBox4.updatePropertiesAndNeighbourhoods(properties, neighbourhoods);
    }
}