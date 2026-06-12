package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login2 extends JFrame implements ActionListener {
    JTextField textField1;
    JPasswordField passwordField1;
    JButton b1, b2;

    public Login2(){
        super("VSA SYSTEMS - ADMINISTRATOR SECURITY VERIFICATION PORTAL");

        JLabel label1 = new JLabel("ADMIN SYSTEM USERNAME");
        label1.setBounds(40, 25, 250, 30);
        label1.setFont(new Font("Tahoma", Font.BOLD, 14));
        label1.setForeground(Color.WHITE);
        add(label1);

        textField1 = new JTextField();
        textField1.setBounds(40, 60, 260, 35);
        textField1.setForeground(Color.WHITE);
        textField1.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField1.setBackground(new Color(26, 104, 110));
        textField1.setCaretColor(Color.WHITE);
        add(textField1);

        JLabel label2 = new JLabel("ROOT MASTER PASSWORD");
        label2.setBounds(40, 115, 250, 30);
        label2.setFont(new Font("Tahoma", Font.BOLD, 14));
        label2.setForeground(Color.WHITE);
        add(label2);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(40, 150, 260, 35);
        passwordField1.setForeground(Color.WHITE);
        passwordField1.setBackground(new Color(26, 104, 110));
        passwordField1.setCaretColor(Color.WHITE);
        add(passwordField1);

        b1 = new JButton("VERIFY ADMINISTRATIVE ACCESS");
        b1.setBounds(40, 220, 260, 40);
        b1.setFont(new Font("Tahoma", Font.BOLD, 12));
        b1.setBackground(new Color(255, 98, 0));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("CANCEL GATEWAY");
        b2.setBounds(40, 280, 260, 35);
        b2.setFont(new Font("Tahoma", Font.BOLD, 12));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/login.gif"));
        Image i1 = imageIcon.getImage().getScaledInstance(700, 400, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(i1));
        label.setBounds(320, 10, 700, 430);
        add(label);

        getContentPane().setBackground(new Color(10, 25, 50));
        setLayout(null);
        setSize(1060, 490);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1){
            try {
                DatabaseConnection c = new DatabaseConnection();
                String user = textField1.getText().trim();
                String pass = new String(passwordField1.getPassword()).trim();

                String q = "select * from login2 where username = '"+user+"' and password = '"+pass+"'";
                ResultSet resultSet = c.statement.executeQuery(q);
                if (resultSet.next()){
                    new admin();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Access Denied: Invalid Administrative Root Credentials.");
                }
            } catch (Exception E){
                E.printStackTrace();
            }
        } else {
            new Dashboard();
            setVisible(false);
        }
    }
}