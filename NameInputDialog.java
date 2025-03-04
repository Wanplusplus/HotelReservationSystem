import javax.swing.*;
import java.awt.*;

public class NameInputDialog {
    public static String[] showDialog(JFrame parent) {
        // Create a dialog for login
        JDialog dialog = new JDialog(parent, "Login", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        dialog.add(usernameField, gbc);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        dialog.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Check credentials
            if ("eulaka".equals(username) && "admin321321".equals(password)) {
                dialog.dispose(); // Close the dialog if authentication is successful
            } else {
                JOptionPane.showMessageDialog(dialog, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        dialog.add(loginButton, gbc);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            dialog.dispose(); // Close the dialog
        });
        gbc.gridy = 3;
        dialog.add(cancelButton, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        // Return the username and password if authentication is successful
        return new String[]{usernameField.getText().trim(), new String(passwordField.getPassword()).trim()};
    }
}