import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
/**
 * Write a description of class Neighbourhood here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Neighbourhood
{
    private String name;
    private ArrayList<AirbnbListing> listingsHere = new ArrayList<>();
    private int numberOfListings = 0;
    public Neighbourhood(String name)
    {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    
    public int getNoOfListings(){
        countListings();
        return listingsHere.size();
    }
    
    public void addListingHere(AirbnbListing l){
        listingsHere.add(l);       
    }
      
    public void countListings(){
        numberOfListings = listingsHere.size();
    }

    public ArrayList<AirbnbListing> getListings(){
        return listingsHere;
    }
    
    public int getAvgMinSpend(){
        int totalMinimumSpend = 0;
        
        for(AirbnbListing listing: listingsHere){
            totalMinimumSpend += listing.getPrice() * listing.getMinimumNights();
            
        }
        
        if(listingsHere.size() == 0){
            return 0;    
        }
        
        return (totalMinimumSpend/listingsHere.size()) ;  
    }
}
