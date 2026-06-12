package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addDriver extends JFrame implements ActionListener {
    JTextField nameText, ageText, carCText, carNText, locText;
    JComboBox comboBox, comboBox1;
    JButton add, back;

    public addDriver(){
        super("VSA CENTRAL LOGISTICS - ADD FLEET DRIVER");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,890,490);
        panel.setBackground(new Color(10,25,50));
        panel.setLayout(null);
        add(panel);

        JLabel label = new JLabel("FLEET LOGISTICS REGISTRY");
        label.setBounds(150,10,400,25);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.BOLD, 22));
        panel.add(label);

        // --- LABELS AND FLUID INTERFACE FIELDS ---
        JLabel name = new JLabel("DRIVER NAME:");
        name.setBounds(40,70,120,22);
        name.setFont(new Font("Tahoma", Font.BOLD,14));
        name.setForeground(Color.WHITE);
        panel.add(name);

        nameText = new JTextField();
        nameText.setBounds(180,70,160,22);
        setupDriverFields(nameText);
        panel.add(nameText);

        JLabel age = new JLabel("DRIVER AGE:");
        age.setBounds(40,110,120,22);
        age.setFont(new Font("Tahoma", Font.BOLD,14));
        age.setForeground(Color.WHITE);
        panel.add(age);

        ageText = new JTextField();
        ageText.setBounds(180,110,160,22);
        setupDriverFields(ageText);
        panel.add(ageText);

        JLabel gender = new JLabel("GENDER:");
        gender.setBounds(40,150,120,22);
        gender.setFont(new Font("Tahoma", Font.BOLD,14));
        gender.setForeground(Color.WHITE);
        panel.add(gender);

        comboBox = new JComboBox(new String[]{"Male","Female"});
        comboBox.setBounds(180,150,160,22);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(16,108,115));
        panel.add(comboBox);

        JLabel carC = new JLabel("CAR BRAND:");
        carC.setBounds(40,190,120,22);
        carC.setFont(new Font("Tahoma", Font.BOLD,14));
        carC.setForeground(Color.WHITE);
        panel.add(carC);

        carCText = new JTextField();
        carCText.setBounds(180,190,160,22);
        setupDriverFields(carCText);
        panel.add(carCText);

        JLabel carN = new JLabel("CAR MODEL:");
        carN.setBounds(40,230,120,22);
        carN.setFont(new Font("Tahoma", Font.BOLD,14));
        carN.setForeground(Color.WHITE);
        panel.add(carN);

        carNText = new JTextField();
        carNText.setBounds(180,230,160,22);
        setupDriverFields(carNText);
        panel.add(carNText);

        JLabel available = new JLabel("AVAILABILITY:");
        available.setBounds(40,270,120,22);
        available.setFont(new Font("Tahoma", Font.BOLD,14));
        available.setForeground(Color.WHITE);
        panel.add(available);

        comboBox1 = new JComboBox(new String[]{"YES","NO"});
        comboBox1.setBounds(180,270,160,22);
        comboBox1.setForeground(Color.WHITE);
        comboBox1.setBackground(new Color(16,108,115));
        panel.add(comboBox1);

        JLabel loc = new JLabel("BASE LOCATION:");
        loc.setBounds(40,310,120,22);
        loc.setFont(new Font("Tahoma", Font.BOLD,14));
        loc.setForeground(Color.WHITE);
        panel.add(loc);

        locText = new JTextField();
        locText.setBounds(180,310,160,22);
        setupDriverFields(locText);
        panel.add(locText);

        add = new JButton("ADD DRIVER");
        add.setBounds(40,380,130,33);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.addActionListener(this);
        panel.add(add);

        back = new JButton("BACK");
        back.setBounds(190,380,130,33);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        panel.add(back);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/license.png"));
        Image image = imageIcon.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT);
        JLabel label1 = new JLabel(new ImageIcon(image));
        label1.setBounds(500,60,300,300);
        panel.add(label1);

        setUndecorated(true);
        setLayout(null);
        setSize(900,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupDriverFields(JTextField f) {
        f.setForeground(Color.WHITE);
        f.setFont(new Font("Tahoma",Font.BOLD,13));
        f.setBackground(new Color(16,108,115));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add){
            String name = nameText.getText().trim();
            String age = ageText.getText().trim();
            String gender = (String) comboBox.getSelectedItem();
            String company = carCText.getText().trim();
            String carname = carNText.getText().trim();
            String available =(String) comboBox1.getSelectedItem();
            String location = locText.getText().trim();

            if (name.isEmpty() || carname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operational Error: Incomplete Fleet Entry Logistics Fields.");
                return;
            }

            try{
                DatabaseConnection c = new DatabaseConnection();
                String q = "insert into driver values('"+name+"','"+age+"', '"+gender+"', '"+company+"', '"+carname+"','"+available+"', '"+location+"')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Logistics Driver Document Saved.");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace();
            }
        }else {
            setVisible(false);
        }
    }
}