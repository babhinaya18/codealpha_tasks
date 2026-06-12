package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UpdateCheck extends JFrame {
    Choice c;
    JTextField textField3, textField4, textField5, textField6, textField7;
    JButton update, back, check;

    public UpdateCheck(){
        super("VSA CENTRAL LEDGER - REVISION PROFILE MODIFIER");

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 940, 490);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel label1 = new JLabel("UPDATE RESERVATION LEDGER DETAILS");
        label1.setBounds(25, 15, 450, 25);
        label1.setFont(new Font("Tahoma", Font.BOLD, 18));
        label1.setForeground(new Color(255, 98, 0));
        panel.add(label1);

        JLabel label2 = new JLabel("Guest Document Key:");
        label2.setBounds(25, 75, 150, 20);
        label2.setForeground(Color.WHITE);
        panel.add(label2);

        c = new Choice();
        c.setBounds(220, 75, 160, 22);
        panel.add(c);

        try {
            DatabaseConnection C = new DatabaseConnection();
            ResultSet resultSet = C.statement.executeQuery("select * from customer");
            while (resultSet.next()){
                c.add(resultSet.getString("number"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        JLabel label3 = new JLabel("Allocated Unit Mapping:");
        label3.setBounds(25, 115, 150, 20);
        label3.setForeground(Color.WHITE);
        panel.add(label3);

        textField3 = new JTextField();
        textField3.setBounds(220, 115, 160, 22);
        panel.add(textField3);

        JLabel label4 = new JLabel("Registered Profile Name:");
        label4.setBounds(25, 155, 150, 20);
        label4.setForeground(Color.WHITE);
        panel.add(label4);

        textField4 = new JTextField();
        textField4.setBounds(220, 155, 160, 22);
        panel.add(textField4);

        JLabel label5 = new JLabel("Check-In Ingress Clock:");
        label5.setBounds(25, 195, 150, 20);
        label5.setForeground(Color.WHITE);
        panel.add(label5);

        textField5 = new JTextField();
        textField5.setBounds(220, 195, 160, 22);
        panel.add(textField5);

        JLabel label6 = new JLabel("Collected Deposit Balance ($):");
        label6.setBounds(25, 235, 180, 20);
        label6.setForeground(Color.WHITE);
        panel.add(label6);

        textField6 = new JTextField();
        textField6.setBounds(220, 235, 160, 22);
        panel.add(textField6);

        JLabel label7 = new JLabel("Outstanding Due Balance ($):");
        label7.setBounds(25, 275, 180, 20);
        label7.setForeground(Color.WHITE);
        panel.add(label7);

        textField7 = new JTextField();
        textField7.setBounds(220, 275, 160, 22);
        textField7.setEditable(false); // Read-only programmatic safety assignment
        panel.add(textField7);

        check = new JButton("Run Diagnostic Check");
        check.setBounds(25, 345, 175, 33);
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        panel.add(check);

        update = new JButton("Commit Changes");
        update.setBounds(215, 345, 165, 33);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        panel.add(update);

        back = new JButton("Dismiss Panel");
        back.setBounds(120, 395, 160, 33);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        panel.add(back);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/updated.png"));
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(500, 60, 300, 300);
        panel.add(label);

        check.addActionListener(e -> executeDiagnosticDataPull());
        update.addActionListener(e -> commitLedgerModificationChanges());
        back.addActionListener(e -> setVisible(false));

        setLayout(null);
        setSize(950, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeDiagnosticDataPull() {
        String idKey = c.getSelectedItem();
        if(idKey == null) return;
        try {
            DatabaseConnection connectionCore = new DatabaseConnection();
            ResultSet rs = connectionCore.statement.executeQuery("select * from customer where number = '" + idKey + "'");
            if (rs.next()) {
                textField3.setText(rs.getString("room"));
                textField4.setText(rs.getString("name"));
                textField5.setText(rs.getString("checkintime"));
                textField6.setText(rs.getString("deposit"));
            }

            ResultSet rsRoom = connectionCore.statement.executeQuery("select * from room where roomnumber = '" + textField3.getText() + "'");
            if (rsRoom.next()) {
                double pricingIndex = Double.parseDouble(rsRoom.getString("price"));
                double settlementCollected = Double.parseDouble(textField6.getText());
                double remainderBalance = pricingIndex - settlementCollected;
                textField7.setText(String.valueOf(remainderBalance));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void commitLedgerModificationChanges() {
        try {
            DatabaseConnection conn = new DatabaseConnection();
            String docNumber = c.getSelectedItem();
            String assignedUnit = textField3.getText().trim();
            String profileName = textField4.getText().trim();
            String ingressTime = textField5.getText().trim();
            String finalDeposit = textField6.getText().trim();

            conn.statement.executeUpdate("update customer set room = '" + assignedUnit + "', name = '" + profileName + "', checkintime = '" + ingressTime + "', deposit = '" + finalDeposit + "' where number = '" + docNumber + "'");
            JOptionPane.showMessageDialog(null, "System Customer Record Modification Transferred Successfully.");
            setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}