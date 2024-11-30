package cop4331.gui;

import cop4331.client.Customer;
import cop4331.client.Database;
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
    private JButton cartIcon;

    public CustomerView(Customer customer) {
        this.customer = customer;
        setTitle("Customer View - " + customer.getUsername());
        setSize(800, 600);
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

        // Add cart icon to the top right
        JPanel topPanel = new JPanel(new BorderLayout());
        cartIcon = new JButton("Cart (0)");
        topPanel.add(cartIcon, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);

        // Load products into the display
        updateProductDisplay(Database.getInstance().getProducts());

        // Add listeners
        addToCartButton.addActionListener(e -> {
            try {
                String productId = getProductId();
                int qty = getQuantity();

                // Find the product in the database
                Product product = Database.getInstance().getProducts().stream()
                        .filter(p -> p.getId().equals(productId))
                        .findFirst()
                        .orElse(null);

                if (product == null) {
                    JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    customer.addToCart(product, qty);
                    updateCartDisplay(); // Update cart display
                    updateCartIcon();    // Update cart icon
                    JOptionPane.showMessageDialog(this, qty + " x " + product.getName() + " added to cart!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        checkoutButton.addActionListener(e -> {
            if (customer.getCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = JOptionPane.showConfirmDialog(this,
                    "Do you want to proceed to checkout?",
                    "Checkout",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                customer.getCart().getItems().clear(); // Clear the cart
                updateCartDisplay(); // Refresh the cart display
                updateCartIcon();    // Reset the cart icon
                JOptionPane.showMessageDialog(this, "Thank you for your purchase!");
            }
        });
    }

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

    public void updateCartIcon() {
        int itemCount = customer.getCart().getItems().stream()
                .mapToInt(item -> item.getQuantity())
                .sum();
        cartIcon.setText("Cart (" + itemCount + ")");
    }

    public String getProductId() {
        return productIdField.getText();
    }

    public int getQuantity() {
        return Integer.parseInt(quantityField.getText());
    }
}
