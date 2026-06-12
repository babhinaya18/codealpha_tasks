package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class NewCustomer extends JFrame implements ActionListener {
    JComboBox comboBox;
    JTextField textFieldNumber, TextName, TextCountry, TextDeposite;
    JRadioButton r1, r2;
    ButtonGroup genderGroup;
    Choice c1;
    JLabel date;
    JButton add, back;

    public NewCustomer() {
        super("Hotel Reception - Guest Check-In Form");

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 840, 540);
        panel.setLayout(null);
        panel.setBackground(new Color(10, 25, 50));
        add(panel);

        JLabel labelName = new JLabel("GUEST CHECK-IN FORM");
        labelName.setBounds(118, 11, 300, 53);
        labelName.setFont(new Font("Tahoma", Font.BOLD, 22));
        labelName.setForeground(new Color(255, 98, 0));
        panel.add(labelName);

        // --- ID TYPE ---
        JLabel labelID = new JLabel("Choose ID Type:");
        labelID.setBounds(35, 76, 200, 20);
        labelID.setForeground(Color.WHITE);
        labelID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelID);

        comboBox = new JComboBox(new String[]{"Aadhar Card", "Passport", "Voter ID", "Driving License"});
        comboBox.setBounds(271, 73, 180, 24);
        comboBox.setBackground(new Color(16, 108, 115));
        comboBox.setForeground(Color.WHITE);
        panel.add(comboBox);

        // --- ID NUMBER ---
        JLabel labelNumberLbl = new JLabel("ID Card Number:");
        labelNumberLbl.setBounds(35, 115, 200, 20);
        labelNumberLbl.setForeground(Color.WHITE);
        labelNumberLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelNumberLbl);

        textFieldNumber = new JTextField();
        textFieldNumber.setBounds(271, 115, 180, 24);
        panel.add(textFieldNumber);

        // --- GUEST NAME ---
        JLabel labelname = new JLabel("Guest Full Name:");
        labelname.setBounds(35, 155, 200, 20);
        labelname.setForeground(Color.WHITE);
        labelname.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelname);

        TextName = new JTextField();
        TextName.setBounds(271, 155, 180, 24);
        panel.add(TextName);

        // --- GENDER ---
        JLabel labelGender = new JLabel("Gender:");
        labelGender.setBounds(35, 195, 200, 20);
        labelGender.setForeground(Color.WHITE);
        labelGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelGender);

        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Tahoma", Font.BOLD, 14));
        r1.setForeground(Color.WHITE);
        r1.setBackground(new Color(10, 25, 50));
        r1.setBounds(271, 195, 80, 22);
        panel.add(r1);

        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Tahoma", Font.BOLD, 14));
        r2.setForeground(Color.WHITE);
        r2.setBackground(new Color(10, 25, 50));
        r2.setBounds(350, 195, 90, 22);
        panel.add(r2);

        genderGroup = new ButtonGroup();
        genderGroup.add(r1);
        genderGroup.add(r2);

        // --- COUNTRY ---
        JLabel labelCountry = new JLabel("Country Name:");
        labelCountry.setBounds(35, 235, 200, 20);
        labelCountry.setForeground(Color.WHITE);
        labelCountry.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelCountry);

        TextCountry = new JTextField();
        TextCountry.setBounds(271, 235, 180, 24);
        panel.add(TextCountry);

        // --- ROOM NUMBER ---
        JLabel labelRoom = new JLabel("Assign Room Number:");
        labelRoom.setBounds(35, 275, 200, 20);
        labelRoom.setForeground(Color.WHITE);
        labelRoom.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelRoom);

        c1 = new Choice();
        try {
            DatabaseConnection c = new DatabaseConnection();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM room WHERE availability = 'Available'");
            while (resultSet.next()) {
                c1.add(resultSet.getString("roomnumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c1.setBounds(271, 275, 180, 24);
        c1.setBackground(new Color(16, 108, 115));
        c1.setForeground(Color.WHITE);
        panel.add(c1);

        // --- TIME ---
        JLabel labelCIS = new JLabel("Check-In Date & Time:");
        labelCIS.setBounds(35, 315, 200, 20);
        labelCIS.setForeground(Color.WHITE);
        labelCIS.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelCIS);

        date = new JLabel("" + new Date());
        date.setBounds(271, 315, 250, 20);
        date.setForeground(Color.WHITE);
        date.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(date);

        // --- ADVANCE PAYMENT ---
        JLabel labelDeposite = new JLabel("Advance Payment Amount:");
        labelDeposite.setBounds(35, 355, 200, 20);
        labelDeposite.setForeground(Color.WHITE);
        labelDeposite.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(labelDeposite);

        TextDeposite = new JTextField();
        TextDeposite.setBounds(271, 355, 180, 24);
        panel.add(TextDeposite);

        // --- BUTTONS ---
        add = new JButton("Save & Check-In");
        add.setBounds(50, 430, 160, 35);
        add.setForeground(Color.WHITE);
        add.setBackground(Color.BLACK);
        add.addActionListener(this);
        panel.add(add);

        back = new JButton("Cancel");
        back.setBounds(240, 430, 160, 35);
        back.setForeground(Color.WHITE);
        back.setBackground(Color.BLACK);
        back.addActionListener(this);
        panel.add(back);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/customer.png"));
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel imglabel = new JLabel(new ImageIcon(image));
        imglabel.setBounds(550, 150, 200, 200);
        panel.add(imglabel);

        setLayout(null);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            String genderStr = r1.isSelected() ? "Male" : "Female";
            String idType = (String) comboBox.getSelectedItem();
            String idNumber = textFieldNumber.getText().trim();
            String name = TextName.getText().trim();
            String country = TextCountry.getText().trim();
            String roomNum = c1.getSelectedItem();
            String checkInTime = date.getText();
            String deposit = TextDeposite.getText().trim();

            if (idNumber.isEmpty() || name.isEmpty() || roomNum == null) {
                JOptionPane.showMessageDialog(null, "Please fill in all the required fields before saving.");
                return;
            }

            try {
                DatabaseConnection c = new DatabaseConnection();
                String q = "INSERT INTO customer VALUES ('" + idType + "', '" + idNumber + "', '" + name + "', '" + genderStr + "', '" + country + "', '" + roomNum + "', '" + checkInTime + "', '" + deposit + "')";
                String q1 = "UPDATE room SET availability = 'Occupied' WHERE roomnumber = '" + roomNum + "'";
                c.statement.executeUpdate(q);
                c.statement.executeUpdate(q1);

                JOptionPane.showMessageDialog(null, "Guest checked in successfully!");
                setVisible(false);
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}