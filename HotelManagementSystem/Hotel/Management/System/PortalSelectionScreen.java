package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortalSelectionScreen extends JFrame implements ActionListener {
    JButton btnStaff, btnUser;

    public PortalSelectionScreen() {
        super("VSA LUXURY RESORTS - PORTAL SYSTEM SELECTOR");

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 565, 345);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel lblTitle = new JLabel("VSA LUXURY HOTELS & RESORTS", JLabel.CENTER);
        lblTitle.setBounds(30, 30, 500, 35);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(255, 98, 0));
        panel.add(lblTitle);

        JLabel lblSub = new JLabel("Please choose an entry portal platform path to log in:", JLabel.CENTER);
        lblSub.setBounds(30, 80, 500, 20);
        lblSub.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSub.setForeground(Color.WHITE);
        panel.add(lblSub);

        btnUser = new JButton("GUEST ONLINE RESERVATION PORTAL");
        btnUser.setBounds(100, 140, 360, 45);
        btnUser.setBackground(new Color(255, 98, 0));
        btnUser.setForeground(Color.WHITE);
        btnUser.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUser.setFocusPainted(false);
        btnUser.addActionListener(this);
        panel.add(btnUser);

        btnStaff = new JButton("OFFICIAL STAFF MANAGEMENT ENTERPRISE");
        btnStaff.setBounds(100, 210, 360, 45);
        btnStaff.setBackground(new Color(16, 108, 115));
        btnStaff.setForeground(Color.WHITE);
        btnStaff.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnStaff.setFocusPainted(false);
        btnStaff.addActionListener(this);
        panel.add(btnStaff);

        getContentPane().setBackground(new Color(7, 15, 35));
        setLayout(null);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUser) {
            new UserLoginScreen();
            setVisible(false);
        } else if (e.getSource() == btnStaff) {
            new Login(); // Routes cleanly to official staff/admin operations verification entry points
            setVisible(false);
        }
    }
}