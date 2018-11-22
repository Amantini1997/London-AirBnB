/**
 * A simple window providing infos about a houseRenter App
 *
 * @author Alessandro Amantini
 * @version 1
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePanel extends JPanel{
    //private JButton button; //button to proceed to the website
    private JLabel textLabel;
    private final static Color BACKGROUND_COLOR = Color.WHITE;

    /**
     * public constructor initializing all the contents
     */
    public WelcomePanel(){
        makeContainer();
        makeText();
        //makeButton();
    }

    /**
     * creates and initialize the container and its content
     */
    private void makeContainer(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(20,10,10,10,BACKGROUND_COLOR));
    }

    /**
     * create the text to put in the label.
     * this text will provide the user with some infos 
     * about the website
     */
    private void makeText(){
        String title = "<html>"
                    + "<font size = 20 color = red face = verdana><center>HOW TO USE THE APP</center></font><br><br><hr>"
                    + "<font size = 8 color = black><p>Here you can search for accommodation to rent in London. </p>"
                    + "<p>The app provides you with two boxes at the top to insert </p>"
                    + "<p>the price range that you prefer. Once you have inserted the </p>"
                    + "<p>prices, just click on the button \">\". An interactive and </p>"
                    + "<p>very simple map of the city will appear on the screen showing </p>"
                    + "<p>all the boroughs. Above each borough you can see a button, </p>"
                    + "<p>click on it to receive information about the associated </p>"
                    + "<p>borough. The buttons at the bottom also allow you to move </p>"
                    + "<p>towards all the panels that are, in order: instruction sheet, a </p>"
                    + "<p>map, statistics about the houses fitting your price range </p>"
                    + "<p>and [CHALLANGE PANEL].</p></font>"
                    + "</html>";
            
        JLabel titleLabel = new JLabel(title,SwingConstants.CENTER);
        titleLabel.setBackground(BACKGROUND_COLOR);
        titleLabel.setOpaque(true);
        add(titleLabel);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(new WelcomePanel());
        frame.setLocationRelativeTo(null);
    }
}
