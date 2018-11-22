/**
 * Creates the range panel, that is, a
 * string on the left pushing an error when floor price is higher then 
 * the ceiling one, and 2 JComboBoxes on the right to select respectively
 * the floor and the ceiling prices.
 * 
 * @author Alessandro Amantini
 * @version 1
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class PricePanel extends JPanel 
{
    private JLabel range; //label containing whether the range is correct
    private JComboBox fromBox; //floor price box
    private JComboBox toBox; //ceiling price box
    private final static Color BACKGROUND_COLOR = Color.WHITE; //default background colour

    /**
     * public constructor
     */
    public PricePanel(){
        super(new BorderLayout());
        createPanel();
        addTipsTexts();
    }

    /**
     * method initializing the panel itself
     */
    private void createPanel(){
        String rangeString = "Define your range of price";
        range = new JLabel(rangeString);

        setBackground(BACKGROUND_COLOR);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(1,4));
        innerPanel.setBackground(BACKGROUND_COLOR);

        // floor price box
        fromBox = new JComboBox();
        innerPanel.add(new JLabel("      From"));
        innerPanel.add(fromBox);

        // ceiling price box
        toBox = new JComboBox();
        innerPanel.add(new JLabel("          To"));
        innerPanel.add(toBox);

        innerPanel.add(range);
        setPreferredSize(new Dimension(350,45));
        add(innerPanel, BorderLayout.EAST);
        add(range, BorderLayout.WEST);
        setBorder(BorderFactory.createMatteBorder(10,10,10,10,BACKGROUND_COLOR));
    }
    
    /**
     * add a list of prices to fromBox and toBox from 0 to gap*cycles
     * 
     * @param gap The difference between one price and the next
     * @param cycles Number of loop repetitions
     */
    public void setPrices(int gap,int cycles){// from 0 to 7000 increasing by 25 each step
        for(int i = 0;i<=cycles;i++){
            int toAdd = i*gap;
            fromBox.addItem(toAdd);
            toBox.addItem(toAdd);
        }
    }

    /** 
     * add toolTipsText to components
     */
    private void addTipsTexts(){
        fromBox.setToolTipText("Choose the minimum price");
        toBox.setToolTipText("Choose the maximum price");
    }

    public JComboBox getFromBox(){
        return fromBox;
    }

    public JComboBox getToBox(){
        return toBox;
    }

    public int getFromPrice(){
        return (int) fromBox.getSelectedItem();
    }

    public int getToPrice(){
        return (int) toBox.getSelectedItem();
    }

    /**
     * set the string to be shown on the left
     * 
     * @param text
     */
    public void setText(String text){
        range.setText(text);
    }

    /**
     * main method testing the panel and showing what it looks like
     */
    public static void main(String args[]){
        JFrame frame = new JFrame();
        PricePanel pp = new PricePanel();
        frame.add(pp);

        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
