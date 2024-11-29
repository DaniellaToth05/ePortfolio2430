

 /* 
 * Name: Daniella Toth
 * Student ID: 1261398
 * Compile Command: javac DiscussionBoard.java User.java Post.java DiscussionBoardGUI.java
 * Run Command: java DiscussionBoardGUI
 */

// import the files needed for the GUI
import ePortfolio.Investment;
import ePortfolio.Stock;
import ePortfolio.MutualFund;
import ePortfolio.Portfolio;

// import the swing components and other necessary components
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * class to represent the GUI for the ePortfolio program
 */
public class ePortfolioGUI extends JFrame {
    public static final int WIDTH = 700; // width of the frame
    public static final int HEIGHT = 500; // height of the frame

    // instance variables for the GUI
    private Portfolio portfolio; // portfolio object

    private int index = 0; // index for the search panel and updating the investment fields
    private JButton prevButton;
    private JButton nextButton;
    private JButton saveButton;


    // buy panel-specific fields
    private JTextField buySymbolField;
    private JTextField buyNameField;
    private JTextField buyQuantityField;
    private JTextField buyPriceField;
    
    // sell panel-specific fields
    private JTextField sellSymbolField;
    private JTextField sellQuantityField;
    private JTextField sellPriceField;

    // update panel-specific fields
    private JTextField updateSymbolField;
    private JTextField updateNameField;
    private JTextField updatePriceField;
    

    private JComboBox<String> typeComboBox; // combo box for investment type
    private CardLayout cardLayout; // card layout for the panels
    private JPanel panel; // panel for the cards

    // message areas for the panels (to display the error messages or other messages)
    private JTextArea buyMessageArea;
    private JTextArea sellMessageArea;
    private JTextArea updateMessageArea;
    //private JTextArea gainMessageArea;
    private JTextArea searchMessageArea;

    private JTextField totalGainField;
    private JTextArea individualGainsArea;

    
    
    /**
     * constructor for the ePortfolio GUI
     */
    public ePortfolioGUI() {
        portfolio = new Portfolio(); 
        initializeGUI();
    }
 
    
    /**
     * main method to run the GUI
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ePortfolioGUI frame = new ePortfolioGUI(); // create the frame

        frame.setVisible(true); // set the frame to be visible

    }

    /**
     * method to initialize the GUI components
     */
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
        //gainMessageArea = new JTextArea(8, 20);
        searchMessageArea = new JTextArea(8, 20);

        // fields specific to the buy panel
        buySymbolField = new JTextField(15);
        buyNameField = new JTextField(15);
        buyQuantityField = new JTextField(10);
        buyPriceField = new JTextField(10);

        // fields specific to the sell panel
        sellSymbolField = new JTextField(15);
        sellQuantityField = new JTextField(10);
        sellPriceField = new JTextField(10);

    
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
    
    
    /**
     * method to make the main welcome/instructions panel
     * @return the panel for the main screen
     */
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
    
