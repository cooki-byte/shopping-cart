package cop4331.client;

/**
 * Represents a seller in the system, managing an inventory of products.
 * Inherits from the {@link User} class.
 */
public class Seller extends User {
    /** The inventory managed by the seller. */
    private Inventory inventory;

    /**
     * Default constructor that initializes the seller with an empty inventory.
     */
    public Seller() {
        this.inventory = new Inventory();
    }

    /**
     * Constructs a seller with the specified ID, username, and password, 
     * and initializes an empty inventory.
     *
     * @param id the unique identifier for the seller.
     * @param username the seller's username.
     * @param password the seller's password.
     */
    public Seller(String id, String username, String password) {
        super(id, username, password);
        this.inventory = new Inventory();
    }

    /**
     * Constructs a seller with the specified ID, username, password, and inventory.
     *
     * @param id the unique identifier for the seller.
     * @param username the seller's username.
     * @param password the seller's password.
     * @param inventory the inventory managed by the seller.
     */
    public Seller(String id, String username, String password, Inventory inventory) {
        super(id, username, password);
        this.inventory = inventory;
    }

    /**
     * Retrieves the seller's inventory.
     *
     * @return the inventory managed by the seller.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the seller's inventory.
     *
     * @param inventory the inventory to set.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a product to the seller's inventory and notifies observers of the change.
     *
     * @param product the product to add to the inventory.
     */
    public void addProduct(Product product) {
        inventory.addProduct(product);
        Database.getInstance().addProduct(product);
        System.out.println("Product added to inventory: " + product.getName());
    }

    /**
     * Updates the inventory and notifies all observers of any changes.
     */
    public void updateInventory() {
        inventory.notifyObservers();
        System.out.println("Inventory updated.");
    }

    /**
     * Calculates and displays the total financial value of the inventory.
     * The total value is computed as the sum of (price * quantity) for all products.
     */
    public void viewFinancialData() {
        double totalValue = inventory.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
        System.out.println("Total inventory value: $" + totalValue);
    }

    /**
     * Logs out the seller from the system and displays a logout message.
     */
    @Override
    public void logout() {
        System.out.println("Seller " + username + " logged out.");
    }
}
