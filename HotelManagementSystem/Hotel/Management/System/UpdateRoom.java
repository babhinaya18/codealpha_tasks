package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UpdateRoom extends JFrame {
    Choice c;
    JTextField textField3, textField4, textField5;
    JButton update, back, check;

    public UpdateRoom(){
        super("VSA SYSTEMS - RECEPTION HOUSEKEEPING MANAGEMENT STATUS PANEL");

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 940, 490);
        panel.setBackground(new Color(10, 25, 50));
        panel.setLayout(null);
        add(panel);

        JLabel label1 = new JLabel("INVENTORY UNIT HOUSEKEEPING CONSOLE");
        label1.setBounds(25, 15, 450, 25);
        label1.setFont(new Font("Tahoma", Font.BOLD, 18));
        label1.setForeground(new Color(255, 98, 0));
        panel.add(label1);

        JLabel label2 = new JLabel("Resident Active Index Mapping:");
        label2.setBounds(25, 85, 200, 20);
        label2.setForeground(Color.WHITE);
        panel.add(label2);

        c = new Choice();
        c.setBounds(240, 85, 160, 22);
        panel.add(c);

        try {
            DatabaseConnection C = new DatabaseConnection();
            ResultSet resultSet = C.statement.executeQuery("select * from customer");
            while (resultSet.next()){
                c.add(resultSet.getString("number"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        JLabel label3 = new JLabel("Unit Location Designation Code:");
        label3.setBounds(25, 130, 200, 20);
        label3.setForeground(Color.WHITE);
        panel.add(label3);

        textField3 = new JTextField();
        textField3.setBounds(240, 130, 160, 22);
        panel.add(textField3);

        JLabel label4 = new JLabel("Current Deployment Availability:");
        label4.setBounds(25, 175, 200, 20);
        label4.setForeground(Color.WHITE);
        panel.add(label4);

        textField4 = new JTextField();
        textField4.setBounds(240, 175, 160, 22);
        panel.add(textField4);

        JLabel label5 = new JLabel("Housekeeping Maintenance State:");
        label5.setBounds(25, 220, 200, 20);
        label5.setForeground(Color.WHITE);
        panel.add(label5);

        textField5 = new JTextField();
        textField5.setBounds(240, 220, 160, 22);
        panel.add(textField5);

        check = new JButton("Fetch Status");
        check.setBounds(40, 330, 160, 32);
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        panel.add(check);

        update = new JButton("Commit State");
        update.setBounds(220, 330, 160, 32);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        panel.add(update);

        back = new JButton("Close Panel");
        back.setBounds(130, 385, 160, 32);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        panel.add(back);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/update.png"));
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(500, 60, 300, 300);
        panel.add(label);

        check.addActionListener(e -> executeStatusDiagnosticAudit());
        update.addActionListener(e -> commitMaintenanceStatusChange());
        back.addActionListener(e -> setVisible(false));

        setLayout(null);
        setSize(950, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeStatusDiagnosticAudit() {
        String profileToken = c.getSelectedItem();
        if(profileToken == null) return;
        try {
            DatabaseConnection db = new DatabaseConnection();
            ResultSet rs = db.statement.executeQuery("select * from customer where number = '" + profileToken + "'");
            if (rs.next()) {
                textField3.setText(rs.getString("room"));
            }

            ResultSet rsRoom = db.statement.executeQuery("select * from room where roomnumber = '" + textField3.getText() + "'");
            if (rsRoom.next()) {
                textField4.setText(rsRoom.getString("availability"));
                textField5.setText(rsRoom.getString("cleaning_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void commitMaintenanceStatusChange() {
        try {
            DatabaseConnection db = new DatabaseConnection();
            String cleanStatusValue = textField5.getText().trim();
            String targetRoomNum = textField3.getText().trim();

            db.statement.executeUpdate("update room set cleaning_status = '" + cleanStatusValue + "' where roomnumber = '" + targetRoomNum + "'");
            JOptionPane.showMessageDialog(null, "Inventory Housekeeping Metric Successfully Overwritten.");
            setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}