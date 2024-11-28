package ePortfolio;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.HashSet;

/**
 * class to represent a portfolio that stores investments of stocks and mutual funds
 */
public class Portfolio {
    private ArrayList<Investment> investments; // list of investments in the portfolio
    private HashMap<String, ArrayList<Investment>> mapKeyword; // map of keywords to investments
    private double keepTotalGain; // total gain from selling investments

    /**
     * method to create an empty portfolio
     */
    public Portfolio(){
        this.investments = new ArrayList<>();
        this.mapKeyword = new HashMap<>();
        this.keepTotalGain = 0.0;
        
    }

    /**
     * method to get the list of investments in the portfolio
     * 
     * @return the list of investments
     */
    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    /**
     * method to get the total gain so that it counts gains that are removed from the portfolio
     * @return the total gain
     */
    public double getTotalGain() {
    return keepTotalGain;
    }


    /**
     * method to buy an investment and add it to the portfolio. If the investment already exists, just update quantity and price.
     * 
     * @param investment the investment thats added/updated
     */
    public void buyInvestment( Investment investment){
        
        // loop through the investment list to check if the investment already exists
        for (int i = 0; i < investments.size(); i++){
            Investment investCompare = investments.get(i); // get the investment at index i

            // if the investment matches, then update the number of units and price 
            if (investCompare.getSymbol().toLowerCase().equals(investment.getSymbol())){
                // if both investments are stocks, then buy the stock
                if(investCompare instanceof Stock && investment instanceof Stock){
                    // cast the investments to stocks
                    Stock stock = (Stock) investCompare; 
                    Stock stockInvestment = (Stock) investment;
                    // buy the stock
                    stock.buyStock(stockInvestment.getQuantity(), stockInvestment.getPrice());
                }
                // if both investments are mutual funds, then buy the mutual fund
                else if(investCompare instanceof MutualFund && investment instanceof MutualFund){
                    // cast the investments to mutual funds
                    MutualFund fund = (MutualFund) investCompare;
                    MutualFund fundInvestment = (MutualFund) investment;
                    // buy the mutual fund
                    fund.buyFundUnits(fundInvestment.getQuantity(), fundInvestment.getPrice());
                }
                // // update the keyword map
                // String[] keywords = investCompare.getName().split(" "); // split the stock name into keywords
                // for(int j = 0; j < keywords.length; j++){ // loop through the keywords
                //     String keyword = keywords[j].toLowerCase(); // get the keyword
                //     if(this.mapKeyword.containsKey(keyword)){ // if the keyword is in the map
                //         this.mapKeyword.get(keyword).add(investment); // add the stock to the keyword list
                //     }
                //     else{ // otherwise.. 
                //         ArrayList<Investment> keywordList = new ArrayList<>(); // create a new keyword list
                //         keywordList.add(investment); // add the stock to the list
                //         this.mapKeyword.put(keyword, keywordList); // add the keyword to the map
                //     }
                // }
                return; // return if the stock is found
            }
        }
        // add the stock to portfolio
        investments.add(investment);

        // add the iinvestment to the keyword map
        String[] keywords = investment.getName().split(" ");
        // loop through the keywords and add the investment to the map
        for (int i = 0; i < keywords.length; i++){
            String keyword = keywords[i].trim().toLowerCase(); // get the keyword

            if(keyword.isEmpty()){
                continue;
            }

            if (this.mapKeyword.containsKey(keyword)){ // if the keyword is in the map
                ArrayList<Investment> existingKeyWordList = this.mapKeyword.get(keyword); // get the list of investments

                boolean investmentExists = false; // check if the investment already exists in the list
                for(int j = 0; j < existingKeyWordList.size(); j++){
                    Investment existingInvestment = existingKeyWordList.get(j); // get the investment at index j
                    if(existingInvestment.getSymbol().equalsIgnoreCase(investment.getSymbol())){ // if the investment already exists
                        investmentExists = true; // set the flag to true
                        break; // break the loop
                    }
                }
                if(!investmentExists){ // if the investment doesn't exist
                    existingKeyWordList.add(investment); // add the stock to the keyword list
                }
            }
            else {
                ArrayList<Investment> keywordList = new ArrayList<>(); // create a new keyword list
                keywordList.add(investment); // add the stock to the list
                this.mapKeyword.put(keyword, keywordList); // add the keyword to the map
            }
        }
    }


