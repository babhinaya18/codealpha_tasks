package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ManagerInfo extends JFrame {
    public ManagerInfo(){
        super("VSA EXECUTIVE MANAGEMENT HUB - AUTHORITY DIRECTORY");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,990,590);
        panel.setBackground(new Color(10,25,50));
        panel.setLayout(null);
        add(panel);

        JTable table = new JTable();
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(16,32,64));
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,45,970,440);
        panel.add(scrollPane);

        try {
            DatabaseConnection c = new DatabaseConnection();
            String q = "select name, age, gender, job, salary, phone, email, aadhar from employee where job = 'Manager'";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e ){
            e.printStackTrace();
        }

        JButton back = new JButton("Close Executive Hub");
        back.setBounds(380,510,240,35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        JLabel mLabel = new JLabel("EXECUTIVE CORPORATE MANAGEMENT REGISTRY DIRECTORY", JLabel.CENTER);
        mLabel.setBounds(15, 12, 960, 25);
        mLabel.setForeground(new Color(255, 98, 0));
        mLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(mLabel);

        setUndecorated(true);
        setLayout(null);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}