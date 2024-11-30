package cop4331.gui;

import cop4331.client.Product;
import cop4331.client.Seller;
import cop4331.client.FinancialData;

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
    private JTextField productPriceField;
    private JTextField productQuantityField;
    private JButton addProductButton;
    private JButton viewFinancialButton;

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
        JPanel bottomPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        bottomPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        bottomPanel.add(productNameField);

        bottomPanel.add(new JLabel("Price:"));
        productPriceField = new JTextField();
        bottomPanel.add(productPriceField);

        bottomPanel.add(new JLabel("Quantity:"));
        productQuantityField = new JTextField();
        bottomPanel.add(productQuantityField);

        addProductButton = new JButton("Add Product");
        viewFinancialButton = new JButton("View Financial Data");
        bottomPanel.add(addProductButton);
        bottomPanel.add(viewFinancialButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        // Add action listener to addProductButton
        addProductButton.addActionListener(e -> {
            try {
                String name = getProductName();
                double price = getProductPrice();
                int quantity = getProductQuantity();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
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

                String productId = String.valueOf(System.currentTimeMillis());
                Product product = new Product(productId, name, name + " description", price, quantity, seller);
                seller.addProduct(product);
                updateInventoryDisplay();

                productNameField.setText("");
                productPriceField.setText("");
                productQuantityField.setText("");

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
    }

    /**
     * Updates the inventory display to reflect the current products in the seller's inventory.
     */
    public void updateInventoryDisplay() {
        List<Product> products = seller.getInventory().getProducts();
        inventoryDisplay.setText("");
        for (Product product : products) {
            inventoryDisplay.append(product.getName() + " - $" + product.getPrice() +
                    " (Stock: " + product.getQuantity() + ")\n");
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
}
