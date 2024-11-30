package cop4331.gui;

import cop4331.client.Customer;
import cop4331.client.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerView extends JFrame {
    private Customer customer;
    private JTextArea productDisplay;
    private JTextArea cartDisplay;
    private JTextField productIdField;
    private JTextField quantityField;
    private JButton addToCartButton;
    private JButton checkoutButton;

    public CustomerView(Customer customer) {
        this.customer = customer;
        setTitle("Customer View - " + customer.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Product Display
        productDisplay = new JTextArea();
        productDisplay.setEditable(false);
        JScrollPane productScroll = new JScrollPane(productDisplay);
        productScroll.setBorder(BorderFactory.createTitledBorder("Available Products"));
        add(productScroll, BorderLayout.WEST);

        // Cart Display
        cartDisplay = new JTextArea();
        cartDisplay.setEditable(false);
        JScrollPane cartScroll = new JScrollPane(cartDisplay);
        cartScroll.setBorder(BorderFactory.createTitledBorder("Your Cart"));
        add(cartScroll, BorderLayout.EAST);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        bottomPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        bottomPanel.add(productIdField);

        bottomPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        bottomPanel.add(quantityField);

        addToCartButton = new JButton("Add to Cart");
        checkoutButton = new JButton("Checkout");
        bottomPanel.add(addToCartButton);
        bottomPanel.add(checkoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    // Methods to update product and cart displays
    public void updateProductDisplay(List<Product> products) {
        productDisplay.setText("");
        for (Product product : products) {
            productDisplay.append(product.getId() + ": " + product.getName() +
                    " - $" + product.getPrice() + " (Stock: " + product.getQuantity() + ")\n");
        }
    }

    public void updateCartDisplay() {
        cartDisplay.setText("");
        customer.getCart().getItems().forEach(item -> cartDisplay.append(
                item.getProduct().getName() + " x " + item.getQuantity() + "\n"));
    }

    public String getProductId() {
        return productIdField.getText();
    }

    public int getQuantity() {
        return Integer.parseInt(quantityField.getText());
    }

    public JButton getAddToCartButton() {
        return addToCartButton;
    }

    public JButton getCheckoutButton() {
        return checkoutButton;
    }
}
