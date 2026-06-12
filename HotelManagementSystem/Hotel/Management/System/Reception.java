package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reception extends JFrame {
    public Reception() {
        super("VSA SYSTEM PORTAL - RECEPTION DESK CORE CONTROLS");

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(5, 5, 270, 820);
        panel1.setBackground(new Color(16, 32, 64)); // Darker navy sapphire sidebar tint
        panel1.setBorder(BorderFactory.createLineBorder(new Color(16, 108, 115), 1));
        add(panel1);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(280, 5, 1238, 820);
        panel.setBackground(new Color(10, 25, 50));
        add(panel);

        // Core Branding Decorative Space Context Header
        JLabel headerTag = new JLabel("STAFF OPERATION DESK", JLabel.LEFT);
        headerTag.setBounds(40, 20, 600, 35);
        headerTag.setFont(new Font("Tahoma", Font.BOLD, 24));
        headerTag.setForeground(new Color(255, 98, 0));
        panel.add(headerTag);

        ImageIcon i111 = new ImageIcon(ClassLoader.getSystemResource("icon/cccc.gif"));
        Image i22 = i111.getImage().getScaledInstance(1150, 700, Image.SCALE_DEFAULT);
        JLabel label11 = new JLabel(new ImageIcon(i22));
        label11.setBounds(40, 70, 1150, 700);
        panel.add(label11);

        // Operational Menu Action System Array configuration
        JButton btnNCF = new JButton("Walk-In Registration");
        formatReceptionButtons(btnNCF, 30);
        panel1.add(btnNCF);
        btnNCF.addActionListener(e -> new NewCustomer());

        // UNIQUE HIGH-VALUE FACULTY FEATURE: Handle Web Placements Approval Core System
        JButton btnOnlineReg = new JButton("Online Reservations Queue");
        formatReceptionButtons(btnOnlineReg, 70);
        btnOnlineReg.setBackground(new Color(255, 98, 0)); // Highlighted orange branding
        panel1.add(btnOnlineReg);
        btnOnlineReg.addActionListener(e -> new OnlineBookingsApprovalScreen());

        JButton btnRoom = new JButton("Room Inventory Status");
        formatReceptionButtons(btnRoom, 110);
        panel1.add(btnRoom);
        btnRoom.addActionListener(e -> new Room());

        JButton btnDepartment = new JButton("Department Ledger");
        formatReceptionButtons(btnDepartment, 150);
        panel1.add(btnDepartment);
        btnDepartment.addActionListener(e -> new Department());

        JButton btnAEI = new JButton("All Employee Information");
        formatReceptionButtons(btnAEI, 190);
        panel1.add(btnAEI);
        btnAEI.addActionListener(e -> new Employee());

        JButton btnCI = new JButton("In-House Customer Logs");
        formatReceptionButtons(btnCI, 230);
        panel1.add(btnCI);
        btnCI.addActionListener(e -> new CustomerInfo());

        JButton btnMI = new JButton("Managerial Staff Directory");
        formatReceptionButtons(btnMI, 270);
        panel1.add(btnMI);
        btnMI.addActionListener(e -> new ManagerInfo());

        JButton btnCO = new JButton("Checkout Financial Settlement");
        formatReceptionButtons(btnCO, 310);
        panel1.add(btnCO);
        btnCO.addActionListener(e -> new CheckOut());

        JButton btnUC = new JButton("Update Check-In Ledger");
        formatReceptionButtons(btnUC, 350);
        panel1.add(btnUC);
        btnUC.addActionListener(e -> new UpdateCheck());

        JButton btnURS = new JButton("Update Room Status Info");
        formatReceptionButtons(btnURS, 390);
        panel1.add(btnURS);
        btnURS.addActionListener(e -> new UpdateRoom());

        JButton btnPUS = new JButton("Logistics / Pick up System");
        formatReceptionButtons(btnPUS, 430);
        panel1.add(btnPUS);
        btnPUS.addActionListener(e -> new PickUp());

        JButton btnSR = new JButton("Multi-Criteria Room Search");
        formatReceptionButtons(btnSR, 470);
        panel1.add(btnSR);
        btnSR.addActionListener(e -> new SearchRoom());

        JButton logout = new JButton("Logout System");
        logout.setBounds(30, 520, 200, 30);
        logout.setBackground(new Color(180, 45, 45));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel1.add(logout);
        logout.addActionListener(e -> System.exit(0));

        JButton back = new JButton("Return Dashboard");
        back.setBounds(30, 560, 200, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel1.add(back);
        back.addActionListener(e -> {
            setVisible(false);
            new Dashboard();
        });

        getContentPane().setBackground(new Color(7, 15, 35));
        setLayout(null);
        setSize(1550, 880);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void formatReceptionButtons(JButton b, int yCoord) {
        b.setBounds(30, yCoord, 200, 32);
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setFocusPainted(false);
    }

    public static void main(String[] args) {
        new Reception();
    }
}