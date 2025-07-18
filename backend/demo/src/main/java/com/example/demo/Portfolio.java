package com.example.demo;


import java.util.ArrayList;
//import java.util.Arrays;
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
    private double keepTotalGain = 0.0; // total gain from selling investments

    /**
     * method to create an empty portfolio
     */
    public Portfolio(){
        this.investments = new ArrayList<>();
        this.mapKeyword = new HashMap<>();
        this.keepTotalGain = 0.0;
        //System.out.println("portfolio object is created"); // debugging print statement
        
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
        double currentGain = 0.0; // variable to store the current gain

        for (int i = 0; i < investments.size(); i++) {
            Investment investment = investments.get(i); // get the investment at index i
            currentGain = currentGain + investment.calculateGain(); // calculate the gain for the current investment
        }
        return keepTotalGain + currentGain; // return the total gain including the removed investments
    }


    /**
     * method to buy an investment and add it to the portfolio. If the investment already exists, just update quantity and price.
     * 
     * @param investment the investment thats added/updated
     */
    public void buyInvestment(Investment investment) {

            // loop through the investment list to check if the investment already exists
            for (int i = 0; i < investments.size(); i++) {
                Investment investCompare = investments.get(i); // get the investment at index i
        
                // if the investment matches, update the number of units and price
                if (investCompare.getSymbol().equalsIgnoreCase(investment.getSymbol())) {
                    investCompare.buy(investment.getQuantity(), investment.getPrice()); // polymorphic call to buy method
                    return; // return if the investment is found and updated
                }
            }
        
            // add the investment to the portfolio if it doesn't already exist
            investments.add(investment);
        
            // add the investment to the keyword map
            String[] keywords = investment.getName().split(" "); // split the investment name into keywords
            for (int i = 0; i < keywords.length; i++) {
                String keyword = keywords[i].trim().toLowerCase(); // get the keyword
        
                if (!keyword.isEmpty()) { // skip empty keywords
                    if (!mapKeyword.containsKey(keyword)) {
                        mapKeyword.put(keyword, new ArrayList<>()); // create a new list if the keyword doesn't exist
                    }
                    mapKeyword.get(keyword).add(investment); // add the investment to the keyword list
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
    public double sellInvestment(String symbol, int quantity, double price) {
        // loop through the investments list to find the one user wants to sell
        for (int i = 0; i < investments.size(); i++) {
            Investment investment = investments.get(i); // get the investment at index i
    
            // if the symbol matches, sell the specified number of units
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                double payment = investment.sell(quantity, price); // polymorphic call to sell method
    
                // if all units are sold, remove the investment and update total gain
                if (investment.getQuantity() == 0) {
                    keepTotalGain = keepTotalGain + (quantity * price) - investment.getBookValue(); // update the total gain
                    investments.remove(i); // remove the investment from the portfolio
    
                    // update the keyword map to remove the sold investment
                    String[] keywords = investment.getName().split(" "); // split the investment name into keywords
                    for (int j = 0; j < keywords.length; j++) {
                        String keyword = keywords[j].trim().toLowerCase(); // normalize the keyword
    
                        // if the keyword exists, remove the investment from its list
                        if (mapKeyword.containsKey(keyword)) {
                            ArrayList<Investment> keywordList = mapKeyword.get(keyword); // get the keyword's investment list
                            keywordList.remove(investment); // remove the investment from the list
    
                            // if the list is now empty, remove the keyword from the map
                            if (keywordList.isEmpty()) {
                                mapKeyword.remove(keyword);
                            }
                        }
                    }
                    i--; // decrement the index to account for the removed investment
                }
    
                return payment; // return the payment for the sold investment
            }
        }
        return -1; // return -1 if the investment was not found
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
        // check if there are investments in the portfolio
        if (investments != null && !investments.isEmpty()) {
            System.out.println("Updating Investment Prices: ");

            // loop through the investments and update the price for each one
            for (int i = 0; i < investments.size(); i++) {
                Investment investment = investments.get(i); // get the investment
                System.out.print("Enter new price for " + investment.getSymbol().toUpperCase() + ": $");
                double newPrice = scan.nextDouble(); // get the new price from the user
                scan.nextLine(); // consume the newline character

                // check if the price is negative
                if (newPrice < 0) {
                    System.out.println("\nOops, price cannot be negative.");
                } 
                // otherwise, update the price
                else {
                    investment.setPrice(newPrice); // update the price
                    System.out.println("Price updated for " + investment.getSymbol().toUpperCase());
                }
            }
        } 
        else {
            System.out.println("There are currently no investments in your portfolio to update.");
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
        String results = ""; // variable to store the search results
        ArrayList<Investment> matchedInvestments = new ArrayList<>(); // list of investments that match the search criteria

        // // debugging
        // System.out.println("all investments in the portfolio:");
        // for (Investment investment : investments) {
        //     System.out.println(investment.getSymbol() + " - Price: " + investment.getPrice());
        // }


        // tokenize keywords into individual words 
        if (!keywordsInput.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(keywordsInput, " ");
            HashSet<Investment> matchedKeyword = null;
            
            // loop through each keyword and add the investments that match
            while (tokenizer.hasMoreTokens()) {
                String keyword = tokenizer.nextToken().toLowerCase(); // get the next keyword

                // if the keyword is in the map, add the investments that match
                if(mapKeyword.containsKey(keyword)){
                    HashSet<Investment> keywordMatches = new HashSet<>(mapKeyword.get(keyword)); // investments for this keyword

                    // if this is the first keyword, add all the investments
                    if(matchedKeyword == null){
                        matchedKeyword = new HashSet<>(keywordMatches); 
                    } 
                    // otherwise, retain only the investments that match this keyword
                    else{
                        matchedKeyword.retainAll(keywordMatches);
                    }
                }
                // otherwise, no matches for this keyword
                else {
                    matchedKeyword = new HashSet<>(); // so set the list to empty
                    break; 
                }
            }
            // add the matched investments to the list
            if(matchedKeyword != null){
                matchedInvestments.addAll(matchedKeyword);
            }

        }
        // otherwise, add all investments to the list
        else {
            matchedInvestments.addAll(investments);
        }
        // filter the matched investments based on the symbol 
        if(!symbolInput.isEmpty()){
            // loop through the matched investments and remove the ones that don't match the symbol
            for(int i = matchedInvestments.size() -1; i >= 0; i--){
                Investment investment = matchedInvestments.get(i);
                if(!investment.getSymbol().equalsIgnoreCase(symbolInput)){
                    matchedInvestments.remove(i); // remove the investment if the symbol doesn't match
                }
            }
        }
        // // // debugging
        // // System.out.println("Matched investments after symbol filtering:");
        // for (Investment investment : matchedInvestments) {
        //     System.out.println(investment.getSymbol() + " - Price: " + investment.getPrice());
        // }


        // filter the matched investments based on the price range
        if (!priceRangeInput.isEmpty()) {
            for (int i = matchedInvestments.size() - 1; i >= 0; i--) {
                Investment investment = matchedInvestments.get(i); // get the investment at index i
                try {
                    // System.out.println("Checking price for investment: " + investment.getSymbol() + " - Price: " + investment.getPrice()); // debugging
                    if (!matchesPrice(investment.getPrice(), priceRangeInput)) {
                        //System.out.println("Investment does not match price range: " + priceRangeInput); // debugging
                        matchedInvestments.remove(i); // remove the investment if it doesn't match the price range
                    } 
                    else {
                        //System.out.println("Investment matches price range: " + priceRangeInput);
                    }
                } 
                catch (Exception e) {
                    System.out.println("Error while checking price for investment: " + investment.getSymbol());
                    e.printStackTrace(); // print the stack trace for debugging
                    matchedInvestments.remove(i); // exclude the investment if an error occurs
                }
            }
        }
        

        // // debugging
        // System.out.println("Final matched investments:");
        // for (Investment investment : matchedInvestments) {
        //     System.out.println(investment.getSymbol() + " - Price: " + investment.getPrice());
        // }

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

    // method to check if the price of the investment matches the inputted price range
    private boolean matchesPrice(double price, String priceRangeInput) {
        priceRangeInput = priceRangeInput.trim();
        // System.out.println("initial input: " + priceRangeInput); // debugging
    
        Double lowerRange = null;
        Double upperRange = null;
    
        if (priceRangeInput.isEmpty()) { 
            // System.out.println("price range is empty, returning true."); // debugging
            return true; // if no range is given, all prices are valid
        }
    
        String[] priceRangeSplit = priceRangeInput.split("-", -1); // include empty strings for missing bounds
        // System.out.println("split price range input: " + Arrays.toString(priceRangeSplit)); // debugging
    
        // handle missing lower or upper bounds
        if (priceRangeSplit.length == 2) {
            String lowerRangeString = priceRangeSplit[0].trim();
            String upperRangeString = priceRangeSplit[1].trim();
    
            // parse the lower range
            if (!lowerRangeString.isEmpty()) {
                try {
                    lowerRange = Double.valueOf(lowerRangeString);
                    // System.out.println("lower range: " + lowerRange); // debugging
                } 
                catch (NumberFormatException e) {
                    System.out.println("invalid lower range input: " + lowerRangeString);
                    return false;
                }
            }
    
            // parse the upper range
            if (!upperRangeString.isEmpty()) {
                try {
                    upperRange = Double.valueOf(upperRangeString);
                    // System.out.println("upper range: " + upperRange); // debugging
                } 
                catch (NumberFormatException e) {
                    System.out.println("Invalid upper range input: " + upperRangeString);
                    return false;
                }
            }
    
            // ensure lower range is not higher than upper range
            if (lowerRange != null && upperRange != null && lowerRange > upperRange) {
                System.out.println("this is an invalid range, lower range cannot be higher than upper range.");
                return false;
            }
        } 
        else {
            //System.out.println("invalid range input: " + priceRangeInput);
            return false; // invalid format
        }
    
        // // debug output to confirm parsed ranges
        // System.out.println("Price: " + price + ", Lower range: " + lowerRange + ", Upper range: " + upperRange);
    
        // check against the lower and upper range
        boolean isGreaterOrEqual = (lowerRange == null || price >= lowerRange);
        boolean isLessOrEqual = (upperRange == null || price <= upperRange);
    
        // // debug the range checks
        // System.out.println("isGreaterOrEqual: " + isGreaterOrEqual + ", isLessOrEqual: " + isLessOrEqual);
    
        // only return true if both conditions are met
        return isGreaterOrEqual && isLessOrEqual;
    }
    
    
    


}