    /**
     * method to sell units of an investment from the portfolio. If the number of units reaches zero, delete the investment from the portfolio.
     * 
     * @param symbol the symbol of the investment 
     * @param quantity the number of units to sell
     * @param price the price of each unit
     * @return the payment received from selling the investment, or -1 if the investment couldn't be found in the list
     */
    public double sellInvestment(String symbol, int quantity, double price){
        // loop through stocks list to find the one user wants to sell
        for (int i = 0; i < investments.size(); i++){
            Investment investment = investments.get(i); // get the stock at index i
            
            // if the symbol matches, sell the number of shares
            if (investment.getSymbol().equalsIgnoreCase(symbol)){
                double payment = 0.0;
                // if the investment is a stock, sell the stock
                if (investment instanceof Stock){
                    Stock stock = (Stock) investment; // cast the investment to a stock
                    payment = stock.sellStock(quantity, price); // sell stock if symbol matches

                    // remove it from the portfolio if the number of shares is zero
                    if(stock.getQuantity() == 0){
                        keepTotalGain = keepTotalGain + stock.calculateGain(); // add the gain to the total gain
                        for(int j = 0; j < investments.size(); j++){
                            if(investments.get(j).equals(stock)){
                                investments.remove(j); // remove the mutual fund from the list
                                break;
                            }
                        }
                        String[] keywords = stock.getName().split(" "); 

                        for(int k = 0; k < keywords.length; k++){
                            String keyword = keywords[k].trim().toLowerCase(); // get the keyword

                            // if the keyword is empty, skip it
                            if(keyword.isEmpty()){
                                continue;
                            }

                            // if the keyword is in the map, remove the stock from the keyword list
                            if(mapKeyword.containsKey(keyword)){
                                ArrayList<Investment> keywordList = mapKeyword.get(keyword); // get the list of investments

                                // to remove the stock from the keyword list
                                for(int l = 0; l < keywordList.size(); l++){
                                    if(keywordList.get(l).equals(stock)){ // if the stock is found
                                        keywordList.remove(l); // remove the stock
                                        break;
                                    }
                                }
                                // remove the keyword from the map if the list is empty
                                if(keywordList.isEmpty()){
                                    mapKeyword.remove(keyword);
                                }
                            }
                        }
                    }
                    return payment; // return the payment
                }
                // if the investment is a mutual fund, sell the mutual fund
                else if (investment instanceof MutualFund){
                    MutualFund fund = (MutualFund) investment; // cast the investment to a mutual fund
                    payment = fund.sellFundUnits(quantity, price); // sell fund if symbol matches

                    // remove it from portfolio if number of units is zero
                    if(fund.getQuantity() == 0){
                        keepTotalGain = keepTotalGain + fund.calculateGain(); // add the gain to the total gain
                        for(int j = 0; j < investments.size(); j++){
                            if(investments.get(j).equals(fund)){
                                investments.remove(j); // remove the mutual fund from the list
                                break;
                            }
                        }
                        // remove the mutual fund from the keyword map
                        String[] keywords = fund.getName().split(" ");
                        for(int k = 0; k < keywords.length; k++){
                            String keyword = keywords[k].trim().toLowerCase(); // get the keyword

                            // if the keyword is empty, skip it
                            if(keyword.isEmpty()){
                                continue;
                            }

                            // if the keyword is in the map, remove the stock from the keyword list
                            if(mapKeyword.containsKey(keyword)){
                                ArrayList<Investment> keywordList = mapKeyword.get(keyword); // get the list of investments

                                // to remove the stock from the keyword list
                                for(int l = 0; l < keywordList.size(); l++){
                                    if(keywordList.get(l).equals(fund)){ // if the stock is found
                                        keywordList.remove(l); // remove the stock
                                        break;
                                    }
                                }
                                // remove the keyword from the map if the list is empty
                                if(keywordList.isEmpty()){
                                    mapKeyword.remove(keyword);
                                }
                            }
                        }
                    }
                    return payment;
                }
            }
        }
        return -1; // stock not found
    }
    

