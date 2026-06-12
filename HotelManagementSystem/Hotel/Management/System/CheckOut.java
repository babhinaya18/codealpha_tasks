package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class CheckOut extends JFrame {
    Choice Customer;
    JLabel labelRoomnumber, labelcheckintime;

    public CheckOut(){
        super("VSA FINANCIAL GATEWAY - SYSTEM CHECK-OUT OUTTAKE");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,790,390);
        panel.setBackground(new Color(10,25,50));
        panel.setLayout(null);
        add(panel);

        JLabel label = new JLabel("SETTLE ACCOUNTS & CHECK-OUT");
        label.setBounds(30,20,400,30);
        label.setFont(new Font("Tahoma",Font.BOLD,18));
        label.setForeground(new Color(255, 98, 0));
        panel.add(label);

        JLabel UserId = new JLabel("Customer Active Identifier:");
        UserId.setBounds(30,80,180,30);
        UserId.setFont(new Font("Tahoma",Font.BOLD,13));
        UserId.setForeground(Color.WHITE);
        panel.add(UserId);

        Customer = new Choice();
        Customer.setBounds(230,84,180,25);
        panel.add(Customer);

        JLabel roomNum = new JLabel("Assigned Unit ID Mapping:");
        roomNum.setBounds(30,130,180,30);
        roomNum.setFont(new Font("Tahoma",Font.BOLD,13));
        roomNum.setForeground(Color.WHITE);
        panel.add(roomNum);

        labelRoomnumber = new JLabel("---");
        labelRoomnumber.setBounds(230,130,150,30);
        labelRoomnumber.setFont(new Font("Tahoma",Font.BOLD,14));
        labelRoomnumber.setForeground(Color.WHITE);
        panel.add(labelRoomnumber);

        JLabel checkintime = new JLabel("Arrival Registry Ingress:");
        checkintime.setBounds(30,180,180,30);
        checkintime.setFont(new Font("Tahoma",Font.BOLD,13));
        checkintime.setForeground(Color.WHITE);
        panel.add(checkintime);

        labelcheckintime = new JLabel("---");
        labelcheckintime.setBounds(230,180,250,30);
        labelcheckintime.setFont(new Font("Tahoma",Font.BOLD,13));
        labelcheckintime.setForeground(Color.WHITE);
        panel.add(labelcheckintime);

        JLabel checkouttime = new JLabel("Departure Terminal Outgress:");
        checkouttime.setBounds(30,230,180,30);
        checkouttime.setFont(new Font("Tahoma",Font.BOLD,13));
        checkouttime.setForeground(Color.WHITE);
        panel.add(checkouttime);

        JLabel labelcheckouttime = new JLabel("" + new Date());
        labelcheckouttime.setBounds(230,230,250,30);
        labelcheckouttime.setFont(new Font("Tahoma",Font.BOLD,13));
        labelcheckouttime.setForeground(Color.WHITE);
        panel.add(labelcheckouttime);

        populateCustomerChoiceRegistry();

        JButton btnFetch = new JButton("Fetch Metadata");
        btnFetch.setBounds(30,300,140,32);
        btnFetch.setForeground(Color.WHITE);
        btnFetch.setBackground(Color.BLACK);
        panel.add(btnFetch);
        btnFetch.addActionListener(e -> executeDataDiagnosticFetch());

        JButton checkOutBtn = new JButton("Settle & Settle");
        checkOutBtn.setBounds(190,300,160,32);
        checkOutBtn.setForeground(Color.WHITE);
        checkOutBtn.setBackground(new Color(16, 108, 115));
        panel.add(checkOutBtn);
        checkOutBtn.addActionListener(e -> executeSettlementOuttakePipeline());

        JButton back = new JButton("Dismiss");
        back.setBounds(360,300,120,32);
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        setUndecorated(true);
        setLayout(null);
        setSize(800,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateCustomerChoiceRegistry() {
        try{
            DatabaseConnection c = new DatabaseConnection();
            ResultSet resultSet = c.statement.executeQuery("select * from customer");
            while (resultSet.next()){
                Customer.add(resultSet.getString("number"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    private void executeDataDiagnosticFetch() {
        try{
            DatabaseConnection c = new DatabaseConnection();
            ResultSet resultSet = c.statement.executeQuery("select * from customer where number = '"+Customer.getSelectedItem()+"'");
            if (resultSet.next()){
                labelRoomnumber.setText(resultSet.getString("room"));
                labelcheckintime.setText(resultSet.getString("checkintime"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    private void executeSettlementOuttakePipeline() {
        if(labelRoomnumber.getText().equals("---")) {
            JOptionPane.showMessageDialog(null, "Run diagnostic data verification fetch sequence parameters first.");
            return;
        }
        try {
            DatabaseConnection cv = new DatabaseConnection();
            cv.statement.executeUpdate("delete from customer where number = '"+Customer.getSelectedItem()+"'");
            cv.statement.executeUpdate("update room set availability = 'Available' where roomnumber = '"+labelRoomnumber.getText()+"'");
            JOptionPane.showMessageDialog(null, "Financial Account Ledger Settled. Destination Module Returned into Deployment Array.");
            setVisible(false);
        }catch (Exception E ){
            E.printStackTrace();
        }
    }
}