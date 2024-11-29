package cop4331.client;

/**
 * Represents a seller user in the system.
 * Sellers can manage inventory and view financial data.
 */
public class Seller extends User{
    private Inventory inventory;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public Seller() {
        this.inventory = new Inventory();
    }

    /**
     * Constructs a Seller with specified id, username, and password.
     * Assigns the provided Inventory to the seller.
     *
     * @param id the seller ID
     * @param username the username
     * @param password the password
     * @param inventory the inventory managed by the seller
     */
    public Seller(String id, String username, String password, Inventory inventory) {
        super(id, username, password);
        this.inventory = inventory;
    }

    /**
     * Authenticates the seller with the provided password.
     *
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }

    /**
     * Logs out the seller from the system.
     */
    @Override
    public void logout() {
        // Perform logout operations
    }

    // Getters and Setters

    /**
     * Returns the seller's inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the seller's inventory.
     *
     * @param inventory the inventory to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
