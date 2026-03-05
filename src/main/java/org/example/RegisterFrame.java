package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Register");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> register());
        panel.add(registerBtn);

        JButton backBtn = new JButton("Back to Login");
        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        panel.add(backBtn);

        add(panel);
        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String password = Utils.hashPassword(new String(passwordField.getPassword()));

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users(username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginFrame();
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}