package ePortfolio;

/**
 * class to represent an investment in the portfolio
 */
public abstract class Investment {
    private String symbol;        // symbol of the investment
    private String name;          // name of the investment
    private int quantity;         // quantity of the investment
    private double price;         // price of the investment
    protected double bookValue;   // total value of the investment
    protected boolean isFromFile; // boolean to check if the investment is from a file


    /**
     * method to create an investment with an inputted symbol, name, quantity, price, and book value
     * @param symbol the symbol of the investment
     * @param name the name of the investment
     * @param quantity the quantity of the investment
     * @param price the price of the investment
     * @param bookValue the total value of the investment
     */
    public Investment(String symbol, String name, int quantity, double price, boolean isFromFile){
        this.symbol = symbol; 
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isFromFile = isFromFile;
        if(!isFromFile){
            setBookValue(price);
        }
    }

    /**
     * abstract method to sell units of an investment.
     * 
     * @param quantity the number of units to sell
     * @param price the price per unit
     * @return the payment received from selling the investment
     */
    public abstract double sell(int quantity, double price);

    /**
     * abstract method to buy units of an investment and update its state.
     * 
     * @param quantity the number of units to buy
     * @param price the price per unit
     */
    public abstract void buy(int quantity, double price);



    /**
     * method to get the symbol of the investment
     * @return the symbol of the investment
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * method to get the name of the investment
     * @return the name of the investment
     */    
    public String getName() {
        return name;
    }

    /**
     * method to get the quantity of the investment
     * @return the quantity of the investment
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * method to get the price of the investment
     * @return the price of the investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * method to set the price of the investment
     * @param price the new price of the investment
     */
    public void setPrice(double price) {
        this.price = price; // set the new price
        if(!isFromFile){
            setBookValue(price);
        }
    }

    /**
     * method to set the quantity of the investment
     * @param quantity the new quantity of the investment
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity; // set the new quantity
        if(!isFromFile){
            setBookValue(price);
        }
    }

    /**
     * abstract method to get a formatted string representing the investment
     * 
     * @return the formatted string
     */
    @Override
    public abstract String toString();

    /**
     * abstract method to update the book value of the investment
     * 
     * @param price the current price
     */
    public abstract void setBookValue(double price);

    /** 
     * method to get the total value of the investment for files
     * @param bookValue the total value of the investment
     */
    public void setBookValueFromFile(double bookValue) {
        this.bookValue = bookValue;
        this.isFromFile = true;
    }

    /**
     * abstract method to calculate the gain of the investment
     * 
     * @return the total gain
     */
    public abstract double calculateGain();
    
    /**
     * method to buy more shares of an investment and update quantity, price, and book value
     * @return the profit from the sale
     */
    public double getBookValue() {
        return bookValue;
    }
    
}
