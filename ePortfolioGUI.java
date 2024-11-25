import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import ePortfolio.Investment;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font; 
import java.util.ArrayList;
import java.util.HashMap;



/*
 * RESOURCES I USED FOR THIS ASSIGNMENT:
 * https://www.youtube.com/watch?app=desktop&v=5o3fMLPY7qY
 * https://www.google.com/search?q=rgb+color+picker&rlz=1C5CHFA_enCA1059CA1066&oq=rgb+col&gs_lcrp=EgZjaHJvbWUqDQgAEAAYgwEYsQMYgAQyDQgAEAAYgwEYsQMYgAQyBwgBEAAYgAQyBwgCEAAYgAQyBwgDEAAYgAQyBggEEEUYOTIHCAUQABiABDIHCAYQABiABDIHCAcQABiABDIHCAgQABiABDIHCAkQABiABNIBCDMwMTNqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8
 * https://docs.oracle.com/javase/tutorial/uiswing/ 
 * https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
 */

// class that creates the GUI for the ePortfolio
public class ePortfolioGUI extends JFrame {
    public static final int WIDTH = 700; // width of the frame
    public static final int HEIGHT = 500; // height of the frame

    private ArrayList<Investment> investments; // list of investments in the portfolio
    private HashMap<String, ArrayList<Investment>> mapKeyword; // map of keywords to investments

    
}
