// package cop4331.gui;

// import javax.swing.*;
// import java.awt.*;

// /**
//  * GUI class representing the login view.
//  * Allows users to enter their credentials for log in.
//  */
// public class LoginView extends JFrame {
//     private JTextField usernameField;
//     private JPasswordField passwordField;
//     private JButton loginButton;

//     /**
//      * Constructs the LoginView.
//      * Initializes the GUI components.
//      */
//     public LoginView() {
//         setTitle("Login");
//         setSize(300, 150);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         initializeComponents();
//         setLocationRelativeTo(null); // Centers the window on the screen
//     }

//     /**
//      * Initializes the GUI components and layout.
//      */
//     private void initializeComponents() {
//         usernameField = new JTextField(15);
//         passwordField = new JPasswordField(15);
//         loginButton = new JButton("Login");

//         JPanel panel = new JPanel();
//         panel.setLayout(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();

//         // Username Label and Field
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.anchor = GridBagConstraints.WEST;
//         panel.add(new JLabel("Username:"), gbc);

//         gbc.gridx = 1;
//         panel.add(usernameField, gbc);

//         // Password Label and Field
//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         panel.add(new JLabel("Password:"), gbc);

//         gbc.gridx = 1;
//         panel.add(passwordField, gbc);

//         // Login Button
//         gbc.gridx = 0;
//         gbc.gridy = 2;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;
//         panel.add(loginButton, gbc);

//         add(panel);
//     }

//     // Getters for fields and button

//     /**
//      * Returns the entered username.
//      *
//      * @return the username
//      */
//     public String getUsername() {
//         return usernameField.getText();
//     }

//     /**
//      * Returns the entered password.
//      *
//      * @return the password
//      */
//     public String getPassword() {
//         return new String(passwordField.getPassword());
//     }

//     /**
//      * Returns the login button.
//      *
//      * @return the login button
//      */
//     public JButton getLoginButton() {
//         return loginButton;
//     }
// }
package cop4331.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginView() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Welcome to the Shopping Cart App", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        centerPanel.add(usernameField);

        centerPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);
        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center the window
    }

    // Getters for fields and buttons
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
