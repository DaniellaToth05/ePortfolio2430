package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows frontend to connect (adjust for production)
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
}
