package cop4331.gui;

import cop4331.client.Customer;
import cop4331.client.Database;
import cop4331.client.Product;
import cop4331.client.Cart;
import cop4331.client.Observer;
import cop4331.client.LineItem;

import cop4331.gui.CheckoutView; // Import CheckoutView

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GUI class representing the customer view in the system.
 * Provides functionality for customers to browse products, add items to their cart,
 * view their cart, and proceed to checkout.
 */
public class CustomerView extends JFrame implements Observer<Cart> {
    /** The customer using this view. */
    private Customer customer;

    /** Text area for displaying available products. */
    private JTextArea productDisplay;

    /** Text area for displaying items in the customer's cart. */
    private JTextArea cartDisplay;

    /** Text field for entering the ID of the product to add to the cart. */
    private JTextField productIdField;

    /** Text field for entering the quantity of the product to add to the cart. */
    private JTextField quantityField;

    /** Button for adding a product to the cart. */
    private JButton addToCartButton;

    /** Button for proceeding to checkout. */
    private JButton checkoutButton;

    /** Button displaying the cart icon with the total number of items in the cart. */
    private JButton cartIcon;

    /**
     * Constructs the customer view with the specified customer.
     *
     * @param customer the customer using this view.
     */
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

        // Register as an observer of the cart
        customer.getCart().addObserver(this);

        // Add ActionListener to cartIcon
        cartIcon.addActionListener(e -> {
            CartView cartView = new CartView(customer);
            cartView.setVisible(true);
        });

        setLocationRelativeTo(null);

        // Load products into the display
        updateProductDisplay(Database.getInstance().getProducts());

        // Initial update of cart display and icon
        updateCartDisplay();
        updateCartIcon();

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
                } else if (product.getQuantity() < qty) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for " + product.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    customer.addToCart(product, qty);
                    // No need to manually update cart display and icon here since the observer pattern handles it
                    JOptionPane.showMessageDialog(this, qty + " x " + product.getName() + " added to cart!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        checkoutButton.addActionListener(e -> {
            if (customer.getCart().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Open the CheckoutView
            CheckoutView checkoutView = new CheckoutView(this, customer);
            checkoutView.setVisible(true);

            // After checkout, refresh the UI
            updateCartDisplay();
            updateCartIcon();
        });
    }

    /**
     * Updates the product display with the available products.
     *
     * @param products the list of products to display.
     */
    public void updateProductDisplay(List<Product> products) {
        productDisplay.setText("");
        for (Product product : products) {
            productDisplay.append(product.getId() + ": " + product.getName() +
                    " - $" + product.getPrice() + " (Stock: " + product.getQuantity() + ")\n");
        }
    }

    /**
     * Updates the cart display with the items currently in the customer's cart.
     */
    public void updateCartDisplay() {
        cartDisplay.setText("");
        customer.getCart().getItems().forEach(item -> cartDisplay.append(
                item.getProduct().getName() + " x " + item.getQuantity() + "\n"));
    }

    /**
     * Updates the cart icon to display the total number of items in the customer's cart.
     */
    public void updateCartIcon() {
        int itemCount = customer.getCart().getItems().stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
        cartIcon.setText("Cart (" + itemCount + ")");
    }

    /**
     * Retrieves the product ID entered by the customer.
     *
     * @return the entered product ID.
     */
    public String getProductId() {
        return productIdField.getText();
    }

    /**
     * Retrieves the quantity of the product entered by the customer.
     *
     * @return the entered quantity.
     * @throws NumberFormatException if the entered quantity is not a valid integer.
     */
    public int getQuantity() {
        return Integer.parseInt(quantityField.getText());
    }

    /**
     * Called when the cart is updated.
     *
     * @param cart the cart that was updated.
     */
    @Override
    public void update(Cart cart) {
        SwingUtilities.invokeLater(() -> {
            updateCartDisplay();
            updateCartIcon();
            updateProductDisplay(Database.getInstance().getProducts()); // Refresh product display
        });
    }
}
