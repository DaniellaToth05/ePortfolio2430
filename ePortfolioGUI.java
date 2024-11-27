

 /* 
 * Name: Daniella Toth
 * Student ID: 1261398
 * Compile Command: javac DiscussionBoard.java User.java Post.java DiscussionBoardGUI.java
 * Run Command: java DiscussionBoardGUI
 */

 import ePortfolio.Investment;
 import ePortfolio.Stock;
 import ePortfolio.MutualFund;
 import ePortfolio.Portfolio;
 
 import javax.swing.*;
 import java.awt.*;
 
 public class ePortfolioGUI extends JFrame {
     public static final int WIDTH = 700;
     public static final int HEIGHT = 500;
 
     private Portfolio portfolio; // Reference to your Portfolio class
     private JTextField symbolField, nameField, quantityField, priceField; // Input fields
     private JComboBox<String> typeComboBox; // Dropdown for investment type
     private CardLayout cardLayout;
     private JPanel panel;
     private JTextArea buyMessageArea;
     private JTextArea sellMessageArea;
     private JTextArea updateMessageArea;
     private JTextArea gainMessageArea;
     private JTextArea searchMessageArea;
 
     public ePortfolioGUI() {
         portfolio = new Portfolio(); // Initialize the Portfolio instance
         initializeGUI();
     }
 
     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             ePortfolioGUI frame = new ePortfolioGUI();
             frame.setVisible(true);
         });
     }
 
     private void initializeGUI() {
        setTitle("ePortfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
    
        // Initialize card layout and panel
        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);
    
        // Initialize all message areas early
        buyMessageArea = new JTextArea(8, 20);
        sellMessageArea = new JTextArea(8, 20);
        updateMessageArea = new JTextArea(8, 20); // Initialize here
        gainMessageArea = new JTextArea(8, 20);
        searchMessageArea = new JTextArea(8, 20);
    
        // Create all the panels
        panel.add(createMainPanel(), "MAIN");
        panel.add(createBuyPanel(), "BUY");
        panel.add(createSellPanel(), "SELL");
        panel.add(createUpdatePanel(), "UPDATE");
        panel.add(createGainPanel(), "GAIN");
        panel.add(createSearchPanel(), "SEARCH");
    
        // Add panels to the frame
        add(panel, BorderLayout.CENTER); // Main content area
        setJMenuBar(createMenuBar()); // Add menu bar
    }
    
 
     private JPanel createMainPanel() {
         JPanel mainPanel = new JPanel();
         mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
 
         JLabel welcomeLabel = new JLabel("<html><br><br>Welcome to ePortfolio.<html>");
         welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.PLAIN, 20));
         welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));
 
         JLabel instructionLabel = new JLabel("<html><br><br>Choose a command from the “Commands” menu to buy or sell<br>" +
                 "an investment, update prices for all investments, get gain for the<br>" +
                 "portfolio, search for relevant investments, or quit the program.<html>");
         instructionLabel.setFont(new Font(instructionLabel.getFont().getName(), Font.PLAIN, 20));
         instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));
 
         mainPanel.add(welcomeLabel);
         mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add 20px vertical space
         mainPanel.add(instructionLabel);
 
         return mainPanel;
     }
 
     