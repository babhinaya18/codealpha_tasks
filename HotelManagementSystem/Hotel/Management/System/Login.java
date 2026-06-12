package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField textField1;
    JPasswordField passwordField1;
    JButton b1, b2;

    public Login() {
        super("VSA SECURE AUTHENTICATION SYSTEM - PRINCIPAL ENTERPRISE ARCHITECTURE GATEWAY");

        JLabel label1 = new JLabel("STAFF USERNAME");
        label1.setBounds(40, 30, 200, 30);
        label1.setFont(new Font("Tahoma", Font.BOLD, 15));
        label1.setForeground(Color.WHITE);
        add(label1);

        textField1 = new JTextField();
        textField1.setBounds(40, 65, 280, 35);
        textField1.setForeground(Color.WHITE);
        textField1.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField1.setBackground(new Color(26, 104, 110));
        textField1.setCaretColor(Color.WHITE);
        add(textField1);

        JLabel label2 = new JLabel("SECURITY ACCESS PASSWORD");
        label2.setBounds(40, 120, 250, 30);
        label2.setFont(new Font("Tahoma", Font.BOLD, 15));
        label2.setForeground(Color.WHITE);
        add(label2);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(40, 155, 280, 35);
        passwordField1.setForeground(Color.WHITE);
        passwordField1.setBackground(new Color(26, 104, 110));
        passwordField1.setCaretColor(Color.WHITE);
        add(passwordField1);

        b1 = new JButton("AUTHORIZE GATE");
        b1.setBounds(40, 230, 280, 40);
        b1.setFont(new Font("Tahoma", Font.BOLD, 14));
        b1.setBackground(new Color(255, 98, 0));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("RETURN GATEWAY");
        b2.setBounds(40, 290, 280, 35);
        b2.setFont(new Font("Tahoma", Font.BOLD, 12));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/login.gif"));
        JLabel label = new JLabel(imageIcon);
        label.setBounds(350, 0, 850, 650);
        add(label);

        getContentPane().setBackground(new Color(10, 25, 50));
        setLayout(null);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                DatabaseConnection c = new DatabaseConnection();
                String user = textField1.getText().trim();
                String pass = new String(passwordField1.getPassword()).trim();

                String q = "select * from login where username = '" + user + "' and password = '" + pass + "'";
                ResultSet resultSet = c.statement.executeQuery(q);
                if (resultSet.next()) {
                    new Dashboard();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Access Terminated: Invalid Staff Signature Credentials.");
                }
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            new PortalSelectionScreen();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}