package ePortfolio;

/**
 * class to represent a stock investment in the portfolio
 */
public class Stock extends Investment {

    // ~~~~ OLD VARIABLES MOVED TO INVESTMENT SUPERCLASS ~~~~
    // private String stockSymbol;         // stock's symbol (i.e. AAPL)
    // private String stockName;           // name of the company
    // private int shareQuantity;          // amount of shares owned
    // private double sharePrice;          // the current price per share
    // private double bookValue;           // total value of the shares based on purchase price
    
    
    /** the fixed commision fee for buying and selling shares */
    private double commissionFee = 9.99; // commission fee for buy/sell

    /**
     * method to create a stock with an inputted symbol, name, quantity, and price
     * 
     * @param symbol the symbol of the stock
     * @param name the name of the stock
     * @param quantity the quantity of shares
     * @param price the price per share
     * @param isFromFile boolean to check if the stock is from a file
     */
    public Stock(String symbol, String name, int quantity, double price, boolean isFromFile){
        super(symbol, name, quantity, price, isFromFile); // call the superclass constructor
    }
    
    /**
     * method to update the book value
     * 
     * @param sharePrice the price per share
     */
    @Override
    public void setBookValue(double sharePrice){
        if(!isFromFile){
            this.bookValue = (getQuantity() * sharePrice) + commissionFee;
        }
    }

    /**
     * method to get the book value of the stock
     * @return the book value of the stock
     */
    public double getBookValue() {
        return this.bookValue;
    }

    /** 
     * method to set the book value of the stock directly for files
     * @param bookValue the book value of the stock
     */
    public void setBookValueFromFile(double bookValue) {
        this.bookValue = bookValue;
        this.isFromFile = true;
    }


    /**
     * method to buy more shares of a stock and update quantity, price, and book value
     * 
     * @param shareQuantity number of shares being bought
     * @param sharePrice current price per unit
     */
    public void buyStock(int shareQuantity, double sharePrice){
        setQuantity(getQuantity() + shareQuantity); // increase amount of shares owned
        setPrice(sharePrice); // update book value
    }

    /**
     * method to sell shares of a stock and update quantity, price, and book value 
     * @param shareQuantity number of shares being sold
     * @param sharePrice current price per share
     * @return the profit from the sale
     */
    @Override
    public double sell(int shareQuantity, double sharePrice){
        double sellProfit = (shareQuantity * sharePrice) - commissionFee; // calculate payment
        int oldQuantity = getQuantity();
        setQuantity(oldQuantity - shareQuantity); // decrease amount of shares owned
        setPrice(sharePrice); // update price of the shares

        // if there are still shares left, update the book value
        if (getQuantity() > 0) {
            this.bookValue = this.bookValue * ((double) getQuantity() / oldQuantity);
        } 
        else {
            setBookValue(0); // set to 0 if all shares are sold
        } 

        return sellProfit; // return the payment amount for sold shares     
    }

    

    /**
     * method to calculate the gain for a stock
     * 
     * @return the total gain
     */
    @Override
    public double calculateGain() {
        return (getQuantity() * getPrice()) - (bookValue + commissionFee); // gain = (current value - book value)
    }

    /**
     * method to format a string for the stock that shows all of its details
     * 
     * @return the formatted string
     */
    @Override
    public String toString(){
        return "Stock: " + getName() + " (" + getSymbol().toUpperCase() + "), Quantity: " + getQuantity() + ", Price: $" + String.format("%.2f", getPrice()) + ", Book Value: $" + String.format("%.2f", getBookValue());
    }
}
