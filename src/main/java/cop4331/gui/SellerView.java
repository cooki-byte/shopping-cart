package cop4331.gui;

import cop4331.client.Product;
import cop4331.client.Seller;
import cop4331.client.FinancialData;
import cop4331.client.Database;
import cop4331.client.DiscountedProduct;
import cop4331.client.ProductBundle;


import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * GUI class representing the seller view in the system.
 * Provides functionality for sellers to view and manage their inventory,
 * add new products, and access financial data.
 */
public class SellerView extends JFrame {
    private Seller seller;
    private JTextArea inventoryDisplay;
    private JTextField productNameField;
    private JTextField productDescriptionField; 
    private JTextField productPriceField;
    private JTextField productQuantityField;
    private JTextField productInvoicePriceField;
    private JButton addProductButton;
    private JButton viewFinancialButton;
    private JButton createBundleButton;
    private JButton applyDiscountButton;

    /**
     * Constructs the seller view with the specified seller.
     *
     * @param seller the seller using this view.
     */
    public SellerView(Seller seller) {
        this.seller = seller;
        setTitle("Seller View - " + seller.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inventory Display
        inventoryDisplay = new JTextArea();
        inventoryDisplay.setEditable(false);
        JScrollPane inventoryScroll = new JScrollPane(inventoryDisplay);
        inventoryScroll.setBorder(BorderFactory.createTitledBorder("Your Inventory"));
        add(inventoryScroll, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Changed to 5 rows
        bottomPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        bottomPanel.add(productNameField);

        bottomPanel.add(new JLabel("Description:")); // Added description label
        productDescriptionField = new JTextField();   // Added description field
        bottomPanel.add(productDescriptionField);

        bottomPanel.add(new JLabel("Price:"));
        productPriceField = new JTextField();
        bottomPanel.add(productPriceField);

        bottomPanel.add(new JLabel("Quantity:"));
        productQuantityField = new JTextField();
        bottomPanel.add(productQuantityField);

        bottomPanel.add(new JLabel("Invoice Price:")); 
        productInvoicePriceField = new JTextField();  
        bottomPanel.add(productInvoicePriceField);

        addProductButton = new JButton("Add Product");
        viewFinancialButton = new JButton("View Financial Data");
        bottomPanel.add(addProductButton);
        bottomPanel.add(viewFinancialButton);

        createBundleButton = new JButton("Create Bundle");
        applyDiscountButton = new JButton("Apply Discount");
        bottomPanel.add(createBundleButton);
        bottomPanel.add(applyDiscountButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        // Add action listener to addProductButton
        addProductButton.addActionListener(e -> {
            try {
                String name = getProductName();
                String description = getProductDescription(); 
                double price = getProductPrice();
                int quantity = getProductQuantity();
                double invoicePrice = getProductInvoicePrice();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (invoicePrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Invoice price must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String productId = String.valueOf(System.currentTimeMillis());
                Product product = new Product(productId, name, description, price, quantity, seller.getId(), invoicePrice, "product");
                seller.addProduct(product);

                // Save the updated data to products.json
                Database.getInstance().saveData();

                updateInventoryDisplay();

                productNameField.setText("");
                productDescriptionField.setText(""); 
                productPriceField.setText("");
                productQuantityField.setText("");
                productInvoicePriceField.setText("");

                JOptionPane.showMessageDialog(this, "Product added successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please ensure price and quantity are numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add action listener to viewFinancialButton
        viewFinancialButton.addActionListener(e -> {
            FinancialData financialData = seller.getFinancialData();
            String message = String.format("Costs: $%.2f\nRevenues: $%.2f\nProfits: $%.2f",
                    financialData.getCosts(), financialData.getRevenues(), financialData.getProfits());
            JOptionPane.showMessageDialog(this, message, "Financial Data", JOptionPane.INFORMATION_MESSAGE);
        });

        // Action listeners for product bundle and discount buttons
        createBundleButton.addActionListener(e -> createBundle());
        applyDiscountButton.addActionListener(e -> applyDiscount());

        // Initial inventory display
        updateInventoryDisplay();
    }

    /**
     * Updates the inventory display to reflect the current products in the seller's inventory.
     */
    public void updateInventoryDisplay() {
        List<Product> products = seller.getInventory().getProducts();
        inventoryDisplay.setText("");
        for (Product product : products) {
            inventoryDisplay.append("Name: " + product.getName() + "\n");
            inventoryDisplay.append("Description: " + product.getDescription() + "\n");
            inventoryDisplay.append("Listed Price: $" + product.getPrice() + "\n");
            inventoryDisplay.append("Invoice Price: $" + product.getInvoicePrice() + "\n");
            inventoryDisplay.append("Stock: " + product.getQuantity() + "\n");
            inventoryDisplay.append("-----------------------------------\n");
        }
    }

    /**
     * Creates a product bundle by selecting multiple products from the inventory.
     */
    private void createBundle() {
        // Get the list of products from the seller's inventory
        List<Product> products = seller.getInventory().getProducts();

        // Check if there are enough products to create a bundle
        if (products.size() < 2) {
            JOptionPane.showMessageDialog(this, "You need at least two products to create a bundle.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a list of product names for display
        String[] productNames = products.stream()
                .map(product -> product.getId() + ": " + product.getName())
                .toArray(String[]::new);

        // Show a multiple selection dialog to select products for the bundle
        JList<String> productList = new JList<>(productNames);
        productList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Select Products for Bundle", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            List<String> selectedValues = productList.getSelectedValuesList();
            if (selectedValues.size() < 2) {
                JOptionPane.showMessageDialog(this, "Please select at least two products for the bundle.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ask for the bundle name
            String bundleName = JOptionPane.showInputDialog(this, "Enter Bundle Name:");
            if (bundleName == null || bundleName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bundle name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ask for the bundle description
            String bundleDescription = JOptionPane.showInputDialog(this, "Enter Bundle Name:");
            if (bundleDescription == null || bundleDescription.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bundle description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the ProductBundle
            String bundleId = "bundle-" + System.currentTimeMillis();
            ProductBundle bundle = new ProductBundle(bundleId, bundleName, bundleDescription);

            // Add selected products to the bundle
            for (String value : selectedValues) {
                String productId = value.split(":")[0];
                Product product = products.stream()
                        .filter(p -> p.getId().equals(productId))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    bundle.addProduct(product);
                }
            }

            // Add the bundle to the seller's inventory and the database
            seller.addProduct(bundle);
            updateInventoryDisplay();

            JOptionPane.showMessageDialog(this, "Bundle created successfully!");
        }
    }

    /**
     * Applies a discount to a selected product.
     */
    private void applyDiscount() {
        // Get the list of products from the seller's inventory
        List<Product> products = seller.getInventory().getProducts();

        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products available to apply discount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a list of product names for display
        String[] productNames = products.stream()
                .map(product -> product.getId() + ": " + product.getName())
                .toArray(String[]::new);

        // Show a selection dialog to select a product
        String selectedProduct = (String) JOptionPane.showInputDialog(this, "Select a Product:", "Apply Discount",
                JOptionPane.PLAIN_MESSAGE, null, productNames, productNames[0]);

        if (selectedProduct != null) {
            String productId = selectedProduct.split(":")[0];
            Product product = products.stream()
                    .filter(p -> p.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (product != null) {
                // Ask for the discount rate
                String discountRateStr = JOptionPane.showInputDialog(this, "Enter Discount Rate (e.g., 0.10 for 10%):");
                try {
                    double discountRate = Double.parseDouble(discountRateStr);
                    if (discountRate <= 0 || discountRate >= 1) {
                        JOptionPane.showMessageDialog(this, "Discount rate must be between 0 and 1.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create a DiscountedProduct and replace the original product in the inventory
                    DiscountedProduct discountedProduct = new DiscountedProduct(product, discountRate);
                    seller.getInventory().removeProduct(product);
                    seller.getInventory().addProduct(discountedProduct);
                    Database.getInstance().saveData();

                    updateInventoryDisplay();

                    JOptionPane.showMessageDialog(this, "Discount applied successfully!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid discount rate.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Retrieves the product name entered by the seller.
     *
     * @return the entered product name.
     */
    public String getProductName() {
        return productNameField.getText();
    }

    /**
     * Retrieves the product description entered by the seller.
     *
     * @return the entered product description.
     */
    public String getProductDescription() {
        return productDescriptionField.getText();
    }

    /**
     * Retrieves the product price entered by the seller.
     *
     * @return the entered product price.
     * @throws NumberFormatException if the entered price is not a valid double.
     */
    public double getProductPrice() {
        return Double.parseDouble(productPriceField.getText());
    }

    /**
     * Retrieves the product quantity entered by the seller.
     *
     * @return the entered product quantity.
     * @throws NumberFormatException if the entered quantity is not a valid integer.
     */
    public int getProductQuantity() {
        return Integer.parseInt(productQuantityField.getText());
    }

    /**
     * Retrieves the product invoice price entered by the seller.
     *
     * @return the entered product invoice price.
     * @throws NumberFormatException if the entered invoice price is not a valid double.
     */
    public double getProductInvoicePrice() {
        return Double.parseDouble(productInvoicePriceField.getText());
    }
}