    /**
     * Method to create the buy investment panel
     * @return the panel for buying investments
     */
    private JPanel createBuyPanel() {
        JPanel buyPanel = new JPanel(new BorderLayout());
        buyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // declare the fields for the buy panel
        buySymbolField = new JTextField(15);
        buyNameField = new JTextField(15);
        buyQuantityField = new JTextField(10);
        buyPriceField = new JTextField(10);


        // left side of the panel for the input fields (symbol, name, quantity, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        /* ~~ title panel ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Buying an investment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        /* ~~ type comboBox panel ~~ */
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLabel = new JLabel("Type ");
        typeLabel.setPreferredSize(new Dimension(100, 35));
        typeLabel.setFont(new Font(typeLabel.getFont().getName(), Font.PLAIN, 16));
        typeComboBox = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});
        typeComboBox.setPreferredSize(new Dimension(200, 25));
        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        leftPanel.add(typePanel);

        /* ~~ symbol field panel ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35));
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        buySymbolField.setPreferredSize(new Dimension(150, 25));
        symbolPanel.add(symbolLabel);
        symbolPanel.add(buySymbolField);
        leftPanel.add(symbolPanel);

        /* ~~ name field panel ~~ */
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setPreferredSize(new Dimension(100, 35));
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 16));
        buyNameField.setPreferredSize(new Dimension(250, 25));
        namePanel.add(nameLabel);
        namePanel.add(buyNameField);
        leftPanel.add(namePanel);

        /* ~~ quantity field panel ~~ */
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel quantityLabel = new JLabel("Quantity ");
        quantityLabel.setPreferredSize(new Dimension(100, 35));
        quantityLabel.setFont(new Font(quantityLabel.getFont().getName(), Font.PLAIN, 16));
        buyQuantityField.setPreferredSize(new Dimension(125, 25));
        quantityPanel.add(quantityLabel);
        quantityPanel.add(buyQuantityField);
        leftPanel.add(quantityPanel);

        /* ~~ price field panel ~~ */
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setPreferredSize(new Dimension(100, 35));
        priceLabel.setFont(new Font(priceLabel.getFont().getName(), Font.PLAIN, 16));
        buyPriceField.setPreferredSize(new Dimension(125, 25));
        pricePanel.add(priceLabel);
        pricePanel.add(buyPriceField);
        leftPanel.add(pricePanel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 16)));

        // right side of the panel for the buttons (reset and buy)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        /* ~~ panel for the reset button ~~ */
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(120, 50));
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.PLAIN, 16));

        // action listener for the reset button to clear the fields
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buySymbolField.setText("");
                buyNameField.setText("");
                buyQuantityField.setText("");
                buyPriceField.setText("");
                buyMessageArea.setText("");
            }
        });

        JPanel resetWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        resetWrapper.add(resetButton);

        /* ~~ panel for the buy button ~~ */
        JButton buyButton = new JButton("Buy");
        buyButton.setPreferredSize(new Dimension(120, 50));
        buyButton.setFont(new Font(buyButton.getFont().getName(), Font.PLAIN, 16));

        // action listener for the buy button to buy the investment
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyInvestmentAction(typeComboBox, buySymbolField, buyNameField, buyQuantityField, buyPriceField);
            }
        });

        JPanel buyWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        buyWrapper.add(buyButton);

        // add the reset and buy buttons to the right panel
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(resetWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(buyWrapper);
        

        // add the left and right panels to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.7);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        buyPanel.add(splitPane, BorderLayout.CENTER);
        buyPanel.add(createBuyMessagePanel(), BorderLayout.SOUTH);

        return buyPanel;
    }


    /**
     * method to create the message panel specific to the buy panel
     * @return the panel for the buy messages
     */
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
    
    
 
    /**
     * method to handle the buy action when the buy button is clicked (using the buyInvestment method from my Portfolio class)
     * @param typeComboBox combo box for selecting the investment type
     * @param buySymbolField text field for entering the investment symbol
     * @param buyNameField text field for entering the investment name
     * @param buyQuantityField text field for entering the quantity of the investment
     * @param buyPriceField text field for entering the price of the investment
     */
    private void buyInvestmentAction(JComboBox<String> typeComboBox, JTextField buySymbolField, JTextField buyNameField, JTextField buyQuantityField, JTextField buyPriceField) {
        try {
            //System.out.println("buyInvestmentAction started"); // debugging print statement
    
            // get the input values from the fields like the type of investment, symbol, name, quantity, and price
            String type = (String) typeComboBox.getSelectedItem();
            String symbol = buySymbolField.getText().trim();
            String name = buyNameField.getText().trim();
    
            buyMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered
    
            // if the user leaves the symbol field empty, display an error message
            if (symbol.isEmpty()) {
                //System.out.println("symbol was empty"); // debug print statement
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
                //System.out.println("name is empty"); // debug print statement
                buyMessageArea.append("Sorry, investment name cannot be empty.\n");
                return;
            }
    
            int quantity; // variable to hold the quantity of the investment
            // a try catch block to handle the case where the user enters a non-integer value for the quantity
            try {
                quantity = Integer.valueOf(buyQuantityField.getText().trim()); // get the quantity from the field
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
                price = Double.valueOf(buyPriceField.getText().trim());
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
            // otherwise its an invalid choice, so display an error message, although this should never be triggered as the combo box only has two options
            else {
                buyMessageArea.append("Invalid choice, please try again.\n");
                return;
            }
    
            investment.setBookValue(price); // set the book value of the investment
            //System.out.println("Book value set for investment"); // debugging print statement
    
            portfolio.buyInvestment(investment); // buy the investment and add it to the portfolio
            //System.out.println("Portfolio size after buying: " + portfolio.getInvestments().size()); // debugging print statement
            updateGainPanel(totalGainField, individualGainsArea); // update the gain panel with the new investment


            // loop through the investments in the portfolio to get the index of the new investment
            index = portfolio.getInvestments().size() - 1;
            for (int i = 0; i < portfolio.getInvestments().size(); i++) {
                // if the current investment symbol is the same as the new investment symbol
                if (i == portfolio.getInvestments().size() - 1) {
                    index = i; // set the index to the last investment in the portfolio
                }
            }
            
            //System.out.println("Index after buying: " + index); // debugging statement

            buyMessageArea.append("Investment added successfully!\n"); // display the success message when the investment is added
    
            updateUpdatePanel(); // refresh the update panel with the new investment
    
      
    
        } 
        catch (Exception ex) { // catch any other exceptions that could happen
            buyMessageArea.append("Sorry, an unexpected error occurred. Please try again.\n");
        }
    }


    /**
     * method to create the sell investment panel
     * @return the panel for selling investments
     */
    private JPanel createSellPanel() {

        JPanel sellPanel = new JPanel(new BorderLayout());
        sellPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        sellSymbolField = new JTextField(15);
        sellQuantityField = new JTextField(10);
        sellPriceField = new JTextField(10);

        // left side of the panel for the input fields (symbol, quantity, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
        
        /* ~~ title panel for selling an investment ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Selling an investment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // make it bold and larger text
        titlePanel.add(titleLabel); 
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // add space between the title and the input fields
        
        /* ~~ panel for the symbol input field ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35));
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        sellSymbolField.setPreferredSize(new Dimension(150, 25)); 
        // add the label and field to the panel
        symbolPanel.add(symbolLabel);
        symbolPanel.add(sellSymbolField);
        leftPanel.add(symbolPanel);
        
        /* ~~ panel for the quantity input field ~~ */
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel quantityLabel = new JLabel("Quantity ");
        quantityLabel.setPreferredSize(new Dimension(100, 35)); 
        quantityLabel.setFont(new Font(quantityLabel.getFont().getName(), Font.PLAIN, 16));
        sellQuantityField.setPreferredSize(new Dimension(125, 25));
        // add the label and field to the panel
        quantityPanel.add(quantityLabel);
        quantityPanel.add(sellQuantityField);
        leftPanel.add(quantityPanel);
        
        /* ~~ panel for the price input field ~~ */
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setPreferredSize(new Dimension(100, 35)); 
        priceLabel.setFont(new Font(priceLabel.getFont().getName(), Font.PLAIN, 16));
        sellPriceField.setPreferredSize(new Dimension(125, 25)); 
        // add the label and field to the panel
        pricePanel.add(priceLabel);
        pricePanel.add(sellPriceField);
        leftPanel.add(pricePanel);
        
        leftPanel.add(Box.createRigidArea(new Dimension(0, 16))); // add space

        // right side of the panel for the buttons (reset and sell)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); 
        
        /* ~~ panel for the reset button ~~ */
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(120, 50)); 
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.PLAIN, 16));

        // action listener for the reset button to clear the fields
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellSymbolField.setText("");
                sellQuantityField.setText("");
                sellPriceField.setText("");
                sellMessageArea.setText("");
            }
        });

        JPanel resetWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetWrapper.add(resetButton); 
        
        /* ~~ panel for the sell button ~~ */
        JButton sellButton = new JButton("Sell");
        sellButton.setPreferredSize(new Dimension(120, 50)); 
        sellButton.setFont(new Font(sellButton.getFont().getName(), Font.PLAIN, 16));

        // action listener for the sell button to sell the investment
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellInvestmentAction(sellSymbolField, sellQuantityField, sellPriceField); // call the sellInvestmentAction method
            }
        });
        
        JPanel sellWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sellWrapper.add(sellButton); 
        
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(resetWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        rightPanel.add(sellWrapper);
        
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
    
    
    /**
     * method to create the message panel specific to the sell panel
     * @return the panel for the sell messages
     */
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
    
    /**
     * method to handle the sell action when the sell button is clicked using the sellInvestment method from my Portfolio class
     * @param symbolField input field for the symbol
     * @param quantityField input field for the quantity
     * @param priceField input field for the price
     */
    private void sellInvestmentAction(JTextField symbolField, JTextField quantityField, JTextField priceField) {
        
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
            updateGainPanel(totalGainField, individualGainsArea); // update the gain panel with the new investment
            sellMessageArea.append("Investment sold successfully. Payment: $" + String.format("%.2f", payment) + "\n");
            
            if (portfolio.getInvestments().isEmpty()) {
                index = 0; // reset index if the portfolio is empty
            } 
            else if (index >= portfolio.getInvestments().size()) {
                index = portfolio.getInvestments().size() - 1; // adjust index if it goes out of bounds
            }
            updateUpdatePanel();
        
        } 
        // otherwise display an error message
        else {
            sellMessageArea.append("Oops, investment wasn't found or an invalid quantity was entered.\n");
        }
    }
    /**
     * method to update the update panel with the current investment details (after an investment is bought or sold)
     */
    private void updateUpdatePanel() {

        // debugging print statements
        //System.out.println("the portfolio size in updateUpdatePanel: " + portfolio.getInvestments().size());
        //System.out.println("Index: " + index);

        // debugging loop through the investments in the portfolio and print them to debug    
        // for(int i = 0; i < portfolio.getInvestments().size(); i++) {
        //     System.out.println(portfolio.getInvestments().get(i));
        // }

        if (portfolio.getInvestments().isEmpty()) {
            //System.out.println("Portfolio is empty in updateUpdatePanel.");
            updateSymbolField.setText("");
            updateNameField.setText("");
            updatePriceField.setText("");
            updateMessageArea.setText("No investments to display.");
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
        } 
        else {
            // System.out.println("Portfolio is not empty. Size: " + portfolio.getInvestments().size()); // debugging print statement 
            Investment current = portfolio.getInvestments().get(index);
            // System.out.println("Displaying investment: " + current); // debugging print statement
            updateSymbolField.setText(current.getSymbol());
            updateNameField.setText(current.getName());
            updatePriceField.setText(String.valueOf(current.getPrice()));
            updateMessageArea.setText(""); // clear messages
            prevButton.setEnabled(index > 0);
            nextButton.setEnabled(index < portfolio.getInvestments().size() - 1);
        }

        // revalidate and repaint the panel
        panel.revalidate();
        panel.repaint();
    }

    

    /**
     * method to create the update investment panel
     * @return the panel for updating investments
     */
    private JPanel createUpdatePanel() {
        JPanel updatePanel = new JPanel(new BorderLayout());
        updatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        updateSymbolField = new JTextField(15);
        updateNameField = new JTextField(15);
        updatePriceField = new JTextField(10);


        // left side of the panel for the input fields (symbol, name, and price)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        /* ~~ title panel for updating investments ~~ */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Updating investments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel); // add the label to the panel
        leftPanel.add(titlePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        /* ~~ panel for the symbol input field ~~ */
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel symbolLabel = new JLabel("Symbol ");
        symbolLabel.setPreferredSize(new Dimension(100, 35));
        symbolLabel.setFont(new Font(symbolLabel.getFont().getName(), Font.PLAIN, 16));
        updateSymbolField.setPreferredSize(new Dimension(150, 25));
        updateSymbolField.setEditable(false);
        // add the label and field to the panel
        symbolPanel.add(symbolLabel);
        symbolPanel.add(updateSymbolField);
        leftPanel.add(symbolPanel);

        /* ~~ panel for the name input field ~~ */
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setPreferredSize(new Dimension(100, 35));
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 16));
        updateNameField.setPreferredSize(new Dimension(250, 25));
        updateNameField.setEditable(false);
        // add the label and field to the panel
        namePanel.add(nameLabel);
        namePanel.add(updateNameField);
        leftPanel.add(namePanel);

        /* ~~ panel for the price input field ~~ */
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setPreferredSize(new Dimension(100, 35));
        priceLabel.setFont(new Font(priceLabel.getFont().getName(), Font.PLAIN, 16));
        updatePriceField.setPreferredSize(new Dimension(125, 25));
        // add the label and field to the panel
        pricePanel.add(priceLabel);
        pricePanel.add(updatePriceField);
        leftPanel.add(pricePanel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 16))); // add space between fields

        // right side of the panel for the buttons (prev, next, and save)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        /* ~~ panel for the prev button ~~ */
        prevButton = new JButton("Prev");
        prevButton.setPreferredSize(new Dimension(120, 50));
        prevButton.setFont(new Font(prevButton.getFont().getName(), Font.PLAIN, 16));
        JPanel prevWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevWrapper.add(prevButton);

        /* ~~ panel for the next button ~~ */
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(120, 50));
        nextButton.setFont(new Font(nextButton.getFont().getName(), Font.PLAIN, 16));
        JPanel nextWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nextWrapper.add(nextButton);

        /* ~~ panel for the save button ~~ */
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 50));
        saveButton.setFont(new Font(saveButton.getFont().getName(), Font.PLAIN, 16));
        JPanel saveWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveWrapper.add(saveButton);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(prevWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(nextWrapper);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(saveWrapper);

        // action listener for the previous button
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index > 0) {
                    index--;
                    updateUpdatePanel();
                }
            }
        });
        // action listener for the next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < portfolio.getInvestments().size() - 1) {
                    index++;
                    updateUpdatePanel();
                }
            }
        });
        // action listener for the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInvestmentAction(updatePriceField, index); // call the updateInvestmentAction method
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

    
    /**
     * method to create the message panel specific to the update panel
     * @return messagePanel the message panel
     */
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

    /**
     * method to handle the update action when the save button is clicked
     * @param updatePriceField input field for the price
     * @param investmentIndex index of the investment in the portfolio
     */
    private void updateInvestmentAction(JTextField updatePriceField, int investmentIndex) {

        updateMessageArea.setText(""); // clear the message area before displaying new messages so that it is not cluttered
    
        // try catch block to handle the if the user enters an invalid value for the price
        try {
            double newPrice = Double.valueOf(updatePriceField.getText().trim()); // get the new price from the field
            // if the new price is less than or equal to 0, display an error message
            if (newPrice <= 0) {
                updateMessageArea.append("Oops, price must be greater than zero!\n");
                return;
            }
            portfolio.getInvestments().get(investmentIndex).setPrice(newPrice); // set the new price of the investment
            updateGainPanel(totalGainField, individualGainsArea); // update the gain panel after the price update
            updateMessageArea.append("Price updated successfully to $" + String.format("%.2f", newPrice) + ".\n"); // display the success message with the new price
        }
        // catch the exception if the user enters an invalid value for the price
        catch (NumberFormatException ex) {
            updateMessageArea.append("Sorry, please enter a valid price.\n");
        } 
        // catch the exception if the user enters an index that is out of range, should never reach this as the index is checked before
        catch (IndexOutOfBoundsException ex) {
            updateMessageArea.append("Oops, unable to update investment - index out of range!\n");
        }
    }


    /**
     * method to create the gain panel
     * @return gainPanel the gain panel
     */
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
        JTextField gainTotalField = new JTextField(10); 
        gainTotalField.setPreferredSize(new Dimension(100, 25));
        gainTotalField.setEditable(false);  // not editable by the user
        totalGainPanel.add(gainTotalField);
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
        JTextArea gainIndividualArea = new JTextArea(15, 40); 
        gainIndividualArea.setEditable(false);  // not editable by the user
        gainIndividualArea.setLineWrap(true);  // wrap the text so that it fits in the text area
        gainIndividualArea.setWrapStyleWord(true); // wrap the text at words instead of letters, so that words are not split in half

        /* ~~ scroll pane for the text area ~~ */
        JScrollPane gainsScrollPane = new JScrollPane(gainIndividualArea);
        gainsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // always show the vertical scroll bar
        gainsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // always show the horizontal scroll bar
        gainsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT); 
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); 
        mainPanel.add(gainsScrollPane);

        gainPanel.add(mainPanel, BorderLayout.CENTER);

        // these set the total gain field and individual gains area to be used in the updateGainPanel method
        totalGainField = gainTotalField;
        individualGainsArea = gainIndividualArea;

        return gainPanel;
    }



    /**
     * method to update the gain panel with the latest portfolio data.
     */
    private void updateGainPanel(JTextField totalGainField, JTextArea individualGainsArea) {
        double realizedGain = portfolio.getTotalGain(); // get realized gains (from sold investments)
        double unrealizedGain = 0.0; // sum up gains from remaining investments

        String individualGainsText = ""; // initialize the text for the individual gains
        // loop through each investment in the portfolio to calculate individual gains
        for (int i = 0; i < portfolio.getInvestments().size(); i++) {
            Investment investment = portfolio.getInvestments().get(i); // get the investment
            double gain = investment.calculateGain(); // calculate the unrealized gain for the investment
            unrealizedGain = unrealizedGain + gain; // add to the total unrealized gain

            // add the investment details and gain to the text area
            individualGainsText = individualGainsText + investment.toString() + " | Gain: " + String.format("%.2f", gain) + "\n";
        }

        double totalGain = realizedGain + unrealizedGain; // calculate the total gain
        totalGainField.setText(String.format("%.2f", totalGain)); // update the total gain field
        individualGainsArea.setText(individualGainsText); // update the individual gains area
    }


    
    /**
     * method to create the search panel
     * @return searchPanel the search panel
     */
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
        highPricePanel.add(highPriceLabel);
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
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                symbolField.setText("");
                keywordsField.setText("");
                lowPriceField.setText("");
                highPriceField.setText("");
                searchMessageArea.setText("");
            }
        });
    
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInvestmentsAction(symbolField, keywordsField, lowPriceField, highPriceField); // call the searchInvestmentsAction method
            }
        });
    
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
    
    /**
     * method to create the search results panel
     * @return the search results panel
     */
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

    
    /**
     * method to handle the search action when the search button is clicked using the searchForInvestment method from my Portfolio class
     * @param symbolField symbol field
     * @param keywordsField keywords field
     * @param lowPriceField low price field
     * @param highPriceField high price field
     */
    private void searchInvestmentsAction(JTextField symbolField, JTextField keywordsField, JTextField lowPriceField, JTextField highPriceField) {
        
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
    
    
    /**
     * method to create the menu bar for the GUI holding all of the commands
     * @return the menu bar with the commands
     */
    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Commands"); // commands menu
        options.setFont(new Font(options.getFont().getName(), Font.PLAIN, 20));
        options.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 

        // menu items for the commands
        JMenuItem buyItem = new JMenuItem("Buy");
        JMenuItem sellItem = new JMenuItem("Sell");
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem gainItem = new JMenuItem("Get Gain");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem quitItem = new JMenuItem("Quit");


        // action listeners for the menu items to switch between the panels
        buyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "BUY"); // show the buy panel
            }
        });
        // action listener for the sell item to switch to the sell panel
        sellItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "SELL"); // show the sell panel
            }
        });
        // action listener for the update item to switch to the update panel
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (portfolio.getInvestments().isEmpty()) {
                    updateMessageArea.setText("No investments to display.");
                    updateSymbolField.setText("");
                    updateNameField.setText("");
                    updatePriceField.setText("");
                    prevButton.setEnabled(false);
                    nextButton.setEnabled(false);
                } 
                else {
                    index = 0; // start with the first investment
                    updateUpdatePanel(); // refresh the update panel with the current investment
                    cardLayout.show(panel, "UPDATE"); // show the update panel
                }
                
            }
        });
        
        // action listener for the search item to switch to the search panel
        searchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "SEARCH"); // show the search panel
            }
        });
        
        // action listener for the quit item to exit the program
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // exit the program
            }
        });
        // the gain action listener is a bit different as it needs to update the gain panel with the latest portfolio data when the menu item is clicked instead of a button
        gainItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGainPanel(totalGainField, individualGainsArea); 
                cardLayout.show(panel, "GAIN"); // update the gain panel with the latest portfolio data by calling it again to show
            }
        });
         

        // add the menu items to the options menu
        options.add(buyItem);
        options.add(sellItem);
        options.add(updateItem);
        options.add(gainItem);
        options.add(searchItem);
        options.add(quitItem);
        menuBar.add(options);


        return menuBar;
    }
}

    
    