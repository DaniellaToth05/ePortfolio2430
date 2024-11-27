

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

// GUI class for ePortfolio
public class ePortfolioGUI extends JFrame {
    public static final int WIDTH = 700; // width of the frame
    public static final int HEIGHT = 500; // height of the frame

    // instance variables for the GUI
    private Portfolio portfolio; // portfolio object

    // text fields for user input
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField; 

    private JComboBox<String> typeComboBox; // combo box for investment type
    private CardLayout cardLayout; // card layout for the panels
    private JPanel panel; // panel for the cards

    // message areas for the panels (to display the error messages or other messages)
    private JTextArea buyMessageArea;
    private JTextArea sellMessageArea;
    private JTextArea updateMessageArea;
    private JTextArea gainMessageArea;
    private JTextArea searchMessageArea;

    // constructor for the GUI
    public ePortfolioGUI() {
        portfolio = new Portfolio(); 
        initializeGUI();
    }
 
    // main method to run the GUI
    public static void main(String[] args) {

        ePortfolioGUI frame = new ePortfolioGUI(); // create the frame

        frame.setVisible(true); // set the frame to be visible

    }
 
    // method to initialize the GUI components
    private void initializeGUI() {

        setTitle("ePortfolio"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit the program when the frame is closed
        setSize(WIDTH, HEIGHT); // set the size of the frame
        setLayout(new BorderLayout()); // set the layout of the frame
    
        // create the card layout and panel
        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);
    
        // create the message areas for each of the panels
        buyMessageArea = new JTextArea(8, 20);
        sellMessageArea = new JTextArea(8, 20);
        updateMessageArea = new JTextArea(8, 20); 
        gainMessageArea = new JTextArea(8, 20);
        searchMessageArea = new JTextArea(8, 20);
    
        // add each of the panels to the card layout so that they can be switched between
        panel.add(createMainPanel(), "MAIN");
        panel.add(createBuyPanel(), "BUY");
        panel.add(createSellPanel(), "SELL");
        panel.add(createUpdatePanel(), "UPDATE");
        panel.add(createGainPanel(), "GAIN");
        panel.add(createSearchPanel(), "SEARCH");
    
        // add the main panel to the frame
        add(panel, BorderLayout.CENTER); 
        setJMenuBar(createMenuBar());  // add the menu bar to the frame (commands)
    }
    
    // method to make the main welcome/instructions panel
    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // set the layout of the panel

