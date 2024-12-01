package cop4331.gui;

import cop4331.client.Customer;
import cop4331.client.Cart;
import cop4331.client.Payment;

import javax.swing.*;
import java.awt.*;

/**
 * The CheckoutView class provides a window where customers can input credit card information to complete the checkout process.
 */
public class CheckoutView extends JDialog {
    private Customer customer;
    private Cart cart;

    private JTextField cardNumberField;
    private JTextField expirationField;
    private JTextField cvvField;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel totalLabel;

    public CheckoutView(JFrame parent, Customer customer) {
        super(parent, "Checkout", true);
        this.customer = customer;
        this.cart = customer.getCart();

        initializeComponents();
        layoutComponents();
        registerListeners();

        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        cardNumberField = new JTextField(16);
        expirationField = new JTextField(5);
        cvvField = new JTextField(3);
        submitButton = new JButton("Submit Payment");
        cancelButton = new JButton("Cancel");
        totalLabel = new JLabel("Total: $" + String.format("%.2f", cart.getTotal()));
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Card Number:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cardNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Expiration (MM/YY):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(expirationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("CVV:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cvvField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel(""), gbc); // Spacer

        gbc.gridx = 1;
        mainPanel.add(totalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);

        setContentPane(mainPanel);
    }

    private void registerListeners() {
        submitButton.addActionListener(e -> processPayment());
        cancelButton.addActionListener(e -> dispose());
    }

    private void processPayment() {
        String cardNumber = cardNumberField.getText().trim();
        String expiration = expirationField.getText().trim();
        String cvv = cvvField.getText().trim();
        double amount = cart.getTotal();

        // Basic validation
        if (cardNumber.isEmpty() || expiration.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cardNumber.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "Invalid card number. Must be 16 digits.", "Invalid Card Number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!expiration.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Invalid expiration date. Format MM/YY.", "Invalid Expiration Date", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cvv.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid CVV. Must be 3 digits.", "Invalid CVV", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulate payment processing
        Payment payment = new Payment();
        boolean paymentSuccess = payment.processPayment(cardNumber, expiration, cvv, amount);
        if (paymentSuccess) {
            // Clear the cart
            customer.checkout();

            // Inform the user
            JOptionPane.showMessageDialog(this, "Payment successful! Thank you for your purchase.");

            // Close the checkout window
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
