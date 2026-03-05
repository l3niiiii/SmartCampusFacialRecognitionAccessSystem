package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        // Username
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // Password
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> login());
        panel.add(loginBtn);

        // Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose(); // Close login frame
        });
        panel.add(registerBtn);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = Utils.hashPassword(new String(passwordField.getPassword()));

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new DashboardFrame(username); // Open Dashboard
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}