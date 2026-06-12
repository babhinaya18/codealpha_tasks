package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class CustomerInfo extends JFrame {
    public CustomerInfo(){
        super("VSA CENTRAL LEDGER - ACTIVE IN-HOUSE GUEST MANIFEST");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,890,590);
        panel.setBackground(new Color(10,25,50));
        panel.setLayout(null);
        add(panel);

        JTable table = new JTable();
        table.setBounds(10,45,870,440);
        table.setBackground(new Color(16,32,64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);
        panel.add(table);

        try {
            DatabaseConnection c = new DatabaseConnection();
            String q = "select id as 'Verification Class', number as 'Document ID', name as 'Guest Profile', gender as 'Gender', country as 'Nationality', room as 'Room Allocated', checkintime as 'Ingress Clock', deposit as 'Settlement Bal ($)' from Customer";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e ){
            e.printStackTrace();
        }

        JButton back = new JButton("Close Ledger Portal");
        back.setBounds(330,510,240,35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        JLabel titleLbl = new JLabel("IN-HOUSE ACTIVE RESIDENT MANIFEST CENTRAL DIRECTORY");
        titleLbl.setBounds(15, 12, 600, 25);
        titleLbl.setForeground(new Color(255, 98, 0));
        titleLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(titleLbl);

        setUndecorated(true);
        setLayout(null);
        setSize(900,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}