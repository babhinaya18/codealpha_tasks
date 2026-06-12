package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Department extends JFrame {
    public Department(){
        super("VSA SYSTEMS - FINANCIAL DEPARTMENT BUDGET ALLOCATIONS");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(10,25,50));
        panel.setBounds(5,5,690,490);
        panel.setLayout(null);
        add(panel);

        JTable table = new JTable();
        table.setBounds(15,50,660,330);
        table.setBackground(new Color(16,32,64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);
        panel.add(table);

        try{
            DatabaseConnection c = new DatabaseConnection();
            String departmentInfo = "select department as 'Operational Division', budget as 'Fiscal Allocation Balance ($)' from department";
            ResultSet resultSet = c.statement.executeQuery(departmentInfo);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }

        JButton back = new JButton("Close Ledger Panel");
        back.setBounds(230,410,240,35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        JLabel dTitle = new JLabel("CORPORATE OPERATIONAL SECTOR METRICS");
        dTitle.setBounds(15, 15, 500, 25);
        dTitle.setForeground(new Color(255, 98, 0));
        dTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel.add(dTitle);

        setUndecorated(true);
        setLayout(null);
        setSize(700,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}