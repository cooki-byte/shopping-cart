package cop4331.gui;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class representing the seller view.
 * Displays a simple message to confirm the view is loaded.
 */
public class SellerView extends JFrame{
    private JLabel messageLabel;

    /**
     * Constructs the SellerView.
     * Initializes the GUI components.
     *
     * @param seller the Seller object associated with this view
     */
    public SellerView(cop4331.client.Seller seller) {
        setTitle("Seller View");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setLocationRelativeTo(null); // Centers the window on the screen
    }

    /**
     * Initializes the GUI components and layout.
     */
    private void initializeComponents() {
        messageLabel = new JLabel("This is Seller View", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(messageLabel);
    }

    // Add other methods and components
}
