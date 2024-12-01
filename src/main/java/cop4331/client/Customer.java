package cop4331.client;

import java.util.List;

/**
 * The Customer class represents a customer user in the system.
 * It extends the User class and includes a shopping cart.
 */
public class Customer extends User {
    private Cart cart;

    /**
     * Default constructor.
     * Initializes the customer with an empty cart.
     */
    public Customer() {
        this.cart = new Cart(); // Initialize with an empty cart
    }

    /**
     * Parameterized constructor.
     * Initializes the customer with the specified id, username, and password, and an empty cart.
     * 
     * @param id the customer's id
     * @param username the customer's username
     * @param password the customer's password
     */
    public Customer(String id, String username, String password) {
        super(id, username, password, "customer");
        this.cart = new Cart(); // Initialize with an empty cart
    }

    /**
     * Parameterized constructor.
     * Initializes the customer with the specified id, username, password, and cart.
     * 
     * @param id the customer's id
     * @param username the customer's username
     * @param password the customer's password
     * @param cart the customer's cart
     */
    public Customer(String id, String username, String password, Cart cart) {
        super(id, username, password, "customer");
        this.cart = cart;
    }

    /**
     * Gets the customer's cart.
     * 
     * @return the customer's cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets the customer's cart.
     * 
     * @param cart the new cart
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Displays all available products.
     * 
     * @param products the list of products to display
     */
    public void viewProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Available Products:");
            for (Product product : products) {
                System.out.println("- " + product.getName() + ": " + product.getDescription() +
                        " | Price: $" + product.getPrice() + " | Quantity: " + product.getQuantity());
            }
        }
    }

    /**
     * Adds a product to the cart.
     * 
     * @param product the product to add
     * @param qty the quantity of the product
     */
    public void addToCart(Product product, int qty) {
        if (product.getQuantity() < qty) {
            System.out.println("Insufficient stock for " + product.getName());
        } else {
            cart.addItem(product, qty); // Add item to the cart
            product.setQuantity(product.getQuantity() - qty); // Decrease product stock

            // Update the database with the changes
            Database.getInstance().updateProduct(product); // Save updated product to products.json
            Database.getInstance().updateUser(this);       // Save updated customer to users.json

            System.out.println(qty + " x " + product.getName() + " added to cart.");
        }
    }

        /**
     * Completes the checkout process by clearing the cart and updating product inventory.
     */
    public void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty. Add items before checking out.");
            return;
        }

        for (LineItem item : cart.getItems()) {
            Product product = item.getProduct();
            int purchasedQty = item.getQuantity();

            // Update the product inventory
            product.setQuantity(product.getQuantity() - purchasedQty);
            Database.getInstance().updateProduct(product); // Save product changes to products.json
        }

        // Clear the cart
        cart.clearCart(); // Properly clears the cart
        Database.getInstance().updateUser(this); // Save the cleared cart to users.json

        System.out.println("Checkout completed successfully. Thank you for your purchase!");
    }

    /**
     * Logs out the customer.
     */
    @Override
    public void logout() {
        System.out.println("Customer " + username + " logged out.");
    }
}