    /**
     * method to calculate the total gain from all investments in the portfolio
     * 
     * @return the total gain/loss
     */
    public double calculateTotalGain() {
        double totalGain = 0.0;

        // calculate total gain from investments
        if (investments != null && investments.size() > 0) {
            for (int i = 0; i < investments.size(); i++) { // iterate through the list of investments
                Investment investment = investments.get(i); // get the investment at index i

                // use polymorphism to calculate the gain for the current investment
                double gain = investment.calculateGain(); // calculate the gain using the overridden method
                totalGain = totalGain + gain; // add the gain to the total gain
            }
        }

        return totalGain; // return the total gain
    }


    /**
     * method to update the price of all investments in the portfolio
     * 
     * @param scan input for the new investment prices
     */
    public void updateInvestmentPrices(Scanner scan) {

        // check if there are stocks in the list
        if(investments != null && !investments.isEmpty()){
            System.out.println("Updating Investment Prices: ");
            for (int i = 0; i < investments.size(); i++) {
                Investment investment = investments.get(i);
                System.out.print("Enter new price for " + investment.getSymbol().toUpperCase() + ": $");
                double newPrice = scan.nextDouble(); // get new price from user
                scan.nextLine();
                if (newPrice < 0){
                    System.out.println("\nOops, shares/units cannot be a negative price.");
                }
                else {
                    investment.setPrice(newPrice); // update the stock price
                    System.out.println("\nPrice updated for " + investment.getSymbol().toUpperCase());
                }
            }
        }
        else {
            System.out.println("There are currently no stocks in your portfolio to update.");
        }
        
    }

    

    /**
     * method to search for investments based on the user input for symbol, keywords, and price range (each are optional).
     * 
     * @param symbolInput the symbol of the investment to search for
     * @param keywordsInput hte keywords to search for 
     * @param priceRangeInput the price to search for
     * @return the search results
     */
    public String searchForInvestment(String symbolInput, String keywordsInput, String priceRangeInput) {
        String results = "";
        ArrayList<Investment> matchedInvestments = new ArrayList<>();

        // tokenize keywords into individual words 
        if (!keywordsInput.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(keywordsInput, " ");
            HashSet<Investment> matchedKeyword = new HashSet<>();
            
            // loop through each keyword and add the investments that match
            while (tokenizer.hasMoreTokens()) {
                String keyword = tokenizer.nextToken().toLowerCase(); // get the next keyword

                // if the keyword is in the map, add the investments that match
                if(mapKeyword.containsKey(keyword)){
                    ArrayList<Investment> investmentsKeyword = mapKeyword.get(keyword);

                    // add the investments that match the keyword
                    for(int i = 0; i < investmentsKeyword.size(); i++){
                        Investment currentInvestment = investmentsKeyword.get(i);
                        matchedKeyword.add(currentInvestment);

                    }
                }
            }
            // add the matched investments to the list
            matchedInvestments.addAll(matchedKeyword);
        }
        else {
            // if no keywords are given, add all investments to the list
            for(int i = 0; i < investments.size(); i++){
                matchedInvestments.add(investments.get(i));
            }
            
        }

        // filter the matched investments based on the symbol 
        if(!symbolInput.isEmpty()){
            // loop through the matched investments and remove the ones that don't match the symbol
            for(int i = matchedInvestments.size() -1; i >=0; i--){
                Investment investment = matchedInvestments.get(i);
                if(!investment.getSymbol().equalsIgnoreCase(symbolInput)){
                    matchedInvestments.remove(i);
                }
            }
        }
        // filter the matched investments based on the price range
        if(!priceRangeInput.isEmpty()){
            // loop through the matched investments and remove the ones that don't match the price range
            for(int i = matchedInvestments.size() -1; i >=0; i--){
                Investment investment = matchedInvestments.get(i);
                if(!matchesPrice(investment.getPrice(), priceRangeInput)){
                    matchedInvestments.remove(i);
                }
            }
        }

        // loop through the matched investments and add the ones that match the symbol and price range
        for(int i = 0; i < matchedInvestments.size(); i++){
            Investment investment = matchedInvestments.get(i);

            // add the investment to the results if it matches the symbol and price range
            if(matchesInvestment(investment, symbolInput, priceRangeInput)){
                results = results + investment.toString() + "\n";
            }
        }

        // no matches found
        if (results.isEmpty()) {
            results = "Sorry, no investments match your search. ";
        }

        return results; // return the search results
    }

