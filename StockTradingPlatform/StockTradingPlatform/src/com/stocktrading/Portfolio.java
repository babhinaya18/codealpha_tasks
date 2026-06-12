package com.stocktrading;

import java.util.HashMap;

public class Portfolio {
    private HashMap<String, Integer> holdings = new HashMap<>();

    public void buyStock(String stockName, int quantity) {
        holdings.put(stockName, holdings.getOrDefault(stockName, 0) + quantity);
    }

    public void sellStock(String stockName, int quantity) {
        if (holdings.containsKey(stockName)) {
            int currentQty = holdings.get(stockName);
            currentQty -= quantity;
            if (currentQty <= 0) {
                holdings.remove(stockName);
            } else {
                holdings.put(stockName, currentQty);
            }
        }
    }

    public HashMap<String, Integer> getHoldings() { return holdings; }
    public void setHoldings(HashMap<String, Integer> holdings) { this.holdings = holdings; }
}