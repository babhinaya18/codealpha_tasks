package Hotel.Management.System;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {
    Splash() {
        super("VSA LUXURY HOTELS - WELCOME");

        // Main structural background setup
        JPanel mainContainer = new JPanel(null);
        mainContainer.setBounds(0, 0, 858, 680);
        mainContainer.setBackground(new Color(10, 25, 50));
        add(mainContainer);

        // Elegant Title HUD Layer overlaying the GIF presentation workspace
        JLabel lblWelcome = new JLabel("VSA LUXURY RESORT SYSTEM", JLabel.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(255, 98, 0));
        lblWelcome.setBounds(50, 40, 758, 40);
        mainContainer.add(lblWelcome);

        JLabel lblSub = new JLabel("Experiencing Perfection Since 2026", JLabel.CENTER);
        lblSub.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblSub.setForeground(Color.LIGHT_GRAY);
        lblSub.setBounds(50, 85, 758, 20);
        mainContainer.add(lblSub);

        // Scaled loading animation workspace module
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icon/splash.gif"));
        Image img = icon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel label = new JLabel(scaledIcon);
        label.setBounds(129, 130, 600, 400);
        mainContainer.add(label);

        setUndecorated(true);
        setLayout(null);
        setSize(858, 680);
        setLocationRelativeTo(null); // Perfectly centered display viewport
        setVisible(true);

        try {
            Thread.sleep(15000); // 3.5 seconds clean delay
            new PortalSelectionScreen();
            setVisible(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}