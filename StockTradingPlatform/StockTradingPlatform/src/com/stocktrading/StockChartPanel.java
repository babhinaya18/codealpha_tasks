package com.stocktrading;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StockChartPanel extends JPanel {
    private ArrayList<Double> priceHistory = new ArrayList<>();
    private String stockName = "";

    public void updateChart(String stockName, ArrayList<Double> history) {
        this.stockName = stockName;
        this.priceHistory = new ArrayList<>(history);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(24, 26, 32));
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (priceHistory == null || priceHistory.size() < 2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.drawString("Select an asset row to generate asset metrics chart.", 30, getHeight() / 2);
            return;
        }

        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        for (double p : priceHistory) {
            if (p < minPrice) minPrice = p;
            if (p > maxPrice) maxPrice = p;
        }

        if (maxPrice == minPrice) {
            maxPrice += 10;
            minPrice -= 10;
        }

        int padding = 40;
        int graphWidth = getWidth() - (2 * padding);
        int graphHeight = getHeight() - (2 * padding);

        g2.setColor(new Color(240, 165, 0));
        g2.setStroke(new BasicStroke(2.5f));

        for (int i = 0; i < priceHistory.size() - 1; i++) {
            int x1 = padding + (i * graphWidth / (priceHistory.size() - 1));
            int y1 = padding + graphHeight - (int) ((priceHistory.get(i) - minPrice) * graphHeight / (maxPrice - minPrice));
            int x2 = padding + ((i + 1) * graphWidth / (priceHistory.size() - 1));
            int y2 = padding + graphHeight - (int) ((priceHistory.get(i + 1) - minPrice) * graphHeight / (maxPrice - minPrice));

            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1 - 3, y1 - 3, 6, 6);
            if (i == priceHistory.size() - 2) {
                g2.fillOval(x2 - 3, y2 - 3, 6, 6);
            }
        }
    }
}