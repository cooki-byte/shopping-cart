package cop4331.client;

public class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product() {}

    public Product(String id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Other potential methods
    /**
     * Reduces the available quantity of the product.
     * @param amount the amount to reduce.
     * @throws IllegalArgumentException if the amount is greater than the available quantity.
     */
    public void reduceQuantity(int amount) {
        if (amount > this.quantity) {
            throw new IllegalArgumentException("Not enough stock available.");
        }
        this.quantity -= amount;
    }

    /**
     * Increases the available quantity of the product.
     * @param amount the amount to add.
     */
    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
}
