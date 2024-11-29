package cop4331.client;

import cop4331.gui.LoginView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the LoginView.
 * Handles authentication and navigation to appropriate views.
 */
public class LoginController {
    private LoginView loginView;
    private Database database;
    private User authenticatedUser;

    /**
     * Constructs a LoginController with the specified LoginView.
     *
     * @param loginView the login view
     */
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.database = Database.getInstance();
        this.authenticatedUser = null;
        initController();
    }

    /**
     * Initializes the controller by adding action listeners.
     */
    private void initController() {
        loginView.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    /**
     * Authenticates the user based on entered credentials.
     * Closes login view upon successful login.
     */
    private void authenticateUser() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        // Authenticate user
        for (User user : database.getUsers()) {
            if (user.getUsername().equals(username) && user.login(password)) {
                // Successful login
                authenticatedUser = user;
                loginView.dispose();
                return;
            }
        }

        // If authentication fails
        JOptionPane.showMessageDialog(loginView, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Returns the authenticated user after successful login.
     *
     * @return the authenticated User object, or null if not authenticated
     */
    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
