package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class PortfolioController {

    private Portfolio portfolio = new Portfolio();

    // GET request to fetch the full portfolio
    @GetMapping("/portfolio")
    public Portfolio getPortfolio() {
        return portfolio;
    }

    // POST request to buy a stock
    @PostMapping("/buyStock")
    public String buyStock(@RequestBody Stock stock) {
        portfolio.buyInvestment(stock);
        return "Successfully bought " + stock.getQuantity() + " shares of " + stock.getSymbol();
    }

    // POST request to sell a stock
    @PostMapping("/sellStock")
    public String sellStock(@RequestBody SellRequest sellRequest) {
        double payment = portfolio.sellInvestment(
            sellRequest.getSymbol(),
            sellRequest.getQuantity(),
            sellRequest.getPrice()
        );
        if (payment == -1) {
            return "Sell failed. Investment not found.";
        }
        return "Successfully sold " + sellRequest.getQuantity() + " shares of " + sellRequest.getSymbol();
    }


    // GET request to search for a stock
    @GetMapping("/searchInvestments")
    public String searchInvestments(
            @RequestParam(required = false, defaultValue = "") String symbol,
            @RequestParam(required = false, defaultValue = "") String keywords,
            @RequestParam(required = false, defaultValue = "") String priceRange) {

        return portfolio.searchForInvestment(symbol.trim(), keywords.trim(), priceRange.trim());
    }
}
