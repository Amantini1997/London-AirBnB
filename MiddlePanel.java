
/**
 * Panel containing two lines(at the top and the bottom) non-resizable
 * and a middle JPanel
 *
 * @author Alessandro Amantini
 * @version 1 16-03-2018
 */
 
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import java.util.ArrayList;

public class MiddlePanel extends JPanel
{
    private JPanel innerPanel; //panel containing map, stats and so on
    private final static Color BACKGROUND_COLOR = Color.WHITE; //default background colour
    
    /**
     * public constructor
     */
    public MiddlePanel()
    {
        //we use the BorderLayout so that the lines won't get stretched
        super(new BorderLayout());
        createMiddlePanel();
    }
    
    /**
     * Initialize the two lines and the panel.
     * the method is provided with a component to put into the panel.
     */
    private void createMiddlePanel(){
        innerPanel = new JPanel(new CardLayout());
        
        //adding lines adn center scrollPanel
        add(line(Color.BLACK),BorderLayout.SOUTH);
        add(innerPanel,BorderLayout.CENTER);
        add(line(Color.BLACK),BorderLayout.NORTH);
        
        //putting in map and stats panels 
        /*scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setPreferredSize(new Dimension(900,900));//.setBounds(50, 30, 300, 50);*/
        
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Create a black line, put it into a panel,
     * and then add the panel to the content pane
     * 
     * @param color The color of the line
     */
    private Component line(Color color){
        JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
        line.setBackground(color);
        line.setPreferredSize(new Dimension(getWidth(),2));
        return line;
    }
    
    /**
     * adding the provided component, not to the Panel as a class, but to an inner panel
     * contained in the Panel class itself.
     * 
     * @param component to add in the panel
     */
    public void innerAdd(JComponent component){
        innerPanel.add(component);
    }
    
    /**
     * remove all the components, not from the Panel as a class, but from an inner panel
     * contained in the Panel class itself.
     */
    public void innerRemoveAll(){
        innerPanel.removeAll();
    }
    
    /**
     * main to test the panel and see how it looks like.
     * a button composes the scroll panel.
     */
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        JButton button = new JButton("PRESS ME");
        button.setMinimumSize(new Dimension(40,40));
        MiddlePanel mp = new MiddlePanel();
        cp.add(mp);
        frame.setVisible(true);
        frame.pack();
    }
}
