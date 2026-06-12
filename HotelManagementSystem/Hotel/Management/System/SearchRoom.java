package Hotel.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class SearchRoom extends JFrame implements ActionListener {
    JCheckBox checkBox;
    JComboBox choiceClass, choiceAC;
    JTable table;
    JButton search, back;

    SearchRoom() {
        super("VSA CENTRAL INTELLIGENCE - MULTI-CRITERIA ROOM DISCOVERY INDEX");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(10, 25, 50));
        panel.setBounds(5, 5, 890, 490);
        panel.setLayout(null);
        add(panel);

        JLabel searchForRoom = new JLabel("ADVANCED INVENTORY SEARCH CRITERIA MODULE");
        searchForRoom.setBounds(25, 15, 500, 25);
        searchForRoom.setForeground(Color.WHITE);
        searchForRoom.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel.add(searchForRoom);

        JLabel rbt = new JLabel("Class Class Tier:");
        rbt.setBounds(25, 65, 120, 20);
        rbt.setForeground(Color.WHITE);
        rbt.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(rbt);

        choiceClass = new JComboBox(new String[]{"Standard", "Deluxe", "Suite"});
        choiceClass.setBounds(145, 65, 120, 22);
        panel.add(choiceClass);

        JLabel rac = new JLabel("Climate Profile:");
        rac.setBounds(290, 65, 120, 20);
        rac.setForeground(Color.WHITE);
        rac.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(rac);

        choiceAC = new JComboBox(new String[]{"AC", "Non-AC"});
        choiceAC.setBounds(410, 65, 120, 22);
        panel.add(choiceAC);

        checkBox = new JCheckBox("Restrict Selection to Available Units Only");
        checkBox.setBounds(560, 65, 300, 22);
        checkBox.setForeground(Color.WHITE);
        checkBox.setBackground(new Color(10, 25, 50));
        panel.add(checkBox);

        table = new JTable();
        table.setBackground(new Color(16, 32, 64));
        table.setForeground(Color.WHITE);
        table.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(25, 115, 840, 290);
        panel.add(scroll);

        search = new JButton("Run Diagnostic Query");
        search.setBounds(220, 425, 200, 35);
        search.setBackground(Color.BLACK);
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        panel.add(search);

        back = new JButton("Close Search Portal");
        back.setBounds(460, 425, 200, 35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        panel.add(back);

        executeFilteredSearchActionPipeline();

        setUndecorated(true);
        setLayout(null);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeFilteredSearchActionPipeline() {
        String targetClass = (String) choiceClass.getSelectedItem();
        String targetAC = (String) choiceAC.getSelectedItem();

        StringBuilder queryBuilder = new StringBuilder("SELECT roomnumber as 'Room No', availability as 'Status', cleaning_status as 'Cleanliness', price as 'Price/Day', bed_type as 'Bed Configuration', room_class as 'Class Tier', ac_status as 'Climate' FROM room WHERE room_class = '")
                .append(targetClass).append("' AND ac_status = '").append(targetAC).append("'");

        if (checkBox.isSelected()) {
            queryBuilder.append(" AND availability = 'Available'");
        }

        try {
            DatabaseConnection c = new DatabaseConnection();
            ResultSet rs = c.statement.executeQuery(queryBuilder.toString());
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            executeFilteredSearchActionPipeline();
        } else {
            setVisible(false);
        }
    }
}