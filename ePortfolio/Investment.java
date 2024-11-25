package ePortfolio;

/**
 * class to represent an investment in the portfolio
 */
public class Investment {
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
     * method to calculate the total value of the investment
     * @param price the price of the investment
     */
    public void setBookValue(double price) {
        if(!isFromFile){
            this.bookValue = this.quantity * price;
        }
    }

    /** 
     * method to get the total value of the investment for files
     * @param bookValue the total value of the investment
     */
    public void setBookValueFromFile(double bookValue) {
        this.bookValue = bookValue;
        this.isFromFile = true;
    }
    
    /**
     * method to buy more shares of an investment and update quantity, price, and book value
     * @return the profit from the sale
     */
    public double getBookValue() {
        return bookValue;
    }
    
}
