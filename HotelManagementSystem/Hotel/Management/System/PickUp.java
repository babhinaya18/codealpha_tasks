package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class PickUp extends JFrame {
    JTable table;
    Choice c;

    public PickUp(){
        super("VSA LOGISTICS - FLEET PICKUP SERVICE");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(10,25,50));
        panel.setBounds(5,5,790,590);
        panel.setLayout(null);
        add(panel);

        JLabel pus = new JLabel("Pick Up Service Logistics Engine");
        pus.setBounds(32,15,400,25);
        pus.setForeground(new Color(255, 98, 0));
        pus.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(pus);

        JLabel TOC = new JLabel("Select Car Model:");
        TOC.setBounds(32,70,120,25);
        TOC.setForeground(Color.WHITE);
        TOC.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(TOC);

        c = new Choice();
        c.setBounds(160,72,200,25);
        panel.add(c);

        try{
            DatabaseConnection C = new DatabaseConnection();
            ResultSet resultSet = C.statement.executeQuery("select * from driver");
            while (resultSet.next()){
                c.add(resultSet.getString("carname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        table.setBackground(new Color(16, 32, 64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,120,750,340);
        panel.add(scrollPane);

        // Load all data into view by default on screen opening
        refreshDriverTableData("SELECT name, age, gender, company, carname, available, location FROM driver");

        JButton display = new JButton("Filter Car Model");
        display.setBounds(200,490,160,35);
        display.setBackground(new Color(16, 108, 115));
        display.setForeground(Color.WHITE);
        panel.add(display);
        display.addActionListener(e -> {
            String selectedCar = c.getSelectedItem();
            if(selectedCar != null) {
                refreshDriverTableData("select name, age, gender, company, carname, available, location from driver where carname = '" + selectedCar + "'");
            }
        });

        JButton Back = new JButton("Dismiss Panel");
        Back.setBounds(400,490,160,35);
        Back.setBackground(Color.BLACK);
        Back.setForeground(Color.WHITE);
        panel.add(Back);
        Back.addActionListener(e -> setVisible(false));

        setLayout(null);
        setSize(800,580);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void refreshDriverTableData(String query) {
        try{
            DatabaseConnection dbConn = new DatabaseConnection();
            ResultSet resultSet = dbConn.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception E){
            E.printStackTrace();
        }
    }
}