package com.stocktrading;

import java.io.*;
import java.util.HashMap;

public class FileManager {
    public static void savePortfolio(Portfolio portfolio) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("portfolio.txt"))) {
            for (String stock : portfolio.getHoldings().keySet()) {
                writer.write(stock + "," + portfolio.getHoldings().get(stock));
                writer.newLine();
            }
        } catch (IOException e) {/**/}
    }

    public static HashMap<String, Integer> loadPortfolio() {
        HashMap<String, Integer> holdings = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("portfolio.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    holdings.put(tokens[0], Integer.parseInt(tokens[1]));
                }
            }
        } catch (IOException e) {/**/}
        return holdings;
    }
}