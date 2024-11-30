package cop4331.client;

import java.util.List;

/**
 * Represents a seller in the system, managing an inventory of products.
 * Inherits from the {@link User} class.
 */
public class Seller extends User {
    private Inventory inventory;
    private FinancialData financialData;

    /**
     * Default constructor that initializes the seller with an empty inventory.
     */
    public Seller() {
        this.inventory = new Inventory();
        this.financialData = new FinancialData();
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
        this.financialData = new FinancialData();
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
        this.financialData = new FinancialData();
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
        product.setSeller(this); // Set the seller reference
        inventory.addProduct(product);
        // Update financial data with the cost of the product
        financialData.updateData(0, product.getPrice() * product.getQuantity());
        System.out.println("Product added to inventory: " + product.getName());
    }

    /**
     * Records a sale of a product and updates the financial data.
     *
     * @param product the product sold
     * @param quantity the quantity sold
     */
    public void recordSale(Product product, int quantity) {
        double saleAmount = product.getPrice() * quantity;
        financialData.updateData(saleAmount, 0); // Update revenue
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
     * Gets the seller's financial data.
     * 
     * @return the seller's financial data
     */
    public FinancialData getFinancialData() {
        return financialData;
    }

    /**
     * Logs out the seller from the system and displays a logout message.
     */
    @Override
    public void logout() {
        System.out.println("Seller logged out.");
    }
}