        JLabel welcomeLabel = new JLabel("<html><br><br>Welcome to ePortfolio.<html>"); // create a label for the welcome message using html tags to format the text
        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.PLAIN, 20));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0)); // add padding to the label

        // create a label for the instructions using html tags to format the text
        JLabel instructionLabel = new JLabel("<html><br><br>Choose a command from the “Commands” menu to buy or sell<br>" + // idk if these tags are the correct way to do this but it works so..
                "an investment, update prices for all investments, get gain for the<br>" +
                "portfolio, search for relevant investments, or quit the program.<html>");
        instructionLabel.setFont(new Font(instructionLabel.getFont().getName(), Font.PLAIN, 20));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));

        mainPanel.add(welcomeLabel); // add the welcome label to the panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // add space between the labels
        mainPanel.add(instructionLabel); // add the instruction label to the panel

        return mainPanel;
    }
    
    // method to create the buy investment panel
    private JPanel createBuyPanel() {

        JPanel buyPanel = new JPanel(new BorderLayout());
        buyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    
        // left side of the panel for the input fields (symbol, name, quantity, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    
        /* ~~ panel for the title (buying an investment) */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Buying an investment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // make it bold and larger text
        titlePanel.add(titleLabel);
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // add space between the title and the input fields
    
        /* ~~ panel for the type combo box (stock or mutual fund) ~~ */
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // flowlayout to align the label and combo box
        JLabel typeLabel = new JLabel("Type ");
        typeLabel.setPreferredSize(new Dimension(100, 35));  // set the size of the label so that it is aligned with the other labels
        typeLabel.setFont(new Font(typeLabel.getFont().getName(), Font.PLAIN, 16)); 
        typeComboBox = new JComboBox<>(new String[]{"Stock", "Mutual Fund"}); // options for the combo box
        typeComboBox.setPreferredSize(new Dimension(200, 25)); // set the size of the combo box to somewhat match the sample image
        // add all the components to the panel
        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        leftPanel.add(typePanel);
    
        /* ~~ panel for the symbol input field ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35)); 
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        symbolField = new JTextField();
        symbolField.setPreferredSize(new Dimension(150, 25)); 
        // add all the components to the panel
        symbolPanel.add(symbolLabel);
        symbolPanel.add(symbolField);
        leftPanel.add(symbolPanel);
    
        /* ~~ panel for the name input field ~~ */
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setPreferredSize(new Dimension(100, 35)); 
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 16));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 25)); 
        // add all the components to the panel
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        leftPanel.add(namePanel);
    
        /* ~~ panel for the quantity input field ~~ */
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel quantityLabel = new JLabel("Quantity ");
        quantityLabel.setPreferredSize(new Dimension(100, 35)); 
        quantityLabel.setFont(new Font(quantityLabel.getFont().getName(), Font.PLAIN, 16));
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(125, 25)); 
        // add all the components to the panel
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        leftPanel.add(quantityPanel);
    
        /* ~~ panel for the price input field ~~ */
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setPreferredSize(new Dimension(100, 35)); 
        priceLabel.setFont(new Font(priceLabel.getFont().getName(), Font.PLAIN, 16));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(125, 25)); 
        // add all the components to the panel
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        leftPanel.add(pricePanel);
    
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 16))); // add space between the input fields and the message area below
    
        // right side of the panel for the buttons (reset and buy)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // add padding
    
        /* ~~ panel for the reset button ~~ */
        JButton resetButton = new JButton("Reset"); 
        resetButton.setPreferredSize(new Dimension(120, 50)); 
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.PLAIN, 16));
        JPanel resetWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); // this is a wrapper panel to center the button in the panel (flowlayout)
        resetWrapper.add(resetButton);
    
        /* ~~ panel for the buy button ~~ */
        JButton buyButton = new JButton("Buy");
        buyButton.setPreferredSize(new Dimension(120, 50)); 
        buyButton.setFont(new Font(buyButton.getFont().getName(), Font.PLAIN, 16));
        JPanel buyWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); // same wrapper panel as above
        buyWrapper.add(buyButton); 
    
        
        rightPanel.add(Box.createVerticalGlue());  // add space at the top of the panel
        // add the buttons to the panel
        rightPanel.add(resetWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        rightPanel.add(buyWrapper);
    

        // add action listeners to the buttons to handle the actions when they are clicked
        resetButton.addActionListener(e -> resetFields());
        buyButton.addActionListener(e -> handleBuyAction());
    
        // add the left and right panels to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); // the divider should be about 3/4 of the way to the right
        splitPane.setResizeWeight(0.7);  
        splitPane.setContinuousLayout(true); 
        splitPane.setBorder(null); // remove the border of the split pane to make it look nicer
    
        // add the split pane and the message panel to the buy panel
        buyPanel.add(splitPane, BorderLayout.CENTER);
        buyPanel.add(createBuyMessagePanel(), BorderLayout.SOUTH);
    
        return buyPanel;
     }

    // method to create the message panel specific to the buy panel
    private JPanel createBuyMessagePanel() {

        JPanel messagePanel = new JPanel(new BorderLayout());
    
        JLabel messageLabel = new JLabel("Messages "); // create a label for the message area
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 16));
    
        /* ~~ text area for the messages ~~ */
        buyMessageArea = new JTextArea(8, 20);
        buyMessageArea.setEditable(false); // not editable by the user
        buyMessageArea.setLineWrap(true); // wrap the text so that it fits in the text area
        buyMessageArea.setWrapStyleWord(true); // wrap the text at words instead of letters, so that words are not split in half
    
        /* ~~ scroll pane for the text area ~~ */
        JScrollPane messageScrollPane = new JScrollPane(buyMessageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // always show the vertical scroll bar
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // always show the horizontal scroll bar
    
        messagePanel.add(messageLabel, BorderLayout.NORTH); 
        messagePanel.add(messageScrollPane, BorderLayout.CENTER); 
    
        return messagePanel;
    }
    
    // method to reset the input fields and message areas when the reset button is clicked
    private void resetFields() {

        // reset the input fields
        symbolField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");

        // reset the message areas if they are not null
        if (buyMessageArea != null){
            buyMessageArea.setText(""); // for buy panel
        } 
        if (sellMessageArea != null){
            sellMessageArea.setText(""); // for sell panel
        }
        if (updateMessageArea != null){
            updateMessageArea.setText(""); // for update panel
        } 
        if (gainMessageArea != null){
            gainMessageArea.setText(""); // for gain panel
        }
        if (searchMessageArea != null){
            searchMessageArea.setText(""); // for search panel
        } 
    }
 
    // method to handle the buy action when the buy button is clicked (using the buyInvestment method from my Portfolio class) 
    private void handleBuyAction() {
        try {
            // get the input values from the fields like the type of investment, symbol, name, quantity, and price
            String type = (String) typeComboBox.getSelectedItem();
            String symbol = symbolField.getText().trim();
            String name = nameField.getText().trim();

            
            buyMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered

            // if the user leaves the symbol field empty, display an error message
            if (symbol.isEmpty()) {
                buyMessageArea.append("Sorry, investment symbol cannot be empty.\n");
                return;
            }

            boolean inPortfolio = false; // variable to hold true or false depending on if the symbol is already in the portfolio
            // loop through the investments in the portfolio to check if the symbol already exists
            for (int i = 0; i < portfolio.getInvestments().size(); i++) {
                Investment investment = portfolio.getInvestments().get(i);
                if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                    inPortfolio = true;
                    break; // exit the loop early as the symbol is found
                }
            }
            // if the symbol already exists in the portfolio, display an error message
            if (inPortfolio) {
                buyMessageArea.append("Sorry, this symbol already exists in your portfolio! Please choose a different symbol.\n");
                return;
            }

            // if the user leaves the name field empty, display an error message
            if (name.isEmpty()) {
                buyMessageArea.append("Sorry, investment name cannot be empty.\n");
                return;
            }

            int quantity; // variable to hold the quantity of the investment
            // a try catch block to handle the case where the user enters a non-integer value for the quantity
            try {
                quantity = Integer.valueOf(quantityField.getText().trim()); // get the quantity from the field
                // if the quantity is less than or equal to 0, display an error message
                if (quantity <= 0) {
                    buyMessageArea.append("Oops, you can't purchase " + quantity + " units!\n");
                    return;
                }
            } 
            catch (NumberFormatException ex) { // catch the exception if the user enters a non-integer value
                buyMessageArea.append("Sorry, please enter a valid quantity.\n");
                return;
            }

            double price; // variable to hold the price of the investment
            // same try catch block as above to handle the case where the user enters a non-double value for the price
            try {
                price = Double.parseDouble(priceField.getText().trim());
                // if the price is less than 0, display an error message
                if (price < 0) {
                    buyMessageArea.append("Oops, you can't purchase units for $" + price + "!\n");
                    return;
                }
            } 
            catch (NumberFormatException ex) {
                buyMessageArea.append("Sorry, please enter a valid price.\n");
                return;
            }

            
            Investment investment = null; // variable to hold the investment object
            // if the type is stock, create a stock object
            if (type.equalsIgnoreCase("Stock")) { 
                investment = new Stock(symbol, name, quantity, price, false);
            } 
            // if the type is mutual fund, create a mutual fund object
            else if (type.equalsIgnoreCase("Mutual Fund")) {
                investment = new MutualFund(symbol, name, quantity, price, false);
            }
            // otherwise its an invalid choice, so display an error message 
            else {
                buyMessageArea.append("Invalid choice, please try again.\n");
                return;
            }

            investment.setBookValue(price); // set the book value of the investment

            portfolio.buyInvestment(investment); // buy the investment and add it to the portfolio

            buyMessageArea.append("Investment added successfully!\n"); // display the success message when the investment is added

        } 
        catch (Exception ex) { // catch any other exceptions that could happen
            buyMessageArea.append("Oops, an unexpected error occurred: " + ex.getMessage() + "\n");
        }
    }

    // method to create the sell investment panel
    private JPanel createSellPanel() {

        JPanel sellPanel = new JPanel(new BorderLayout());
        sellPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    
        // left side of the panel for the input fields (symbol, quantity, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
    
        /* ~~ title panel for selling an investment ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Selling an investment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // make it bold and larger text
        // add the label to the panel
        titlePanel.add(titleLabel); 
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // add space between the title and the input fields
    
        /* ~~ panel for the symbol input field ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35));
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField symbolField = new JTextField();
        symbolField.setPreferredSize(new Dimension(150, 25)); 
        // add the label and field to the panel
        symbolPanel.add(symbolLabel);
        symbolPanel.add(symbolField);
        leftPanel.add(symbolPanel);
    
        /* ~~ panel for the quantity input field ~~ */
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel quantityLabel = new JLabel("Quantity ");
        quantityLabel.setPreferredSize(new Dimension(100, 35)); 
        quantityLabel.setFont(new Font(quantityLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(125, 25));
        // add the label and field to the panel
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        leftPanel.add(quantityPanel);
    
        /* ~~ panel for the price input field ~~ */
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setPreferredSize(new Dimension(100, 35)); 
        priceLabel.setFont(new Font(priceLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(125, 25)); 
        // add the label and field to the panel
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        leftPanel.add(pricePanel);
    
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 16))); // add space between the input fields and the message area below
    
        // right side of the panel for the buttons (reset and sell)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); 
    
        /* ~~ panel for the reset button ~~ */
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(120, 50)); 
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.PLAIN, 16));
        JPanel resetWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetWrapper.add(resetButton); 
    
        /* ~~ panel for the sell button ~~ */
        JButton sellButton = new JButton("Sell");
        sellButton.setPreferredSize(new Dimension(120, 50)); 
        sellButton.setFont(new Font(sellButton.getFont().getName(), Font.PLAIN, 16));
        JPanel sellWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sellWrapper.add(sellButton); 
    
        // add the buttons to the panel
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(resetWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        rightPanel.add(sellWrapper);
    
        // add action listeners to the buttons to handle the actions when they are clicked, this was done previously in its own method for buy but i decided to do it here instead
        resetButton.addActionListener(e -> {
            symbolField.setText("");
            quantityField.setText("");
            priceField.setText("");
            if (sellMessageArea != null){
                sellMessageArea.setText(""); 
            }
        });
        // action listener for the sell button to handle the sell action 
        sellButton.addActionListener(e -> handleSellAction(symbolField, quantityField, priceField)); 
    
        // add the left and right panels to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); 
        splitPane.setResizeWeight(0.7); 
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);
    
        sellPanel.add(splitPane, BorderLayout.CENTER);
        sellPanel.add(createSellMessagePanel(), BorderLayout.SOUTH);
    
        return sellPanel;
    }
    
    // method to create the message panel specific to the sell panel
    private JPanel createSellMessagePanel() {

        JPanel messagePanel = new JPanel(new BorderLayout());
    
        JLabel messageLabel = new JLabel("Messages "); // create a label for the message area
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 16));
        
        // text area for the messages
        sellMessageArea = new JTextArea(8, 20);
        sellMessageArea.setEditable(false); 
        sellMessageArea.setLineWrap(true); 
        sellMessageArea.setWrapStyleWord(true); 
    
        // scroll pane for the text area
        JScrollPane messageScrollPane = new JScrollPane(sellMessageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    
        messagePanel.add(messageLabel, BorderLayout.NORTH);
        messagePanel.add(messageScrollPane, BorderLayout.CENTER);
    
        return messagePanel;
    }
    
    // method to handle the sell action when the sell button is clicked using the sellInvestment method from my Portfolio class
    private void handleSellAction(JTextField symbolField, JTextField quantityField, JTextField priceField) {
        
        sellMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered
    

        String symbol = symbolField.getText().trim(); // get the symbol from the field
        // if the symbol is empty, display an error message
        if (symbol.isEmpty()) {
            sellMessageArea.append("Sorry, investment symbol cannot be empty.\n");
            return;
        }
    
        int quantity;
        // try catch block to handle the case where the user enters a non-integer value for the quantity
        try {
            quantity = Integer.valueOf(quantityField.getText().trim()); // get the quantity from the field
            // if the quantity is less than or equal to 0, display an error message
            if (quantity <= 0) {
                sellMessageArea.append("Oops, you can't sell " + quantity + " units!\n");
                return;
            }
        } 
        catch (NumberFormatException ex) {
            sellMessageArea.append("Sorry, please enter a valid quantity.\n");
            return;
        }
    
        double price; // variable to hold the price of the investment
        try {
            price = Double.valueOf(priceField.getText().trim()); // get the price from the field
            // if the price is less than 0, display an error message
            if (price < 0) {
                sellMessageArea.append("Oops, you can't sell units for $" + price + "!\n");
                return;
            }
        } 
        catch (NumberFormatException ex) {
            sellMessageArea.append("orry, please enter a valid price.\n");
            return;
        }
    
        double payment = portfolio.sellInvestment(symbol, quantity, price); // sell the investment and get the payment amount
        // if the payment is greater than or equal to 0, display the success message with the payment amount
        if (payment >= 0) {
            sellMessageArea.append("Investment sold successfully. Payment: $" + String.format("%.2f", payment) + "\n");
        } 
        // otherwise display an error message
        else {
            sellMessageArea.append("Oops, investment wasn't found or an invalid quantity was entered.\n");
        }
    }
    