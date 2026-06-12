package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Room extends JFrame {
    JTable table;
    JButton back;

    Room() {
        super("VSA CENTRAL OPERATIONS - SYSTEM ROOM DIRECTORY MATRIX");

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 990, 590);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        table = new JTable();
        table.setBackground(new Color(16, 32, 64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 50, 680, 440);
        panel.add(scroll);

        try {
            DatabaseConnection c = new DatabaseConnection();
            // Maps comprehensive relational information dynamically into structural user row models
            String q = "SELECT roomnumber as 'Room No', availability as 'Status', cleaning_status as 'Cleanliness', price as 'Base Price/Day', bed_type as 'Layout', room_class as 'Class Tier', ac_status as 'Climate' FROM room";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        back = new JButton("Close View Workspace");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 510, 220, 35);
        panel.add(back);
        back.addActionListener(e -> setVisible(false));

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/roomm.png"));
        Image image = imageIcon.getImage().getScaledInstance(230, 230, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(730, 150, 230, 230);
        panel.add(label);

        setUndecorated(true);
        setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}