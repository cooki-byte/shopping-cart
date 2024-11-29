package cop4331.client;

/**
 * Represents a customer user in the system.
 * Customers can add products to their cart and make purchases.
 */
public class Customer extends User{
    private Cart cart;
    
    /**
     * Default constructor for Jackson deserialization.
     */
    public Customer() {
        this.cart = new Cart();
    }

    /**
     * Constructs a Customer with specified id, username, and password.
     * Initializes a new Cart for the customer.
     *
     * @param id the customer ID
     * @param username the username
     * @param password the password
     */
    public Customer(String id, String username, String password) {
        super(id, username, password);
        this.cart = new Cart();
    }

    /**
     * Authenticates the customer with the provided password.
     *
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    /**
     * Logs out the customer from the system.
     */
    @Override
    public void logout() {
        // Perform logout operations
    }

    // Getters and Setters

    /**
     * Returns the customer's cart.
     *
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets the customer's cart.
     *
     * @param cart the cart to set
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
