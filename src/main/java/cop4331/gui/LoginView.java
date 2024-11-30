package cop4331.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GUI class representing the login view of the application.
 * Provides fields for entering username and password, along with login and sign-up buttons.
 */
public class LoginView extends JFrame {
    /** Text field for entering the username. */
    private JTextField usernameField;

    /** Password field for entering the password. */
    private JPasswordField passwordField;

    /** Button for initiating the login process. */
    private JButton loginButton;

    /** Button for navigating to the sign-up view. */
    private JButton signupButton;

    /**
     * Constructs the login view and initializes its components.
     */
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

    /**
     * Retrieves the entered username.
     *
     * @return the username entered in the text field.
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Retrieves the entered password.
     *
     * @return the password entered in the password field.
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    /**
     * Retrieves the login button.
     *
     * @return the login button.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Retrieves the sign-up button.
     *
     * @return the sign-up button.
     */
    public JButton getSignupButton() {
        return signupButton;
    }

    /**
     * Displays an error message in a dialog box.
     *
     * @param message the error message to display.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
