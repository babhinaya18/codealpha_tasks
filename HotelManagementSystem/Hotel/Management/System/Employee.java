package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Employee extends JFrame {
    public Employee(){
        super("VSA CENTRAL REGISTRY - ACTIVE PERSONNEL SYSTEM MATRIX");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,990,590);
        panel.setBackground(new Color(10,25,50));
        panel.setLayout(null);
        add(panel);

        JTable table = new JTable();
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(16,32,64));
        table.setRowHeight(24);

        // Put the table inside a JScrollPane so the column headers are visible!
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,45,970,440);
        panel.add(scrollPane);

        try{
            DatabaseConnection c = new DatabaseConnection();
            // Using standard columns to guarantee dynamic model binding
            String EmployeeSQL = "select name, age, gender, job, salary, phone, email, aadhar from employee";
            ResultSet resultSet = c.statement.executeQuery(EmployeeSQL);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }

        JButton back = new JButton("Close View Workspace");
        back.setBounds(380,510,240,35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        JLabel headerLbl = new JLabel("CENTRAL STAFF OPERATIONAL PERSONNEL DIRECTORY", JLabel.CENTER);
        headerLbl.setBounds(15, 12, 960, 25);
        headerLbl.setForeground(new Color(255,98,0));
        headerLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(headerLbl);

        setUndecorated(true);
        setLayout(null);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}