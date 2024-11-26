

 /* 
 * Name: Daniella Toth
 * Student ID: 1261398
 * Compile Command: javac DiscussionBoard.java User.java Post.java DiscussionBoardGUI.java
 * Run Command: java DiscussionBoardGUI
 */

 import ePortfolio.Investment;
 import ePortfolio.Stock;
 import ePortfolio.MutualFund;
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class ePortfolioGUI extends JFrame {
     public static final int WIDTH = 700;
     public static final int HEIGHT = 500;
 
     private ArrayList<Investment> investments;
     private HashMap<String, ArrayList<Investment>> mapKeyword;
     private CardLayout cardLayout;
     private JPanel panel;
 
     public ePortfolioGUI() {
         investments = new ArrayList<>();
         mapKeyword = new HashMap<>();
         initializeGUI();
     }
 
     public static void main(String[] args) {
         ePortfolioGUI frame = new ePortfolioGUI();
         frame.setVisible(true);
     }
 
     private void initializeGUI() {
         setTitle("ePortfolio");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(WIDTH, HEIGHT);
         setLayout(new BorderLayout());
 
         cardLayout = new CardLayout();
         panel = new JPanel(cardLayout);
 
         // Add panels to the CardLayout
         panel.add(createMainPanel(), "MAIN");
         panel.add(createBuyPanel(), "BUY");
         panel.add(createSellPanel(), "SELL");
         panel.add(createSearchPanel(), "SEARCH");
 
         add(panel, BorderLayout.CENTER);
         setJMenuBar(createMenuBar());
     }
 
     private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Stack components vertically
    
        JLabel welcomeLabel = new JLabel("<html><br><br>Welcome to ePortfolio.<html>");
        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.PLAIN, 20));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0)); // add 20px padding on the left and 10px padding below
    
        JLabel instructionLabel = new JLabel("<html><br><br>Choose a command from the “Commands” menu to buy or sell<br>" + 
                                             "an investment, update prices for all investments, get gain for the<br>" + 
                                             "portfolio, search for relevant investments, or quit the program.<html>");
        instructionLabel.setFont(new Font(instructionLabel.getFont().getName(), Font.PLAIN, 20));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0)); // add 20px padding on the left and 10px padding below
    
        // Center align the labels
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        // Add labels with a space in between
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add 20px vertical space
        mainPanel.add(instructionLabel);
    
        return mainPanel;
    }
    
 
     private JPanel createBuyPanel() {
         JPanel buyPanel = new JPanel();
         buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));
         buyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
         JLabel titleLabel = new JLabel("Buy an Investment");
         titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
         buyPanel.add(titleLabel);
 
         // Add fields for investment details
         buyPanel.add(new JLabel("Type:"));
         JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});
         buyPanel.add(typeComboBox);
 
         buyPanel.add(new JLabel("Symbol:"));
         JTextField symbolField = new JTextField();
         buyPanel.add(symbolField);
 
         buyPanel.add(new JLabel("Name:"));
         JTextField nameField = new JTextField();
         buyPanel.add(nameField);
 
         buyPanel.add(new JLabel("Quantity:"));
         JTextField quantityField = new JTextField();
         buyPanel.add(quantityField);
 
         buyPanel.add(new JLabel("Price:"));
         JTextField priceField = new JTextField();
         buyPanel.add(priceField);
 
         // Add action buttons
         JButton resetButton = new JButton("Reset");
         JButton buyButton = new JButton("Buy");
         JPanel buttonPanel = new JPanel(new FlowLayout());
         buttonPanel.add(resetButton);
         buttonPanel.add(buyButton);
 
         buyPanel.add(buttonPanel);
 
         // Action listeners
         resetButton.addActionListener(e -> {
             symbolField.setText("");
             nameField.setText("");
             quantityField.setText("");
             priceField.setText("");
         });
 
         buyButton.addActionListener(e -> {
             try {
                 String type = (String) typeComboBox.getSelectedItem();
                 String symbol = symbolField.getText();
                 String name = nameField.getText();
                 int quantity = Integer.parseInt(quantityField.getText());
                 double price = Double.parseDouble(priceField.getText());
 
                 if (quantity <= 0 || price <= 0) {
                     throw new IllegalArgumentException("Quantity and price must be positive.");
                 }
 
                 Investment investment;
                 if (type.equals("Stock")) {
                     investment = new Stock(symbol, name, quantity, price, false);
                 } else {
                     investment = new MutualFund(symbol, name, quantity, price, false);
                 }
 
                 investments.add(investment);
                 JOptionPane.showMessageDialog(this, "Investment bought successfully!");
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and price.", "Input Error", JOptionPane.ERROR_MESSAGE);
             } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
             }
         });
 
         return buyPanel;
     }
 
     private JPanel createSellPanel() {
         JPanel sellPanel = new JPanel();
         sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS));
         sellPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         sellPanel.add(new JLabel("Sell Panel (To be implemented)"));
         return sellPanel;
     }
 
     private JPanel createSearchPanel() {
         JPanel searchPanel = new JPanel();
         searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
         searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         searchPanel.add(new JLabel("Search Panel (To be implemented)"));
         return searchPanel;
     }
 
     private JMenuBar createMenuBar() {
         JMenuBar menuBar = new JMenuBar();
         JMenu options = new JMenu("Commands");
         options.setFont(new Font(options.getFont().getName(), Font.PLAIN, 20));
         options.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // add 20px padding on the left and 10px padding below
 
         JMenuItem buyItem = new JMenuItem("Buy");
         JMenuItem sellItem = new JMenuItem("Sell");
         JMenuItem searchItem = new JMenuItem("Search Investments");
 
         buyItem.addActionListener(e -> cardLayout.show(panel, "BUY"));
         sellItem.addActionListener(e -> cardLayout.show(panel, "SELL"));
         searchItem.addActionListener(e -> cardLayout.show(panel, "SEARCH"));
 
         options.add(buyItem);
         options.add(sellItem);
         options.add(searchItem);
         menuBar.add(options);
 
         return menuBar;
     }
 }
 