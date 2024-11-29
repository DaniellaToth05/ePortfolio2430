package ePortfolio;

/**
 * class to represent a mutual fund investment in the portfolio
 */
public class MutualFund extends Investment{
    
    // ~~~~ OLD VARIABLES MOVED TO INVESTMENT SUPERCLASS ~~~~
    // private String mutualFundSymbol;        // mutual fund symbol
    // private String mutualFundName;          // mutual fund name
    // private int unitQuantity;               // number of units
    // private double unitPrice;               // price for each unit 
    // private double bookValue;               // total value when bought
    
    
    private double redemptionFee = 45.00;   // fee for selling mutual funds

    /**
     * method to create a mutual fund with an inputted symbol, name, quantity, and price
     * 
     * @param symbol the symbol of the mutual fund
     * @param name the name of the mutual fund
     * @param quantity the number of units bought
     * @param price the price per unit
     * @param isFromFile boolean to check if the mutual fund is from a file
     */
    public MutualFund(String symbol, String name, int quantity, double price, boolean isFromFile){
        super(symbol, name, quantity, price, isFromFile); // call the superclass constructor
    }

    /**
     * method to set the book value of the mutual fund
     * 
     * @param sharePrice the current price per unit
     */
    @Override
    public void setBookValue(double unitPrice){
        if(!isFromFile){
            this.bookValue = getQuantity() * unitPrice;
        }
    }

    /**
     * method to get the book value of the mutual fund
     * @return the book value of the mutual fund
     */
    public double getBookValue() {
        return this.bookValue;
    }

    /**
     * method to set the book value of the mutual fund directly for files
     * @param bookValue the book value of the mutual fund
     */
    public void setBookValueFromFile(double bookValue) {
        this.bookValue = bookValue;
        this.isFromFile = true;
    }

    /**
     * method to buy more units of a mutual fund and update quantity, price, and book value
     * 
     * @param unitQuantity number of units being bought
     * @param unitPrice current price per unit
     */
    @Override
    public void buy(int unitQuantity, double unitPrice){
        setQuantity(getQuantity() + unitQuantity); // increase amount of units owned    
        setPrice(unitPrice); // update book value
    }

    /**
     * method to sell units of a mutual fund and update quantity, price, and book value
     * 
     * @param unitQuantity number of units that are being sold
     * @param unitPrice current price per unit
     * @return the profit from the sale
     */
    @Override
    public double sell(int unitQuantity, double unitPrice){
        double sellProfit = (unitQuantity * unitPrice) - redemptionFee; // calculate payment
        int oldQuantity = getQuantity();
        setQuantity(oldQuantity - unitQuantity); // decrease amount of units owned
        setPrice(unitPrice); // update price of the units

        // if there are still units left, update the book value
        if (getQuantity() > 0) {
            this.bookValue = this.bookValue * ((double) getQuantity() / oldQuantity);
        } 
        else {
            setBookValue(0); // set to 0 if all units are sold
        } 

        return sellProfit; // return the payment amount for sold units
    }

    /**
     * method to calculate the gain for a mutual fund
     * 
     * @return the total gain
     */
    @Override
    public double calculateGain(){
        double gain;
        gain = (getQuantity() * getPrice()) - (bookValue - redemptionFee); // gain = (current value - book value)
        return gain;
    }

    /**
     * method to format a string for the mutual fund that shows all of its details
     * 
     * @return the formatted string
     */
    @Override
    public String toString(){
        return "Mutual fund: " + getName() + " (" + getSymbol().toUpperCase() + "), Quantity: " + getQuantity() + ", Price: $" + String.format("%.2f", getPrice()) + ", Book Value: $" + String.format("%.2f", getBookValue());
    }
}