    // method to check if an investment matches the symbol and price range
    private boolean matchesInvestment(Investment investment, String symbolInput, String priceRangeInput) {
        // check if the symbol matches 
        boolean symbolMatches = true;
        if (!symbolInput.isEmpty()) {
            symbolMatches = investment.getSymbol().equalsIgnoreCase(symbolInput);
        }

        // // check if the keywords match the stock name 
        // boolean keywordMatches = true;
        // if (!keywords.isEmpty()) {
        //     keywordMatches = matchesKeywords(investment.getName(), keywords);
        // }

        // check if the price matches 
        boolean priceMatches = true;
        if (!priceRangeInput.isEmpty()) {
            priceMatches = matchesPrice(investment.getPrice(), priceRangeInput);
        }

        // return true only if all conditions are met
        if (symbolMatches && priceMatches){
            return true;
        }
        else {
            return false;
        }
    }


    // // check if the investment name contains all the keywords 
    // private boolean matchesKeywords(String investmentName, ArrayList<String> keywords) {
        
    //     for(int i = 0; i < keywords.size(); i++){
    //         String keyword = keywords.get(i);

    //         if(!mapKeyword.containsKey(keyword)){
    //             return false;
    //         }

    //         ArrayList<Investment> investmentKeywordList = mapKeyword.get(keyword);
    //         boolean found = false;

    //         for(int j = 0; j < investmentKeywordList.size(); j++){
    //             Investment investment = investmentKeywordList.get(j);
    //             if(investment.getName().equalsIgnoreCase(investmentName)){
    //                 found = true;
    //                 break;
    //             }
    //         }

    //         if(!found){
    //             return false;
    //         }
    //     }
        
        // StringTokenizer nameTokenizer = new StringTokenizer(investmentName.toLowerCase(), " ");
        // ArrayList<String> nameTokens = new ArrayList<>();

        // // tokenize the investment name
        // while (nameTokenizer.hasMoreTokens()) {
        //     nameTokens.add(nameTokenizer.nextToken());
        // }

        // // check if all keywords are present in the name tokens
        // for (int i = 0; i < keywords.size(); i++) {
        //     if (!nameTokens.contains(keywords.get(i))) {
        //         return false;
        //     }
        // }

    //     return true;
    // }

    // check if the price of the investment matches the inputted price range
    private boolean matchesPrice(double price, String priceRangeInput) {
        // split the price range input into lower and upper ranges
        priceRangeInput = priceRangeInput.trim();

        Double lowerRange = null;
        Double upperRange = null;

        if(priceRangeInput.isEmpty()){
            return true; // if no range is given then all prices are valid
        }

        String[] priceRangeSplit = priceRangeInput.split("-"); // split the input into lower and upper range based on - symbol

        // if theres only one number its an exact price
        if(priceRangeSplit.length == 1){
            String exactPriceString = priceRangeSplit[0].trim();
            if(!exactPriceString.isEmpty()){
                double exactPrice = Double.valueOf(exactPriceString);
                if(price == exactPrice){
                    return true;
                }
                else{
                    return false;
                }
            }
        }

        // if it has two parts then its a range
        if(priceRangeSplit.length == 2){
            String lowerRangeString = priceRangeSplit[0].trim();
            if(!lowerRangeString.isEmpty()){
                lowerRange = Double.valueOf(lowerRangeString);
            }

            String upperRangeString = priceRangeSplit[1].trim();
            if(!upperRangeString.isEmpty()){
                upperRange = Double.valueOf(upperRangeString);
            }
        }

        // check against the lower range
        boolean isGreaterOrEqual;
        if (lowerRange == null) {
            isGreaterOrEqual = true; // no lower range means any price is valid
        } else {
            isGreaterOrEqual = (price >= lowerRange); // check if price is greater than or equal to lowerRange
        }

        // check against the upper range
        boolean isLessOrEqual;
        if (upperRange == null) {
            isLessOrEqual = true; // no upper range means any price is valid
        } else {
            isLessOrEqual = (price <= upperRange); // check if price is less than or equal to upperRange
        }

        boolean isPriceInRange = isGreaterOrEqual && isLessOrEqual;

        // return the final result
        return isPriceInRange;
    }
}


