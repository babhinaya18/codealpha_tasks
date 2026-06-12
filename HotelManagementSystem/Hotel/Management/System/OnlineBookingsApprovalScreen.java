package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class OnlineBookingsApprovalScreen extends JFrame {
    JTable table;
    JButton btnApprove, btnDismiss;
    JTextField txtDocType, txtDocNum;

    public OnlineBookingsApprovalScreen() {
        super("VSA CENTRAL PROCESSING - ONLINE CLOUD RESERVATIONS QUEUE");

        JPanel panel = new JPanel(null);
        panel.setBounds(5, 5, 925, 540);
        panel.setBackground(new Color(10, 25, 50));
        add(panel);

        JLabel title = new JLabel("PENDING WEB RESERVATIONS QUEUE");
        title.setBounds(25, 15, 500, 25);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(255, 98, 0));
        panel.add(title);

        table = new JTable();
        table.setBackground(new Color(16, 32, 64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(25, 55, 875, 300);
        panel.add(scroll);

        // Form Fields for Check-In Validation Document Metadata
        JLabel lblDocType = new JLabel("Document Verification Class:");
        lblDocType.setBounds(25, 380, 200, 20);
        lblDocType.setForeground(Color.WHITE);
        panel.add(lblDocType);

        txtDocType = new JTextField("Passport");
        txtDocType.setBounds(220, 380, 150, 22);
        panel.add(txtDocType);

        JLabel lblDocNum = new JLabel("Verification Serial No:");
        lblDocNum.setBounds(25, 420, 200, 20);
        lblDocNum.setForeground(Color.WHITE);
        panel.add(lblDocNum);

        txtDocNum = new JTextField();
        txtDocNum.setBounds(220, 420, 150, 22);
        panel.add(txtDocNum);

        btnApprove = new JButton("Convert To Active Check-In");
        btnApprove.setBounds(420, 375, 230, 40);
        btnApprove.setBackground(new Color(16, 108, 115));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel.add(btnApprove);

        btnDismiss = new JButton("Close Queue Workspace");
        btnDismiss.setBounds(670, 375, 230, 40);
        btnDismiss.setBackground(Color.BLACK);
        btnDismiss.setForeground(Color.WHITE);
        panel.add(btnDismiss);

        // Table Selection Sync Execution Architecture
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    txtDocNum.setText(table.getValueAt(r, 1).toString());
                }
            }
        });

        btnApprove.addActionListener(e -> executeConversionProcessingGateway());
        btnDismiss.addActionListener(e -> setVisible(false));

        refreshQueueView();

        setLayout(null);
        setSize(945, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void refreshQueueView() {
        try {
            DatabaseConnection c = new DatabaseConnection();
            // Identifies entries submitted through web gateways via the 'ONLINE' label
            String q = "SELECT name as 'Guest Username', number as 'Receipt Token ID', room as 'Room Assigned', checkintime as 'Timestamp Created', deposit as 'Total Paid ($)' FROM customer WHERE id = 'ONLINE'";
            ResultSet rs = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeConversionProcessingGateway() {
        int targetRow = table.getSelectedRow();
        String docTypeInput = txtDocType.getText().trim();
        String docNumInput = txtDocNum.getText().trim();

        if (targetRow < 0 || docNumInput.isEmpty() || docTypeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a pending reservation entry row and provide ID credentials validation metadata.");
            return;
        }

        String receiptTokenID = table.getValueAt(targetRow, 1).toString();

        try {
            DatabaseConnection c = new DatabaseConnection();
            // Upgrades standard metadata records into active physical walk-in ledger references seamlessly
            String updateQuery = "UPDATE customer SET id = '" + docTypeInput + "', number = '" + docNumInput + "' WHERE number = '" + receiptTokenID + "'";
            c.statement.executeUpdate(updateQuery);

            JOptionPane.showMessageDialog(null, "Online Booking Successfully Verified! Security Credentials Link Active.");
            refreshQueueView();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Database Violation: Check verification parameters format entries.");
            ex.printStackTrace();
        }
    }
}