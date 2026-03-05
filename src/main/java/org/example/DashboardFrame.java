package org.example;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String username) {
        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JButton faceBtn = new JButton("Capture Face");
        faceBtn.addActionListener(e -> new FaceCaptureFrame());
        panel.add(faceBtn, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}