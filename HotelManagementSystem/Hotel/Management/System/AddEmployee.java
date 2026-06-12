package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployee extends JFrame implements ActionListener {

    JTextField nameText, ageText, salaryText, phoneText, aadharText, emailText;
    JRadioButton radioButtonM, radioButtonF;
    ButtonGroup genderGroup;
    JComboBox comboBox;
    JButton add, back;

    public AddEmployee(){
        super("VSA CENTRAL REGISTER - ADD PERSONNEL PROFILE");

        JPanel panel = new JPanel();
        panel.setBounds(5,5,890,490);
        panel.setLayout(null);
        panel.setBackground(new Color(10,25,50));
        add(panel);

        JLabel AED = new JLabel("STAFF PROFILE REGISTRY");
        AED.setBounds(450,24,400,35);
        AED.setFont(new Font("Tahoma", Font.BOLD, 26));
        AED.setForeground(new Color(255, 98, 0));
        panel.add(AED);

        // --- FORM LABELS AND FIELDS ARRAY ---
        JLabel name = new JLabel("FULL NAME:");
        name.setBounds(60,30,150,27);
        name.setFont(new Font("Tahoma", Font.BOLD, 14));
        name.setForeground(Color.WHITE);
        panel.add(name);

        nameText = new JTextField();
        nameText.setBounds(200,30,180,27);
        setupFormTextField(nameText);
        panel.add(nameText);

        JLabel Age = new JLabel("AGE:");
        Age.setBounds(60,80,150,27);
        Age.setFont(new Font("Tahoma", Font.BOLD, 14));
        Age.setForeground(Color.WHITE);
        panel.add(Age);

        ageText = new JTextField();
        ageText.setBounds(200,80,180,27);
        setupFormTextField(ageText);
        panel.add(ageText);

        JLabel gender = new JLabel("GENDER:");
        gender.setBounds(60,120,150,27);
        gender.setFont(new Font("Tahoma", Font.BOLD, 14));
        gender.setForeground(Color.WHITE);
        panel.add(gender);

        radioButtonM = new JRadioButton("MALE");
        radioButtonM.setBounds(200,120,80,27);
        radioButtonM.setBackground(new Color(10,25,50));
        radioButtonM.setForeground(Color.WHITE);
        radioButtonM.setFont(new Font("Tahoma", Font.BOLD,12));
        panel.add(radioButtonM);

        radioButtonF = new JRadioButton("FEMALE");
        radioButtonF.setBounds(290,120,90,27);
        radioButtonF.setBackground(new Color(10,25,50));
        radioButtonF.setForeground(Color.WHITE);
        radioButtonF.setFont(new Font("Tahoma", Font.BOLD,12));
        panel.add(radioButtonF);

        genderGroup = new ButtonGroup();
        genderGroup.add(radioButtonM);
        genderGroup.add(radioButtonF);

        JLabel job = new JLabel("JOB DEPLOYMENT:");
        job.setBounds(60,170,150,27);
        job.setFont(new Font("Tahoma", Font.BOLD, 14));
        job.setForeground(Color.WHITE);
        panel.add(job);

        comboBox = new JComboBox(new String[]{"Front Desk", "Housekeeping", "Kitchen Staff","Room Service", "Manager", "Accountant","Chef"});
        comboBox.setBackground(new Color(16,108,115));
        comboBox.setBounds(200,170,180,30);
        comboBox.setFont(new Font("Tahoma", Font.BOLD,13));
        comboBox.setForeground(Color.WHITE);
        panel.add(comboBox);

        JLabel salary = new JLabel("SALARY INDEX:");
        salary.setBounds(60,220,150,27);
        salary.setFont(new Font("Tahoma", Font.BOLD, 14));
        salary.setForeground(Color.WHITE);
        panel.add(salary);

        salaryText = new JTextField();
        salaryText.setBounds(200,220,180,27);
        setupFormTextField(salaryText);
        panel.add(salaryText);

        JLabel phone = new JLabel("CONTACT NO:");
        phone.setBounds(60,270,150,27);
        phone.setFont(new Font("Tahoma", Font.BOLD, 14));
        phone.setForeground(Color.WHITE);
        panel.add(phone);

        phoneText = new JTextField();
        phoneText.setBounds(200,270,180,27);
        setupFormTextField(phoneText);
        panel.add(phoneText);

        JLabel aadhar = new JLabel("AADHAR CARD:");
        aadhar.setBounds(60,320,150,27);
        aadhar.setFont(new Font("Tahoma", Font.BOLD, 14));
        aadhar.setForeground(Color.WHITE);
        panel.add(aadhar);

        aadharText = new JTextField();
        aadharText.setBounds(200,320,180,27);
        setupFormTextField(aadharText);
        panel.add(aadharText);

        JLabel email = new JLabel("EMAIL ID:");
        email.setBounds(60,370,150,27);
        email.setFont(new Font("Tahoma", Font.BOLD, 14));
        email.setForeground(Color.WHITE);
        panel.add(email);

        emailText = new JTextField();
        emailText.setBounds(200,370,180,27);
        setupFormTextField(emailText);
        panel.add(emailText);

        // --- BUTTONS ---
        add = new JButton("SAVE RECORD");
        add.setBounds(60,430,140,30);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.addActionListener(this);
        panel.add(add);

        back = new JButton("CANCEL");
        back.setBounds(220,430,140,30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        panel.add(back);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/addemp.png"));
        Image image = imageIcon.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(500,100,300,300);
        panel.add(label);

        setUndecorated(true);
        setLayout(null);
        setSize(900,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupFormTextField(JTextField tf) {
        tf.setBackground(new Color(16,108,115));
        tf.setFont(new Font("Tahoma", Font.BOLD,13));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add){
            String name = nameText.getText().trim();
            String age = ageText.getText().trim();
            String salary = salaryText.getText().trim();
            String phone = phoneText.getText().trim();
            String email = emailText.getText().trim();
            String aadhar = aadharText.getText().trim();
            String job = (String) comboBox.getSelectedItem();
            String gender = radioButtonM.isSelected() ? "Male" : "Female";

            if (name.isEmpty() || aadhar.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: Crucial identification fields cannot be blank.");
                return;
            }

            try{
                DatabaseConnection c = new DatabaseConnection();
                // FIXED: Removed the accidental 'skin' typo below
                String q = "insert into employee values('"+name+"', '"+age+"', '"+gender+"', '"+job+"', '"+salary+"','"+phone+"', '"+email+"', '"+aadhar+"')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Staff Member Profile Written Successfully.");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace();
            }
        }else{
            setVisible(false);
        }
    }
}