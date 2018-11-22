/**
 * A panel containing two arrow-buttons 
 * pointing, respectively from left to right,
 * back and forward.
 *
 * @author Alessandro Amantini
 * @version 1
 */

import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;

public class ArrowPanel extends JPanel
{
    private JButton forward = new JButton(">");
    private JButton back = new JButton("<"); 
    private final static Color BACKGROUND_COLOR = Color.WHITE; //default background colour
    /**
     * public constructor
     */
    public ArrowPanel()
    {
        super(new BorderLayout());
        createPanel();
        addTipsTexts();
    }

    /**
     * initialize the panel
     */
    private void createPanel(){
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(BACKGROUND_COLOR);

        //setting back-arrow
        back.setEnabled(false);
        back.setPreferredSize(new Dimension(60, 20));
        innerPanel.add(back, BorderLayout.LINE_START);

        //adding space between the buttons
        Box.createHorizontalGlue();

        //setting forward-arrow
        forward.setEnabled(false);
        forward.setPreferredSize(new Dimension(60,20));
        innerPanel.add(forward, BorderLayout.LINE_END);

        add(innerPanel);
        setBorder(BorderFactory.createMatteBorder(10,10,10,10,BACKGROUND_COLOR));
    }
    
    /** 
     * add toolTipsText to components
     */
    private void addTipsTexts(){
        forward.setToolTipText("Next panel");
        back.setToolTipText("Previous panel");
    }

    public JButton getForwardButton(){
        return forward;
    }

    public JButton getBackButton(){
        return back;
    }
    
    /**
     * main method to test and see what the panel looks like
     */
    public static void main(String[] args){
        ArrowPanel ap = new ArrowPanel();
        JFrame frame = new JFrame();
        
        frame.add(ap);
        frame.pack();
        frame.setVisible(true);
    }
}
