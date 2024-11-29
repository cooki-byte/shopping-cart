package cop4331.client;

import cop4331.gui.*;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Entry point of the application.
 * Initializes and starts the shopping cart application.
 */
public class SystemApp {

    /**
     * Main method to start the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SystemApp app = new SystemApp();
        app.initialize();
        app.startApplication();
    }

    /**
     * Initializes the application.
     * Loads data and performs any necessary setup.
     */
    private void initialize() {
        // Initialize the database and load data
        Database db = Database.getInstance();

    }

    /**
     * Starts the application by showing the login view and handling navigation.
     */
    private void startApplication() {
        // Ensure GUI creation is done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Show login view
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginView.setVisible(true);

                // Wait for the login view to be disposed
                loginView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        User authenticatedUser = loginController.getAuthenticatedUser();
                        if (authenticatedUser != null) {
                            openUserView(authenticatedUser);
                        } else {
                            // Exit the application if authentication failed or window was closed
                            System.exit(0);
                        }
                    }
                });
            }
        });
    }

    /**
     * Opens the appropriate view based on the user type.
     *
     * @param user the authenticated User object
     */
    private void openUserView(User user) {
        if (user instanceof Customer) {
            // Open Customer View
            CustomerView customerView = new CustomerView((Customer) user);
            customerView.setVisible(true);
        } else if (user instanceof Seller) {
            // Open Seller View
            SellerView sellerView = new SellerView((Seller) user);
            sellerView.setVisible(true);
        } else {
            // Handle other user types if necessary
            JOptionPane.showMessageDialog(null, "Unknown user type.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
