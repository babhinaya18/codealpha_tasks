package com.stocktrading;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("VSTread Gateway");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(10, 11, 14));
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("VSTread Auth Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(240, 165, 0));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);

        c.gridwidth = 1;
        JLabel userLabel = new JLabel("Username ID:");
        userLabel.setForeground(Color.LIGHT_GRAY);
        c.gridx = 0; c.gridy = 1;
        add(userLabel, c);

        userField = new JTextField(15);
        userField.setBackground(new Color(24, 26, 32));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        userField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        c.gridx = 1; c.gridy = 1;
        add(userField, c);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.LIGHT_GRAY);
        c.gridx = 0; c.gridy = 2;
        add(passLabel, c);

        passField = new JPasswordField(15);
        passField.setBackground(new Color(24, 26, 32));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        c.gridx = 1; c.gridy = 2;
        add(passField, c);

        JButton loginButton = new JButton("Login Secure Session");
        loginButton.setBackground(new Color(240, 165, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        loginButton.setFocusPainted(false);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        add(loginButton, c);

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty()) return;

            if (DatabaseManager.validateLogin(username, password)) {
                dispose();
                new TradingGUI(username);
            } else {
                JOptionPane.showMessageDialog(this, "Profile not matched. Falling back to local offline mode.");
                dispose();
                new TradingGUI(username);
            }
        });

        setVisible(true);
    }
}