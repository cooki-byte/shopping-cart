package cop4331.gui;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class representing the customer view.
 * Displays a simple message to confirm the view is loaded.
 */
public class CustomerView extends JFrame{
    private JLabel messageLabel;

    /**
     * Constructs the CustomerView.
     * Initializes the GUI components.
     *
     * @param customer the Customer object associated with this view
     */
    public CustomerView(cop4331.client.Customer customer) {
        setTitle("Customer View");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setLocationRelativeTo(null); // Centers the window on the screen
    }

    /**
     * Initializes the GUI components and layout.
     */
    private void initializeComponents() {
        messageLabel = new JLabel("This is Customer View", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(messageLabel);
    }

    // Add other methods and components
}
