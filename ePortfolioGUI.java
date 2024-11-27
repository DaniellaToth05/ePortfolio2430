

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
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
    
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
    
    // method to create the update investment panel
    private JPanel createUpdatePanel() {

        JPanel updatePanel = new JPanel(new BorderLayout());
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    
        // left side of the panel for the input fields (symbol, name, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
    
        /* ~~ title panel for updating investments ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Updating investments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        // add the label to the panel
        titlePanel.add(titleLabel); 
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
    
        /* ~~ panel for the symbol input field ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35)); 
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField symbolField = new JTextField();
        symbolField.setPreferredSize(new Dimension(150, 25)); 
        symbolField.setEditable(false);
        // add the label and field to the panel
        symbolPanel.add(symbolLabel);
        symbolPanel.add(symbolField);
        leftPanel.add(symbolPanel);
    
        /* ~~ panel for the name input field ~~ */    
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setPreferredSize(new Dimension(100, 35)); 
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 25)); 
        nameField.setEditable(false); 
        // add the label and field to the panel
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        leftPanel.add(namePanel);
    
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
    
        // right side of the panel for the buttons (prev, next, and save)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Add padding
    
        /* ~~ panel for the prev button ~~ */
        JButton prevButton = new JButton("Prev");
        prevButton.setPreferredSize(new Dimension(120, 50)); 
        prevButton.setFont(new Font(prevButton.getFont().getName(), Font.PLAIN, 16));
        JPanel prevWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevWrapper.add(prevButton);
    
        /* ~~ panel for the next button ~~ */
        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(120, 50));
        nextButton.setFont(new Font(nextButton.getFont().getName(), Font.PLAIN, 16));
        JPanel nextWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nextWrapper.add(nextButton); 
    
        /* ~~ panel for the save button ~~ */
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 50)); 
        saveButton.setFont(new Font(saveButton.getFont().getName(), Font.PLAIN, 16));
        JPanel saveWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveWrapper.add(saveButton); 
    
        // add the buttons to the panel
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(prevWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        rightPanel.add(nextWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(saveWrapper);
    
        
        final int[] index = {0}; // variable to track the current investment index

        // if the portfolio is empty, display a message saying there are no investments to display in the message area and clear the fields
        if (portfolio.getInvestments().isEmpty()) {
            updateMessageArea.setText("No investments to display.");
            symbolField.setText("");
            nameField.setText("");
            priceField.setText("");
        } 
        // otherwise display the first investment in the portfolio
        else {
            Investment current = portfolio.getInvestments().get(index[0]); // get the first investment
            symbolField.setText(current.getSymbol()); // set the symbol field to the symbol of the investment
            nameField.setText(current.getName()); // set the name field to the name of the investment
            priceField.setText(String.valueOf(current.getPrice())); // set the price field to the price of the investment
        }

        // action listeners for the buttons to handle the actions when they are clicked
        prevButton.addActionListener(e -> {
            // if the index is greater than 0, decrement the index and display the previous investment
            if (index[0] > 0) {
                index[0]--;
                // if the portfolio is empty, display a message saying there are no investments to display in the message area and clear the fields
                if (portfolio.getInvestments().isEmpty()) {
                    updateMessageArea.setText("No investments to display.");
                    symbolField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                } 
                // otherwise display the previous investment
                else {
                    Investment previous = portfolio.getInvestments().get(index[0]); // get the previous investment
                    symbolField.setText(previous.getSymbol()); // set the symbol field to the symbol of the investment
                    nameField.setText(previous.getName()); // set the name field to the name of the investment
                    priceField.setText(String.valueOf(previous.getPrice())); // set the price field to the price of the investment
                }
            }
        });

        // action listener for the next button to display the next investment
        nextButton.addActionListener(e -> {
            // if the index is less than the size of the portfolio1, increment the index and display the next investment
            if (index[0] < portfolio.getInvestments().size() - 1) {
                index[0]++; // increment the index
                // if the portfolio is empty, display a message saying there are no investments to display in the message area and clear the fields
                if (portfolio.getInvestments().isEmpty()) {
                    updateMessageArea.setText("No investments to display.");
                    symbolField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                } 
                // otherwise display the next investment
                else {
                    Investment next = portfolio.getInvestments().get(index[0]); // get the next investment
                    symbolField.setText(next.getSymbol()); // set the symbol field to the symbol of the investment
                    nameField.setText(next.getName()); // set the name field to the name of the investment
                    priceField.setText(String.valueOf(next.getPrice())); // set the price field to the price of the investment
                }
            }
        });

        // action listener for the save button to save the updated price of the investment
        saveButton.addActionListener(e -> {
            // try catch block to handle the if the user enters an invalid value for the price
            try {
                double newPrice = Double.parseDouble(priceField.getText().trim()); // get the new price from the field
                portfolio.getInvestments().get(index[0]).setPrice(newPrice); // set the new price of the investment
                updateMessageArea.setText("Price updated successfully."); // display the success message
            } 
            catch (NumberFormatException ex) {
                updateMessageArea.setText("Sorry, please enter a valid value for price.");
            }
        });

        // add the left and right panels to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); 
        splitPane.setResizeWeight(0.7); 
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        updatePanel.add(splitPane, BorderLayout.CENTER);
        updatePanel.add(createUpdateMessagePanel(), BorderLayout.SOUTH);

        return updatePanel;
    }
    
    // method to create the message panel specific to the update panel
    private JPanel createUpdateMessagePanel() {
        JPanel messagePanel = new JPanel(new BorderLayout());
    
        // label for the message area
        JLabel messageLabel = new JLabel("Messages ");
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 16));
    
        // text area for the messages
        updateMessageArea = new JTextArea(8, 20);
        updateMessageArea.setEditable(false);
        updateMessageArea.setLineWrap(true); 
        updateMessageArea.setWrapStyleWord(true); 
    
        // scroll pane for the text area
        JScrollPane messageScrollPane = new JScrollPane(updateMessageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
        // add the label and scroll pane to the panel
        messagePanel.add(messageLabel, BorderLayout.NORTH);
        messagePanel.add(messageScrollPane, BorderLayout.CENTER);
    
        return messagePanel;
    }
    
    // method to create the gain panel
    private void handleUpdateAction(JTextField priceField, int investmentIndex) {

        updateMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered
    
        // try catch block to handle the if the user enters an invalid value for the price
        try {
            double newPrice = Double.parseDouble(priceField.getText().trim()); // get the new price from the field
            // if the new price is less than or equal to 0, display an error message
            if (newPrice <= 0) {
                updateMessageArea.append("Oops, price must be greater than zero!\n");
                return;
            }
            portfolio.getInvestments().get(investmentIndex).setPrice(newPrice); // set the new price of the investment
            
            updateMessageArea.append("Price updated successfully to $" + String.format("%.2f", newPrice) + ".\n"); // display the success message with the new price
        }
        // catch the exception if the user enters an invalid value for the price
        catch (NumberFormatException ex) {
            updateMessageArea.append("Sorry, please enter a valid price.\n");
        } 
        // catch the exception if the user enters an index that is out of range
        catch (IndexOutOfBoundsException ex) {
            updateMessageArea.append("Oops, unable to update investment - index out of range!\n");
        }
    }

    // method to create the gain panel
    private JPanel createGainPanel() {

        // panel for the gain panel
        JPanel gainPanel = new JPanel(new BorderLayout());
        gainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); 
    
        // main panel for the gain panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        
        /* ~~ title for getting total gain ~~ */
        JLabel titleLabel = new JLabel("Getting total gain");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));  // add space between the title and the total gain label
        
        /* ~~ panel for the total gain label ~~ */
        JPanel totalGainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel totalGainLabel = new JLabel("Total gain");
        totalGainLabel.setFont(new Font(totalGainLabel.getFont().getName(), Font.PLAIN, 16));
        totalGainPanel.add(totalGainLabel);
    
        /* ~~ panel for the total gain field ~~ */
        JTextField totalGainField = new JTextField(10);
        totalGainField.setPreferredSize(new Dimension(100, 25));
        totalGainField.setEditable(false);  // not editable by the user
        totalGainPanel.add(totalGainField);
        totalGainPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        mainPanel.add(totalGainPanel);
    
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); 
    
        /* ~~ label for individual gains ~~ */
        JLabel individualGainsLabel = new JLabel("Individual gains");
        individualGainsLabel.setFont(new Font(individualGainsLabel.getFont().getName(), Font.PLAIN, 16));
        individualGainsLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        mainPanel.add(individualGainsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
        /* ~~ text area for the individual gains ~~ */
        JTextArea individualGainsArea = new JTextArea(15, 40); 
        individualGainsArea.setEditable(false);  // not editable by the user
        individualGainsArea.setLineWrap(true);  // wrap the text so that it fits in the text area
        individualGainsArea.setWrapStyleWord(true); // wrap the text at words instead of letters, so that words are not split in half
    
        /* ~~ scroll pane for the text area ~~ */
        JScrollPane gainsScrollPane = new JScrollPane(individualGainsArea);
        gainsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // always show the vertical scroll bar
        gainsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // always show the horizontal scroll bar
        gainsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT); 
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); 
        mainPanel.add(gainsScrollPane);
 
        double totalGain; // variable to hold the total gain of the portfolio
        String individualGainsText; // variable to hold the individual gains text for each investment
    
        // calculate the total gain and display it in the total gain field
        totalGain = portfolio.calculateTotalGain(); 
        totalGainField.setText(String.format("%.2f", totalGain));  // set the total gain field to the total gain
    
        
        individualGainsText = ""; // set the individual gains text to an empty string
        // loop through the investments in the portfolio to calculate the gain for each investment
        for (int i = 0; i < portfolio.getInvestments().size(); i++) {

            Investment investment; // variable to hold the investment object
            String investmentType; // variable to hold the type of investment
            double gain = 0.0; // variable to hold the gain of the investment
            investment = portfolio.getInvestments().get(i); // get the investment at the current index

            investmentType = ""; // set the investment type to an empty string
            
    
            // if the investment is a stock, calculate the gain for the stock
            if (investment instanceof Stock) {
                investmentType = "Stock"; // set the investment type to stock
                Stock stock = (Stock) investment; // cast the investment to a stock
                gain = stock.calculateGain(); // calculate the gain for the stock
            } 
            // if the investment is a mutual fund, calculate the gain for the mutual fund
            else if (investment instanceof MutualFund) {
                investmentType = "Mutual Fund"; // set the investment type to mutual fund
                MutualFund fund = (MutualFund) investment;  // cast the investment to a mutual fund
                gain = fund.calculateGain(); // calculate the gain for the mutual fund
            }
    
            individualGainsText += String.format("%s - Symbol: %s, Gain: %.2f%n", investmentType, investment.getSymbol(), gain);
        }
    
        individualGainsArea.setText(individualGainsText); // set the text area to the individual gains text
    
        gainPanel.add(mainPanel, BorderLayout.CENTER);
        return gainPanel;
    }
    
    // method to create the search panel
    private JPanel createSearchPanel() {

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    
        // left side of the panel for the input fields (symbol, keywords, low price, and high price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
    
        /* ~~ title panel for searching investments ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Searching investments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // make it bold and larger text
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

    
        /* ~~ panel for the keywords input field ~~ */
        JPanel keywordsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel keywordsLabel = new JLabel("<html>Name<br> keywords <html>");
        keywordsLabel.setPreferredSize(new Dimension(100, 35)); 
        keywordsLabel.setFont(new Font(keywordsLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField keywordsField = new JTextField();
        keywordsField.setPreferredSize(new Dimension(250, 25)); 
        // add the label and field to the panel
        keywordsPanel.add(keywordsLabel);
        keywordsPanel.add(keywordsField);
        leftPanel.add(keywordsPanel);
    
        /* ~~ panel for the low price input field ~~ */
        JPanel lowPricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lowPriceLabel = new JLabel("Low price "); // lower range of the price
        lowPriceLabel.setPreferredSize(new Dimension(100, 35)); 
        lowPriceLabel.setFont(new Font(lowPriceLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField lowPriceField = new JTextField(); 
        lowPriceField.setPreferredSize(new Dimension(125, 25)); 
        // add the label and field to the panel
        lowPricePanel.add(lowPriceLabel);
        lowPricePanel.add(lowPriceField);
        leftPanel.add(lowPricePanel);
    
        /* ~~ panel for the high price input field ~~ */
        JPanel highPricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel highPriceLabel = new JLabel("High price "); // upper range of the price
        highPriceLabel.setPreferredSize(new Dimension(100, 35)); 
        highPriceLabel.setFont(new Font(highPriceLabel.getFont().getName(), Font.PLAIN, 16));
        JTextField highPriceField = new JTextField();
        highPriceField.setPreferredSize(new Dimension(125, 25)); 
        // add the label and field to the panel
        highPricePanel.add(highPriceField);
        leftPanel.add(highPricePanel);
    
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 16))); // add space between the input fields and the message area below
    
        // right side of the panel for the buttons (reset and search)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); 
    
        /* ~~ panel for the reset button ~~ */
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(120, 50));
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.PLAIN, 16));
        JPanel resetWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); // create a wrapper panel to center the button
        resetWrapper.add(resetButton);
    
        /* ~~ panel for the search button ~~ */
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(120, 50));
        searchButton.setFont(new Font(searchButton.getFont().getName(), Font.PLAIN, 16));
        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchWrapper.add(searchButton); 
    
        // add the buttons to the panel
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(resetWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        rightPanel.add(searchWrapper);
    
        // action listenr for reset button to clear the fields
        resetButton.addActionListener(e -> {
            symbolField.setText(""); // clear the symbol field
            keywordsField.setText(""); // clear the keywords field
            lowPriceField.setText(""); // clear the low price field
            highPriceField.setText(""); // clear the high price field
            searchMessageArea.setText(""); // clear the message area
        });
    
        searchButton.addActionListener(e -> handleSearchAction(symbolField, keywordsField, lowPriceField, highPriceField)); // action listener for the search button to handle the search action
    
        // add the left and right panels to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.7); 
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null); 
    
        searchPanel.add(splitPane, BorderLayout.CENTER);
        searchPanel.add(createSearchResultsPanel(), BorderLayout.SOUTH);
    
        return searchPanel;
    }
    
    // method to create the search results panel
    private JPanel createSearchResultsPanel() {

        JPanel resultsPanel = new JPanel(new BorderLayout());
    
        // label for the search results
        JLabel resultsLabel = new JLabel("Search results");
        resultsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 
        resultsLabel.setFont(new Font(resultsLabel.getFont().getName(), Font.PLAIN, 16));
    
        // text area for the search results
        searchMessageArea = new JTextArea(8, 20); 
        searchMessageArea.setEditable(false); // not editable by the user
        searchMessageArea.setLineWrap(true);  // wrap the text so that it fits in the text area
        searchMessageArea.setWrapStyleWord(true);  // wrap the text at words instead of letters, so that words are not split in half
    
        // scroll pane for the text area
        JScrollPane resultsScrollPane = new JScrollPane(searchMessageArea);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // always show the vertical scroll bar
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  // always show the horizontal scroll bar
    
        // add the label and scroll pane to the panel
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);
        resultsPanel.add(resultsScrollPane, BorderLayout.CENTER);
    
        return resultsPanel;
    }

    // method to handle the search action when the search button is clicked using the searchForInvestment method from my Portfolio class
    private void handleSearchAction(JTextField symbolField, JTextField keywordsField, JTextField lowPriceField, JTextField highPriceField) {
        
        // variables to hold the inputs from the fields
        String symbolInput;
        String keywordsInput;
        String priceRangeInput;
        String lowPrice;
        String highPrice;

        searchMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered
    
        symbolInput = symbolField.getText().trim(); // get the symbol from the field
        keywordsInput = keywordsField.getText().trim(); // get the keywords from the field
        priceRangeInput = ""; // initialize the price range input to an empty string
    
        lowPrice = lowPriceField.getText().trim(); // get the low price from the field
        highPrice = highPriceField.getText().trim(); // get the high price from the field
    
        // if the low price or high price is not empty, set the price range input to the low price and high price separated by a hyphen
        if (!lowPrice.isEmpty() || !highPrice.isEmpty()) {
            priceRangeInput = lowPrice + "-" + highPrice;
        }
        
        // try catch block to handle the search action and display the search results
        try {
            String results; // variable to hold the search results
            results = portfolio.searchForInvestment(symbolInput, keywordsInput, priceRangeInput); // search for the investment based on the inputs
    
            // if the results are empty, display a message saying no investments match the search
            if (results.isEmpty()) {
                searchMessageArea.append("Sorry, no investments match your search.\n");
            } 
            // otherwise display the search results
            else {
                searchMessageArea.append(results);
            }
        } 
        // catch any exceptions that could happen
        catch (Exception ex) {
            searchMessageArea.append("Oops, was unable to process your search. Please double check your inputs or try again!\n");
        }
    }
    
    
    
    