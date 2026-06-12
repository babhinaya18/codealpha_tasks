package com.stocktrading;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class TradingGUI extends JFrame {

    private String currentUsername;
    private User user;
    private Portfolio portfolio;
    private double initialNetWorth = 100000.0;

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel sidebarPanel;

    private JLabel balanceLabel, portfolioValueLabel, profitLossLabel;
    private JTable marketTable, portfolioTable, transactionTable;
    private DefaultTableModel portfolioModel, transactionModel, marketModel;
    private DefaultListModel<String> watchlistModel;
    private JList<String> watchlistComponent;
    private StockChartPanel chartRenderer;

    private JLabel walletBalanceDisplayLabel;
    private HashMap<String, ArrayList<Double>> historyEngine = new HashMap<>();
    private HashMap<String, Integer> trajectoryMatrix = new HashMap<>();
    private Color activeSidebarBtnColor = new Color(24, 26, 32);

    public TradingGUI(String username) {
        this.currentUsername = username;
        String[] details = DatabaseManager.getUserDetails(username);
        this.user = new User(username, details[0], details[1], DatabaseManager.getBalance(username));
        this.portfolio = new Portfolio();

        HashMap<String, Integer> dbHoldings = DatabaseManager.loadPortfolio(username);
        if (!dbHoldings.isEmpty()) {
            portfolio.setHoldings(dbHoldings);
        } else {
            portfolio.setHoldings(FileManager.loadPortfolio());
        }

        setTitle("VSTread Trading Terminal");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(10, 11, 14));
        setLayout(new BorderLayout());

        String[] columns = {"Asset Code", "Spot Price Value (₹)"};
        Object[][] data = {
                {"TCS", 3500.0},
                {"Infosys", 1600.0},
                {"Reliance", 2900.0},
                {"HDFC", 1800.0}
        };

        for (Object[] row : data) {
            String sym = (String) row[0];
            double val = ((Number) row[1]).doubleValue();
            ArrayList<Double> hList = new ArrayList<>();
            hList.add(val);
            historyEngine.put(sym, hList);
            trajectoryMatrix.put(sym, 0);
        }

        // 1. Sidebar Setup
        createSidebar();

        // 2. Main Content Card Workspace View
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(18, 19, 26));

        contentPanel.add(buildMarketPanel(data, columns), "Home");
        contentPanel.add(buildPortfolioPanel(), "Portfolio");
        contentPanel.add(buildWatchlistPanel(), "Watchlist");
        contentPanel.add(buildTransactionPanel(), "Activity");
        contentPanel.add(buildWalletPanel(), "Wallet");
        contentPanel.add(buildProfilePanel(), "Profile");

        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Sync startup storage datasets
        ArrayList<String> dbWatchlist = DatabaseManager.loadWatchlist(user.getUsername());
        for (int i = 0; i < dbWatchlist.size(); i++) {
            watchlistModel.addElement(dbWatchlist.get(i));
        }

        ArrayList<String[]> dbLogs = DatabaseManager.loadTransactions(user.getUsername());
        for (int i = 0; i < dbLogs.size(); i++) {
            String[] log = dbLogs.get(i);
            transactionModel.addRow(new Object[]{log[0], log[1], log[2], log[3]});
        }

        refreshPortfolioTable();
        updatePortfolioValue();
        startMarketSimulation();
        setVisible(true);
    }

    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridBagLayout());
        sidebarPanel.setBackground(new Color(10, 11, 14));
        sidebarPanel.setPreferredSize(new Dimension(240, 0));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.DARK_GRAY));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 12, 6, 12);
        c.gridx = 0; c.gridy = 0;

        // Logo Header
        JLabel logoLabel = new JLabel("VSTread");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        logoLabel.setForeground(new Color(240, 165, 0));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebarPanel.add(logoLabel, c);

        // Navigation Items
        c.gridy++; addSidebarButton("Home / Market", "Home", c);
        c.gridy++; addSidebarButton("Portfolio Tracker", "Portfolio", c);
        c.gridy++; addSidebarButton("Watchlist Desk", "Watchlist", c);
        c.gridy++; addSidebarButton("Activity Logs", "Activity", c);
        c.gridy++; addSidebarButton("My Wallet Balance", "Wallet", c);
        c.gridy++; addSidebarButton("User Profile", "Profile", c);

        // Separator vertical glue push
        c.gridy++;
        c.weighty = 1.0;
        sidebarPanel.add(Box.createGlue(), c);

        // Exit button at the bottom frame
        c.weighty = 0.0;
        c.gridy++;
        JButton logoutBtn = new JButton("Logout Session");
        logoutBtn.setPreferredSize(new Dimension(200, 42));
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 13));
        logoutBtn.setBackground(new Color(45, 20, 20));
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        sidebarPanel.add(logoutBtn, c);
    }

    private void addSidebarButton(String name, String cardTarget, GridBagConstraints c) {
        JButton btn = new JButton(name);
        btn.setPreferredSize(new Dimension(210, 45));
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBackground(new Color(10, 11, 14));
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addActionListener(e -> {
            cardLayout.show(contentPanel, cardTarget);
            for (Component comp : sidebarPanel.getComponents()) {
                if (comp instanceof JButton && comp.getForeground() != Color.RED) {
                    comp.setBackground(new Color(10, 11, 14));
                    comp.setForeground(Color.LIGHT_GRAY);
                }
            }
            btn.setBackground(activeSidebarBtnColor);
            btn.setForeground(Color.WHITE);
        });

        sidebarPanel.add(btn, c);
    }

    private JPanel buildMarketPanel(Object[][] data, String[] columns) {
        JPanel pane = new JPanel(new BorderLayout(20, 20));
        pane.setBackground(new Color(18, 19, 26));
        pane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Stats Panel Top Layout
        JPanel header = new JPanel(new GridLayout(1, 3, 20, 0));
        header.setOpaque(false);

        balanceLabel = createStatCard("Clear Available Funds", "₹" + user.getBalance(), Color.GREEN);
        portfolioValueLabel = createStatCard("Current Holdings Value", "₹0.00", Color.CYAN);
        profitLossLabel = createStatCard("Total Net Return Profit/Loss", "₹0.00", Color.WHITE);

        header.add(balanceLabel);
        header.add(portfolioValueLabel);
        header.add(profitLossLabel);
        pane.add(header, BorderLayout.NORTH);

        // Center split grids
        JPanel centerGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        centerGrid.setOpaque(false);

        marketModel = new DefaultTableModel(data, columns);
        marketTable = new JTable(marketModel);
        configureTableTheme(marketTable);
        JScrollPane scrollTable = new JScrollPane(marketTable);
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
        centerGrid.add(scrollTable);

        // Right side graph area view with an inline trading desk dashboard below it
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setOpaque(false);
        chartRenderer = new StockChartPanel();
        rightPanel.add(chartRenderer, BorderLayout.CENTER);

        // FIX: Added direct integrated Order execution panel right into the views next to chart
        JPanel inlineOrderDesk = new JPanel(new GridLayout(1, 3, 10, 0));
        inlineOrderDesk.setOpaque(false);
        JButton inlineBuy = createControlBtn("Place Buy Order", Color.GREEN);
        JButton inlineSell = createControlBtn("Place Sell Order", Color.RED);
        JButton inlineWatch = createControlBtn("Add to Trackers", Color.GRAY);

        inlineOrderDesk.add(inlineBuy);
        inlineOrderDesk.add(inlineSell);
        inlineOrderDesk.add(inlineWatch);
        rightPanel.add(inlineOrderDesk, BorderLayout.SOUTH);

        centerGrid.add(rightPanel);
        pane.add(centerGrid, BorderLayout.CENTER);

        // Advisory actions row
        JPanel lowerBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowerBar.setOpaque(false);
        JButton runAdvisor = createControlBtn("Run Momentum Trend Advisor", Color.ORANGE);
        lowerBar.add(runAdvisor);
        pane.add(lowerBar, BorderLayout.SOUTH);

        marketTable.getSelectionModel().addListSelectionListener(e -> {
            int row = marketTable.getSelectedRow();
            if (row != -1) {
                String sym = marketTable.getValueAt(row, 0).toString();
                chartRenderer.updateChart(sym, historyEngine.get(sym));
            }
        });

        inlineBuy.addActionListener(e -> processBuyOrderRequest());
        inlineSell.addActionListener(e -> {
            cardLayout.show(contentPanel, "Portfolio"); // Redirect user to their portfolio grid list
        });
        inlineWatch.addActionListener(e -> {
            int row = marketTable.getSelectedRow();
            if (row == -1) return;
            String sym = marketTable.getValueAt(row, 0).toString();
            if (!watchlistModel.contains(sym)) {
                watchlistModel.addElement(sym);
                DatabaseManager.addToWatchlist(user.getUsername(), sym);
            }
        });
        runAdvisor.addActionListener(e -> executeAdvisoryTrendsEngine());

        return pane;
    }

    private JPanel buildPortfolioPanel() {
        JPanel pane = new JPanel(new BorderLayout(20, 20));
        pane.setBackground(new Color(18, 19, 26));
        pane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        portfolioModel = new DefaultTableModel(new String[]{"Asset Position Item", "Total Unit Volume Base Held"}, 0);
        portfolioTable = new JTable(portfolioModel);
        configureTableTheme(portfolioTable);

        JScrollPane sp = new JScrollPane(portfolioTable);
        sp.setBorder(BorderFactory.createEmptyBorder());
        pane.add(sp, BorderLayout.CENTER);

        // Proper actionable row panel containing clear layout sell functions buttons
        JPanel actionsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsRow.setOpaque(false);
        JButton sellBtn = createControlBtn("Liquidate Chosen Stock Position", Color.RED);
        actionsRow.add(sellBtn);
        pane.add(actionsRow, BorderLayout.SOUTH);

        sellBtn.addActionListener(e -> processSellOrderRequest());

        return pane;
    }

    private JPanel buildWatchlistPanel() {
        JPanel pane = new JPanel(new BorderLayout(20, 20));
        pane.setBackground(new Color(18, 19, 26));
        pane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        watchlistModel = new DefaultListModel<>();
        watchlistComponent = new JList<>(watchlistModel);
        watchlistComponent.setBackground(new Color(24, 26, 32));
        watchlistComponent.setForeground(Color.WHITE);
        watchlistComponent.setFont(new Font("Arial", Font.PLAIN, 15));
        watchlistComponent.setFixedCellHeight(40);
        watchlistComponent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane sp = new JScrollPane(watchlistComponent);
        sp.setBorder(BorderFactory.createEmptyBorder());
        pane.add(sp, BorderLayout.CENTER);
        return pane;
    }

    private JPanel buildTransactionPanel() {
        JPanel pane = new JPanel(new BorderLayout(20, 20));
        pane.setBackground(new Color(18, 19, 26));
        pane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        transactionModel = new DefaultTableModel(new String[]{"Order Execution Context", "Asset Symbol", "Units Delta Traded", "Executed Rate Value (₹)"}, 0);
        transactionTable = new JTable(transactionModel);
        configureTableTheme(transactionTable);

        JScrollPane sp = new JScrollPane(transactionTable);
        sp.setBorder(BorderFactory.createEmptyBorder());
        pane.add(sp, BorderLayout.CENTER);
        return pane;
    }

    private JPanel buildWalletPanel() {
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(18, 19, 26));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        walletBalanceDisplayLabel = new JLabel("Net Cash Balance Availability Pool: ₹" + user.getBalance(), SwingConstants.CENTER);
        walletBalanceDisplayLabel.setFont(new Font("Arial", Font.BOLD, 20));
        walletBalanceDisplayLabel.setForeground(Color.GREEN);

        JButton depositFundsBtn = new JButton("Deposit Cash Funds Liquidity");
        depositFundsBtn.setPreferredSize(new Dimension(240, 45));
        depositFundsBtn.setBackground(new Color(24, 26, 32));
        depositFundsBtn.setForeground(Color.WHITE);
        depositFundsBtn.setFocusPainted(false);
        depositFundsBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        depositFundsBtn.addActionListener(e -> {
            String val = JOptionPane.showInputDialog(this, "Deposit Amount (₹):");
            if (val != null && !val.isEmpty()) {
                try {
                    double amt = Double.parseDouble(val);
                    user.setBalance(user.getBalance() + amt);
                    DatabaseManager.updateBalance(user.getUsername(), user.getBalance());
                    updatePortfolioValue();
                    walletBalanceDisplayLabel.setText("Net Cash Balance Availability Pool: ₹" + user.getBalance());
                } catch (Exception ex) {/**/}
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        pane.add(walletBalanceDisplayLabel, gbc);
        gbc.gridy = 1;
        pane.add(depositFundsBtn, gbc);

        return pane;
    }

    private JPanel buildProfilePanel() {
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(18, 19, 26));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("User Client Identity Specifications Matrix");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(240, 165, 0));

        JLabel nameLabel = new JLabel("Client Name Identifier:  " + user.getFullName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(Color.WHITE);

        JLabel mailLabel = new JLabel("System Electronic Mail Address:  " + user.getEmail());
        mailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        mailLabel.setForeground(Color.WHITE);

        JLabel statusLabel = new JLabel("Jurisdiction Origin Segment Status: International Active Desk Profile Tier 1");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(Color.LIGHT_GRAY);

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        pane.add(titleLabel, c);
        c.gridy = 1; c.gridwidth = 1;
        pane.add(nameLabel, c);
        c.gridy = 2;
        pane.add(mailLabel, c);
        c.gridy = 3;
        pane.add(statusLabel, c);

        return pane;
    }

    private JLabel createStatCard(String headText, String value, Color verticalLineAccent) {
        JLabel card = new JLabel("<html><body style='padding:10px;'><font color='#8A8D93' size='3'>" + headText + "</font><br><br><font color='white' size='6'><b>" + value + "</b></font></body></html>");
        card.setOpaque(true);
        card.setBackground(new Color(24, 26, 32));
        card.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, verticalLineAccent));
        return card;
    }

    private void configureTableTheme(JTable targetTable) {
        targetTable.setRowHeight(38);
        targetTable.setBackground(new Color(24, 26, 32));
        targetTable.setForeground(Color.WHITE);
        targetTable.setGridColor(new Color(40, 44, 52));
        targetTable.setSelectionBackground(Color.BLACK);
        targetTable.setSelectionForeground(Color.GREEN);
        targetTable.getTableHeader().setBackground(Color.BLACK);
        targetTable.getTableHeader().setForeground(Color.WHITE);
        targetTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        targetTable.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private JButton createControlBtn(String txt, Color lineAccent) {
        JButton btn = new JButton(txt);
        btn.setBackground(new Color(24, 26, 32));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(lineAccent, 1));
        btn.setPreferredSize(new Dimension(220, 42));
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        return btn;
    }

    private void processBuyOrderRequest() {
        int targetRow = marketTable.getSelectedRow();
        if (targetRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a market spot rate asset row item first.");
            return;
        }

        String targetStockSymbol = marketTable.getValueAt(targetRow, 0).toString();
        double currentPricingUnit = Double.parseDouble(marketTable.getValueAt(targetRow, 1).toString());
        String quantityValueInput = JOptionPane.showInputDialog(this, "Units to purchase:");
        if (quantityValueInput == null || quantityValueInput.isEmpty()) return;

        try {
            int itemsVol = Integer.parseInt(quantityValueInput);
            double aggregationCostSummary = currentPricingUnit * itemsVol;
            if (user.getBalance() < aggregationCostSummary) {
                JOptionPane.showMessageDialog(this, "Insufficient core capital liquidity.");
                return;
            }

            user.setBalance(user.getBalance() - aggregationCostSummary);
            portfolio.buyStock(targetStockSymbol, itemsVol);
            transactionModel.insertRow(0, new Object[]{"BUY ORDER", targetStockSymbol, itemsVol, "₹" + currentPricingUnit});

            DatabaseManager.updateBalance(user.getUsername(), user.getBalance());
            DatabaseManager.saveStockToPortfolio(user.getUsername(), targetStockSymbol, portfolio.getHoldings().get(targetStockSymbol));
            DatabaseManager.logTransaction(user.getUsername(), "BUY", targetStockSymbol, itemsVol, currentPricingUnit);
            FileManager.savePortfolio(portfolio);

            refreshPortfolioTable();
            updatePortfolioValue();
        } catch (Exception ex) {/**/}
    }

    private void processSellOrderRequest() {
        int selectedRow = portfolioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an asset position item row inside your holdings table.");
            return;
        }

        String stockName = portfolioTable.getValueAt(selectedRow, 0).toString();
        int availableVolume = Integer.parseInt(portfolioTable.getValueAt(selectedRow, 1).toString());
        String qtyInput = JOptionPane.showInputDialog(this, "Units to liquidate:");
        if (qtyInput == null || qtyInput.isEmpty()) return;

        try {
            int liquidatingVolume = Integer.parseInt(qtyInput);
            if (liquidatingVolume > availableVolume) {
                JOptionPane.showMessageDialog(this, "Requested volume baseline exceeds your asset hold boundaries.");
                return;
            }

            double activeSpotMarketPrice = 0;
            for (int i = 0; i < marketTable.getRowCount(); i++) {
                if (marketTable.getValueAt(i, 0).toString().equals(stockName)) {
                    activeSpotMarketPrice = Double.parseDouble(marketTable.getValueAt(i, 1).toString());
                    break;
                }
            }

            user.setBalance(user.getBalance() + (activeSpotMarketPrice * liquidatingVolume));
            portfolio.sellStock(stockName, liquidatingVolume);
            transactionModel.insertRow(0, new Object[]{"SELL CLEAR", stockName, liquidatingVolume, "₹" + activeSpotMarketPrice});

            DatabaseManager.updateBalance(user.getUsername(), user.getBalance());
            if (portfolio.getHoldings().containsKey(stockName)) {
                DatabaseManager.saveStockToPortfolio(user.getUsername(), stockName, portfolio.getHoldings().get(stockName));
            } else {
                DatabaseManager.removeStockFromPortfolio(user.getUsername(), stockName);
            }
            DatabaseManager.logTransaction(user.getUsername(), "SELL", stockName, liquidatingVolume, activeSpotMarketPrice);
            FileManager.savePortfolio(portfolio);

            refreshPortfolioTable();
            updatePortfolioValue();
        } catch (Exception ex) {/**/}
    }

    private void executeAdvisoryTrendsEngine() {
        String optimalAssetCodeIdentifier = "None (Flat Trajectories)";
        int maximumSequenceStreak = -1;
        for (String code : trajectoryMatrix.keySet()) {
            int trajectoryStreak = trajectoryMatrix.get(code);
            if (trajectoryStreak > maximumSequenceStreak) {
                maximumSequenceStreak = trajectoryStreak;
                optimalAssetCodeIdentifier = code;
            }
        }
        JOptionPane.showMessageDialog(this, "Market Advisory Output: Focus " + optimalAssetCodeIdentifier + " | Momentum: Upward vector velocity trace at " + maximumSequenceStreak + " intervals.");
    }

    private void refreshPortfolioTable() {
        portfolioModel.setRowCount(0);
        for (String asset : portfolio.getHoldings().keySet()) {
            portfolioModel.addRow(new Object[]{asset, portfolio.getHoldings().get(asset)});
        }
    }

    private void updatePortfolioValue() {
        double summaryHoldingsEvaluationSum = 0;
        for (String code : portfolio.getHoldings().keySet()) {
            int holdingsSharesCount = portfolio.getHoldings().get(code);
            double currentSpotRate = 0;
            for (int i = 0; i < marketTable.getRowCount(); i++) {
                if (marketTable.getValueAt(i, 0).toString().equals(code)) {
                    currentSpotRate = Double.parseDouble(marketTable.getValueAt(i, 1).toString());
                    break;
                }
            }
            summaryHoldingsEvaluationSum += holdingsSharesCount * currentSpotRate;
        }

        balanceLabel.setText("<html><body><font color='#8A8D93' size='3'>Clear Available Funds</font><br><br><font color='white' size='6'><b>" + String.format("₹%.2f", user.getBalance()) + "</b></font></body></html>");
        portfolioValueLabel.setText("<html><body><font color='#8A8D93' size='3'>Current Holdings Value</font><br><br><font color='white' size='6'><b>" + String.format("₹%.2f", summaryHoldingsEvaluationSum) + "</b></font></body></html>");
        if (walletBalanceDisplayLabel != null) {
            walletBalanceDisplayLabel.setText("Net Cash Balance Availability Pool: ₹" + String.format("%.2f", user.getBalance()));
        }

        double profitLossNetDelta = (summaryHoldingsEvaluationSum + user.getBalance()) - initialNetWorth;
        String symbolicTagMarker = profitLossNetDelta >= 0 ? "+₹" : "-₹";
        Color evaluationColorStyle = profitLossNetDelta >= 0 ? new Color(0, 180, 0) : Color.RED;

        profitLossLabel.setText("<html><body><font color='#8A8D93' size='3'>Total Net Return Profit/Loss</font><br><br><font color='" + (profitLossNetDelta >= 0 ? "green" : "red") + "' size='6'><b>" + symbolicTagMarker + String.format("%.2f", Math.abs(profitLossNetDelta)) + "</b></font></body></html>");
        profitLossLabel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, evaluationColorStyle));
    }

    private void startMarketSimulation() {
        Timer timelineTimer = new Timer(3000, e -> {
            Random probabilisticRandomEngine = new Random();
            int highlightedRowIndex = marketTable.getSelectedRow();
            String chosenAssetSymbolName = (highlightedRowIndex != -1) ? marketTable.getValueAt(highlightedRowIndex, 0).toString() : "";

            for (int i = 0; i < marketTable.getRowCount(); i++) {
                String symbolKey = marketTable.getValueAt(i, 0).toString();
                double preExistingRate = Double.parseDouble(marketTable.getValueAt(i, 1).toString());

                double dynamicVolatilityShift = probabilisticRandomEngine.nextInt(101) - 50;
                double calculatedPostShiftRate = preExistingRate + dynamicVolatilityShift;
                if (calculatedPostShiftRate < 100) calculatedPostShiftRate = 100;

                marketTable.setValueAt(calculatedPostShiftRate, i, 1);

                ArrayList<Double> performanceLogHistory = historyEngine.get(symbolKey);
                performanceLogHistory.add(calculatedPostShiftRate);
                if (performanceLogHistory.size() > 25) performanceLogHistory.remove(0);

                if (calculatedPostShiftRate > preExistingRate) {
                    trajectoryMatrix.put(symbolKey, trajectoryMatrix.get(symbolKey) + 1);
                } else {
                    trajectoryMatrix.put(symbolKey, 0);
                }

                if (symbolKey.equals(chosenAssetSymbolName)) {
                    chartRenderer.updateChart(symbolKey, performanceLogHistory);
                }
            }
            updatePortfolioValue();
        });
        timelineTimer.start();
    }
}