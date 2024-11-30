# CIS*2430-F24 Assignment 1 ~ ePortfolio 
Name: Daniella Toth
Student ID: 1261398

## (1) Assignment Overview
This program is designed for investors to manage their investments through an enhanced ePortfolio. Users can buy, sell, update prices, calculate total gains, and search for investments (stocks or mutual funds) using a user-friendly GUI.

## (2) Assumptions and Limitations 
   • Assumed that users only use the ePortfolio to manage either stocks or mutual funds

   • Assumed that two investments cannot have the same symbol within the portfolio

   • Assumed $9.99 commission fee for buy or sell transactions on stocks

   • Assumed $45.00 redemption fee for sell transactions on mutual funds

   • Assumed manual input for all data 

   • Limited asset types: stocks and mutual funds

   • Limited functionalities for investments: sell, buy, update, getGain, and search

   • Limited transaction history: prrogram does not keep a log of transactions, must be manually tracked through the terminal

## (3) User Guide
   • Compile Command: javac ePortfolio/*.java
   • Execute Command: java ePortfolioGUI
   # Using the Program #
   After executing the program, users are faced with the following options via the GUI:
      • buy     ~  Buy an investment (stock/mutual fund)
      • sell    ~  Sell an investment (stock/mutual fund)
      • update  ~  Update prices of investments
      • getGain ~  Calculate total gain
      • search  ~  Search for an investment
      • quit    ~  End Program
    Users can access each functionality through the "Commands" menu at the top of the GUI. Selecting an option, such as "Buy" or "Search," will open the corresponding panel where users can use the functionality within each panel

    Description of each choice:
        buy
          • Users select either "Stock" or "Mutual Fund," then enter details such as symbol, name, quantity, and price.
          • Pressing the "buy" button will add this investment to the portfolio, or add more shares/units if the symbol exists already in the portfolio
          • Pressing the "reset" button will reset all fields to blank, so its easier to enter the next investment
          • Any error messages/prompts, or success messages will be shown in the "messages" text area on the bottom half of the GUI
        sell
          • Users provide the symbol, quantity, and price for the sale. 
          • Pressing the "sell" button will sell however many units/shares they entered for the price they entered. 
          • If all shares/units are sold, the investment is removed.
          • Pressing the "reset" button will reset all fields to blank, so its easier to enter the next investment
          • Any error messages/prompts, or success messages will be shown in the "messages" text area on the bottom half of the GUI
        update
          • User is prompted to enter a new price for each investment in the portfolio
          • The "next" button will move forward in the list of investments to the next investment, and will be disabled once the user reaches the end of the list
          • The "prev" button will move backwards in the list of investments to the previous investment, and will be disabled once the user reaches the first investment in the list
          • The "save" button will save the price the user enters in the price field as the new price for the corresponding investment in the list
          • Any error messages/prompts, or success messages will be shown in the "messages" text area on the bottom half of the GUI
        getGain
          • calculates the total gain (or loss) for all investments in the portfolio, shown in the "total gain" text area.
          • The details of each individual investment's gain will be displayed in the text area on the bottom half of the GUI
          • The gain is refreshed each time the user chooses this option from the menu, no button is needed to be pressed
        search
          • User is prompted to enter a symbol, keyword, and price range (lower and upper) to search through investments (each input is optional)
          • Pressing the "search" button will search for the investment using the fields the user inputs
          • All investments that match their search will be displayed in the "search results" text area at the bottom half of the GUI
          • Pressing the "reset" button will reset all fields to blank, so its easier to enter the next investment's search requirements
        quit
          • Exits the program

## (4) Test Plan
    Test GUI Interactions:
      • Test all GUI components for correct display and functionality with buttons.
      • Verify proper updates to the portfolio after buy, sell, and update actions.
      • Ensure error messages display for invalid inputs in all menu options, as well as success messages.
    Testing Buy Investment
      • Test the buy option to ensure that it can correctly add a new investment to the portfolio, and buy additional shares/units of an investment
    Test Cases:
      • Add a new stock 
           ~ test with entering a valid symbol, name, quantity, and price, and verify that the stock was added by searching for the investment. Expected outcome is that the investment that was inputted is displayed in search with all the correct details.  
      • Add a new mutual fund
           ~ test with entering a valid symbol, name, quantity, and price, and verify that the mutual fund was added by searching for the investment. Expected outcome is that the investment that was inputted is displayed in search with all the correct details.
      • Buy additional shares and units of an investment that already exists
           ~ test by inputting a symbol that already exists, and buy additional quantities, and verify more shares where bought by searching for the investment. Expected outcome is that the investment that was inputted is displayed in search with an updated amount of shares/units.
      • Test for invalid input
           ~ test by entering an invalid symbol, such as one that already exists in the opposing type list (if buying stock, try entering a symbol thats already in the mutual funds list). Test by entering an invalid quantity (<=0), and an invalid price (<0). Expected that the program should print a message saying that these inputs are not allowed.
    
    Testing Sell Investment
      • Test the sell option to ensure that it can correctly sell shares/units of stocks/mutual funds and adjust the portfolio
    Test Cases:
      • Sell part of a stock
           ~ Test by selling a portion of a stock and verify that the remaining quantity and book value are correct. Expected that the quantity is decreased and the sale profit is a gain based on updated price - commision fee.
      • Sell part of a mutual fund
           ~ Test by selling a portion of a mutual fund and verify that the remaining quantity and book value are correct. Expected that the quantity is decreased and the sale profit is a gain based on updated price - commision fee.
      • Sell all of a stock/mutual fund
           ~ Test by selling all shares/units of a stock/mutual fund, and verify that it gets deleted from the portfolio by searching for the investment. Expected that the quantity is zero and the sale profit is a gain based on updated price - commision fee
      • Test for invalid input
           ~ Test by entering invalid prices (>0). Expected that the program should print a message saying that the price cannot be negative 

    Testing Update Prices
      • Test the update option to ensure that it correctly prompts for a new price for each investment in the portfolio
    Test Cases:
      • Update prices with only stocks in the list
           ~ Test by updating prices of stocks before buying any mutual funds, expected that the program should accurately update the prices of each stock, but print a message saying there are no mutual funds to update
      • Update prices with only mutual funds in the list
           ~ Test by updating prices of mutual funds before buying any mutual funds, expected that the program should accurately update the prices of each mutual fund, but print a message saying there are no mutual funds to update 
      • Update prices with both stocks and mutual funds in the list
           ~ Test by buying both stocks and funds first, and then ensuring that the program asks for a new price for each and every investment, as expected.
      • Test for invalid input
           ~ Test by entering invalid prices (>0). The program should print a message saying that the price cannot be negative, as expected. 

    Testing Total Gain
      • Test the calculate total gain option to ensure that it correctly calculate the total gain of all investments in the portfolio.
    Test Cases:
      • Total gain with just one investment
           ~ Test using the gain calculation after selling a portion of a stock or mutual fund, and expected outcome is that the total is accurate
      • Total gain with multiple investments
           ~ Test using the gain calculation after selling multiple stocks or funds in a portfolio, and expected outcome is that the total is accurate
      • Total gain with no investments
           ~ Test using the gain calculation without selling any stocks or funds. Expected outcome is that it should reflect only the fees.

    Testing Search Investment
      • Test the search option to ensure that it correctly returns a match based on symbols, names, and price ranges, as well as a search with all options
    Test Cases:
      • Search by Symbol:
           ~ Search using a symbol that exists in the portfolio and expected outcome is that it returns the correct investment
      • Search by keyword:
           ~ Search using a keyword that exists within a stock/fund name, and expected outcome is that it returns the correct investment
      • Search by exact price:
           ~ Search using an exact price (eg. $110.08), and expected outcome is that it returns the exact investments with only that price
      • Search by price ranges:
           ~ Search using a price range (eg. 100 - 200), as well as lower ranges (-100) and upper ranges (200-), and expected outcome is that all investments returned fall within those price ranges
      • Test for invalid input
           ~ Search with invalid inputs (Symbols, keywords, prices that do not exist in portfolio), and expected outcome is that the program outputs a message saying that no match could be found

    Testing Edge Case
      • Empty portfolio:
           ~ Test that the program handles each relevant command when there are no investments in the portfolio. Expected outcome for each is an error message relevant to each command.
        
    Testing Error Handling Menu
           ~ Test that the menu rejects any command that isn't acceptable, but still practices defensive programming. Expected outcome is a message that says that the input was not valid and to choose a valid option
           
## (5) Potential Improvements
    • Add more asset types, in addition to stocks and mutual funds
    • Add a transaction history functionality
    • Customizable fees
    • Add investment categories (such as industry)
    • Include more options for search (such as by category)
    • Allow users to set goals for their investments


## (6) Resources I Used to complete this assignment
    • https://www.javatpoint.com/java-swing 
    • https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
    • https://swaminathanj.github.io/oop/27_2_Java_Swing_tutorial.pdf 
    • https://www.youtube.com/watch?app=desktop&v=5o3fMLPY7qY