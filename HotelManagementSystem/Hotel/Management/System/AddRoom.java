package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRoom extends JFrame implements ActionListener {
    JTextField t2, t4;
    JComboBox t3, t5, t6, comboAC, comboBed;
    JButton b1, b2;

    AddRoom() {
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 875, 490);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel l1 = new JLabel("Add Hotel Rooms");
        l1.setBounds(150, 10, 220, 25);
        l1.setFont(new Font("Tahoma", Font.BOLD, 22));
        l1.setForeground(Color.WHITE);
        panel.add(l1);

        JLabel l2 = new JLabel("Room Number");
        l2.setBounds(40, 60, 130, 22);
        l2.setFont(new Font("Tahoma", Font.BOLD, 14));
        l2.setForeground(Color.WHITE);
        panel.add(l2);

        t2 = new JTextField();
        t2.setBounds(180, 60, 160, 22);
        t2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        t2.setForeground(Color.WHITE);
        t2.setBackground(new Color(16, 108, 115));
        panel.add(t2);

        JLabel l3 = new JLabel("Availability");
        l3.setBounds(40, 100, 130, 22);
        l3.setFont(new Font("Tahoma", Font.BOLD, 14));
        l3.setForeground(Color.WHITE);
        panel.add(l3);

        t3 = new JComboBox(new String[]{"Available", "Occupied"});
        t3.setBounds(180, 100, 160, 22);
        t3.setBackground(new Color(16, 108, 115));
        t3.setForeground(Color.WHITE);
        panel.add(t3);

        JLabel l4 = new JLabel("Price Index");
        l4.setBounds(40, 140, 130, 22);
        l4.setFont(new Font("Tahoma", Font.BOLD, 14));
        l4.setForeground(Color.WHITE);
        panel.add(l4);

        t4 = new JTextField();
        t4.setBounds(180, 140, 160, 22);
        t4.setForeground(Color.WHITE);
        t4.setBackground(new Color(16, 108, 115));
        panel.add(t4);

        JLabel l5 = new JLabel("Cleaning Status");
        l5.setBounds(40, 180, 130, 22);
        l5.setFont(new Font("Tahoma", Font.BOLD, 14));
        l5.setForeground(Color.WHITE);
        panel.add(l5);

        t5 = new JComboBox(new String[]{"Cleaned", "Dirty"});
        t5.setBounds(180, 180, 160, 22);
        t5.setBackground(new Color(16, 108, 115));
        t5.setForeground(Color.WHITE);
        panel.add(t5);

        JLabel labelAC = new JLabel("Air Conditioning");
        labelAC.setBounds(40, 220, 130, 22);
        labelAC.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelAC.setForeground(Color.WHITE);
        panel.add(labelAC);

        comboAC = new JComboBox(new String[]{"AC", "Non-AC"});
        comboAC.setBounds(180, 220, 160, 22);
        comboAC.setBackground(new Color(16, 108, 115));
        comboAC.setForeground(Color.WHITE);
        panel.add(comboAC);

        JLabel labelBed = new JLabel("Bed Layout");
        labelBed.setBounds(40, 260, 130, 22);
        labelBed.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelBed.setForeground(Color.WHITE);
        panel.add(labelBed);

        comboBed = new JComboBox(new String[]{"Single Bed", "Double Bed"});
        comboBed.setBounds(180, 260, 160, 22);
        comboBed.setBackground(new Color(16, 108, 115));
        comboBed.setForeground(Color.WHITE);
        panel.add(comboBed);

        JLabel l6 = new JLabel("Category Class");
        l6.setBounds(40, 300, 130, 22);
        l6.setFont(new Font("Tahoma", Font.BOLD, 14));
        l6.setForeground(Color.WHITE);
        panel.add(l6);

        t6 = new JComboBox(new String[]{"Standard", "Deluxe", "Suite"});
        t6.setBounds(180, 300, 160, 22);
        t6.setBackground(new Color(16, 108, 115));
        t6.setForeground(Color.WHITE);
        panel.add(t6);

        b1 = new JButton("Add Room");
        b1.setBounds(40, 370, 140, 33);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        panel.add(b1);

        b2 = new JButton("Back");
        b2.setBounds(200, 370, 140, 33);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        panel.add(b2);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/roomser.png"));
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(500, 60, 300, 300);
        panel.add(label);

        setUndecorated(true);
        setLayout(null);
        setSize(885, 500);
        setLocation(500, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                DatabaseConnection c = new DatabaseConnection();
                String room = t2.getText();
                String ava = (String) t3.getSelectedItem();
                String status = (String) t5.getSelectedItem();
                String price = t4.getText();
                String acValue = (String) comboAC.getSelectedItem();
                String bedValue = (String) comboBed.getSelectedItem();
                String typeClass = (String) t6.getSelectedItem();

                // Writes across both legacy and modular tracking profiles seamlessly
                String q = "INSERT INTO room VALUES('" + room + "', '" + ava + "', '" + status + "', '" + price + "', '" + bedValue + "', '" + typeClass + "', '" + acValue + "')";
                c.statement.executeUpdate(q);

                JOptionPane.showMessageDialog(null, "Room Successfully Added to Registry.");
                setVisible(false);
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }
}