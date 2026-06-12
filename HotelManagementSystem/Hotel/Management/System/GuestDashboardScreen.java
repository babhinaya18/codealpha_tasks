package Hotel.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuestDashboardScreen extends JFrame implements ActionListener {
    String currentSessionGuestUsername;
    CardLayout viewSwitchCardEngine;
    JPanel mainWorkspaceDeck;

    JButton btnNavStandard, btnNavDeluxe, btnNavSuite, btnNavMyHistory, btnNavExit;
    JTable tableRoomsView, tableReservationsView;
    DefaultTableModel modelRoomsStructure, modelReservationsStructure;

    JLabel lblSelectedRoomIdDisplay, lblCalculatedPriceDisplay;
    JTextField txtStayDurationField;
    JComboBox comboFilterAC, comboFilterBed;
    JButton btnFilterApply, btnExecuteSimulatedBooking, btnRevokeReservation;

    String currentSelectedClass = "Standard";

    public GuestDashboardScreen(String guestUser) {
        super("Online Booking Hub - Welcome, " + guestUser.toUpperCase());
        this.currentSessionGuestUsername = guestUser;

        JPanel backgroundWrap = new JPanel(null);
        backgroundWrap.setBounds(0, 0, 1200, 680);
        backgroundWrap.setBackground(new Color(10, 25, 50));
        add(backgroundWrap);

        // Sidebar Panel Setup
        JPanel leftSidebarGridPanel = new JPanel(null);
        leftSidebarGridPanel.setBounds(12, 12, 260, 615);
        leftSidebarGridPanel.setBackground(new Color(16, 32, 64));
        leftSidebarGridPanel.setBorder(BorderFactory.createLineBorder(new Color(16, 108, 115), 2));
        backgroundWrap.add(leftSidebarGridPanel);

        JLabel brandingTextLabel = new JLabel("VSA LUXURY HOTEL", JLabel.CENTER);
        brandingTextLabel.setBounds(10, 25, 240, 25);
        brandingTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        brandingTextLabel.setForeground(new Color(255, 98, 0));
        leftSidebarGridPanel.add(brandingTextLabel);

        JLabel userTagLabel = new JLabel("Logged In As: " + currentSessionGuestUsername.toUpperCase(), JLabel.CENTER);
        userTagLabel.setBounds(10, 55, 240, 20);
        userTagLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        userTagLabel.setForeground(Color.LIGHT_GRAY);
        leftSidebarGridPanel.add(userTagLabel);

        JSeparator uiDividerLine = new JSeparator();
        uiDividerLine.setBounds(15, 90, 230, 10);
        leftSidebarGridPanel.add(uiDividerLine);

        JLabel sectionHeader1 = new JLabel("CHOOSE A ROOM TYPE");
        sectionHeader1.setBounds(15, 115, 230, 20);
        sectionHeader1.setFont(new Font("Tahoma", Font.BOLD, 11));
        sectionHeader1.setForeground(Color.GRAY);
        leftSidebarGridPanel.add(sectionHeader1);

        btnNavStandard = new JButton("Standard Rooms");
        btnNavStandard.setBounds(15, 145, 230, 38);
        formatSidebarActionButtons(btnNavStandard);
        leftSidebarGridPanel.add(btnNavStandard);

        btnNavDeluxe = new JButton("Deluxe Executive Rooms");
        btnNavDeluxe.setBounds(15, 195, 230, 38);
        formatSidebarActionButtons(btnNavDeluxe);
        leftSidebarGridPanel.add(btnNavDeluxe);

        btnNavSuite = new JButton("Presidential Suites");
        btnNavSuite.setBounds(15, 245, 230, 38);
        formatSidebarActionButtons(btnNavSuite);
        leftSidebarGridPanel.add(btnNavSuite);

        JLabel sectionHeader2 = new JLabel("YOUR ACCOUNT");
        sectionHeader2.setBounds(15, 315, 230, 20);
        sectionHeader2.setFont(new Font("Tahoma", Font.BOLD, 11));
        sectionHeader2.setForeground(Color.GRAY);
        leftSidebarGridPanel.add(sectionHeader2);

        btnNavMyHistory = new JButton("My Booking History");
        btnNavMyHistory.setBounds(15, 345, 230, 38);
        formatSidebarActionButtons(btnNavMyHistory);
        leftSidebarGridPanel.add(btnNavMyHistory);

        btnNavExit = new JButton("Log Out");
        btnNavExit.setBounds(15, 555, 230, 38);
        btnNavExit.setBackground(new Color(180, 45, 45));
        btnNavExit.setForeground(Color.WHITE);
        btnNavExit.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNavExit.addActionListener(this);
        leftSidebarGridPanel.add(btnNavExit);

        // Card Display Layout Deck
        viewSwitchCardEngine = new CardLayout();
        mainWorkspaceDeck = new JPanel(viewSwitchCardEngine);
        mainWorkspaceDeck.setBounds(285, 12, 885, 615);
        mainWorkspaceDeck.setBackground(new Color(10, 25, 50));
        backgroundWrap.add(mainWorkspaceDeck);

        initializeRoomBrowserInterfaceStructureView();
        initializeReservationHistoryInterfaceStructureView();

        setLayout(null);
        setSize(1190, 675);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        synchronizeAdvancedFilterQueries();
    }

    private void formatSidebarActionButtons(JButton b) {
        b.setBackground(new Color(20, 40, 80));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setBorder(BorderFactory.createLineBorder(new Color(16, 108, 115)));
        b.setFocusPainted(false);
        b.addActionListener(this);
    }

    private void initializeRoomBrowserInterfaceStructureView() {
        JPanel containerPanel = new JPanel(null);
        containerPanel.setBackground(new Color(10, 25, 50));

        JLabel viewHeadingTitle = new JLabel("CHOOSE FROM AVAILABLE ROOMS");
        viewHeadingTitle.setBounds(25, 15, 450, 25);
        viewHeadingTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
        viewHeadingTitle.setForeground(Color.WHITE);
        containerPanel.add(viewHeadingTitle);

        comboFilterAC = new JComboBox(new String[]{"All (AC & Non-AC)", "AC", "Non-AC"});
        comboFilterAC.setBounds(480, 15, 130, 25);
        containerPanel.add(comboFilterAC);

        comboFilterBed = new JComboBox(new String[]{"All Beds", "Single Bed", "Double Bed"});
        comboFilterBed.setBounds(620, 15, 110, 25);
        containerPanel.add(comboFilterBed);

        btnFilterApply = new JButton("Apply Filters");
        btnFilterApply.setBounds(740, 15, 110, 25);
        btnFilterApply.setBackground(new Color(16, 108, 115));
        btnFilterApply.setForeground(Color.WHITE);
        btnFilterApply.addActionListener(this);
        containerPanel.add(btnFilterApply);

        modelRoomsStructure = new DefaultTableModel(new String[]{"Room Number", "Availability", "Clean Status", "Price Per Night", "Bed Type", "AC Status"}, 0);
        tableRoomsView = new JTable(modelRoomsStructure);
        tableRoomsView.setBackground(new Color(16, 32, 64));
        tableRoomsView.setForeground(Color.WHITE);
        tableRoomsView.setRowHeight(26);

        JScrollPane viewTableScrollPane = new JScrollPane(tableRoomsView);
        viewTableScrollPane.setBounds(25, 55, 835, 320);
        containerPanel.add(viewTableScrollPane);

        // Checkout Section Form Layout
        JPanel transactionCheckoutFormPanel = new JPanel(null);
        transactionCheckoutFormPanel.setBounds(25, 395, 835, 195);
        transactionCheckoutFormPanel.setBackground(new Color(16, 32, 64));
        transactionCheckoutFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Booking & Payment Details", 0, 0, null, Color.WHITE));
        containerPanel.add(transactionCheckoutFormPanel);

        JLabel labelFieldRef1 = new JLabel("Selected Room Number:");
        labelFieldRef1.setBounds(25, 40, 200, 20);
        labelFieldRef1.setForeground(Color.WHITE);
        transactionCheckoutFormPanel.add(labelFieldRef1);

        lblSelectedRoomIdDisplay = new JLabel("None Selected");
        lblSelectedRoomIdDisplay.setBounds(230, 40, 150, 20);
        lblSelectedRoomIdDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSelectedRoomIdDisplay.setForeground(new Color(255, 98, 0));
        transactionCheckoutFormPanel.add(lblSelectedRoomIdDisplay);

        JLabel labelFieldRef2 = new JLabel("Price Per Night:");
        labelFieldRef2.setBounds(25, 85, 200, 20);
        labelFieldRef2.setForeground(Color.WHITE);
        transactionCheckoutFormPanel.add(labelFieldRef2);

        lblCalculatedPriceDisplay = new JLabel("$0.00");
        lblCalculatedPriceDisplay.setBounds(230, 85, 150, 20);
        lblCalculatedPriceDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCalculatedPriceDisplay.setForeground(Color.GREEN);
        transactionCheckoutFormPanel.add(lblCalculatedPriceDisplay);

        JLabel labelFieldRef3 = new JLabel("Number of Nights to Stay:");
        labelFieldRef3.setBounds(425, 40, 250, 20);
        labelFieldRef3.setForeground(Color.WHITE);
        transactionCheckoutFormPanel.add(labelFieldRef3);

        txtStayDurationField = new JTextField("1");
        txtStayDurationField.setBounds(680, 40, 125, 25);
        transactionCheckoutFormPanel.add(txtStayDurationField);

        btnExecuteSimulatedBooking = new JButton("Pay & Book Room Now");
        btnExecuteSimulatedBooking.setBounds(425, 95,380, 45);
        btnExecuteSimulatedBooking.setBackground(new Color(255, 98, 0));
        btnExecuteSimulatedBooking.setForeground(Color.WHITE);
        btnExecuteSimulatedBooking.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnExecuteSimulatedBooking.addActionListener(this);
        transactionCheckoutFormPanel.add(btnExecuteSimulatedBooking);

        tableRoomsView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableRoomsView.getSelectedRow();
                if (row >= 0) {
                    lblSelectedRoomIdDisplay.setText(modelRoomsStructure.getValueAt(row, 0).toString());
                    lblCalculatedPriceDisplay.setText("$" + modelRoomsStructure.getValueAt(row, 3).toString());
                }
            }
        });

        mainWorkspaceDeck.add(containerPanel, "ROOM_BROWSER_VIEW");
    }

    private void initializeReservationHistoryInterfaceStructureView() {
        JPanel containerPanel = new JPanel(null);
        containerPanel.setBackground(new Color(10, 25, 50));

        JLabel titleLabelHeader = new JLabel("YOUR ACTIVE RESERVATION RECORDS");
        titleLabelHeader.setBounds(25, 15, 600, 25);
        titleLabelHeader.setFont(new Font("Tahoma", Font.BOLD, 15));
        titleLabelHeader.setForeground(Color.WHITE);
        containerPanel.add(titleLabelHeader);

        modelReservationsStructure = new DefaultTableModel(new String[]{"Username", "Booking Receipt ID", "Room Number", "Booking Date & Time", "Total Amount Paid"}, 0);
        tableReservationsView = new JTable(modelReservationsStructure);
        tableReservationsView.setBackground(new Color(16, 32, 64));
        tableReservationsView.setForeground(Color.WHITE);
        tableReservationsView.setRowHeight(26);

        JScrollPane scrollPaneWrapper = new JScrollPane(tableReservationsView);
        scrollPaneWrapper.setBounds(25, 55, 835, 415);
        containerPanel.add(scrollPaneWrapper);

        btnRevokeReservation = new JButton("Cancel Selected Booking");
        btnRevokeReservation.setBounds(25, 505, 320, 45);
        btnRevokeReservation.setBackground(new Color(180, 45, 45));
        btnRevokeReservation.setForeground(Color.WHITE);
        btnRevokeReservation.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRevokeReservation.addActionListener(this);
        containerPanel.add(btnRevokeReservation);

        mainWorkspaceDeck.add(containerPanel, "HISTORY_BROWSER_VIEW");
    }

    private void synchronizeAdvancedFilterQueries() {
        modelRoomsStructure.setRowCount(0);
        lblSelectedRoomIdDisplay.setText("None Selected");
        lblCalculatedPriceDisplay.setText("$0.00");

        try {
            DatabaseConnection c = new DatabaseConnection();
            StringBuilder queryBuild = new StringBuilder("SELECT * FROM room WHERE room_class = '" + currentSelectedClass + "'");

            String filterAC = (String) comboFilterAC.getSelectedItem();
            if(filterAC != null && !filterAC.equals("All (AC & Non-AC)")) {
                queryBuild.append(" AND ac_status = '").append(filterAC).append("'");
            }

            String filterBed = (String) comboFilterBed.getSelectedItem();
            if(filterBed != null && !filterBed.equals("All Beds")) {
                queryBuild.append(" AND bed_type = '").append(filterBed).append("'");
            }

            ResultSet rs = c.statement.executeQuery(queryBuild.toString());
            while (rs.next()) {
                modelRoomsStructure.addRow(new Object[]{
                        rs.getString("roomnumber"),
                        rs.getString("availability"),
                        rs.getString("cleaning_status"),
                        rs.getString("price"),
                        rs.getString("bed_type"),
                        rs.getString("ac_status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActiveGuestReservationLogsArray() {
        modelReservationsStructure.setRowCount(0);
        try {
            DatabaseConnection c = new DatabaseConnection();
            String selectQueryString = "SELECT * FROM customer WHERE name = '" + currentSessionGuestUsername + "'";
            ResultSet rs = c.statement.executeQuery(selectQueryString);
            while (rs.next()) {
                modelReservationsStructure.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("number"),
                        rs.getString("room"),
                        rs.getString("checkintime"),
                        rs.getString("deposit")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNavStandard) {
            currentSelectedClass = "Standard";
            synchronizeAdvancedFilterQueries();
            viewSwitchCardEngine.show(mainWorkspaceDeck, "ROOM_BROWSER_VIEW");
        } else if (e.getSource() == btnNavDeluxe) {
            currentSelectedClass = "Deluxe";
            synchronizeAdvancedFilterQueries();
            viewSwitchCardEngine.show(mainWorkspaceDeck, "ROOM_BROWSER_VIEW");
        } else if (e.getSource() == btnNavSuite) {
            currentSelectedClass = "Suite";
            synchronizeAdvancedFilterQueries();
            viewSwitchCardEngine.show(mainWorkspaceDeck, "ROOM_BROWSER_VIEW");
        } else if (e.getSource() == btnFilterApply) {
            synchronizeAdvancedFilterQueries();
        } else if (e.getSource() == btnNavMyHistory) {
            refreshActiveGuestReservationLogsArray();
            viewSwitchCardEngine.show(mainWorkspaceDeck, "HISTORY_BROWSER_VIEW");
        } else if (e.getSource() == btnNavExit) {
            new PortalSelectionScreen();
            setVisible(false);
        } else if (e.getSource() == btnExecuteSimulatedBooking) {
            executeLiveTransactionalBookingEngine();
        } else if (e.getSource() == btnRevokeReservation) {
            executeLiveReservationRevocationEngine();
        }
    }

    private void executeLiveTransactionalBookingEngine() {
        String targetedRoomIdRefString = lblSelectedRoomIdDisplay.getText();
        String lengthStr = txtStayDurationField.getText().trim();

        if (targetedRoomIdRefString.equals("None Selected") || lengthStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please choose a room from the table layout rows above first.");
            return;
        }

        try {
            DatabaseConnection c = new DatabaseConnection();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM room WHERE roomnumber = '" + targetedRoomIdRefString + "'");
            if (rs.next()) {
                if (rs.getString("availability").equalsIgnoreCase("Occupied")) {
                    JOptionPane.showMessageDialog(null, "Sorry, this room has already been booked by another user.");
                    return;
                }

                double perDiemCostVal = Double.parseDouble(rs.getString("price"));
                int completeDaysCount = Integer.parseInt(lengthStr);
                double absoluteAggregatedBillSumVal = perDiemCostVal * completeDaysCount;

                int processingInteractionPromptDialogResult = JOptionPane.showConfirmDialog(null,
                        "SECURE ONLINE PAYMENT PORTAL\n" +
                                "--------------------------------------------------------\n" +
                                "Account Owner Profile: " + currentSessionGuestUsername.toUpperCase() + "\n" +
                                "Selected Unit: Room " + targetedRoomIdRefString + "\n" +
                                "Total Billing Cost: $" + absoluteAggregatedBillSumVal + "\n\n" +
                                "Would you like to authorize this payment?",
                        "Secure Payment Processing Gateway", JOptionPane.YES_NO_OPTION);

                if (processingInteractionPromptDialogResult == JOptionPane.YES_OPTION) {
                    String trackingClockStampValueString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    String simulatedReceiptTokenTrace = "VSA" + ((int)(Math.random() * 90000) + 10000);

                    String injectionSQLStringStatement = "INSERT INTO customer VALUES('ONLINE', '" + simulatedReceiptTokenTrace + "', '" + currentSessionGuestUsername + "', 'Unknown', 'Global', '" + targetedRoomIdRefString + "', '" + trackingClockStampValueString + "', '" + absoluteAggregatedBillSumVal + "')";
                    c.statement.executeUpdate(injectionSQLStringStatement);

                    c.statement.executeUpdate("UPDATE room SET availability = 'Occupied' WHERE roomnumber = '" + targetedRoomIdRefString + "'");

                    JOptionPane.showMessageDialog(null, "Payment complete! Your online booking reference is locked.");
                    synchronizeAdvancedFilterQueries();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void executeLiveReservationRevocationEngine() {
        int row = tableReservationsView.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Please click on a booking record row from your history list below.");
            return;
        }

        String structuralRoomMappingRefIdValueString = modelReservationsStructure.getValueAt(row, 2).toString();
        String specificDocumentTransactionIdValueString = modelReservationsStructure.getValueAt(row, 1).toString();

        int verifyActionConfirmationPromptDialogResult = JOptionPane.showConfirmDialog(null,
                "Cancel your online reservation for Room " + structuralRoomMappingRefIdValueString + "?",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        if (verifyActionConfirmationPromptDialogResult == JOptionPane.YES_OPTION) {
            try {
                DatabaseConnection c = new DatabaseConnection();
                c.statement.executeUpdate("DELETE FROM customer WHERE number = '" + specificDocumentTransactionIdValueString + "'");
                c.statement.executeUpdate("UPDATE room SET availability = 'Available' WHERE roomnumber = '" + structuralRoomMappingRefIdValueString + "'");

                JOptionPane.showMessageDialog(null, "Your reservation was successfully cancelled.");
                refreshActiveGuestReservationLogsArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}