package ePortfolio;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * main class for the eportfolio to take user input and manage investments in the portfolio
 */
public class Main {

    /** an instance of the portfolio class to store investments */
    private static Portfolio portfolio = new Portfolio(); // create an instance of Portfolio

    /**
     * main method that runs the program
     * displays a menu and takes user's commands to manage their investments
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args){

        if(args.length < 1){
            System.out.println("\nPlease enter a filename");
            return;
        }

        String filename = args[0];                      // get the filename from the command line   
        Scanner scan = new Scanner(System.in);          // scanner for user's input
        boolean contin = true;                          // boolean that controls the main loop
        String userChoice;                              // variable to hold the user's choice
        String buyChoice, sellChoice;                   // hold choice for buy and sell commands
        String name, symbol;                            // hold investment symbol & name
        int quantity;                                   // hold number of shares/units
        double price = 0.0;                             // hold prices of shares/units
        double saleProfit;                              // hold profit for investments
        double totalGain;                               // holds the total gain
        Investment sellingInvestment = null;            // variable to hold the fund if the fund exists already

        // read the file
        try{
            File file = new File(filename); 
            if(!file.exists()){
                System.out.println("\nFile does not exist - creating a new file");
                file.createNewFile();
            }
            Scanner fileScan = new Scanner(file);       
            Investment investment = null;    
            
            // variables to hold the values of the investment
            String type = "";
            String fileSymbol = "";
            String fileName = "";
            int fileQuantity = 0;
            double filePrice = 0.0;
            boolean bookValueGiven = false;
            double fileBookValue = 0.0;

            // read the file line by line
            while(fileScan.hasNextLine()){
                String line = fileScan.nextLine();

                // if the line contains the type of investment
                if(line.contains("type =")){
                    type = line.split(" = ")[1].replace("\"", "").trim(); // get the type of investment
                }
                // if the line contains the symbol of the investment
                else if (line.contains("symbol =")){
                    fileSymbol = line.split(" = ")[1].replace("\"", "").trim(); // get the symbol of the investment
                }
                // if the line contains the name of the investment
                else if(line.contains("name =")){
                    fileName = line.split(" = ")[1].replace("\"", "").trim(); // get the name of the investment
                }
                // if the line contains the quantity of the investment
                else if(line.contains("quantity =")){
                    fileQuantity = Integer.valueOf(line.split(" = ")[1].replace("\"", "").trim()); // get the quantity of the investment
                }
                // if the line contains the price of the investment
                else if(line.contains("price =")){
                    filePrice = Double.valueOf(line.split(" = ")[1].replace("\"", "").trim()); // get the price of the investment
                }
                // if the line contains the book value of the investment
                else if (line.contains("bookValue =")) {
                    String bookValueStr = line.split(" = ")[1].replace("\"", "").trim();
                    
                    // if the book value is given, set the book value and set the boolean to true
                    if (!bookValueStr.isEmpty()) {
                        fileBookValue = Double.valueOf(bookValueStr);
                        bookValueGiven = true;
                    } 
                    else {
                        bookValueGiven = false; // if the book value is not given, set the boolean to false
                    }
        
                    // if the type is stock, create a stock object
                    if (type.equalsIgnoreCase("stock")) { 
                        investment = new Stock(fileSymbol, fileName, fileQuantity, filePrice, true); 
                        // if the book value is given, set it directly
                        if (bookValueGiven) {
                            ((Stock) investment).setBookValueFromFile(fileBookValue);
                        }
                    } 
                    // if the type is mutual fund, create a mutual fund object
                    else if (type.equalsIgnoreCase("mutual fund")) {
                        investment = new MutualFund(fileSymbol, fileName, fileQuantity, filePrice, true);
                        // if the book value is given, set it directly
                        if (bookValueGiven) {
                            ((MutualFund) investment).setBookValueFromFile(fileBookValue);
                        }
                    }
        
                    // // add the investment to the portfolio
                    // if (bookValueGiven && investment != null) {
                    //     investment.setBookValue(fileBookValue); // set the book value of the investment from the value in the file
                    // }

                    // add the investment to the portfolio
                    if(investment != null){
                        portfolio.buyInvestment(investment);
                    }
                }
            } 
            fileScan.close(); // close the file scanner
            System.out.println("\nFile read successfully\n"); 
        }
        // catch if the file is not found
        catch(FileNotFoundException e){
            System.out.println("\nFile not found");
        }
        // catch if there is an issue reading the file
        catch(Exception e){
            System.out.println("\nError reading file");
        }

        System.out.println("|*********************************|");
        System.out.println("|   Welcome to your ePortfolio!   |");
        System.out.println("|*********************************|");
        

        // loop continues until user exits program (sets to false) 
        while (contin){
            // menu options
            System.out.println("\nPlease choose a command option from the menu:");
            System.out.println("---------------------------------------------");
            System.out.println("buy     ~  Buy an investment (stock/mutual fund)");
            System.out.println("sell    ~  Sell an investment (stock/mutual fund)");
            System.out.println("update  ~  Update prices of investments");
            System.out.println("getGain ~  Calculate total gain");
            System.out.println("search  ~  Search for an investment");
            System.out.println("quit    ~  End Program\n");

            // take the user's choice
            System.out.print("Enter command: ");
            userChoice = scan.nextLine();
            userChoice = userChoice.toLowerCase(); // read the user's command and convert it to lowercase
            System.out.print("\n");
            

            // choice 1: buy investment
            if ((userChoice.equals("buy") || userChoice.equals("b"))){
                System.out.println("Option 1 selected: Buy an investment\n");
                System.out.print("Choose type of investment (stock or mutual fund): ");;
                buyChoice = scan.nextLine(); // get investment type choice
                buyChoice.toLowerCase();
                System.out.print("\n");

                Investment investment = null; // create an investment object

                // get investment details from user
                System.out.println("Enter investment symbol: ");
                symbol = scan.nextLine();

                // check if the symbol already exists in the portfolio
                boolean inPortfolio = false;
                for(int i = 0; i < portfolio.getInvestments().size(); i++){
                    if(portfolio.getInvestments().get(i).getSymbol().equalsIgnoreCase(symbol)){
                        inPortfolio = true;
                        break;
                    }
                }
                // if the symbol already exists in the portfolio
                if(inPortfolio){
                    System.out.println("Sorry, this symbol already exists in your portfolio! Please choose a different symbol.");
                }
                else{
                    System.out.print("Enter investment name: ");
                    name = scan.nextLine();
                    System.out.print("\n");

                    if(buyChoice.equals("stock") || buyChoice.equals("s")){ // buy a stock
                        // get stock details from user and create a stock object
                        System.out.print("Enter quantity of shares: ");
                        quantity = scan.nextInt();
                        scan.nextLine();
                        // if the user tries buy no or negative amount of shares
                        if (quantity <= 0){
                            System.out.println("\nOops, you can't purchase " + quantity + " shares!");
                        }
                        else {
                            System.out.print("Enter price per share: $");
                            price = scan.nextDouble();
                            scan.nextLine();
                            // if the user tries to buy a share with a negative price
                            if (price < 0){
                                System.out.println("\nOops, you can't purchase shares for $" + price + "!");
                            }
                            else{
                                investment = new Stock(symbol, name, quantity, price, false); // create a new stock object
                            }
                        }
                    }
                    else if(buyChoice.equals("mutual fund") || buyChoice.equals("m") || buyChoice.equals("fund") || buyChoice.equals("mutual") || buyChoice.equals("mutualfund")){ // buy mutual fund
                        // get mutual fund details from user and create a mutual fund object
                        System.out.print("Enter quantity of units: ");
                        quantity = scan.nextInt();
                        scan.nextLine();
                        if (quantity <= 0){
                            System.out.println("\nOops, you can't purchase " + quantity + " units!");
                        }
                        else {
                            System.out.print("Enter price per unit: $");
                            price = scan.nextDouble();
                            scan.nextLine();
                            if (price < 0){
                                System.out.println("\nOops, you can't purchase units for $" + price + "!");
                            }
                            else { 
                                investment = new MutualFund(symbol, name, quantity, price, false); // create a new mutual fund object
                            }
                        }
                    }
                    else {
                        System.out.println("Invalid choice, please try again. ");
                    }

                    investment.setBookValue(price); // set the book value of the investment
                    portfolio.buyInvestment(investment); // add the investment to the portfolio
                    System.out.println("\nInvestment added successfully!\n");
                } 
            }

            // choice 2: sell investments
            else if(userChoice.equals("sell")){
                System.out.println("Option 2 selected: Sell an investment\n");
            
                System.out.print("Choose type of investment (stock or mutual fund): ");
                sellChoice = scan.nextLine(); // get investment type choice
                System.out.print("\n");
                sellChoice.toLowerCase();

                if (portfolio.getInvestments().isEmpty()){
                    System.out.println("Sorry, there are currently no investments in your portfolio to sell.");
                }
                else {
                    System.out.print("Enter stock symbol to sell: ");
                    symbol = scan.nextLine();

                    // check if stock exists in the portfolio
                    for (int i = 0; i < portfolio.getInvestments().size(); i++){
                        if(portfolio.getInvestments().get(i).getSymbol().equalsIgnoreCase(symbol)){
                            sellingInvestment = portfolio.getInvestments().get(i); // stock does exist already
                            break;
                        }
                    }

                    // if the stock does not exist in the portfolio
                    if(sellingInvestment == null){
                        System.out.println("Sorry, we couldn't find a stock to match: " + symbol.toUpperCase() + ".\n");
                    }
                    else{
                        if(sellChoice.equals("stock") || sellChoice.equals("s")){ // sell stock
                            // get amount to sell
                            System.out.print("Enter number of shares to sell: ");
                            quantity = scan.nextInt();
                            scan.nextLine();
                            if(quantity <= 0){
                                System.out.println("\nOops, you can't sell " + quantity + " shares!");
                            }
                            else {
                                // check if theres enough shares to sell
                                if (quantity > sellingInvestment.getQuantity()){
                                    System.out.println("\nSorry, you don't have enough shares to sell! You currently have " + sellingInvestment.getQuantity() + " shares.");
                                }
                                else{
                                    // get the price per share
                                    System.out.print("Enter price per share: $");
                                    price = scan.nextDouble();
                                    scan.nextLine();
                                    if (price < 0){
                                        System.out.println("\nOops, you can't sell a share for $" + price + "!");
                                    }
                                    else {
                                        saleProfit = portfolio.sellInvestment(symbol, quantity, price); // sell the stock
                                        
                                        // print the sale results
                                        if(saleProfit > 0){
                                            System.out.printf("\nStock sold for: $" + String.format("%.2f", saleProfit));
                                            System.out.println("\n");
                                        } 
                                    }  
                                }
                            }
                        }
                        else if (sellChoice.equals("mutual fund") || sellChoice.equals("m") || sellChoice.equals("mutual") || sellChoice.equals("fund")|| sellChoice.equals("mutualfund")) { // sell mutual fund

                            // check if the portfolio is empty
                            if (portfolio.getInvestments().isEmpty()){
                                System.out.println("Sorry, there are currently no mutual funds in your portfolio to sell.");
                            }
                            else {
                                System.out.print("Enter mutual fund symbol to sell: ");
                                symbol = scan.nextLine();
                        
                                
                        
                                // check if the mutual fund exists
                                for (int i = 0; i < portfolio.getInvestments().size(); i++) {
                                    if (portfolio.getInvestments().get(i).getSymbol().equalsIgnoreCase(symbol)) {
                                        sellingInvestment = portfolio.getInvestments().get(i); // found fund
                                        break;
                                    }
                                }
                        
                                // mutual fund does not exist
                                if (sellingInvestment == null) {
                                    System.out.println("Sorry, we couldn't find a mutual fund to match: " + symbol.toUpperCase() + ".\n");
                                } 
                                else {
                                    // get amount to sell
                                    System.out.print("Enter number of units to sell: ");
                                    quantity = scan.nextInt();
                                    scan.nextLine();

                                    // if the user tries selling none or less than no shares
                                    if (quantity <= 0){
                                        System.out.println("\nOops, you can't sell " + quantity + " shares!");
                                    }
                                    else {
                                        // check if theres enough
                                        if (quantity > sellingInvestment.getQuantity()) {
                                            System.out.println("\nSorry, you don't have enough units to sell! You currently have " + sellingInvestment.getQuantity() + " units.");
                                        } 
                                        else {
                                            // get the price to sell 
                                            System.out.print("Enter price per unit: $");
                                            price = scan.nextDouble();
                                            scan.nextLine();
                                            
                                            // if the user tries to sell units for less than 0$
                                            if (price < 0){
                                                System.out.println("\nOops, you can't sell units for $" + price + "!");
                                            }
                                            else {
                                                saleProfit = portfolio.sellInvestment(symbol, quantity, price);
                                                
                                                // print the sale results
                                                if (saleProfit > 0) {
                                                    System.out.printf("\nMutual fund sold for: $" + String.format("%.2f", saleProfit));
                                                    System.out.println("\n");
                                                }
                                            } 
                                        }
                                    } 
                                }
                            }
                        }  
                    }
                }
            }
            // choice 3: update prices of investments
            else if((userChoice.equals("update")||userChoice.equals("u"))){
                System.out.println("Option 3 selected: Update prices of investments\n");

                // update investment prices
                portfolio.updateInvestmentPrices(scan);

            }

            // choice 4: calculate the total gain
            else if((userChoice.equals("getgain")||userChoice.equals("g")||userChoice.equals("gain"))){
                System.out.println("Option 4 selected: Calculate total gain\n");
                totalGain = portfolio.calculateTotalGain(); // call method to calculate gain and store result
                System.out.printf("Total Gain: %.2f%n", totalGain);
            }

            // choice 5: search for an investment
            else if(userChoice.equals("search")){
                System.out.println("Option 5 selected: Search for an investment\n");

                System.out.println("Enter fields to search for, leave blank otherwise: ");

                // prompt user for symbol
                System.out.print("Enter the Investment symbol: ");
                String symbolInput = scan.nextLine().trim();

                // prompt user for keywords
                System.out.print("Enter keyword(s): ");
                String keywordsInput = scan.nextLine().trim();

                // prompt user for price range
                System.out.print("Enter a price, or a price range (e.g., 10.00-100.00): ");
                String priceRangeInput = scan.nextLine().trim();

                String searchResults = portfolio.searchForInvestment(symbolInput, keywordsInput, priceRangeInput);

                // display the search results
                System.out.println("\nSearch results: \n" + searchResults);

            }

            // choice 6: exit the program
            else if((userChoice.equals("quit")) || userChoice.equals("q")){
                contin = false; // end the program
                System.out.println("Exiting the program. Thank you!\n");

                try{
                    PrintWriter writer = new PrintWriter(filename);
                    Investment investment;

                    for(int i = 0; i < portfolio.getInvestments().size(); i++){
                        investment = portfolio.getInvestments().get(i);

                        if(investment instanceof Stock){
                            writer.println("type = \"stock\"");
                        }
                        else if(investment instanceof MutualFund){
                            writer.println("type = \"mutual fund\"");
                        }
                        writer.println("symbol = \"" + investment.getSymbol() + "\""); // write the symbol
                        writer.println("name = \"" + investment.getName() + "\""); // write the name
                        writer.println("quantity = \"" + investment.getQuantity() + "\""); // write the quantity
                        writer.println("price = \"" + investment.getPrice() + "\""); // write the price
                        
                        // if the investment is a stock
                        if(investment instanceof Stock){
                            Stock stock = (Stock) investment;
                            writer.println("bookValue = \"" + stock.getBookValue() + "\""); // write the book value
                        }
                        // if the investment is a mutual fund
                        else if(investment instanceof MutualFund){
                            MutualFund fund = (MutualFund) investment;
                            writer.println("bookValue = \"" + fund.getBookValue() + "\""); // write the book value
                        }
                        writer.println(); // add a blank line
                    }
                    // close the writer
                    writer.close();
                    System.out.println("File saved successfully");
                }
                // catch if the file is not found
                catch(FileNotFoundException e){
                    System.out.println("File not found");

                }
            }

            else if(userChoice.equals("s")){
                System.out.println("Sorry, did you mean 'search', or 'sell'?");
            }

            // invalid input
            else {
                System.out.println("Not a valid option, please choose from the commands: buy, sell, update, getGain, search, or quit.");
            }

        }
        scan.close(); // close scanner
    }

    }
