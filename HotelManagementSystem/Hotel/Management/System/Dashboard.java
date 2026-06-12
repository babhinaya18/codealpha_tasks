package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {
    JButton add, rec;

    public Dashboard(){
        super("VSA LUXURY SYSTEMS - ENTERPRISE OPERATIONS CENTER");

        JPanel backgroundContainer = new JPanel(null);
        backgroundContainer.setBounds(0, 0, 1950, 1090);
        backgroundContainer.setBackground(new Color(10, 25, 50));
        add(backgroundContainer);

        // --- NAVIGATION CONTROLS ---
        rec = new JButton("RECEPTION DESK PLATFORM");
        rec.setBounds(420,510,240,35);
        rec.setFont(new Font("Tahoma", Font.BOLD,14));
        rec.setBackground(new Color(255,98,0));
        rec.setForeground(Color.WHITE);
        rec.setFocusPainted(false);
        rec.addActionListener(this);
        backgroundContainer.add(rec);

        add = new JButton("ADMIN CONTROL CORE");
        add.setBounds(850,510,240,35);
        add.setFont(new Font("Tahoma", Font.BOLD,14));
        add.setBackground(new Color(255,98,0));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        add.addActionListener(this);
        backgroundContainer.add(add);

        // --- BRANDING INTERFACE IMAGES ---
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icon/boss.png"));
        Image i2 = i11.getImage().getScaledInstance(200,195, Image.SCALE_DEFAULT);
        JLabel label1 = new JLabel(new ImageIcon(i2));
        label1.setBounds(870,300,200,195);
        backgroundContainer.add(label1);

        ImageIcon i111 = new ImageIcon(ClassLoader.getSystemResource("icon/Reception.png"));
        Image i22 = i111.getImage().getScaledInstance(200,195, Image.SCALE_DEFAULT);
        JLabel label11 = new JLabel(new ImageIcon(i22));
        label11.setBounds(440,300,200,195);
        backgroundContainer.add(label11);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/Dashboard.gif"));
        Image i1 = imageIcon.getImage().getScaledInstance(1950,1090, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(i1));
        label.setBounds(0,0,1950,1090);
        backgroundContainer.add(label);

        setLayout(null);
        setSize(1950,1090);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rec){
            new Reception();
            setVisible(false);
        } else {
            new Login2(); // Secure verification gateway check path leading cleanly to administrator panels
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}