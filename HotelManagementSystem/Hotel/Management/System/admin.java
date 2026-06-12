package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin extends JFrame implements ActionListener {

    JButton add_Employee, add_Room, add_Drivers, logout, back;

    public admin(){
        super("VSA LUXURY RESORTS - CENTRAL ADMINISTRATION DECK");

        // Main background layout container configuration
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1950, 1090);
        panel.setBackground(new Color(10,25,50));
        add(panel);

        // --- SECTION LABELS ---
        JLabel title = new JLabel("ADMINISTRATIVE CORE CONTROL HUB");
        title.setBounds(70, 40, 600, 40);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(new Color(255, 98, 0));
        panel.add(title);

        // --- BUTTON PLATFORMS & HOOKS ---
        add_Employee = new JButton("ADD OPERATIONAL STAFF");
        add_Employee.setBounds(250,230,250,35);
        formatAdminButtons(add_Employee);
        panel.add(add_Employee);

        add_Room = new JButton("ADD INVENTORY ROOM");
        add_Room.setBounds(250,380,250,35);
        formatAdminButtons(add_Room);
        panel.add(add_Room);

        add_Drivers = new JButton("ADD LOGISTICS DRIVER");
        add_Drivers.setBounds(250,530,250,35);
        formatAdminButtons(add_Drivers);
        panel.add(add_Drivers);

        logout = new JButton("System Logout");
        logout.setBounds(70,800,120,35);
        logout.setBackground(new Color(180, 45, 45));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Tahoma", Font.BOLD, 14));
        logout.addActionListener(this);
        panel.add(logout);

        back = new JButton("Go Back");
        back.setBounds(210,800,120,35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 14));
        back.addActionListener(this);
        panel.add(back);

        // --- IMAGE ICON REGISTRY DECK ---
        ImageIcon l1 = new ImageIcon(ClassLoader.getSystemResource("icon/employees.png"));
        Image l11 = l1.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(l11));
        label.setBounds(70,180,120,120);
        panel.add(label);

        ImageIcon imageIcon1 = new ImageIcon(ClassLoader.getSystemResource("icon/room.png"));
        Image image = imageIcon1.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT);
        JLabel label1 = new JLabel(new ImageIcon(image));
        label1.setBounds(70,340,120,120);
        panel.add(label1);

        ImageIcon imageIcon2 = new ImageIcon(ClassLoader.getSystemResource("icon/driver.png"));
        Image image1 = imageIcon2.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT);
        JLabel label2 = new JLabel(new ImageIcon(image1));
        label2.setBounds(70,500,120,120);
        panel.add(label2);

        ImageIcon imageIcon3 = new ImageIcon(ClassLoader.getSystemResource("icon/login.gif"));
        Image image2 = imageIcon3.getImage().getScaledInstance(800,600, Image.SCALE_DEFAULT);
        JLabel label3 = new JLabel(new ImageIcon(image2));
        label3.setBounds(600,100,800,600);
        panel.add(label3);

        setLayout(null);
        setSize(1950,1090);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void formatAdminButtons(JButton b) {
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Tahoma", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add_Employee){
            new AddEmployee();
        } else if (e.getSource() == add_Room) {
            new AddRoom();
        } else if (e.getSource() == add_Drivers) {
            new addDriver();
        } else if (e.getSource() == logout){
            System.exit(0);
        } else if (e.getSource() == back) {
            new Dashboard();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new admin();
    }
}