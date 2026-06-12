package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class UserLoginScreen extends JFrame implements ActionListener {
    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin, btnSignUp, btnBack;

    public UserLoginScreen() {
        super("VSA HUB - GUEST REGISTRATION SIGN IN");

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 480, 430);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("GUEST BOOKING LOGIN");
        title.setBounds(120, 25, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel lblUser = new JLabel("Account Username:");
        lblUser.setBounds(45, 90, 200, 20);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUser.setForeground(Color.WHITE);
        panel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(45, 115, 390, 35);
        txtUsername.setBackground(new Color(26, 104, 110));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtUsername.setCaretColor(Color.WHITE);
        panel.add(txtUsername);

        JLabel lblPass = new JLabel("Secret Security Password:");
        lblPass.setBounds(45, 165, 200, 20);
        lblPass.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblPass.setForeground(Color.WHITE);
        panel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(45, 190, 390, 35);
        txtPassword.setBackground(new Color(26, 104, 110));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        panel.add(txtPassword);

        btnLogin = new JButton("ACCESS SYSTEM PORTAL");
        btnLogin.setBounds(45, 255, 390, 40);
        btnLogin.setBackground(new Color(255, 98, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogin.addActionListener(this);
        panel.add(btnLogin);

        JLabel lblPrompt = new JLabel("Don't have an account with VSA Hotel yet?", JLabel.CENTER);
        lblPrompt.setBounds(20, 315, 440, 20);
        lblPrompt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPrompt.setForeground(Color.LIGHT_GRAY);
        panel.add(lblPrompt);

        btnSignUp = new JButton("SIGN UP HERE");
        btnSignUp.setBounds(45, 345, 180, 35);
        btnSignUp.setBackground(Color.BLACK);
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.addActionListener(this);
        panel.add(btnSignUp);

        btnBack = new JButton("RETURN GATEWAY");
        btnBack.setBounds(255, 345, 180, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        panel.add(btnBack);

        getContentPane().setBackground(new Color(7, 15, 35));
        setLayout(null);
        setSize(500, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String u = txtUsername.getText().trim();
            String p = new String(txtPassword.getPassword()).trim();
            try {
                DatabaseConnection c = new DatabaseConnection();
                String q = "SELECT * FROM hotel_users WHERE username='" + u + "' AND password='" + p + "'";
                ResultSet rs = c.statement.executeQuery(q);
                if (rs.next()) {
                    new GuestDashboardScreen(u);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Access Denied: Invalid Username or Password Match.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == btnSignUp) {
            new UserSignUpScreen();
            setVisible(false);
        } else if (e.getSource() == btnBack) {
            new PortalSelectionScreen();
            setVisible(false);
        }
    }
}