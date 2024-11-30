package cop4331.client;

import java.util.List;

public class Customer extends User {
    private Cart cart;

    // Default constructor
    public Customer() {
        this.cart = new Cart(); // Initialize with an empty cart
    }

    // Parameterized constructor
    public Customer(String id, String username, String password) {
        super(id, username, password);
        this.cart = new Cart(); // Initialize with an empty cart
    }

    public Customer(String id, String username, String password, Cart cart) {
        super(id, username, password);
        this.cart = cart;
    }

    // Get the customer's cart
    public Cart getCart() {
        return cart;
    }

    // Set the customer's cart
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    // View all available products
    public void viewProducts() {
        Database db = Database.getInstance(); // Access the database
        List<Product> products = db.getProducts();

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

    // Add a product to the cart
    public void addToCart(Product product, int qty) {
        if (product.getQuantity() < qty) {
            System.out.println("Insufficient stock for " + product.getName());
        } else {
            cart.addItem(product, qty); // Add item to the cart
            product.setQuantity(product.getQuantity() - qty); // Decrease product stock
            System.out.println(qty + " x " + product.getName() + " added to cart.");
        }
    }

    @Override
    public void logout() {
        System.out.println("Customer " + username + " logged out.");
    }
}
