package cop4331.client;

import java.io.Serializable;

/**
 * Represents a seller in the system, managing an inventory of products.
 * Inherits from the {@link User} class.
 */
public class Seller extends User implements Serializable {
    private Inventory inventory;
    private FinancialData financialData;

    /**
     * Default constructor that initializes the seller with an empty inventory.
     */
    public Seller() {
        super();
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
        super(id, username, password, "seller");
        this.inventory = new Inventory();
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
     * Adds a product to the seller's inventory and updates financial data.
     *
     * @param product the product to add to the inventory.
     */
    public void addProduct(Product product) {
        product.setSellerId(this.getId()); // Set the seller reference
        inventory.addProduct(product);

        // Add the product to the global products list in the Database
        Database.getInstance().addProduct(product);

        // Update financial data with the cost of the product
        double costAmount = product.getInvoicePrice() * product.getQuantity();
        financialData.updateData(0, costAmount); // No revenue yet, only cost
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
     * Views the financial data, displaying revenues, costs, and profits.
     */
    public void viewFinancialData() {
        double revenues = financialData.getRevenues();
        double costs = financialData.getCosts();
        double profits = financialData.getProfits();

        System.out.println("Financial Data:");
        System.out.println("Total Revenues: $" + String.format("%.2f", revenues));
        System.out.println("Total Costs: $" + String.format("%.2f", costs));
        System.out.println("Total Profits: $" + String.format("%.2f", profits));
    }

    /**
     * Records a sale of a product and updates the financial data.
     *
     * @param product the product sold
     * @param quantity the quantity sold
     */
    public void recordSale(Product product, int quantity) {
        double saleAmount = product.getPrice() * quantity;
        double costAmount = 0.0;
        financialData.updateData(saleAmount, costAmount);
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
