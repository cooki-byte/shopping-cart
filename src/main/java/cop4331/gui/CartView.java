package cop4331.gui;

import cop4331.client.Cart;
import cop4331.client.Customer;
import cop4331.client.Database;
import cop4331.client.LineItem;
import cop4331.client.Product;
import cop4331.gui.CheckoutView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CartView extends JFrame {
    private Customer customer;
    private Cart cart;

    private JTable cartTable;
    private CartTableModel cartTableModel;

    private JLabel totalLabel;

    private JButton updateCartButton;
    private JButton checkoutButton;

    public CartView(Customer customer) {
        this.customer = customer;
        this.cart = customer.getCart();

        setTitle("Your Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        layoutComponents();
        registerListeners();

        updateTotal();
    }

    private void initializeComponents() {
        // Initialize the cart table model and table
        cartTableModel = new CartTableModel(cart);
        cartTable = new JTable(cartTableModel);

        // Allow editing of the Quantity column
        cartTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));

        // Set the Remove column to display buttons
        cartTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        totalLabel = new JLabel("Total: $0.00");

        updateCartButton = new JButton("Update Cart");
        checkoutButton = new JButton("Proceed to Checkout");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Cart items table
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel with total and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Total label
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateCartButton);
        buttonPanel.add(checkoutButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void registerListeners() {
        updateCartButton.addActionListener(e -> updateCart());
        checkoutButton.addActionListener(e -> proceedToCheckout());
    }

    private void updateCart() {
        // Stop cell editing to ensure changes are committed
        if (cartTable.isEditing()) {
            cartTable.getCellEditor().stopCellEditing();
        }

        // Apply changes to quantities
        cartTableModel.applyChanges();

        // Update the total
        updateTotal();

        // Save the updated cart to the database
        Database.getInstance().updateUser(customer);
    }

    private void updateTotal() {
        totalLabel.setText("Total: $" + String.format("%.2f", cart.getTotal()));
    }

    private void proceedToCheckout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open the CheckoutView
        CheckoutView checkoutView = new CheckoutView(this, customer);
        checkoutView.setVisible(true);

        // After checkout, refresh the cart view
        cartTableModel.fireTableDataChanged();
        updateTotal();

        // Close CartView if the cart is empty after checkout
        if (cart.isEmpty()) {
            dispose();
        }
    }

    // Inner class for the cart table model
    private class CartTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Product Name", "Price per Unit", "Quantity", "Total Price", "Remove"};
        private Cart cart;

        public CartTableModel(Cart cart) {
            this.cart = cart;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            return cart.getItems().size();
        }

        @Override
        public Object getValueAt(int row, int col) {
            LineItem item = cart.getItems().get(row);
            Product product = item.getProduct();
            switch (col) {
                case 0:
                    return product.getName();
                case 1:
                    return "$" + String.format("%.2f", product.getPrice());
                case 2:
                    return item.getQuantity();
                case 3:
                    return "$" + String.format("%.2f", product.getPrice() * item.getQuantity());
                case 4:
                    return "Remove";
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            // Allow editing of Quantity column and Remove button
            return col == 2 || col == 4;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            LineItem item = cart.getItems().get(row);
            if (col == 2) {
                try {
                    int newQuantity = Integer.parseInt(value.toString());
                    if (newQuantity > 0) {
                        cart.updateItemQuantity(item.getProduct(), newQuantity);
                        fireTableCellUpdated(row, col);
                        fireTableCellUpdated(row, 3); // Update the total price column
                        updateTotal();
                    } else {
                        JOptionPane.showMessageDialog(CartView.this, "Quantity must be greater than 0.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CartView.this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                Database.getInstance().updateUser(customer); // Save changes
            } else if (col == 4) {
                // Remove item
                cart.removeItem(item.getProduct());
                fireTableDataChanged();
                updateTotal();
                Database.getInstance().updateUser(customer); // Save changes
            }
        }

        public void applyChanges() {
            // Recalculate the total in the cart
            cart.calculateTotal();
            // Refresh the table
            fireTableDataChanged();
        }
    }

    // Renderer and editor for the Remove button in the table
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setText("Remove");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private LineItem item;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Remove");
            button.addActionListener(e -> {
                fireEditingStopped();
                cart.removeItem(item.getProduct());
                cartTableModel.fireTableDataChanged();
                updateTotal();
                Database.getInstance().updateUser(customer); // Save changes
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.item = cart.getItems().get(row); // Capture the LineItem before any changes
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Remove";
        }
    }
}
