package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSignUpScreen extends JFrame implements ActionListener {
    JTextField txtUser, txtPhone, txtOtp;
    JPasswordField txtPass;
    JButton btnSendOtp, btnVerifyOtp, btnRegister, btnBack;
    String generatedOtpCode = "";
    boolean identityCodePassedFlag = false;

    public UserSignUpScreen() {
        super("VSA LUXURY RESORTS - MEMBER REGISTRATION");

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 480, 480);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("REGISTER GUEST ACCOUNT");
        title.setBounds(110, 15, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel lblUser = new JLabel("Choose Username:");
        lblUser.setBounds(40, 65, 200, 20);
        lblUser.setForeground(Color.WHITE);
        panel.add(lblUser);
        txtUser = new JTextField();
        txtUser.setBounds(40, 85, 400, 30);
        panel.add(txtUser);

        JLabel lblPhone = new JLabel("Phone Contact Number:");
        lblPhone.setBounds(40, 125, 200, 20);
        lblPhone.setForeground(Color.WHITE);
        panel.add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(40, 145, 260, 30);
        panel.add(txtPhone);

        btnSendOtp = new JButton("Get OTP Code");
        btnSendOtp.setBounds(310, 145, 130, 30);
        btnSendOtp.setBackground(Color.BLACK);
        btnSendOtp.setForeground(Color.WHITE);
        btnSendOtp.addActionListener(this);
        panel.add(btnSendOtp);

        JLabel lblOtp = new JLabel("Enter 4-Digit OTP:");
        lblOtp.setBounds(40, 185, 200, 20);
        lblOtp.setForeground(Color.WHITE);
        panel.add(lblOtp);
        txtOtp = new JTextField();
        txtOtp.setBounds(40, 205, 260, 30);
        panel.add(txtOtp);

        btnVerifyOtp = new JButton("Verify OTP");
        btnVerifyOtp.setBounds(310, 205, 130, 30);
        btnVerifyOtp.setBackground(Color.BLACK);
        btnVerifyOtp.setForeground(Color.WHITE);
        btnVerifyOtp.addActionListener(this);
        panel.add(btnVerifyOtp);

        JLabel lblPass = new JLabel("Create System Password:");
        lblPass.setBounds(40, 250, 200, 20);
        lblPass.setForeground(Color.WHITE);
        panel.add(lblPass);
        txtPass = new JPasswordField();
        txtPass.setBounds(40, 270, 400, 30);
        panel.add(txtPass);

        btnRegister = new JButton("COMPLETE SYSTEM REGISTRATION");
        btnRegister.setBounds(40, 340, 400, 45);
        btnRegister.setBackground(new Color(255, 98, 0));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnRegister.addActionListener(this);
        panel.add(btnRegister);

        btnBack = new JButton("Cancel and Go Back");
        btnBack.setBounds(40, 400, 400, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        panel.add(btnBack);

        getContentPane().setBackground(new Color(7, 15, 35));
        setLayout(null);
        setSize(500, 520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSendOtp) {
            if (txtPhone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: Valid phone entry expected.");
                return;
            }
            int dynamicTokenVal = (int)(Math.random() * 9000) + 1000;
            generatedOtpCode = String.valueOf(dynamicTokenVal);
            JOptionPane.showMessageDialog(null, "[VSA MOBILE ENGINE INTERFACE]\nYour security verification OTP code pin is: " + generatedOtpCode);
        } else if (e.getSource() == btnVerifyOtp) {
            if (!generatedOtpCode.isEmpty() && txtOtp.getText().trim().equals(generatedOtpCode)) {
                identityCodePassedFlag = true;
                JOptionPane.showMessageDialog(null, "Phone verification passed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid token signature entered.");
            }
        } else if (e.getSource() == btnRegister) {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();
            String ph = txtPhone.getText().trim();

            if (u.isEmpty() || p.isEmpty() || ph.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: Fill out all registration spaces.");
                return;
            }
            if (!identityCodePassedFlag) {
                JOptionPane.showMessageDialog(null, "Security Bypass Attempted: Pass OTP validation code step first.");
                return;
            }

            try {
                DatabaseConnection c = new DatabaseConnection();
                String statementStringQuery = "INSERT INTO hotel_users VALUES('" + u + "', '" + p + "', '" + ph + "')";
                c.statement.executeUpdate(statementStringQuery);
                JOptionPane.showMessageDialog(null, "Account successfully written to MySQL server instances!");
                new UserLoginScreen();
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Username has been taken. Pick a distinct value registration marker.");
                ex.printStackTrace();
            }
        } else if (e.getSource() == btnBack) {
            new UserLoginScreen();
            setVisible(false);
        }
    }
}