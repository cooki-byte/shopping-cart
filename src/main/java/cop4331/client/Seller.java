package cop4331.client;

public class Seller extends User {
    private Inventory inventory;

    public Seller() {
        this.inventory = new Inventory(); // Initialize with an empty inventory
    }

    public Seller(String id, String username, String password) {
        super(id, username, password);
        this.inventory = new Inventory(); // Initialize with an empty inventory
    }

    public Seller(String id, String username, String password, Inventory inventory) {
        super(id, username, password);
        this.inventory = inventory;
    }

    // Get the seller's inventory
    public Inventory getInventory() {
        return inventory;
    }

    // Set the seller's inventory
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // Add a product to the inventory
    public void addProduct(Product product) {
        inventory.addProduct(product);
        System.out.println("Product added to inventory: " + product.getName());
    }

    // Update the inventory (e.g., notify observers)
    public void updateInventory() {
        inventory.notifyObservers();
        System.out.println("Inventory updated.");
    }

    // View financial data (e.g., total inventory value)
    public void viewFinancialData() {
        double totalValue = inventory.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
        System.out.println("Total inventory value: $" + totalValue);
    }

    @Override
    public void logout() {
        System.out.println("Seller " + username + " logged out.");
    }
}
